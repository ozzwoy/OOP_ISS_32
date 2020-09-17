import java.io.Serializable;

/**
 * main.Cat class is used as victim of serialize and deserialize processes
 */
public class Cat implements Serializable {
    private String name;
    private String color;
    private Integer age;

    public Cat(){
        this.name = "";
        this.color = "";
        this.age = 1;
    }

    public Cat(String name, String color, Integer age) {
        this.name = name;
        this.color = color;
        if(age <= 0)
            this.age = 1;
        else
            this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setAge(Integer age) {
        if(age <= 0)
            this.age = 1;
        else
            this.age = age;
    }

    public void printInfo() {
        System.out.println("Cat's name:" + this.name + "\n" + "Cat's color:" + this.color + "\n" + "Cat's age:" + this.age.toString() + "\n");
    }

}
