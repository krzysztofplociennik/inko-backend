package com.plociennik.service;

import com.plociennik.model.Post;
import com.plociennik.model.repository.PostRepository;
import com.plociennik.service.dto.PostCreate;
import com.plociennik.service.dto.PostRead;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostRead> getPosts() {
        List<PostRead> collect = postRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return collect;
    }

    private PostRead mapToDto(Post post) {
        return new PostRead(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );
    }

    public Long save(PostCreate postCreateDto) {
        Post newPost = new Post(null, postCreateDto.getTitle(), postCreateDto.getContent());
        Post save = postRepository.save(newPost);
        return save.getId();
    }
}