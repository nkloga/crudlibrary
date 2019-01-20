package com.sda.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {
    private long id;
    private int age;
    private String name;
    private String city;
    private String phone;
}
