package org.example;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.models.Student;
import org.example.repository.SqliteStudentsRepository;
import org.example.repository.StudentsRepository;
import org.example.service.StudentsService;
import org.example.service.StudentsServiceImp;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    private StudentsService studentsService;
    private StudentsRepository repo;

    public void init() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            String tableName = "students";
            this.repo = new SqliteStudentsRepository(connection, tableName);
            this.studentsService = new StudentsServiceImp(repo);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to db " + e.getMessage());
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Fetching students list ...");
        try {
            List<Student> listStudent = this.studentsService.getAllStudents();

            System.out.println("list size: " + listStudent.size());

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            StringBuilder jsonResponseBuilder = new StringBuilder("[");
            Gson gson = new Gson();
            for (int i = 0; i < listStudent.size(); i++) {
                Student student = listStudent.get(i);
                String json = gson.toJson(student);
                jsonResponseBuilder.append(json);

                if (i < listStudent.size() - 1) {
                    jsonResponseBuilder.append(",");
                }
            }
            jsonResponseBuilder.append("]");

            response.getWriter().write(jsonResponseBuilder.toString());

        } catch (Exception e) {
            System.out.println("Exception while fetching students list: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if ("add".equals(action)) {
                this.insertStudent(request, response);
            } else if ("delete".equals(action)) {
                this.deleteStudent(request, response);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader bufferedReader = request.getReader();
            Gson gson = new Gson();
            Student student = gson.fromJson(bufferedReader, Student.class);
            System.out.println("student: " + student);
            this.studentsService.addStudent(student);
            response.sendRedirect("students");
        } catch (Exception e) {
            System.out.println("Exception while inserting student: " + e.getMessage());
        }
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader bufferedReader = request.getReader();
            Gson gson = new Gson();
            UUID student = gson.fromJson(bufferedReader, UUID.class);
            System.out.println("student: " + student);
            // UUID uniqueNumber = UUID.fromString(request.getParameter("id"));
            this.studentsService.deleteStudentById(student);
            response.sendRedirect("students");
        } catch (Exception e) {
            System.out.println("Exception while deleting student: " + e.getMessage());
        }
    }
}
