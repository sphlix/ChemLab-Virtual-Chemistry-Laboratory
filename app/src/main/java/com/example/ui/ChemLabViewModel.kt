package com.example.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BadgeEntity
import com.example.data.local.CompletedExperimentEntity
import com.example.data.local.LabReportEntity
import com.example.data.local.UserProgressEntity
import com.example.data.model.Chemical
import com.example.data.model.ChemistryNote
import com.example.data.model.ElementCategory
import com.example.data.model.Experiment
import com.example.data.model.ExperimentCategory
import com.example.data.model.PeriodicElement
import com.example.data.model.QuizQuestion
import com.example.data.model.ReactionResult
import com.example.data.model.SafetyTopic
import com.example.data.repository.CalculationResult
import com.example.data.repository.ChemLabRepository
import com.example.data.repository.ChemicalsData
import com.example.data.repository.ChemistryCalculatorEngine
import com.example.data.repository.ExperimentsData
import com.example.data.repository.NotesData
import com.example.data.repository.PeriodicTableData
import com.example.data.repository.QuizData
import com.example.data.repository.ReactionsEngine
import com.example.data.repository.SafetyData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow

class ChemLabViewModel(
    private val repository: ChemLabRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.initializeProgressIfEmpty()
        }
    }

    // NAVIGATION STATE (Single source of truth)
    private val _currentRoute = MutableStateFlow("home")
    val currentRoute: StateFlow<String> = _currentRoute.asStateFlow()

    private val _previousRoute = MutableStateFlow("home")
    val previousRoute: StateFlow<String> = _previousRoute.asStateFlow()

    fun navigateTo(route: String) {
        if (_currentRoute.value != route) {
            _previousRoute.value = _currentRoute.value
            _currentRoute.value = route
        }
    }

    fun navigateBack() {
        val curr = _currentRoute.value
        _currentRoute.value = when (curr) {
            "guided_experiment" -> "experiments"
            "home" -> "home"
            else -> _previousRoute.value.ifEmpty { "home" }
        }
    }

    // Database Flows
    val userProgress: StateFlow<UserProgressEntity?> = repository.userProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProgressEntity())

    val completedExperiments: StateFlow<List<CompletedExperimentEntity>> = repository.completedExperiments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unlockedBadges: StateFlow<List<BadgeEntity>> = repository.unlockedBadges
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val labReports: StateFlow<List<LabReportEntity>> = repository.labReports
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // HOME DASHBOARD STATE
    private val _homeSearchQuery = MutableStateFlow("")
    val homeSearchQuery: StateFlow<String> = _homeSearchQuery.asStateFlow()

    fun setHomeSearchQuery(query: String) {
        _homeSearchQuery.value = query
    }

    // EXPERIMENTS LIBRARY
    private val _selectedExpCategory = MutableStateFlow<ExperimentCategory?>(null)
    val selectedExpCategory: StateFlow<ExperimentCategory?> = _selectedExpCategory.asStateFlow()

    fun selectExperimentCategory(category: ExperimentCategory?) {
        _selectedExpCategory.value = category
    }

    // GUIDED EXPERIMENT MODE
    private val _currentGuidedExperiment = MutableStateFlow<Experiment?>(ExperimentsData.allExperiments.first())
    val currentGuidedExperiment: StateFlow<Experiment?> = _currentGuidedExperiment.asStateFlow()

    private val _currentStepIndex = MutableStateFlow(0)
    val currentStepIndex: StateFlow<Int> = _currentStepIndex.asStateFlow()

    fun startGuidedExperiment(exp: Experiment) {
        _currentGuidedExperiment.value = exp
        _currentStepIndex.value = 0
    }

    fun nextStep() {
        val exp = _currentGuidedExperiment.value ?: return
        if (_currentStepIndex.value < exp.steps.size - 1) {
            _currentStepIndex.value += 1
        } else {
            // Completed!
            viewModelScope.launch {
                repository.completeExperiment(exp.id, exp.title)
            }
        }
    }

    fun prevStep() {
        if (_currentStepIndex.value > 0) {
            _currentStepIndex.value -= 1
        }
    }

    // INTERACTIVE VIRTUAL LAB STATE
    data class VirtualLabState(
        val selectedApparatus: String = "Conical Flask",
        val addedChemicals: List<Chemical> = listOf(ChemicalsData.allChemicals[0]), // HCl
        val volumeMl: Float = 30f,
        val temperatureCelsius: Float = 25.0f,
        val isBunsenHeating: Boolean = false,
        val liquidColorArgb: Long = 0x3338BDF8,
        val isBubbling: Boolean = false,
        val hasPrecipitate: Boolean = false,
        val precipitateColorArgb: Long? = null,
        val currentPh: Float = 1.0f,
        val currentMassGrams: Float = 78.4f,
        val equationResult: String = "",
        val reactionExplanation: String = "Test chemicals or turn on Bunsen heating to observe thermal and chemical interactions.",
        val recordedNotes: String = ""
    )

    private val _virtualLabState = MutableStateFlow(VirtualLabState())
    val virtualLabState: StateFlow<VirtualLabState> = _virtualLabState.asStateFlow()

    fun selectApparatus(name: String) {
        _virtualLabState.value = _virtualLabState.value.copy(selectedApparatus = name)
    }

    fun addChemicalToLab(chemical: Chemical) {
        val current = _virtualLabState.value
        val newChems = current.addedChemicals + chemical
        val newVolume = (current.volumeMl + 15f).coerceAtMost(100f)

        // If 2 chemicals exist, trigger reaction
        var reactionResult: ReactionResult? = null
        if (newChems.size >= 2) {
            val chemA = newChems[newChems.size - 2].formula
            val chemB = newChems.last().formula
            reactionResult = ReactionsEngine.simulateReaction(chemA, chemB)
        }

        val newTemp = if (reactionResult != null) {
            current.temperatureCelsius + reactionResult.temperatureDeltaCelsius
        } else current.temperatureCelsius

        val newColor = reactionResult?.resultingColor ?: chemical.color
        val hasBubbles = reactionResult?.hasGasBubbles ?: false
        val hasPrecipitate = reactionResult?.hasPrecipitate ?: false
        val precipitateColor = reactionResult?.precipitateColor

        val newPh = chemical.pH ?: current.currentPh
        val newMass = current.currentMassGrams + 15.0f

        _virtualLabState.value = current.copy(
            addedChemicals = newChems,
            volumeMl = newVolume,
            temperatureCelsius = newTemp,
            liquidColorArgb = newColor,
            isBubbling = hasBubbles,
            hasPrecipitate = hasPrecipitate,
            precipitateColorArgb = precipitateColor,
            currentPh = newPh,
            currentMassGrams = newMass,
            equationResult = reactionResult?.balancedEquation ?: "",
            reactionExplanation = reactionResult?.explanation ?: "Added ${chemical.name} (${chemical.formula})."
        )
    }

    fun toggleBunsenHeating() {
        val current = _virtualLabState.value
        val newState = !current.isBunsenHeating
        val targetTemp = if (newState) (current.temperatureCelsius + 35f).coerceAtMost(98f) else 25.0f
        _virtualLabState.value = current.copy(
            isBunsenHeating = newState,
            temperatureCelsius = targetTemp,
            isBubbling = newState || current.isBubbling
        )
    }

    fun resetVirtualLab() {
        _virtualLabState.value = VirtualLabState()
    }

    fun saveLabObservation(observations: String) {
        val state = _virtualLabState.value
        val chemsStr = state.addedChemicals.joinToString(", ") { it.formula }
        viewModelScope.launch {
            repository.saveLabReport(
                title = "Lab Experiment (${state.selectedApparatus})",
                chemicals = chemsStr,
                observations = observations,
                result = "Temp: ${state.temperatureCelsius}°C, pH: ${state.currentPh}"
            )
        }
    }

    // CHEMICAL REACTION SIMULATOR
    private val _simChemicalA = MutableStateFlow<Chemical?>(ChemicalsData.findChemical("HCl"))
    val simChemicalA: StateFlow<Chemical?> = _simChemicalA.asStateFlow()

    private val _simChemicalB = MutableStateFlow<Chemical?>(ChemicalsData.findChemical("NaOH"))
    val simChemicalB: StateFlow<Chemical?> = _simChemicalB.asStateFlow()

    private val _simReactionResult = MutableStateFlow<ReactionResult?>(
        ReactionsEngine.simulateReaction("HCl", "NaOH")
    )
    val simReactionResult: StateFlow<ReactionResult?> = _simReactionResult.asStateFlow()

    fun setSimChemicalA(chem: Chemical) {
        _simChemicalA.value = chem
        triggerSimReaction()
    }

    fun setSimChemicalB(chem: Chemical) {
        _simChemicalB.value = chem
        triggerSimReaction()
    }

    private fun triggerSimReaction() {
        val a = _simChemicalA.value?.formula ?: return
        val b = _simChemicalB.value?.formula ?: return
        val res = ReactionsEngine.simulateReaction(a, b)
        _simReactionResult.value = res
        viewModelScope.launch {
            repository.addXp(10)
        }
    }

    // TITRATION SIMULATOR
    data class TitrationState(
        val titrant: String = "HCl (0.10 M)",
        val analyte: String = "NaOH (Unknown M)",
        val analyteVolumeMl: Float = 20.0f,
        val indicator: String = "Phenolphthalein",
        val buretteAddedMl: Float = 0.0f,
        val currentPh: Float = 13.0f,
        val isEndpointReached: Boolean = false,
        val flaskColor: Long = 0xFFEC4899, // Bright pink
        val phCurvePoints: List<Pair<Float, Float>> = listOf(0.0f to 13.0f),
        val calculatedMolarity: Double? = null,
        val isDripping: Boolean = false
    )

    private val _titrationState = MutableStateFlow(TitrationState())
    val titrationState: StateFlow<TitrationState> = _titrationState.asStateFlow()

    fun selectTitrationIndicator(indicator: String) {
        val current = _titrationState.value
        val color = when (indicator) {
            "Phenolphthalein" -> if (current.currentPh > 8.2) 0xFFEC4899 else 0x1538BDF8
            "Methyl Orange" -> if (current.currentPh < 3.1) 0xFFEF4444 else 0xFFF59E0B
            else -> 0xFF10B981 // Universal / Bromothymol
        }
        _titrationState.value = current.copy(indicator = indicator, flaskColor = color)
    }

    fun addTitrant(volumeToAddMl: Float) {
        val current = _titrationState.value
        val newAdded = (current.buretteAddedMl + volumeToAddMl).coerceAtMost(50.0f)

        // Equivalence volume is around 20.0 mL (0.10M HCl with 20mL of 0.10M NaOH)
        val equivalenceV = 20.0f
        val newPh = when {
            newAdded < equivalenceV - 0.5f -> {
                // Alkaline region: unneutralized OH-
                val remainingM = (0.1 * (equivalenceV - newAdded)) / (current.analyteVolumeMl + newAdded)
                val poh = -log10(remainingM.coerceAtLeast(1e-7))
                (14.0 - poh).toFloat().coerceIn(7.5f, 13.0f)
            }
            newAdded in (equivalenceV - 0.5f)..(equivalenceV + 0.5f) -> {
                // Sharp steep drop at equivalence point
                val t = (newAdded - (equivalenceV - 0.5f)) / 1.0f
                (10.5f - t * 7.0f).coerceIn(3.5f, 10.5f)
            }
            else -> {
                // Acidic excess
                val excessM = (0.1 * (newAdded - equivalenceV)) / (current.analyteVolumeMl + newAdded)
                val ph = -log10(excessM.coerceAtLeast(1e-7))
                ph.toFloat().coerceIn(1.2f, 3.5f)
            }
        }

        val endpointReached = newAdded >= equivalenceV
        val newColor = when (current.indicator) {
            "Phenolphthalein" -> if (newPh > 8.2f) 0xFFEC4899 else 0x1538BDF8 // Pink to colorless!
            "Methyl Orange" -> if (newPh < 3.8f) 0xFFEF4444 else 0xFFF59E0B // Yellow to red!
            else -> if (newPh < 7.0f) 0xFFF59E0B else 0xFF0284C7
        }

        val newPoints = current.phCurvePoints + (newAdded to newPh)
        val calcM = if (endpointReached) (0.10 * equivalenceV) / current.analyteVolumeMl else null

        if (endpointReached && !current.isEndpointReached) {
            viewModelScope.launch {
                repository.addXp(50)
            }
        }

        _titrationState.value = current.copy(
            buretteAddedMl = newAdded,
            currentPh = newPh,
            isEndpointReached = endpointReached,
            flaskColor = newColor,
            phCurvePoints = newPoints,
            calculatedMolarity = calcM,
            isDripping = volumeToAddMl > 0f
        )
    }

    fun resetTitration() {
        _titrationState.value = TitrationState()
    }

    // CALCULATOR STATE
    private val _calculatorType = MutableStateFlow("Molar Mass")
    val calculatorType: StateFlow<String> = _calculatorType.asStateFlow()

    private val _calcResult = MutableStateFlow<CalculationResult?>(
        ChemistryCalculatorEngine.calculateMolarMass("H2SO4")
    )
    val calcResult: StateFlow<CalculationResult?> = _calcResult.asStateFlow()

    fun setCalculatorType(type: String) {
        _calculatorType.value = type
    }

    fun computeMolarMass(formula: String) {
        _calcResult.value = ChemistryCalculatorEngine.calculateMolarMass(formula)
    }

    fun computeMoles(mass: Double, molarMass: Double) {
        _calcResult.value = ChemistryCalculatorEngine.calculateMoles(mass, molarMass)
    }

    fun computeMolarity(moles: Double, volumeMl: Double) {
        _calcResult.value = ChemistryCalculatorEngine.calculateMolarity(moles, volumeMl)
    }

    fun computeNormality(molarity: Double, factor: Int) {
        _calcResult.value = ChemistryCalculatorEngine.calculateNormality(molarity, factor)
    }

    fun computeDilution(m1: Double, v1: Double, m2: Double?, v2: Double?) {
        _calcResult.value = ChemistryCalculatorEngine.calculateDilution(m1, v1, m2, v2)
    }

    fun computePh(hConc: Double) {
        _calcResult.value = ChemistryCalculatorEngine.calculatePhFromH(hConc)
    }

    fun computeGasLaw(moles: Double, tempK: Double, volumeL: Double) {
        _calcResult.value = ChemistryCalculatorEngine.calculateGasPressure(moles, tempK, volumeL)
    }

    fun computeDensity(mass: Double, volume: Double) {
        _calcResult.value = ChemistryCalculatorEngine.calculateDensity(mass, volume)
    }

    // PERIODIC TABLE STATE
    private val _periodicSearchQuery = MutableStateFlow("")
    val periodicSearchQuery: StateFlow<String> = _periodicSearchQuery.asStateFlow()

    private val _selectedElementCategory = MutableStateFlow<ElementCategory?>(null)
    val selectedElementCategory: StateFlow<ElementCategory?> = _selectedElementCategory.asStateFlow()

    private val _selectedElement = MutableStateFlow<PeriodicElement?>(PeriodicTableData.elements[0])
    val selectedElement: StateFlow<PeriodicElement?> = _selectedElement.asStateFlow()

    fun setPeriodicSearchQuery(q: String) {
        _periodicSearchQuery.value = q
    }

    fun setElementCategoryFilter(cat: ElementCategory?) {
        _selectedElementCategory.value = cat
    }

    fun selectElement(element: PeriodicElement) {
        _selectedElement.value = element
        viewModelScope.launch { repository.addXp(5) }
    }

    // QUIZ STATE
    private val _currentQuizIndex = MutableStateFlow(0)
    val currentQuizIndex: StateFlow<Int> = _currentQuizIndex.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    private val _selectedQuizOption = MutableStateFlow<Int?>(null)
    val selectedQuizOption: StateFlow<Int?> = _selectedQuizOption.asStateFlow()

    private val _isAnswerSubmitted = MutableStateFlow(false)
    val isAnswerSubmitted: StateFlow<Boolean> = _isAnswerSubmitted.asStateFlow()

    private val _isQuizCompleted = MutableStateFlow(false)
    val isQuizCompleted: StateFlow<Boolean> = _isQuizCompleted.asStateFlow()

    fun selectQuizOption(index: Int) {
        if (!_isAnswerSubmitted.value) {
            _selectedQuizOption.value = index
        }
    }

    fun submitQuizAnswer() {
        val selected = _selectedQuizOption.value ?: return
        val currentQ = QuizData.questions.getOrNull(_currentQuizIndex.value) ?: return
        _isAnswerSubmitted.value = true
        if (selected == currentQ.correctIndex) {
            _quizScore.value += 1
        }
    }

    fun nextQuizQuestion() {
        if (_currentQuizIndex.value < QuizData.questions.size - 1) {
            _currentQuizIndex.value += 1
            _selectedQuizOption.value = null
            _isAnswerSubmitted.value = false
        } else {
            _isQuizCompleted.value = true
            viewModelScope.launch {
                repository.completeQuiz(_quizScore.value, QuizData.questions.size)
            }
        }
    }

    fun restartQuiz() {
        _currentQuizIndex.value = 0
        _quizScore.value = 0
        _selectedQuizOption.value = null
        _isAnswerSubmitted.value = false
        _isQuizCompleted.value = false
    }

    // SAFETY STATE
    private val _selectedSafetyTopic = MutableStateFlow<SafetyTopic?>(SafetyData.topics.first())
    val selectedSafetyTopic: StateFlow<SafetyTopic?> = _selectedSafetyTopic.asStateFlow()

    fun selectSafetyTopic(topic: SafetyTopic) {
        _selectedSafetyTopic.value = topic
    }
}
