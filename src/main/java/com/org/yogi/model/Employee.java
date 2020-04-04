package com.org.yogi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/*
 * created by yogi
 * */

@Data
@Document(collection = "employee")
public class Employee implements Serializable {
    @Id
    private String empId;
    private String empName;
    private String empAddress;

}
