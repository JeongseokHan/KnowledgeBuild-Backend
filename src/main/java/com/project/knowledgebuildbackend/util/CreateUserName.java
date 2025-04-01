package com.project.knowledgebuildbackend.util;

import com.project.knowledgebuildbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateUserName {
    private final UserRepository userRepository;
    public String createName() {
        String name;
        do {
            name = "User_" + RandomStringUtils.random(5, 48, 122, true, true);
        } while (userRepository.findByNickname(name).isPresent());

        return name;
    }
}
