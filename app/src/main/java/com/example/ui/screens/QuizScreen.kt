package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.QuizData
import com.example.ui.ChemLabViewModel

@Composable
fun QuizScreen(
    viewModel: ChemLabViewModel,
    modifier: Modifier = Modifier
) {
    val currentIndex by viewModel.currentQuizIndex.collectAsState()
    val score by viewModel.quizScore.collectAsState()
    val selectedOption by viewModel.selectedQuizOption.collectAsState()
    val isSubmitted by viewModel.isAnswerSubmitted.collectAsState()
    val isCompleted by viewModel.isQuizCompleted.collectAsState()

    val totalQuestions = QuizData.questions.size
    val currentQuestion = QuizData.questions.getOrNull(currentIndex) ?: QuizData.questions.first()
    val progress = (currentIndex + 1).toFloat() / totalQuestions.toFloat()

    if (isCompleted) {
        // QUIZ COMPLETION SUMMARY
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp)
                .testTag("quiz_completed_screen"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFF59E0B).copy(alpha = 0.2f),
                modifier = Modifier.size(90.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = "Trophy",
                        tint = Color(0xFFF59E0B),
                        modifier = Modifier.size(52.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Quiz Completed! 🎉",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "You scored $score out of $totalQuestions correctly!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Score and XP details card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val accuracy = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()
                    Text("Accuracy: $accuracy%", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("+${score * 15} XP Earned to Student Profile", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.restartQuiz() },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("restart_quiz_btn")
            ) {
                Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Try Again", fontWeight = FontWeight.Bold)
            }
        }
        return
    }

    // ACTIVE QUIZ INTERFACE
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("quiz_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // HEADER WITH PROGRESS & SCORE
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Question ${currentIndex + 1} of $totalQuestions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = currentQuestion.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            ) {
                Text(
                    text = "Score: $score",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        // QUESTION CARD
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = currentQuestion.type.name.replace("_", " "),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // OPTIONS LIST
        Text("Choose the correct answer:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)

        currentQuestion.options.forEachIndexed { index, optionText ->
            val isSelected = selectedOption == index
            val isCorrect = index == currentQuestion.correctIndex

            Card(
                onClick = { viewModel.selectQuizOption(index) },
                enabled = !isSubmitted,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quiz_option_$index"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        isSubmitted && isCorrect -> Color(0xFF10B981).copy(alpha = 0.25f)
                        isSubmitted && isSelected && !isCorrect -> Color(0xFFEF4444).copy(alpha = 0.25f)
                        isSelected -> MaterialTheme.colorScheme.primaryContainer
                        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    }
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = optionText,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )

                    if (isSubmitted) {
                        if (isCorrect) {
                            Icon(Icons.Default.Check, contentDescription = "Correct", tint = Color(0xFF10B981))
                        } else if (isSelected) {
                            Icon(Icons.Default.Close, contentDescription = "Incorrect", tint = Color(0xFFEF4444))
                        }
                    }
                }
            }
        }

        // EXPLANATION CARD (AFTER SUBMISSION)
        if (isSubmitted) {
            val wasCorrect = selectedOption == currentQuestion.correctIndex
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (wasCorrect) Color(0xFFECFDF5) else Color(0xFFFEF2F2)
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (wasCorrect) "✅ Correct!" else "❌ Incorrect",
                        fontWeight = FontWeight.Bold,
                        color = if (wasCorrect) Color(0xFF047857) else Color(0xFFB91C1C),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentQuestion.explanation,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (wasCorrect) Color(0xFF065F46) else Color(0xFF991B1B)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // SUBMIT OR NEXT BUTTON
        if (!isSubmitted) {
            Button(
                onClick = { viewModel.submitQuizAnswer() },
                enabled = selectedOption != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("submit_quiz_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Submit Answer", fontWeight = FontWeight.Bold)
            }
        } else {
            Button(
                onClick = { viewModel.nextQuizQuestion() },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("next_quiz_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (currentIndex < totalQuestions - 1) "Next Question" else "See Results", fontWeight = FontWeight.Bold)
            }
        }
    }
}
