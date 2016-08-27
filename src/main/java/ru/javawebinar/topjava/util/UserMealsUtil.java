package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<UserMeal> userMealList = new ArrayList<>();
        int dayOfMonth = mealList.get(0).getDateTime().getDayOfMonth();
        int caloriesSum = 0;
        int count = 0;

        for(UserMeal userMeal : mealList)
        {
            count++;
            if(dayOfMonth != userMeal.getDateTime().getDayOfMonth() || count == mealList.size())
            {
                if(count == mealList.size())
                {
                    userMealList.add(userMeal);
                    caloriesSum += userMeal.getCalories();
                }
                dayOfMonth = userMeal.getDateTime().getDayOfMonth();

                if(caloriesSum > caloriesPerDay)
                {
                    for(UserMeal userMeal1 : userMealList)
                    {
                        LocalTime localTime = userMeal1.getDateTime().toLocalTime();
                        if(localTime.isAfter(startTime) && localTime.isBefore(endTime))
                        {
                            listResult.add(new UserMealWithExceed(userMeal1.getDateTime(), userMeal1.getDescription(), userMeal1.getCalories(), true));
                        }
                    }
                }
                userMealList.clear();
                caloriesSum = 0;
            }
            userMealList.add(userMeal);
            caloriesSum += userMeal.getCalories();
        }
        // TODO return filtered listResult with correctly exceeded field

        return listResult;
    }
}
