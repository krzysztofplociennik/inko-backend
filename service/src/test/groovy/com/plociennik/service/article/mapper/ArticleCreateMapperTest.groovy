package com.plociennik.service.article.mapper

import com.plociennik.model.ArticleType
import com.plociennik.model.TagEntity
import com.plociennik.service.article.dto.ArticleCreate
import spock.lang.Specification

class ArticleCreateMapperTest extends Specification {

    ArticleCreateMapper mapper = new ArticleCreateMapper()

    def "should map correctly"() {
        given:
            def stringTags = Set.of("CSS", "HTML")
            def article = ArticleCreate.builder()
                    .title("title")
                    .type("DATABASE")
                    .content("content")
                    .tags(stringTags)
                    .build()
            def entityTags = List.of(
                        TagEntity.builder().value("CSS").build(),
                        TagEntity.builder().value("HTML").build()
            )
        when:
            def mappedArticle = mapper.mapToEntity(article, entityTags)
        then:
            mappedArticle != null
            mappedArticle.id == null
            mappedArticle.title == "title"
            mappedArticle.tags.size() == 2
            mappedArticle.tags.stream().anyMatch {t -> t.value == "CSS"}
            mappedArticle.tags.stream().anyMatch {t -> t.value == "HTML"}
            mappedArticle.content == "content"
            mappedArticle.type == ArticleType.DATABASE
            mappedArticle.creationDate != null
            mappedArticle.modificationDate == null
    }

}
