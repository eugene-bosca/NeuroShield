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
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.neuroshield_app.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunTestsScreen(
    onClickEyeAlignment: () -> Unit,
    onClickResultsPage: () -> Unit
) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Run Tests", fontSize = 28.sp) },
                navigationIcon = {
                    IconButton(onClick = onClickEyeAlignment) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color(0xFF84BBD3),
                            contentDescription = "Back",
                            modifier = Modifier.width(32.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            tint = Color(0xFF84BBD3),
                            contentDescription = "Menu",
                            modifier = Modifier.width(32.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                // Reduced top padding to move content closer to the header
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
                        onClick = {},
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
                        onClick = {},
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

            // "View Results" Button directly after the cards
            ElevatedButton(
                onClick = onClickResultsPage,
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
    }
}


