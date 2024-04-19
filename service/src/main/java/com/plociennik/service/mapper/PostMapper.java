package com.plociennik.service.mapper;

import com.plociennik.model.Post;
import com.plociennik.service.dto.PostRead;

public class PostMapper {

    public PostRead mapToRead(Post post) {
        return new PostRead(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );
    }
}
