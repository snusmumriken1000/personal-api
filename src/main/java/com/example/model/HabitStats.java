package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HabitStats {
    private Double completionRate;
    private Integer maxStreak;

    public HabitStats(Double completionRate, Integer maxStreak) {
        this.completionRate = completionRate;
        this.maxStreak = maxStreak;
    }
} 