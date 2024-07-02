package com.plociennik.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "ARTICLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {

    @Id
    @UuidGenerator
    private String id;
    private String title;
    private String content;
    private ArticleType type;
    private Set<String> tags;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
}
