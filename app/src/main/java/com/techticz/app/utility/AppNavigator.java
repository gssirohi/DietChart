package com.techticz.app.utility;

import android.app.Activity;
import android.content.Context;

import com.techticz.app.base.BaseActivity;
import com.techticz.app.constant.Key;
import com.techticz.app.constant.LaunchMode;
import com.techticz.app.domain.model.pojo.MealPlan;
import com.techticz.app.ui.activity.BrowseFoodActivity;
import com.techticz.app.ui.activity.BrowseMealActivity;
import com.techticz.app.ui.activity.BrowseMealPlanActivity;
import com.techticz.app.ui.activity.CreateFoodActivity;
import com.techticz.app.ui.activity.CreateMealActivity;
import com.techticz.app.ui.activity.CreateMealPlanActivity;
import com.techticz.app.ui.activity.DailyRoutineActivity;
import com.techticz.app.ui.activity.HealthDetailFormActivity;
import com.techticz.app.ui.activity.HealthGoalActivity;


/**
 * Created by gssirohi on 3/7/16.
 */
public class AppNavigator {
    private Context context;

    private AppNavigator() {
        //default constructor blocked
    }

    public AppNavigator(Context context) {
        this.context = context;
    }

    public void navigateToFoodChartActivity(Long planId) {
        if (context != null) {
            context.startActivity(DailyRoutineActivity.getCallingIntent(context, planId));
        }
    }

    public void navigateToCreateMealPlanActivity() {
        if (context != null) {
            context.startActivity(CreateMealPlanActivity.getCallingIntent(context));
        }
    }

    public static void startBrowseMealActivity(Activity context, Long mealPlanId, int day, int routineId) {
        if (context != null) {
            context.startActivityForResult(BrowseMealActivity.getCallingIntent(context, mealPlanId, day, routineId), Key.ADD_MEAL);
        }
    }

    public void navigateToHealthDetailForm() {
        if (context != null) {
            context.startActivity(HealthDetailFormActivity.getCallingIntent(context));
        }
    }

    public void navigateToHealtGoalActivity() {
        if (context != null) {
            context.startActivity(HealthGoalActivity.getCallingIntent(context));
        }
    }

    public void navigateToCreateMealActivity(Activity context, long mealPlanId) {
        if (context != null) {
            context.startActivityForResult(CreateMealActivity.getCallingIntent(context, mealPlanId,LaunchMode.CREATE), Key.CREATE_MEAL);
        }
    }

    public void navigateToMealDetailsActivity(Activity context, long planId, Long mealId) {
        if (context != null) {
            context.startActivityForResult(CreateMealActivity.getCallingIntent(context, planId,mealId, LaunchMode.VIEW), Key.VIEW_MEAL);
        }
    }
    public void navigateToBrowseFoodActivity(Activity context, Long mealId) {
        if (context != null) {
            context.startActivityForResult(BrowseFoodActivity.getCallingIntent(context, mealId), Key.BROWSE_FOOD);
        }
    }

    public void navigateToCreateFoodActivity(Activity context, int mealId) {
        if (context != null) {
            context.startActivityForResult(CreateFoodActivity.getCallingIntent(context, mealId), Key.CREATE_FOOD);
        }
    }

    public void navigateToBrowseMealPlanActivity() {
        if (context != null) {
            context.startActivity(BrowseMealPlanActivity.getCallingIntent(context));
        }
    }

}

