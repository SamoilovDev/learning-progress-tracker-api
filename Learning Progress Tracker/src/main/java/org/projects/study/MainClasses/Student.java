package org.projects.study.MainClasses;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Student implements Comparable<Student> {

    private final String NAME;

    private final String SURNAME;

    private final String MAIL;

    private final int ID;

    private int pointsJava = 0;

    private int pointsDSA = 0;

    private int pointsDatabases = 0;

    private int pointsSpring = 0;

    public Student(String name, String surname, String mail, int ID) {
        this.NAME = name;
        this.SURNAME = surname;
        this.MAIL = mail;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getSURNAME() {
        return SURNAME;
    }

    public String getMAIL() {
        return MAIL;
    }

    public void addPointsJava(int points) {
        this.pointsJava += points;
    }

    public void addPointsDSA(int points) {
        this.pointsDSA += points;
    }

    public void addPointsDatabases(int points) {
        this.pointsDatabases += points;
    }

    public void addPointsSpring(int points) {
        this.pointsSpring += points;
    }

    public void setEndPoints(String course) {
        switch (course.trim().toLowerCase()) {
            case "java" -> this.pointsJava = -1;
            case "dsa" -> this.pointsDSA = -1;
            case "database" -> this.pointsDatabases = -1;
            case "spring" -> this.pointsSpring = -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return ID == student.ID && NAME.equals(student.NAME) && SURNAME.equals(student.SURNAME);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NAME, SURNAME, ID);
    }

    @Override
    public String toString() {
        return this.NAME + " " + this.SURNAME + " " + this.ID;
    }

    @Override
    public int compareTo(@NotNull Student otherStudent) {
        return Comparator.comparing(Student::getID).compare(this, otherStudent);
    }

    public ArrayList<Integer> getPointsByArrayList() {
        return new ArrayList<>(List.of(this.pointsJava, this.pointsDSA, this.pointsDatabases, this.pointsSpring));
    }

}
