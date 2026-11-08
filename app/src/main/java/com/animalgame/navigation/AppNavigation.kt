package com.animalgame.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                        popUpTo(navController.graph.startDestinationId) {
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
                MarketScreen(
                    viewModel = mainViewModel,
                    onCryptoSelected = { crypto ->
                        navController.navigate("detail/${crypto.id}")
                    }
                )
            }

            composable("bicho") {
                JogoDoBichoScreen(
                    viewModel = mainViewModel
                )
            }

            composable("explorer") {
                ExplorerScreen(
                    viewModel = mainViewModel,
                    onCryptoSelected = { crypto ->
                        navController.navigate("detail/${crypto.id}")
                    }
                )
            }

            composable("bookmark") {
                BookmarkScreen(
                    viewModel = mainViewModel,
                    onCryptoSelected = { crypto ->
                        navController.navigate("detail/${crypto.id}")
                    }
                )
            }

            composable("chart") {
                ChartScreen(
                    viewModel = mainViewModel,
                    onCryptoSelected = { crypto ->
                        navController.navigate("detail/${crypto.id}")
                    }
                )
            }

            composable("profile") {
                ProfileScreen()
            }

            composable(
                route = "detail/{cryptoId}",
                arguments = listOf(
                    navArgument("cryptoId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val cryptoId = backStackEntry.arguments?.getString("cryptoId") ?: "btc"
                DetailScreen(
                    viewModel = mainViewModel,
                    cryptoId = cryptoId,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
