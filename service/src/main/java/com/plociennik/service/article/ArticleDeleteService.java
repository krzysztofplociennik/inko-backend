package com.plociennik.service.article;

import com.plociennik.common.errorhandling.exceptions.ArticleNotFoundException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.model.repository.tag.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ArticleDeleteService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    public void delete(String articleId) throws ArticleNotFoundException {
        UUID articleUuid = UUID.fromString(articleId);

        Optional<ArticleEntity> optionalArticle = articleRepository.findById(articleUuid);

        if (optionalArticle.isEmpty()) {
            throw new ArticleNotFoundException(articleUuid.toString(), "241020251633");
        }

        ArticleEntity articleToBeDeleted = optionalArticle.get();
        List<TagEntity> tags = articleToBeDeleted.getTags();

        tags.forEach(tag -> deleteTagAssociation(tag, articleId));
        articleToBeDeleted.getTags().clear();
        articleRepository.deleteById(articleUuid);
    }

    private void deleteTagAssociation(TagEntity tagEntity, String articleId) {
        List<ArticleEntity> articles = tagEntity.getArticles();
        Optional<ArticleEntity> optionalAssociation = articles.stream()
                .filter(article -> article.getId().toString().equals(articleId))
                .findFirst();

        optionalAssociation.ifPresent(article -> articles.remove(article));
        TagEntity savedTag = tagRepository.save(tagEntity);
    }
}
