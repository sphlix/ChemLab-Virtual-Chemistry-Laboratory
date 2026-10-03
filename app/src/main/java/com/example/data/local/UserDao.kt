package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_progress WHERE id = 1 LIMIT 1")
    fun getUserProgress(): Flow<UserProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProgress(progress: UserProgressEntity)

    @Query("SELECT * FROM completed_experiments ORDER BY completedAt DESC")
    fun getCompletedExperiments(): Flow<List<CompletedExperimentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markExperimentCompleted(exp: CompletedExperimentEntity)

    @Query("SELECT * FROM unlocked_badges ORDER BY unlockedAt DESC")
    fun getUnlockedBadges(): Flow<List<BadgeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun unlockBadge(badge: BadgeEntity)

    @Query("SELECT * FROM lab_reports ORDER BY timestamp DESC")
    fun getLabReports(): Flow<List<LabReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabReport(report: LabReportEntity): Long

    @Query("DELETE FROM lab_reports WHERE id = :id")
    suspend fun deleteLabReport(id: Long)
}
