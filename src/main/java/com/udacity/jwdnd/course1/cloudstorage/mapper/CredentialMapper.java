package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId}")
    public List<Credential> getCredentials(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credentialId} AND userid=#{userId}")
    public Credential getCredential(Integer credentialId, Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid)" +
            " VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public Integer insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, " +
            "password=#{password} WHERE userid=#{userId}")
    public void update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialId} AND userid=#{userId}")
    public void delete(Integer credentialId, Integer userId);

}
