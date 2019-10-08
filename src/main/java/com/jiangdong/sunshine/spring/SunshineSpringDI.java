package com.jiangdong.sunshine.spring;

import java.util.Iterator;

public class SunshineSpringDI extends SunShineSpringContext {

    @Override
    public Object getBean(Class clazz) {
        Iterator<Class<?>> classIterator = classes.iterator();
        while (classIterator.hasNext()) {
            Class c = classIterator.next();
            if (c != null && c.equals(clazz)) {
                return c;
            }
        }
        return null;
    }

}
