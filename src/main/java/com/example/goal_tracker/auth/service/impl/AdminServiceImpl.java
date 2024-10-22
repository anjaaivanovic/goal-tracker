package com.example.goal_tracker.auth.service.impl;

import com.example.goal_tracker.auth.exception.UserEngagedException;
import com.example.goal_tracker.auth.exception.UserNotFoundException;
import com.example.goal_tracker.auth.model.User;
import com.example.goal_tracker.auth.repository.UserRepository;
import com.example.goal_tracker.auth.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public Page<User> findUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findUser(Long userId) {

        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (!user.getCreatedGoals().isEmpty() || !user.getAssignedGoals().isEmpty()) {
            throw new UserEngagedException();
        }

        userRepository.deleteById(userId);
    }
}