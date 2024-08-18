package ru.glindaqu.ejournal.database.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pair(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo val title: String = "",
    @ColumnInfo val teacherId: Int = 1,
)
