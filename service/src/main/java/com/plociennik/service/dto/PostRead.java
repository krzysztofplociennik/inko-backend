package com.plociennik.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class PostRead implements Serializable {
    private Long id;
    private String title;
    private String content;
}
