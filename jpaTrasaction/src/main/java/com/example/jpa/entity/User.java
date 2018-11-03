package com.example.jpa.entity;

import lombok.Data;

import javax.persistence.*;
/**
 * User实体类
 *
 * 何哥 {@link  Entity}
 * 2018/11/3 17:55
 **/
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Column(columnDefinition = "varchar(100) COMMENT '用户名'")
    private String username;
    private int age;
}
