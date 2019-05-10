package cn.bsd.learn.ioc.library;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectManager {
    public static void inject(Activity activity) {
        //布局的注入
        injectLayout(activity);
        //控件的注入
        injectViews(activity);
        //事件的注入
        injectEvents(activity);
    }

    //布局的注入
    private static void injectLayout(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取这个类上的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if(contentView != null){
            //获取这个注解的值
            int layoutId = contentView.value();
            //执行方法:setContentView(R.layout.activity_main);

            //第一种方法：
            activity.setContentView(layoutId);

            try {
                Method method = clazz.getMethod("setContentView", int.class);
                //执行方法
                method.invoke(activity,layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //控件的注入
    private static void injectViews(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取类的所有属性
        Field[] fields = clazz.getDeclaredFields();
        //循环，拿到每个属性
        for (Field field : fields) {
            //获取每个属性上的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            //获取注解的值
            if (injectView!=null) {//并不是每个注解都有值
                int viewId = injectView.value();
                //执行方法:findViewById(xxxid);
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, viewId);
                    //讲方法执行返回值view赋值给全局某属性
                    field.setAccessible(true);//设置private访问权限为true
                    field.set(activity,view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //事件的注入
    private static void injectEvents(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取类的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //获取每个方法的注解
            Annotation[] annotations = method.getAnnotations();
            //遍历每个方法的多个注解
            for (Annotation annotation : annotations) {
                //获取OnClick的注解上的注解类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType!=null) {
                    //通过EventBase获取上个重要规律
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    //事件的三个规律
                    String listenerSetter = eventBase.listenerSetter();
                    Class<?> listenerType = eventBase.listenerType();
                    String callBackListener = eventBase.callBackListener();

                    //注解的值
                    try {
                        //通过annotationType获取onClick注解的value值
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        //执行value方法获得注解的值
                        int[] viewIds = (int[]) valueMethod.invoke(annotation);

                        ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                        handler.addMethodMap(callBackListener,method);
                        //打包之后，代理处理后续工作
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);


//                        //R.id.tv,R.id.btn
                        for (int viewId : viewIds) {
                            //控件的赋值过程
                            View view = activity.findViewById(viewId);
                            //获取方法 Method method = clazz.getMethod("findViewById", int.class);
                            Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                            //执行方法
                            setter.invoke(view,listener);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
