package com.animalgame.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.animalgame.ui.components.BottomNavigationBar
import com.animalgame.ui.screens.*
import com.animalgame.ui.viewmodel.MainViewModel

@Composable
fun AppNavigation(
    mainViewModel: MainViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    viewModel = mainViewModel,
                    onNavigateToRegistrar = {
                        navController.navigate("registrar")
                    },
                    onNavigateToResultados = {
                        navController.navigate("bicho")
                    }
                )
            }

            composable("bicho") {
                JogoDoBichoScreen(
                    viewModel = mainViewModel
                )
            }

            composable("registrar") {
                RegistrarJogoScreen(
                    viewModel = mainViewModel
                )
            }

            composable("profile") {
                ProfileScreen()
            }
        }
    }
}
