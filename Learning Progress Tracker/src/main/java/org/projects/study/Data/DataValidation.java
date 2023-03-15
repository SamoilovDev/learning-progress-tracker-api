package org.projects.study.Data;

public class DataValidation {

    public static boolean isEnteredBack(String userRequest, int counter) {
        if (userRequest.equals("back")) {
            System.out.printf("Total %d students have been added.%n", counter);
        }
        return userRequest.equals("back");
    }

    public static boolean checkByPattern(String expression) {
        return ! expression.matches("^(\\w*''\\w*)|(\\w*-'\\w*)|(\\w*'-\\w*)|(\\w*--\\w*)|('\\w)|(\\w+')|(-\\w)|(\\w+-)$")
                && expression.matches("^[a-zA-Z]+([ '\\-]*[a-zA-Z]*)*$");
    }

    public static int checkAllData(String name, String surname, String mail, int count) {
        if (! mail.matches("^[\\w.]+@\\w+\\.\\w+$")) {
            System.out.println("Incorrect email.");
        } else if (Database.isMailTaken(mail)) {
            System.out.println("This email is already taken.");
        } else if (! checkByPattern(name) || name.length() < 2) {
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
