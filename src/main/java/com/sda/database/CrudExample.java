package com.sda.database;

import com.sda.database.connection.DatabaseConnection;
import com.sda.database.connection.MysqlDatabaseConnection;
import com.sda.database.constant.DatabaseType;
import com.sda.database.entity.EmployeeEntity;
import com.sda.database.property.ConnectionProperty;
import com.sda.database.repository.EmployeeRepository;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class CrudExample {

    public static void main(String[] args) {


        DatabaseConnection mysqlDatabaseConnection = new MysqlDatabaseConnection();
        ConnectionProperty connectionProperty = mysqlDatabaseConnection.getConnectionProperties(DatabaseType.MYSQL);

        System.out.println(
                String.format("Driver Name: %s , Database Name: %s, Username: %s, Password: ... ",
                        connectionProperty.getDriverName(), connectionProperty.getDatabaseUrl()
                        , connectionProperty.getUsername()));

        mysqlDatabaseConnection.open(connectionProperty);

        EmployeeRepository employeeRepository = new EmployeeRepository(mysqlDatabaseConnection);
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        for (EmployeeEntity employeeEntity : employeeEntities) {
            System.out.println(String.format("Id: %d, Name: %s, City:%s, Phone:%s, Age:%d"
                    , employeeEntity.getId(), employeeEntity.getName(), employeeEntity.getCity()
                    , employeeEntity.getPhone(), employeeEntity.getAge()));
        }

        log.info("usage of findById");
        EmployeeEntity employeeEntity = employeeRepository.findById(4L);
        System.out.println(employeeEntity.getName() != null ? employeeEntity.toString() : "No employee found");

//        log.info("deleting record from the table ");
//        employeeRepository.delete(2);

//        log.info("updating specific employee from table");
//        employeeRepository.update(EmployeeEntity.builder().id(6).name("Kolja Kloga").city("Tallinn").age(32).build());
//
//        log.info("adding new employee");
//        employeeRepository.create(EmployeeEntity.builder().name("Vasja Builder").city("Johvi").age(33).phone("4342342342").build());
//        employeeEntity = employeeRepository.findById(7);
//        System.out.println(employeeEntity.getName() != null ? employeeEntity.toString() : "No employee found");

        log.info("counting amount of employees");
        long employeeCount = employeeRepository.count();
        System.out.println(employeeCount != 0 ? "Amount of employees is: " + employeeCount : "No employee found");

    }
}
