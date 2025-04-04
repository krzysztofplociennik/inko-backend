package com.plociennik.service.importing.validation

import spock.lang.Specification
import spock.lang.Unroll

class CorrectTypeValidatorTest extends Specification {

    def validator = new CorrectTypeValidator()

    @Unroll("isFileOfCorrectType with filename '#filename' and contentType '#contentType' should return #expectedResult")
    def "should return correct boolean based on filename and content type validity"() {
        when: "the validator checks if the values are valid"
            def actualResult = validator.isFileOfCorrectExtensionAndType(filename as String, contentType as String)

        then: "the actual result should match the expected result"
            actualResult == expectedResult

        where: "provided with valid and invalid test cases"
            filename                            | contentType                       | expectedResult
            'testing_file.txt'                  | 'text/plain'                      | true
            'another.txt'                       | 'text/plain'                      | true
            'data_with_numbers_123.txt'         | 'text/plain'                      | true
            'UPPERCASE.TXT'                     | 'text/plain'                      | true
            '/path/to/file/archive.txt'         | 'text/plain'                      | true
            ".config.txt"                       | "text/plain"                      | true

            "testing_qq.pdf"                    | "application/pdf"                 | false
            "testing_ss.txt"                    | "application/pdf"                 | false
            "testing_aa.doc"                    | "text/plain"                      | false
            "image.jpeg"                        | "image/jpeg"                      | false
            "document.txt.pdf"                  | "application/pdf"                 | false
            "archive.zip"                       | "application/zip"                 | false
            "testing_file.txt"                  | "text/html"                       | false
            "script.js"                         | "application/javascript"          | false
            "no_extension_file"                 | "text/plain"                      | false
            "file_ends_with_dot."               | "text/plain"                      | false
            ".hiddenfile"                       | "text/plain"                      | false
            null                                | 'text/plain'                      | false
            'testing_file.txt'                  | null                              | false
            null                                | null                              | false
            ""                                  | "text/plain"                      | false
            "testing_file.txt"                  | ""                                | false
            ""                                  | ""                                | false
            "testing_file.txt"                  | " text/plain "                    | false

    }
}
