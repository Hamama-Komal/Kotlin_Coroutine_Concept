package com.example.kotlincoroutine

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlincoroutine.databinding.ActivitySuspendBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class SuspendActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuspendBinding

    private val TAG: String = "KOTLINFUN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySuspendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       //  task1()      >>>  cannot be call without suspending function 0r Coroutine

        CoroutineScope(Dispatchers.Main).launch {
            task1()
        }

        CoroutineScope(Dispatchers.Main).launch {
            task2()
        }


    }

    suspend fun task1(){
        Log.d(TAG, "Starting Task 1")
        // yield()
        delay(2000)
        Log.d(TAG, "Ending Task 1")
    }

    suspend fun task2(){
        Log.d(TAG, "Starting Task 2")
        yield()
        Log.d(TAG, "Ending Task 2")
    }


}