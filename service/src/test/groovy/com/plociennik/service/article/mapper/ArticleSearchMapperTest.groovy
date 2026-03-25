package com.plociennik.service.article.mapper

import com.plociennik.model.ArticleEntity
import com.plociennik.model.ArticleType
import com.plociennik.model.TagEntity
import spock.lang.Specification

import java.time.LocalDateTime

class ArticleSearchMapperTest extends Specification {

    ArticleSearchMapper mapper = new ArticleSearchMapper()

    def "should map correctly"() {
        given:
            def entityTags = List.of(
                    TagEntity.builder().value("CSS").build(),
                    TagEntity.builder().value("HTML").build()
            )
            def randomUuid = UUID.fromString("d7f7ff0b-8dec-41c3-9a27-2a68ffc15d75")
            def dateTime = LocalDateTime.of(2026, 6, 9, 11, 33)
            def articleEntity = ArticleEntity.builder()
                    .id(randomUuid)
                    .title("example title")
                    .type(ArticleType.TOOLS)
                    .tags(entityTags)
                    .creationDate(dateTime)
                    .modificationDate(dateTime.plusDays(3))
                    .build()
        when:
            def mappedArticle = mapper.mapToRead(articleEntity)
        then:
            mappedArticle.id == randomUuid
            mappedArticle.title == "example title"
            mappedArticle.tags.size() == 2
            mappedArticle.tags.contains("CSS")
            mappedArticle.tags.contains("HTML")
            mappedArticle.type == ArticleType.TOOLS
            mappedArticle.creationDate == dateTime
            mappedArticle.modificationDate == LocalDateTime.of(2026, 6, 12, 11, 33)
    }

}
