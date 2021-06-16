package com.ihsan.sona3.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.utils.TypeConvertersObject

@Database(
    entities = [User::class],
    version = 2
)
@TypeConverters(TypeConvertersObject::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "App.db"
            ).fallbackToDestructiveMigration().build()
    }
}