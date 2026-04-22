package com.plociennik.web;

import com.plociennik.common.errorhandling.exceptions.ArticleNotFoundException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.repository.article.ArticleCustomRepositoryImpl;
import com.plociennik.model.repository.article.ArticleRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    void shouldThrowWhenArticleNotFound() {
        // given
        UUID randomId = UUID.randomUUID();

        // when / then
        assertThatThrownBy(() -> articleCustomRepository.findByUUID(randomId))
                .isInstanceOf(ArticleNotFoundException.class);
    }
}
