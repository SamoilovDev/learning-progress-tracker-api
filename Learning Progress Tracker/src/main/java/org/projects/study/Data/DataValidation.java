package org.projects.study.Data;

import java.util.regex.Pattern;

public class DataValidation {

    public static boolean isEnteredBack(String userRequest, int counter) {
        if (userRequest.equals("back")) {
            System.out.printf("Total %d students have been added.%n", counter);
        }
        return userRequest.equals("back");
    }

    public static boolean checkByPattern(String expression) {
        Pattern pattern = Pattern
                .compile("[a-zA-Z]+([ '\\-]*[a-zA-Z]*)*");
        Pattern patternNot = Pattern
                .compile("(\\w*''\\w*)|(\\w*-'\\w*)|(\\w*'-\\w*)|(\\w*--\\w*)|('\\w)|(\\w+')|(-\\w)|(\\w+-)");
        return !patternNot.matcher(expression).matches() && pattern.matcher(expression).matches();
    }

    public static boolean checkMailByValid(String mail) {
        Pattern pattern = Pattern.compile("[\\w.]+@\\w+\\.\\w+");
        return pattern.matcher(mail).matches();
    }

    public static int checkAllData(String name, String surname, String mail, int count) {

        if (! checkMailByValid(mail)) {
            System.out.println("Incorrect email.");
            return count;
        } else if (Database.isMailTaken(mail)) {
            System.out.println("This email is already taken.");
            return count;
        }

        if (! checkByPattern(name) || name.length() < 2) {
            System.out.println("Incorrect first name.");
        } else if (! checkByPattern(surname) || surname.length() < 2) {
            System.out.println("Incorrect last name.");
        } else {
            Database.addStudent(name, surname, mail);
            System.out.println("The student has been added.");
            count++;
        }
        return count;
    }

}
