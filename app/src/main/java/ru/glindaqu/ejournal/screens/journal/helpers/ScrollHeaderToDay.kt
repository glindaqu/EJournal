@file:Suppress("ktlint:standard:no-empty-file")

package ru.glindaqu.ejournal.screens.journal.helpers

import androidx.compose.foundation.ScrollState

suspend fun scrollHeaderToDay(
    day: Int,
    itemSizeInPx: Float,
    scrollState: ScrollState,
) {
    scrollState.animateScrollTo(Math.round(day * itemSizeInPx) - 20)
}
