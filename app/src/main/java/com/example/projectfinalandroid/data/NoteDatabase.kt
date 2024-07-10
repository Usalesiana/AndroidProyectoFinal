package com.example.projectfinalandroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projectfinalandroid.models.Note
import com.example.projectfinalandroid.room.NoteDao

@Database(entities = [Note::class], version = 3)
abstract class NoteDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao

    //Singleton
    companion object {
        @Volatile //2 lugares distintos no acceden a la vez
        private var INSTANCES: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase{
            synchronized(this){
                var instance = INSTANCES
                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, NoteDatabase::class.java, "notes_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCES = instance
                return instance
            }
        }
    }

}