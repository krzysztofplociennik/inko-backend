package com.plociennik.service.article.validation

import com.plociennik.service.article.dto.ArticleCreate
import spock.lang.Specification

class ContentRequiredValidatorTest extends Specification {

    ContentRequiredValidator validator = new ContentRequiredValidator()

    def "article should be valid only when content is not blank: [#testContent]"() {
        given: "an article with the provided content"
            def articleCreate = ArticleCreate.builder()
                    .title('dummy title')
                    .type('Programming')
                    .content(testContent)
                    .build()

        expect:
            validator.isValid(articleCreate) == expectedResult

        where:
            testContent          | expectedResult
            "hello"              | true
            "a"                  | true
            "  leading spaces"   | true
            "trailing spaces  "  | true
            "  surrounded  "     | true
            "multi\nline"        | true
            ""                   | false
            " "                  | false
            "\t"                 | false
            "\n"                 | false
            "   "                | false
            null                 | false
    }
}
