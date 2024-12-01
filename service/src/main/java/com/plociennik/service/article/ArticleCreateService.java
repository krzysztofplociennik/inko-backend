package com.plociennik.service.article;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.TagEntity;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.model.repository.tag.TagRepository;
import com.plociennik.service.article.dto.ArticleCreate;
import com.plociennik.service.article.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ArticleCreateService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final TagRepository tagRepository;

    public ArticleCreateService(
            ArticleRepository articleRepository,
            TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.articleMapper = new ArticleMapper();
        this.tagRepository = tagRepository;
    }

    public String create(ArticleCreate articleCreate) {
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
}
