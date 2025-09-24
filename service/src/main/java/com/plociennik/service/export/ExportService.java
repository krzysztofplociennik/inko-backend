package com.plociennik.service.export;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.plociennik.common.util.StringUtil.newLine;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class ExportService {

    private final ArticleRepository articleRepository;

    private boolean withHTML = false;

    public ExportService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public File exportArticles(boolean withHTML) {

        List<ArticleEntity> all = this.articleRepository.findAll();
        if (isEmpty(all)) {
            return null;
        }

        Path backupDir = null;
        try {
            backupDir = Files.createTempDirectory("articles_backup_");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (ArticleEntity article : all) {
            try {
                this.withHTML = withHTML;
                createSingleFile(article, backupDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File zipFile = null;
        try {
            zipFile = zipFiles(backupDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        deleteDirectory(backupDir.toFile());

        return zipFile;
    }

    private void createSingleFile(ArticleEntity article, Path backupDir) throws IOException {
        String safeTitle = StringUtils.replace(article.getTitle(), " ", "_").replaceAll("[^a-zA-Z0-9_.-]", "");
        if (safeTitle.isEmpty()) {
            safeTitle = "article_" + UUID.randomUUID();
        }

        File file = new File(backupDir.toFile(), safeTitle + ".txt");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(createFileContent(article));
        }
    }

    private String createFileContent(ArticleEntity entity) {
        StringBuilder sb = new StringBuilder();

        sb.append("UUID: " + entity.getId())
                .append(newLine(1) + "Title: " + entity.getTitle())
                .append(newLine(1) + "Type: " + entity.getType().toString())
                .append(newLine(1) + "Date of creation: " + getDate(entity.getCreationDate()))
                .append(newLine(1) + "Date of modification: " + getDate(entity.getModificationDate()))
                .append(newLine(1) + "Tags: " + Arrays.toString(getTags(entity).toArray()))
                .append(newLine(2) + "Content: " + newLine(2) + handleContent(entity.getContent()))
                .append(newLine(2));

        return sb.toString();
    }

    private List<String> getTags(ArticleEntity entity) {
        return entity.getTags().stream()
                .map(TagEntity::getValue)
                .toList();
    }

    private String handleContent(String content) {
        if (!this.withHTML) {
            return sanitizeContent(content);
        }
        return content;
    }

    String sanitizeContent(String content) {
        return content
                .replaceAll("<p>", "")
                .replaceAll("<br>", "")
                .replaceAll("<br/>", "")
                .replaceAll("<img src=\"data", "<img src=\"\ndata")
                .replaceAll("</p>", "\n");
    }

    private String getDate(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return date.toString();
    }

    private File zipFiles(Path sourceDir) throws IOException {
        File zipFile = Files.createTempFile("backup_", ".zip").toFile();

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            Files.walk(sourceDir).filter(Files::isRegularFile).forEach(path -> {
                try {
                    ZipEntry zipEntry = new ZipEntry(sourceDir.relativize(path).toString());
                    zipOut.putNextEntry(zipEntry);
                    Files.copy(path, zipOut);
                    zipOut.closeEntry();
                } catch (IOException e) {
                    throw new RuntimeException("Error creating ZIP", e);
                }
            });
        }
        return zipFile;
    }

    private void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                deleteDirectory(file);
            }
        }
        directory.delete();
    }
}
