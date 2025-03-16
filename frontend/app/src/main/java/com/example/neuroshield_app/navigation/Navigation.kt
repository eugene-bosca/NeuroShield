package com.example.neuroshield_app.navigation

import RunTestsScreen
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
            onClickUserInfo = {navController.navigate(route = UserInfoPage)},
            onClickRunTests = {navController.navigate(route = RunTestsPage)}
        ) }
        composable<HistoryPage> { HistoryPageScreen(
            onClickHomePage = {navController.navigate(route = HomePage)},
            onClickResultsPage = { patientId -> navController.navigate("results/$patientId")}
        ) }
        composable<UserInfoPage> { UserInfoScreen(
            onClickHomePage = {navController.navigate(route = HomePage)},
            onClickEyeAlignment = {navController.navigate(route = EyeAlignmentPage)}
        ) }
        composable<EyeAlignmentPage> { EyeAlignmentScreen(
            onClickRunTestsPage = {navController.navigate(route = RunTestsPage)},
            onClickUserInfoPage = {navController.navigate(route = UserInfoPage)}
        )}
        composable<RunTestsPage> { RunTestsScreen(
            onClickResultsPage = {navController.navigate(route = ResultsPage)},
            onClickEyeAlignment = {navController.navigate(route = EyeAlignmentPage)}
        )}
        composable("results/{patientId}") { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            ResultsScreen(
                patientId = patientId,
                onClickHomePage = { navController.navigate(HomePage) }
            )
        }
    }
}
