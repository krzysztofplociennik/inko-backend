package com.plociennik.service.article.mapper

import com.plociennik.model.ArticleEntity
import com.plociennik.model.ArticleType
import com.plociennik.model.TagEntity
import spock.lang.Specification

import java.time.LocalDateTime

class ArticleReadMapperTest extends Specification {

    ArticleReadMapper mapper = new ArticleReadMapper()

    def "should map correctly"() {
        given:
            def entityTags = List.of(
                    TagEntity.builder().value("CSS").build(),
                    TagEntity.builder().value("HTML").build()
            )
            def randomUuid = "34fc88dd-e4f5-48dd-b16b-d0c6442aa71c"
            def dateTime = LocalDateTime.of(2026, 2, 22, 10, 53)
            def articleEntity = ArticleEntity.builder()
                    .title("dummy title")
                    .content("dummy content")
                    .type(ArticleType.PROGRAMMING)
                    .tags(entityTags)
                    .creationDate(dateTime)
                    .build()
            articleEntity.id = UUID.fromString(randomUuid)
        when:
            def mappedArticle = mapper.mapToDetails(articleEntity)
        then:
            mappedArticle != null
            mappedArticle.id == randomUuid
            mappedArticle.title == "dummy title"
            mappedArticle.content == "dummy content"
            mappedArticle.type == "Programming"
            mappedArticle.tags.size() == 2
            mappedArticle.tags.contains("CSS")
            mappedArticle.tags.contains("HTML")
            mappedArticle.creationDate == dateTime
            mappedArticle.modificationDate == null
    }

}
