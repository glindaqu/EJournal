package ru.glindaqu.ejournal.screens.journal.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.glindaqu.ejournal.R

@Suppress("ktlint:standard:function-naming")
@Composable
fun Loading() {
    val animation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onBackground),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieAnimation(
            composition = animation,
            modifier = Modifier.fillMaxSize(0.3f),
            iterations = LottieConstants.IterateForever,
        )
    }
}
