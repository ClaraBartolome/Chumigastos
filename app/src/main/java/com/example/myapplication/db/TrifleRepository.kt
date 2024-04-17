package com.example.myapplication.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.db.models.TrifleModel

class TrifleRepository(private val trifleDao: TrifleDao) {

    private val _allTrifles = MutableLiveData<List<TrifleModel>>()
    val allTrifles: LiveData<List<TrifleModel>>
        get() = _allTrifles

    //region insert
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(trifle: TrifleModel) {
        trifleDao.insertTrifle(trifle)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllTrifles(trifles: List<TrifleModel>) {
        trifleDao.insertAllTrifles(trifles)
    }

    // endregion

    //region get

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllTrifles() {
        _allTrifles.postValue(trifleDao.getAll())
    }

    //endregion

    //region delete

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteTrifle(trifle: TrifleModel) {
        trifleDao.deleteTrifle(trifle)
    }

    //endregion

    /*private val _allLinks = MutableLiveData<List<LinkModel>>()
    val allLinks: LiveData<List<LinkModel>>
        get() =_allLinks

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(link: LinkModel) {
        linkSaverDao.insertLink(link)
    }

    //region getAllLinks

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByNameAsc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByNameAsc())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByNameDesc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByNameDesc())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByDateOfCreationAsc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByDateOfCreationAsc())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByDateOfCreationDesc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByDateOfCreationDesc())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByDateOfModifiedAsc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByDateOfModifiedAsc())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllLinksByDateOfModifiedDesc() {
        _allLinks.postValue(linkSaverDao.getAllOrderedByDateOfModifiedDesc())
    }

    //endregion

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateLink(link: LinkModel) {
        linkSaverDao.updateLink(link)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteLink(link: LinkModel) {
        linkSaverDao.deleteLink(link)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteListLink(linkList: List<LinkModel>) {
        linkSaverDao.deleteListLink(linkList)
    }*/

}