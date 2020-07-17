package com.example.event.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity (Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity (Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

    public static void removeAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static Activity getActivity(Class<?> activity) {
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getClass().isAssignableFrom(activity)) {
                return activities.get(i);
            }
        }
        return null;
    }

    public static void removeAllExcept(Class<?>... clazz) {
        boolean isExist = false;
        for (Activity act : activities) {
            for (Class c : clazz) {
                if (act.getClass().isAssignableFrom(c)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                if (!act.isFinishing()) {
                    act.finish();
                }
            } else {
                isExist = false;
            }
        }
    }
}
