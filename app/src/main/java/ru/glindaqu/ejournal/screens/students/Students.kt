package ru.glindaqu.ejournal.screens.students

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
import ru.glindaqu.ejournal.viewModel.implementation.StudentsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Suppress("ktlint:standard:function-naming")
@Composable
fun Students(navHostController: NavHostController) {
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[StudentsViewModel::class.java]

    val uiState by viewModel.uiState.collectAsState()

    val systemUiController = rememberSystemUiController()
    val onBackground = MaterialTheme.colorScheme.onBackground

    LaunchedEffect(Unit) {
        launch {
            systemUiController.setStatusBarColor(onBackground)
            viewModel.uiState.value = StudentsUIState.VIEW
        }
    }

    when (uiState) {
        StudentsUIState.ADD -> AddStudent(viewModel = viewModel)
        StudentsUIState.EDIT -> TODO()
        StudentsUIState.DELETE -> TODO()
        StudentsUIState.VIEW ->
            ViewStudents(
                viewModel = viewModel,
                navHostController = navHostController,
            )
    }
}
