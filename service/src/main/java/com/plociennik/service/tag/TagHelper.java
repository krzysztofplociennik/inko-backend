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

    public List<TagEntity> mergeExistingTagsWithNewTags(Set<String> dtoTags) {

        if (dtoTags == null || dtoTags.isEmpty()) {
            return new ArrayList<>();
        }

        List<TagEntity> existingTags = tagRepository.findByValueIn(dtoTags);
        List<TagEntity> resultTags = new ArrayList<>(existingTags);

        List<TagEntity> newTags = dtoTags.stream()
                .filter(dtoTag -> isDtoTagNew(dtoTag, existingTags))
                .map(s -> new TagEntity(null, new ArrayList<>(), s))
                .toList();

        resultTags.addAll(newTags);

        return resultTags;
    }

    private boolean isDtoTagNew(String dtoTag, List<TagEntity> existingTags) {
        return existingTags.stream()
                .map(TagEntity::getValue)
                .noneMatch(existingTag -> existingTag.equals(dtoTag));
    }
}
