package org.example.service;

import org.example.models.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface StudentsService {
    boolean addStudent(Student student) throws Exception;
    boolean deleteStudentById(UUID id);
    List<Student> getAllStudents();
}
