package com.plociennik.service.importing.helper;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.article.ArticleCustomRepositoryImpl;
import com.plociennik.service.tag.TagHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FileToArticleMapper {

    private final TagHelper tagHelper;
    private final ArticleCustomRepositoryImpl articleRepository;

    public ArticleEntity map(MultipartFile file) {
        String fileContents = extractFileContents(file);

        String[] fileContentSplit = fileContents.split("\n");
        return ArticleEntity.builder()
                .id(null)
                .title(extractTitle(fileContentSplit))
                .type(extractType(fileContentSplit))
                .creationDate(extractDateOfCreations(fileContentSplit))
                .modificationDate(extractDateOfModification(fileContentSplit))
                .tags(extractTags(fileContentSplit))
                .content(extractContent(fileContentSplit))
                .build();
    }

    private String extractFileContents(MultipartFile file) {
        String fileContents;
        try {
            fileContents = new String(file.getBytes());
        } catch (IOException e) {
            throw new InkoRuntimeException("An error occurred when trying to extract the file's fileContents", "202504031634");
        }
        return fileContents;
    }

    private String extractTitle(String[] contentSplit) {
        String titleLine = contentSplit[1].trim();
        String title = titleLine.substring(7);
        return changeTitleIfDuplicatesFound(title);
    }

    private String changeTitleIfDuplicatesFound(String title) {
        List<ArticleEntity> duplicates = articleRepository.findByPhrase(title);

        if (duplicates.isEmpty()) {
            return title;
        }

        int suffix = duplicates.size() + 1;
        String newTitle = title + " (" + suffix + ")";
        return changeTitleIfDuplicatesFound(newTitle);
    }

    private ArticleType extractType(String[] contentSplit) {
        String typeLine = contentSplit[2].trim();
        String subString = typeLine.substring(6);
        return ArticleType.getType(subString);
    }

    private LocalDateTime extractDateOfCreations(String[] contentSplit) {
        String dateLine = contentSplit[3].trim();
        String subString = dateLine.substring(18);
        return LocalDateTime.parse(subString);
    }

    private LocalDateTime extractDateOfModification(String[] contentSplit) {
        String dateLine = contentSplit[4].trim();
        String subString = dateLine.substring(21);
        if (StringUtils.isBlank(subString)) {
            return null;
        }
        return LocalDateTime.parse(subString.trim());
    }

    private List<TagEntity> extractTags(String[] contentSplit) {
        String tagsLine = contentSplit[5].trim();
        String subString = tagsLine.substring(6);

        String tagsWithNoBrackets = subString.replace("[", "").replace("]", "");
        String[] tags = tagsWithNoBrackets.split(",");

        Set<String> setOfTags = Arrays.stream(tags)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());

        return setOfTags.isEmpty()
                ? new ArrayList<>()
                : tagHelper.mergeExistingTagsWithNewTags(setOfTags);
    }

    private String extractContent(String[] contentSplit) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 9; i < contentSplit.length; i++) {
            String line = contentSplit[i].trim();
            stringBuilder
                    .append(line)
                    .append("\n");
        }
        return stringBuilder.toString().trim();
    }
}
