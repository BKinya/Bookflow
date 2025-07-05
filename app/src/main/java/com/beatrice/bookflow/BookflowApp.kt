package com.beatrice.bookflow

import android.app.Application
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BookflowApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
        setUpLogger()

    }

    private fun startKoin(){
        startKoin {
            androidLogger()
            androidContext(this@BookflowApp)
            modules()
        }
    }

    private fun setUpLogger(){
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
    }
}