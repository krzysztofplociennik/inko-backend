package com.plociennik.service.export

import com.plociennik.model.ArticleEntity
import com.plociennik.model.ArticleType
import com.plociennik.model.TagEntity
import com.plociennik.model.repository.article.ArticleRepository
import spock.lang.Specification

import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.util.zip.ZipFile

class ExportServiceTest extends Specification {

    def articleRepository = Mock(ArticleRepository)
    def exportService = new ExportService(articleRepository)

    def "should return null when there are no articles"() {
        given:
            articleRepository.findAll() >> []
        when:
            def result = exportService.packageAllArticlesIntoZip(true)
        then:
            result == null
    }

    def "should produce a zip file when articles exist"() {
        given:
            articleRepository.findAll() >> [buildArticle("My Article", "<p>Content</p>", [])]
        when:
            def result = exportService.packageAllArticlesIntoZip(true)
        then:
            result != null
            result.exists()
            result.name.endsWith(".zip")
    }

    def "should produce one file per article in the zip"() {
        given:
            articleRepository.findAll() >> [
                    buildArticle("First Article", "<p>Hello</p>", []),
                    buildArticle("Second Article", "<p>World</p>", [])
            ]
        when:
            def result = exportService.packageAllArticlesIntoZip(true)
        then:
            getZipEntryNames(result).size() == 2
    }

    def "should use article title as the filename"() {
        given:
            articleRepository.findAll() >> [buildArticle("My Cool Article", "<p>Hi</p>", [])]
        when:
            def result = exportService.packageAllArticlesIntoZip(true)
        then:
            getZipEntryNames(result).any { it.contains("My_Cool_Article") }
    }

    def "should strip HTML tags from content when withHTML is false"() {
        given:
            articleRepository.findAll() >> [buildArticle("Article", "<p>Clean content</p>", [])]
        when:
            def result = exportService.packageAllArticlesIntoZip(false)
        then:
            def content = readFirstZipEntry(result)
            !content.contains("<p>")
            !content.contains("</p>")
            content.contains("Clean content")
    }

    def "should preserve HTML tags in content when withHTML is true"() {
        given:
            articleRepository.findAll() >> [buildArticle("Article", "<p>Raw content</p>", [])]
        when:
            def result = exportService.packageAllArticlesIntoZip(true)
        then:
            def content = readFirstZipEntry(result)
            content.contains("<p>Raw content</p>")
    }

    def "should include article tags in exported file content"() {
        given:
            def tag = new TagEntity(id: UUID.randomUUID(), value: "Docker", articles: [])
            articleRepository.findAll() >> [buildArticle("Article", "<p>Content</p>", [tag])]
        when:
            def result = exportService.packageAllArticlesIntoZip(true)
        then:
            readFirstZipEntry(result).contains("Docker")
    }

    def "should handle article title with special characters safely"() {
        given:
            articleRepository.findAll() >> [buildArticle("C# & Java: A Comparison!", "<p>Content</p>", [])]
        when:
            def result = exportService.packageAllArticlesIntoZip(true)
        then:
            result != null
            getZipEntryNames(result).size() == 1
    }

    def "should handle article with blank title without throwing"() {
        given:
            articleRepository.findAll() >> [buildArticle("", "<p>Content</p>", [])]
        when:
            def result = exportService.packageAllArticlesIntoZip(true)
        then:
            result != null
            notThrown(Exception)
    }

    def "should include multiple article tags in exported file content"() {
        given:
            def tag1 = new TagEntity(id: UUID.randomUUID(), value: "Docker", articles: [])
            def tag2 = new TagEntity(id: UUID.randomUUID(), value: "Linux", articles: [])
            articleRepository.findAll() >> [buildArticle("Article", "<p>Content</p>", [tag1, tag2])]
        when:
            def result = exportService.packageAllArticlesIntoZip(true)
        then:
            def content = readFirstZipEntry(result)
            content.contains("Tags: [Docker, Linux]")
            !content.contains("Docker, Linux,") // no trailing comma
            !content.contains("Linux, Docker,") // no trailing comma
    }

    def "should handle article with no article tags without throwing"() {
        given:
            articleRepository.findAll() >> [buildArticle("Article", "<p>Content</p>", [])]
        when:
            exportService.packageAllArticlesIntoZip(true)
        then:
            notThrown(Exception)
    }

    def "should trim whitespace from article tags"() {
        given:
            def tag = new TagEntity(id: UUID.randomUUID(), value: "  Docker  ", articles: [])
            articleRepository.findAll() >> [buildArticle("Article", "<p>Content</p>", [tag])]
        when:
            def result = exportService.packageAllArticlesIntoZip(true)
        then:
            def content = readFirstZipEntry(result)
            content.contains("Tags: [Docker]")
            !content.contains("  Docker  ")
    }

    // --- helpers ---

    private ArticleEntity buildArticle(String title, String content, List<TagEntity> tags) {
        return ArticleEntity.builder()
                .id(UUID.randomUUID())
                .title(title)
                .content(content)
                .type(ArticleType.PROGRAMMING)
                .tags(tags)
                .creationDate(LocalDateTime.now())
                .modificationDate(null)
                .build()
    }

    private List<String> getZipEntryNames(File zipFile) {
        new ZipFile(zipFile).withCloseable { zip ->
            zip.entries().collect { it.name }
        }
    }

    private String readFirstZipEntry(File zipFile) {
        new ZipFile(zipFile).withCloseable { zip ->
            def entry = zip.entries().nextElement()
            zip.getInputStream(entry).getText(StandardCharsets.UTF_8.name())
        }
    }
}