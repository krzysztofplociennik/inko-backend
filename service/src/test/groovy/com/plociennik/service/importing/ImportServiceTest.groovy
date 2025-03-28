package com.plociennik.service.importing

import com.plociennik.model.repository.article.ArticleRepository
import spock.lang.Specification

class ImportServiceTest extends Specification {

    def repositoryStub = Stub(ArticleRepository)
    def importService = new ImportService(repositoryStub)

    def "import a file"() {
        given: "a dummy file"
        when:
            def mappedFile = importService.mapFileIntoEntity()
        then:
            "test" == mappedFile.title
    }
}
