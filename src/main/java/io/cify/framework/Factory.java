package io.cify.framework;


import io.cify.framework.annotations.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Factory implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(Factory.class);

    private Object obj;


    private Factory(Object obj) {
        this.obj = obj;
    }

    public static Object get(Target target, String className) {
        try {
            String nameForClass = className + target.name();
            Class<?> clazz = Class.forName(nameForClass);
            Object obj = clazz.newInstance();
            return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
                    new Factory(obj));
        } catch (Exception e) {
            //TODO: Add logging
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result;


        Marker marker = MarkerFactory.getMarker("ACTIONS");
        if (method.getName().contains("is") || method.getName().contains("should")) {
            marker = MarkerFactory.getMarker("MATCHERS");
        }

        Method annotationMethod = obj.getClass().getMethod(method.getName(), method.getParameterTypes());
        String argsString = "";
        if (args != null && args.length > 0) {
            argsString = Arrays.stream(args).map(e -> e != null ? e.toString() : "").collect(Collectors.joining(", "));
        }

        if (annotationMethod.getAnnotation(Title.class) != null) {
            log.debug(marker, "{}", annotationMethod.getAnnotation(Title.class).value());
        } else {
            log.debug(marker, "Executing");
        }
        log.debug(marker, "{}", argsString);


        try {
            result = method.invoke(obj, args);
            log.debug(marker, "Done");
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }

        return result;
    }
}