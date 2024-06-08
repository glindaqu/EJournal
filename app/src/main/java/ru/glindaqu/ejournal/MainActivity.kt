package ru.glindaqu.ejournal

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
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
            EJournalTheme {
                val routes = RoutesDao()
                Scaffold(bottomBar = { BottomBar(destinations = routes.destinations) }) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .padding(top = 10.dp),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavGraph()
                    }
                }
            }
        }
    }
}