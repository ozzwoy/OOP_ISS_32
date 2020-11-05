package task4;

import task4.class_info_printer.ClassInfoPrinter;

public class Main {
    public static void main(String[] args) {
        try {
            ClassInfoPrinter printer = new ClassInfoPrinter("task4.sample_classes.DuckImpl");
            printer.print();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
