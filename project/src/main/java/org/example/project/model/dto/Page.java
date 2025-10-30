package org.example.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    private List<T> content;
    private int page;
    private int pageSize;
    private int totalCount;

    public int getTotalPages() {
        return (int) Math.ceil((double) totalCount / pageSize);
    }
}