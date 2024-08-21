@file:Suppress("ktlint:standard:filename")

package ru.glindaqu.ejournal.database.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class PeopleKReturnTypes {
    LASTNAME,
    NAME,
    PATRONYMIC,
    NAME_LASTNAME,
    FULL_NAME,
    LASTNAME_NAME,
    LASTNAME_INITIALS,
}

@Entity
data class People(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo val name: String = "",
    @ColumnInfo val lastname: String = "",
    @ColumnInfo val patronymic: String = "",
    @ColumnInfo val role: String = "",
) {
    fun get(type: PeopleKReturnTypes): String =
        when (type) {
            PeopleKReturnTypes.LASTNAME -> this.lastname
            PeopleKReturnTypes.FULL_NAME -> "${this.lastname} ${this.name} ${this.patronymic}"
            PeopleKReturnTypes.NAME -> this.name
            PeopleKReturnTypes.PATRONYMIC -> this.patronymic
            PeopleKReturnTypes.NAME_LASTNAME -> "${this.name} ${this.lastname}"
            PeopleKReturnTypes.LASTNAME_NAME -> "${this.lastname} ${this.name}"
            PeopleKReturnTypes.LASTNAME_INITIALS ->
                "${this.lastname} " +
                    "${this.name.first().uppercase()}. " +
                    "${this.patronymic.first().uppercase()}."
        }
}
