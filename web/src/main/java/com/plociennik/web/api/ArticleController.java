package com.plociennik.web.api;

import com.plociennik.service.ArticleService;
import com.plociennik.service.dto.ArticleCreate;
import com.plociennik.service.dto.ArticleRead;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/articles")
@CrossOrigin
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping(value = "/getAll" )
    public ResponseEntity<List<ArticleRead>> getPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<ArticleRead> posts = articleService.getAll(page, size);
        return ResponseEntity.ok(posts);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> addPost(@RequestBody ArticleCreate articleCreate) {
        String id = articleService.save(articleCreate);
        return ResponseEntity.ok(id);
    }
}
