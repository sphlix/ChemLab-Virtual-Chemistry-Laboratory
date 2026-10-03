package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ui.screens.ChemistryCalculatorScreen
import com.example.ui.screens.ExperimentLibraryScreen
import com.example.ui.screens.GuidedExperimentScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NotesScreen
import com.example.ui.screens.PeriodicTableScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.ReactionSimulatorScreen
import com.example.ui.screens.SafetyScreen
import com.example.ui.screens.TitrationScreen
import com.example.ui.screens.VirtualLabScreen

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Experiments : Screen("experiments", "Experiments", Icons.Default.Science)
    object VirtualLab : Screen("virtual_lab", "Lab", Icons.Default.WaterDrop)
    object Reactions : Screen("reactions", "Reactions", Icons.Default.Science)
    object Calculator : Screen("calculator", "Calculator", Icons.Default.Calculate)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChemLabApp(
    viewModel: ChemLabViewModel,
    modifier: Modifier = Modifier
) {
    val currentRoute by viewModel.currentRoute.collectAsState()

    val navItems = listOf(
        Screen.Home,
        Screen.Experiments,
        Screen.VirtualLab,
        Screen.Reactions,
        Screen.Calculator
    )

    fun navigateTo(route: String) {
        viewModel.navigateTo(route)
    }

    fun navigateBack() {
        viewModel.navigateBack()
    }

    // Hardware/System back button handling
    if (currentRoute != "home") {
        BackHandler {
            navigateBack()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = when (currentRoute) {
                                "home" -> "ChemLab"
                                "experiments" -> "Experiment Library"
                                "guided_experiment" -> "Guided Experiment"
                                "virtual_lab" -> "Virtual Laboratory"
                                "reactions" -> "Reaction Simulator"
                                "titration" -> "Titration Simulator"
                                "calculator" -> "Chemical Calculator"
                                "periodic_table" -> "Periodic Table"
                                "notes" -> "Chemistry Notes"
                                "quiz" -> "Chemistry Quiz"
                                "safety" -> "Lab Safety Center"
                                "profile" -> "Student Profile"
                                else -> "ChemLab"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (currentRoute == "home") {
                            Text(
                                text = "Virtual Chemistry Lab",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                navigationIcon = {
                    if (currentRoute != "home") {
                        IconButton(
                            onClick = { navigateBack() },
                            modifier = Modifier.testTag("top_bar_back_btn")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        IconButton(onClick = {}) {
                            Text("🧪", fontSize = 24.sp)
                        }
                    }
                },
                actions = {
                    if (currentRoute != "home") {
                        IconButton(
                            onClick = { navigateTo("home") },
                            modifier = Modifier.testTag("top_bar_home_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    IconButton(
                        onClick = { navigateTo("profile") },
                        modifier = Modifier.testTag("top_bar_profile_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = if (currentRoute == "profile") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.testTag("main_bottom_nav")
            ) {
                navItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = { navigateTo(screen.route) },
                        modifier = Modifier.testTag("nav_${screen.route}")
                    )
                }
            }
        }
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)

        when (currentRoute) {
            "home" -> HomeScreen(
                viewModel = viewModel,
                onNavigate = { navigateTo(it) },
                onStartExperiment = { exp ->
                    viewModel.startGuidedExperiment(exp)
                    navigateTo("guided_experiment")
                },
                modifier = screenModifier
            )

            "experiments" -> ExperimentLibraryScreen(
                viewModel = viewModel,
                onStartExperiment = { exp ->
                    viewModel.startGuidedExperiment(exp)
                    navigateTo("guided_experiment")
                },
                modifier = screenModifier
            )

            "guided_experiment" -> GuidedExperimentScreen(
                viewModel = viewModel,
                onBack = { navigateBack() },
                modifier = screenModifier
            )

            "virtual_lab" -> VirtualLabScreen(
                viewModel = viewModel,
                modifier = screenModifier
            )

            "reactions" -> ReactionSimulatorScreen(
                viewModel = viewModel,
                modifier = screenModifier
            )

            "titration" -> TitrationScreen(
                viewModel = viewModel,
                modifier = screenModifier
            )

            "calculator" -> ChemistryCalculatorScreen(
                viewModel = viewModel,
                modifier = screenModifier
            )

            "periodic_table" -> PeriodicTableScreen(
                viewModel = viewModel,
                modifier = screenModifier
            )

            "notes" -> NotesScreen(
                viewModel = viewModel,
                modifier = screenModifier
            )

            "quiz" -> QuizScreen(
                viewModel = viewModel,
                modifier = screenModifier
            )

            "safety" -> SafetyScreen(
                viewModel = viewModel,
                modifier = screenModifier
            )

            "profile" -> ProfileScreen(
                viewModel = viewModel,
                modifier = screenModifier
            )

            else -> HomeScreen(
                viewModel = viewModel,
                onNavigate = { navigateTo(it) },
                onStartExperiment = { exp ->
                    viewModel.startGuidedExperiment(exp)
                    navigateTo("guided_experiment")
                },
                modifier = screenModifier
            )
        }
    }
}
