package com.plociennik.model.repository;

import com.plociennik.model.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagRepository extends JpaRepository<TagEntity, UUID> {

    List<TagEntity> findByValueIn(Set<String> values);
}
