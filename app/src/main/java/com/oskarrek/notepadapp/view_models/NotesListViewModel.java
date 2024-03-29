package com.oskarrek.notepadapp.view_models;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.oskarrek.notepadapp.models.Note;
import com.oskarrek.notepadapp.repositories.NotesRepository;

import java.util.List;

public class NotesListViewModel extends AndroidViewModel {

    private LiveData<List<Note>> notesLiveData;
    private NotesRepository notesRepository;

    public NotesListViewModel(@NonNull Application application) {
        super(application);
        notesRepository = NotesRepository.getInstance(application.getApplicationContext());
        fetchNotes();
    }

    /*Get live data to let activity observe it.*/
    public LiveData<List<Note>> getNotes() {
        return notesLiveData;
    }

    public void deleteNote(Note... notes) {
        notesRepository.deleteNotesTask(notes);
    }

    /*Update fetch notes from database and pass it to live data*/
    private void fetchNotes() {
        notesLiveData = notesRepository.getNotesTask();
    }

}
