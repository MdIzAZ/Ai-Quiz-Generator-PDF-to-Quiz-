package org.example.app.ai.quiz.knowledge

import android.app.Application
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import org.example.app.ai.quiz.knowledge.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        PDFBoxResourceLoader.init(applicationContext)

        initKoin {
            androidLogger()
            androidContext(this@MyApplication)
        }
    }
}