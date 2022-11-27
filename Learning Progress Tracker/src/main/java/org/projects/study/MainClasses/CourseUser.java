package org.projects.study.MainClasses;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class CourseUser implements Comparable<CourseUser> {

    private final int ID;

    private final int points;

    private final double percent;

    public CourseUser(int ID, int points, double percent) {
        this.ID = ID;
        this.points = points;
        this.percent = percent;
    }

    public int getID() {
        return ID;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return String.format("%d\t%d\t\t%.1f%%", this.ID, this.points, this.percent);
    }

    @Override
    public int compareTo(@NotNull CourseUser otherUser) {
        return Comparator.comparing(CourseUser::getPoints)
                .reversed()
                .thenComparing(CourseUser::getID)
                .compare(this, otherUser);
    }
}
