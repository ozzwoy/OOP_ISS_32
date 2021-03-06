import com.task3.ThreadGroupStatusDaemonLauncher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ThreadGroupStatusDaemonTest {
    private static final ByteArrayOutputStream outData = new ByteArrayOutputStream();
    private static final PrintStream consoleOut = System.out;

    @BeforeAll
    public static void redirectStream() {
        System.setOut(new PrintStream(outData));
    }

    @Test
    public void testWrongPeriodConstructor() {
        ThreadGroup threadGroup = new ThreadGroup("TG");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ThreadGroupStatusDaemonLauncher.launch(threadGroup, -1),
                "A period should be a positive long integer. Current value: -1");
    }

    @Test
    public void testThreadGroupWithDeepNesting() {
        ThreadGroup masterGroup = new ThreadGroup("Master Group");
        ThreadGroup slaveGroup1 = new ThreadGroup(masterGroup,"Slave Group 1");
        ThreadGroup slaveGroup2 = new ThreadGroup(masterGroup,"Slave Group 2");
        ThreadGroup slaveGroup3 = new ThreadGroup(slaveGroup1,"Slave Group 3");

        Thread th1 = new Thread(masterGroup, "First") {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread th2 = new Thread(slaveGroup1, "Second") {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread th3 = new Thread(slaveGroup2, "Third") {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread th4 = new Thread(slaveGroup3, "Fourth") {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        th1.start();
        th2.start();
        th3.start();
        th4.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread daemon = ThreadGroupStatusDaemonLauncher.launch(masterGroup, 1000);
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        daemon.interrupt();

        Assertions.assertEquals("THREAD GROUP HIERARCHY:\n" +
                "Thread Group: Master Group, max priority: 10, parent: main\n" +
                "          |-Thread: First, priority: 5, group: Master Group, state: TIMED_WAITING\n" +
                "          |-Thread Group: Slave Group 1, max priority: 10, parent: Master Group\n" +
                "          |          |-Thread: Second, priority: 5, group: Slave Group 1, state: TIMED_WAITING\n" +
                "          |          |-Thread Group: Slave Group 3, max priority: 10, parent: Slave Group 1\n" +
                "          |          |          |-Thread: Fourth, priority: 5, group: Slave Group 3, state: TIMED_WAITING\n" +
                "          |-Thread Group: Slave Group 2, max priority: 10, parent: Master Group\n" +
                "          |          |-Thread: Third, priority: 5, group: Slave Group 2, state: TIMED_WAITING\n" +
                "\n" +
                "\n" +
                "THREAD GROUP HIERARCHY:\n" +
                "Thread Group: Master Group, max priority: 10, parent: main\n" +
                "          |-Thread Group: Slave Group 1, max priority: 10, parent: Master Group\n" +
                "          |          |-Thread Group: Slave Group 3, max priority: 10, parent: Slave Group 1\n" +
                "          |          |          |-Thread: Fourth, priority: 5, group: Slave Group 3, state: TIMED_WAITING\n" +
                "          |-Thread Group: Slave Group 2, max priority: 10, parent: Master Group\n" +
                "          |          |-Thread: Third, priority: 5, group: Slave Group 2, state: TIMED_WAITING\n" +
                "\n" +
                "\n" +
                "THREAD GROUP HIERARCHY:\n" +
                "Thread Group: Master Group, max priority: 10, parent: main\n" +
                "          |-Thread Group: Slave Group 1, max priority: 10, parent: Master Group\n" +
                "          |          |-Thread Group: Slave Group 3, max priority: 10, parent: Slave Group 1\n" +
                "          |-Thread Group: Slave Group 2, max priority: 10, parent: Master Group",
                outData.toString().trim().replace("\r", ""));
    }

    @AfterAll
    public static void restoreStream() { System.setOut(consoleOut); }
}
