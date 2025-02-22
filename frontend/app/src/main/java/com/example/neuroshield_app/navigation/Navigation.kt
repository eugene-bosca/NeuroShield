package com.example.neuroshield_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.neuroshield_app.presentation.screens.history.HistoryPageScreen
import com.example.neuroshield_app.presentation.screens.home.HomePageScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomePage) {
        composable<HomePage> { HomePageScreen(
            onClickHistory = {navController.navigate(route = HistoryPage)}
        ) }
        composable<HistoryPage> { HistoryPageScreen(
            onClickHomePage = {navController.navigate(route = HomePage)},
        ) }
    }
}
