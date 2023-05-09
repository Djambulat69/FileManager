package com.isaev.filemanager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HashFileDao {
    @Query("SELECT * FROM hash_files_database WHERE hash = :hashCode")
    suspend fun find(hashCode: Int): HashFile?

    @Insert
    suspend fun saveAll(storeFiles: List<HashFile>)

    @Query("DELETE FROM hash_files_database")
    suspend fun cleanAll()
}