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

    public void delete(String id) throws ArticleNotFoundException {
        UUID uuid = UUID.fromString(id);

        Optional<ArticleEntity> optionalArticle = articleRepository.findById(uuid);

        if (optionalArticle.isEmpty()) {
            throw new ArticleNotFoundException(uuid.toString());
        }

        ArticleEntity articleToBeDeleted = optionalArticle.get();
        List<TagEntity> tags = articleToBeDeleted.getTags();

        tags.forEach(tag -> deleteTagAssociation(tag, id));
        articleToBeDeleted.getTags().clear();
        articleRepository.deleteById(uuid);
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
