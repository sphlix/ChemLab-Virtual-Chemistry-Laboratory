package com.example.data.repository

import com.example.data.model.QuizQuestion
import com.example.data.model.QuizType

object QuizData {
    val questions: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = 1,
            question = "Which apparatus is specifically designed for quantitative, drop-by-drop delivery of a liquid in titrations?",
            type = QuizType.APPARATUS_IDENTIFY,
            options = listOf("Burette", "Measuring Cylinder", "Conical Flask", "Beaker"),
            correctIndex = 0,
            explanation = "A burette features a precision graduated glass barrel and stopcock tap for accurate dropwise addition of titrant.",
            difficulty = "Easy",
            category = "Laboratory Apparatus"
        ),
        QuizQuestion(
            id = 2,
            question = "What color does phenolphthalein turn when added to a basic solution (pH > 8.2)?",
            type = QuizType.MULTIPLE_CHOICE,
            options = listOf("Colorless", "Deep Fuchsia Pink", "Bright Yellow", "Emerald Green"),
            correctIndex = 1,
            explanation = "Phenolphthalein undergoes resonance deprotonation to form a pink dianion chromophore in alkaline pH.",
            difficulty = "Easy",
            category = "Acids & Bases"
        ),
        QuizQuestion(
            id = 3,
            question = "In the reaction: Zn + 2HCl → ZnCl₂ + H₂, what gas is liberated?",
            type = QuizType.REACTION_IDENTIFY,
            options = listOf("Oxygen (O₂)", "Carbon Dioxide (CO₂)", "Hydrogen (H₂)", "Chlorine (Cl₂)"),
            correctIndex = 2,
            explanation = "Zinc displaces hydrogen ions from hydrochloric acid, generating colorless, flammable hydrogen gas.",
            difficulty = "Easy",
            category = "Chemical Reactions"
        ),
        QuizQuestion(
            id = 4,
            question = "True or False: When diluting a concentrated acid, you should always add water directly into the acid.",
            type = QuizType.TRUE_FALSE,
            options = listOf("True", "False"),
            correctIndex = 1,
            explanation = "FALSE! Always add Acid to Water ('AAA: Always Add Acid'). Adding water to acid generates sudden violent boiling that can spray corrosive acid.",
            difficulty = "Medium",
            category = "Lab Safety"
        ),
        QuizQuestion(
            id = 5,
            question = "Which stoichiometric coefficients balance the combustion of propane: _ C₃H₈ + _ O₂ → _ CO₂ + _ H₂O?",
            type = QuizType.EQUATION_BALANCE,
            options = listOf("1, 5, 3, 4", "1, 3, 3, 4", "2, 7, 6, 8", "1, 4, 3, 4"),
            correctIndex = 0,
            explanation = "C₃H₈ + 5O₂ → 3CO₂ + 4H₂O has 3 Carbons, 8 Hydrogens, and 10 Oxygens on both reactant and product sides.",
            difficulty = "Medium",
            category = "Stoichiometry"
        ),
        QuizQuestion(
            id = 6,
            question = "What is the flame test emission color of Sodium (Na⁺) ions?",
            type = QuizType.MULTIPLE_CHOICE,
            options = listOf("Lilac / Violet", "Persistent Golden Yellow", "Brick Red", "Emerald Green"),
            correctIndex = 1,
            explanation = "Sodium atoms emit strong atomic resonance radiation at 589.0 nm and 589.6 nm (yellow sodium D-lines).",
            difficulty = "Easy",
            category = "Inorganic Tests"
        ),
        QuizQuestion(
            id = 7,
            question = "What gas turns limewater [Ca(OH)₂] milky and cloudy?",
            type = QuizType.MULTIPLE_CHOICE,
            options = listOf("Carbon Dioxide (CO₂)", "Nitrogen (N₂)", "Sulfur Dioxide (SO₂)", "Methane (CH₄)"),
            correctIndex = 0,
            explanation = "CO₂ reacts with calcium hydroxide to precipitate insoluble white calcium carbonate (CaCO₃↓).",
            difficulty = "Easy",
            category = "Gas Identification"
        ),
        QuizQuestion(
            id = 8,
            question = "What is the pH of a neutral solution at 25°C?",
            type = QuizType.MULTIPLE_CHOICE,
            options = listOf("0", "1", "7", "14"),
            correctIndex = 2,
            explanation = "At 25°C, [H⁺] = [OH⁻] = 10⁻⁷ M, therefore pH = -log(10⁻⁷) = 7.0.",
            difficulty = "Easy",
            category = "Physical Chemistry"
        ),
        QuizQuestion(
            id = 9,
            question = "Which type of reaction occurs when AgNO₃(aq) and NaCl(aq) form AgCl(s) solid precipitate?",
            type = QuizType.REACTION_IDENTIFY,
            options = listOf("Decomposition", "Double Displacement (Precipitation)", "Combination", "Combustion"),
            correctIndex = 1,
            explanation = "Ag⁺ and Na⁺ swap anions (Cl⁻ and NO₃⁻), forming an insoluble salt AgCl.",
            difficulty = "Medium",
            category = "Reactions"
        ),
        QuizQuestion(
            id = 10,
            question = "Which apparatus is used to accurately measure out a fixed specific volume of liquid (e.g. exactly 20.0 mL or 25.0 mL)?",
            type = QuizType.APPARATUS_IDENTIFY,
            options = listOf("Beaker", "Volumetric Pipette", "Watch Glass", "Dropper"),
            correctIndex = 1,
            explanation = "A volumetric pipette has a calibration ring calibrated for high precision transfer of a single fixed volume.",
            difficulty = "Medium",
            category = "Laboratory Apparatus"
        )
    )
}
