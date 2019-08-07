package com.oskarrek.notepadapp.persistence;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.oskarrek.notepadapp.models.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public static String DATABASE_NAME ="notes_db";

    private static NotesDatabase instance;

    public static NotesDatabase getInstance(final Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NotesDatabase.class,
                    DATABASE_NAME).build();
        }
        return instance;    }

    public abstract NotesDao getNotesDao();

}
