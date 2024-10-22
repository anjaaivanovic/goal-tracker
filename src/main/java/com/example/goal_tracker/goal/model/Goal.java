package com.example.goal_tracker.goal.model;

import com.example.goal_tracker.auth.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDate dateCreated;

    @Column(nullable = false)
    private LocalDate deadline;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User assignee;

    @OneToMany(mappedBy = "parentGoal", cascade = CascadeType.REMOVE)
    private List<Task> tasks;
}