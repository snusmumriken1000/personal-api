package com.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import com.example.entity.Habit;
import com.example.entity.HabitRecord;
import com.example.repository.HabitRepository;
import com.example.repository.HabitRecordRepository;
import com.example.model.HabitStats;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class HabitService {
    @Inject
    HabitRepository habitRepository;
    
    @Inject
    HabitRecordRepository habitRecordRepository;

    @Transactional
    public Habit createHabit(Habit habit) {
        habitRepository.persist(habit);
        return habit;
    }

    @Transactional
    public Habit updateHabit(Long id, Habit habitDetails) {
        Habit habit = habitRepository.findById(id);
        if (habit == null) {
            throw new RuntimeException("Habit not found");
        }
        
        habit.setTitle(habitDetails.getTitle());
        habit.setDescription(habitDetails.getDescription());
        habitRepository.persist(habit);
        return habit;
    }

    @Transactional
    public HabitRecord updateHabitStatus(Long habitId, LocalDate date, boolean completed) {
        Habit habit = habitRepository.findById(habitId);
        if (habit == null) {
            throw new RuntimeException("Habit not found");
        }

        List<HabitRecord> records = habitRecordRepository.findByHabitAndRecordDate(habit, date);
        HabitRecord record = records.isEmpty() ? new HabitRecord() : records.get(0);

        record.setHabit(habit);
        record.setRecordDate(date);
        record.setCompleted(completed);
        
        habitRecordRepository.persist(record);
        return record;
    }

    public HabitStats getHabitStats(Long habitId) {
        Habit habit = habitRepository.findById(habitId);
        if (habit == null) {
            throw new RuntimeException("Habit not found");
        }

        Double completionRate = habitRecordRepository.getCompletionRate(habit);
        Integer maxStreak = habitRecordRepository.getMaxStreakDays(habitId);

        return new HabitStats(completionRate, maxStreak);
    }
} 