package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class CounterWorker(private val context: Context, private val workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("Sobhan","Do CounterWorkManager $counter")
            counter++
            Result.success()
        }catch (e : Exception){
            Result.failure()
        }
    }

    companion object {
        const val TAG = "com.example.workmanager.CounterWorkManager"
        private var counter = 0;
    }
}