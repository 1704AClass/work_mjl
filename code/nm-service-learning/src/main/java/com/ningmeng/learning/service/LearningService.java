package com.ningmeng.learning.service;

import com.ningmeng.framework.domain.course.TeachplanMediaPub;
import com.ningmeng.framework.domain.learning.GetMediaResult;
import com.ningmeng.framework.exception.ExceptionCast;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.learning.client.CourseSearchClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class LearningService {

    @Resource
    private CourseSearchClient courseSearchClient;

    //获取课程
    public GetMediaResult getMedia(String courseId,String teachplanId){
        //效验学生的学习权限，是否资费等
        //调用搜索服务查询
        TeachplanMediaPub teachplanMediaPub = courseSearchClient.getmedia(teachplanId);
        if(teachplanMediaPub == null || StringUtils.isEmpty(teachplanMediaPub.getMediaUrl())){
            //播放视频地址出错
            ExceptionCast.cast(CommonCode.FAIL);
        }
        return new GetMediaResult(CommonCode.SUCCESS,teachplanMediaPub.getMediaUrl());
    }





}
