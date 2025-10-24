package org.example.project.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.example.project.model.User;

@Mapper
public interface UserMapper {
    void insert(User user);

    User findByEmail(String email);
}
