package com.isaev.filemanager

class MainRepository {

    private val storeFileDao = AppDatabase.db.hashFilesDao()

    suspend fun refreshHashFiles(newFiles: List<HashFile>) {
        storeFileDao.cleanAll()
        storeFileDao.saveAll(newFiles)
    }

    suspend fun isModified(hashCode: Int): Boolean = storeFileDao.find(hashCode) == null

}