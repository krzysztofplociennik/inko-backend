package com.plociennik.service;

import com.plociennik.model.Post;
import com.plociennik.model.repository.PostRepository;
import com.plociennik.service.dto.PostCreate;
import com.plociennik.service.dto.PostRead;
import com.plociennik.service.mapper.PostMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private PostMapper postMapper;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
        this.postMapper = new PostMapper();
    }

    public List<PostRead> getPosts(int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        List<PostRead> collect = postRepository.findAll(pageRequest)
                .stream()
                .map(post -> postMapper.mapToRead(post))
                .collect(Collectors.toList());
        return collect;
    }

    public Long save(PostCreate postCreateDto) {
        Post newPost = new Post(null, postCreateDto.getTitle(), postCreateDto.getContent());
        Post save = postRepository.save(newPost);
        return save.getId();
    }
}