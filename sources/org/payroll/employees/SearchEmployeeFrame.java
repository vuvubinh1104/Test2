package org.payroll.employees;

import javax.swing.*;

import org.payroll.Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchEmployeeFrame extends JFrame{
    
    JLabel lbl_fn, lbl_ln, lbl_id;
    JTextField txt_fn, txt_ln, txt_id;
    JButton btn_cancel, btn_find;
    ArrayList<String[]> data = Main.dbManager.getEmployeesVer2();

    public SearchEmployeeFrame() {
        initFrame();
		initComponents();
		configureComponents();
		addActionListeners();
		addComponentsToFrame();
    }

    void initFrame() {
        setTitle("Find Employee");
		setSize(320, 170);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
    }

    void initComponents() {
        lbl_fn = new JLabel			("First Name: ");
		lbl_ln = new JLabel			("Last Name: ");
		lbl_id = new JLabel			("          ID: ");
		txt_fn = new JTextField(18);
		txt_ln = new JTextField(18);
		txt_id = new JTextField(18);
		btn_cancel = new JButton("Cancel");
		btn_find = new JButton("Find");
    }

    void configureComponents() {
    }

    void addActionListeners() {
        btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});

        btn_find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                    String s1 = txt_fn.getText(), s2 = txt_ln.getText();
                    int s3 = Integer.parseInt(txt_id.getText());
                    if (checkTF(s1) && checkTF(s2) && checkTF(s3+"")) { // Check if text field has nothing
                        JOptionPane.showMessageDialog(
                        null,
                        "Enter at least one field !!!",
                        "Attention",
                        JOptionPane.ERROR_MESSAGE
                    );return;}
                    
                    if (!checkTF(s3+"")) {
                        JOptionPane.showMessageDialog(
                                null,
                                "ID: " + data.get(s3)[0]
                                +"\nFirst Name: " + data.get(s3)[1] 
                                + "\nLast Name: " + data.get(s3)[2]
                                + "\nEmail: " + data.get(s3)[3]
                                + "\nDepartment: " + data.get(s3)[4]
                                + "\nSalary: " + data.get(s3)[5],
                                "Found Employee",
                                JOptionPane.INFORMATION_MESSAGE
                                );
                                dispose();        
                    } 
                } catch (NumberFormatException e1) {
					System.err.println(e1.getMessage());
				} finally {
                    String s1 = txt_fn.getText(), s2 = txt_ln.getText();
                    ArrayList<String[]> employees = new ArrayList<>();
                    String s = "";

                    if (!checkTF(s1)) {
                        for (int i=0;i<data.size();i++) {
                            if (s1.equals(data.get(i)[1])) employees.add(new String[] {data.get(i)[0], data.get(i)[1], data.get(i)[2], data.get(i)[3], data.get(i)[4], data.get(i)[5]});
                        }

                        for (int i=0;i<employees.size();i++) {
                            s += "ID: " + employees.get(i)[0]
                            +"\nFirst Name: " + employees.get(i)[1] 
                            + "\nLast Name: " + employees.get(i)[2]
                            + "\nEmail: " + employees.get(i)[3]
                            + "\nDepartment: " + employees.get(i)[4]
                            + "\nSalary: " + employees.get(i)[5] + "\n";
                        }

                        if (employees.size() == 0) {
                                if (checkTF(s2)) {
                                    JOptionPane.showMessageDialog(
                                        null,
                                        "Not found!",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE,
                                        new ImageIcon("img/XD.png")
                                        );
                                    return;
                                } else {
                                    for (int i=0;i<data.size();i++) {
                                        if (s2.equals(data.get(i)[2])) employees.add(new String[] {data.get(i)[0], data.get(i)[1], data.get(i)[2], data.get(i)[3], data.get(i)[4], data.get(i)[5]});
                                    }
            
                                    for (int i=0;i<employees.size();i++) {
                                        s += "ID: " + employees.get(i)[0]
                                        +"\nFirst Name: " + employees.get(i)[1] 
                                        + "\nLast Name: " + employees.get(i)[2]
                                        + "\nEmail: " + employees.get(i)[3]
                                        + "\nDepartment: " + employees.get(i)[4]
                                        + "\nSalary: " + employees.get(i)[5] + "\n";
                                    }

                                    if (employees.size() == 0) {
                                        JOptionPane.showMessageDialog(
                                        null,
                                        "Not found!",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE,
                                        new ImageIcon("img/XD.png")
                                        );
                                        return;
                                    } else {
                                        JOptionPane.showMessageDialog(
                                        null,
                                        s,
                                        "Found Employee",
                                        JOptionPane.INFORMATION_MESSAGE
                                        );
                                        dispose();      
                                    }
                                }
                        } else {
                            JOptionPane.showMessageDialog(
                            null,
                            s,
                            "Found Employee",
                            JOptionPane.INFORMATION_MESSAGE
                            );
                            dispose();
                        }
                    }
                    
                    if (!checkTF(s2)) {
                        for (int i=0;i<data.size();i++) {
                            if (s2.equals(data.get(i)[2])) employees.add(new String[] {data.get(i)[0], data.get(i)[1], data.get(i)[2], data.get(i)[3], data.get(i)[4], data.get(i)[5]});
                        }

                        for (int i=0;i<employees.size();i++) {
                            s += "ID: " + employees.get(i)[0]
                            +"\nFirst Name: " + employees.get(i)[1] 
                            + "\nLast Name: " + employees.get(i)[2]
                            + "\nEmail: " + employees.get(i)[3]
                            + "\nDepartment: " + employees.get(i)[4]
                            + "\nSalary: " + employees.get(i)[5] + "\n";
                        }

                        if (employees.size() == 0) {
                            JOptionPane.showMessageDialog(
                            null,
                            "Not found!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE,
                            new ImageIcon("img/XD.png")
                            );
                            return;
                        } else {
                            JOptionPane.showMessageDialog(
                            null,
                            s,
                            "Found Employee",
                            JOptionPane.INFORMATION_MESSAGE
                            );
                            dispose();      
                        }
                    }
                }
			}

			public boolean checkTF(String s1) {
				if (s1.length() == 0) return true;
				return false;
			}
		});
    }

    void addComponentsToFrame() {
        add(lbl_fn);
		add(txt_fn);
		add(lbl_ln);
		add(txt_ln);
		add(lbl_id);
		add(txt_id);
		add(btn_cancel);
		add(btn_find);
    }
}
