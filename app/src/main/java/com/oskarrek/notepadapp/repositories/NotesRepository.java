package com.oskarrek.notepadapp.repositories;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.oskarrek.notepadapp.models.Note;
import com.oskarrek.notepadapp.persistence.NotesDao;
import com.oskarrek.notepadapp.persistence.NotesDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotesRepository {

    private NotesDao notesDao;
    private ExecutorService executor;

    public static NotesRepository instance;

    private NotesRepository(final Context context) {
        NotesDatabase weatherInfoDatabase = NotesDatabase.getInstance(context);
        this.notesDao = weatherInfoDatabase.getNotesDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public static NotesRepository getInstance(Context context) {
        if(instance == null) {
            instance = new NotesRepository(context);
        }
        return instance;
    }

    public void insertNotesTask(final Note... notes) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.insertNotes(notes);
            }
        });
    }

    public LiveData<List<Note>> getNotesTask() {
        return notesDao.getNotesList();
    }

    public void updateNotesTask(final Note... notes) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.updateNotes(notes);
            }
        });
    }

    public void deleteNotesTask(final Note... notes) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                notesDao.updateNotes(notes);
            }
        });
    }



}
