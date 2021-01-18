package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final FileService fileService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public HomeController(NoteService noteService,
                          UserService userService,
                          FileService fileService,
                          CredentialService credentialService,
                          EncryptionService encryptionService){
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.encryptionService = encryptionService;
    }
    @GetMapping()
    public String homeView(Model model, Authentication authentication){
        User user = this.userService.getUser(authentication.getName());
        int userId = user.getUserId();
        model.addAttribute("notes", this.noteService.getUserNotes(userId));
        model.addAttribute("credentials", this.credentialService.getUserCredentials(userId));
        model.addAttribute("files", this.fileService.getUserFiles(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
}
