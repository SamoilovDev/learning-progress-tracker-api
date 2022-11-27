package org.projects.study.Data;

import org.projects.study.MainClasses.Student;

import java.util.*;

public class Database {
    private static int ID = 10000;

    public static final HashMap<String, Student> DATABASE = new HashMap<>();

    public static void addStudent(String name, String surname, String mail) {
        Student student = new Student(name, surname, mail, ID);
        ID++;
        DATABASE.put(mail, student);
    }

    public static boolean isMailTaken(String mail) {
        for (String takenMail : DATABASE.keySet()) {
            if (mail.equals(takenMail)) return true;
        }
        return false;
    }

    public static boolean isIDTaken(int java, int DSA, int Databases, int Spring, int id) {
        for (Student student : DATABASE.values()) {
            if (student.getID() == id) {
                if (java > 0) {
                    student.addPointsJava(java);
                    Statistics.addActivity(0);
                }
                if (DSA > 0) {
                    student.addPointsDSA(DSA);
                    Statistics.addActivity(1);
                }
                if (Databases > 0) {
                    student.addPointsDatabases(Databases);
                    Statistics.addActivity(2);
                }
                if (Spring > 0) {
                    student.addPointsSpring(Spring);
                    Statistics.addActivity(3);
                }
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Integer> findByID(int id) {
        for (Student student : DATABASE.values()) {
            if (student.getID() == id) {
                return student.getPointsByArrayList();
            }
        }
        return new ArrayList<>(List.of(-1));
    }

    public static void studentList() {
        if (DATABASE.isEmpty()) {
            System.out.println("No students found");
        } else {
            System.out.println("Students:");
            DATABASE.values()
                    .stream()
                    .sorted(Student::compareTo)
                    .forEach(System.out::println);
        }
    }

    public static Set<Student> studentsEndedCourses() {
        Set<Student> students = new LinkedHashSet<>();

        for (Student student : DATABASE.values()) {
            List<Integer> points = student.getPointsByArrayList();
            for (int i = 0; i < points.size(); i++) {
                if (Objects.equals(points.get(i), Statistics.MAX_VALUES.get(i))) students.add(student);
            }
        }
        return students;
    }
}