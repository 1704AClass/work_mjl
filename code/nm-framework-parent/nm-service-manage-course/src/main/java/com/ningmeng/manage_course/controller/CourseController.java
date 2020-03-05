package com.ningmeng.manage_course.controller;

import com.ningmeng.framework.domain.course.Teachplan;
import com.ningmeng.framework.domain.course.ext.CourseInfo;
import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import com.ningmeng.framework.domain.course.request.CourseListRequest;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * Created by Administrator on 2020/2/18.
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/findTeachplanList/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody Teachplan teachplan) {
        return courseService.add(teachplan);
    }

    @GetMapping("/findCourseList/page/size")
    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, @RequestBody CourseListRequest courseListRequest) {
        return courseService.findCourseList(page,size,courseListRequest);
    }
}
