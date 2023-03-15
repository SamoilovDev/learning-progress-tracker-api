package org.projects.study.MainClasses;

import org.projects.study.Data.DataValidation;
import org.projects.study.Data.Database;
import org.projects.study.Data.Statistics;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public enum Interface {

    WAIT_FOR_INPUT {
        @Override
        protected Interface select() {
            System.out.print("""
                    To get information about commands enter 'help'
                    Write command:\040""");

            return switch (INPUT.nextLine().trim().toLowerCase()) {
                case ""             -> EMPTY_INPUT.select();
                case "help"         -> HELP.select();
                case "notify"       -> NOTIFY.select();
                case "list"         -> STUDENTS_LIST.select();
                case "find"         -> FIND.select();
                case "statistics"   -> STATISTICS.select();
                case "add points"   -> ADD_POINTS.select();
                case "add students" -> ADD_STUDENTS.select();
                case "back"         -> ERROR_BACK.select();
                case "exit"         -> EXIT.select();
                default             -> ERROR.select();
            };
        }
    },

    HELP {
        @Override
        protected Interface select() {
            System.out.println("""
                    'add students' - adding a new student
                    'add points' - add learning progress points to student
                    'statistics' - get statistics of students
                    'find' - find a student
                    'list' - get all students
                    'notify' - get new notifications
                    'exit' - close the program""");
            return WAIT_FOR_INPUT.select();
        }
    },

    STUDENTS_LIST {
        @Override
        protected Interface select() {
            Database.studentList();
            return WAIT_FOR_INPUT.select();
        }
    },

    ADD_POINTS {
        @Override
        protected Interface select() {
            System.out.println("Enter an id and points or 'back' to return");

            while (true) {
                List<String> stringUserID = new ArrayList<>(List.of(INPUT.nextLine().split(" ")));
                String user = stringUserID.get(0);
                int userID = 0;

                if (user.equals("back")) return WAIT_FOR_INPUT.select();

                List<Integer> points = Interface.integersInInput(stringUserID);
                if (points.size() > 1 && points.get(0) >= 10000) userID = points.remove(0);
                Interface.printResultOfAdding(points, stringUserID, userID, user);
            }
        }
    },

    ADD_STUDENTS {
        @Override
        protected Interface select() {
            int counter = 0;
            System.out.println("Enter student credentials or 'back' to return:");

            while (true) {
                String userRequest = INPUT.nextLine();
                if (! DataValidation.isEnteredBack(userRequest, counter)) {
                    List<String> studentCredentials = List.of(userRequest.split(" "));

                    if (studentCredentials.size() >= 3) {
                        String email = studentCredentials.get(studentCredentials.size() - 1);
                        String name = studentCredentials.get(0);
                        StringBuilder surname = new StringBuilder();

                        for (int i = 1; i < studentCredentials.size() - 1; i++) {
                            surname.append(studentCredentials.get(i)).append(" ");
                        }

                        counter = DataValidation.checkAllData(name, surname.toString().trim(), email, counter);
                    } else {
                        System.out.println("Incorrect credentials.");
                    }

                } else {
                    return WAIT_FOR_INPUT.select();
                }
            }
        }
    },

    FIND {
        @Override
        protected Interface select() {
            System.out.println("Enter an id or 'back' to return:");
            while (true) {
                String userID = INPUT.nextLine();
                List<String> splitUserID = new ArrayList<>(List.of(userID.split(" ")));
                int ID = 0;

                for (String string : splitUserID) {
                    try {
                        ID = Integer.parseInt(string);
                        break;
                    } catch (Exception ex) {
                        if (string.equals("back")) return WAIT_FOR_INPUT.select();
                    }
                }

                List<Integer> points = Database.findByID(ID); // [Java - 0, DSA - 1, Databases - 2, Spring - 3]

                if (points.get(0) == -1) {
                    System.out.printf("No student is found for id=%s.%n", userID);
                } else {
                    System.out.printf("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d",
                            userID, points.get(0), points.get(1), points.get(2), points.get(3));
                }
            }
        }
    },
    NOTIFY {
        @Override
        protected Interface select() {
            AtomicInteger count = new AtomicInteger(0);

            Database.studentsEndedCourses().forEach(student -> {
                Statistics.endedCourses(student).forEach(course -> System.out.printf(
                        """
                            To: %s
                            Re: Your Learning Progress
                            Hello, %s %s! You have accomplished our %s course!
                        """, student.getMail(), student.getName(), student.getSurname(), course)
                );
                count.incrementAndGet();
            });

            System.out.printf("Total %d students have been notified. ", count.get());
            return WAIT_FOR_INPUT.select();
        }
    },

    STATISTICS {
        @Override
        protected Interface select() {
            System.out.println("Type the name of a course to see details or 'back' to quit:");

            Statistics.buildAndPrintStrings(
                    "Most popular: ", "Least popular: ", Statistics.getStudentsAtCourses()
            );
            Statistics.buildAndPrintStrings(
                    "Highest activity: ", "Lowest activity: ", Statistics.ACTIVITY_BY_COURSES
            );
            Statistics.easiestAndHardest();

            while (true) {
                String input = INPUT.nextLine().trim().toLowerCase();
                if (input.equals("back")) return WAIT_FOR_INPUT.select();
                Statistics.printStatistic(input);
            }
        }
    },

    ERROR_BACK {
        @Override
        protected Interface select() {
            System.out.println("Enter 'exit' to exit the program");
            return WAIT_FOR_INPUT.select();
        }
    },

    EXIT {
        @Override
        protected Interface select() {
            System.out.println("Bye!");
            return EXIT;
        }
    },

    ERROR {
        @Override
        protected Interface select() {
            System.out.println("Unknown command!");
            return WAIT_FOR_INPUT.select();
        }
    },

    EMPTY_INPUT {
        @Override
        protected Interface select() {
            System.out.println("No input.");
            return WAIT_FOR_INPUT.select();
        }
    };

    protected static final Scanner INPUT = new Scanner(System.in);

    protected abstract Interface select();

    private static List<Integer> integersInInput(List<String> stringUserID) {
        return stringUserID.stream()
                .map(Integer::parseInt)
                .toList();
    }

    private static void printResultOfAdding(List<Integer> points, List<String> stringUserID, int userID, String user) {
        if (! points.get(points.size() - 1).toString().equals(stringUserID.get(stringUserID.size() - 1))
                || points.size() != 4
                || Collections.min(points) < 0) {
            System.out.println("Incorrect points format.");
        } else if (userID < 10000 || ! Database.isIdTaken(points, userID)) {
            System.out.printf("No student is found for id=%s.%n", user);
        }  else {
            System.out.println("Points updated.");
        }
    }
}
