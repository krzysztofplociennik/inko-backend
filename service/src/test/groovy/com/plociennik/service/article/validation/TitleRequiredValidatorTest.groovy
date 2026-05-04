package com.plociennik.service.article.validation

import com.plociennik.service.article.dto.ArticleCreate
import spock.lang.Specification

class TitleRequiredValidatorTest extends Specification {

    TitleRequiredValidator validator = new TitleRequiredValidator()

    def "article should be valid only when title is not blank: [#testTitle]"() {
        given: "an article with the provided title"
            def articleCreate = ArticleCreate.builder()
                    .title(testTitle)
                    .type('Programming')
                    .content('dummy content')
                    .build()

        expect:
            validator.isValid(articleCreate) == expectedResult

        where:
            testTitle            | expectedResult
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
