package com.blz.payroll.sql;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class EmployeePayroll {
	public static void main(String[] args) throws EmployeePayrollException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String userName = "root";
		String password = "1234";
		Connection connection;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");
		} catch (ClassNotFoundException e) {
			throw new EmployeePayrollException(e.getMessage(), EmployeePayrollException.ExceptionType.NoSuchClass);
		}
		listDrivers();
		try {
			System.out.println("Connecting to database: "+jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, password);
			System.out.println("Connection is successful: "+connection);
		} catch (SQLException e) {
			throw new EmployeePayrollException(e.getMessage(), EmployeePayrollException.ExceptionType.DatabaseException);
		}
	}
	
	public static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while(driverList.hasMoreElements()) {
			Driver driverClass = driverList.nextElement();
			System.out.println(" "+driverClass.getClass().getName());
		}
	}
}
