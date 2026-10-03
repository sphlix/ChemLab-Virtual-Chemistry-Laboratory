package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.ChemLabViewModel

@Composable
fun ChemistryCalculatorScreen(
    viewModel: ChemLabViewModel,
    modifier: Modifier = Modifier
) {
    val currentType by viewModel.calculatorType.collectAsState()
    val calcResult by viewModel.calcResult.collectAsState()

    val calculatorTypes = listOf(
        "Molar Mass", "Moles", "Molarity", "Normality", "Dilution", "pH & pOH", "Gas Law", "Density"
    )

    // Form inputs state
    var formulaInput by remember { mutableStateOf("H2SO4") }
    var massInput by remember { mutableStateOf("9.8") }
    var molarMassInput by remember { mutableStateOf("98.08") }
    var molesInput by remember { mutableStateOf("0.25") }
    var volumeMlInput by remember { mutableStateOf("500") }
    var valencyInput by remember { mutableStateOf("2") }
    var m1Input by remember { mutableStateOf("1.0") }
    var v1Input by remember { mutableStateOf("50") }
    var v2Input by remember { mutableStateOf("250") }
    var hConcInput by remember { mutableStateOf("0.001") }
    var tempKInput by remember { mutableStateOf("298.15") }
    var volumeLInput by remember { mutableStateOf("22.4") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("chemistry_calculator_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column {
            Text(
                text = "Chemical Calculator 🧮",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Step-by-step chemical calculations and unit conversions",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // CALCULATOR MODE CHIPS
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(calculatorTypes) { type ->
                FilterChip(
                    selected = currentType == type,
                    onClick = { viewModel.setCalculatorType(type) },
                    label = { Text(type) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }

        // INPUT FORMS ACCORDING TO TYPE
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                when (currentType) {
                    "Molar Mass" -> {
                        OutlinedTextField(
                            value = formulaInput,
                            onValueChange = { formulaInput = it },
                            label = { Text("Chemical Formula (e.g. CuSO4, Ca(OH)2, C6H12O6)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("calc_formula_input"),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Button(
                            onClick = { viewModel.computeMolarMass(formulaInput) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("calc_submit_btn"),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Calculate Molar Mass & % Composition", fontWeight = FontWeight.Bold)
                        }
                    }

                    "Moles" -> {
                        OutlinedTextField(
                            value = massInput,
                            onValueChange = { massInput = it },
                            label = { Text("Mass in grams (g)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = molarMassInput,
                            onValueChange = { molarMassInput = it },
                            label = { Text("Molar Mass (g/mol)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Button(
                            onClick = {
                                val m = massInput.toDoubleOrNull() ?: 0.0
                                val mm = molarMassInput.toDoubleOrNull() ?: 1.0
                                viewModel.computeMoles(m, mm)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Calculate Moles (n = m / M)", fontWeight = FontWeight.Bold)
                        }
                    }

                    "Molarity" -> {
                        OutlinedTextField(
                            value = molesInput,
                            onValueChange = { molesInput = it },
                            label = { Text("Moles of Solute (mol)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = volumeMlInput,
                            onValueChange = { volumeMlInput = it },
                            label = { Text("Solution Volume in mL") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Button(
                            onClick = {
                                val mol = molesInput.toDoubleOrNull() ?: 0.0
                                val v = volumeMlInput.toDoubleOrNull() ?: 1000.0
                                viewModel.computeMolarity(mol, v)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Calculate Molarity (M = n / V)", fontWeight = FontWeight.Bold)
                        }
                    }

                    "Normality" -> {
                        OutlinedTextField(
                            value = m1Input,
                            onValueChange = { m1Input = it },
                            label = { Text("Molarity (M)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = valencyInput,
                            onValueChange = { valencyInput = it },
                            label = { Text("Valency Factor / Basicity (n)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Button(
                            onClick = {
                                val m = m1Input.toDoubleOrNull() ?: 1.0
                                val n = valencyInput.toIntOrNull() ?: 1
                                viewModel.computeNormality(m, n)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Calculate Normality (N = M × n)", fontWeight = FontWeight.Bold)
                        }
                    }

                    "Dilution" -> {
                        OutlinedTextField(
                            value = m1Input,
                            onValueChange = { m1Input = it },
                            label = { Text("Initial Concentration M₁ (M)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = v1Input,
                            onValueChange = { v1Input = it },
                            label = { Text("Initial Stock Volume V₁ (mL)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = v2Input,
                            onValueChange = { v2Input = it },
                            label = { Text("Desired Final Diluted Volume V₂ (mL)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Button(
                            onClick = {
                                val m1 = m1Input.toDoubleOrNull() ?: 1.0
                                val v1 = v1Input.toDoubleOrNull() ?: 50.0
                                val v2 = v2Input.toDoubleOrNull() ?: 250.0
                                viewModel.computeDilution(m1, v1, null, v2)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Calculate Diluted M₂ (M₁V₁ = M₂V₂)", fontWeight = FontWeight.Bold)
                        }
                    }

                    "pH & pOH" -> {
                        OutlinedTextField(
                            value = hConcInput,
                            onValueChange = { hConcInput = it },
                            label = { Text("Hydrogen Ion [H⁺] Concentration (M)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Button(
                            onClick = {
                                val h = hConcInput.toDoubleOrNull() ?: 0.001
                                viewModel.computePh(h)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Calculate pH & pOH", fontWeight = FontWeight.Bold)
                        }
                    }

                    "Gas Law" -> {
                        OutlinedTextField(
                            value = molesInput,
                            onValueChange = { molesInput = it },
                            label = { Text("Moles of Gas (n)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = tempKInput,
                            onValueChange = { tempKInput = it },
                            label = { Text("Temperature in Kelvin (K)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = volumeLInput,
                            onValueChange = { volumeLInput = it },
                            label = { Text("Gas Volume in Liters (L)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Button(
                            onClick = {
                                val n = molesInput.toDoubleOrNull() ?: 1.0
                                val t = tempKInput.toDoubleOrNull() ?: 298.15
                                val v = volumeLInput.toDoubleOrNull() ?: 22.4
                                viewModel.computeGasLaw(n, t, v)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Calculate Gas Pressure (PV = nRT)", fontWeight = FontWeight.Bold)
                        }
                    }

                    "Density" -> {
                        OutlinedTextField(
                            value = massInput,
                            onValueChange = { massInput = it },
                            label = { Text("Mass in grams (g)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = volumeMlInput,
                            onValueChange = { volumeMlInput = it },
                            label = { Text("Volume in mL (cm³)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Button(
                            onClick = {
                                val m = massInput.toDoubleOrNull() ?: 10.0
                                val v = volumeMlInput.toDoubleOrNull() ?: 5.0
                                viewModel.computeDensity(m, v)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Calculate Density (d = m / V)", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // CALCULATION RESULTS CARD
        if (calcResult != null) {
            val res = calcResult!!
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = res.title.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF38BDF8),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = res.finalValue,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF1E293B)
                    ) {
                        Text(
                            text = "Formula: ${res.formulaUsed}",
                            color = Color(0xFF7DD3FC),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "STEP-BY-STEP DERIVATION",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    res.steps.forEach { step ->
                        Text(
                            text = step,
                            color = Color(0xFFE2E8F0),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
