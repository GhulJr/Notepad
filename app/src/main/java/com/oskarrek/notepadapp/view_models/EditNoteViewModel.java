package com.oskarrek.notepadapp.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.oskarrek.notepadapp.models.Note;
import com.oskarrek.notepadapp.repositories.NotesRepository;


public class EditNoteViewModel extends AndroidViewModel {

    private LiveData<Note> editedNote;
    private NotesRepository notesRepository;


    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        notesRepository = NotesRepository.getInstance(application.getApplicationContext());
    }

    public void fetchNoteById(int id) {
        editedNote = notesRepository.getNoteById(id);
    }

    public LiveData<Note> getEditedNote() {
        return editedNote;
    }

    /*Add new note to database.*/
    public void insertNote(String title, String text) {
        Note note = new Note(title, text);
        notesRepository.insertNotesTask(note);
    }

    public void updateNote(Note note) {
        notesRepository.updateNotesTask(note);
    }
}
