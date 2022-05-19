package package_default;

public class Person implements Comparable<Person>{
    private int age;
    private String name;

    public Person(int age, String name){
        this.age = age;
        this.name = name;
    }

    public int compareTo(Person other){
        return Integer.compare(age, other.age);
    }

    public String toString(){
        return String.format("%s, %d",name, age);
    }
}
