import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neuroshield_app.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.neuroshield_app.presentation.screens.runTests.RunTestsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunTestsScreen(
    patientId: String,
    onClickEyeAlignment: (patientId: String) -> Unit,
    onClickResultsPage: (patientId: String) -> Unit,
    runTestsViewModel: RunTestsViewModel = hiltViewModel()
) {
    // Observe loading and error state from the ViewModel
    val isLoading by runTestsViewModel.isLoading.observeAsState(false)
    val errorMessage by runTestsViewModel.error.observeAsState(null)

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Run Tests", fontSize = 28.sp) },
                navigationIcon = {
                    IconButton(onClick = { onClickEyeAlignment(patientId) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color(0xFF84BBD3),
                            contentDescription = "Back",
                            modifier = Modifier.width(32.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Card for Smooth Pursuit Test (Icon + Button)
                Card(
                    modifier = Modifier
                        .width(350.dp)
                        .padding(horizontal = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F7FF)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.eye_tracking_24px),
                            contentDescription = "Smooth Pursuit Test Image",
                            modifier = Modifier
                                .width(128.dp)
                                .height(128.dp)
                        )
                        ElevatedButton(
                            onClick = { runTestsViewModel.onSmoothPursuitButtonClicked() },
                            modifier = Modifier
                                .width(300.dp)
                                .height(80.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84BBD3))
                        ) {
                            Text(
                                text = "Run Smooth Pursuit Test",
                                fontSize = 24.sp
                            )
                        }
                    }
                }

                // Card for PLR Test (Image + Button)
                Card(
                    modifier = Modifier
                        .width(350.dp)
                        .padding(horizontal = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE6F7FF)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.plr_icon),
                            contentDescription = "PLR Test Image",
                            modifier = Modifier
                                .width(128.dp)
                                .height(128.dp)
                        )
                        ElevatedButton(
                            onClick = { runTestsViewModel.onPlrButtonClicked() },
                            modifier = Modifier
                                .width(300.dp)
                                .height(80.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84BBD3))
                        ) {
                            Text(
                                text = "Run PLR Test",
                                fontSize = 24.sp
                            )
                        }
                    }
                }

                // "View Results" Button
                ElevatedButton(
                    onClick = {
                        runTestsViewModel.onNavigateToResults(patientId)
                        onClickResultsPage(patientId)
                    },
                    modifier = Modifier
                        .width(300.dp)
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84BBD3))
                ) {
                    Text(
                        text = "View Results",
                        fontSize = 24.sp
                    )
                }
            }

            // Display a loading indicator over the UI when a test is running
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            // Optionally show an error message at the bottom if there is an error
            errorMessage?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
        }
    }
}
