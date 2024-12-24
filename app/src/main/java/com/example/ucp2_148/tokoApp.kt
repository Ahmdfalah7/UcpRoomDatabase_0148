package com.example.ucp2_148

import android.app.Application
import com.example.ucp2_148.data.dependenciesinjection.ContainerApp

class TokoApp : Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        // Membuat instance ContainerApp
        containerApp = ContainerApp(this)
    }
}