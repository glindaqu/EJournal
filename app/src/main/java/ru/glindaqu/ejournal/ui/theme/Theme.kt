package ru.glindaqu.ejournal.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val MainPalette =
    lightColorScheme(
        background = Color(0xFFF7E3E4),
        primary = Color(0xFFBB7F81),
        onBackground = Color.White,
        errorContainer = Color(0xFFFF9595),
        error = Color(0xFFBB7F81),
    )

@Suppress("ktlint:standard:function-naming")
@Composable
fun EJournalTheme(content: @Composable () -> Unit) {
    val systemUIController = rememberSystemUiController()

    SideEffect {
        systemUIController.setStatusBarColor(MainPalette.background)
        systemUIController.setNavigationBarColor(MainPalette.onBackground)
    }

    MaterialTheme(
        colorScheme = MainPalette,
        typography = Typography,
        content = content,
    )
}
