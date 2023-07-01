package com.example.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chainWorkManager()
    }

    private fun periodicWorkManager() {
        val counterWorker =
            PeriodicWorkRequestBuilder<CounterWorker>(15, TimeUnit.MINUTES)
                .addTag(CounterWorker.TAG)
                .build()
        val workManager = WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            CounterWorker.TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            counterWorker
        )

        workManager.state.observe(this) {
            Log.d("Sobhan", "periodicWorkManager: $it")
        }
    }

    private fun oneTimeWorkManager() {
        val nameWorker =
            OneTimeWorkRequestBuilder<NameWorker>()
                .addTag(NameWorker.TAG)
                .build()
        val workManager = WorkManager.getInstance(this)
            .beginUniqueWork(NameWorker.TAG, ExistingWorkPolicy.KEEP, nameWorker).enqueue()

        workManager.state.observe(this) {
            Log.d("Sobhan", "oneTimeWorkManager: $it")
        }
    }

    private fun parallelWorkManager() {
        val fourSecondWorker =
            OneTimeWorkRequestBuilder<SecondDelayWorker>()
                .addTag(SecondDelayWorker.TAG)
                .setInputData(
                    Data.Builder().putLong(SecondDelayWorker.MILLISECOND_TIME_DELAY, 4000).build()
                )
                .build()

        val sevenSecondWorker =
            OneTimeWorkRequestBuilder<SecondDelayWorker>()
                .addTag(SecondDelayWorker.TAG)
                .setInputData(
                    Data.Builder().putLong(SecondDelayWorker.MILLISECOND_TIME_DELAY, 7000).build()
                )
                .build()

        val workManager =
            WorkManager.getInstance(this)
                .beginWith(listOf(fourSecondWorker, sevenSecondWorker))
                .enqueue()

        workManager.state.observe(this) {
            Log.d("Sobhan", "parallelWorkManager: $it")
        }
    }

    private fun chainWorkManager(){
        val fourSecondWorker =
            OneTimeWorkRequestBuilder<SecondDelayWorker>()
                .addTag(SecondDelayWorker.TAG)
                .setInputData(
                    Data.Builder().putLong(SecondDelayWorker.MILLISECOND_TIME_DELAY, 4000).build()
                )
                .build()

        val sevenSecondWorker =
            OneTimeWorkRequestBuilder<SecondDelayWorker>()
                .addTag(SecondDelayWorker.TAG)
                .setInputData(
                    Data.Builder().putLong(SecondDelayWorker.MILLISECOND_TIME_DELAY, 7000).build()
                )
                .build()

        val tenSecondWorker =
            OneTimeWorkRequestBuilder<SecondDelayWorker>()
                .addTag(SecondDelayWorker.TAG)
                .setInputData(
                    Data.Builder().putLong(SecondDelayWorker.MILLISECOND_TIME_DELAY, 10000).build()
                )
                .build()

        val workManager =
            WorkManager.getInstance(this)
                .beginWith(listOf(fourSecondWorker, sevenSecondWorker))
                .then(tenSecondWorker)
                .enqueue()

        workManager.state.observe(this) {
            Log.d("Sobhan", "parallelWorkManager: $it")
        }
    }
}