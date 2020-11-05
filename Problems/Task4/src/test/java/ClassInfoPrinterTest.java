import org.junit.jupiter.api.*;
import task4.class_info_printer.ClassInfoPrinter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ClassInfoPrinterTest {
    private final ByteArrayOutputStream outData = new ByteArrayOutputStream();
    private static final PrintStream consoleOut = System.out;

    @BeforeEach
    public void setOutputStream() {
        System.setOut(new PrintStream(outData));
    }

    @Test
    public void testOnAbsentClass() {
        Assertions.assertThrows(ClassNotFoundException.class,
                                () -> new ClassInfoPrinter("AbsentClass"),
                        "The class is not found.");
    }

    @Test
    public void testOnInterface() throws ClassNotFoundException {
        ClassInfoPrinter printer = new ClassInfoPrinter("task4.sample_classes.Duck");
        printer.print();
        String output = outData.toString().trim().replace("\r", "");
        Assertions.assertTrue(output.contains("public abstract interface task4.sample_classes.Duck") &&
                output.contains("public abstract void swim() throws java.lang.InterruptedException") &&
                output.contains("public abstract void quack(int)"));
    }

    @Test
    public void testOnClass() throws ClassNotFoundException {
        ClassInfoPrinter printer = new ClassInfoPrinter("task4.sample_classes.DuckImpl");
        printer.print();
        String output = outData.toString().trim().replace("\r", "");
        Assertions.assertTrue(output.contains("public class task4.sample_classes.DuckImpl") &&
                output.contains("extends java.lang.Object implements task4.sample_classes.Duck") &&
                output.contains("public static final enum Species") &&
                output.contains("public static final int NUM_OF_FEET") &&
                output.contains("private final java.lang.String name") &&
                output.contains("private final boolean friendly") &&
                output.contains("public DuckImpl(java.lang.String, boolean)") &&
                output.contains("public void swim() throws java.lang.InterruptedException") &&
                output.contains("public void quack(int)") &&
                output.contains("private boolean is_friendly()") &&
                output.contains("public void introduce()"));
    }

    @AfterAll
    public static void restoreStream() { System.setOut(consoleOut); }
}
