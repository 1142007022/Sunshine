package com.jiangdong.entity;

import com.jiangdong.sunshine.annotation.Column;

public class Test {

    @Column("name")
    private String name;

    @Column("test_age")
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
