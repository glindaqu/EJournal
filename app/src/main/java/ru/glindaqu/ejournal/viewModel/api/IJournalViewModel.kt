package ru.glindaqu.ejournal.viewModel.api

import ru.glindaqu.ejournal.dataModels.JournalRowData

interface IJournalViewModel {
    val studentsList: List<JournalRowData>
}