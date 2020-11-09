package task4.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import task4.main.custom_class_loader.CustomClassLoader;

public class CustomClassLoaderTest {
    private static final CustomClassLoader loader = new CustomClassLoader();

    @Test
    public void testOnAbsentClass() {
        Assertions.assertThrows(ClassNotFoundException.class,
                () -> loader.findClass("AbsentClass"),
                "The class is not found.");
    }

    @Test
    public void testOnItself() throws ClassNotFoundException {
        Class<?> foundClass = loader.findClass(this.getClass().getName());
        Assertions.assertNotNull(foundClass);
    }
}
