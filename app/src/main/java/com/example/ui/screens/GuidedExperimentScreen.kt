package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.StepVisualType
import com.example.ui.ChemLabViewModel
import com.example.ui.components.BunsenBurnerCanvas
import com.example.ui.components.BuretteGlassware
import com.example.ui.components.FlaskGlassware

@Composable
fun GuidedExperimentScreen(
    viewModel: ChemLabViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val experiment by viewModel.currentGuidedExperiment.collectAsState()
    val currentStepIndex by viewModel.currentStepIndex.collectAsState()

    var showWhyExplanation by remember { mutableStateOf(false) }

    if (experiment == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No experiment selected.")
        }
        return
    }

    val exp = experiment!!
    val currentStep = exp.steps.getOrNull(currentStepIndex) ?: exp.steps.first()
    val isLastStep = currentStepIndex == exp.steps.size - 1
    val progress = (currentStepIndex + 1).toFloat() / exp.steps.size.toFloat()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("guided_experiment_screen")
    ) {
        // TOP APP BAR
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 3.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = exp.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Text(
                            text = "Step ${currentStepIndex + 1} of ${exp.steps.size}: ${currentStep.title}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }

        // SCROLLABLE CONTENT BODY
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // INTERACTIVE APPARATUS VISUAL DEMONSTRATION
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when (currentStep.visualType) {
                        StepVisualType.HEAT_BUNSEN -> {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                BunsenBurnerCanvas(
                                    modifier = Modifier
                                        .size(110.dp, 150.dp)
                                        .padding(bottom = 12.dp),
                                    isIgnited = true,
                                    flameType = "Roaring Blue"
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                FlaskGlassware(
                                    modifier = Modifier
                                        .size(110.dp, 160.dp)
                                        .padding(bottom = 12.dp),
                                    fillLevel = 0.55f,
                                    liquidColor = Color(0xFF0284C7),
                                    isBubbling = true,
                                    isHeating = true
                                )
                            }
                        }

                        StepVisualType.OBSERVE_FLAME -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                val flameName = when {
                                    currentStep.title.contains("Sodium", true) -> "Golden Yellow"
                                    currentStep.title.contains("Potassium", true) -> "Lilac"
                                    currentStep.title.contains("Copper", true) -> "Emerald Green"
                                    currentStep.title.contains("Calcium", true) -> "Brick Red"
                                    else -> "Roaring Blue"
                                }
                                BunsenBurnerCanvas(
                                    modifier = Modifier.size(150.dp, 180.dp),
                                    isIgnited = true,
                                    flameType = flameName
                                )
                                Text(
                                    text = "$flameName Flame Emission",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        StepVisualType.TITRATE -> {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BuretteGlassware(
                                    modifier = Modifier.size(110.dp, 190.dp),
                                    liquidVolumeMl = 20f + (currentStepIndex * 8f),
                                    isDripping = true
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                FlaskGlassware(
                                    modifier = Modifier.size(110.dp, 150.dp),
                                    fillLevel = 0.45f,
                                    liquidColor = if (currentStepIndex >= 3) Color(0x2238BDF8) else Color(0xFFEC4899),
                                    isBubbling = false
                                )
                            }
                        }

                        StepVisualType.ADD_INDICATOR -> {
                            FlaskGlassware(
                                modifier = Modifier.size(130.dp, 180.dp),
                                fillLevel = 0.5f,
                                liquidColor = Color(0xFFEC4899), // Pink
                                isBubbling = true
                            )
                        }

                        StepVisualType.FILTER_PRECIPITATE -> {
                            FlaskGlassware(
                                modifier = Modifier.size(130.dp, 180.dp),
                                fillLevel = 0.6f,
                                liquidColor = Color(0xFF38BDF8),
                                precipitateColor = Color(0xFFF59E0B),
                                isBubbling = false
                            )
                        }

                        else -> {
                            // Standard Pour/Mix/Apparatus
                            FlaskGlassware(
                                modifier = Modifier.size(130.dp, 180.dp),
                                fillLevel = 0.45f + (currentStepIndex * 0.1f),
                                liquidColor = Color(0xFF00E5FF),
                                isBubbling = true
                            )
                        }
                    }
                }
            }

            // STEP INSTRUCTION CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${currentStep.stepNumber}",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = currentStep.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = currentStep.instruction,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Reagents and Apparatus Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (currentStep.reagentsInUse.isNotBlank() && currentStep.reagentsInUse != "None") {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            ) {
                                Text(
                                    text = "🧪 ${currentStep.reagentsInUse}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        if (currentStep.apparatusInUse.isNotBlank()) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                            ) {
                                Text(
                                    text = "🔬 ${currentStep.apparatusInUse}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // CHEMICAL EQUATION (IF ANY)
            if (currentStep.equation.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF0F172A)
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "CHEMICAL EQUATION",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF38BDF8),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentStep.equation,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            // EXPECTED OBSERVATION
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.Visibility,
                        contentDescription = "Observation",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Expected Observation",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = currentStep.observation,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // COLLAPSIBLE "WHY?" EXPLANATION
            Card(
                onClick = { showWhyExplanation = !showWhyExplanation },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("guided_why_card"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.HelpOutline,
                                contentDescription = "Why",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Why does this happen? (Scientific Mechanism)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            text = if (showWhyExplanation) "▲ Hide" else "▼ Show",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    AnimatedVisibility(visible = showWhyExplanation) {
                        Column {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = currentStep.whyExplanation,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // BOTTOM NAVIGATION BUTTONS
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { viewModel.prevStep() },
                    enabled = currentStepIndex > 0,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Previous")
                }

                Button(
                    onClick = {
                        if (isLastStep) {
                            viewModel.nextStep()
                            onBack()
                        } else {
                            viewModel.nextStep()
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isLastStep) Color(0xFF10B981) else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.testTag("guided_next_btn")
                ) {
                    Text(if (isLastStep) "Complete (+75 XP)" else "Next Step")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        if (isLastStep) Icons.Default.CheckCircle else Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
