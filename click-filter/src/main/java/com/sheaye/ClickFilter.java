package com.sheaye;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.sheaye.binder.ClickBinder;
import com.sheaye.binder.Finder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yexinyan on 2017/5/24.
 */

public class ClickFilter {

    public static final String SUFFIX = "$$ClickBinder";
    static final Map<Class<?>, ClickBinder<Object>> BINDERS = new LinkedHashMap<>();

    private static int intervalMillis = 4000;
    private static int oldResId = 0;
    private static long oldTimeMillis = 0L;
    private static int oldSourceHashCode = 0;

    public static void bind(Activity target) {
        bind(target, target, Finder.ACTIVITY);
    }

    public static void bind(View target) {
        bind(target, target, Finder.VIEW);
    }

    public static void bind(Object target, View source) {
        bind(target, source, Finder.VIEW);
    }

    /**
     *
     * @param target @BindClick的目标，@BindClick所属类
     * @param source 执行findViewById的对象，可能是Activity或者View
     * @param finder 区分source，将source细化为Activity或者View，执行findViewById
     */
    private static void bind(Object target, Object source, Finder finder) {
        Class<?> targetClass = target.getClass();
        ClickBinder<Object> clickBinder = BINDERS.get(targetClass);
        if (clickBinder == null) {
            try {
                Class<?> bindClass = Class.forName(targetClass.getName() + SUFFIX);
                clickBinder = (ClickBinder<Object>) bindClass.newInstance();
                BINDERS.put(targetClass, clickBinder);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (clickBinder != null) {
            clickBinder.bind(target, source, finder);
        }
    }

    public static void setIntervalSeconds(int intervalSeconds) {
        intervalMillis = intervalSeconds * 1000;
    }

    public interface ClickExecutor {
        void execute(View view);
    }

    /**
     * 过滤点击事件，屏蔽频繁点击
     *
     * @param targetClassName 目标class名
     * @param source 执行findViewById的对象,解决Adapter中resId相同，convertView不同的情况
     * @param view 接收到点击事件的View
     * @param clickExecutor 点击事件执行者
     */
    public static void checkClickEvent(String targetClassName, Object source, View view, ClickExecutor clickExecutor) {
        int resId = view.getId();
        long currentMillis = System.currentTimeMillis();
        int hashCode = source.hashCode();
        if (resId != oldResId || hashCode != oldSourceHashCode || currentMillis - oldTimeMillis > intervalMillis) {
            clickExecutor.execute(view);
            oldResId = resId;
            oldTimeMillis = currentMillis;
            oldSourceHashCode = hashCode;
        } else {
            Log.e(targetClassName, "同一控件点击事件过于频繁！");
        }
    }
}
