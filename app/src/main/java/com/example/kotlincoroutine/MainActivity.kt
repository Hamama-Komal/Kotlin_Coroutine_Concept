package com.example.kotlincoroutine

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlincoroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG: String = "KOTLINFUN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d(TAG, "${Thread.currentThread().name}")

        binding.btnCounter.setOnClickListener {
            updateCounter();
        }

        binding.btnLongTask.setOnClickListener {

           // excuteLongRuningTask()

           // doActionThread()

            doActionOnCoroutine()
        }

    }

    private fun doActionOnCoroutine() {

        // Create Simple Coroutines
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "1) ${Thread.currentThread().name}")
        }

        // run on main thread
        GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "2) ${Thread.currentThread().name}")
        }

        MainScope().launch(Dispatchers.Default){
            Log.d(TAG, "3) ${Thread.currentThread().name}")
        }

    }

    private fun doActionThread() {
        // create new thread
        thread(start = true){
            excuteLongRuningTask()
            Log.d(TAG, "${Thread.currentThread().name}")
        }
    }

    private fun excuteLongRuningTask() {

        for(i in 1..10000000L){

        }
    }

    private fun updateCounter() {

        Log.d(TAG, "${Thread.currentThread().name}")
        binding.counterTxt.text = "${binding.counterTxt.text.toString().toInt() + 1}"
    }
}