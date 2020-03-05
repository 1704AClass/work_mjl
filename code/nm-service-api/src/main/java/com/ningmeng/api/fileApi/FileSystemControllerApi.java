package com.ningmeng.api.fileApi;

import com.ningmeng.framework.domain.course.CoursePic;
import com.ningmeng.framework.domain.filesystem.response.UploadFileResult;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2020/2/21.
 */
@Api(value = "课程图片管理",description = "课程图片管理",tags = {"课程图片管理"})
public interface FileSystemControllerApi {
    @ApiOperation("上传图片")
    public UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata);
    @ApiOperation("添加课程图片")
    public ResponseResult addCoursePic(String courseId, String pic);
    @ApiOperation("获取课程基础信息")
    public CoursePic findCoursePic(String courseId);
    @ApiOperation("删除课程图片")
    public ResponseResult deleteCoursePic(String courseId);
}
