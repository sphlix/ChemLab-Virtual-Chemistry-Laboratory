package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.local.ChemLabDatabase
import com.example.data.repository.ChemLabRepository
import com.example.ui.ChemLabApp
import com.example.ui.ChemLabViewModel
import com.example.ui.theme.ChemLabTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ChemLabViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val db = ChemLabDatabase.getDatabase(applicationContext)
                val repository = ChemLabRepository(db.userDao())
                return ChemLabViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChemLabTheme {
                ChemLabApp(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
