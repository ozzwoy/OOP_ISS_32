import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CatTest {
    private Cat cat1, cat2;

    @Before
    public void setUp(){
        cat1 = new Cat("Tom", "Black", 10);
        cat2 = new Cat("Mike", "White", -3);
    }

    @Test
    public void setAge() {
        Integer ten = 10;
        Integer three = 3;
        Integer one = 1;
        Assert.assertEquals (cat1.getAge(), ten);

        cat1.setAge(3);
        Assert.assertEquals (cat1.getAge(), three);

        cat1.setAge(1);
        Assert.assertEquals (cat1.getAge(), one);
    }

    @Test
    public void setAge_WRONG_VALUE() {
        Integer one = 1;

        Assert.assertEquals (cat2.getAge(), one);
    }
}