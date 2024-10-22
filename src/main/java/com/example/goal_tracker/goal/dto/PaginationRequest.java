package com.example.goal_tracker.goal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {

    private int page = 0;

    private int size = 10;

    private String sortBy = "id";

    private String direction = "asc";

    private String search;

    private Long userId;
}