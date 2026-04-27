package com.plociennik.web.api;

import com.plociennik.common.errorhandling.exceptions.ArticleNotFoundException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.article.ArticleCustomRepositoryImpl;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.service.article.ArticleCreateService;
import com.plociennik.service.article.ArticleDeleteService;
import com.plociennik.service.article.ArticleReadService;
import com.plociennik.service.article.ArticleUpdateService;
import com.plociennik.service.article.dto.ArticleCreate;
import com.plociennik.service.article.dto.ArticleDetails;
import com.plociennik.service.article.dto.ArticleUpdate;
import com.plociennik.web.IntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Transactional
class ArticleControllerTest extends IntegrationTest {

    @Autowired
    private ArticleReadService articleReadService;
    @Autowired
    private ArticleCreateService articleCreateService;
    @Autowired
    private ArticleUpdateService articleUpdateService;
    @Autowired
    private ArticleDeleteService articleDeleteService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleCustomRepositoryImpl articleCustomRepository;

    @Test
    void getDetails() {
        // given
        initArticles();
        String title = "Building a REST API with Spring Boot and PostgreSQL";
        ArticleEntity article = articleCustomRepository.findByPhrase(title).getFirst();

        // when / then
        Assertions.assertEquals(title, article.getTitle());
        Assertions.assertEquals(ArticleType.PROGRAMMING, article.getType());
        Assertions.assertEquals("Dummy content here (Building...)", article.getContent());
        Assertions.assertEquals("Java", article.getTags().get(0).getValue());
        Assertions.assertEquals("Spring Boot", article.getTags().get(1).getValue());
        Assertions.assertEquals("PostgreSQL", article.getTags().get(2).getValue());
        Assertions.assertEquals("REST", article.getTags().get(3).getValue());
        Assertions.assertEquals(LocalDateTime.of(2026, 1, 5, 10, 30), article.getCreationDate());
        Assertions.assertNull(article.getModificationDate());
    }

    @Test
    void getTypes() {
        // given
        List<String> allTypes = articleReadService.getAllTypes();

        // when / then
        Assertions.assertNotNull(allTypes);
        Assertions.assertFalse(allTypes.isEmpty());
    }

    @Test
    void create() throws ArticleNotFoundException {
        // given / when
//        ArticleEntity article = ArticleEntity.builder()
//                .title("Building a REST API with Spring Boot and PostgreSQL")
//                .content("Dummy content here (Building...)")
//                .type(ArticleType.PROGRAMMING)
//                .tags(List.of(
//                        TagEntity.builder().value("Java").build(),
//                        TagEntity.builder().value("Spring Boot").build(),
//                        TagEntity.builder().value("PostgreSQL").build(),
//                        TagEntity.builder().value("REST").build()
//                ))
//                .creationDate(LocalDateTime.of(2026, 1, 5, 10, 30))
//                .build();
//        ArticleEntity saved = articleRepository.save(article);

        ArticleCreate article = ArticleCreate.builder()
                .title("Mastering Git Branching Strategies for Team Collaboration")
                .content("Dummy content here")
                .type("OS")
                .tags(Set.of(
                        "Git",
                        "GitHub",
                        "DevOps",
                        "Agile"
                )).build();

        String savedID = articleCreateService.create(article);

        ArticleEntity saved = articleCustomRepository.findByUUID(UUID.fromString(savedID));

        LocalDateTime currentDateTime = LocalDateTime.now();

        // then
        Assertions.assertEquals(1, articleRepository.count());
        Assertions.assertEquals("Mastering Git Branching Strategies for Team Collaboration", saved.getTitle());
        Assertions.assertEquals(ArticleType.OS, saved.getType());
        Assertions.assertEquals("Dummy content here", saved.getContent());
        Assertions.assertTrue(saved.getTags().stream().anyMatch(t -> t.getValue().equals("Git")));
        Assertions.assertTrue(saved.getTags().stream().anyMatch(t -> t.getValue().equals("GitHub")));
        Assertions.assertTrue(saved.getTags().stream().anyMatch(t -> t.getValue().equals("DevOps")));
        Assertions.assertTrue(saved.getTags().stream().anyMatch(t -> t.getValue().equals("Agile")));
        Assertions.assertEquals(currentDateTime.getYear(), saved.getCreationDate().getYear());
        Assertions.assertEquals(currentDateTime.getMonthValue(), saved.getCreationDate().getMonthValue());
        Assertions.assertEquals(currentDateTime.getDayOfMonth(), saved.getCreationDate().getDayOfMonth());
        Assertions.assertEquals(currentDateTime.getHour(), saved.getCreationDate().getHour());
        Assertions.assertEquals(currentDateTime.getMinute(), saved.getCreationDate().getMinute());
        Assertions.assertNull(saved.getModificationDate());
    }

    @Test
    void update() throws ArticleNotFoundException {
        // given
        initArticles();
        UUID id = articleCustomRepository.findByPhrase("Docker").getFirst().getId();

        ArticleUpdate update = ArticleUpdate.builder()
                .id(id.toString())
                .title("Introduction to Docker and Container Orchestration [edit]")
                .content("Dummy content here (Introduction...) [edit]")
                .tags(Set.of(
                        "Docker",
                        "Kubernetes",
                        "DevOps",
                        "Orchestration"
                ))
                .build();

        // when
        ArticleDetails saved = articleUpdateService.update(update);

        // then
        Assertions.assertEquals(3, articleRepository.count());
        Assertions.assertEquals("Introduction to Docker and Container Orchestration [edit]", saved.getTitle());
        Assertions.assertEquals("Programming", saved.getType());
        Assertions.assertEquals("Dummy content here (Introduction...) [edit]", saved.getContent());
        Assertions.assertTrue(saved.getTags().contains("Docker"));
        Assertions.assertTrue(saved.getTags().contains("Kubernetes"));
        Assertions.assertTrue(saved.getTags().contains("DevOps"));
        Assertions.assertTrue(saved.getTags().contains("Orchestration"));
        Assertions.assertEquals(LocalDateTime.of(2025, 11, 22, 14, 0), saved.getCreationDate());
        Assertions.assertNotNull(saved.getModificationDate());
    }

    @Test
    void delete() throws ArticleNotFoundException {
        // given
        initArticles();
        long sizeBeforeDeletion = articleRepository.count();
        UUID id = articleCustomRepository.findByPhrase("JWT").getFirst().getId();

        // when
        articleDeleteService.delete(id.toString());

        long sizeAfterDeletion = articleRepository.count();

        // then
        Assertions.assertEquals(3, sizeBeforeDeletion);
        Assertions.assertEquals(2, sizeAfterDeletion);
    }

    void initArticles() {
        List<ArticleEntity> articlesToSave = List.of(
                ArticleEntity.builder()
                        .title("Building a REST API with Spring Boot and PostgreSQL")
                        .content("Dummy content here (Building...)")
                        .type(ArticleType.PROGRAMMING)
                        .tags(new ArrayList<>(List.of(
                                TagEntity.builder().value("Java").build(),
                                TagEntity.builder().value("Spring Boot").build(),
                                TagEntity.builder().value("PostgreSQL").build(),
                                TagEntity.builder().value("REST").build()
                        )))
                        .creationDate(LocalDateTime.of(2026, 1, 5, 10, 30))
                        .build(),
                ArticleEntity.builder()
                        .title("Introduction to Docker and Container Orchestration")
                        .content("Dummy content here (Introduction...)")
                        .type(ArticleType.PROGRAMMING)
                        .tags(new ArrayList<>(List.of(
                                TagEntity.builder().value("Docker").build(),
                                TagEntity.builder().value("Kubernetes").build(),
                                TagEntity.builder().value("DevOps").build()
                        )))
                        .creationDate(LocalDateTime.of(2025, 11, 22, 14, 0))
                        .build(),
                ArticleEntity.builder()
                        .title("Understanding JWT Authentication in Modern Web Apps")
                        .content("Dummy content here (Understanding...)")
                        .type(ArticleType.PROGRAMMING)
                        .tags(new ArrayList<>(List.of(
                                TagEntity.builder().value("JWT").build(),
                                TagEntity.builder().value("Security").build(),
                                TagEntity.builder().value("Spring Security").build(),
                                TagEntity.builder().value("Angular").build()
                        )))
                        .creationDate(LocalDateTime.of(2025, 9, 3, 9, 15))
                        .build()
        );        articleRepository.saveAll(articlesToSave);
    }
}