package org.example.service;

import org.example.models.Student;
import org.example.repository.StudentsRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class StudentsServiceImp implements StudentsService{
    StudentsRepository repository;

    public StudentsServiceImp(
            StudentsRepository repository
    ){
        this.repository = repository;
    }


    @Override
    public boolean addStudent(Student student) throws Exception {
        boolean executed = false;
        boolean check =  studentExists(student);
        if (executed != check)
        {
            return true;
        }
            try {
                executed = repository.addStudent(student);
                int x = 0;
            } catch (SQLException e) {
                System.out.println("Failed to insert student! " + e.getMessage());
            }
            return executed;
    }
    @Override
    public boolean deleteStudentById(UUID id) {
        boolean executed = false;

        try{
            executed = repository.deleteStudentById(id);
        } catch (SQLException e) {
            System.out.println("Failed to delete student! " + e.getMessage());
        }

        return executed;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = Collections.emptyList();

        try{
            students = repository.getAllStudents();
        } catch (SQLException e) {
            System.out.println("Failed to fetch students list! " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return students;
    }
    public boolean studentExists(Student student) throws Exception {
        List<Student> students = repository.getAllStudents();

        for (Student existingStudent : students) {
            if (existingStudent.id.equals(student.id)) {
                System.out.println("Такой студент уже есть");
                return true;
            }
        }
        return false;
    }
}
