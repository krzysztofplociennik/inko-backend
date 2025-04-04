package com.plociennik.service.importing.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ImportFilesRequestBody {
    private List<MultipartFile> files;
}
