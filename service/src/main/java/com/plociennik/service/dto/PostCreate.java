package com.plociennik.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class PostCreate implements Serializable {
    private String title;
    private String content;
}
