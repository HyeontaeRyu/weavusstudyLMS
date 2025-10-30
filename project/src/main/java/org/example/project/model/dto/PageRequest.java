package org.example.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
    private int page;
    private int size;

    public int getOffset() {
        return (page - 1) * size;
    }
}