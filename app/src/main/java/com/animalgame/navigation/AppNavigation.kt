package com.animalgame.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.animalgame.ui.components.BottomNavigationBar
import com.animalgame.ui.screens.*
import com.animalgame.ui.viewmodel.MainViewModel

@Composable
fun AppNavigation(
    mainViewModel: MainViewModel = viewModel()
) {
    val selectedTab by mainViewModel.selectedTab.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                currentRoute = selectedTab,
                onNavigate = { route ->
                    mainViewModel.selectTab(route)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                "home" -> HomeScreen(
                    viewModel = mainViewModel,
                    onNavigateToRegistrar = { mainViewModel.selectTab("registrar") },
                    onNavigateToResultados = { mainViewModel.selectTab("bicho") }
                )
                "bicho" -> JogoDoBichoScreen(
                    viewModel = mainViewModel
                )
                "registrar" -> RegistrarJogoScreen(
                    viewModel = mainViewModel
                )
                "profile" -> ProfileScreen(
                    viewModel = mainViewModel
                )
            }
        }
    }
}
