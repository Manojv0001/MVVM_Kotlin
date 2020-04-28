package com.nullbyte.mvvm.db

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nullbyte.mvvm.db.dao.NoteDao
import com.nullbyte.mvvm.db.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    private val mApplication: Application? = null

    abstract fun noteDao(): NoteDao

    companion object{
        internal var instance: NoteDatabase? = null
        fun getInstance(context: Context): NoteDatabase {
            if(instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    "notes_database"
                )
                    .addCallback(RoomCallback)
                    .build()
            }
            return instance as NoteDatabase
        }
    }

//    private var instance: NoteDatabase? = getInstance(mApplication!!)
//
//    public fun getInstance(context: Context): NoteDatabase? {
//        if(instance == null) {
//            synchronized(NoteDatabase::class) {
//                instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    NoteDatabase::class.java,
//                    "notes_database"
//                )
//                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
//                    .build()
//            }
//        }
//        return instance
//    }

    fun destroyInstance() {
        instance = null
    }

    private val roomCallback = object : RoomDatabase.Callback() {
        private val noteDao = NoteDatabase.instance?.noteDao()
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            PopulateDbAsyncTask(noteDao).execute()
        }
    }
}

object RoomCallback : RoomDatabase.Callback() {
    private val noteDao = NoteDatabase.instance?.noteDao()
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        PopulateDbAsyncTask(noteDao).execute()
    }
}

class PopulateDbAsyncTask(noteDatabase: NoteDao?): AsyncTask<Unit, Unit, Unit>() {
    private val noteDao = noteDatabase
    override fun doInBackground(vararg p0: Unit?) {
        noteDao?.insert(Note("Title1", "Description1"))
        noteDao?.insert(Note("Title2", "Description2"))
        noteDao?.insert(Note("Title3", "Description3"))
    }

}
