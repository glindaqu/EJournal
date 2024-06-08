package ru.glindaqu.ejournal.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val MainPalette = lightColorScheme(
    background = Color(0xFFF7E3E4),
    primary = Color(0xFFAD696B),
    onBackground = Color.White
)

@Composable
fun EJournalTheme(
    content: @Composable () -> Unit
) {
    val systemUIController = rememberSystemUiController()

    SideEffect {
        systemUIController.setStatusBarColor(MainPalette.background)
        systemUIController.setNavigationBarColor(MainPalette.onBackground)
    }

    MaterialTheme(
        colorScheme = MainPalette,
        typography = Typography,
        content = content
    )
}