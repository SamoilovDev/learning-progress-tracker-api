package org.projects.study.Data;

import org.projects.study.MainClasses.CourseUser;
import org.projects.study.MainClasses.Student;

import java.util.*;
import java.util.stream.IntStream;

public class Statistics extends Database {

    public static final List<Integer> ACTIVITY_BY_COURSES = new LinkedList<>(List.of(0, 0, 0, 0)); // [Java - 0, DSA - 1, Databases - 2, Spring - 3]

    public static final List<Integer> MAX_VALUES = List.of(600, 400, 480, 550);

    public static final List<String> COURSES = List.of("Java, ", "DSA, ", "Databases, ", "Spring, ");

    public static void buildAndPrintStrings(String most, String least, List<Integer> studentsAtCourses) {
        StringBuilder mostString = new StringBuilder(most);
        StringBuilder leastString = new StringBuilder(least);

        if (Collections.max(studentsAtCourses) == 0) {
            System.out.println(mostString.append("n/a") + "\n" + leastString.append("n/a"));
        } else {
            for (int i = 0; i < studentsAtCourses.size(); i++) {
                if (studentsAtCourses.get(i).equals(Collections.max(studentsAtCourses))) {
                    mostString.append(COURSES.get(i));
                } else if (studentsAtCourses.get(i).equals(Collections.min(studentsAtCourses))) {
                    leastString.append(COURSES.get(i));
                }
            }
            mostString.delete(mostString.length() - 2, mostString.length());

            if (areAllSame(studentsAtCourses)) {
                leastString.delete(0, leastString.length() - 1).append(least).append("n/a");
            } else leastString.delete(leastString.length() - 2, leastString.length());

            System.out.println(mostString + "\n" + leastString);
        }
    }

    public static void easiestAndHardest() {
        List<Integer> studentsAtCourses = getStudentsAtCourses();
        List<Integer> allPointsOfCourses = new ArrayList<>(List.of(0, 0, 0, 0));

        DATABASE.values().forEach(student -> {
            for (int i = 0; i < allPointsOfCourses.size(); i++) {
                allPointsOfCourses.set(i, allPointsOfCourses.get(i) + student.getPointsByArrayList().get(i));
            }
        });

        IntStream.range(0, studentsAtCourses.size()).forEach(i -> {
            try {
                studentsAtCourses.set(i, allPointsOfCourses.get(i) / studentsAtCourses.get(i));
            } catch (ArithmeticException ex) {
                studentsAtCourses.set(i, 0);
            }
        });


        if (areAllSame(studentsAtCourses) && Collections.max(studentsAtCourses) != 0) {
            System.out.println("Easiest course: Java\nHardest course: Java");
        } else buildAndPrintStrings("Easiest course: ", "Hardest course: ", studentsAtCourses);

    }

    public static List<Integer> getStudentsAtCourses() {
        List<Integer> studentsAtCourses = new LinkedList<>(List.of(0, 0, 0, 0)); // [Java - 0, DSA - 1, Databases - 2, Spring - 3]

        DATABASE.values().forEach(
                student -> IntStream.range(0, studentsAtCourses.size())
                        .filter(i ->student.getPointsByArrayList().get(i) > 0)
                        .forEach(i -> studentsAtCourses.set(i, studentsAtCourses.get(i) + 1))
        );

        return studentsAtCourses;
    }

    public static List<String> endedCourses(Student student) {
        List<Integer> points = student.getPointsByArrayList();

        return IntStream.range(0, points.size())
                .filter(i -> Objects.equals(points.get(i), MAX_VALUES.get(i)))
                .mapToObj(i -> {
                    String substring = COURSES.get(i).substring(0, COURSES.get(i).length() - 2);
                    student.setEndPoints(substring);
                    return substring;
                })
                .toList();
    }

    public static void printStatistic(String courseName) {
        switch (courseName.trim().toLowerCase()) {
            case "java" -> {
                System.out.println("""
                        Java
                        id    points    completed""");
                getInfoAboutCourse(0);
            }
            case "dsa" -> {
                System.out.println("""
                        DSA
                        id    points    completed""");
                getInfoAboutCourse(1);
            }
            case "databases" -> {
                System.out.println("""
                        Databases
                        id    points    completed""");
                getInfoAboutCourse(2);
            }
            case "spring" -> {
                System.out.println("""
                        Spring
                        id    points    completed""");
                getInfoAboutCourse(3);
            }
            default -> System.out.println("Unknown course.");
        }
    }

    private static void getInfoAboutCourse(int courseID) {
        DATABASE.values().stream()
                .filter(student -> student.getPointsByArrayList().get(courseID) > 0)
                .map(student -> {
                    double completedPercent = (student.getPointsByArrayList().get(courseID) * 100d) / MAX_VALUES.get(courseID);
                    return CourseUser.builder()
                            .id(student.getId())
                            .points(student.getPointsByArrayList().get(courseID))
                            .percent(completedPercent)
                            .build();
                })
                .sorted(CourseUser::compareTo)
                .forEach(System.out::println);
    }

    private static boolean areAllSame(List<Integer> studentsAtCourses) {
        List<Integer> sortedNumsOfStudents = studentsAtCourses.stream()
                .sorted()
                .toList();
        return Objects.equals(sortedNumsOfStudents.get(0), sortedNumsOfStudents.get(sortedNumsOfStudents.size() - 1));
    }


}
