package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> listResult = new ArrayList<>();
        List<LocalDate> localDateList = mealList.stream().map(UserMeal::getDateTime).map(LocalDateTime::toLocalDate).distinct().collect(Collectors.toList());

        for(LocalDate date : localDateList)
        {
            List<UserMeal> userMealList = mealList.stream().filter((p) -> p.getDateTime().toLocalDate().equals(date)).collect(Collectors.toList());

            int caloriesSum = userMealList.stream().mapToInt(UserMeal::getCalories).sum();
            if (caloriesSum > caloriesPerDay) {
                List<UserMeal> userMealListToExceed = userMealList.stream().filter((p) -> p.getDateTime().toLocalTime().isAfter(startTime) && p.getDateTime().toLocalTime().isBefore(endTime)).collect(Collectors.toList());
                listResult.addAll(userMealListToExceed.stream().map(userMeal1 -> new UserMealWithExceed(userMeal1.getDateTime(), userMeal1.getDescription(), userMeal1.getCalories(), true)).collect(Collectors.toList()));
            }
        }

        return listResult;
    }
}
