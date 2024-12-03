package com.plociennik.service.tag;

import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.tag.TagRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class TagHelper {

    private final TagRepository tagRepository;

    public TagHelper(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagEntity> mergeExistingTagsWithNewTags(Set<String> tags) {

        if (tags == null || tags.isEmpty()) {
            return new ArrayList<>();
        }

        List<TagEntity> existingTags = tagRepository.findByValueIn(tags);
        List<TagEntity> resultTags = new ArrayList<>(existingTags);

        List<TagEntity> newTags = tags.stream()
                .filter(dtoTag -> existingTags.stream().anyMatch(tagEntity -> !tagEntity.getValue().equals(dtoTag)))
                .map(s -> new TagEntity(null, new ArrayList<>(), s))
                .toList();

        resultTags.addAll(newTags);

        return resultTags;
    }
}
