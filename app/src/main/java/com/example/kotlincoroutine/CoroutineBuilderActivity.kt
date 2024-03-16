package com.example.kotlincoroutine

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutineBuilderActivity : AppCompatActivity() {

    private val TAG: String = "KOTLINFUN"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_coroutine_builder)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // both Coroutines run in parallel fashion
        /*CoroutineScope(Dispatchers.IO).launch {
            // printOutput()
        }*/

        GlobalScope.launch(Dispatchers.Main) {

           // jobsMethod()

           // longJobsMethod()

            coroutineWithContext()
        }



    }

    private suspend fun coroutineWithContext() {

        Log.d(TAG, "Before WithContext")

        withContext(Dispatchers.Main){
            delay(1000)
            Log.d(TAG, "Inside WithContext")
        }

        Log.d(TAG, "After WithContext")

    }


    private suspend fun longJobsMethod() {

        val parentJob = CoroutineScope(Dispatchers.IO).launch {


            for(i in 1..5000) {
                if(isActive){
                excuteLongRunningTask()
                Log.d(TAG, i.toString())
            }
            }
        }

        delay(50)
        Log.d(TAG, "Parent job cancel")
        parentJob.cancel()

        parentJob.join()
        delay(1000)
        Log.d(TAG, "Parent job completed")


    }

    private suspend fun jobsMethod() {

        val parentJob = GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "Parent job started")

            val childJob = launch (Dispatchers.IO){
                try {
                    Log.d(TAG, "Child job started")
                    delay(6000)
                    Log.d(TAG, "Child job ended")
                }
                catch (e : CancellationException){
                    Log.d(TAG, "Child job cancelled")
                }

            }

            // if child job canceled
            delay(1000)
            childJob.cancel()

            delay(3000)
            Log.d(TAG, "Parent job ended")

        }

        // if parent job ended
         // delay(1000)
        // parentJob.cancel()  // cancel out both parent and child job

        parentJob.join()
        Log.d(TAG, "Parent job completed")      // parent job complete after the completion of child jobs
    }


    private fun excuteLongRunningTask() {

        for (i in 1..1000){

        }
    }

    private suspend fun printOutput() {

        // join()
       /*
        var myNumber = 0;
        val job = CoroutineScope(Dispatchers.IO).launch {
            myNumber = getNumber()
        }
        // join() : Suspend this coroutine, wait for the completion of this coroutine
        job.join()
        Log.d(TAG, myNumber.toString())
        */


        // async() and await()
        /*val job = CoroutineScope(Dispatchers.IO).async {
            getNumber()
            // "Hello" // return deferred object
        }
        Log.d(TAG, job.await().toString())
        */

        CoroutineScope(Dispatchers.IO).launch {
            val firstNumber = async { getNumber() }
            val secondNumber = async { getOtherNumber() }
            // async() : parallel execute functions

            Log.d(TAG, "First = ${firstNumber.await()} ,Second = ${secondNumber.await()}")
        }

    }

    private suspend fun getOtherNumber() : Int {
        delay(3000)
        return 60
    }

    private suspend fun getNumber(): Int {

        delay(1000)
        return 50

    }
}