package com.plociennik.service.importing;

import com.plociennik.common.errorhandling.exceptions.InkoRuntimeException;
import com.plociennik.model.ArticleEntity;
import com.plociennik.model.repository.article.ArticleRepository;
import com.plociennik.service.importing.helper.FileToArticleMapper;
import com.plociennik.service.validation.ValidationManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ImportService {

    private final ArticleRepository articleRepository;
    private final ValidationManager validationManager;
    private final FileToArticleMapper fileToArticleMapper;

    public void importFiles(MultipartFile file) {
        throwIfRequestIsNull(file);
        validationManager.validateFile(file);
        ArticleEntity mapped = fileToArticleMapper.map(file);
        articleRepository.save(mapped);
    }

    private void throwIfRequestIsNull(MultipartFile file) {
        if (file == null) {
            throw new InkoRuntimeException("MultipartFile is null!", "202502041021");
        }
    }
}
