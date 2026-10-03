package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserProgressEntity::class,
        CompletedExperimentEntity::class,
        BadgeEntity::class,
        LabReportEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ChemLabDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: ChemLabDatabase? = null

        fun getDatabase(context: Context): ChemLabDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChemLabDatabase::class.java,
                    "chemlab_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
