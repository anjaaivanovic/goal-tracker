package com.example.goal_tracker.auth.service;

import com.example.goal_tracker.auth.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    Page<User> findUsers(Pageable pageable);
    User findUser(Long userId);
    void deleteUser(Long userId);
}