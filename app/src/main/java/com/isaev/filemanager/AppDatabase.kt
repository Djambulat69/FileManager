package com.isaev.filemanager

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HashFile::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hashFilesDao(): HashFileDao

    companion object {

        val db: AppDatabase by lazy {
            Room.databaseBuilder(FileManagerApp.instance, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        const val DATABASE_NAME = "hash_files_database"
    }
}
