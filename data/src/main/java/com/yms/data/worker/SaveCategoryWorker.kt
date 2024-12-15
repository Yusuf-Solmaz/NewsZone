package com.yms.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.yms.data.worker.utils.OUTPUT_CATEGORY_KEY
import com.yms.data.worker.utils.WORKER_ERROR
import com.yms.domain.R
import com.yms.domain.repository.UserPreferencesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "SaveCategoryWorker"

@HiltWorker
class SaveCategoryWorker @AssistedInject constructor(
    private val repository: UserPreferencesRepository,
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters) : CoroutineWorker(ctx, params){



    override suspend fun doWork(): Result {
        val category = inputData.getString(OUTPUT_CATEGORY_KEY)

        return withContext(Dispatchers.IO){

            return@withContext try {
                require(!category.isNullOrBlank()){
                    val errorMessage =
                        applicationContext.resources.getString(R.string.invalid_input_data)
                    Log.e(TAG, errorMessage)
                    errorMessage
                }
                repository.saveCategory(category)

                Log.d(TAG, "doWork: $category")
                Result.success()
            }
            catch (e: Exception){
                val failureData = workDataOf(WORKER_ERROR to (e.localizedMessage ?: "Something Went Wrong") )
                Result.failure(failureData)
            }
        }
    }


}