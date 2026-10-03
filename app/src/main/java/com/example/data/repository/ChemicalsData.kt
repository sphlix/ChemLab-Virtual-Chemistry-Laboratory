package com.example.data.repository

import com.example.data.model.Chemical

object ChemicalsData {
    val allChemicals: List<Chemical> = listOf(
        Chemical("HCl", "Hydrochloric Acid (0.1M)", "Liquid", 0x2280D8FF, 1.0f, "Corrosive Acid", "⚠️ Corrosive", 36.46, isCorrosive = true),
        Chemical("H2SO4", "Sulfuric Acid (0.1M)", "Liquid", 0x2240C4FF, 0.5f, "Strong Acid & Dehydrating Agent", "⚠️ Corrosive", 98.08, isCorrosive = true),
        Chemical("HNO3", "Nitric Acid (0.1M)", "Liquid", 0x33FFE082, 1.2f, "Strong Oxidizing Acid", "⚠️ Oxidizer", 63.01, isCorrosive = true),
        Chemical("CH3COOH", "Acetic Acid (Vinegar)", "Liquid", 0x11FFFFFF, 2.9f, "Weak Organic Acid", "⚠️ Irritant", 60.05),
        Chemical("NaOH", "Sodium Hydroxide (0.1M)", "Liquid", 0x11FFFFFF, 13.0f, "Strong Caustic Base", "⚠️ Corrosive Base", 40.00, isCorrosive = true),
        Chemical("KOH", "Potassium Hydroxide", "Liquid", 0x11FFFFFF, 13.5f, "Caustic Potash", "⚠️ Corrosive Base", 56.11, isCorrosive = true),
        Chemical("NH4OH", "Ammonium Hydroxide", "Liquid", 0x11FFFFFF, 11.0f, "Weak Base with pungent odor", "⚠️ Toxic Vapors", 35.05),
        Chemical("Ca(OH)2", "Calcium Hydroxide (Limewater)", "Solution", 0x22FFFFFF, 12.4f, "Tests for CO2 (turns milky)", "⚠️ Alkaline", 74.09),
        Chemical("NaCl", "Sodium Chloride Solution", "Solution", 0x11FFFFFF, 7.0f, "Neutral Table Salt", "✅ Safe", 58.44),
        Chemical("CuSO4", "Copper(II) Sulfate", "Solution", 0xFF0284C7, 4.0f, "Bright Blue Crystal Solution", "⚠️ Toxic to aquatic life", 159.61),
        Chemical("AgNO3", "Silver Nitrate (0.1M)", "Solution", 0x11FFFFFF, 5.0f, "Tests for Halides / Stains skin", "⚠️ Corrosive", 169.87),
        Chemical("BaCl2", "Barium Chloride", "Solution", 0x11FFFFFF, 6.5f, "Tests for Sulfate ions", "⚠️ Toxic", 208.23),
        Chemical("KI", "Potassium Iodide", "Solution", 0x11FFFFFF, 7.0f, "Source of Iodide ions", "✅ Low Hazard", 166.00),
        Chemical("Pb(NO3)2", "Lead(II) Nitrate", "Solution", 0x11FFFFFF, 4.5f, "Heavy metal salt solution", "⚠️ Toxic Heavy Metal", 331.20),
        Chemical("FeSO4", "Iron(II) Sulfate", "Solution", 0xFF86EFAC, 3.5f, "Pale Green Solution", "⚠️ Irritant", 151.91),
        Chemical("FeCl3", "Iron(III) Chloride", "Solution", 0xFFD97706, 2.0f, "Yellow-Brown Solution", "⚠️ Corrosive", 162.20),
        Chemical("Na2CO3", "Sodium Carbonate", "Solution", 0x11FFFFFF, 11.5f, "Washing Soda solution", "⚠️ Irritant", 105.99),
        Chemical("NaHCO3", "Sodium Bicarbonate", "Solution", 0x11FFFFFF, 8.3f, "Baking soda solution", "✅ Safe", 84.01),
        Chemical("KMnO4", "Potassium Permanganate", "Solution", 0xFF831843, 7.0f, "Deep Purple Strong Oxidant", "⚠️ Oxidizing", 158.03),
        Chemical("Phenolphthalein", "Phenolphthalein Indicator", "Solution", 0x11FFFFFF, null, "Acid: Colorless | Base: Bright Pink", "⚠️ Flammable (alcoholic)", 318.32, isFlammable = true),
        Chemical("Methyl_Orange", "Methyl Orange Indicator", "Solution", 0xFFF97316, null, "Acid: Red | Base: Yellow", "⚠️ Toxic", 327.33),
        Chemical("Universal_Indicator", "Universal pH Indicator", "Solution", 0xFF10B981, null, "Colors across pH 1 to 14", "✅ Safe", 0.0),
        Chemical("Zn", "Zinc Granules", "Solid", 0xFF94A3B8, null, "Silvery-grey reactive metal", "⚠️ Water-reactive dust", 65.38),
        Chemical("Mg", "Magnesium Ribbon", "Solid", 0xFFCBD5E1, null, "Burns with blinding white light", "⚠️ Flammable solid", 24.31, isFlammable = true),
        Chemical("Cu", "Copper Wire / Foil", "Solid", 0xFFB45309, null, "Reddish-brown metal", "✅ Safe", 63.55),
        Chemical("C2H5OH", "Ethanol (Ethyl Alcohol)", "Liquid", 0x11FFFFFF, 7.0f, "Volatile Organic Solvent", "⚠️ Highly Flammable", 46.07, isFlammable = true),
        Chemical("H2O", "Distilled Water", "Liquid", 0x2238BDF8, 7.0f, "Pure universal solvent", "✅ Non-hazardous", 18.015)
    )

    fun findChemical(formulaOrName: String): Chemical? {
        return allChemicals.find { it.formula.equals(formulaOrName, ignoreCase = true) || it.name.contains(formulaOrName, ignoreCase = true) }
    }
}
