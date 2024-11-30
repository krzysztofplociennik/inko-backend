package com.plociennik.service.article;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.model.repository.article.ArticleCustomRepository;
import com.plociennik.model.repository.tag.TagRepository;
import com.plociennik.service.article.dto.AllArticlesItem;
import com.plociennik.service.article.dto.ArticleCreate;
import com.plociennik.service.article.dto.ArticleDetails;
import com.plociennik.service.article.dto.ArticleUpdate;
import com.plociennik.service.article.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleCustomRepository articleCustomRepository;
    private final ArticleMapper articleMapper;
    private final TagRepository tagRepository;

    public ArticleService(ArticleRepository articleRepository, ArticleCustomRepository articleCustomRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.articleCustomRepository = articleCustomRepository;
        this.tagRepository = tagRepository;
        this.articleMapper = new ArticleMapper();
    }

    public List<AllArticlesItem> getAll() {
        List<AllArticlesItem> collect = articleRepository.findAll()
                .stream()
                .map(articleMapper::mapToAllItem)
                .collect(Collectors.toList());
        return collect;
    }

    public String save(ArticleCreate articleCreate) {
        List<TagEntity> tags = handleTags(articleCreate);
        ArticleEntity articleEntity = articleMapper.mapToEntity(articleCreate, tags);
        ArticleEntity save = articleRepository.save(articleEntity);
        return save.getId().toString();
    }

    private List<TagEntity> handleTags(ArticleCreate articleCreate) {
        Set<String> dtoTags = articleCreate.getTags();

        if (dtoTags == null || dtoTags.isEmpty()) {
            return new ArrayList<>();
        }

        List<TagEntity> existingTags = tagRepository.findByValueIn(dtoTags);
        List<TagEntity> resultTags = new ArrayList<>(existingTags);

        List<TagEntity> newTags = dtoTags.stream()
                .filter(dtoTag -> existingTags.stream().anyMatch(tagEntity -> !tagEntity.getValue().equals(dtoTag)))
                .map(s -> new TagEntity(null, new ArrayList<>(), s))
                .toList();

        resultTags.addAll(newTags);

        return resultTags;
    }

    public ArticleDetails getArticleDetails(String id) throws Exception {
        UUID uuid = UUID.fromString(id);
        Optional<ArticleEntity> searchedArticle = articleRepository.findById(uuid);
        if (searchedArticle.isPresent()) {
            ArticleEntity articleEntity = searchedArticle.get();
            ArticleDetails articleDetails = articleMapper.mapToDetails(articleEntity);
            return articleDetails;
        } else {
            throw new Exception("There is no article with this ID: '" + id + "' in the database! (eid: 310720240701");
        }
    }

    public void delete(String id) {
        UUID uuid = UUID.fromString(id);
        articleRepository.deleteById(uuid);
    }

    public List<String> getAllTypes() {
        ArticleType[] values = ArticleType.values();
        List<String> articleTypes = Arrays.stream(values)
                .map(Enum::toString)
                .toList();
        return articleTypes;
    }

    public ArticleDetails update(ArticleUpdate articleUpdate) {
        LocalDateTime modificationDate = LocalDateTime.now();

        UUID uuid = UUID.fromString(articleUpdate.getId());
        ArticleEntity entity = articleCustomRepository.findByUUID(uuid);

        entity.setTitle(articleUpdate.getTitle());
        entity.setContent(articleUpdate.getContent());
        entity.setModificationDate(modificationDate);

        ArticleEntity updatedArticle = articleRepository.save(entity);

        ArticleDetails response = articleMapper.mapToDetails(updatedArticle);

        return response;
    }
}