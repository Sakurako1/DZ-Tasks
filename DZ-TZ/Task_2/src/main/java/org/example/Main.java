package org.example;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.FilterHolder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        try (
                        // create a database connection
                        Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
                        Statement statement = connection.createStatement();
                        )
        {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            System.out.println("Dropping table students...");
            statement.executeUpdate("drop table if exists students");
            System.out.println("Dropping table students... DONE");
            System.out.println("Creating table students...");
            statement.executeUpdate("create table students (name string, lastName string, fatherName string, birthDate string, groupNumber string, id string)");
            System.out.println("Creating table students... DONE");
            connection.close();
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            e.printStackTrace(System.err);
        }
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addFilter(new FilterHolder(new CorsFilter()), "/*", null);
        handler.setContextPath("/");
        server.setHandler(handler);

        handler.addServlet(new ServletHolder(new StudentServlet()), "/students");

        server.start();
        server.join();
        }
    }
