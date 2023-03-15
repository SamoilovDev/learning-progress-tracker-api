package org.projects.study.MainClasses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseUser implements Comparable<CourseUser> {

    private int id;

    private int points;

    private double percent;

    @Override
    public int compareTo(@NotNull CourseUser otherUser) {
        return Comparator.comparing(CourseUser::getPoints)
                .reversed()
                .thenComparing(CourseUser::getId)
                .compare(this, otherUser);
    }

}
