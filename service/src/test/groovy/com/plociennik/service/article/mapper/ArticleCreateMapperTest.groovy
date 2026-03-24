package com.plociennik.service.article.mapper

import com.plociennik.model.ArticleType
import com.plociennik.model.TagEntity
import com.plociennik.service.article.dto.ArticleCreate
import spock.lang.Specification

class ArticleCreateMapperTest extends Specification {

    ArticleCreateMapper mapper = new ArticleCreateMapper()

    def "should map correctly"() {
        given:
            def tags = Set.of("CSS", "HTML")
            def article = ArticleCreate.builder()
                    .title("title")
                    .type("DATABASE")
                    .content("content")
                    .tags(tags)
                    .build()
            def tags2 = List.of(
                        TagEntity.builder().value("CSS").build(),
                        TagEntity.builder().value("HTML").build()
            )
        when:
            def mappedArticle = mapper.mapToEntity(article, tags2)
        then:
            mappedArticle != null
            mappedArticle.id == null
            mappedArticle.title == "title"
            mappedArticle.tags.size() == 2
            mappedArticle.tags.contains("CSS")
            mappedArticle.tags.contains("HTML")
            mappedArticle.content == "content"
            mappedArticle.type == ArticleType.DATABASE
            mappedArticle.creationDate != null
            mappedArticle.modificationDate == null
    }

}
