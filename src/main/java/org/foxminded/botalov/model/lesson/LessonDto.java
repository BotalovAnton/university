package org.foxminded.botalov.model.lesson;

import org.foxminded.botalov.utilities.validate.CheckAddedEntity;
import org.foxminded.botalov.utilities.validate.CheckUpdatedEntity;
import org.foxminded.botalov.utilities.validate.annotation.ValidLessonTime;

import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.time.Instant;
import java.util.Objects;

@ValidLessonTime
public class LessonDto {
    @Null(groups = CheckAddedEntity.class)
    @Positive(groups = CheckUpdatedEntity.class)
    private Integer id;

    private Instant start;
    private Instant end;

    @Positive
    private int courseId;

    private String courseName;

    @Positive
    private int groupId;

    private String groupName;

    @Positive
    private int teacherId;

    private String teacherFirstName;
    private String teacherLastName;

    @Positive
    private int classroom;

    public LessonDto() {
    }

    public LessonDto(Integer id) {
        this.id = id;
    }

    public LessonDto(Instant start, Instant end, int courseId, int groupId, int teacherId, int classroom) {
        this.start = start;
        this.end = end;
        this.courseId = courseId;
        this.groupId = groupId;
        this.teacherId = teacherId;
        this.classroom = classroom;
    }

    public LessonDto(Integer id, Instant start, Instant end, int courseId, int groupId, int teacherId, int classroom) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.courseId = courseId;
        this.groupId = groupId;
        this.teacherId = teacherId;
        this.classroom = classroom;
    }

    public LessonDto(Integer id,
                     Instant start,
                     Instant end,
                     int courseId,
                     String courseName,
                     int groupId,
                     String groupName,
                     int teacherId,
                     String teacherFirstName,
                     String teacherLastName,
                     int classroom) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.courseId = courseId;
        this.courseName = courseName;
        this.groupId = groupId;
        this.groupName = groupName;
        this.teacherId = teacherId;
        this.teacherFirstName = teacherFirstName;
        this.teacherLastName = teacherLastName;
        this.classroom = classroom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    public void setTeacherFirstName(String teacherFirstName) {
        this.teacherFirstName = teacherFirstName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
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
        LessonDto lessonDto = (LessonDto) o;
        return courseId == lessonDto.courseId && groupId == lessonDto.groupId && teacherId == lessonDto.teacherId && classroom == lessonDto.classroom && Objects.equals(id, lessonDto.id) && Objects.equals(start, lessonDto.start) && Objects.equals(end, lessonDto.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, courseId, groupId, teacherId, classroom);
    }
}
