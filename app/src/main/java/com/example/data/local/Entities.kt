package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val id: Int = 1,
    val xp: Int = 120,
    val level: Int = 1,
    val streakDays: Int = 3,
    val completedExperimentsCount: Int = 1,
    val totalQuizzesTaken: Int = 2,
    val lastActiveTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "completed_experiments")
data class CompletedExperimentEntity(
    @PrimaryKey val experimentId: String,
    val experimentTitle: String,
    val completedAt: Long = System.currentTimeMillis(),
    val scoreEarned: Int = 50
)

@Entity(tableName = "unlocked_badges")
data class BadgeEntity(
    @PrimaryKey val badgeId: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val unlockedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "lab_reports")
data class LabReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val experimentTitle: String,
    val timestamp: Long = System.currentTimeMillis(),
    val chemicalsUsed: String,
    val observations: String,
    val calculatedResult: String = ""
)
