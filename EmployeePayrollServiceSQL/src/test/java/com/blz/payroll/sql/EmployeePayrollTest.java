package com.blz.payroll.sql;

import java.sql.SQLException;
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
}
