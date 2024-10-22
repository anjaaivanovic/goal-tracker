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

    private String title;

    private String description;

    private Status status;

    private LocalDate dateCreated;

    private LocalDate deadline;

    @OneToOne(cascade = CascadeType.ALL)
    private User assignee;

    @OneToMany(mappedBy = "parentGoal")
    private List<Task> tasks;
}