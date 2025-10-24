package org.example.project.service;

import lombok.RequiredArgsConstructor;
import org.example.project.mapper.UserMapper;
import org.example.project.model.User;
import org.example.project.model.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserDto> login(UserDto userDto) {
        return Optional.empty();
    }

    public User register(String name, String email, String rawPassword) {
        if (userMapper.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(rawPassword));
        userMapper.insert(u);
        return u;
    }
}
