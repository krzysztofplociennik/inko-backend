package com.plociennik.service;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.ArticleRepository;
import com.plociennik.model.repository.TagCustomRepositoryImpl;
import com.plociennik.service.dto.ArticleCreate;
import com.plociennik.service.dto.ArticleDetails;
import com.plociennik.service.mapper.ArticleMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
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

    public List<ArticleDetails> getAll(int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        List<ArticleDetails> collect = articleRepository.findAll(pageRequest)
                .stream()
                .map(articleMapper::mapToRead)
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
            ArticleDetails articleDetails = articleMapper.mapToRead(articleEntity);
            return articleDetails;
        } else {
            throw new Exception("There is no article with this ID: '" + id + "' in the database! (eid: 310720240701");
        }
    }
}