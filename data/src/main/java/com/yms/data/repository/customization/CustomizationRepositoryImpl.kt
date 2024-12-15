package com.yms.data.repository.customization

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.yms.data.worker.DetermineCategoryWorker
import com.yms.data.worker.SaveCategoryWorker
import com.yms.data.worker.utils.AGE_GROUP_KEY
import com.yms.data.worker.utils.FOLLOW_UP_TIME_KEY
import com.yms.data.worker.utils.GENDER_KEY
import com.yms.domain.model.user_preferences.AgeGroup
import com.yms.domain.model.user_preferences.FollowUpTime
import com.yms.domain.model.user_preferences.Gender
import com.yms.domain.repository.user_preferences.CustomizationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CustomizationRepositoryImpl @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private val workManager: WorkManager
) : CustomizationRepository{


    override suspend fun saveCategory(followUpTime: FollowUpTime, ageGroup: AgeGroup, gender: Gender) {
        val determineCategoryBuilder = OneTimeWorkRequestBuilder<DetermineCategoryWorker>()
            .setInputData(createInputDataForWorkRequest(followUpTime, ageGroup, gender))
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()

        val saveCategoryBuilder = OneTimeWorkRequestBuilder<SaveCategoryWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()


        workManager
            .beginWith(determineCategoryBuilder)
            .then(saveCategoryBuilder)
            .enqueue()
    }


    private fun createInputDataForWorkRequest(followUpTime: FollowUpTime, ageGroup: AgeGroup, gender: Gender): Data {
        val builder = Data.Builder()
        builder.putString(FOLLOW_UP_TIME_KEY, followUpTime.name).putString(AGE_GROUP_KEY, ageGroup.name).putString(GENDER_KEY, gender.name)
        return builder.build()
    }

}