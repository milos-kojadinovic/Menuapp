package com.example.menuapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.doWhileObserved(
    observer: Observer<T>,
    function: LiveData<T>.() -> Unit
) {
    observeForever(observer)
    function()
    removeObserver(observer)
}
