package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class NameWorkManager(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("Sobhan", "Do NameWorkManager")
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}