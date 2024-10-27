package com.plociennik.model.repository.autocomplete;

import com.plociennik.model.AutocompleteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutocompleteRepository extends JpaRepository<AutocompleteEntity, UUID> {
}
