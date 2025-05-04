package com.jj.shore

import android.app.Application
import com.google.firebase.FirebaseApp
import com.jj.shore.data.AppContainer
import com.jj.shore.data.AppDataContainer

/**
 * Wrapper for the Application, used to initialize the AppContainer.
 */
class ShoreApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        container = AppDataContainer(this)
    }
}