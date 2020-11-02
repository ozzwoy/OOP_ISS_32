package task4.class_info_printer;

import task4.custom_class_loader.CustomClassLoader;

public class ClassInfoPrinter {
    private Class<?> aClass;

    public ClassInfoPrinter(String name) throws ClassNotFoundException {
        ClassLoader classLoader = new CustomClassLoader();
        aClass = classLoader.loadClass(name);
    }


}
