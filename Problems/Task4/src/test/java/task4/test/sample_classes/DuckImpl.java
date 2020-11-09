package task4.test.sample_classes;

public class DuckImpl implements Duck {
    public enum Species { GOLDEN, SILVER, BRONZE }
    public static final int NUM_OF_FEET = 3;
    private final String name;
    private final boolean friendly;

    public DuckImpl(String name, boolean friendly) {
        this.name = name;
        this.friendly = friendly;
    }

    @Override
    public void swim() throws InterruptedException {
        Thread.sleep(100);
    }

    @Override
    public void quack(int times) {
        for (int i = 0; i < times; i++) {
            System.out.println("Quack.");
        }
    }

    private boolean is_friendly() {
        return friendly;
    }

    public void introduce() {
        if (is_friendly()) {
            System.out.println("My name is " + name + ".");
        } else {
            System.out.println("Get away!");
        }
    }
}
