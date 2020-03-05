package com.ningmeng.manage_course.dao;

import com.ningmeng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
/**
 * Created by Administrator on 2020/2/18.
 */
@Mapper
public interface TeachplanMapper {
    TeachplanNode findTeachplanList(String courseId);
}
