package ru.glindaqu.ejournal.screens.subjects

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import ru.glindaqu.ejournal.viewModel.implementation.SubjectsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Suppress("ktlint:standard:function-naming")
@Composable
fun Subjects() {
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[SubjectsViewModel::class.java]

    val uiState by viewModel.uiState.collectAsState()

    val systemUiController = rememberSystemUiController()
    val background = MaterialTheme.colorScheme.background
    val onBackground = MaterialTheme.colorScheme.onBackground

    LaunchedEffect(Unit) {
        launch {
            systemUiController.setStatusBarColor(onBackground)
            viewModel.uiState.value = SubjectsUIState.VIEW
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            systemUiController.setStatusBarColor(background)
        }
    }

    when (uiState) {
        SubjectsUIState.ADD -> AddSubject(viewModel = viewModel)
        SubjectsUIState.EDIT -> TODO()
        SubjectsUIState.DELETE -> TODO()
        SubjectsUIState.VIEW -> ViewSubjects(viewModel = viewModel)
    }
}
