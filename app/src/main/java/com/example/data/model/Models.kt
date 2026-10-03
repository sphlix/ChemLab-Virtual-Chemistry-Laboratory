package com.example.data.model

data class Experiment(
    val id: String,
    val title: String,
    val category: ExperimentCategory,
    val difficulty: String, // Beginner, Intermediate, Advanced
    val estimatedTime: String,
    val chemicals: List<String>,
    val apparatus: List<String>,
    val description: String,
    val steps: List<ExperimentStep>
)

enum class ExperimentCategory(val title: String, val icon: String) {
    BASIC("Basic Chemistry", "🧪"),
    INORGANIC("Inorganic Chemistry", "⚗️"),
    ORGANIC("Organic Chemistry", "🧬"),
    PHYSICAL("Physical Chemistry", "🌡️")
}

data class ExperimentStep(
    val stepNumber: Int,
    val title: String,
    val instruction: String,
    val whyExplanation: String,
    val apparatusInUse: String,
    val reagentsInUse: String,
    val equation: String = "",
    val observation: String,
    val visualType: StepVisualType = StepVisualType.POUR_MIX
)

enum class StepVisualType {
    COLLECT_APPARATUS,
    MEASURE_LIQUID,
    POUR_MIX,
    ADD_INDICATOR,
    HEAT_BUNSEN,
    TITRATE,
    FILTER_PRECIPITATE,
    OBSERVE_FLAME,
    GAS_COLLECT
}

data class Chemical(
    val formula: String,
    val name: String,
    val state: String, // Liquid, Solid, Solution, Gas
    val color: Long, // Color ARGB long
    val pH: Float? = null,
    val hazard: String = "None",
    val hazardSymbol: String = "⚠️",
    val molarMass: Double = 0.0,
    val isFlammable: Boolean = false,
    val isCorrosive: Boolean = false
)

data class ReactionResult(
    val reactants: List<String>,
    val products: List<String>,
    val balancedEquation: String,
    val reactionType: String,
    val observableChange: String,
    val explanation: String,
    val temperatureDeltaCelsius: Float, // +ve exothermic, -ve endothermic
    val resultingColor: Long,
    val hasGasBubbles: Boolean,
    val hasPrecipitate: Boolean,
    val precipitateColor: Long? = null,
    val safetyWarning: String? = null
)

data class PeriodicElement(
    val number: Int,
    val symbol: String,
    val name: String,
    val atomicMass: Double,
    val category: ElementCategory,
    val group: Int,
    val period: Int,
    val block: String,
    val electronConfig: String,
    val valency: String,
    val meltingPointC: String,
    val boilingPointC: String,
    val densityGcm3: String,
    val commonUses: String,
    val commonCompounds: List<String>,
    val shells: List<Int>
)

enum class ElementCategory(val label: String, val colorHex: Long) {
    ALKALI_METAL("Alkali Metal", 0xFFEF4444),
    ALKALINE_EARTH("Alkaline Earth", 0xFFF97316),
    TRANSITION_METAL("Transition Metal", 0xFFF59E0B),
    POST_TRANSITION("Post-transition Metal", 0xFF10B981),
    METALLOID("Metalloid", 0xFF06B6D4),
    NON_METAL("Reactive Non-metal", 0xFF3B82F6),
    HALOGEN("Halogen", 0xFF8B5CF6),
    NOBLE_GAS("Noble Gas", 0xFFEC4899),
    LANTHANIDE("Lanthanide", 0xFF14B8A6),
    ACTINIDE("Actinide", 0xFF6366F1)
}

data class ChemistryNote(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val keyPoints: List<String>,
    val importantFormulas: List<String>,
    val chemicalEquations: List<String>,
    val examTips: List<String>,
    val simpleExplanation: String,
    val realWorldExample: String,
    val quickQuiz: NoteQuizQuestion
)

data class NoteQuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val type: QuizType,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val difficulty: String,
    val category: String
)

enum class QuizType {
    MULTIPLE_CHOICE,
    TRUE_FALSE,
    APPARATUS_IDENTIFY,
    REACTION_IDENTIFY,
    EQUATION_BALANCE
}

data class SafetyTopic(
    val id: String,
    val title: String,
    val iconEmoji: String,
    val category: String,
    val guidelines: List<String>,
    val doList: List<String>,
    val dontList: List<String>,
    val emergencyAction: String
)
