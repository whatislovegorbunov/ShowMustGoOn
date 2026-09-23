package com.example.showmustgoon.di

import com.example.showmustgoon.data.api.NoteRepository
import com.example.showmustgoon.data.impl.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindNoteRepository(impl: NoteRepositoryImpl): NoteRepository
}
