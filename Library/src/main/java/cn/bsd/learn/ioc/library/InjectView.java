package cn.bsd.learn.ioc.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)//属性上
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {
    int value();
}
