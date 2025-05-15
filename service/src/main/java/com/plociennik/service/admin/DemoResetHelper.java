package com.plociennik.service.admin;

import com.plociennik.model.ArticleEntity;
import com.plociennik.model.ArticleType;
import com.plociennik.model.TagEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DemoResetHelper {

    public List<ArticleEntity> createDummyArticles() {

        TagEntity tag1 = TagEntity.builder()
                .id(UUID.randomUUID())
                .articles(new ArrayList<>())
                .value("arch")
                .build();

        TagEntity tag2 = TagEntity.builder()
                .id(UUID.randomUUID())
                .articles(new ArrayList<>())
                .value("linux")
                .build();

        TagEntity tag3 = TagEntity.builder()
                .id(UUID.randomUUID())
                .articles(new ArrayList<>())
                .value("partition")
                .build();

        ArticleEntity article1 = ArticleEntity.builder()
                .id(UUID.randomUUID())
                .tags(List.of(tag1, tag2, tag3))
                .title("Root partition almost full on Arch Linux")
                .type(ArticleType.PROGRAMMING)
                .content("Sometimes the root partition can run out of space. To be able to do a little bit of a cleanup you can run:\n" +
                        "\n" +
                        "sudo pacman -Scc\n" +
                        "Source:\n" +
                        "\n" +
                        "https://www.reddit.com/r/archlinux/comments/1dffm3u/root_partition_almost_full/")
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .build();

        TagEntity tag4 = TagEntity.builder()
                .id(UUID.randomUUID())
                .articles(new ArrayList<>())
                .value("kubernetes")
                .build();

        TagEntity tag5 = TagEntity.builder()
                .id(UUID.randomUUID())
                .articles(new ArrayList<>())
                .value("commands")
                .build();

        TagEntity tag6 = TagEntity.builder()
                .id(UUID.randomUUID())
                .articles(new ArrayList<>())
                .value("kube")
                .build();

        ArticleEntity article2 = ArticleEntity.builder()
                .id(UUID.randomUUID())
                .tags(List.of(tag4, tag5, tag6))
                .title("kubernetes random commands")
                .type(ArticleType.DATABASE)
                .content("""
                        "C:\\Aplikacje\\kubectl\\dev.yaml"
                        
                        kubectl --kubeconfig dev2.yml get pods
                        
                        kubectl --kubeconfig "C:\\Aplikacje\\kubectl\\dev.yaml" get pods
                        
                        kubectl --kubeconfig "C:\\Aplikacje\\kubectl\\k-dev.yaml" -ns ZZZ-dev get pods
                        
                        kubectl --kubeconfig "C:\\Aplikacje\\kubectl\\k-dev.yaml" get pods --namespace=ZZZ-dev
                        
                        --namespace=ZZZ-dev
                        
                        -ns ZZZ-dev get pods
                        
                        k9s --kubeconfig "C:\\Aplikacje\\kubectl\\dev.yaml"
                        
                        k9s --kubeconfig "C:\\Aplikacje\\kubectl\\k-dev.yaml"
                        
                        k9s --kubeconfig "C:\\Aplikacje\\kubectl\\k-dev.yaml" --namespace=ZZZ-dev
                        
                        =============================
                        
                        kubectl get pods | grep ewasd
                        
                        kubectl --kubeconfig dev2.yml get pods
                        
                        kubectl --kubeconfig dev.yaml get pods | findstr ewasd
                        
                        kubectl --kubeconfig "C:\\Aplikacje\\kubectl\\dev.yaml" get pods | findstr ewasd
                        
                        kubectl --kubeconfig "C:\\Aplikacje\\kubectl\\dev.yaml" get pods | findstr patents
                        """)
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .build();

        TagEntity tag7 = TagEntity.builder()
                .id(UUID.randomUUID())
                .articles(new ArrayList<>())
                .value("linux")
                .build();

        TagEntity tag8 = TagEntity.builder()
                .id(UUID.randomUUID())
                .articles(new ArrayList<>())
                .value("commands")
                .build();

        TagEntity tag9 = TagEntity.builder()
                .id(UUID.randomUUID())
                .articles(new ArrayList<>())
                .value("random")
                .build();

        ArticleEntity article3 = ArticleEntity.builder()
                .id(UUID.randomUUID())
                .tags(List.of(tag7, tag8, tag9))
                .title("linux commands")
                .type(ArticleType.DATABASE)
                .content("""
                        [stworzenie folderu]
                        
                                                          mkdir dir1
                                                          [install JRE]
                        
                                                          sudo apt install -y openjdk-18-jre
                                                          [dodanie dostępu w terminalu]
                        
                                                          sudo chmod a+x /home/x/Desktop/b/l/linux/podpis/jre/bin/java
                                                          - problem może się pojawić gdy w ścieżce jest znak specjalny np. '('                        """)
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .build();

        return List.of(article1, article2, article3);
    }
}
