package com.link.coding;

/**
 * Created by Oak on 2018/7/20.
 * Description:
 */
public class Person {
    private String name;
    private String sex;
    private int age;

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public Person setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    public Person() {
    }

    public Person add(String name){
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
