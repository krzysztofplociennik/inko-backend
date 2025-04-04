package com.plociennik.service.importing.validation

import com.plociennik.service.importing.dto.ImportFilesRequestBody
import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification

class CorrectContentStructureValidatorTest extends Specification {

    def validator = new CorrectContentStructureValidator()

    def "should return true when file's content has a valid structure"() {
        given: "a request body with a valid mock file"
            String content =
                    """UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                    Title: Styling PrimeNG's components
                    Type: Programming
                    Date of creation: 2024-11-04T18:42:34.414108
                    Date of modification: 2024-12-08T12:39:24.108839
                    Tags: [CSS]
                    
                    Content: 
                    
                    <p>Styling components using PrimeNG can be very weird sometimes. These are examples that worked for me though:</p><pre data-language="plain">
                      &lt;p-chip [style]=&quot;{&#39;height&#39;: &#39;30px&#39;,&#39;width&#39;: &#39;auto&#39;}&quot;&gt;{{ tag }}&lt;/p-chip&gt;
                    
                      [style]=&quot;{&#39;height&#39;: &#39;35px&#39;, &#39;width&#39;: &#39;auto&#39;, &#39;margin&#39;: &#39;5px 5px 5px 5px&#39;}&quot;  
                    
                    ::ng-deep .p-card .p-card-title {
                        font-size: 20px;
                    }
                    </pre>
                    """
            def validFile = new MockMultipartFile(
                    "files", "document2.txt", "text/plain", content.getBytes())
            def requestBody = new ImportFilesRequestBody(List.of(validFile))
        when:
            def isRequestBodyValid = validator.isValid(requestBody)
        then:
            isRequestBodyValid
    }

    def "should return false when file's content is a blank string"() {
        given: "a request body with an invalid mock file"
            String blankContent =
                    """
                    """
            def invalidFile = new MockMultipartFile(
                    "files", "document2.txt", "text/plain", blankContent.getBytes())
            def requestBody = new ImportFilesRequestBody(List.of(invalidFile))
        when:
            def isRequestBodyValid = validator.isValid(requestBody)
        then:
            !isRequestBodyValid
    }

    def "should return false when file's content is not valid"() {
        given: "a request body with a file that has missing properties like title and date of modification"
            String content =
                    """
                        UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                        Type: Programming
                        Date of creation: 2024-11-04T18:42:34.414108

                        Tags: [CSS]
                        
                        Content: 
                        
                        <p>Styling components using PrimeNG can be very weird sometimes. These are examples that worked for me though:</p><pre data-language="plain">
                          &lt;p-chip [style]=&quot;{&#39;height&#39;: &#39;30px&#39;,&#39;width&#39;: &#39;auto&#39;}&quot;&gt;{{ tag }}&lt;/p-chip&gt;
                        
                          [style]=&quot;{&#39;height&#39;: &#39;35px&#39;, &#39;width&#39;: &#39;auto&#39;, &#39;margin&#39;: &#39;5px 5px 5px 5px&#39;}&quot;  
                        
                        ::ng-deep .p-card .p-card-title {
                            font-size: 20px;
                        }
                        </pre>
                        """
        def invalidFile = new MockMultipartFile(
                    "files", "document2.txt", "text/plain", content.getBytes())
            def requestBody = new ImportFilesRequestBody(List.of(invalidFile))
        when:
            def isRequestBodyValid = validator.isValid(requestBody)
        then:
            !isRequestBodyValid
    }

    def "should return false when the title property in the file's content is not valid"() {
        given:
            String content =
                    """
                    UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                    QTitle: Styling PrimeNG's components
                    Type: Programming
                    Date of creation: 2024-11-04T18:42:34.414108
                    Date of modification: 2024-12-08T12:39:24.108839
                    Tags: [CSS]
                    
                    Content: 
                    
                    <p>Styling components using PrimeNG can be very weird sometimes. These are examples that worked for me though:</p><pre data-language="plain">
                      &lt;p-chip [style]=&quot;{&#39;height&#39;: &#39;30px&#39;,&#39;width&#39;: &#39;auto&#39;}&quot;&gt;{{ tag }}&lt;/p-chip&gt;
                    
                      [style]=&quot;{&#39;height&#39;: &#39;35px&#39;, &#39;width&#39;: &#39;auto&#39;, &#39;margin&#39;: &#39;5px 5px 5px 5px&#39;}&quot;  
                    
                    ::ng-deep .p-card .p-card-title {
                        font-size: 20px;
                    }
                    </pre>
                    """
        def invalidFile = new MockMultipartFile(
                    "files", "document2.txt", "text/plain", content.getBytes())
            def requestBody = new ImportFilesRequestBody(List.of(invalidFile))
        when:
            def isRequestBodyValid = validator.isValid(requestBody)
        then:
            !isRequestBodyValid
    }

    def "should return false when the type property in the file's content is not valid"() {
        given:
            String content =
                    """
                    UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                    Title: Styling PrimeNG's components
                    QType: Programming
                    Date of creation: 2024-11-04T18:42:34.414108
                    Date of modification: 2024-12-08T12:39:24.108839
                    Tags: [CSS]
                    
                    Content: 
                    
                    <p>Styling components using PrimeNG can be very weird sometimes. These are examples that worked for me though:</p><pre data-language="plain">
                      &lt;p-chip [style]=&quot;{&#39;height&#39;: &#39;30px&#39;,&#39;width&#39;: &#39;auto&#39;}&quot;&gt;{{ tag }}&lt;/p-chip&gt;
                    
                      [style]=&quot;{&#39;height&#39;: &#39;35px&#39;, &#39;width&#39;: &#39;auto&#39;, &#39;margin&#39;: &#39;5px 5px 5px 5px&#39;}&quot;  
                    
                    ::ng-deep .p-card .p-card-title {
                        font-size: 20px;
                    }
                    </pre>
                    """
        def invalidFile = new MockMultipartFile(
                    "files", "document2.txt", "text/plain", content.getBytes())
            def requestBody = new ImportFilesRequestBody(List.of(invalidFile))
        when:
            def isRequestBodyValid = validator.isValid(requestBody)
        then:
            !isRequestBodyValid
    }

    def "should return false when the date of creation property in the file's content is not valid"() {
        given:
            String content =
                    """
                    UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                    Title: Styling PrimeNG's components
                    Type: Programming
                    QDate of creation: 2024-11-04T18:42:34.414108
                    Date of modification: 2024-12-08T12:39:24.108839
                    Tags: [CSS]
                    
                    Content: 
                    
                    <p>Styling components using PrimeNG can be very weird sometimes. These are examples that worked for me though:</p><pre data-language="plain">
                      &lt;p-chip [style]=&quot;{&#39;height&#39;: &#39;30px&#39;,&#39;width&#39;: &#39;auto&#39;}&quot;&gt;{{ tag }}&lt;/p-chip&gt;
                    
                      [style]=&quot;{&#39;height&#39;: &#39;35px&#39;, &#39;width&#39;: &#39;auto&#39;, &#39;margin&#39;: &#39;5px 5px 5px 5px&#39;}&quot;  
                    
                    ::ng-deep .p-card .p-card-title {
                        font-size: 20px;
                    }
                    </pre>
                    """
        def invalidFile = new MockMultipartFile(
                    "files", "document2.txt", "text/plain", content.getBytes())
            def requestBody = new ImportFilesRequestBody(List.of(invalidFile))
        when:
            def isRequestBodyValid = validator.isValid(requestBody)
        then:
            !isRequestBodyValid
    }

    def "should return false when the date of modification property in the file's content is not valid"() {
        given:
            String content =
                    """
                    UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                    Title: Styling PrimeNG's components
                    Type: Programming
                    Date of creation: 2024-11-04T18:42:34.414108
                    QDate of modification: 2024-12-08T12:39:24.108839
                    Tags: [CSS]
                    
                    Content: 
                    
                    <p>Styling components using PrimeNG can be very weird sometimes. These are examples that worked for me though:</p><pre data-language="plain">
                      &lt;p-chip [style]=&quot;{&#39;height&#39;: &#39;30px&#39;,&#39;width&#39;: &#39;auto&#39;}&quot;&gt;{{ tag }}&lt;/p-chip&gt;
                    
                      [style]=&quot;{&#39;height&#39;: &#39;35px&#39;, &#39;width&#39;: &#39;auto&#39;, &#39;margin&#39;: &#39;5px 5px 5px 5px&#39;}&quot;  
                    
                    ::ng-deep .p-card .p-card-title {
                        font-size: 20px;
                    }
                    </pre>
                    """
        def invalidFile = new MockMultipartFile(
                    "files", "document2.txt", "text/plain", content.getBytes())
            def requestBody = new ImportFilesRequestBody(List.of(invalidFile))
        when:
            def isRequestBodyValid = validator.isValid(requestBody)
        then:
            !isRequestBodyValid
    }

    def "should return false when the tags property in the file's content is not valid"() {
        given:
            String content =
                    """
                    UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                    Title: Styling PrimeNG's components
                    Type: Programming
                    Date of creation: 2024-11-04T18:42:34.414108
                    Date of modification: 2024-12-08T12:39:24.108839
                    QTags: [CSS]
                    
                    Content: 
                    
                    <p>Styling components using PrimeNG can be very weird sometimes. These are examples that worked for me though:</p><pre data-language="plain">
                      &lt;p-chip [style]=&quot;{&#39;height&#39;: &#39;30px&#39;,&#39;width&#39;: &#39;auto&#39;}&quot;&gt;{{ tag }}&lt;/p-chip&gt;
                    
                      [style]=&quot;{&#39;height&#39;: &#39;35px&#39;, &#39;width&#39;: &#39;auto&#39;, &#39;margin&#39;: &#39;5px 5px 5px 5px&#39;}&quot;  
                    
                    ::ng-deep .p-card .p-card-title {
                        font-size: 20px;
                    }
                    </pre>
                    """
        def invalidFile = new MockMultipartFile(
                    "files", "document2.txt", "text/plain", content.getBytes())
            def requestBody = new ImportFilesRequestBody(List.of(invalidFile))
        when:
            def isRequestBodyValid = validator.isValid(requestBody)
        then:
            !isRequestBodyValid
    }

    def "should return false when the content property in the file's content is not valid"() {
        given:
            String content =
                    """
                    UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                    Title: Styling PrimeNG's components
                    Type: Programming
                    Date of creation: 2024-11-04T18:42:34.414108
                    Date of modification: 2024-12-08T12:39:24.108839
                    Tags: [CSS]
                    
                    QContent: 
                    
                    <p>Styling components using PrimeNG can be very weird sometimes. These are examples that worked for me though:</p><pre data-language="plain">
                      &lt;p-chip [style]=&quot;{&#39;height&#39;: &#39;30px&#39;,&#39;width&#39;: &#39;auto&#39;}&quot;&gt;{{ tag }}&lt;/p-chip&gt;
                    
                      [style]=&quot;{&#39;height&#39;: &#39;35px&#39;, &#39;width&#39;: &#39;auto&#39;, &#39;margin&#39;: &#39;5px 5px 5px 5px&#39;}&quot;  
                    
                    ::ng-deep .p-card .p-card-title {
                        font-size: 20px;
                    }
                    </pre>
                    """
        def invalidFile = new MockMultipartFile(
                    "files", "document2.txt", "text/plain", content.getBytes())
            def requestBody = new ImportFilesRequestBody(List.of(invalidFile))
        when:
            def isRequestBodyValid = validator.isValid(requestBody)
        then:
            !isRequestBodyValid
    }
}
