package com.ningmeng.filesystem.service;

import com.ningmeng.framework.domain.course.CoursePic;
import com.ningmeng.framework.domain.filesystem.response.UploadFileResult;
import com.ningmeng.framework.model.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileSystemService {
    UploadFileResult upload(MultipartFile file, String filetag, String businesskey, String metadata);

    ResponseResult saveCoursePic(String courseId, String pic);

    CoursePic findCoursepic(String courseId);

    ResponseResult deleteCoursePic(String courseId);
}
