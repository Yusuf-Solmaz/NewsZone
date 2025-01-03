package com.yms.data.utils

import com.google.ai.client.generativeai.GenerativeModel
import com.yms.data.BuildConfig

val generativeModel = GenerativeModel(
    modelName = "gemini-1.5-flash",
    apiKey = BuildConfig.GEMINI_API_KEY
)