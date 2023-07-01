package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import java.lang.Exception

class SecondDelayWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val delay = inputData.getLong(MILLISECOND_TIME_DELAY , 0)
            delay(delay)
            Log.d("Sobhan", "doWork: Do SecondDelayWorker $delay")
            Result.success()
        }catch (e : Exception){
            Result.failure()
        }
    }

    companion object{
        const val TAG = "com.example.workmanager.SecondDelayWorker"
        const val MILLISECOND_TIME_DELAY = "com.example.workmanager.SecondDelayWorker.TIME"
    }
}