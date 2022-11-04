package com.hejz.jpa.jpatx.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author:hejz 75412985@qq.com
 * @create: 2022-11-04 09:13
 * @Description: 用户
 */
@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    //建唯一索引
    @Column(name = "user_name",unique = true)
    private String username;
    private String password;
    private Integer age;
    private String roleId;

}
