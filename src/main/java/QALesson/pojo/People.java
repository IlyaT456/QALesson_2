package QALesson.pojo;

import java.util.ArrayList;

public class People {
    public String name;
    public int age;
    public ArrayList<Child> children;

    public static class Child {
        public String name;
        public int age;
    }
}
