package com.example.menuapp.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.menuapp.data.LoginRequest
import com.example.menuapp.data.Repository
import com.example.menuapp.data.Venue
import com.example.menuapp.getErrorMessage
import kotlinx.coroutines.launch

class MainViewModel(repository: Repository) :
    ViewModelBase<MainViewModelState>(repository, MainViewModelState.Loading) {

    fun handleIntent(intent: MainViewModelIntent) {
        when (intent) {
            MainViewModelIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {
        _state.postValue(MainViewModelState.Loading)
        viewModelScope.launch {
            val dataResponse = repository.loadData()
            val responseBody = dataResponse.body()
            _state.postValue(
                if (dataResponse.isSuccessful && responseBody != null) {
                    MainViewModelState.LoadedData(responseBody.data.venues)
                } else {
                    MainViewModelState.Error(dataResponse.errorBody().getErrorMessage())
                }
            )
        }
    }

}


sealed interface MainViewModelState {
    data class LoadedData(val venues: List<Venue>) : MainViewModelState
    object Loading : MainViewModelState
    data class Error(val message: String) : MainViewModelState
}

sealed interface MainViewModelIntent {
    object LoadData : MainViewModelIntent
}