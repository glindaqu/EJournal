package ru.glindaqu.ejournal.screens

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import ru.glindaqu.ejournal.viewModel.implementation.HomeViewModel

@Composable
fun Home() {
    val viewModel = ViewModelProvider(LocalContext.current as ComponentActivity)[HomeViewModel::class.java]
}