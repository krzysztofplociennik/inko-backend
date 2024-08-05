package com.plociennik.model.repository;

import com.plociennik.model.TagEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class TagCustomRepositoryImpl {

    private final TagRepository tagRepository;

    public List<TagEntity> getTagValues(Set<String> values) {

        List<TagEntity> byValueIn = tagRepository.findByValueIn(values);

        List<TagEntity> collect;
        if (byValueIn.isEmpty()) {
            collect = values.stream()
                    .map(value -> new TagEntity(null, new ArrayList<>(), value))
                    .toList();
        } else {
            return byValueIn;
        }

        return collect;
    }
}
