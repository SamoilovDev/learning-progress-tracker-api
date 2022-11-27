package org.projects.study.Data;

import org.projects.study.MainClasses.CourseUser;
import org.projects.study.MainClasses.Student;

import java.util.*;
import static java.util.List.*;

public class Statistics extends Database {

    private static final List<Integer> activityByCourses = new LinkedList<>(of(0, 0, 0, 0)); // [Java - 0, DSA - 1, Databases - 2, Spring - 3]

    public static final List<Integer> MAX_VALUES = new ArrayList<>(Set.of(600, 400, 480, 550));

    private static final List<String> courses = new ArrayList<>(List.of("Java, ", "DSA, ", "Databases, ", "Spring, "));

    public static void addActivity(int index) {
        activityByCourses.set(index, activityByCourses.get(index) + 1);
    }

    public static List<Integer> getActivityByCourses() {
        return activityByCourses;
    }

    public static void buildAndPrintStrings(String most, String least, List<Integer> studentsAtCourses) {
        StringBuilder mostString = new StringBuilder(most);
        StringBuilder leastString = new StringBuilder(least);

        if (Collections.max(studentsAtCourses) == 0) {
            System.out.println(mostString.append("n/a") + "\n" + leastString.append("n/a"));
        } else {
            for (int i = 0; i < studentsAtCourses.size(); i++) {
                if (studentsAtCourses.get(i).equals(Collections.max(studentsAtCourses))) {
                    mostString.append(courses.get(i));
                } else if (studentsAtCourses.get(i).equals(Collections.min(studentsAtCourses))) {
                    leastString.append(courses.get(i));
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
        List<Integer> allPointsOfCourses = new LinkedList<>(of(0, 0, 0, 0));

        for (Student student : DATABASE.values()) {
            for (int i = 0; i < allPointsOfCourses.size(); i++) {
                allPointsOfCourses.set(i, allPointsOfCourses.get(i) + student.getPointsByArrayList().get(i));
            }
        }

        for (int i = 0; i < studentsAtCourses.size(); i++) {
            try {
                studentsAtCourses.set(i, allPointsOfCourses.get(i) / studentsAtCourses.get(i));
            } catch (ArithmeticException ex) {
                studentsAtCourses.set(i, 0);
            }
        }

        if (areAllSame(studentsAtCourses) && Collections.max(studentsAtCourses) != 0) {
            System.out.println("Easiest course: Java\nHardest course: Java");
        } else buildAndPrintStrings("Easiest course: ", "Hardest course: ", studentsAtCourses);

    }

    public static List<Integer> getStudentsAtCourses() {
        List<Integer> studentsAtCourses = new LinkedList<>(of(0, 0, 0, 0)); // [Java - 0, DSA - 1, Databases - 2, Spring - 3]

        for(Student student : DATABASE.values()) {

            for (int i = 0; i < studentsAtCourses.size(); i++) {
                if (student.getPointsByArrayList().get(i) > 0) {
                    studentsAtCourses.set(i, studentsAtCourses.get(i) + 1);
                }
            }
        }
        return studentsAtCourses;
    }

    public static List<String> endedCourses(Student student) {
        List<String> coursesEnded = new ArrayList<>();
        List<Integer> points = student.getPointsByArrayList();

        for (int i = 0; i < points.size(); i++) {
            if (Objects.equals(points.get(i), MAX_VALUES.get(i))) {
                String substring = courses
                        .get(i).substring(0, courses.get(i).length() - 2);

                coursesEnded.add(substring);
                student.setEndPoints(substring);
            }
        }

        return coursesEnded;
    }

    public static void printStatistic(String courseName) {
        switch (courseName) {
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
        List<CourseUser> courseUsers = new ArrayList<>();

        for (Student student : DATABASE.values()) {

            if (student.getPointsByArrayList().get(courseID) > 0) {
                double completedPercent =
                        (student.getPointsByArrayList().get(courseID) * 100d) / MAX_VALUES.get(courseID);

                courseUsers
                        .add(new CourseUser(student.getID(),
                                student.getPointsByArrayList().get(courseID), completedPercent));
            }

        }
        courseUsers.sort(CourseUser::compareTo);
        for (CourseUser user : courseUsers) System.out.println(user.toString());
    }

    private static boolean areAllSame(List<Integer> studentsAtCourses) {
        for (int i = 1; i < studentsAtCourses.size(); i++) {
            if (!Objects.equals(studentsAtCourses.get(i - 1), studentsAtCourses.get(i))) return false;
        }
        return true;
    }


}
