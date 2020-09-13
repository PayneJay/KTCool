package com.ktcool.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//声明作用域是作用于类，还可以是方法、成员变量、构造函数等等
@Target(ElementType.TYPE)
/*
 * 注解的时机有三类
 * RetentionPolicy.SOURCE：源码期
 * RetentionPolicy.CLASS：编译期（生成class文件）
 * RetentionPolicy.RUNTIME：运行期（）
 */
//声明这个注解时机在编译时
@Retention(RetentionPolicy.CLASS)
public @interface Router {
    //路由地址
    String path();
}