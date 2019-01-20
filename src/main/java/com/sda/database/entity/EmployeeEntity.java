package com.sda.database.entity;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeEntity {
    private long id;
    private int age;
    private String name;
    private String city;
    private String phone;
}
