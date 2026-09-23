package com.example.showmustgoon.di

import android.content.Context
import androidx.room.Room
import com.example.showmustgoon.data.local.NoteDao
import com.example.showmustgoon.data.local.NoteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(context: Context): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, "notes.db").build()

    @Provides
    @Singleton
    fun provideNoteDao(database: NoteDatabase): NoteDao = database.noteDao()
}
