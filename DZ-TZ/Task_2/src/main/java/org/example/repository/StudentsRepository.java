package org.example.repository;

import org.example.models.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface StudentsRepository {
    boolean addStudent(Student student) throws SQLException;
    boolean deleteStudentById(UUID id) throws SQLException;
    List<Student> getAllStudents() throws Exception;
}
