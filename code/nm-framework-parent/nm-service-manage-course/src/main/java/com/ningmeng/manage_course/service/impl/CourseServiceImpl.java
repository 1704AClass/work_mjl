package com.ningmeng.manage_course.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ningmeng.framework.domain.course.CourseBase;
import com.ningmeng.framework.domain.course.Teachplan;
import com.ningmeng.framework.domain.course.ext.CourseInfo;
import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import com.ningmeng.framework.domain.course.request.CourseListRequest;
import com.ningmeng.framework.exception.CustomExceptionCast;
import com.ningmeng.framework.model.response.CommonCode;
import com.ningmeng.framework.model.response.QueryResponseResult;
import com.ningmeng.framework.model.response.QueryResult;
import com.ningmeng.framework.model.response.ResponseResult;
import com.ningmeng.manage_course.dao.CourseBaseRepository;
import com.ningmeng.manage_course.dao.CourseMapper;
import com.ningmeng.manage_course.dao.TeachplanDao;
import com.ningmeng.manage_course.dao.TeachplanMapper;
import com.ningmeng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * Created by Administrator on 2020/2/18.
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private TeachplanMapper teachplanMapper;
    @Autowired
    private TeachplanDao teachplanDao;
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public TeachplanNode findTeachplanList(String courseId) {
        return teachplanMapper.findTeachplanList(courseId);
    }
    public String getTeachplan(String courseid) {
        Optional<CourseBase> courseBase = courseBaseRepository.findById(courseid);
        if(!courseBase.isPresent())
        {
            return null;
        }
        CourseBase courseBase1 = courseBase.get();
        List<Teachplan> list = teachplanDao.findByParentidAndCourseid("0", courseid);
        if (list==null||list.size()==0)
        {
            Teachplan teachplan = new Teachplan();
            teachplan.setCourseid(courseid);
            teachplan.setPname(courseBase1.getName());
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setStatus("0");
            teachplanDao.save(teachplan);
            return teachplan.getId();
        }
        Teachplan teachplan = list.get(0);
        return teachplan.getId();
    }
    @Override
    public ResponseResult add(Teachplan teachplan) {
        if (teachplan!=null|| StringUtils.isEmpty(teachplan.getCourseid())|| StringUtils.isEmpty(teachplan.getPname()))
        {
            CustomExceptionCast.cast(CommonCode.FAIL);
        }
        String courseid = teachplan.getCourseid();
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid))
        {
            parentid = getTeachplan(courseid);
        }
        Optional<Teachplan> byId = teachplanDao.findById(parentid);
        if(!byId.isPresent())
        {
            CustomExceptionCast.cast(CommonCode.FAIL);
        }
        Teachplan teachplan1 = byId.get();
        String grade = teachplan1.getGrade();
        teachplan.setParentid(parentid);
        teachplan.setStatus("0");
        if (grade.equals("1"))
        {
            teachplan.setGrade("2");
        }else if (grade.equals("2"))
        {
            teachplan.setGrade("3");
        }
        teachplan.setCourseid(teachplan1.getCourseid());
        teachplanDao.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @Transactional
    public QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest) {
        if (courseListRequest==null)
        {
            CustomExceptionCast.cast(CommonCode.FAIL);
        }
        PageHelper.startPage(page,size);
        Page<CourseInfo> courseListpage = courseMapper.findCourseListpage(courseListRequest);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(courseListpage.getResult());
        queryResult.setTotal(courseListpage.getTotal());
        return new QueryResponseResult<CourseInfo>(CommonCode.SUCCESS,queryResult);
    }
}
