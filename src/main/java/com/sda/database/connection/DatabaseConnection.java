package com.sda.database.connection;

import com.sda.database.constant.StatementType;
import com.sda.database.property.ConnectionProperty;
import lombok.extern.java.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

@Log
public abstract class DatabaseConnection {

    private Connection connection = null;

    public ConnectionProperty getConnectionProperties(final String fileName) {

        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ConnectionProperty.builder()
                .databaseUrl(properties.getProperty("database.url"))
                .driverName(properties.getProperty("database.driver"))
                .username(properties.getProperty("database.username"))
                .password(properties.getProperty("database.password")).build();
    }

    public void open(final ConnectionProperty connectionProperty) {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(connectionProperty.getDatabaseUrl(), connectionProperty.getUsername(), connectionProperty.getPassword());
                log.info("Connection established over the driver " + connectionProperty.getDriverName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    abstract void connect();

    public void close() {
        try {
            if (!connection.isClosed()) {
                connection.close();
                log.info("Connection is closed... ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet read(final String sql) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            return statement.executeQuery(sql);

        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }

    public int delete(final String sql) {
        return executeQuery(sql, StatementType.DELETE);
    }

    public int update(final String sql) {
        return executeQuery(sql, StatementType.UPDATE);
    }

    public int create(final String sql) {
        return executeQuery(sql, StatementType.INSERT);
    }

    private int executeQuery(final String sql, final StatementType statementType) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            if (result > 0) {
                if (StatementType.DELETE.equals(statementType)) {
                    log.info(result + " row is affected and deleted.");
                } else if (StatementType.INSERT.equals(statementType)) {
                    log.info(result + " row is affected and inserted.");
                } else if (StatementType.UPDATE.equals(statementType)) {
                    log.info(result + " row is affected and updated.");
                }
                return result;
            } else {
                throw new NoSuchFieldException();
            }
        } catch (SQLException e) {
            throw new IllegalStateException();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
