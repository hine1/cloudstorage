package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper){this.noteMapper = noteMapper;}

    public boolean isNoteTitleAvailable(String noteTitle, Integer userId){
        List<Note> userNotes = noteMapper.getNotes(userId);
        if (userNotes.size() == 0) return true;
        else{
            return !userNotes.contains(noteTitle);
        }
    }

    public List<Note> getUserNotes(Integer userId){return noteMapper.getNotes(userId);}

    public Integer insert(Note note){
        return noteMapper.insert(note);
    }

    public void update(Note note){noteMapper.update(note);}

    public void delete(Integer noteId, Integer userId){noteMapper.delete(noteId, userId);}
}
