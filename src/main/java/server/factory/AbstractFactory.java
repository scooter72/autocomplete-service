package server.factory;

import server.config.Configuration;

public abstract class AbstractFactory {
    protected static <T> T getClassInstance(String key) {
        T t;
        String clazz = Configuration.getInstance().getProperty(key);
        try {
            t = (T) Class.forName(clazz).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}
