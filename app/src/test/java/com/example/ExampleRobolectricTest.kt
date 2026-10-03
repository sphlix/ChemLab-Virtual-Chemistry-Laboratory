package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.ChemLabDatabase
import com.example.data.repository.ChemLabRepository
import com.example.ui.ChemLabApp
import com.example.ui.ChemLabViewModel
import com.example.ui.theme.ChemLabTheme
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

import androidx.compose.ui.test.performScrollTo

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("ChemLab", appName)
    }

    @Test
    fun `initialize database and repository`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = ChemLabDatabase.getDatabase(context)
        val repository = ChemLabRepository(db.userDao())
        repository.initializeProgressIfEmpty()
        val progress = repository.userProgress.firstOrNull()
        assertNotNull(progress)
    }

    @Test
    fun `test click navigation and buttons`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = ChemLabDatabase.getDatabase(context)
        val repository = ChemLabRepository(db.userDao())
        val viewModel = ChemLabViewModel(repository)

        composeTestRule.setContent {
            ChemLabTheme {
                ChemLabApp(viewModel = viewModel)
            }
        }

        // Verify Home is displayed
        composeTestRule.onNodeWithTag("home_screen_column").assertIsDisplayed()

        // 1. Click "Enter Virtual Lab" button in Hero Banner
        composeTestRule.onNodeWithTag("open_virtual_lab_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("virtual_lab_screen").assertIsDisplayed()

        // Test buttons inside Virtual Lab: Pour and Bunsen Heat
        composeTestRule.onNodeWithTag("pour_chemical_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("toggle_bunsen_btn").performClick()
        composeTestRule.waitForIdle()

        // Click Back to Home
        composeTestRule.onNodeWithTag("top_bar_back_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("home_screen_column").assertIsDisplayed()

        // 2. Click Bottom Navigation: Reactions
        composeTestRule.onNodeWithTag("nav_reactions").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("reaction_simulator_screen").assertIsDisplayed()

        // Test reactant selection button in simulator
        composeTestRule.onNodeWithTag("select_sim_a_Zn").performClick()
        composeTestRule.waitForIdle()

        // Click Home button in Top Bar
        composeTestRule.onNodeWithTag("top_bar_home_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("home_screen_column").assertIsDisplayed()

        // 3. Click Bottom Navigation: Experiments
        composeTestRule.onNodeWithTag("nav_experiments").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("experiment_library_screen").assertIsDisplayed()

        // Click on an experiment card to start guided experiment
        composeTestRule.onNodeWithTag("start_exp_exp_acid_base").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("guided_experiment_screen").assertIsDisplayed()

        // Click Guided Next Step
        composeTestRule.onNodeWithTag("guided_next_btn").performClick()
        composeTestRule.waitForIdle()

        // Click Back button
        composeTestRule.onNodeWithTag("top_bar_back_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("experiment_library_screen").assertIsDisplayed()

        // 4. Click Bottom Navigation: Calculator
        composeTestRule.onNodeWithTag("nav_calculator").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("chemistry_calculator_screen").assertIsDisplayed()

        // Click Calculate button
        composeTestRule.onNodeWithTag("calc_submit_btn").performClick()
        composeTestRule.waitForIdle()

        // Click Profile button in Top Bar
        composeTestRule.onNodeWithTag("top_bar_profile_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("profile_screen").assertIsDisplayed()

        // 5. Navigate back Home and test Quick Action Cards
        composeTestRule.onNodeWithTag("top_bar_home_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("home_screen_column").assertIsDisplayed()

        // Click Quick Action: Experiments
        composeTestRule.onNodeWithTag("quick_action_experiments").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("experiment_library_screen").assertIsDisplayed()

        // Return Home
        composeTestRule.onNodeWithTag("top_bar_home_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("home_screen_column").assertIsDisplayed()

        // Click Streak Badge to navigate to Profile
        composeTestRule.onNodeWithTag("home_streak_badge").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("profile_screen").assertIsDisplayed()
    }
}
