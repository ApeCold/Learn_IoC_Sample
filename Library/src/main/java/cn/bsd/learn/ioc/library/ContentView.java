package cn.bsd.learn.ioc.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//该注解作用在类之上
@Retention(RetentionPolicy.RUNTIME)//jvm在运行时通过反射获取注解的值
public @interface ContentView {
    int value();
}
