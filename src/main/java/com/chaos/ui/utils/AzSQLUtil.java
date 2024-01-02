package com.chaos.ui.utils;

import com.chaos.ui.pojo.AzSQLConfiguration;
import com.chaos.ui.pojo.CustomerVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AzSQLUtil {

    private static Connection connection;
    private static AzSQLUtil reader;
    private AzSQLUtil(){
    }

    private static String getConnectionString(AzSQLConfiguration properties) {
        return String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
                properties.getHostname(), properties.getDatabase(),properties.getUsername(), properties.getPassword());
    }

    public List<CustomerVO> readAllRecords(AzSQLConfiguration properties) throws SQLException {
        System.out.println("Read all records");
        List<CustomerVO> customers = new ArrayList<>();
        ResultSet rs = null;
        try(Connection connection = DriverManager.getConnection(getConnectionString(properties))) {
            rs = connection.createStatement().executeQuery("SELECT * FROM SalesLT.Customer;");
            while(rs.next())
            {
                CustomerVO customer = new CustomerVO();
                customer.setCustomerId(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setCompanyName(rs.getString("CompanyName"));
                customer.setEmail(rs.getString("EmailAddress"));
                customer.setPhone(rs.getString("Phone"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();;
            throw new SQLException(e);
        }
        return customers;
    }

    public List<CustomerVO> readAllRecordsTop10(AzSQLConfiguration properties) throws SQLException {
        System.out.println("Read all records");
        List<CustomerVO> customers = new ArrayList<>();
        ResultSet rs = null;
        try(Connection connection = DriverManager.getConnection(getConnectionString(properties))) {
            rs = connection.createStatement().executeQuery("SELECT TOP 10 * FROM SalesLT.Customer;");
            while(rs.next())
            {
                CustomerVO customer = new CustomerVO();
                customer.setCustomerId(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setCompanyName(rs.getString("CompanyName"));
                customer.setEmail(rs.getString("EmailAddress"));
                customer.setPhone(rs.getString("Phone"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();;
            throw new SQLException(e);
        }
        return customers;
    }

    public void insertData(AzSQLConfiguration properties) throws SQLException {
        System.out.println("Insert data");
        try(Connection connection = DriverManager.getConnection(getConnectionString(properties))) {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO SalesLT.todo (id, description, details, done) VALUES (?, ?, ?, ?);");
            for (int i = 1; i <= 1000; i++) {
                insertStatement.setLong(1, i);
                insertStatement.setString(2, "Description");
                insertStatement.setString(3, "Details");
                insertStatement.setBoolean(4, false);
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new SQLException(e);
        }

    }

    public static AzSQLUtil getInstance() {
        if(reader == null) {
            reader = new AzSQLUtil();
        }
        return reader;
    }
}
