package org.foxminded.botalov.repository;

import org.foxminded.botalov.model.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer>, JpaSpecificationExecutor<Lesson> {

    @Query("SELECT student.group.lessons FROM Student student WHERE student.id = :studentId")
    List<Lesson> getStudentLessons(@Param("studentId")Integer studentId);

    List<Lesson> getLessonsByTeacherId(Integer id);
}
