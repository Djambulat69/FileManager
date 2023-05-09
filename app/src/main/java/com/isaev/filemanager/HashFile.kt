package com.isaev.filemanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AppDatabase.DATABASE_NAME)
data class HashFile(
    @PrimaryKey val hash: Int
)
