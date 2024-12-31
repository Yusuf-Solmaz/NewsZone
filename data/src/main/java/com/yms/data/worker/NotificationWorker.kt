package com.yms.data.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.yms.data.utils.makeStatusNotification
import com.yms.domain.repository.news.NewsRepository
import com.yms.domain.utils.RootResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    private val repository : NewsRepository,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams) {

    companion object {
        const val WORK_NAME = "NotificationWorker"
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO){

                return@withContext try {
                    val response = repository.getBreakingNews(
                        category = null,
                        page = 1,
                        pageSize = 1,
                        source = "cnn"
                    )
                    response.collect{
                        result->
                        when(result){
                            is RootResult.Success -> {
                                val data = result.data?.articleDtos?.get(0)

                                if (data != null) {

                                    Log.d("NotificationWorker", "Notification sent: $data")
                                    makeStatusNotification(data, applicationContext)
                                }
                            }
                            is RootResult.Error -> {
                                Log.e("NotificationWorker", "Error: ${result.message}")

                            }
                            RootResult.Loading -> {
                                Log.d("NotificationWorker", "Loading")

                            }
                        }
                    }
                    Result.success()
                }
                catch (e: Exception){
                    val failureData = workDataOf(WORK_NAME to (e.localizedMessage ?: "Something Went Wrong") )
                    Result.failure(failureData)
                }

     }
    }

}