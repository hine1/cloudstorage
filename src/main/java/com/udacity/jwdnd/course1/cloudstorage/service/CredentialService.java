package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService){
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }


    public List<Credential> getUserCredentials(Integer userId){return credentialMapper.getCredentials(userId);}


    public Integer insert(Credential credential){return credentialMapper.insert(credential);}

    public void update(Credential credential){credentialMapper.update(credential);}

    public void delete(Integer credentialId, Integer userId){credentialMapper.delete(credentialId, userId);}
}
