import com.task3.ThreadGroupStatusDaemonLauncher;

public class ThreadGroupStatusDaemonTest {
    public static void main(String[] args) {
        ThreadGroup masterGroup = new ThreadGroup("Master Group");
        ThreadGroup slaveGroup1 = new ThreadGroup(masterGroup,"Slave Group 1");
        ThreadGroup slaveGroup2 = new ThreadGroup(masterGroup,"Slave Group 2");
        ThreadGroup slaveGroup3 = new ThreadGroup(slaveGroup1,"Slave Group 3");

        Thread th1 = new Thread(masterGroup, "First") {
            @Override
            public void run() {
                try {
                    sleep(1000);
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

        ThreadGroupStatusDaemonLauncher.launch(masterGroup, 500);
    }
}
