package com.example.goal_tracker.auth.controller;

import com.example.goal_tracker.auth.dto.UserResponse;
import com.example.goal_tracker.auth.mapper.UserMapper;
import com.example.goal_tracker.auth.model.User;
import com.example.goal_tracker.auth.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserResponse>> findUsers(@PageableDefault Pageable pageable) {

        Page<User> userPage = adminService.findUsers(pageable);
        List<UserResponse> userResponses = userMapper.toUserResponses(userPage.getContent());

        return ResponseEntity.ok(new PageImpl<>(userResponses, userPage.getPageable(), userPage.getTotalElements()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUser(@PathVariable Long userId){
        return ResponseEntity.ok(userMapper.toUserResponse(adminService.findUser(userId)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long userId
    ) {

        adminService.deleteUser(userId);

        return ResponseEntity.ok("User successfully deleted.");
    }
}