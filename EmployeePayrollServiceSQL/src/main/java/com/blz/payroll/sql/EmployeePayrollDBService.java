package com.blz.payroll.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
	private PreparedStatement employeePayrollPreparedStatement;
	private static EmployeePayrollDBService employeePayrollDBService;
	
	private EmployeePayrollDBService() {
		
	}
	
	public static EmployeePayrollDBService getInstance() {
		if(employeePayrollDBService == null)
			employeePayrollDBService = new EmployeePayrollDBService();
		return employeePayrollDBService;
	}
	
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
	
	public List<EmployeePayrollData> readData(LocalDate start, LocalDate end) throws EmployeePayrollException {
		String query = null;
		if(start != null)
			query = String.format("SELECT * FROM employee_payroll WHERE Start BETWEEN '%s' AND '%s';", start, end);
		if(start == null)
			query = "SELECT * FROM employee_payroll";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try (Connection connect = this.getConnection();) {
			Statement statement = connect.createStatement();
			ResultSet rs = statement.executeQuery(query);
			employeePayrollList = this.getEmployeePayrollData(rs);
		} catch (SQLException e) {
			throw new EmployeePayrollException(e.getMessage(), EmployeePayrollException.ExceptionType.DatabaseException);
		}
		return employeePayrollList;
	}
	
	public int updateEmployeeData(String name, double salary) throws EmployeePayrollException {
		return this.updateEmployeeDataUsingStatement(name, salary);
	}
	private int updateEmployeeDataUsingStatement(String name, double salary) throws EmployeePayrollException {
		String query = String.format("update employeepayroll set salary = %.2f where name = '%s',", salary, name);
		try (Connection connection = this.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			return preparedStatement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<EmployeePayrollData> getEmployeePayrollData(String name) throws EmployeePayrollException {
		List<EmployeePayrollData> employeePayrollData = null;
		if (this.employeePayrollPreparedStatement == null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollPreparedStatement.setString(1, name);
			ResultSet resultSet = employeePayrollPreparedStatement.executeQuery();
			employeePayrollData = this.getEmployeePayrollData(resultSet);
		} catch (SQLException e) {
			throw new EmployeePayrollException(e.getMessage(),
					EmployeePayrollException.ExceptionType.DatabaseException);
		}
		return employeePayrollData;
	}
	
	private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) throws EmployeePayrollException {
		List<EmployeePayrollData> employeePayrollData = new ArrayList();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("ID");
				String name = resultSet.getString("Name");
				double salary = resultSet.getDouble("Salary");
				LocalDate startDate = resultSet.getDate("Start").toLocalDate();
				String gender = resultSet.getString("Gender");
				employeePayrollData.add(new EmployeePayrollData(id, name, salary, startDate, gender));
			}
		} catch (SQLException e) {
			throw new EmployeePayrollException(e.getMessage(),
					EmployeePayrollException.ExceptionType.DatabaseException);
		}
		return employeePayrollData;
	}

	private void prepareStatementForEmployeeData() throws EmployeePayrollException {
		try {
			Connection connection = this.getConnection();
			String sql = "SELECT * FROM employee_payroll WHERE Name = ?";
			employeePayrollPreparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new EmployeePayrollException(e.getMessage(),
					EmployeePayrollException.ExceptionType.DatabaseException);
		}
	}
	
	public int readDataPayroll(String total, String gender) throws EmployeePayrollException {
		int sum = 0;
		String query = String.format("select %s(Salary) from employeepayroll where gender = '%s' group by gender;", total, gender);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			sum = resultSet.getInt(1);
		} catch (SQLException e) {
			throw new EmployeePayrollException(e.getMessage(),
					EmployeePayrollException.ExceptionType.DatabaseException);
		}
		return sum;
	}
	
	public int readDataAvgPayroll(String avg, String gender) throws EmployeePayrollException {
		int avgTotal = 0;
		String query = String.format("select %s(Salary) from employeepayroll where gender = '%s' group by gender;", avg, gender);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			avgTotal = resultSet.getInt(1);
		} catch (SQLException e) {
			throw new EmployeePayrollException(e.getMessage(),
					EmployeePayrollException.ExceptionType.DatabaseException);
		}
		return avgTotal;
	}
}
