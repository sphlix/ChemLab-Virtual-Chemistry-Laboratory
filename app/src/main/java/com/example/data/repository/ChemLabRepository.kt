package com.example.data.repository

import com.example.data.local.BadgeEntity
import com.example.data.local.CompletedExperimentEntity
import com.example.data.local.LabReportEntity
import com.example.data.local.UserDao
import com.example.data.local.UserProgressEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class ChemLabRepository(private val userDao: UserDao) {

    val userProgress: Flow<UserProgressEntity?> = userDao.getUserProgress()
    val completedExperiments: Flow<List<CompletedExperimentEntity>> = userDao.getCompletedExperiments()
    val unlockedBadges: Flow<List<BadgeEntity>> = userDao.getUnlockedBadges()
    val labReports: Flow<List<LabReportEntity>> = userDao.getLabReports()

    suspend fun initializeProgressIfEmpty() {
        val existing = userDao.getUserProgress().firstOrNull()
        if (existing == null) {
            userDao.insertOrUpdateProgress(
                UserProgressEntity(
                    id = 1,
                    xp = 150,
                    level = 1,
                    streakDays = 3,
                    completedExperimentsCount = 1,
                    totalQuizzesTaken = 1
                )
            )
            // Initial starter badge
            userDao.unlockBadge(
                BadgeEntity(
                    badgeId = "badge_welcome",
                    title = "Apprentice Chemist",
                    description = "Started your virtual chemistry laboratory adventure.",
                    iconEmoji = "🧪"
                )
            )
        }
    }

    suspend fun addXp(amount: Int) {
        val current = userDao.getUserProgress().firstOrNull() ?: UserProgressEntity()
        val newXp = current.xp + amount
        val newLevel = (newXp / 150) + 1
        userDao.insertOrUpdateProgress(
            current.copy(
                xp = newXp,
                level = newLevel,
                lastActiveTimestamp = System.currentTimeMillis()
            )
        )

        if (newLevel >= 2) {
            userDao.unlockBadge(
                BadgeEntity(
                    badgeId = "badge_lvl2",
                    title = "Lab Assistant",
                    description = "Reached Level 2 in ChemLab.",
                    iconEmoji = "🔬"
                )
            )
        }
        if (newLevel >= 5) {
            userDao.unlockBadge(
                BadgeEntity(
                    badgeId = "badge_lvl5",
                    title = "Chemistry Master",
                    description = "Mastered fundamental chemical techniques and theory.",
                    iconEmoji = "🏆"
                )
            )
        }
    }

    suspend fun completeExperiment(experimentId: String, title: String) {
        userDao.markExperimentCompleted(
            CompletedExperimentEntity(
                experimentId = experimentId,
                experimentTitle = title,
                completedAt = System.currentTimeMillis(),
                scoreEarned = 75
            )
        )
        addXp(75)

        val current = userDao.getUserProgress().firstOrNull() ?: UserProgressEntity()
        userDao.insertOrUpdateProgress(
            current.copy(
                completedExperimentsCount = current.completedExperimentsCount + 1
            )
        )

        userDao.unlockBadge(
            BadgeEntity(
                badgeId = "badge_first_exp",
                title = "Practical Chemist",
                description = "Successfully executed a guided laboratory procedure.",
                iconEmoji = "⚗️"
            )
        )
    }

    suspend fun completeQuiz(score: Int, total: Int) {
        val earnedXp = score * 15
        addXp(earnedXp)

        val current = userDao.getUserProgress().firstOrNull() ?: UserProgressEntity()
        userDao.insertOrUpdateProgress(
            current.copy(
                totalQuizzesTaken = current.totalQuizzesTaken + 1
            )
        )

        if (score == total) {
            userDao.unlockBadge(
                BadgeEntity(
                    badgeId = "badge_perfect_quiz",
                    title = "Quiz Champion",
                    description = "Scored 100% on a chemistry quiz!",
                    iconEmoji = "🧠"
                )
            )
        }
    }

    suspend fun saveLabReport(title: String, chemicals: String, observations: String, result: String): Long {
        addXp(30)
        return userDao.insertLabReport(
            LabReportEntity(
                experimentTitle = title,
                chemicalsUsed = chemicals,
                observations = observations,
                calculatedResult = result
            )
        )
    }

    suspend fun deleteLabReport(id: Long) {
        userDao.deleteLabReport(id)
    }
}
