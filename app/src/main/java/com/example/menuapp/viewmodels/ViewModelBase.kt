package com.example.menuapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.menuapp.data.Repository

open class ViewModelBase<T>(protected val repository: Repository, initialValue: T) : ViewModel() {

    protected val _state = MutableLiveData(initialValue)
    val state: LiveData<T>
        get() = _state

}