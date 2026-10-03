package com.example

import com.example.data.repository.ChemistryCalculatorEngine
import com.example.data.repository.ReactionsEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testReactionsEngine_Neutralization() {
        val result = ReactionsEngine.simulateReaction("HCl", "NaOH")
        assertEquals("HCl + NaOH → NaCl + H₂O", result.balancedEquation)
        assertEquals("Neutralization (Acid-Base)", result.reactionType)
        assertTrue(result.temperatureDeltaCelsius > 0)
    }

    @Test
    fun testReactionsEngine_Precipitation() {
        val result = ReactionsEngine.simulateReaction("AgNO3", "NaCl")
        assertTrue(result.hasPrecipitate)
        assertEquals("Precipitation (Double Displacement)", result.reactionType)
    }

    @Test
    fun testReactionsEngine_GasEvolution() {
        val result = ReactionsEngine.simulateReaction("Zn", "HCl")
        assertTrue(result.hasGasBubbles)
        assertNotNull(result.safetyWarning)
    }

    @Test
    fun testCalculatorEngine_MolarMassH2SO4() {
        val result = ChemistryCalculatorEngine.calculateMolarMass("H2SO4")
        assertTrue(result.finalValue.contains("98."))
    }

    @Test
    fun testCalculatorEngine_MolarMassCaCO3() {
        val result = ChemistryCalculatorEngine.calculateMolarMass("CaCO3")
        assertTrue(result.finalValue.contains("100."))
    }

    @Test
    fun testCalculatorEngine_Molarity() {
        val result = ChemistryCalculatorEngine.calculateMolarity(0.5, 1000.0)
        assertEquals("0.5000 M (mol/L)", result.finalValue)
    }

    @Test
    fun testCalculatorEngine_Dilution() {
        val result = ChemistryCalculatorEngine.calculateDilution(2.0, 50.0, null, 200.0)
        assertEquals("0.5000 M", result.finalValue)
    }

    @Test
    fun testCalculatorEngine_pH() {
        val result = ChemistryCalculatorEngine.calculatePhFromH(0.01)
        assertTrue(result.finalValue.contains("pH = 2.00"))
    }
}
