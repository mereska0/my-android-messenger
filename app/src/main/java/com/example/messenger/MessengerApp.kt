package com.example.messenger

import android.app.Application
import com.example.messenger.data.repository.Graph

class MessengerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.init(this)
    }
}