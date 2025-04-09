package com.plociennik.service.importing.helper

import com.plociennik.model.ArticleEntity
import com.plociennik.model.ArticleType
import com.plociennik.model.TagEntity
import com.plociennik.model.repository.article.ArticleCustomRepositoryImpl
import com.plociennik.model.repository.tag.TagRepository
import com.plociennik.service.tag.TagHelper
import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification

import java.time.LocalDateTime

class FileToArticleMapperTest extends Specification {

    def tagRepository = Mock(TagRepository)
    def tagHelper = Mock(TagHelper)
    def articleRepository = Mock(ArticleCustomRepositoryImpl)
    def mapper = new FileToArticleMapper(tagHelper, articleRepository)

    def "should be able to map the file to an expected entity"() {
        given: "a mock file"
            String content =
                    """UUID: 51884e4c-c2ca-4772-8930-25c57d0291a7
                    Title: Simple CSS border properties
                    Type: Programming
                    Date of creation: 2024-11-04T18:15:47.841903
                    Date of modification: 2025-02-16T18:36:31.412107
                    Tags: [CSS]
                    
                    Content: 
                    
                    <p>Here are some simple CSS properties that can show a particle border:a</p><pre data-language="plain">

                    """
        MockMultipartFile validFile = new MockMultipartFile(
                        "files", "document2.txt", "text/plain", content.getBytes())
        and: "mocking the repository find method"
            tagRepository.findByValue("CSS") >> Optional.of(new TagEntity(null, List.of(), "CSS"))
        and: "mocking the helper"
            tagHelper.mergeExistingTagsWithNewTags(Set.of("CSS")) >> List.of(new TagEntity(null, List.of(), "CSS"))
        and: "mocking the article repository find method"
            articleRepository.findByPhrase("Simple CSS border properties") >> List.of()
        when: "mapping the file into an entity"
            def actualEntity = mapper.map(validFile)
        then: "expected entity and actual entity should match"
            def expectedTags = List.of(new TagEntity(null, List.of(), "CSS"))

            def expectedEntity = ArticleEntity.builder()
                    .id()
                    .creationDate(LocalDateTime.of(2024, 11, 4, 18, 15, 47, 841_903_000))
                    .modificationDate(LocalDateTime.of(2025, 2, 16, 18, 36, 31, 412_107_000))
                    .type(ArticleType.PROGRAMMING)
                    .tags(expectedTags)
                    .content("""<p>Here are some simple CSS properties that can show a particle border:a</p><pre data-language="plain">""")
                    .title("Simple CSS border properties")
                    .build()
            actualEntity == expectedEntity
    }

    def "should be able to map the file to an entity 2"() {
        given: "a mock file"
            String content =
                    """UUID: 51884e4c-c2ca-4772-8930-25c57d0291a7
                    Title: Simple CSS border properties
                    Type: Programming
                    Date of creation: 2024-11-04T18:15:47.841903
                    Date of modification: 2025-02-16T18:36:31.412107
                    Tags: [CSS]
                
                    Content: 

                    <p>Here are some simple CSS properties that can show a particle border:a</p><pre data-language="plain">

                    """
        MockMultipartFile validFile = new MockMultipartFile(
                        "files", "document2.txt", "text/plain", content.getBytes())
        and: "mocking a repository find method"
            tagRepository.findByValue("CSS") >> Optional.of(new TagEntity(null, List.of(), "CSS"))
        and: "mocking a helper"
            tagHelper.mergeExistingTagsWithNewTags(Set.of("CSS")) >> List.of(new TagEntity(null, List.of(), "CSS"))
        and: "mocking the article repository find method"
            articleRepository.findByPhrase("Simple CSS border properties") >> List.of()
        when: "mapping the file into an entity"
            def actualEntity = mapper.map(validFile)
        then: "expected entity and actual entity should match"
            def expectedTags = List.of(new TagEntity(null, List.of(), "CSS"))

            def expectedEntity = ArticleEntity.builder()
                    .id()
                    .creationDate(LocalDateTime.of(2024, 11, 4, 18, 15, 47, 841_903_000))
                    .modificationDate(LocalDateTime.of(2025, 2, 16, 18, 36, 31, 412_107_000))
                    .type(ArticleType.PROGRAMMING)
                    .tags(expectedTags)
                    .content("""<p>Here are some simple CSS properties that can show a particle border:a</p><pre data-language="plain">""")
                    .title("Simple CSS border properties")
                    .build()
            actualEntity == expectedEntity
    }


}
