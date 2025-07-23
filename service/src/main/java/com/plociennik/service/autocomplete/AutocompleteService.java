package com.plociennik.service.autocomplete;

import com.plociennik.model.AutocompleteEntity;
import com.plociennik.model.repository.autocomplete.AutocompleteCustomRepository;
import com.plociennik.model.repository.autocomplete.AutocompleteRepository;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AutocompleteService {

    private final AutocompleteRepository repository;
    private final AutocompleteCustomRepository customRepository;

    public List<String> findAllBySearchPhrase(String phrase) {

        List<AutocompleteEntity> allBySearchPhrase = customRepository.findAllBySearchPhrase(phrase);

        List<String> autocompletes = allBySearchPhrase.stream()
                .map(AutocompleteEntity::getValue)
                .toList();

        return autocompletes;
    }

    public void incrementUsageIfExists(String searchPhrase) {
        if (StringUtils.isBlank(searchPhrase)) {
            return;
        }

        Optional<AutocompleteEntity> optionalExactAutocomplete = customRepository.findExactAutocomplete(searchPhrase);

        AutocompleteEntity entity;
        if (optionalExactAutocomplete.isPresent()) {
            entity = optionalExactAutocomplete.get();
            int currentNumberOfUsage = entity.getNumberOfUses();
            entity.setNumberOfUses(++currentNumberOfUsage);
        } else {
            entity = new AutocompleteEntity(null, searchPhrase, 1);
        }
        repository.save(entity);
    }
}
