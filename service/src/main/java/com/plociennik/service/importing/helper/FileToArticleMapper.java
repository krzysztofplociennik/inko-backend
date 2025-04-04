package com.plociennik.service.importing.helper;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import com.plociennik.service.tag.TagHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FileToArticleMapper {

    private final TagHelper tagHelper;

    public ArticleEntity map(MultipartFile file) {
        String content = null;
        try {
            content = new String(file.getBytes());
        } catch (IOException e) {
            throw new InkoRuntimeException("An error occurred when trying to extract the file's content", "202504031634");
        }

        String[] fileContentSplit = content.split("\n");
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

    private String extractTitle(String[] contentSplit) {
        String titleLine = contentSplit[1].trim();
        return titleLine.substring(7);
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
        String subString = dateLine.substring(22);
        return LocalDateTime.parse(subString);
    }

    private List<TagEntity> extractTags(String[] contentSplit) {
        String tagsLine = contentSplit[5].trim();
        String subString = tagsLine.substring(6);

        String tagsWithNoBrackets = subString.replace("[", "").replace("]", "");
        String[] tags = tagsWithNoBrackets.split(",");

        Set<String> setOfTags = Arrays.stream(tags)
                .collect(Collectors.toSet());

        return tagHelper.mergeExistingTagsWithNewTags(setOfTags);
    }

    private String extractContent(String[] contentSplit) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 9; i < contentSplit.length; i++) {
            String line = contentSplit[i];
            stringBuilder
                    .append("\n")
                    .append(line);
        }
        return stringBuilder.toString();
    }
}
