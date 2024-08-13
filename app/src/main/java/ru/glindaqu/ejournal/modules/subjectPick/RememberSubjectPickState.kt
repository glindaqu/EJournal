package ru.glindaqu.ejournal.modules.subjectPick

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberSubjectPickState(): SubjectPickState =
    remember {
        SubjectPickState()
    }
