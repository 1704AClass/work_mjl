package com.ningmeng.manage_course.service;

import com.ningmeng.framework.domain.course.CourseBase;
import com.ningmeng.framework.domain.course.ext.CategoryNode;
import com.ningmeng.framework.model.response.ResponseResult;

public interface CategoryService {
    CategoryNode findList();

    CourseBase getCourseBaseById(String courseId);

    ResponseResult updateCourseBase(String id, CourseBase courseBase);
}
