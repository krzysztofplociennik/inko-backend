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

    public List<TagEntity> getTagValues(Set<String> values) {

        if (values == null || values.isEmpty()) {
            return new ArrayList<>();
        }

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
