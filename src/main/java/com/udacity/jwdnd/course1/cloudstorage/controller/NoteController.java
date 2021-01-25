package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;
    public NoteController(NoteService noteService, UserService userService){
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable(value="noteId") Integer noteId, Model model, Authentication authentication){
        User user = this.userService.getUser(authentication.getName());
        try{
            this.noteService.delete(noteId, user.getUserId());
            model.addAttribute("deleteNoteSuccess", true);
            return "home";
        }
        catch (Exception e) {
            return "error";
        }
    }

    @PostMapping()
    public String uploadNote(Note note, Model model, Authentication authentication){
        User user = this.userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());

        if (note.getNoteId()!=null){
            noteService.update(note);
        }
        else{
            int rowsAdded = noteService.insert(note);
            if (rowsAdded < 0){
                model.addAttribute("uploadError", true);
                return "result";
            }
        }
        model.addAttribute("success", true);
        return "result";
    }
}
