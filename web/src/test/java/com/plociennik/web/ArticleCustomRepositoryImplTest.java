package com.plociennik.web;

import com.plociennik.common.errorhandling.exceptions.ArticleNotFoundException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.article.ArticleCustomRepositoryImpl;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.service.article.search.ArticleFilter;
import com.plociennik.service.article.search.ArticleSpecification;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Transactional
public class ArticleCustomRepositoryImplTest extends IntegrationTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCustomRepositoryImpl articleCustomRepository;

    @Test
    void shouldFindArticleByPhraseInTitle() {
        // given
        ArticleEntity article = ArticleEntity.builder()
                .title("Spring Boot Guide")
                .content("Some content here")
                .type(ArticleType.PROGRAMMING)
                .creationDate(LocalDateTime.now())
                .build();
        articleRepository.save(article);

        // when
        List<ArticleEntity> results = articleCustomRepository.findByPhrase("Spring Boot");

        // then
        assertThat(results).hasSize(1);
        assertThat(results.getFirst().getTitle()).isEqualTo("Spring Boot Guide");
    }

    @Test
    void shouldFindArticleByPhraseInContent() {
        // given
        ArticleEntity article = ArticleEntity.builder()
                .title("Some title")
                .content("This is about Docker containers")
                .type(ArticleType.TOOLS)
                .creationDate(LocalDateTime.now())
                .build();
        articleRepository.save(article);

        // when
        List<ArticleEntity> results = articleCustomRepository.findByPhrase("Docker containers");

        // then
        assertThat(results).hasSize(1);
    }

    @Test
    void shouldFindArticleByUUID() {
        // given
        ArticleEntity article = ArticleEntity.builder()
                .title("Some title")
                .content("This is about Docker containers")
                .type(ArticleType.TOOLS)
                .creationDate(LocalDateTime.now())
                .build();
        ArticleEntity savedArticle = articleRepository.save(article);
        UUID savedId = savedArticle.getId();

        // when
        ArticleEntity searchedArticle;
        try {
            searchedArticle = articleCustomRepository.findByUUID(savedId);
        } catch (ArticleNotFoundException e) {
            throw new RuntimeException(e);
        }

        // then
        Assertions.assertNotNull(searchedArticle);
    }

    @Test
    void shouldFindArticleBySpecification() {
        // given
        ArticleEntity article = ArticleEntity.builder()
                .title("How to install Linux Mint")
                .content("Here is how to install Linux Mint on your PC.")
                .tags(List.of(
                        TagEntity.builder().value("Linux").build(),
                        TagEntity.builder().value("Mint").build()
                ))
                .type(ArticleType.OS)
                .creationDate(LocalDateTime.of(2026, 2, 4, 14, 22))
                .build();
        articleRepository.save(article);

        ArticleFilter filter = ArticleFilter.builder()
                .searchPhrase("Linux Mint")
                .tags(List.of("Linux", "Mint"))
                .creationDateFrom(LocalDate.of(2026, 2, 1))
                .creationDateTo(LocalDate.of(2026, 2, 5))
                .build();
        Specification<ArticleEntity> spec = ArticleSpecification.createWith(filter);
        Pageable pageable = PageRequest.of(0, 5);

        // when
        Page<ArticleEntity> pageOfArticles = articleCustomRepository.findBySpecification(spec, pageable);

        // then
        Assertions.assertNotNull(pageOfArticles);
        Assertions.assertEquals(1, pageOfArticles.getTotalElements());
    }

    @Test
    void shouldThrowWhenArticleNotFound() {
        // given
        UUID randomId = UUID.randomUUID();

        // when / then
        assertThatThrownBy(() -> articleCustomRepository.findByUUID(randomId))
                .isInstanceOf(ArticleNotFoundException.class);
    }
}
