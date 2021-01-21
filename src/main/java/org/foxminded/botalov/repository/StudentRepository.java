package org.foxminded.botalov.repository;

import org.foxminded.botalov.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {


}
