package ru.glindaqu.ejournal.database.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mark(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo val studentId: Int,
    @ColumnInfo val pairId: Int,
    @ColumnInfo val date: Long = 0
)
