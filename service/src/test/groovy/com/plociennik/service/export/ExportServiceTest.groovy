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

    def "should include tags in exported file content"() {
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

    def "SanitizeContent"() {
        given:
            String value = '<p class="ql-align-center">When you encounter this error:<img src="data:image/png;base64,iVBORw0CYII="></p>\n' +
                    '\n' +
                    '<p class="ql-align-right">you just have to delete a .lock file and run the application again.</p>\n' +
                    '<p></p>\n' +
                    '<p>One of the file&#39;s position can be at*:</p>\n' +
                    '<pre data-language="plain">\n' +
                    './home/{your-username}/.var/app/com.jetbrains.IntelliJ-IDEA-Community/config/JetBrains/IdeaIC2024.1/.lock\n' +
                    '</pre>\n' +
                    '<p>*Obviously - depending on your OS and where you initially installed the application it might not be the right directory.</p>\n' +
                    '<p></p>\n' +
                    '<br/>\n' +
                    '<p>PS. edited for testing</p>'

            ExportService exportService = new ExportService(null)

        when:
            def actual = exportService.sanitizeContent(value)

        then:
            String expected = '<p class="ql-align-center">When you encounter this error:<img src="\ndata:image/png;base64,iVBORw0CYII=">\n\n' +
                    '\n' +
                    '<p class="ql-align-right">you just have to delete a .lock file and run the application again.\n\n' +
                    '\n\n' +
                    'One of the file&#39;s position can be at*:\n\n' +
                    '<pre data-language="plain">\n' +
                    './home/{your-username}/.var/app/com.jetbrains.IntelliJ-IDEA-Community/config/JetBrains/IdeaIC2024.1/.lock\n' +
                    '</pre>\n' +
                    '*Obviously - depending on your OS and where you initially installed the application it might not be the right directory.\n\n' +
                    '\n\n' +
                    '\n' +
                    'PS. edited for testing\n'
            actual == expected
    }

    // --- helpers ---

    def "should handle article with blank title without throwing"() {
        given:
            articleRepository.findAll() >> [buildArticle("", "<p>Content</p>", [])]
        when:
            def result = exportService.packageAllArticlesIntoZip(true)
        then:
            result != null
            notThrown(Exception)
    }

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