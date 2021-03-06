package task4.main.class_info_printer;

import task4.main.custom_class_loader.CustomClassLoader;

import java.lang.reflect.*;

public class ClassInfoPrinter {
    private String fullName;
    private Class<?> aClass;

    public ClassInfoPrinter(String fullName) throws ClassNotFoundException {
        CustomClassLoader classLoader = new CustomClassLoader();
        aClass = classLoader.findClass(fullName);
        this.fullName = fullName;
    }

    public void print() {
        printGeneralInfo();
        System.out.println(" {");
        printSubclasses();
        printFields();
        printConstructors();
        printMethods();
        System.out.println("}");
    }

    private String getEntityKeyword(Class<?> entity) {
        return entity.isEnum() ? "enum" : (entity.isInterface() ? "interface" : "class");
    }

    private void printGeneralInfo() {
        System.out.print(Modifier.toString(aClass.getModifiers()));
        if (getEntityKeyword(aClass).equals("class")) {
            System.out.print(" class");
        }
        System.out.print(" " + aClass.getName());

        Class<?> superclass = aClass.getSuperclass();
        if (superclass != null) {
            System.out.print(" extends " + superclass.getName());
        }

        Class<?>[] interfaces = aClass.getInterfaces();
        if (interfaces.length != 0) {
            System.out.print(" implements");
            for (Class<?> anInterface : interfaces) {
                System.out.print(" " + anInterface.getName());
            }
        }
    }

    private void printSubclasses() {
        Class<?>[] subclasses = aClass.getDeclaredClasses();
        for (Class<?> subclass : subclasses) {
            System.out.print("\t" + Modifier.toString(subclass.getModifiers()) + " ");
            System.out.println(getEntityKeyword(subclass) + " " + subclass.getSimpleName());
        }
    }

    private void printFields() {
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("\t" + Modifier.toString(field.getModifiers()) + " " + field.getType().getName() +
                    " " + field.getName());
        }
    }

    private void printConstructors() {
        Constructor<?>[] constructors = aClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println();
            System.out.println("\t" + constructor.toString().replace(fullName, aClass.getSimpleName())
                                                 .replace(",", ", "));
        }
    }

    private void printMethods() {
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println();
            System.out.println("\t" + method.toString().replace(fullName + ".", "")
                                            .replace(",", ", "));
        }
    }
}
