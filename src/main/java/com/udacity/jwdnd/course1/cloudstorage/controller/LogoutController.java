package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping("/logout")
public class LogoutController {
    @PostMapping()
    public String logoutRedirect(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("logout", true);
        return "redirect:/login";
    }
}
