package org.foxminded.botalov.model.lesson;

import org.foxminded.botalov.model.teacher.Teacher;
import org.foxminded.botalov.model.course.Course;
import org.foxminded.botalov.model.group.Group;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "start_time")
    private Instant start;

    @Column(name = "end_time")
    private Instant end;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    private int classroom;

    public Lesson() {
    }

    public Lesson(int id) {
        this.id = id;
    }

    public Lesson(int id, Instant start, Instant end, Course course, Group group, Teacher teacher, int classroom) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.course = course;
        this.group = group;
        this.teacher = teacher;
        this.classroom = classroom;
    }

    public Lesson(Instant start, Instant end, Course course, Group group, Teacher teacher, int classroom) {
        this.start = start;
        this.end = end;
        this.course = course;
        this.group = group;
        this.teacher = teacher;
        this.classroom = classroom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getClassroom() {
        return classroom;
    }

    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return id == lesson.id &&
                classroom == lesson.classroom &&
                Objects.equals(start, lesson.start) &&
                Objects.equals(end, lesson.end) &&
                Objects.equals(course, lesson.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, course, classroom);
    }
}
