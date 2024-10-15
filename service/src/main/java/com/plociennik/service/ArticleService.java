package com.plociennik.service;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.ArticleRepository;
import com.plociennik.model.repository.TagCustomRepositoryImpl;
import com.plociennik.service.dto.AllArticlesItem;
import com.plociennik.service.dto.ArticleCreate;
import com.plociennik.service.dto.ArticleDetails;
import com.plociennik.service.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final TagCustomRepositoryImpl tagRepository;

    public ArticleService(ArticleRepository articleRepository, TagCustomRepositoryImpl tagRepository) {
        this.articleRepository = articleRepository;
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
        Set<String> dtoTags = articleCreate.getTags();
        List<TagEntity> tagsValues = tagRepository.getTagValues(dtoTags);
        ArticleEntity articleEntity = articleMapper.mapToEntity(articleCreate, tagsValues);
        ArticleEntity save = articleRepository.save(articleEntity);
        return save.getId().toString();
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
}