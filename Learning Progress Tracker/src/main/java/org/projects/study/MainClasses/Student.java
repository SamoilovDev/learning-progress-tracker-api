package org.projects.study.MainClasses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Comparable<Student> {

    private int id;

    private String name;

    private String surname;

    private String mail;

    @Builder.Default
    private int pointsJava = 0;

    @Builder.Default
    private int pointsDSA = 0;

    @Builder.Default
    private int pointsDatabases = 0;

    @Builder.Default
    private int pointsSpring = 0;

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
    public int compareTo(@NotNull Student otherStudent) {
        return Comparator.comparing(Student::getId).compare(this, otherStudent);
    }

    public ArrayList<Integer> getPointsByArrayList() {
        return new ArrayList<>(List.of(this.pointsJava, this.pointsDSA, this.pointsDatabases, this.pointsSpring));
    }

}
