package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    public boolean isFileNameAvailable(String fileName, Integer userId) {
        List<File> userFiles = fileMapper.getFiles(userId);
        if (userFiles.size() == 0) return true;
        else{
            return !userFiles.contains(fileName);
        }
    }

    public List<File> getUserFiles(Integer userId){return fileMapper.getFiles(userId);}

    public File download(Integer fileId, Integer userId){return this.fileMapper.download(fileId, userId);}

    public Integer upload(MultipartFile multipartFile, Integer userId) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        File file = new File();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(multipartFile.getSize());
        file.setUserId(userId);
        file.setFileData(multipartFile.getBytes());
        System.out.println(multipartFile.getContentType());
        return fileMapper.upload(file);
    }

    public void delete(Integer fileId, Integer userId){fileMapper.delete(fileId, userId);}


}
