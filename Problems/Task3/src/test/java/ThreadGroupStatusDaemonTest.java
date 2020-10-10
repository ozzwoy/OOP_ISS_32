import com.task3.ThreadGroupStatusDaemonLauncher;

public class ThreadGroupStatusDaemonTest {
    public static void main(String[] args) {
        ThreadGroup masterGroup = new ThreadGroup("Master Group");
        ThreadGroup slaveGroup = new ThreadGroup(masterGroup,"Slave Group");

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

        Thread th2 = new Thread(masterGroup, "Second") {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread th3 = new Thread(slaveGroup, "Third") {
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

        Thread daemon = ThreadGroupStatusDaemonLauncher.launch(masterGroup, 500);
        daemon.start();
    }
}
