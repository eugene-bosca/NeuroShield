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

const val HomePage = "home"
const val HistoryPage = "history"
const val UserInfoPage = "userinfo"

const val EyeAlignmentPage = "eyealignment/{patientId}"
const val RunTestsPage = "runtests/{patientId}"
const val ResultsPage = "results/{patientId}"

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomePage) {
        composable(HomePage) {
            val patientId = "1019656d-b0bc-4a62-8372-8bad580fa7b2"
            HomePageScreen(
                onClickHistory = { navController.navigate(HistoryPage) },
                onClickUserInfo = { navController.navigate(UserInfoPage) },
                onClickRunTestsPage = {
                    navController.navigate("runtests/$patientId")
                }
            )
        }
        composable(HistoryPage) {
            HistoryPageScreen(
                onClickHomePage = { navController.navigate(HomePage) },
                onClickResultsPage = { patientId ->
                    navController.navigate("results/$patientId")
                }
            )
        }
        composable(UserInfoPage) {
            // In UserInfoScreen, assume the patientId is entered or selected.
            // The onClickEyeAlignment callback now takes the patientId and passes it along.
            UserInfoScreen(
                onClickHomePage = { navController.navigate(HomePage) },
                onClickEyeAlignment = { patientId ->
                    navController.navigate("eyealignment/$patientId")
                }
            )
        }
        composable(EyeAlignmentPage) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            EyeAlignmentScreen(
                patientId = patientId,
                onClickRunTestsPage = {
                    navController.navigate("runtests/$patientId")
                },
                onClickUserInfoPage = { navController.navigate(UserInfoPage) }
            )
        }
        composable(RunTestsPage) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            RunTestsScreen(
                patientId = patientId,
                onClickResultsPage = {
                    navController.navigate("results/$patientId")
                },
                onClickEyeAlignment = { navController.navigate("eyealignment/$patientId") }
            )
        }
        composable(ResultsPage) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            ResultsScreen(
                patientId = patientId,
                onClickHomePage = { navController.navigate(HomePage) }
            )
        }
    }
}
