package com.example.objectmapper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

//@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String name;
    private int age;
    @JsonProperty("phone_number")
    private String phoneNumber;

//    public String getName() {
//        return name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public User(String name, int age) {
//        this.name = name;
//        this.age = age;
//    }
}
