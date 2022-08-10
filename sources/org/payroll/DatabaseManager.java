package org.payroll;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DatabaseManager {
	
	String ConnectionString;
	
	Connection conn;
	Statement curs;
	
	public DatabaseManager(String db) {
		ConnectionString = "jdbc:sqlite:" + db;
		                 
		if (!(new File(db)).exists()) {
			System.out.println("Khoi tao 1");
			connectToDatabase();
			initNewDatabase();
		} else {
			connectToDatabase();
			File f1 = new File("mydb.sqlite");
			if (f1.exists() && !f1.isDirectory())
				if (f1.length() == 0) {
					System.out.println("Khoi tao 2");
					initNewDatabase();
				}
		}
	}
	
	void connectToDatabase() {
		try {
			conn = DriverManager.getConnection(ConnectionString);
			curs = conn.createStatement();
			curs.setQueryTimeout(30);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	void initNewDatabase() {
		try {
			curs.executeUpdate(
					"CREATE TABLE login_ids(id INTEGER NOT NULL PRIMARY KEY, username STRING NOT NULL, password STRING NOT NULL)"
				);
			curs.executeUpdate(
					"INSERT INTO login_ids VALUES(null, \"admin\", \"password\")"
				);
			curs.executeUpdate(
					"CREATE TABLE departments(" +
							"id INTEGER NOT NULL PRIMARY KEY," +
							"dep_name STRING NOT NULL," +
							"basic_salary INTEGER NOT NULL," +
							"da INTEGER NOT NULL," +
							"hra INTEGER NOT NULL," +
							"pf INTEGER NOT NULL," +
							"gross_salary INTEGER NOT NULL," +
							"epf INTEGER NOT NULL," +
							"lic INTEGER NOT NULL," +
							"deductions INTEGER NOT NULL," +
							"net_salary INTEGER NOT NULL" +
					")"
				);
			curs.executeUpdate(
					"CREATE TABLE employees(" +
						"id INTEGER NOT NULL PRIMARY KEY," +
						"first_name STRING NOT NULL," +
						"last_name STRING NOT NULL," +
						"email STRING NOT NULL," +
						"department STRING NOT NULL" +
					")"
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public Boolean verifyLoginId(String username) {
		try {
			return curs.executeQuery(
					"SELECT * FROM login_ids WHERE username=\"" + username + "\""
				).next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public Boolean verifyLoginId(String username, String password) {
		try {
			return curs.executeQuery(
					"SELECT * FROM login_ids WHERE username=\"" + username + "\" AND password=\"" + password + "\""
				).next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public void createLoginId(String username, String password) {
		try {
			curs.executeUpdate("INSERT INTO login_ids VALUES(null, \"" + username + "\", \"" + password + "\")");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void deleteLoginId(String username) {
		try {
			curs.executeUpdate(
					"DELETE FROM login_ids WHERE username=\"" + username + "\""
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void changePassword(String username, String newPassword) {
		try {
			curs.executeUpdate(
					"UPDATE login_ids SET password=\"" + newPassword + "\" WHERE username=\"" + username + "\""
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public Boolean existsDepartment(String dep_name) {
		try {
			return curs.executeQuery(
					"SELECT * FROM departments WHERE dep_name=\"" + dep_name + "\""
				).next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public void newDepartment(String dep_name, int basic_salary, int da_percent, int hra_percent, int pf_percent) {
		int da = (da_percent / 100) * basic_salary;
		int hra = (hra_percent / 100) * basic_salary;
		int pf = (pf_percent / 100) * basic_salary;
		int gross_salary = basic_salary + da + hra + pf;
		int epf = pf / 2;
		int lic = epf / 2;
		int deductions = epf + lic;
		int net_salary = gross_salary - deductions;
		
		try {
			curs.executeUpdate(
					"INSERT INTO departments VALUES(" +
							"null," +
							"\"" + dep_name + "\" ," +
							Integer.toString(basic_salary) + "," +
							Integer.toString(da) + "," +
							Integer.toString(hra) + "," +
							Integer.toString(pf) + "," +
							Integer.toString(gross_salary) + "," +
							Integer.toString(epf) + "," +
							Integer.toString(lic) + "," +
							Integer.toString(deductions) + "," +
							Integer.toString(net_salary) +
					")"
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void deleteDepartment(String dep_name) {
		try {
			curs.executeUpdate(
					"DELETE FROM departments WHERE dep_name=\"" + dep_name + "\""
				);
			curs.executeUpdate(
					"DELETE FROM employees WHERE department=\"" + dep_name + "\""
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void updateDepartment(String dep_name, int basic_salary, int da, int hra, int pf) {
		// deleteDepartment(dep_name);
		// newDepartment(dep_name, basic_salary, da, hra, pf);
		try {
			curs.executeUpdate(
				"Update departments SET net_salary=\"" + basic_salary + "\"WHERE dep_name=\"" + dep_name + "\""
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public ArrayList<String> getListOfDepartments() {
		ArrayList<String> lst = new ArrayList<String>();
		
		try {
			ResultSet rs = curs.executeQuery("SELECT dep_name FROM departments");
			
			while (rs.next()) {
				lst.add(rs.getString("dep_name"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return lst;
	}
	
	public int getSalary(String dep_name) {
		try {
			ResultSet rs = curs.executeQuery("SELECT net_salary FROM departments WHERE dep_name=\"" + dep_name + "\"");
			
			if (rs.next())
				return rs.getInt("net_salary");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return 0;
	}
	
	public Boolean existsEmployeeID(int id) {
		try {
			return curs.executeQuery(
					"SELECT * FROM employees WHERE id=" + Integer.toString(id)
				).next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return true;
	}
	
	public void createEmployee(String fn, String ln, String email, String department) {
		try {
			curs.executeUpdate("INSERT INTO employees VALUES(" +
					"null," +
					"\"" + fn + "\"," +
					"\"" + ln + "\"," + 
					"\"" + email + "\"," +
					"\"" + department + "\"" +
				")");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void updateID(ArrayList<String> name) {
		try {
			for (int i=0;i<name.size();i++)
				curs.executeUpdate("UPDATE employees SET id=\"" + i + "\" WHERE first_name=\"" + name.get(i) + "\"");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
		}
	}
	
	public void deleteEmployee(int id) {
		try {
			curs.executeUpdate(
					"DELETE FROM employees WHERE id=" + Integer.toString(id)
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void updateEmployee(int id, String fn, String ln, String email, String department) {
		try {
			curs.executeUpdate(
					"UPDATE employees SET " +
					"first_name=\"" + fn + "\"," +
					"last_name=\"" + ln + "\"," +
					"email=\"" + email + "\"," +
					"department=\"" + department + "\" " +
					"WHERE id=" + Integer.toString(id)
				);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public Object[][] getEmployees() {
		ArrayList<Object[]> employees = new ArrayList<Object[]>();
		ArrayList<String> department1 = new ArrayList<>();
		ResultSet rs;
		int i1 = 0;
		
		try {
			rs = curs.executeQuery(
					"SELECT * FROM employees"
				);
			
			while (rs.next()) {
				i1++;
				Object[] temp = {
					rs.getInt("id"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getString("email"),
					rs.getString("department"),
					null
				};
				
				// department1.add(rs.getString("department"));
				department1.add(temp[4].toString());
				employees.add(temp);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		for (int i=0;i<i1;i++) {
			employees.get(i)[employees.get(i).length - 1] = getSalary(department1.get(i));
		}

		return employees.toArray(new Object[employees.size()][]);
	}

	public ArrayList<String[]> getEmployeesVer2() {
		ArrayList<String[]> employees = new ArrayList<String[]>();
		ArrayList<String> department1 = new ArrayList<>();
		ResultSet rs;
		int i1 = 0;
		
		try {
			rs = curs.executeQuery(
					"SELECT * FROM employees"
				);
			
			while (rs.next()) {
				i1++;
				String[] temp = {
					rs.getInt("id")+"",
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getString("email"),
					rs.getString("department"),
					null
				};
				
				// department1.add(rs.getString("department"));
				department1.add(temp[4].toString());
				employees.add(temp);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		for (int i=0;i<i1;i++) {
			employees.get(i)[employees.get(i).length - 1] = getSalary(department1.get(i))+"";
		}

		return employees;
	}
}
