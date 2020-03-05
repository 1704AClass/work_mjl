package com.ningmeng.filesystem.controller;

import com.ningmeng.api.fileApi.FileSystemControllerApi;
import com.ningmeng.filesystem.service.FileSystemService;
import com.ningmeng.framework.domain.course.CoursePic;
import com.ningmeng.framework.domain.filesystem.response.UploadFileResult;
import com.ningmeng.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2020/2/21.
 */
@RestController
@RequestMapping("/filesystem")
public class FileSystemController implements FileSystemControllerApi {
    @Autowired
    private FileSystemService fileSystemService;

    @Override
    @PostMapping("/upload")
    public UploadFileResult upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "filetag", required = true) String filetag, @RequestParam(value = "businesskey", required = false) String businesskey, @RequestParam(value = "metedata", required = false) String metadata) {
        return fileSystemService.upload(file,filetag,businesskey,metadata);
    }

    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic") String pic) {
        //保存课程图片
        return fileSystemService.saveCoursePic(courseId,pic);
    }

    @Override
    public CoursePic findCoursePic(String courseId) {
        return fileSystemService.findCoursepic( courseId );
    }

    @Override
    @DeleteMapping("/coursepic/delete")
    public ResponseResult deleteCoursePic(@RequestParam("courseId") String courseId) {
        return fileSystemService.deleteCoursePic(courseId);
    }
}
