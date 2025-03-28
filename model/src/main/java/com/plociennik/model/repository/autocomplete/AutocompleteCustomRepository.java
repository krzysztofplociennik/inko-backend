package com.plociennik.model.repository.autocomplete;

import com.plociennik.model.AutocompleteEntity;

import java.util.List;
import java.util.Optional;

public interface AutocompleteCustomRepository {

    List<AutocompleteEntity> findAllBySearchPhrase(String phrase);
    Optional<AutocompleteEntity> findExactAutocomplete(String phrase);
}
