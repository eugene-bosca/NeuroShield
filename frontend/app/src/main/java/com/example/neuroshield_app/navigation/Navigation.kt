package com.example.neuroshield_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.neuroshield_app.presentation.screens.history.HistoryPageScreen
import com.example.neuroshield_app.presentation.screens.home.HomePageScreen
import com.example.neuroshield_app.presentation.screens.userInfo.UserInfoScreen
import com.example.neuroshield_app.presentation.screens.eyeAlignment.EyeAlignmentScreen
import com.example.neuroshield_app.presentation.screens.results.ResultsScreen


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomePage) {
        composable<HomePage> { HomePageScreen(
            onClickHistory = {navController.navigate(route = HistoryPage)},
            onClickUserInfo = {navController.navigate(route = UserInfoPage)}
        ) }
        composable<HistoryPage> { HistoryPageScreen(
            onClickHomePage = {navController.navigate(route = HomePage)},
        ) }
        composable<UserInfoPage> { UserInfoScreen(
            onClickHomePage = {navController.navigate(route = HomePage)},
            onClickEyeAlignment = {navController.navigate(route = EyeAlignmentPage)}
        ) }
        composable<EyeAlignmentPage> { EyeAlignmentScreen(
            onClickResultsPage = {navController.navigate(route = ResultsPage)},
            onClickUserInfoPage = {navController.navigate(route = UserInfoPage)}
        )}
        composable<ResultsPage> { ResultsScreen (
            onClickHomePage = {navController.navigate(route = HomePage)}
        )}
    }
}
