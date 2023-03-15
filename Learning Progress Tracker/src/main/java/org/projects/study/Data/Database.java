package org.projects.study.Data;

import org.projects.study.MainClasses.Student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Database {
    private static int id = 10000;

    public static final Map<String, Student> DATABASE = new HashMap<>();

    public static void addStudent(String name, String surname, String mail) {
        Student student = Student.builder()
                .name(name)
                .surname(surname)
                .mail(mail)
                .id(id++)
                .build();
        DATABASE.put(mail, student);
    }

    public static boolean isMailTaken(String mail) {
        return DATABASE.containsKey(mail);
    }

    public static boolean isIdTaken(List<Integer> points, int id) {
        List<Student> studentList = DATABASE.values()
                .stream()
                .filter(student -> student.getId() == id)
                .toList();

        if (studentList.isEmpty()) return false;

        IntStream.range(0, 4).filter(i -> points.get(i) > 0).forEach(i -> {
            switch (i) {
                case 0 -> studentList.get(0).addPointsJava(points.get(i));
                case 1 -> studentList.get(0).addPointsDSA(points.get(i));
                case 2 -> studentList.get(0).addPointsDatabases(points.get(i));
                case 3 ->studentList.get(0).addPointsSpring(points.get(i));
            }
            Statistics.ACTIVITY_BY_COURSES.add(i);
        });
        return true;
    }

    public static List<Integer> findByID(int id) {
        List<Student> studentList = DATABASE.values()
                .stream()
                .filter(student -> student.getId() == id)
                .toList();

        return studentList.isEmpty()
                ? List.of(-1)
                : studentList.get(0).getPointsByArrayList();
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
        return DATABASE.values().stream()
                .filter(student -> student.getPointsJava() == Statistics.MAX_VALUES.get(0))
                .filter(student -> student.getPointsDSA() == Statistics.MAX_VALUES.get(1))
                .filter(student -> student.getPointsDatabases() == Statistics.MAX_VALUES.get(2))
                .filter(student -> student.getPointsSpring() == Statistics.MAX_VALUES.get(3))
                .collect(Collectors.toSet());
    }
}