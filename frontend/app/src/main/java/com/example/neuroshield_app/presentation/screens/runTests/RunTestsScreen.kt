import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neuroshield_app.R
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
    // Observe states from the ViewModel
    val plrLoading by runTestsViewModel.plrLoading.observeAsState(false)
    val spLoading by runTestsViewModel.smoothPursuitLoading.observeAsState(false)
    val plrResult by runTestsViewModel.plrResult.observeAsState(null)
    val spResult by runTestsViewModel.smoothPursuitResult.observeAsState(null)
    val errorMessage by runTestsViewModel.error.observeAsState(null)
    // Optionally, observe global loading for navigation if needed
    val isLoading by runTestsViewModel.isLoading.observeAsState(false)

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
                Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Card(
                        modifier = Modifier
                            .width(350.dp)
                            .alpha(if (spResult != null) 0.5f else 1f),
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
                                onClick = { runTestsViewModel.onSmoothPursuitButtonClicked(patientId) },
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(80.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84BBD3))
                            ) {
                                if (spLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        text = "Run Smooth Pursuit Test",
                                        fontSize = 24.sp
                                    )
                                }
                            }
                        }
                    }
                    // Overlay a check mark if test completed successfully
                    if (spResult != null) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Success",
                            tint = Color(0xFF84BBD3),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        )
                    }
                }

                // Card for PLR Test (Image + Button)
                Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Card(
                        modifier = Modifier
                            .width(350.dp)
                            .alpha(if (plrResult != null) 0.5f else 1f),
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
                                onClick = { runTestsViewModel.onPlrButtonClicked(patientId) },
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(80.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84BBD3))
                            ) {
                                if (plrLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(
                                        text = "Run PLR Test",
                                        fontSize = 24.sp
                                    )
                                }
                            }
                        }
                    }
                    if (plrResult != null) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Success",
                            tint = Color(0xFF84BBD3),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        )
                    }
                }

                // "View Results" Button remains unchanged
                ElevatedButton(
                    onClick = {
                        onClickResultsPage(patientId)
                    },
                    modifier = Modifier
                        .width(300.dp)
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF84BBD3))
                ) {
                    // Optionally, you might add a loading indicator here if needed
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "View Results",
                            fontSize = 24.sp
                        )
                    }
                }
            }

            // Optionally show a global error message at the bottom
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
