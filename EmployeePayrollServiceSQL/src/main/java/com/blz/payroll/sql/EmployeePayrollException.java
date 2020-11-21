package com.blz.payroll.sql;

public class EmployeePayrollException extends Exception {
	enum ExceptionType {
		DatabaseException, NoSuchClass
	}
	
	public ExceptionType type;
	
	public EmployeePayrollException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}
}
