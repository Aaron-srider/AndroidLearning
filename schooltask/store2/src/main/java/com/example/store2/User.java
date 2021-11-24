package com.example.store2;

import java.io.Serializable;

class User implements Serializable {
    String name;
    String studentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
