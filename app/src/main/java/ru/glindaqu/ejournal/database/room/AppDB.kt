package ru.glindaqu.ejournal.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.glindaqu.ejournal.database.room.dao.PairDao
import ru.glindaqu.ejournal.database.room.dao.PeopleDao
import ru.glindaqu.ejournal.database.room.tables.Mark
import ru.glindaqu.ejournal.database.room.tables.Pair
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.database.room.tables.Skip

@Database(
    entities = [Mark::class, Pair::class, Skip::class, People::class],
    version = 1,
)
abstract class AppDB : RoomDatabase() {
    abstract fun getPairDao(): PairDao

    abstract fun getPeopleDao(): PeopleDao

    companion object {
        fun getDatabase(context: Context): AppDB =
            Room
                .databaseBuilder(
                    context = context.applicationContext,
                    klass = AppDB::class.java,
                    name = "eJournalBase.db",
                ).addCallback(onDatabaseCreate)
                .build()

        private var onDatabaseCreate: Callback =
            object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    db.execSQL("INSERT INTO Pair(title, teacherId) VALUES ('Математика', 1)")
                }
            }
    }
}
