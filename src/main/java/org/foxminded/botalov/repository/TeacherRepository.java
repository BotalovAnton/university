package org.foxminded.botalov.repository;

import org.foxminded.botalov.model.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeacherRepository extends JpaRepository<Teacher, Integer>, JpaSpecificationExecutor<Teacher> {
}
