package com.plociennik.service.article.validation

import com.plociennik.service.article.dto.ArticleCreate
import spock.lang.Specification

class TypeRequiredValidatorTest extends Specification {

    TypeRequiredValidator validator = new TypeRequiredValidator()

    def "article should be valid only when type is recognized: [#testType]"() {
        given: "an article with the provided type"
            def articleCreate = ArticleCreate.builder()
                    .title('dummy title')
                    .type(testType)
                    .content('dummy content')
                    .build()

        expect:
            validator.isValid(articleCreate) == expectedResult

        where:
            testType        | expectedResult
            "programming"   | true
            "PROGRAMMING"   | true
            "Programming"   | true
            "tools"         | true
            "TOOLS"         | true
            "database"      | true
            "DATABASE"      | true
            "os"            | true
            "OS"            | true
            "unknown"       | false
            "123"           | false
            ""              | false
            " "             | false
            null            | false
    }
}
