package com.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import com.example.entity.Habit;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class HabitRepository implements PanacheRepository<Habit> {
    // 基本的なCRUD操作はPanacheRepositoryで提供されます
} 