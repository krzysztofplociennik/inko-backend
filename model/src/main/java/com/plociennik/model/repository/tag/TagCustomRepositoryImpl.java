package com.plociennik.model.repository.tag;

import com.plociennik.model.TagEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class TagCustomRepositoryImpl {

    // todo: to rewrite - no idea what is happening here

    private final TagRepository tagRepository;

    public List<TagEntity> getTags(Set<String> tags) {

        if (tags == null || tags.isEmpty()) {
            return new ArrayList<>();
        }

        List<TagEntity> existingTags = tagRepository.findByValueIn(tags);

        List<TagEntity> collect;
        if (existingTags.isEmpty()) {
            collect = tags.stream()
                    .map(value -> new TagEntity(null, new ArrayList<>(), value))
                    .toList();
        } else {
            return existingTags;
        }

        return collect;
    }
}
