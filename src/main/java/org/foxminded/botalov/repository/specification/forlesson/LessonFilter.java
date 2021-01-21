package org.foxminded.botalov.repository.specification.forlesson;

public class LessonFilter {
    private String lessonId;
    private String minStartTime;
    private String maxStartTime;
    private String courseId;
    private String groupId;
    private String teacherId;
    private String classroom;

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getMinStartTime() {
        return minStartTime;
    }

    public void setMinStartTime(String minStartTime) {
        this.minStartTime = minStartTime;
    }

    public String getMaxStartTime() {
        return maxStartTime;
    }

    public void setMaxStartTime(String maxStartTime) {
        this.maxStartTime = maxStartTime;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
