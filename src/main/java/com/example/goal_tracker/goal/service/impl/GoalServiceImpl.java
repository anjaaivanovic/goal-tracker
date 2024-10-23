package com.example.goal_tracker.goal.service.impl;

import com.example.goal_tracker.auth.exception.UserNotFoundException;
import com.example.goal_tracker.auth.model.User;
import com.example.goal_tracker.auth.repository.UserRepository;
import com.example.goal_tracker.goal.dto.GoalRequest;
import com.example.goal_tracker.goal.dto.PaginationRequest;
import com.example.goal_tracker.goal.exception.GoalNotFoundException;
import com.example.goal_tracker.goal.exception.NoAccessToResourceException;
import com.example.goal_tracker.goal.model.Goal;
import com.example.goal_tracker.goal.model.Status;
import com.example.goal_tracker.goal.repository.GoalRepository;
import com.example.goal_tracker.goal.service.GoalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;

    private final UserRepository userRepository;

    @Value("${deadlines.nearing}")
    private String nearingDeadlineString;

    @Value("${deadlines.behind}")
    private String behindDeadlineString;

    @Override
    public Page<Goal> findGoals(PaginationRequest paginationRequest) {

        Sort sort = paginationRequest.getDirection().equalsIgnoreCase("asc")
                ? Sort.by(paginationRequest.getSortBy()).ascending()
                : Sort.by(paginationRequest.getSortBy()).descending();

        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), sort);

        if (paginationRequest.getSearch() != null && !paginationRequest.getSearch().isEmpty()) {
            return goalRepository.findByAssigneeIdAndTitleContaining(paginationRequest.getUserId(), paginationRequest.getSearch(), pageable);
        } else {
            return goalRepository.findByAssigneeId(paginationRequest.getUserId(), pageable);
        }
    }

    @Override
    public Goal findGoal(Long goalId, Long userId) {

        Goal goal = goalRepository.findByIdWithTasks(goalId).orElseThrow(GoalNotFoundException::new);

        if (!Objects.equals(goal.getCreatedBy().getId(), userId) && !Objects.equals(goal.getAssignee().getId(), userId)) {
            throw new NoAccessToResourceException();
        }

        return goal;
    }

    @Override
    public Goal addGoal(Goal goal, GoalRequest goalRequest) {

        User assignee = userRepository.findById(goalRequest.getAssigneeId()).orElseThrow(UserNotFoundException::new);
        User createdBy = userRepository.findById(goalRequest.getCreatedById()).orElseThrow(UserNotFoundException::new);

        goal.setAssignee(assignee);
        goal.setCreatedBy(createdBy);
        goal.setDateCreated(LocalDate.now());
        goal.setStatus(Status.TO_DO);

        return goalRepository.save(goal);
    }

    @Override
    public void deleteGoal(Long goalId, Long userId) {

        Goal goal = goalRepository.findById(goalId).orElseThrow(GoalNotFoundException::new);

        if (!Objects.equals(goal.getCreatedBy().getId(), userId)){
            throw new NoAccessToResourceException();
        }

        goalRepository.deleteById(goalId);
    }

    @Scheduled(cron = "${cron.daily}")
    void checkDeadlines() {
        notifyUsersAboutDeadlines(LocalDate.now(), LocalDate.now().plusDays(3), nearingDeadlineString);
        notifyUsersAboutDeadlines(LocalDate.now(), LocalDate.now(), behindDeadlineString);
    }

    private void notifyUsersAboutDeadlines(LocalDate startDate, LocalDate endDate, String template) {
        List<Goal> goals = template.equals(nearingDeadlineString) ?
                goalRepository.findGoalsBetweenDates(startDate, endDate)
                : goalRepository.findGoalsBeforeDate(endDate);

        Map<String, List<Goal>> groupedGoals = goals.stream().collect(Collectors.groupingBy(goal -> goal.getAssignee().getEmail()));

        groupedGoals.forEach((email, userGoals) -> {
            String notification = createNotificationBody(userGoals, template);
        });
    }

    private String createNotificationBody(List<Goal> goals, String template) {
        StringBuilder notificationBuilder = new StringBuilder(template);
        goals.forEach(goal -> notificationBuilder
                .append("<li>#").append(goal.getId().toString())
                .append(" - ")
                .append(goal.getTitle())
                .append(" - ")
                .append(goal.getDeadline().toString())
                .append("</li>"));

        return notificationBuilder.append("</ul>").toString();
    }
}