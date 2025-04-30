package com.jj.shore

import android.app.Application
import com.google.firebase.FirebaseApp
import com.jj.shore.data.AppContainer
import com.jj.shore.data.AppDataContainer
import com.jj.shore.helpers.connectivity.NetworkConnectivityObserver

class ShoreApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        NetworkConnectivityObserver(this)

        container = AppDataContainer(this)
    }
}