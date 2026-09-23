package com.example.showmustgoon

import android.app.Application
import com.example.showmustgoon.di.AppComponent
import com.example.showmustgoon.di.DaggerAppComponent

class ShowMustGoOnApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}
