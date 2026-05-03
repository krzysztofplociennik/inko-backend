package com.plociennik.service.article.validation

import jakarta.validation.ConstraintValidatorContext
import spock.lang.Specification

class ArticleUpdateContentNotEmptyTest extends Specification {

    ArticleUpdateContentNotEmpty validator = new ArticleUpdateContentNotEmpty()
    ConstraintValidatorContext context = Mock()

    def "should return false for null"() {
        expect:
            !validator.isValid(null, context)
    }

    def "should return false for blank or empty content"() {
        expect:
            !validator.isValid(value, context)

        where:
            value << [
                    "",
                    "   ",
                    "<p></p>",
                    "<p><br></p>",
                    "<p>   </p>",
                    "<p><br/></p>",
                    "&nbsp;",
            ]
    }

    def "should return true for content with actual text"() {
        expect:
            validator.isValid(value, context)

        where:
            value << [
                    "Hello",
                    "<p>Hello world</p>",
                    "<p><strong>Bold text</strong></p>",
                    "<ul><li>Item one</li></ul>",
            ]
    }

}
