package com.example.data.repository

import com.example.data.model.ReactionResult

object ReactionsEngine {

    fun simulateReaction(chemicalA: String, chemicalB: String): ReactionResult {
        val pair = setOf(chemicalA.trim(), chemicalB.trim())

        return when {
            // Neutralization: HCl + NaOH
            pair == setOf("HCl", "NaOH") -> ReactionResult(
                reactants = listOf("HCl (aq)", "NaOH (aq)"),
                products = listOf("NaCl (aq)", "H2O (l)"),
                balancedEquation = "HCl + NaOH → NaCl + H₂O",
                reactionType = "Neutralization (Acid-Base)",
                observableChange = "Solution turns slightly warm; clear liquid remains neutral (pH ~7.0).",
                explanation = "H⁺ ions from the strong acid react with OH⁻ ions from the strong base to form water and common salt. Exothermic reaction releases enthalpy of neutralization (~57.1 kJ/mol).",
                temperatureDeltaCelsius = 6.8f,
                resultingColor = 0x2238BDF8,
                hasGasBubbles = false,
                hasPrecipitate = false
            )

            // Neutralization: CH3COOH + NaOH
            pair == setOf("CH3COOH", "NaOH") -> ReactionResult(
                reactants = listOf("CH3COOH (aq)", "NaOH (aq)"),
                products = listOf("CH3COONa (aq)", "H2O (l)"),
                balancedEquation = "CH₃COOH + NaOH → CH₃COONa + H₂O",
                reactionType = "Neutralization (Weak Acid - Strong Base)",
                observableChange = "Mild temperature rise; slight vinegar scent disappears; slightly basic salt solution (pH ~8.8).",
                explanation = "Acetic acid reacts with sodium hydroxide to form sodium acetate and water. Hydrolysis of acetate ion leaves the solution mildly alkaline.",
                temperatureDeltaCelsius = 4.2f,
                resultingColor = 0x1538BDF8,
                hasGasBubbles = false,
                hasPrecipitate = false
            )

            // Gas evolution: Na2CO3 + HCl
            pair == setOf("Na2CO3", "HCl") -> ReactionResult(
                reactants = listOf("Na2CO3 (aq)", "2HCl (aq)"),
                products = listOf("2NaCl (aq)", "H2O (l)", "CO2 (g)"),
                balancedEquation = "Na₂CO₃ + 2HCl → 2NaCl + H₂O + CO₂↑",
                reactionType = "Double Displacement & Gas Evolution",
                observableChange = "Brisk, vigorous effervescence (bubbling). Clear colorless carbon dioxide gas evolves rapidly!",
                explanation = "Carbonate ions react with hydronium ions to form carbonic acid (H2CO3), which instantly decomposes into water and carbon dioxide gas.",
                temperatureDeltaCelsius = 1.5f,
                resultingColor = 0x2038BDF8,
                hasGasBubbles = true,
                hasPrecipitate = false
            )

            // Gas evolution: NaHCO3 + HCl
            pair == setOf("NaHCO3", "HCl") -> ReactionResult(
                reactants = listOf("NaHCO3 (aq)", "HCl (aq)"),
                products = listOf("NaCl (aq)", "H2O (l)", "CO2 (g)"),
                balancedEquation = "NaHCO₃ + HCl → NaCl + H₂O + CO₂↑",
                reactionType = "Neutralization & Gas Evolution",
                observableChange = "Instant effervescence with continuous rapid stream of CO2 gas bubbles.",
                explanation = "Bicarbonate reacts with acid releasing carbon dioxide. The escaping gas creates foam and bubbles.",
                temperatureDeltaCelsius = -0.5f,
                resultingColor = 0x2038BDF8,
                hasGasBubbles = true,
                hasPrecipitate = false
            )

            // Metal + Acid: Zn + HCl
            pair == setOf("Zn", "HCl") -> ReactionResult(
                reactants = listOf("Zn (s)", "2HCl (aq)"),
                products = listOf("ZnCl2 (aq)", "H2 (g)"),
                balancedEquation = "Zn + 2HCl → ZnCl₂ + H₂↑",
                reactionType = "Single Displacement (Redox)",
                observableChange = "Vigorous fizzing around zinc granules; flammable Hydrogen gas released (burns with a 'pop' sound); zinc metal gradually dissolves.",
                explanation = "Zinc has a higher oxidation potential than hydrogen in the electrochemical series. It reduces H⁺ ions to gaseous H₂ while getting oxidized to Zn²⁺.",
                temperatureDeltaCelsius = 5.0f,
                resultingColor = 0x2594A3B8,
                hasGasBubbles = true,
                hasPrecipitate = false,
                safetyWarning = "Hydrogen gas is highly flammable! Keep away from open flames."
            )

            // Metal + Acid: Mg + HCl
            pair == setOf("Mg", "HCl") -> ReactionResult(
                reactants = listOf("Mg (s)", "2HCl (aq)"),
                products = listOf("MgCl2 (aq)", "H2 (g)"),
                balancedEquation = "Mg + 2HCl → MgCl₂ + H₂↑",
                reactionType = "Single Displacement (Redox)",
                observableChange = "Rapid violent effervescence, test tube becomes hot, magnesium ribbon quickly disappears into solution!",
                explanation = "Magnesium is a highly reactive alkaline earth metal that vigorously displaces hydrogen from dilute acid.",
                temperatureDeltaCelsius = 12.0f,
                resultingColor = 0x20CBD5E1,
                hasGasBubbles = true,
                hasPrecipitate = false,
                safetyWarning = "Exothermic reaction! Liquid can sputter if acid is concentrated."
            )

            // Precipitation: AgNO3 + NaCl
            pair == setOf("AgNO3", "NaCl") -> ReactionResult(
                reactants = listOf("AgNO3 (aq)", "NaCl (aq)"),
                products = listOf("AgCl (s)↓", "NaNO3 (aq)"),
                balancedEquation = "AgNO₃ + NaCl → AgCl↓ (white precipitate) + NaNO₃",
                reactionType = "Precipitation (Double Displacement)",
                observableChange = "Instant formation of a thick, milky-white curdy precipitate of Silver Chloride!",
                explanation = "Silver ions (Ag⁺) and Chloride ions (Cl⁻) have a very low solubility product (Ksp ≈ 1.8×10⁻¹⁰), forming an insoluble solid lattice immediately upon contact.",
                temperatureDeltaCelsius = 0.8f,
                resultingColor = 0xFFFFFFFF,
                hasGasBubbles = false,
                hasPrecipitate = true,
                precipitateColor = 0xFFFFFFFF
            )

            // Precipitation: BaCl2 + H2SO4
            pair == setOf("BaCl2", "H2SO4") -> ReactionResult(
                reactants = listOf("BaCl2 (aq)", "H2SO4 (aq)"),
                products = listOf("BaSO4 (s)↓", "2HCl (aq)"),
                balancedEquation = "BaCl₂ + H₂SO₄ → BaSO₄↓ + 2HCl",
                reactionType = "Precipitation (Double Displacement)",
                observableChange = "Dense, heavy chalk-white precipitate forms that is insoluble even in excess dilute acid.",
                explanation = "Barium sulfate is practically insoluble in water and acids, making this the standard qualitative test for sulfate ions.",
                temperatureDeltaCelsius = 1.0f,
                resultingColor = 0xFFF8FAFC,
                hasGasBubbles = false,
                hasPrecipitate = true,
                precipitateColor = 0xFFFFFFFF
            )

            // Precipitation: Pb(NO3)2 + KI (Golden Spangles reaction!)
            pair == setOf("Pb(NO3)2", "KI") -> ReactionResult(
                reactants = listOf("Pb(NO3)2 (aq)", "2KI (aq)"),
                products = listOf("PbI2 (s)↓", "2KNO3 (aq)"),
                balancedEquation = "Pb(NO₃)₂ + 2KI → PbI₂↓ (bright yellow) + 2KNO₃",
                reactionType = "Precipitation (Double Displacement)",
                observableChange = "Spectacular bright canary-yellow precipitate of Lead(II) Iodide forms instantaneously!",
                explanation = "Lead cations combine with iodide anions to form Lead(II) iodide. When heated and allowed to cool slowly, it crystallizes into glittering golden flakes ('Golden Rain').",
                temperatureDeltaCelsius = 0.5f,
                resultingColor = 0xFFFBBF24,
                hasGasBubbles = false,
                hasPrecipitate = true,
                precipitateColor = 0xFFF59E0B,
                safetyWarning = "Lead compounds are toxic. Do not ingest, dispose in heavy metal waste."
            )

            // Precipitation: CuSO4 + NaOH
            pair == setOf("CuSO4", "NaOH") -> ReactionResult(
                reactants = listOf("CuSO4 (aq)", "2NaOH (aq)"),
                products = listOf("Cu(OH)2 (s)↓", "Na2SO4 (aq)"),
                balancedEquation = "CuSO₄ + 2NaOH → Cu(OH)₂↓ + Na₂SO₄",
                reactionType = "Precipitation & Complex Formation",
                observableChange = "A pale sky-blue gelatinous precipitate of Copper(II) Hydroxide separates from the solution.",
                explanation = "Copper(II) ions precipitate as hydroxide in basic medium. If heated, this precipitate turns black as it dehydrates into CuO.",
                temperatureDeltaCelsius = 2.0f,
                resultingColor = 0xFF38BDF8,
                hasGasBubbles = false,
                hasPrecipitate = true,
                precipitateColor = 0xFF0284C7
            )

            // Precipitation: FeCl3 + NaOH
            pair == setOf("FeCl3", "NaOH") -> ReactionResult(
                reactants = listOf("FeCl3 (aq)", "3NaOH (aq)"),
                products = listOf("Fe(OH)3 (s)↓", "3NaCl (aq)"),
                balancedEquation = "FeCl₃ + 3NaOH → Fe(OH)₃↓ + 3NaCl",
                reactionType = "Precipitation",
                observableChange = "Reddish-brown reddish-rust gelatinous precipitate forms.",
                explanation = "Iron(III) ions precipitate quantitatively as insoluble iron(III) hydroxide (hydrated ferric oxide).",
                temperatureDeltaCelsius = 2.5f,
                resultingColor = 0xFFB45309,
                hasGasBubbles = false,
                hasPrecipitate = true,
                precipitateColor = 0xFF78350F
            )

            // Gas detection: Ca(OH)2 + CO2 (or Na2CO3 + acid in limewater)
            pair == setOf("Ca(OH)2", "Na2CO3") -> ReactionResult(
                reactants = listOf("Ca(OH)2 (aq)", "Na2CO3 (aq)"),
                products = listOf("CaCO3 (s)↓", "2NaOH (aq)"),
                balancedEquation = "Ca(OH)₂ + Na₂CO₃ → CaCO₃↓ + 2NaOH",
                reactionType = "Precipitation (Causticizing)",
                observableChange = "Milky-white cloudiness appears as insoluble calcium carbonate forms.",
                explanation = "Calcium ions precipitate carbonate ions to form microcrystalline chalk (calcium carbonate).",
                temperatureDeltaCelsius = 0.5f,
                resultingColor = 0xFFE2E8F0,
                hasGasBubbles = false,
                hasPrecipitate = true,
                precipitateColor = 0xFFFFFFFF
            )

            // Organic Esterification: CH3COOH + C2H5OH
            pair == setOf("CH3COOH", "C2H5OH") -> ReactionResult(
                reactants = listOf("CH3COOH (l)", "C2H5OH (l) [in presence of H2SO4]"),
                products = listOf("CH3COOC2H5 (l)", "H2O (l)"),
                balancedEquation = "CH₃COOH + C₂H₅OH ⇌ CH₃COOC₂H₅ + H₂O",
                reactionType = "Esterification (Condensation)",
                observableChange = "Clear solution remains colorless; upon gentle warming produces a distinct pleasant, sweet, fruity apple/pear fragrance.",
                explanation = "Fischer esterification: carboxylic acid reacts with alcohol in equilibrium, catalyzed by concentrated sulfuric acid, eliminating a water molecule to create Ethyl Acetate ester.",
                temperatureDeltaCelsius = 1.0f,
                resultingColor = 0x15FFFFFF,
                hasGasBubbles = false,
                hasPrecipitate = false
            )

            // Indicator reactions
            pair.contains("Phenolphthalein") && pair.contains("NaOH") -> ReactionResult(
                reactants = listOf("Phenolphthalein", "NaOH (alkali)"),
                products = listOf("Dianion colored resonance hybrid"),
                balancedEquation = "H₂In (colorless) + 2OH⁻ ⇌ In²⁻ (vibrant magenta pink) + 2H₂O",
                reactionType = "Acid-Base Indicator Quinonoid Rearrangement",
                observableChange = "Color changes instantly from transparent clear to vivid, glowing fuchsia pink (pH > 8.2)!",
                explanation = "At high pH, the lactone ring opens and protons are abstracted, yielding an extensive conjugated quinonoid chromophore that absorbs green light and transmits deep pink.",
                temperatureDeltaCelsius = 0.0f,
                resultingColor = 0xFFEC4899,
                hasGasBubbles = false,
                hasPrecipitate = false
            )

            pair.contains("Phenolphthalein") && pair.contains("HCl") -> ReactionResult(
                reactants = listOf("Phenolphthalein", "HCl (acid)"),
                products = listOf("Neutral In neutral form"),
                balancedEquation = "In²⁻ + 2H⁺ → H₂In (colorless lactone)",
                reactionType = "Indicator Acidic Equilibrium",
                observableChange = "Liquid remains completely clear and colorless.",
                explanation = "In acidic solution (pH < 8.2), phenolphthalein exists predominantly in its colorless neutral lactone form.",
                temperatureDeltaCelsius = 0.0f,
                resultingColor = 0x1538BDF8,
                hasGasBubbles = false,
                hasPrecipitate = false
            )

            pair.contains("Methyl_Orange") && pair.contains("HCl") -> ReactionResult(
                reactants = listOf("Methyl Orange", "HCl (acid)"),
                products = listOf("Protonated quinonoid form"),
                balancedEquation = "HIn (red) ⇌ In⁻ (yellow) + H⁺",
                reactionType = "Acid-Base Indicator Tautomerism",
                observableChange = "Liquid turns from bright orange to deep crimson red (pH < 3.1).",
                explanation = "Azo nitrogen accepts a proton in strongly acidic media, extending electronic delocalization and shifting absorption to give a red color.",
                temperatureDeltaCelsius = 0.0f,
                resultingColor = 0xFFEF4444,
                hasGasBubbles = false,
                hasPrecipitate = false
            )

            // Default: No significant reaction
            else -> ReactionResult(
                reactants = listOf(chemicalA, chemicalB),
                products = listOf("No Reaction (Physical mixture only)"),
                balancedEquation = "$chemicalA + $chemicalB → No Reaction",
                reactionType = "Physical Mixture",
                observableChange = "Chemicals dissolve or mix together uniformly without any bubbling, precipitate, or noticeable color change.",
                explanation = "No energetically favorable reaction pathway exists between these substances under standard laboratory conditions.",
                temperatureDeltaCelsius = 0.0f,
                resultingColor = 0x2238BDF8,
                hasGasBubbles = false,
                hasPrecipitate = false
            )
        }
    }
}
