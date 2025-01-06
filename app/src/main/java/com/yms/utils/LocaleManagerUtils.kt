package com.yms.utils

import android.content.Context
import java.util.Locale

object LocaleManager {
    fun changeAppLanguage(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)

        context.createConfigurationContext(config)
    }
}