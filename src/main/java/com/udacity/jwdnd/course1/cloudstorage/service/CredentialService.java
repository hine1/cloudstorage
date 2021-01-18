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

    public boolean isCredentialUrlAvailable(String url, Integer userId){
        List<Credential> userCredentials = credentialMapper.getCredentials(userId);
        if (userCredentials.size()==0) return true;
        else{
            return !userCredentials.contains(url);
        }
    }

    public List<Credential> getUserCredentials(Integer userId){return credentialMapper.getCredentials(userId);}

//    public String getCredentialPassword(Integer credentialId, Integer userId){
//        Credential credential = credentialMapper.getCredential(credentialId, userId);
//        return encryptionService.decryptValue(credential.getPassword(),);
//    }

    public Integer insert(Credential credential){return credentialMapper.insert(credential);}

    public void update(Credential credential){credentialMapper.update(credential);}

    public void delete(Integer credentialId, Integer userId){credentialMapper.delete(credentialId, userId);}
}
