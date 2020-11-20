package com.blz.payroll.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String userName = "root";
		String password = "1234";
		Connection connect;
		System.out.println("Connecting to database:" + jdbcURL);
		connect = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is successful:" + connect);
		return connect;
	}
	
	public List<EmployeePayrollData> readData() {
		String query = "SELECT * FROM employee_payroll; ";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try (Connection connect = this.getConnection();) {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				double salary = rs.getDouble("salary");
				LocalDate start = rs.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, salary, start));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
}
