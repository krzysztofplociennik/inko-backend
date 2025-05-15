package com.plociennik.service.admin;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.model.repository.tag.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DemoResetService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final DemoResetHelper demoResetHelper;

    @Value("${admin.token}")
    private String adminToken;

    public DemoResetService(
            ArticleRepository articleRepository,
            TagRepository tagRepository,
            DemoResetHelper demoResetHelper
    ) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.demoResetHelper = demoResetHelper;
    }

    public void reset(String adminToken) {
        throwIfAdminTokenIsInvalid(adminToken);

        log.info("Commencing demo reset... {}", "(eventId: 202505151514)");
        deleteAllArticlesAndTags();

        List<ArticleEntity> dummyArticles = demoResetHelper.createDummyArticles();
        articleRepository.saveAll(dummyArticles);

        log.info("Demo reset has been successfully finished! {}", "(eventId: 202505151515)");
    }

    private void throwIfAdminTokenIsInvalid(String adminToken) {
        boolean isValid = this.adminToken.equals(adminToken);
        if (!isValid) {
            throw new InkoRuntimeException("Admin token is not valid!", "202505151730");
        }
    }

    private void deleteAllArticlesAndTags() {
        log.info("Deleting all articles and tags... {}", "(eventId: 202505151516)");
        articleRepository.deleteAll();
        tagRepository.deleteAll();
        log.info("Deleting all articles and tags has been successfully finished! {}", "(eventId: 202505151517)");
    }
}
