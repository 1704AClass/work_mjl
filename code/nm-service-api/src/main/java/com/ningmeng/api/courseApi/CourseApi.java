package com.ningmeng.api.courseApi;

import com.ningmeng.framework.domain.course.Teachplan;
import com.ningmeng.framework.domain.course.ext.CourseInfo;
import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import com.ningmeng.framework.domain.course.request.CourseListRequest;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by Administrator on 2020/2/18.
 */
@Api(value = "课程管理",description = "课程管理，提供页面的增删改查")
public interface CourseApi {
    @ApiOperation("课程计划查询")
    public TeachplanNode findTeachplanList(String courseId);
    @ApiOperation("添加课程计划")
    public ResponseResult add(Teachplan teachplan);
    @ApiOperation("查询我的课程列表")
    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest);
}
