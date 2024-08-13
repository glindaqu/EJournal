package ru.glindaqu.ejournal.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.glindaqu.ejournal.database.room.dao.PairDao
import ru.glindaqu.ejournal.database.room.tables.Mark
import ru.glindaqu.ejournal.database.room.tables.Pair
import ru.glindaqu.ejournal.database.room.tables.Skip
import ru.glindaqu.ejournal.database.room.tables.User

@Database(
    entities = [Mark::class, Pair::class, Skip::class, User::class],
    version = 1,
)
abstract class AppDB : RoomDatabase() {
    abstract fun getPairDao(): PairDao

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
