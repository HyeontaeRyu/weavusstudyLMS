package org.example.project.model.dto;

import lombok.*;
import org.example.project.model.Role;

import java.util.Date;

@Data
@ToString(exclude = "role")
@EqualsAndHashCode(exclude = "role")
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Date createdAt;
    private Date updatedAt;
    private String password;

}
