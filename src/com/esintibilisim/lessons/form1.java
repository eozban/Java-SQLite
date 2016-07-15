package com.esintibilisim.lessons;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.esintibilisim.classes.Connector_SQLite;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.sql.ResultSet;
import java.sql.SQLException;

public class form1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connector_SQLite conn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					form1 frame = new form1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public form1() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				conn = null;
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		final JTextArea txtConsole = new JTextArea();
		txtConsole.setText("Console");
		contentPane.add(txtConsole, BorderLayout.CENTER);

		JPanel panel_top = new JPanel();
		contentPane.add(panel_top, BorderLayout.NORTH);

		JButton btnCreateDatabase = new JButton("Create/Open");
		btnCreateDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				conn = new Connector_SQLite("DBs/example.db");
			}
		});
		panel_top.add(btnCreateDatabase);

		JButton btnCreateTable = new JButton("createTable");
		btnCreateTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (conn != null) {
					String sql = "CREATE TABLE COMPANY " 
							+ "(ID 		INTEGER	PRIMARY KEY		AUTOINCREMENT,"
							+ " NAME	TEXT	NOT NULL, "
							+ " AGE		INT		NOT NULL, "
							+ " ADDRESS	CHAR(50), "
							+ " SALARY	REAL)";
					conn.execSql(sql);
				}

			}
		});
		panel_top.add(btnCreateTable);

		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (conn != null) {
					String sql = "INSERT INTO COMPANY (NAME,AGE,ADDRESS,SALARY) VALUES ('Allen', 25, 'Texas', 15000.00 );";
					conn.execSql(sql);
				}

			}
		});
		panel_top.add(btnInsert);

		JButton btnSelectQuery = new JButton("select");
		btnSelectQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (conn != null) {
					try {
						txtConsole.setText(null);
						String sql = "SELECT * FROM COMPANY;";
						ResultSet rs = conn.viewSql(sql);
						while (rs.next()) {
							int id = rs.getInt("id");
							String name = rs.getString("name");
							int age = rs.getInt("age");
							String address = rs.getString("address");
							float salary = rs.getFloat("salary");
							
							txtConsole.append(String.format("ID=%d NAME=%s AGE=%d ADDRESS=%s SALARY=%s\n", id, name, age, address, salary));
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		panel_top.add(btnSelectQuery);
		
	}

}