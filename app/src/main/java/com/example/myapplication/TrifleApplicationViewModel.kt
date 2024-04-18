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
        getAllTriflesDateOfCreationDesc()
    }

    fun insertAll(trifleList: List<TrifleModel>)= viewModelScope.launch{
        repository.insertAllTrifles(trifleList)
        getAllTriflesDateOfCreationDesc()
    }

    //endregion

    //region Get

    fun getAllTriflesDateOfCreationDesc()= viewModelScope.launch{
        repository.getAllTriflesDateOfCreationDesc()
    }

    fun getAllTriflesDateOfCreationAsc()= viewModelScope.launch{
        repository.getAllTriflesDateOfCreationAsc()
    }

    fun getAllTriflesDateOfModDesc()= viewModelScope.launch{
        repository.getAllTriflesDateOfModDesc()
    }

    fun getAllTriflesDateOfModAsc()= viewModelScope.launch{
        repository.getAllTriflesDateOfModAsc()
    }

    fun getAllTriflesNameDesc()= viewModelScope.launch{
        repository.getAllTriflesNameDesc()
    }

    fun getAllTriflesNameAsc()= viewModelScope.launch{
        repository.getAllTriflesNameAsc()
    }

    fun getAllTriflesStoreNameDesc()= viewModelScope.launch{
        repository.getAllTriflesStoreNameDesc()
    }

    fun getAllTriflesStoreNameAsc()= viewModelScope.launch{
        repository.getAllTriflesStoreNameAsc()
    }

    //endregion

    fun deleteTrifle(trifle:TrifleModel)= viewModelScope.launch{
        repository.deleteTrifle(trifle)
    }

    fun updateTrifle(trifle:TrifleModel)= viewModelScope.launch{
        repository.updateTrifle(trifle)
        getAllTriflesDateOfCreationDesc()
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