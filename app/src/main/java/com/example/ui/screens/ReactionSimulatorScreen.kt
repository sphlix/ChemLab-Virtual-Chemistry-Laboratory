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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.data.repository.ChemicalsData
import com.example.ui.ChemLabViewModel
import com.example.ui.components.FlaskGlassware

@Composable
fun ReactionSimulatorScreen(
    viewModel: ChemLabViewModel,
    modifier: Modifier = Modifier
) {
    val chemA by viewModel.simChemicalA.collectAsState()
    val chemB by viewModel.simChemicalB.collectAsState()
    val reactionResult by viewModel.simReactionResult.collectAsState()

    val availableReactants = listOf("HCl", "NaOH", "CH3COOH", "Na2CO3", "NaHCO3", "Zn", "Mg", "AgNO3", "NaCl", "BaCl2", "H2SO4", "Pb(NO3)2", "KI", "CuSO4", "Phenolphthalein", "Methyl_Orange")

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("reaction_simulator_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // TITLE
        Column {
            Text(
                text = "Chemical Reaction Simulator ⚗️",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Select two reactants to simulate chemical stoichiometry and observable kinetics",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // REACTANT SELECTION ROW
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Reactant A Box
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(90.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Reactant 1", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(chemA?.formula ?: "Select", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(chemA?.state ?: "", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.padding(horizontal = 8.dp))

            // Reactant B Box
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(90.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Reactant 2", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(chemB?.formula ?: "Select", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(chemB?.state ?: "", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // CHEMICAL CHOOSER - REACTANT A
        Text("Select Reactant 1:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(availableReactants) { formula ->
                val chem = ChemicalsData.findChemical(formula)
                if (chem != null) {
                    Surface(
                        onClick = { viewModel.setSimChemicalA(chem) },
                        shape = RoundedCornerShape(10.dp),
                        color = if (chemA?.formula == formula) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.testTag("select_sim_a_$formula")
                    ) {
                        Text(
                            text = formula,
                            fontWeight = FontWeight.Bold,
                            color = if (chemA?.formula == formula) Color.White else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // CHEMICAL CHOOSER - REACTANT B
        Text("Select Reactant 2:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(availableReactants) { formula ->
                val chem = ChemicalsData.findChemical(formula)
                if (chem != null) {
                    Surface(
                        onClick = { viewModel.setSimChemicalB(chem) },
                        shape = RoundedCornerShape(10.dp),
                        color = if (chemB?.formula == formula) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.testTag("select_sim_b_$formula")
                    ) {
                        Text(
                            text = formula,
                            fontWeight = FontWeight.Bold,
                            color = if (chemB?.formula == formula) Color.White else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // REACTION VISUALIZATION FLASK
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                FlaskGlassware(
                    modifier = Modifier.size(130.dp, 160.dp),
                    fillLevel = 0.6f,
                    liquidColor = Color(reactionResult?.resultingColor ?: 0x3338BDF8),
                    isBubbling = reactionResult?.hasGasBubbles ?: false,
                    precipitateColor = reactionResult?.precipitateColor?.let { Color(it) }
                )
            }
        }

        // BALANCED CHEMICAL EQUATION CARD
        if (reactionResult != null) {
            val res = reactionResult!!
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("BALANCED CHEMICAL EQUATION", style = MaterialTheme.typography.labelSmall, color = Color(0xFF38BDF8), fontWeight = FontWeight.Bold)
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF38BDF8).copy(alpha = 0.2f)
                        ) {
                            Text(res.reactionType, color = Color(0xFF38BDF8), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = res.balancedEquation,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Observable change
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(res.observableChange, color = Color(0xFFE2E8F0), style = MaterialTheme.typography.bodySmall)
                    }

                    if (res.temperatureDeltaCelsius != 0.0f) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Thermostat, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            val deltaStr = if (res.temperatureDeltaCelsius > 0) "+${res.temperatureDeltaCelsius}°C (Exothermic)" else "${res.temperatureDeltaCelsius}°C (Endothermic)"
                            Text("Thermal Change: $deltaStr", color = Color(0xFFFDE68A), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            // SCIENTIFIC EXPLANATION
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Chemical Reaction Explanation", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(res.explanation, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                }
            }

            // SAFETY WARNING IF ANY
            if (res.safetyWarning != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = "Warning", tint = Color(0xFFDC2626), modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Safety Caution", color = Color(0xFFDC2626), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                            Text(res.safetyWarning!!, color = Color(0xFF7F1D1D), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
