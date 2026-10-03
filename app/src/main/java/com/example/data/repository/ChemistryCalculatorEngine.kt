package com.example.data.repository

import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

data class CalculationResult(
    val title: String,
    val finalValue: String,
    val formulaUsed: String,
    val steps: List<String>
)

object ChemistryCalculatorEngine {

    // Atomic masses of common elements
    private val atomicWeights = mapOf(
        "H" to 1.008, "He" to 4.003, "Li" to 6.941, "Be" to 9.012, "B" to 10.811,
        "C" to 12.011, "N" to 14.007, "O" to 15.999, "F" to 18.998, "Ne" to 20.180,
        "Na" to 22.990, "Mg" to 24.305, "Al" to 26.982, "Si" to 28.086, "P" to 30.974,
        "S" to 32.065, "Cl" to 35.453, "K" to 39.098, "Ar" to 39.948, "Ca" to 40.078,
        "Cr" to 51.996, "Mn" to 54.938, "Fe" to 55.845, "Cu" to 63.546, "Zn" to 65.38,
        "Br" to 79.904, "Ag" to 107.868, "I" to 126.904, "Ba" to 137.327, "Pb" to 207.2
    )

    // Formula parser for molar mass and percent composition
    fun calculateMolarMass(rawFormula: String): CalculationResult {
        val formula = rawFormula.trim().replace(" ", "")
        if (formula.isEmpty()) {
            return CalculationResult("Molar Mass", "0.00 g/mol", "M = Σ(n_i × A_i)", listOf("Please enter a chemical formula (e.g. H2SO4, CaCO3)."))
        }

        try {
            val elementCounts = parseFormula(formula)
            if (elementCounts.isEmpty()) {
                return CalculationResult("Molar Mass", "Invalid Formula", "M = Σ(n_i × A_i)", listOf("Could not parse formula. Example formats: H2O, CuSO4, Ca(OH)2"))
            }

            var totalMass = 0.0
            val steps = mutableListOf<String>()
            steps.add("Formula parsed into elemental counts:")

            elementCounts.forEach { (elem, count) ->
                val weight = atomicWeights[elem] ?: 0.0
                val subMass = weight * count
                totalMass += subMass
                val percent = if (totalMass > 0) 0.0 else 0.0 // updated below
                steps.add("• $elem × $count = $count × ${"%.3f".format(weight)} = ${"%.3f".format(subMass)} g/mol")
            }

            val finalSteps = mutableListOf<String>()
            finalSteps.addAll(steps)
            finalSteps.add("Sum of all atomic masses: ${"%.3f".format(totalMass)} g/mol")
            finalSteps.add("Percentage Composition:")
            elementCounts.forEach { (elem, count) ->
                val weight = atomicWeights[elem] ?: 0.0
                val percent = (weight * count / totalMass) * 100.0
                finalSteps.add("  - % $elem = (${"%.2f".format(weight * count)} / ${"%.2f".format(totalMass)}) × 100% = ${"%.2f".format(percent)}%")
            }

            return CalculationResult(
                title = "Molar Mass of $formula",
                finalValue = "${"%.3f".format(totalMass)} g/mol",
                formulaUsed = "M = Σ (Number of atoms × Atomic weight)",
                steps = finalSteps
            )
        } catch (e: Exception) {
            return CalculationResult("Molar Mass", "Parsing Error", "M = Σ(n_i × A_i)", listOf("Error: ${e.message}. Use capital letters for elements, e.g., NaCl."))
        }
    }

    private fun parseFormula(formula: String): Map<String, Int> {
        // Handles simple parentheses like Ca(OH)2 or direct formulas like H2SO4
        val result = mutableMapOf<String, Int>()

        // Check for single parenthesis e.g. Ca(OH)2
        val parenRegex = Regex("""(.*)\((.*)\)(\d+)(.*)""")
        val match = parenRegex.matchEntire(formula)
        if (match != null) {
            val prefix = match.groupValues[1]
            val inner = match.groupValues[2]
            val multiplier = match.groupValues[3].toIntOrNull() ?: 1
            val suffix = match.groupValues[4]

            parseSimpleSegment(prefix, 1, result)
            parseSimpleSegment(inner, multiplier, result)
            parseSimpleSegment(suffix, 1, result)
            return result
        }

        parseSimpleSegment(formula, 1, result)
        return result
    }

    private fun parseSimpleSegment(segment: String, multiplier: Int, map: MutableMap<String, Int>) {
        val elemRegex = Regex("""([A-Z][a-z]?)(\d*)""")
        for (m in elemRegex.findAll(segment)) {
            val elem = m.groupValues[1]
            val countStr = m.groupValues[2]
            val count = (if (countStr.isEmpty()) 1 else countStr.toInt()) * multiplier
            map[elem] = (map[elem] ?: 0) + count
        }
    }

    // Moles: n = mass / molarMass
    fun calculateMoles(massGrams: Double, molarMass: Double): CalculationResult {
        if (molarMass <= 0.0) return CalculationResult("Moles", "Error", "n = m / M", listOf("Molar mass must be greater than 0."))
        val moles = massGrams / molarMass
        return CalculationResult(
            title = "Moles (n)",
            finalValue = "${"%.4f".format(moles)} mol",
            formulaUsed = "n = m / M",
            steps = listOf(
                "1. Given Mass (m) = $massGrams g",
                "2. Given Molar Mass (M) = $molarMass g/mol",
                "3. Substitution: n = $massGrams / $molarMass",
                "4. Calculated Moles = ${"%.5f".format(moles)} mol (${"%.4f".format(moles * 1000)} mmol)"
            )
        )
    }

    // Molarity: M = moles / Volume(L)
    fun calculateMolarity(moles: Double, volumeMl: Double): CalculationResult {
        if (volumeMl <= 0.0) return CalculationResult("Molarity", "Error", "M = n / V", listOf("Volume must be greater than 0 mL."))
        val volumeL = volumeMl / 1000.0
        val molarity = moles / volumeL
        return CalculationResult(
            title = "Molarity (M)",
            finalValue = "${"%.4f".format(molarity)} M (mol/L)",
            formulaUsed = "M = n / V(L) = (n × 1000) / V(mL)",
            steps = listOf(
                "1. Amount of Solute (n) = $moles mol",
                "2. Solution Volume = $volumeMl mL = ${"%.4f".format(volumeL)} L",
                "3. Molarity formula: M = $moles / $volumeL",
                "4. Result: M = ${"%.4f".format(molarity)} M"
            )
        )
    }

    // Normality: N = M × Valency Factor (n-factor)
    fun calculateNormality(molarity: Double, valencyFactor: Int): CalculationResult {
        val normality = molarity * valencyFactor
        return CalculationResult(
            title = "Normality (N)",
            finalValue = "${"%.4f".format(normality)} N (eq/L)",
            formulaUsed = "N = Molarity (M) × Valency Factor (n)",
            steps = listOf(
                "1. Molarity (M) = $molarity mol/L",
                "2. Valency factor / Basicity / Acidity (n) = $valencyFactor",
                "   • For HCl (monoprotic): n = 1",
                "   • For H2SO4 (diprotic): n = 2",
                "   • For Al(OH)3: n = 3",
                "3. Calculation: N = $molarity × $valencyFactor = ${"%.4f".format(normality)} N"
            )
        )
    }

    // Dilution: M1 * V1 = M2 * V2
    fun calculateDilution(m1: Double, v1: Double, m2: Double?, v2: Double?): CalculationResult {
        return when {
            m2 == null && v2 != null && v2 > 0 -> {
                val targetM2 = (m1 * v1) / v2
                CalculationResult(
                    title = "Target Concentration (M₂)",
                    finalValue = "${"%.4f".format(targetM2)} M",
                    formulaUsed = "M₁ × V₁ = M₂ × V₂  ⟹  M₂ = (M₁ × V₁) / V₂",
                    steps = listOf(
                        "1. Initial Stock Molarity (M₁) = $m1 M",
                        "2. Volume of Stock used (V₁) = $v1 mL",
                        "3. Final Diluted Volume (V₂) = $v2 mL",
                        "4. M₂ = ($m1 × $v1) / $v2 = ${"%.4f".format(targetM2)} M"
                    )
                )
            }
            v2 == null && m2 != null && m2 > 0 -> {
                val targetV2 = (m1 * v1) / m2
                val waterToAdd = targetV2 - v1
                CalculationResult(
                    title = "Target Total Volume (V₂)",
                    finalValue = "${"%.2f".format(targetV2)} mL",
                    formulaUsed = "V₂ = (M₁ × V₁) / M₂",
                    steps = listOf(
                        "1. Initial Stock Molarity (M₁) = $m1 M",
                        "2. Volume of Stock (V₁) = $v1 mL",
                        "3. Desired Final Molarity (M₂) = $m2 M",
                        "4. Total Final Volume V₂ = ($m1 × $v1) / $m2 = ${"%.2f".format(targetV2)} mL",
                        "5. Distilled Water to Add = V₂ - V₁ = ${"%.2f".format(waterToAdd)} mL"
                    )
                )
            }
            else -> CalculationResult("Dilution", "Error", "M₁V₁ = M₂V₂", listOf("Provide either M2 or V2."))
        }
    }

    // pH & pOH: pH = -log[H+], pOH = 14 - pH
    fun calculatePhFromH(concentrationH: Double): CalculationResult {
        if (concentrationH <= 0.0) return CalculationResult("pH Calculation", "Error", "pH = -log₁₀[H⁺]", listOf("[H⁺] must be greater than 0."))
        val ph = -log10(concentrationH)
        val poh = 14.0 - ph
        val classification = when {
            ph < 6.8 -> "Acidic (Red/Orange on Universal Indicator)"
            ph > 7.2 -> "Basic / Alkaline (Blue/Violet on Universal Indicator)"
            else -> "Neutral (Green on Universal Indicator)"
        }
        return CalculationResult(
            title = "pH & pOH",
            finalValue = "pH = ${"%.2f".format(ph)} | pOH = ${"%.2f".format(poh)}",
            formulaUsed = "pH = -log₁₀[H⁺]  and  pOH = 14.0 - pH",
            steps = listOf(
                "1. Hydrogen ion concentration [H⁺] = $concentrationH M",
                "2. pH = -log₁₀($concentrationH) = ${"%.3f".format(ph)}",
                "3. pOH = 14.0 - ${"%.3f".format(ph)} = ${"%.3f".format(poh)}",
                "4. Hydroxide ion concentration [OH⁻] = 10^(-pOH) = ${"%.3e".format(10.0.pow(-poh))} M",
                "5. Classification: $classification"
            )
        )
    }

    // Ideal Gas Law: PV = nRT
    fun calculateGasPressure(nMol: Double, tempKelvin: Double, volumeL: Double): CalculationResult {
        val rConstant = 0.08206 // L*atm / (mol*K)
        if (volumeL <= 0.0) return CalculationResult("Ideal Gas Law", "Error", "PV = nRT", listOf("Volume must be greater than 0 L."))
        val pressureAtm = (nMol * rConstant * tempKelvin) / volumeL
        val pressureKpa = pressureAtm * 101.325
        return CalculationResult(
            title = "Gas Pressure (P)",
            finalValue = "${"%.3f".format(pressureAtm)} atm (${"%.2f".format(pressureKpa)} kPa)",
            formulaUsed = "P = (n × R × T) / V",
            steps = listOf(
                "1. Moles of gas (n) = $nMol mol",
                "2. Absolute Temperature (T) = $tempKelvin K (${"%.1f".format(tempKelvin - 273.15)}°C)",
                "3. Container Volume (V) = $volumeL L",
                "4. Universal Gas Constant (R) = 0.08206 L·atm/(mol·K)",
                "5. Substitution: P = ($nMol × 0.08206 × $tempKelvin) / $volumeL",
                "6. Pressure P = ${"%.4f".format(pressureAtm)} atm = ${"%.2f".format(pressureKpa)} kPa"
            )
        )
    }

    // Density: d = m / V
    fun calculateDensity(massG: Double, volumeMl: Double): CalculationResult {
        if (volumeMl <= 0.0) return CalculationResult("Density", "Error", "d = m / V", listOf("Volume must be greater than 0 mL."))
        val density = massG / volumeMl
        return CalculationResult(
            title = "Density (d)",
            finalValue = "${"%.4f".format(density)} g/cm³",
            formulaUsed = "d = m / V",
            steps = listOf(
                "1. Mass (m) = $massG g",
                "2. Volume (V) = $volumeMl mL (cm³)",
                "3. d = $massG / $volumeMl = ${"%.4f".format(density)} g/cm³",
                "4. Equivalent in SI units: ${"%.2f".format(density * 1000)} kg/m³"
            )
        )
    }
}
