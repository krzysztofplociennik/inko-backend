package com.plociennik.web.api;

import com.plociennik.service.PostService;
import com.plociennik.service.dto.PostCreate;
import com.plociennik.service.dto.PostRead;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
@CrossOrigin
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value = "/getPosts" )
    public ResponseEntity<List<PostRead>> getPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<PostRead> posts = postService.getPosts(page, size);
        return ResponseEntity.ok(posts);
    }

    @PostMapping(value = "/addPost")
    public ResponseEntity<Long> addPost(@RequestBody PostCreate postCreateDto) {
        Long id = postService.save(postCreateDto);
        return ResponseEntity.ok(id);
    }
}
