package com.plociennik.service.other;

import com.plociennik.model.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class KeepAliveService {

    private final UserRepository userRepository;

    public void findFirstArticle() {
        this.userRepository.findById(UUID.randomUUID());
    }
}
