package com.blz.payroll.sql;

import java.time.LocalDate;

public class EmployeePayrollData {
	public int id;
	public String name;
	public double salary;
	public LocalDate startDate;
	//public String gender;

	public EmployeePayrollData(int id, String name, double salary, LocalDate startDate) {
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.startDate = startDate;
		//this.gender = gender;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeePayrollData empdata = (EmployeePayrollData) obj;
		if (id != empdata.id)
			return false;
		if (name == null) {
			if (empdata.name != null)
				return false;
		} else if (!name.equals(empdata.name))
			return false;
		if (Double.doubleToLongBits(salary) != Double.doubleToLongBits(empdata.salary))
			return false;
		if (startDate == null) {
			if (empdata.startDate != null)
				return false;
		} else if (!startDate.equals(empdata.startDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmployeePayrollData [id=" + id + ", name=" + name + ", salary=" + salary + ", start=" + startDate + "]";
	}
}
