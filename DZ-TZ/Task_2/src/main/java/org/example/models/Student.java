package org.example.models;

import java.sql.SQLException;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.sql.ResultSet;

import static org.example.utils.StudentUtils.dateFormatter;

public class Student {
    public String name;
    public String lastName;
    public String fatherName;
    //public Date birthDate;
    public  String birthDate;
    public String groupNumber;
    public UUID id;

    public Student(ResultSet rs) throws SQLException, ParseException {
        this.name = rs.getString("name");
        this.lastName = rs.getString("lastName");
        this.fatherName = rs.getString("fatherName");
        //this.birthDate = dateFormatter.parse(rs.getString("birthDate"));
        this.birthDate = rs.getString("birthDate");
        this.groupNumber = rs.getString("groupNumber");
        this.id = UUID.fromString(rs.getString("id"));
    }

    public String toString() {
        return String.format("Name: %s, Last name: %s, fatherName: %s, birthDate: %s, group number: %s, id: %s",
        this.name,
        this.lastName,
        this.fatherName,
        this.birthDate,
        this.groupNumber,
        this.id
                );
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(lastName, student.lastName) &&
                Objects.equals(fatherName, student.fatherName) &&
                Objects.equals(birthDate, student.birthDate) &&
                Objects.equals(groupNumber, student.groupNumber) &&
                Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, fatherName, birthDate, groupNumber, id);
    }

}
