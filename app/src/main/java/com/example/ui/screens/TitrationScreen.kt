package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.ChemLabViewModel
import com.example.ui.components.BuretteGlassware
import com.example.ui.components.FlaskGlassware

@Composable
fun TitrationScreen(
    viewModel: ChemLabViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.titrationState.collectAsState()

    val indicators = listOf("Phenolphthalein", "Methyl Orange", "Bromothymol Blue")

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("titration_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // TITLE & RESET
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Acid-Base Titration Lab 📊",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Standard HCl in burette vs Unknown NaOH in flask",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            OutlinedButton(
                onClick = { viewModel.resetTitration() },
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Reset")
            }
        }

        // INDICATOR PICKER
        Text("Select Indicator:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            indicators.forEach { ind ->
                FilterChip(
                    selected = state.indicator == ind,
                    onClick = { viewModel.selectTitrationIndicator(ind) },
                    label = { Text(ind) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }

        // TITRATION APPARATUS WORKBENCH
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Burette & Flask visuals
                Row(
                    modifier = Modifier.weight(1.1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    BuretteGlassware(
                        modifier = Modifier
                            .size(90.dp, 210.dp)
                            .padding(bottom = 12.dp),
                        liquidVolumeMl = state.buretteAddedMl,
                        isDripping = state.isDripping
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FlaskGlassware(
                        modifier = Modifier
                            .size(110.dp, 160.dp)
                            .padding(bottom = 12.dp),
                        fillLevel = ((state.analyteVolumeMl + state.buretteAddedMl) / 80f).coerceIn(0.25f, 0.85f),
                        liquidColor = Color(state.flaskColor),
                        isBubbling = false
                    )
                }

                // Digital readout metrics
                Column(
                    modifier = Modifier
                        .weight(0.9f)
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Burette Reading
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Burette Reading", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            Text(
                                text = "${"%.2f".format(state.buretteAddedMl)} mL",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Current pH
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("Solution pH", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                            Text(
                                text = "pH ${"%.2f".format(state.currentPh)}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = when {
                                    state.currentPh < 6.5f -> Color(0xFFEF4444)
                                    state.currentPh > 7.5f -> Color(0xFF8B5CF6)
                                    else -> Color(0xFF10B981)
                                }
                            )
                        }
                    }

                    // Endpoint status
                    if (state.isEndpointReached) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF10B981).copy(alpha = 0.2f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Endpoint Reached!", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
                            }
                        }
                    }
                }
            }
        }

        // BURETTE DISPENSING CONTROLS
        Text("Burette Stopcock Dispenser:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.addTitrant(0.1f) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("titrate_drop_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("+0.1 mL\n(Drop)", textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontSize = 12.sp)
            }

            Button(
                onClick = { viewModel.addTitrant(1.0f) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("titrate_1ml_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("+1.0 mL\n(Flow)", textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontSize = 12.sp)
            }

            Button(
                onClick = { viewModel.addTitrant(5.0f) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("titrate_5ml_btn"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("+5.0 mL\n(Rapid)", textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontSize = 12.sp)
            }
        }

        // TITRATION CURVE GRAPH CANVAS (Volume vs pH)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TITRATION CURVE (pH vs Volume Added)",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF38BDF8),
                        fontWeight = FontWeight.Bold
                    )
                    Text("Equivalence ~ 20.0 mL", color = Color(0xFF94A3B8), fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Canvas Graph
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    val w = size.width
                    val h = size.height

                    // Grid lines for pH 0, 7, 14
                    val y14 = 0f
                    val y7 = h * 0.5f
                    val y0 = h

                    drawLine(Color(0xFF334155), Offset(0f, y7), Offset(w, y7), strokeWidth = 1f)
                    drawLine(Color(0xFF334155), Offset(w * (20f / 40f), 0f), Offset(w * (20f / 40f), h), strokeWidth = 1f)

                    // Plot points
                    if (state.phCurvePoints.size > 1) {
                        val path = Path()
                        state.phCurvePoints.forEachIndexed { i, pt ->
                            val x = (pt.first / 40f).coerceIn(0f, 1f) * w
                            val y = ((14f - pt.second) / 14f).coerceIn(0f, 1f) * h
                            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                        }

                        drawPath(
                            path = path,
                            color = Color(0xFF38BDF8),
                            style = Stroke(width = 3f)
                        )
                    }
                }
            }
        }

        // QUANTITATIVE STOICHIOMETRY FORMULA M1V1 = M2V2
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Volumetric Neutralization Calculation",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Formula:  M₁ × V₁ = M₂ × V₂",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("• M₁ = Acid concentration = 0.10 M HCl", style = MaterialTheme.typography.bodySmall)
                Text("• V₁ = Acid volume dispensed = ${"%.2f".format(state.buretteAddedMl)} mL", style = MaterialTheme.typography.bodySmall)
                Text("• V₂ = Base volume in flask = ${state.analyteVolumeMl} mL", style = MaterialTheme.typography.bodySmall)
                Text("• M₂ = Unknown NaOH concentration", style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(10.dp))

                if (state.calculatedMolarity != null) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "M₂(NaOH) = (0.10 M × ${"%.2f".format(state.buretteAddedMl)} mL) / ${state.analyteVolumeMl} mL",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Result: Unknown Base Molarity = ${"%.4f".format(state.calculatedMolarity)} M",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Dispense acid to reach the equivalence point (sudden color discharge) to reveal the calculated concentration.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
