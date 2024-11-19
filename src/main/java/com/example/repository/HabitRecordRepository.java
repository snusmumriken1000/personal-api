package com.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import com.example.entity.Habit;
import com.example.entity.HabitRecord;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class HabitRecordRepository implements PanacheRepository<HabitRecord> {
    @PersistenceContext
    EntityManager em;

    public List<HabitRecord> findByHabitAndRecordDate(Habit habit, LocalDate date) {
        return list("habit = ?1 and recordDate = ?2", habit, date);
    }
    
    public Double getCompletionRate(Habit habit) {
        Long totalCount = count("habit", habit);
        if (totalCount == 0) return 0.0;
        
        Long completedCount = count("habit = ?1 and completed = true", habit);
        return (completedCount.doubleValue() / totalCount.doubleValue()) * 100;
    }
    
    public Integer getMaxStreakDays(Long habitId) {
        // ネイティブクエリを使用して最長連続日数を計算
        return em.createNativeQuery(
            "WITH RECURSIVE dates AS (" +
            "SELECT record_date, completed, " +
            "ROW_NUMBER() OVER (ORDER BY record_date) - " +
            "ROW_NUMBER() OVER (PARTITION BY completed ORDER BY record_date) as grp " +
            "FROM habit_records WHERE habit_id = ?1 AND completed = true) " +
            "SELECT MAX(COUNT(*)) FROM dates GROUP BY grp")
            .setParameter(1, habitId)
            .getSingleResult() != null ? 
            ((Number) em.createNativeQuery(
                "WITH RECURSIVE dates AS (" +
                "SELECT record_date, completed, " +
                "ROW_NUMBER() OVER (ORDER BY record_date) - " +
                "ROW_NUMBER() OVER (PARTITION BY completed ORDER BY record_date) as grp " +
                "FROM habit_records WHERE habit_id = ?1 AND completed = true) " +
                "SELECT MAX(COUNT(*)) FROM dates GROUP BY grp")
                .setParameter(1, habitId)
                .getSingleResult()).intValue() : 0;
    }
} 