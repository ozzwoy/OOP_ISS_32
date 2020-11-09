package task4.main.custom_class_loader;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader {
    static {
        new PropertyConfigurator().doConfigure("log4j.properties", LogManager.getLoggerRepository());
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomClassLoader.class);

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassFromFile(name);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassFromFile(String fileName) throws ClassNotFoundException {
        //LOGGER.info("ClassLoader runs.");
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                fileName.replace('.', File.separatorChar) + ".class");
        if (inputStream == null) {
            throw new ClassNotFoundException("The class is not found.");
        }
        byte[] buffer;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue;

        try {
            while (true) {
                nextValue = inputStream.read();
                if (nextValue != -1) {
                    byteStream.write(nextValue);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error while reading class from stream.", e);
        }

        buffer = byteStream.toByteArray();
        return buffer;
    }
}
