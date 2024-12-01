package com.plociennik.web.api;

import com.plociennik.service.article.ArticleCreateService;
import com.plociennik.service.article.ArticleDeleteService;
import com.plociennik.service.article.ArticleFetchService;
import com.plociennik.service.article.ArticleUpdateService;
import com.plociennik.service.article.dto.AllArticlesItem;
import com.plociennik.service.article.dto.ArticleCreate;
import com.plociennik.service.article.dto.ArticleDetails;
import com.plociennik.service.article.dto.ArticleUpdate;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/article")
@AllArgsConstructor
public class ArticleController {

    private final ArticleFetchService articleFetchService;
    private final ArticleCreateService articleCreateService;
    private final ArticleUpdateService articleUpdateService;
    private final ArticleDeleteService articleDeleteService;

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<AllArticlesItem>> getAll() {
        List<AllArticlesItem> all = articleFetchService.getAll();
        return ResponseEntity.ok(all);
    }

    @Operation(
            summary = "Fetch article details by ID",
            description = "Fetches article's content with it's metadata by ID. It will throw an exception if there " +
                    "is no article in the database with that ID."
    )
    @GetMapping
    public ResponseEntity<ArticleDetails> getDetails(@RequestParam String id) throws Exception {
        ArticleDetails articleDetails = articleFetchService.getArticleDetails(id);
        return ResponseEntity.ok(articleDetails);
    }

    @GetMapping(value = "/getTypes")
    public ResponseEntity<List<String>> getTypes() {
        List<String> allTypes = articleFetchService.getAllTypes();
        return ResponseEntity.ok(allTypes);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> create(@RequestBody ArticleCreate articleCreate) {
        String id = articleCreateService.create(articleCreate);
        return ResponseEntity.ok(id);
    }

    @PutMapping
    public ResponseEntity<ArticleDetails> update(@RequestBody ArticleUpdate articleUpdate) {
        ArticleDetails response = articleUpdateService.update(articleUpdate);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam String id) {
        articleDeleteService.delete(id);
        return ResponseEntity.ok("The article has been deleted.");
    }
}
