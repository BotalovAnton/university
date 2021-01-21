package org.foxminded.botalov.model.course;

import org.foxminded.botalov.model.department.Department;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    public Course() {
    }

    public Course(int id) {
        this.id = id;
    }

    public Course(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Course(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public Course(int id, String name, Department department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public Course(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
