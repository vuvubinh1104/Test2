package org.payroll.departments;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.payroll.*;

public class DeleteDepartmentFrame extends JFrame {
	
	ArrayList<String> departments = Main.dbManager.getListOfDepartments();
	
	JLabel lbl_dep_name;
	JComboBox<String> combobox;
	JButton btn_cancel, btn_delete;
	Object[][] data;
	ArrayList<String> name;

	void processData() {
		data = Main.dbManager.getEmployees();
		name = new ArrayList<>();
		for (int i=0;i<data.length;i++) {
			name.add(data[i][1].toString());
		}
	}
	
	public DeleteDepartmentFrame() {
		initFrame();
		initComponents();
		addActionListeners();
		addComponentsToFrame();
	}
	
	void initFrame() {
		setTitle("Delete Department");
		setSize(330, 120);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
	}
	
	void initComponents() {
		lbl_dep_name = new JLabel("Department Name: ");
		combobox = new JComboBox<String>(departments.toArray(new String[departments.size()]));
		btn_cancel = new JButton("Cancel");
		btn_delete = new JButton("Delete");
	}
	
	void addActionListeners() {
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.dbManager.deleteDepartment(combobox.getSelectedItem().toString());
				processData();
				Main.dbManager.updateID(name);
				setVisible(false);
				dispose();
				Main.mainFrame.setVisible(false);
				(Main.mainFrame = new MainFrame(Main.userName)).setVisible(true);
			}
		});
	}
	
	void addComponentsToFrame() {
		add(lbl_dep_name);
		add(combobox);
		add(btn_cancel);
		add(btn_delete);
	}
}
