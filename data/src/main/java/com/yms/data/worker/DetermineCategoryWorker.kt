package com.yms.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.yms.data.utils.AGE_GROUP_KEY
import com.yms.data.utils.FOLLOW_UP_TIME_KEY
import com.yms.data.utils.GENDER_KEY
import com.yms.data.utils.OUTPUT_CATEGORY_KEY
import com.yms.data.utils.WORKER_ERROR
import com.yms.domain.R
import com.yms.domain.model.user_preferences.AgeGroup
import com.yms.domain.model.user_preferences.Category
import com.yms.domain.model.user_preferences.FollowUpTime
import com.yms.domain.model.user_preferences.Gender
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "DetermineCategoryWorker"

@HiltWorker
class DetermineCategoryWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters) : CoroutineWorker(ctx, params){

    override suspend fun doWork(): Result {
        val followUpTime = inputData.getString(FOLLOW_UP_TIME_KEY)
        val ageGroup = inputData.getString(AGE_GROUP_KEY)
        val gender = inputData.getString(GENDER_KEY)

        return withContext(Dispatchers.IO){

            return@withContext try {
                require(!followUpTime.isNullOrBlank() && !ageGroup.isNullOrBlank() && !gender.isNullOrBlank()){
                    val errorMessage =
                        applicationContext.resources.getString(R.string.invalid_input_data)
                    Log.e(TAG, errorMessage)
                    errorMessage
                }

                val category = determineCategory(FollowUpTime.valueOf(followUpTime), AgeGroup.valueOf(ageGroup), Gender.valueOf(gender))

                Log.d(TAG, "Category: $category")

                val outputData = workDataOf(OUTPUT_CATEGORY_KEY to category)
                Result.success(outputData)
            }
            catch (e: Exception){
                val failureData = workDataOf(WORKER_ERROR to (e.localizedMessage ?: "Something Went Wrong") )
                Result.failure(failureData)
            }

        }
    }


    private fun determineCategory(followUpTime: FollowUpTime?, ageGroup: AgeGroup?, gender: Gender?): String {
        return when {
            followUpTime == FollowUpTime.EVERY_DAY && ageGroup == AgeGroup.AGE_16_25 -> Category.TECHNOLOGY.categoryName
            followUpTime == FollowUpTime.A_FEW_TIMES_A_WEEK && ageGroup == AgeGroup.AGE_26_35 -> Category.BUSINESS.categoryName
            followUpTime == FollowUpTime.SOMETIMES && gender == Gender.FEMALE -> Category.ENTERTAINMENT.categoryName
            followUpTime == FollowUpTime.VERY_RARELY && ageGroup == AgeGroup.AGE_35_PLUS -> Category.HEALTH.categoryName
            ageGroup == AgeGroup.AGE_5_15 -> Category.SCIENCE.categoryName
            gender == Gender.MALE && followUpTime == FollowUpTime.EVERY_DAY -> Category.SPORTS.categoryName
            else -> Category.GENERAL.categoryName
        }
    }

}