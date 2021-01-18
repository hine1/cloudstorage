package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService){
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable(value="credentialId") Integer credentialId, Authentication authentication){
        User user = this.userService.getUser(authentication.getName());
        this.credentialService.delete(credentialId, user.getUserId());
        return "redirect:/home";
    }
    @PostMapping()
    public String uploadCredential(Credential credential, Authentication authentication, Model model){

        User user = this.userService.getUser(authentication.getName());
        credential.setUserId(user.getUserId());
        credential.setKey(user.getSalt());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);

        if (!credentialService.isCredentialUrlAvailable(credential.getUrl(), user.getUserId())){
            model.addAttribute("error", "There is already a credential available for this url.");
        }else{
            if (credential.getCredentialId()!=null){
                credentialService.update(credential);
            }else {
                int rowsAdded = credentialService.insert(credential);
                if (rowsAdded < 0) {
                    model.addAttribute("uploadError", "There was an error adding the credential. Please try again.");
                    return "result";
                }
            }

            model.addAttribute("success", true);
        }
        return "result";
    }
}
