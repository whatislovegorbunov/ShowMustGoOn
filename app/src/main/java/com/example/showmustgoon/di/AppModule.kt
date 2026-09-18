package com.example.showmustgoon.di

import com.example.showmustgoon.data.repository.ShowRepositoryImpl
import com.example.showmustgoon.domain.repository.ShowRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindShowRepository(impl: ShowRepositoryImpl): ShowRepository
}
