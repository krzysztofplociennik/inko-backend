package com.plociennik.service.export

import spock.lang.Specification

class ExportServiceTest extends Specification {

    def "SanitizeContent"() {
        // Arrange
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

        // Act
        def actual = exportService.sanitizeContent(value)

        // Assert
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
        expect:
            actual.equals(expected)
    }
}