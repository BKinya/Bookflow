package com.beatrice.bookflow

import android.app.Application
import com.beatrice.bookflow.data.di.dataModule
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
        val modules = listOf(dataModule)
        startKoin {
            androidLogger()
            androidContext(this@BookflowApp)
            modules(modules)
        }
    }

    private fun setUpLogger(){
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
    }
}