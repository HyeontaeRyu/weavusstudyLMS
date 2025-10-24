package org.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;

    @Builder.Default
    private Role role = Role.ROLE_STUDENT;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
