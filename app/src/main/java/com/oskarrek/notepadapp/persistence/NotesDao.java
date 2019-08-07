package com.oskarrek.notepadapp.persistence;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.oskarrek.notepadapp.models.Note;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertNotes(Note... notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotesList();

    @Delete
    int deleteNotes(Note... notes);

    @Update
    int updateNotes(Note... notes);
}
