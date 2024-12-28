package org.craftarix.monitoring.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
@UtilityClass
public class ReflectionUtil {
    public static void setField(Object object, String field, Object fieldValue) {
        Field f = null;
        Class<?> clazz = object.getClass();
        try {
            f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(object, fieldValue);
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        finally {
            f.setAccessible(false);
        }
    }
}
