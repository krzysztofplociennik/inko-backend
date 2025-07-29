package com.plociennik.service.admin;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.tag.TagRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ArticlesParser {

    private final TagRepository tagRepository;

    private static final List<String> FILENAMES = List.of(
            "Arch_Linux_-_point_of_recovery.txt",
            "asdasdas.txt",
            "docker_mongo_replica.txt",
            "docker_random_stuff.txt",
            "elastic_search_commands.txt",
            "elasticsearch_setup.txt",
            "IntelliJ_Idea_is_already_running_popup.txt",
            "kubernetes_random_commands.txt",
            "linux_commands.txt",
            "Lorem_ipsum.txt",
            "mongo_-_przydatne.txt",
            "mongo_setup_with_docker.txt",
            "oracle_setup_with_docker.txt",
            "Oracle_SQL_-_print_drop__bin.txt",
            "Pingowanie_poczenia_internetowego.txt",
            "Root_partition_almost_full_on_Arch_Linux.txt",
            "Simple_CSS_border_properties.txt",
            "Styling_PrimeNGs_components.txt"
    );

    public List<ArticleEntity> process() {

        List<ArticleEntity> parsedArticles = new ArrayList<>();

        for (String filename : FILENAMES) {
            parsedArticles.add(processFile(filename));
        }

        return parsedArticles;
    }

    private ArticleEntity processFile(String filename) {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/demoreset/" + filename)) {
            throwIfFileWasNotFound(inputStream, filename);
            try (
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(reader)
            ) {

                List<String> lines = new ArrayList<>();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    lines.add(line);
                }

                ArticleEntity mappedArticle = parse(lines);
                return mappedArticle;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArticleEntity parse(List<String> lines) {

        String uuidLine = lines.getFirst();
        String uuidSubstring = uuidLine.substring(6);
        UUID uuid = UUID.fromString(uuidSubstring);

        String titleLine = lines.get(1);
        String title = titleLine.substring(7);

        String typeLine = lines.get(2);
        String typeSubstring = typeLine.substring(6);
        ArticleType type = ArticleType.getType(typeSubstring);

        String creationLine = lines.get(3);
        String creationDateSubstring = creationLine.substring(18);
        LocalDateTime creationDate = LocalDateTime.parse(creationDateSubstring);

        String modificationLine = lines.get(4);
        String modificationDateSubstring = modificationLine.substring(22);
        LocalDateTime modificationDate;
        if (StringUtils.isBlank(modificationDateSubstring)) {
            modificationDate = null;
        } else {
            modificationDate = LocalDateTime.parse(modificationDateSubstring);
        }

        String tagsLine = lines.get(5);
        List<String> tagsAsStrings = extractTags(tagsLine);
        List<TagEntity> mappedTags = mapTags(tagsAsStrings);
        saveAndFlushTagsIfNotPresent(mappedTags);

        StringBuilder contentAsSB = new StringBuilder();
        for (int i = 9; i < lines.size(); i++) {
            contentAsSB.append(lines.get(i)).append("\n");
        }

        return ArticleEntity.builder()
                .id(uuid)
                .title(title)
                .type(type)
                .creationDate(creationDate)
                .modificationDate(modificationDate)
                .tags(mappedTags)
                .content(contentAsSB.toString())
                .build();
    }

    private void saveAndFlushTagsIfNotPresent(List<TagEntity> mappedTags) {
        for (TagEntity mappedTag : mappedTags) {
            Optional<TagEntity> optional = tagRepository.findByValue(mappedTag.getValue());
            if (optional.isEmpty()) {
                tagRepository.saveAndFlush(mappedTag);
            }
        }
    }

    private List<TagEntity> mapTags(List<String> tagsAsStrings) {
        List<TagEntity> resultTags = new ArrayList<>();
        for (String tagAsString : tagsAsStrings) {
            Optional<TagEntity> optional = tagRepository.findByValue(tagAsString);
            if (optional.isPresent()) {
                resultTags.add(optional.get());
            } else {
                TagEntity tag = TagEntity.builder()
                        .articles(new ArrayList<>())
                        .value(tagAsString)
                        .build();
                resultTags.add(tag);
            }
        }
        return resultTags;
    }

    private List<String> extractTags(String tagsLine) {
        String subString = tagsLine.substring(6);

        String tagsWithNoBrackets = subString.replace("[", "").replace("]", "");
        String[] tags = tagsWithNoBrackets.split(",");

        Set<String> setOfTags = Arrays.stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .collect(Collectors.toSet());

        return setOfTags.isEmpty()
                ? new ArrayList<>()
                : new ArrayList<>(setOfTags);
    }

    private void throwIfFileWasNotFound(InputStream inputStream, String filename) {
        if (inputStream == null) {
            throw new InkoRuntimeException("The file: [" + filename + "] has not been found!", "202507291051");
        }
    }
}
