package com.example.ronquillo.ronquillojakeexam2;

public class Student {
    //Long id;
    String first, last;
    Integer result;

    public Student(String first, String last, Integer result) {
        this.first = first;
        this.last = last;
        this.result = result;
    }

    public Student() {
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }


    public Integer getResult() {
        return result;
    }
}