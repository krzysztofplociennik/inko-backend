package com.plociennik.service.importing.validation

import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification

class TitleInFileContentNotBlankValidatorTest extends Specification {

    def validator = new TitleInFileContentNotBlankFileValidator()

    def "should return true when file's content does not have a blank title 1"() {
        given: "a valid mock file"
            String content =
                    """UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                        Title: Example title
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
        when:
            def isFileValid = validator.isValid(validFile)
        then:
            isFileValid
    }

    def "should return true when file's content does not have a blank title 2"() {
        given: "a valid mock file"
            String content =
                    """UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                        Title: \t       \tExample title
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
        when:
            def isFileValid = validator.isValid(validFile)
        then:
            isFileValid
    }

    def "should return false when file's content has a blank title 1"() {
        given:
            String content =
                    """UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                        Title:
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
        when:
            def isFileValid = validator.isValid(validFile)
        then:
            !isFileValid
    }

    def "should return false when file's content has a blank title 2"() {
        given:
            String content =
                    """UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                        Title: 
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
        when:
            def isFileValid = validator.isValid(validFile)
        then:
            !isFileValid
    }

    def "should return false when file's content has a blank title 3"() {
        given:
            String content =
                    """UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                        Title:          
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
        when:
            def isFileValid = validator.isValid(validFile)
        then:
            !isFileValid
    }

    def "should return false when file's content has a blank title 4"() {
        given:
            String content =
                    """UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                        Title:\n       
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
        when:
            def isFileValid = validator.isValid(validFile)
        then:
            !isFileValid
    }

    def "should return false when file's content has a blank title 5"() {
        given:
            String content =
                    """UUID: 6d0379ca-0019-420e-b785-e16b094f3098
                        Title:\n\n\n      
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
        when:
            def isFileValid = validator.isValid(validFile)
        then:
            !isFileValid
    }

}
