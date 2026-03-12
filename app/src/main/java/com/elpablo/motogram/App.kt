package com.elpablo.motogram

import android.app.Application
import com.vk.id.VKID
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        VKID.init(this)
        VKID.instance.setLocale(Locale.forLanguageTag("ru"))
    }
}