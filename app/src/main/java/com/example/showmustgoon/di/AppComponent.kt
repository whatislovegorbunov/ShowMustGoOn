package com.example.showmustgoon.di

import com.example.showmustgoon.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun viewModelFactory(): ViewModelFactory
}
