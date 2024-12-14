package com.project.models;

import java.io.Serializable;

public class Worker implements Serializable {
    private final String name;
    private final String surname;
    private int age;
    private String position;

    public Worker(String name, String surname, int age, String position) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.position = position;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", position='" + position + '\'';
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
