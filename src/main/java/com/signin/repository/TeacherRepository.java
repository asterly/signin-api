package com.signin.repository;

import com.signin.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Auther: engow
 * @Date: 2019/12/6 16:29
 * @Description:
 */
public interface TeacherRepository extends MongoRepository<Teacher, Long> {
}
