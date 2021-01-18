package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/files")
public class FileController {
    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService){
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity downloadFile(@PathVariable(value="fileId") Integer fileId, Authentication authentication){
        User user = this.userService.getUser(authentication.getName());
        File file = fileService.download(fileId, user.getUserId());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));

    }
    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable(value = "fileId") Integer fileId, Authentication authentication){
        User user = this.userService.getUser(authentication.getName());
        fileService.delete(fileId, user.getUserId());
        return "redirect:/home";
    }

    @PostMapping()
    public String uploadFile(MultipartFile fileUpload,
                             Authentication authentication,
                             Model model){
        if (fileUpload.isEmpty()){
            model.addAttribute("error", "You must select a file to upload");
            return "result";
        }
        User user = this.userService.getUser(authentication.getName());

        if (this.fileService.isFileNameAvailable(fileUpload.getOriginalFilename(), user.getUserId())){
            try{
                Integer fileId = this.fileService.upload(fileUpload, user.getUserId());
                if (fileId==null){
                   model.addAttribute("uploadError", true);
                   return "result";
                }
                model.addAttribute("success", true);
                return "result";

            }catch (Exception e){
                model.addAttribute("uploadError", e.getMessage());
                return "result";
            }
        }else{
            model.addAttribute("error", "The file name is already taken. Please use unique file name");
            return "result";
        }
    }
}
