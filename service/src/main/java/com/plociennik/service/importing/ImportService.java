package com.plociennik.service.importing;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.service.importing.dto.ImportFilesRequestBody;
import com.plociennik.service.importing.helper.FileToArticleMapper;
import com.plociennik.service.validation.ValidationManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class ImportService {

    private final ArticleRepository articleRepository;
    private final ValidationManager validationManager;
    private final FileToArticleMapper fileToArticleMapper;

    public void importFiles(ImportFilesRequestBody requestBody) {
        throwIfRequestIsNull(requestBody);
        validationManager.validateImporting(requestBody);
        List<MultipartFile> files = requestBody.getFiles();

        List<ArticleEntity> mappedArticles = files.stream()
                .map(this.fileToArticleMapper::map)
                .toList();

        articleRepository.saveAll(mappedArticles);
    }

    private void throwIfRequestIsNull(ImportFilesRequestBody requestBody) {
        if (requestBody == null) {
            throw new InkoRuntimeException("ImportFilesRequestBody is null!", "202502041021");
        }
    }
}
