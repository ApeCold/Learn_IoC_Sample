package cn.bsd.learn.ioc.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)//作用在注解之上
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    //setXXXListener
    String listenerSetter();

    //new View.OnxxxListener
    Class<?> listenerType();

    //回调执行方法，onxxx()
    String callBackListener();
}
