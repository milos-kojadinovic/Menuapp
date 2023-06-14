package com.example.menuapp.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.menuapp.data.ErrorResponse
import com.example.menuapp.data.LoginRequest
import com.example.menuapp.data.Repository
import com.example.menuapp.getErrorMessage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class LoginViewModel(repository: Repository) :
    ViewModelBase<LoginViewModelState>(repository, LoginViewModelState.Idle) {


    fun handleIntent(intent: LoginViewModelIntent) {
        when (intent) {
            is LoginViewModelIntent.TryLogin -> tryToLogin(intent.email, intent.password)
        }
    }

    private fun tryToLogin(email: String, password: String) {
        _state.postValue(LoginViewModelState.Loading)
        viewModelScope.launch {
            val loginResult = repository.login(LoginRequest(email = email, password = password))
            val responseBody = loginResult.body()
            _state.postValue(
                if (loginResult.isSuccessful && responseBody != null) {
                    LoginViewModelState.UserLoggedIn(responseBody.data.token.value)
                } else {
                    LoginViewModelState.Error(loginResult.errorBody().getErrorMessage())
                }
            )
        }
    }
}


sealed interface LoginViewModelState {
    object Idle : LoginViewModelState
    object Loading : LoginViewModelState
    data class Error(val message: String) : LoginViewModelState
    data class UserLoggedIn(val token: String) : LoginViewModelState

}

sealed interface LoginViewModelIntent {
    class TryLogin(val email: String, val password: String) : LoginViewModelIntent
}