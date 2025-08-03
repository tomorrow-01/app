package com.lawyerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.lawyerapp.presentation.screen.LawyerSearchScreen
import com.lawyerapp.presentation.viewmodel.LawyerViewModel
import com.lawyerapp.ui.theme.LawyerAppTheme

class MainActivity : ComponentActivity() {
    private val lawyerViewModel: LawyerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            LawyerAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        LawyerSearchScreen(viewModel = lawyerViewModel)
                    }
                }
            }
        }
    }
}
