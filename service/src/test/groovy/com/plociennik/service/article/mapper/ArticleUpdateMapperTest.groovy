package com.plociennik.service.article.mapper

import com.plociennik.model.ArticleEntity
import com.plociennik.model.ArticleType
import com.plociennik.model.TagEntity
import com.plociennik.service.article.dto.ArticleUpdate
import spock.lang.Specification

import java.time.LocalDateTime

class ArticleUpdateMapperTest extends Specification {

    ArticleUpdateMapper mapper = new ArticleUpdateMapper()

    def "should map correctly"() {
        given:
            def currentTags = List.of(
                    TagEntity.builder().value("CSS").build(),
                    TagEntity.builder().value("HTML").build()
            )
            def uuidString = "1cc8c207-ae54-43dc-a90b-2550573dba13"
            def randomUuid = UUID.fromString(uuidString)
            def creationDate = LocalDateTime.of(2026, 6, 9, 11, 33)
            def modificationDate = creationDate.plusDays(3)
            def currentArticleEntity = ArticleEntity.builder()
                    .title("example title")
                    .type(ArticleType.TOOLS)
                    .content("example content")
                    .tags(currentTags)
                    .creationDate(creationDate)
                    .modificationDate(modificationDate)
                    .build()
            currentArticleEntity.id = randomUuid
        when:
            def updatedTags = Set.of("CSS", "HTML", "Angular")
            def updatedArticleEntity = ArticleUpdate.builder()
                    .id(uuidString)
                    .title("example title [edit]")
                    .content("example content [edit]")
                    .tags(updatedTags)
                    .build()
            def mergedTags = List.of(
                    TagEntity.builder().value("CSS").build(),
                    TagEntity.builder().value("HTML").build(),
                    TagEntity.builder().value("Angular").build(),
            )
            def mappedArticle = mapper.map(currentArticleEntity, updatedArticleEntity, mergedTags)
        then:
            mappedArticle.id == randomUuid
            mappedArticle.title == "example title [edit]"
            mappedArticle.content == "example content [edit]"
            mappedArticle.tags.size() == 3
            mappedArticle.tags.stream().anyMatch {t -> t.value == "CSS"}
            mappedArticle.tags.stream().anyMatch {t -> t.value == "HTML"}
            mappedArticle.tags.stream().anyMatch {t -> t.value == "Angular"}
            mappedArticle.type == ArticleType.TOOLS
            mappedArticle.creationDate == creationDate
            mappedArticle.modificationDate != modificationDate
    }
}
