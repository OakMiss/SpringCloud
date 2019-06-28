package com.link.coding;

/**
 * Created by Oak on 2018/7/20.
 * Description: 链式编程
 */
public class LinkCoding {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello ").append("World");

        Person person = new Person();
        person.setAge(6).setName("zhang").setSex("男");
        System.out.println(person);
    }
}
