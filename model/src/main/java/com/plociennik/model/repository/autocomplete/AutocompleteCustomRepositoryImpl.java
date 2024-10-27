package com.plociennik.model.repository.autocomplete;

import com.plociennik.model.AutocompleteEntity;
import com.plociennik.model.QAutocompleteEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AutocompleteCustomRepositoryImpl implements AutocompleteCustomRepository {

    private final EntityManager entityManager;

    @Override
    public List<AutocompleteEntity> findAllBySearchPhrase(String phrase) {

        // todo: repeat code, to be fixed

        JPAQuery<AutocompleteEntity> query = new JPAQuery<>(this.entityManager);

        QAutocompleteEntity qAutocompleteEntity = QAutocompleteEntity.autocompleteEntity;

        BooleanExpression valueContainsPhrase = qAutocompleteEntity.value.contains(phrase);

        List<AutocompleteEntity> allMatchedAutocompletes = query.from(qAutocompleteEntity)
                .where(valueContainsPhrase)
                .fetch();

        List<AutocompleteEntity> reverseSortedAutocompletes = allMatchedAutocompletes.stream()
                .sorted(Comparator.comparing(AutocompleteEntity::getNumberOfUses).reversed())
                .distinct()
                .limit(5)
                .toList();

        return reverseSortedAutocompletes;
    }

    @Override
    public Optional<AutocompleteEntity> findExactAutocomplete(String phrase) {

        JPAQuery<AutocompleteEntity> query = new JPAQuery<>(this.entityManager);

        QAutocompleteEntity qAutocompleteEntity = QAutocompleteEntity.autocompleteEntity;

        BooleanExpression valueEqualsPhrase = qAutocompleteEntity.value.eq(phrase);

        AutocompleteEntity autocompleteEntity = query.from(qAutocompleteEntity)
                .where(valueEqualsPhrase)
                .fetchOne();

        if (autocompleteEntity == null) {
            return Optional.empty();
        }
        return Optional.of(autocompleteEntity);
    }
}
