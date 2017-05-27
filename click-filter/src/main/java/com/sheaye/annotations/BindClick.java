package com.sheaye.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yexinyan on 2017/5/24.
 */

@Retention(RetentionPolicy.CLASS)// 编译时注解
@Target(ElementType.METHOD)// 方法注解
public @interface BindClick {
    int[] value();
}
