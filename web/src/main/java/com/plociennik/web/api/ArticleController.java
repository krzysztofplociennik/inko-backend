package com.plociennik.web.api;

import com.plociennik.service.ArticleService;
import com.plociennik.service.dto.ArticleCreate;
import com.plociennik.service.dto.ArticleDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/article")
@CrossOrigin
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping(value = "/add")
    public ResponseEntity<String> create(@RequestBody ArticleCreate articleCreate) {
        String id = articleService.save(articleCreate);
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public ResponseEntity<ArticleDetails> getDetails(@RequestParam String id) throws Exception {
        ArticleDetails articleDetails = articleService.getArticleDetails(id);
        return ResponseEntity.ok(articleDetails);
    }
}
