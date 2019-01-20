package com.sda.database.repository;

import com.sda.database.connection.DatabaseConnection;
import com.sda.database.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EmployeeRepository implements CrudRepository<EmployeeEntity> {

    private final DatabaseConnection databaseConnection;

    @Override
    public List<EmployeeEntity> findAll() {
        List<EmployeeEntity> employeeEntities = new ArrayList<>();

        try {

            ResultSet resultSet = databaseConnection.read("select * from Employee e order by e.id");

            while (resultSet.next()) {
                EmployeeEntity employeeEntity = new EmployeeEntity();
                employeeEntity.setId(resultSet.getInt("id"));
                employeeEntity.setAge(resultSet.getInt("age"));
                employeeEntity.setName(resultSet.getString("name"));
                employeeEntity.setCity(resultSet.getString("city"));
                employeeEntity.setPhone(resultSet.getString("phone_no"));

                employeeEntities.add(employeeEntity);
            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return employeeEntities;
    }

    @Override
    public EmployeeEntity findById(long id) {
        EmployeeEntity employeeEntity = new EmployeeEntity();

        try {
            ResultSet resultSet = databaseConnection.read("select * from Employee e where e.id=" + id);

            while (resultSet.next()) {
                employeeEntity.setId(resultSet.getInt("id"));
                employeeEntity.setAge(resultSet.getInt("age"));
                employeeEntity.setName(resultSet.getString("name"));
                employeeEntity.setCity(resultSet.getString("city"));
                employeeEntity.setPhone(resultSet.getString("phone_no"));

            }

        } catch (SQLException ex) {

            ex.printStackTrace();
        }

        return employeeEntity;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public int delete(int id) {
        databaseConnection.delete("delete from employee where id=" + id);
        return id;
    }

    @Override
    public int update(EmployeeEntity updateEntity) {
        if (updateEntity.getId() > 0) {
            String updateStatement = "update Employee e set ";

            if (updateEntity.getName() != null) {
                updateStatement += " e.name = '" + updateEntity.getName() + "' ,";
            }

            if (updateEntity.getAge() > 0) {
                updateStatement += " e.age = '" + updateEntity.getAge() + "' ,";
            }

            if (updateEntity.getCity() != null) {
                updateStatement += " e.city = '" + updateEntity.getCity() + "' ,";
            }

            if (updateEntity.getPhone() != null) {
                updateStatement += " e.phone_no = '" + updateEntity.getPhone() + "' ,";
            }

            String[] updateStatements = updateStatement.split("where");
            String updateStatementPart = updateStatements[0];
            String whereStatementPart = updateStatements[1];

            int indexOfRedundandComma = 0;

            for (int i = updateStatementPart.length()-1; i > 0 ; i--) {
             //   if(updateStatementPart.charAt(i);
            }

            updateStatement += " where e.id=" + updateEntity.getId();
        } else {
            throw new IllegalArgumentException("Please provide entity id in order to update entity");
        }
        return 0;
    }
    //
// @Override
//    public int update(EmployeeEntity updateEntity) {
//        if (updateEntity.getId() > 0) {
//            String updateStatement = "update Employee e set ";
//
//            boolean isPreviousStatementExecuted = true;
//            if (updateEntity.getName() != null) {
//                updateStatement += " e.name = '" + updateEntity.getName() + "'";
//            } else {
//                isPreviousStatementExecuted = false;
//            }
//
//            if (updateEntity.getAge() > 0) {
//                if (isPreviousStatementExecuted) {
//                    updateStatement += ", e.name = " + updateEntity.getAge();
//                } else {
//                    updateStatement += " e.age = " + updateEntity.getAge() + "";
//                }
//            }
//            updateStatement = updateStatement + " where e.id=" + updateEntity.getId();
//        } else {
//            throw new IllegalArgumentException("Please provide entity id in order to update entity");
//        }
//        return 0;
//    }

    @Override
    public int create(EmployeeEntity newEntity) {
        return 0;
    }

    //TEST

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository(null);
        employeeRepository.update(EmployeeEntity.builder().id(7).age(20).name("Isa").city("Istambul").build());
    }


}
