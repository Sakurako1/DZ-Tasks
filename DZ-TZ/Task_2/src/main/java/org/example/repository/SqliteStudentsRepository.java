package org.example.repository;

import org.example.models.Student;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteStudentsRepository implements StudentsRepository {

    Connection connection;
    String tableName;
    Statement statement;

    public SqliteStudentsRepository(Connection connection, String tableName) throws SQLException {
        this.connection = connection;
        this.tableName = tableName;
        this.statement = connection.createStatement();
    }

    @Override
    public boolean addStudent(Student student) throws SQLException {
        String stmt = String.format(
                "INSERT INTO %s (name, lastName, fatherName, birthDate, groupNumber, id) VALUES('%s', '%s', '%s', '%s', '%s', '%s')",
                tableName,
                student.name,
                student.lastName,
                student.fatherName,
                student.birthDate,
                student.groupNumber,
                student.id.toString()
        );

        System.out.println("Executing " + stmt);

        return statement.execute(stmt);
    }

    @Override
    public boolean deleteStudentById(UUID id) throws SQLException {
        boolean deleted = statement.execute(String.format("DELETE FROM %s WHERE id = '%s'", tableName,id));
        return deleted;
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new java.util.ArrayList<Student>(Collections.EMPTY_LIST);

        try {
            ResultSet rs = statement.executeQuery(String.format("select * from %s", tableName));

            while (rs.next()) {
                System.out.println(rs);
                Student student = new Student(rs);
                students.add(student);
            }
        } catch (Exception e) {
            System.out.println("Exception while fetching students: " + e.getMessage());
        }

        return students;
    }
    
}
