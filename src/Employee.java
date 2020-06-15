import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;

import java.sql.*;
import javax.swing.*;

public class Employee {

	private JFrame frame;
	private JTextField jtxtEmployeeID;
	private JTable table;
	private JTextField jtxtNINumber;
	private JTextField jtxtSurname;
	private JTextField jtxtFirstname;
	private JTextField jtxtSalary;
	private JTextField jtxtAge;
	private JTextField jtxtDOB;
	private JTextField jtxtGender;
	
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	
	DefaultTableModel model = new DefaultTableModel();
	
	
	
	public void updateTable() 
	{
		conn = EmployeeData.ConnectDB();
		
		if(conn != null) 
		{
			String sql = "SELECT EmpID,NINumber,Firstname,Surname,Gender,DOB,Age,Salary";
		
			try 
			{
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] columnData = new Object[8];
				
				while(rs.next()) 
				{
					columnData[0] = rs.getString("EmpID");
					columnData[1] = rs.getString("NINumber");
					columnData[2] = rs.getString("Firstname");
					columnData[3] = rs.getString("Surname");
					columnData[4] = rs.getString("Gender");
					columnData[5] = rs.getString("DOB");
					columnData[6] = rs.getString("Age");
					columnData[7] = rs.getString("Salary");
	
					model.addRow(columnData);
				}
			}
			catch(Exception e) 
			{
				JOptionPane.showMessageDialog(null, e);
			}
	}
		
}

	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee window = new Employee();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Employee() {
		
		initialize();
		
		conn = EmployeeData.ConnectDB();
		Object col[] = {"EmpID","NINumber","Firstname","Surname","Gender","DOB","Age","Salary"};
		model.setColumnIdentifiers(col);
		updateTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1450, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Employee ID");
		lblNewLabel.setBounds(27, 26, 89, 16);
		frame.getContentPane().add(lblNewLabel);
		
		jtxtEmployeeID = new JTextField();
		jtxtEmployeeID.setBounds(149, 21, 130, 26);
		frame.getContentPane().add(jtxtEmployeeID);
		jtxtEmployeeID.setColumns(10);
		
		JButton btnNewButton = new JButton("Add New");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql = "INSERT INTO employee(EmpID,NINumber,Firstname,Surname,Gender,DOB,Age,Salary) VALUES (?,?,?,?,?,?,?,?)";
				
				try {
					pst = conn.prepareStatement(sql);
					pst.setString(1, jtxtEmployeeID.getText());
					pst.setString(2, jtxtNINumber.getText());
					pst.setString(3, jtxtFirstname.getText());
					pst.setString(4, jtxtSurname.getText());
					pst.setString(5, jtxtGender.getText());
					pst.setString(6, jtxtDOB.getText());
					pst.setString(7, jtxtAge.getText());
					pst.setString(8, jtxtSalary.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();

				}
				catch(Exception ev)
				{
					JOptionPane.showMessageDialog(null, "System Update Completed");
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {
						
						jtxtEmployeeID.getText(),
						jtxtNINumber.getText(),
						jtxtFirstname.getText(),
						jtxtSurname.getText(),
						jtxtGender.getText(),
						jtxtDOB.getText(),
						jtxtAge.getText(),
						jtxtSalary.getText(),
				});
				
				if (table.getSelectedRow() == -1)
				{
					if(table.getRowCount() == 0) {
						JOptionPane.showConfirmDialog(null, "Membership Update Confirmed", "Employee Database System", JOptionPane.OK_OPTION);
					}
				}
		        updateTable();

			}
		});
		btnNewButton.setBounds(29, 373, 130, 61);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(372, 27, 738, 308);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][]{
					
						},
				new String[] {
						"EmpID","NINumber","Firstname","Surname","Gender","DOB","Age","Salary"
				}
				));
		
		table.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		scrollPane.setViewportView(table);
		
		JLabel lblNiNumber = new JLabel("NI Number");
		lblNiNumber.setBounds(27, 59, 89, 16);
		frame.getContentPane().add(lblNiNumber);
		
		jtxtNINumber = new JTextField();
		jtxtNINumber.setColumns(10);
		jtxtNINumber.setBounds(149, 54, 130, 26);
		frame.getContentPane().add(jtxtNINumber);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(27, 125, 61, 16);
		frame.getContentPane().add(lblSurname);
		
		jtxtSurname = new JTextField();
		jtxtSurname.setColumns(10);
		jtxtSurname.setBounds(149, 120, 130, 26);
		frame.getContentPane().add(jtxtSurname);
		
		jtxtFirstname = new JTextField();
		jtxtFirstname.setColumns(10);
		jtxtFirstname.setBounds(149, 87, 130, 26);
		frame.getContentPane().add(jtxtFirstname);
		
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(27, 92, 89, 16);
		frame.getContentPane().add(lblFirstname);
		
		JLabel lblSalary = new JLabel("Salary");
		lblSalary.setBounds(27, 257, 61, 16);
		frame.getContentPane().add(lblSalary);
		
		jtxtSalary = new JTextField();
		jtxtSalary.setColumns(10);
		jtxtSalary.setBounds(149, 252, 130, 26);
		frame.getContentPane().add(jtxtSalary);
		
		jtxtAge = new JTextField();
		jtxtAge.setColumns(10);
		jtxtAge.setBounds(149, 219, 130, 26);
		frame.getContentPane().add(jtxtAge);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setBounds(27, 224, 61, 16);
		frame.getContentPane().add(lblAge);
		
		jtxtDOB = new JTextField();
		jtxtDOB.setColumns(10);
		jtxtDOB.setBounds(149, 186, 130, 26);
		frame.getContentPane().add(jtxtDOB);
		
		JLabel lblDob = new JLabel("DOB");
		lblDob.setBounds(27, 191, 61, 16);
		frame.getContentPane().add(lblDob);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(27, 158, 61, 16);
		frame.getContentPane().add(lblGender);
		
		jtxtGender = new JTextField();
		jtxtGender.setColumns(10);
		jtxtGender.setBounds(149, 153, 130, 26);
		frame.getContentPane().add(jtxtGender);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MessageFormat header = new MessageFormat("Printing in Progress");
				MessageFormat footer = new MessageFormat("Page {0, number, integer}");
				
				try {
					table.print();
				}
				catch(java.awt.print.PrinterException ev) {
					System.err.format("No Printer Found", ev.getMessage());
				}
				

			}
		});
		btnPrint.setBounds(171, 373, 130, 61);
		frame.getContentPane().add(btnPrint);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				jtxtEmployeeID.setText(null);
				jtxtNINumber.setText(null);
				jtxtFirstname.setText(null);
				jtxtSurname.setText(null);
				jtxtGender.setText(null);
				jtxtDOB.setText(null);
				jtxtAge.setText(null);
				jtxtSalary.setText(null);
				
			}
		});
		btnReset.setBounds(313, 373, 130, 61);
		frame.getContentPane().add(btnReset);
		
		JButton button_2 = new JButton("Exit");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit", "Employee Database System", 
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION ){
					System.exit(0);
				}
			}
		});
		button_2.setBounds(455, 373, 130, 61);
		frame.getContentPane().add(button_2);
		
		JLabel lblNewLabel_1 = new JLabel("Employee Database Management System");
		lblNewLabel_1.setBounds(389, 6, 334, 16);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
