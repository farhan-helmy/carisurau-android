package com.example.carisurau

import android.app.Application
import com.example.carisurau.data.AppContainer
import com.example.carisurau.data.DefaultAppContainer

class CariSurauApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
