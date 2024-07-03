package com.plociennik.web.api;

import com.plociennik.service.ArticleService;
import com.plociennik.service.dto.ArticleCreate;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/articles")
@CrossOrigin
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> create(@RequestBody ArticleCreate articleCreate) {
        String id = articleService.save(articleCreate);
        return ResponseEntity.ok(id);
    }
}
