package com.sda.database.repository;

import com.sda.database.connection.DatabaseConnection;
import com.sda.database.connection.MysqlDatabaseConnection;
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
        long result = 0;
        ResultSet resultSet = databaseConnection.read("select count(id) from employee");
        try {
            while(resultSet.next()) {
                result = resultSet.getInt("count(id)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(int id) {
        databaseConnection.delete("delete from employee where id=" + id);
        return id;
    }

    @Override
    public int update(EmployeeEntity updatedEntity) {
        if (updatedEntity.getId() > 0) {
            String updateStatement = "update Employee e set ";

            if (updatedEntity.getName() != null) {
                updateStatement += " e.name = '" + updatedEntity.getName() + "' ,";
            }

            if (updatedEntity.getAge() > 0) {

                updateStatement += " e.age = " + updatedEntity.getAge() + " ,";
            }

            if (updatedEntity.getCity() != null) {

                updateStatement += " e.city = '" + updatedEntity.getCity() + "' ,";
            }

            if (updatedEntity.getPhone() != null) {

                updateStatement += " e.phone_no = '" + updatedEntity.getCity() + "' ,";
            }

            updateStatement += " where e.id=" + updatedEntity.getId();

            String[] updateStatements = updateStatement.split("where");
            String updateStatementPart = updateStatements[0];
            String whereStatementPart = updateStatements[1];

            updateStatementPart = updateStatementPart.substring(0, updateStatementPart.length() - 2);
            String updateQuery = updateStatementPart + " where " + whereStatementPart;

            databaseConnection.update(updateQuery);
        } else {
            throw new IllegalArgumentException("Please provide entity id in order to update entity");
        }
        return 0;
    }

    @Override
    public int create(EmployeeEntity newEntity) {

        String updateStatement = "insert into employee values(default, ";

        if (newEntity.getName() != null) {
            updateStatement += " '" + newEntity.getName() + "' ,";
        }

        if (newEntity.getAge() > 0) {

            updateStatement += " " + newEntity.getAge() + " ,";
        }

        if (newEntity.getCity() != null) {

            updateStatement += " '" + newEntity.getCity() + "' ,";
        }

        if (newEntity.getPhone() != null) {

            updateStatement += " '" + newEntity.getPhone() + "' ,";
        }

        String updateQuery = updateStatement.substring(0, updateStatement.length() - 2) + ")";

        databaseConnection.create(updateQuery);
        return 0;
    }
}
