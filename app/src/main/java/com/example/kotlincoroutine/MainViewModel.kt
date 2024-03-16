package com.example.kotlincoroutine

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class MainViewModel: ViewModel() {

    private val TAG: String = "KOTLINFUN"

   /* init {

        viewModelScope.launch {
            while (true){
                delay(500)
                Log.d(TAG, "Coroutine From ViewModel")
            }
        }
    }*/

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "Coroutine ViewModel Destory")
    }
}