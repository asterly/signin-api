package com.signin.repository;

import com.signin.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther: engow
 * @Date: 2019/12/9 13:22
 * @Description:
 */
public interface StudentRepository extends MongoRepository<Student, Long>  {
}
