package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import java.time.Duration
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val counterWorkManager =
            PeriodicWorkRequestBuilder<CounterWorkManager>(15, TimeUnit.MINUTES)
                .addTag(CounterWorkManager.TAG)
                .build()
        val workManager = WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            CounterWorkManager.TAG,
            ExistingPeriodicWorkPolicy.KEEP, counterWorkManager
        )

        workManager.state.observe(this){
            Log.d("Sobhan", "onCreate: $it")
        }


    }
}