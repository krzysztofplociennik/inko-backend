package com.plociennik.service.importing.validation

import com.plociennik.service.importing.dto.ImportFilesRequestBody
import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification

class MissingFilesValidatorTest extends Specification {

    def validator = new MissingFilesFileRequestValidator()

    def "should return false when the request body has a null value"() {
        given:
            def requestBodyWithNullList = new ImportFilesRequestBody(null)
        when:
            def isRequestBodyValid = validator.isValid(requestBodyWithNullList)
        then:
            !isRequestBodyValid
    }

    def "should return false when the request body has an empty list value"() {
        given:
            def requestBodyWithEmptyList = new ImportFilesRequestBody(List.of())
        when:
            def isRequestBodyValid = validator.isValid(requestBodyWithEmptyList)
        then:
            !isRequestBodyValid
    }

    def "should return false when the request body has a null file in a list"() {
        given:
        def listWithNullValue = Arrays.asList(
                new MockMultipartFile("file1", "document1.txt", "text/plain", "Valid file content1".getBytes()),
                new MockMultipartFile("file2", "document2.txt", "text/plain", "Valid file content2".getBytes()),
                null
        )
            def requestBodyWithNullFile = new ImportFilesRequestBody(listWithNullValue)
        when:
            def isRequestBodyValid = validator.isValid(requestBodyWithNullFile)
        then:
            !isRequestBodyValid
    }

    def "should return true when the request body does not have any null values"() {
        given:
            def listWithNullValue = List.of(
                    new MockMultipartFile(
                            "file1", "document1.txt", "text/plain", "Valid file content1".getBytes()),
                    new MockMultipartFile(
                            "file2", "document2.txt", "text/plain", "Valid file content2".getBytes()),
            )
            def requestBodyWithNullFile = new ImportFilesRequestBody(listWithNullValue)
        when:
            def isRequestBodyValid = validator.isValid(requestBodyWithNullFile)
        then:
            isRequestBodyValid
    }
}
