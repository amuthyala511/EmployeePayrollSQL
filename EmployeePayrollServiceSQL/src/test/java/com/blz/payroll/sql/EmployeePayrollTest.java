package com.blz.payroll.sql;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.blz.payroll.sql.EmployeePayrollService.IOService;

public class EmployeePayrollTest {
	private static EmployeePayrollService employeePayrollService;

	@BeforeClass
	public static void createEmpPayrollObj() {
		employeePayrollService = new EmployeePayrollService();
		System.out.println("Welcome to the Employee Payroll Program.. ");
	}

	@Test
	public void givenEmployeePayroll_WhenRetrieved_ShouldMatchEmployeeCount() throws EmployeePayrollException, SQLException {
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Assert.assertEquals(4, employeePayrollData.size());
	}
	
	@Test
	public void givenEmployeePayroll_WhenUpdated_ShouldSyncWithDB() throws EmployeePayrollException, SQLException {
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateRecord("Terisa", 3000000);
		boolean result = employeePayrollService.checkUpdateRecordSyncWithDatabase("Terisa");
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void givenEmployeePayroll_WhenRetrieved_ForGivenRange_ShouldMatchEmployeeCount() throws EmployeePayrollException, SQLException {
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO, "2020-06-14", "2020-11-10");
		Assert.assertEquals(2, employeePayrollData.size());
	}
	
	@Test
	public void givenEmployeePayrollData_CalculateTotalOfSalaries_ForMaleEmployees_AndReturnResult() throws EmployeePayrollException {
		Assert.assertEquals(15000000, employeePayrollService.readEmployeePayrollData("Sum", "M"));
	}
	
	@Test
	public void givenEmployeePayrollData_CalculateTotalOfSalaries_ForFemaleEmployees_AndReturnResult() throws EmployeePayrollException {
		Assert.assertEquals(10000000, employeePayrollService.readEmployeePayrollData("Sum", "F"));
	}
	
	@Test
	public void givenEmployeePayrollData_ShouldCalculateAverageOfSalaries_ForMaleEmployees() throws EmployeePayrollException {
		Assert.assertEquals(5000000, employeePayrollService.readEmployeePayrollData("Avg", "M"));
	}
	
	@Test
	public void givenEmployeePayrollData_ShouldCalculateAverageOfSalaries_ForFemaleEmployees() throws EmployeePayrollException {
		Assert.assertEquals(5000000, employeePayrollService.readEmployeePayrollData("Avg", "F"));
	}
	
	@Test
	public void givenEmployeePayrollData_WhenAddedNewRecord_ShouldSyncWithDatabase() throws EmployeePayrollException, SQLException {
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.addNewEmployeeToDatabase("Alice", 9000000.00, LocalDate.now(), "M");
		boolean result = employeePayrollService.checkUpdateRecordSyncWithDatabase("Alice");
		Assert.assertEquals(true, result);
	}
}
