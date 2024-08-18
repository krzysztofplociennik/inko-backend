package com.plociennik.web.api;

import com.plociennik.service.ArticleService;
import com.plociennik.service.dto.AllArticlesItem;
import com.plociennik.service.dto.ArticleCreate;
import com.plociennik.service.dto.ArticleDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<AllArticlesItem>> getAll() {
        List<AllArticlesItem> all = articleService.getAll();
        return ResponseEntity.ok(all);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam String id) {
        articleService.delete(id);
        return ResponseEntity.ok("Article got deleted.");
    }
}
