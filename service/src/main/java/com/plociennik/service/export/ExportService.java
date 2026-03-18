package com.plociennik.service.export;

import com.plociennik.model.ArticleEntity;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.plociennik.common.util.StringUtil.newLine;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class ExportService {

    private final ArticleRepository articleRepository;

    public ExportService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public File packageAllArticlesIntoZip(boolean withHTML) {

        List<ArticleEntity> allArticles = this.articleRepository.findAll();
        if (isEmpty(allArticles)) {
            return null;
        }

        Path backupDir = null;
        try {
            backupDir = Files.createTempDirectory("articles_backup_");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (ArticleEntity article : allArticles) {
            String articleTitle = article.getTitle();
            String articleAsFileContent = createFileContent(article, withHTML);
            try {
                writeArticleContentIntoFile(articleTitle, articleAsFileContent, backupDir);
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

    private void writeArticleContentIntoFile(String articleTitle, String articleAsFileContent, Path backupDir) throws IOException {
        String safeTitle = StringUtils.replace(articleTitle, " ", "_")
                .replaceAll("[^a-zA-Z0-9_.-]", "");
        if (safeTitle.isEmpty()) {
            safeTitle = "article_" + UUID.randomUUID();
        }

        File file = new File(backupDir.toFile(), safeTitle + ".txt");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(articleAsFileContent);
        }
    }

    private String createFileContent(ArticleEntity entity, boolean withHTML) {
        StringBuilder sb = new StringBuilder();

        sb
                .append("UUID: " + entity.getId())
                .append(newLine(1) + "Title: " + entity.getTitle())
                .append(newLine(1) + "Type: " + entity.getType().toString())
                .append(newLine(1) + "Date of creation: " + getDate(entity.getCreationDate()))
                .append(newLine(1) + "Date of modification: " + getDate(entity.getModificationDate()))
                .append(newLine(1) + "Tags: [" + getTags(entity) + "]")
                .append(newLine(2) + "Content: " + newLine(2) + getContent(entity.getContent(), withHTML))
                .append(newLine(2));

        return sb.toString();
    }

    private String getTags(ArticleEntity entity) {
        return entity.getTags().stream()
                .map(tag -> tag.getValue().trim())
                .collect(Collectors.joining(", "));
    }

    private String getContent(String content, boolean withHTML) {
        if (withHTML) {
            return content;
        }
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
        if (directory.isDirectory() && directory.listFiles() != null) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                deleteDirectory(file);
            }
        }
        directory.delete();
    }
}
