package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.TrifleRepository
import com.example.myapplication.db.models.TrifleModel
import kotlinx.coroutines.launch

class TrifleApplicationViewModel(private val repository: TrifleRepository) : ViewModel() {

    val allTrifles: LiveData<List<TrifleModel>> = repository.allTrifles

    //region Insert
    fun insert(trifle:TrifleModel)= viewModelScope.launch{
        repository.insert(trifle)
        getAll()
    }

    fun insertAll(trifleList: List<TrifleModel>)= viewModelScope.launch{
        repository.insertAllTrifles(trifleList)
        getAll()
    }

    //endregion

    //region Get

    fun getAll()= viewModelScope.launch{
        repository.getAllTrifles()
    }

    //endregion

    fun deleteTrifle(trifle:TrifleModel)= viewModelScope.launch{
        repository.deleteTrifle(trifle)
        getAll()
    }

    fun updateTrifle(trifle:TrifleModel)= viewModelScope.launch{
        repository.updateTrifle(trifle)
        getAll()
    }
}

class TrifleViewModelFactory(private val repository: TrifleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrifleApplicationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrifleApplicationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}