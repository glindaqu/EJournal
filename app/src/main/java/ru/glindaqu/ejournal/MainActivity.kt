package ru.glindaqu.ejournal

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ru.glindaqu.ejournal.navigation.NavGraph
import ru.glindaqu.ejournal.navigation.RoutesDao
import ru.glindaqu.ejournal.ui.components.bottomBar.BottomBar
import ru.glindaqu.ejournal.ui.theme.EJournalTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            var selectedDestination by remember { mutableStateOf("") }
            EJournalTheme {
                val routes = RoutesDao()
                Scaffold(
                    bottomBar = {
                        BottomBar(
                            destinations = routes.destinations,
                            controller = navHostController,
                            selected = selectedDestination,
                        )
                    },
                ) {
                    Surface(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(it),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        NavGraph(
                            navHostController = navHostController,
                            onDestinationChanged = { destination ->
                                selectedDestination = destination
                            },
                        )
                    }
                }
            }
        }
    }
}
