package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT fileId, fileName FROM FILES WHERE userId = #{userId}")
    List<File> getFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId} AND userId=#{userId}")
    File download(Integer fileId, Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName},#{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int upload(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId} AND userid=#{userId}")
    void delete(Integer fileId, Integer userId);

}
