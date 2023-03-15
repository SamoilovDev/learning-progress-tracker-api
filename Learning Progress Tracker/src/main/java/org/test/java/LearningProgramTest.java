package org.test.java;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.projects.study.Data.DataValidation;
import org.projects.study.Data.Database;

import static org.junit.jupiter.api.Assertions.*;

class LearningProgramTest {

    @ParameterizedTest
    @ValueSource(strings = {"back"})
    @DisplayName("Check correct back commands for validity")
    void goodBackCommand(@NotNull String input) {
        int count = 0;
        assertTrue(DataValidation.isEnteredBack(input, count));
    }

    @ParameterizedTest
    @ValueSource(strings = {"bck", "b a c k", "exit", "back plz"})
    @DisplayName("Check correct back commands for validity")
     void badBackCommand(@NotNull String input) {
        int count = 0;
        assertFalse(DataValidation.isEnteredBack(input, count));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jim.", "табу", "-bad", "'gad", "alloha-", "a-'", "a-'b"})
    @DisplayName("Check wrong names for validity")
    void badName(@NotNull String input) {
        assertFalse(DataValidation.checkByPattern(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jim'O", "mama", "ba-d", "g'ad", "aa", "all oha", "a-b"})
    @DisplayName("Check correct names for validity")
    void goodName(@NotNull String input) {
        assertTrue(DataValidation.checkByPattern(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"anny.md@mail", "ma@ma1@2.3"})
    @DisplayName("Check wrong mails for validity")
    void badMail(@NotNull String input) {
        assertFalse(input.matches("^[\\w.]+@\\w+\\.\\w+$"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"anny.md@mail.edu", "mama1@2.3"})
    @DisplayName("Check correct mails for validity")
    void goodMail(@NotNull String input) {
        assertTrue(input.matches("^[\\w.]+@\\w+\\.\\w+$"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"John Smith jsmith@hotmail.com", "Anny Doolittle anny.md@mail.edu",
            "Jean-Claude O'Connor jcda123@google.net", "Mary Emelianenko 125367at@zzz90.z9",
            "Al Owen u15da125@a1s2f4f7.a1c2c5s4"})
    @DisplayName("Check correct credentials for validity")
    void goodAllData(@NotNull String input) {
        int count = 0;
        String[] studentCredentials = input.split(" ");
        String email = studentCredentials[studentCredentials.length - 1];
        String name = studentCredentials[0];
        StringBuilder surname = new StringBuilder();
        for (int i = 1; i < studentCredentials.length - 1; i++) {
            surname.append(studentCredentials[i]).append(" ");
        }
        assertEquals(count + 1, DataValidation.checkAllData(name, surname.toString().trim(), email, count));
    }

    @BeforeAll
    @CsvSource({"John, Smith, jsmith@hotmail.com", "Anny, Doolittle, anny.md@mail.edu",
            "Jean-Claude, O'Connor, jcda123@google.net"})
    static void addStudents(String name, String surname, String mail) {
        Database.addStudent(name, surname, mail);
    }

    @ParameterizedTest
    @ValueSource(strings = {"jsmith@hotmail.com", "anny.md@mail.edu", "jcda123@google.net"})
    @DisplayName("Check added mails")
    void checkAddedMails(String mail) {
        assertTrue(Database.isMailTaken(mail));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jsmith@hotmail.com", "fafmd@gmail.edu", "jcda@google.net"})
    @DisplayName("Check free mails")
    void checkFreeMails(String mail) {
        assertFalse(Database.isMailTaken(mail));
    }
}