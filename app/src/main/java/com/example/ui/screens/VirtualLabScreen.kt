package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
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
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Chemical
import com.example.data.repository.ChemicalsData
import com.example.ui.ChemLabViewModel
import com.example.ui.components.BunsenBurnerCanvas
import com.example.ui.components.FlaskGlassware

@Composable
fun VirtualLabScreen(
    viewModel: ChemLabViewModel,
    modifier: Modifier = Modifier
) {
    val labState by viewModel.virtualLabState.collectAsState()

    var selectedReagent by remember { mutableStateOf(ChemicalsData.allChemicals.first()) }
    var observationInput by remember { mutableStateOf("") }
    var showSavedMessage by remember { mutableStateOf(false) }

    val glasswareOptions = listOf("Conical Flask", "Beaker", "Test Tube", "Measuring Cylinder")

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("virtual_lab_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // HEADER TITLE
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Interactive Virtual Lab 🔬",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Pour, mix, heat, and observe real-time reactions",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            OutlinedButton(
                onClick = { viewModel.resetVirtualLab() },
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Reset")
            }
        }

        // GLASSWARE SELECTOR TABS
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(glasswareOptions) { glass ->
                FilterChip(
                    selected = labState.selectedApparatus == glass,
                    onClick = { viewModel.selectApparatus(glass) },
                    label = { Text(glass) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }

        // VIRTUAL WORKBENCH CANVAS
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                // Background lab bench grid lines
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    if (labState.isBunsenHeating) {
                        BunsenBurnerCanvas(
                            modifier = Modifier
                                .size(95.dp, 140.dp)
                                .padding(bottom = 8.dp),
                            isIgnited = true,
                            flameType = "Roaring Blue"
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    FlaskGlassware(
                        modifier = Modifier
                            .size(140.dp, 200.dp)
                            .padding(bottom = 8.dp),
                        fillLevel = (labState.volumeMl / 100f).coerceIn(0.15f, 0.9f),
                        liquidColor = Color(labState.liquidColorArgb),
                        isBubbling = labState.isBubbling || labState.isBunsenHeating,
                        isHeating = labState.isBunsenHeating,
                        precipitateColor = labState.precipitateColorArgb?.let { Color(it) }
                    )
                }

                // Digital live instruments overlay
                Column(
                    modifier = Modifier.align(Alignment.TopEnd),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Digital Thermometer
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF0F172A).copy(alpha = 0.85f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Thermostat,
                                contentDescription = null,
                                tint = if (labState.temperatureCelsius > 45f) Color(0xFFEF4444) else Color(0xFF38BDF8),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${"%.1f".format(labState.temperatureCelsius)} °C",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Digital pH Meter
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF0F172A).copy(alpha = 0.85f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.WaterDrop,
                                contentDescription = null,
                                tint = when {
                                    labState.currentPh < 6.5f -> Color(0xFFEF4444)
                                    labState.currentPh > 7.5f -> Color(0xFF8B5CF6)
                                    else -> Color(0xFF10B981)
                                },
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "pH ${"%.1f".format(labState.currentPh)}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Mass scale
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF0F172A).copy(alpha = 0.85f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Speed, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${"%.1f".format(labState.currentMassGrams)} g",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // WORKBENCH ACTIONS: POUR CHEMICAL & HEAT
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { viewModel.addChemicalToLab(selectedReagent) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("pour_chemical_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Pour ${selectedReagent.formula}", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { viewModel.toggleBunsenHeating() },
                modifier = Modifier
                    .weight(1f)
                    .testTag("toggle_bunsen_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (labState.isBunsenHeating) Color(0xFFEF4444) else Color(0xFFF59E0B)
                )
            ) {
                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (labState.isBunsenHeating) "Turn Off Flame" else "Bunsen Heat", fontWeight = FontWeight.Bold)
            }
        }

        // CHEMICAL REAGENT SHELF
        Text(
            text = "Chemical Reagents Shelf",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ChemicalsData.allChemicals) { chem ->
                Card(
                    onClick = { selectedReagent = chem },
                    modifier = Modifier.testTag("select_chem_${chem.formula}"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedReagent.formula == chem.formula) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = chem.formula,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = chem.state,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // REACTION EQUATION AND EXPLANATION CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (labState.equationResult.isNotBlank()) {
                    Text(
                        text = "BALANCED REACTION",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = labState.equationResult,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = "Observations & Explanation:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = labState.reactionExplanation,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // LAB NOTEBOOK & RECORD OBSERVATIONS
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Laboratory Journal & Observations",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    if (showSavedMessage) {
                        Text(
                            text = "Saved to Journal! ✓",
                            color = Color(0xFF10B981),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = observationInput,
                    onValueChange = { observationInput = it },
                    placeholder = { Text("Record your findings, color shifts, precipitate or volume...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("lab_notes_input"),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 2
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if (observationInput.isNotBlank()) {
                            viewModel.saveLabObservation(observationInput)
                            showSavedMessage = true
                            observationInput = ""
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .testTag("save_observation_btn"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.BookmarkBorder, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Save to Lab Report (+30 XP)")
                }
            }
        }
    }
}
