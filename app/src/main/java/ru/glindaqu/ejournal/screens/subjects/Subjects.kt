package ru.glindaqu.ejournal.screens.subjects

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import ru.glindaqu.ejournal.viewModel.implementation.SubjectsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Suppress("ktlint:standard:function-naming")
@Composable
fun Subjects(navHostController: NavHostController) {
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[SubjectsViewModel::class.java]

    val uiState by viewModel.uiState.collectAsState()

    val systemUiController = rememberSystemUiController()
//    val background = MaterialTheme.colorScheme.background
    val onBackground = MaterialTheme.colorScheme.onBackground

    LaunchedEffect(Unit) {
        launch {
            systemUiController.setStatusBarColor(onBackground)
            viewModel.uiState.value = SubjectsUIState.VIEW
        }
    }

    when (uiState) {
        SubjectsUIState.ADD -> AddSubject(viewModel = viewModel)
        SubjectsUIState.EDIT -> TODO()
        SubjectsUIState.DELETE -> TODO()
        SubjectsUIState.VIEW -> ViewSubjects(viewModel = viewModel, navHostController = navHostController)
    }
}
