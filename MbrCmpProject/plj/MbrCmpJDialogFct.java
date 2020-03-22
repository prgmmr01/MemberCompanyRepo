package com.MbrCmpProject.plj;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class MbrCmpJDialogFct extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNAIC;
	private JTextField txtState;
	private JTextField txtYear;
	private JFormattedTextField txtAmount;
	private JFormattedTextField txtFactor;
	int seq; String stateCd;
	PreparedStatement preStmt = null;
	Connection connection = null;
	static DatabaseVariables para;
	private NumberFormat amtFmt, pctFmt;
	MaskFormatter taxFmt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MbrCmpJDialogFct dialog = new MbrCmpJDialogFct(new JFrame(), para);
		dialog.setVisible(true);
	}

	/**
	 * Create the dialog.
	 */
	public MbrCmpJDialogFct(JFrame MbrCmpPercentage, DatabaseVariables pctVar) {
		setTitle("Percentages");

		setBounds(300, 300, 300, 271);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// Labels...
		JLabel lblNAIC = new JLabel("NAIC:");
		lblNAIC.setBounds(10, 20, 75, 25);
		lblNAIC.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		contentPanel.add(lblNAIC);
		JLabel lblState = new JLabel("State:");
		lblState.setBounds(10, 50, 75, 25);
		lblState.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		contentPanel.add(lblState);
		JLabel lblYear = new JLabel("Year:");
		lblYear.setBounds(10, 80, 75, 25);
		lblYear.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		contentPanel.add(lblYear);
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(10, 110, 75, 25);
		lblAmount.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		contentPanel.add(lblAmount);
		JLabel lblFactor = new JLabel("Percent:");
		lblFactor.setBounds(10, 140, 75, 25);
		lblFactor.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		contentPanel.add(lblFactor);

		// Setup field formatting...
		setUpFormats();

		// TextFields...
		txtNAIC = new JTextField();
		txtNAIC.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNAIC.setEditable(false);
		txtNAIC.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtNAIC.setBounds(100, 20, 85, 25);
		txtNAIC.setColumns(10);
		txtNAIC.setText(String.valueOf(pctVar.getDbNAIC()));
		contentPanel.add(txtNAIC);
		txtState = new JTextField();
		txtState.setHorizontalAlignment(SwingConstants.LEFT);
		txtState.setEditable(false);
		txtState.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtState.setBounds(100, 50, 150, 25);
		txtState.setColumns(10);
		switch (pctVar.getDbStateCode()) {
		case "D":
			txtState.setText("Delaware");
			break;
		case "P":
			txtState.setText("Pennsylvania");
			break;
		case "W":
			txtState.setText("West Virginia");
			break;
		}
		contentPanel.add(txtState);
		txtYear = new JTextField();
		txtYear.setHorizontalAlignment(SwingConstants.RIGHT);
		txtYear.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtYear.setBounds(100, 80, 85, 25);
		txtYear.setColumns(10);
		txtYear.setText(String.valueOf(pctVar.getDbPremiumYear()));
		contentPanel.add(txtYear);
		txtAmount = new JFormattedTextField(amtFmt);
		txtAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		txtAmount.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtAmount.setBounds(100, 110, 85, 25);
		txtAmount.setColumns(10);
		txtAmount.setValue(new Double(pctVar.getDbPremiumAmount()));
		contentPanel.add(txtAmount);
		txtFactor = new JFormattedTextField(pctFmt);
		txtFactor.setHorizontalAlignment(SwingConstants.RIGHT);
		txtFactor.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtFactor.setBounds(100, 140, 85, 25);
		txtFactor.setColumns(10);
		txtFactor.setValue(new Float(pctVar.getDbPremiumPercentage()));
		contentPanel.add(txtFactor);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						seq = pctVar.getDbPremiumSequence();
						stateCd = pctVar.getDbStateCode();
						UpdatePremium();
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void UpdatePremium() {
		// Connect to database...
		connection = sqlConnection.dbConnector();

		// Query based on options select from MbrCompanyMain menu...
		try {
			String sqlQuery = "Update MemberPremiums Set " + "PremiumYear=? " + ",PremiumAmount=? "
					+ ",PremiumPercentage=? " + "Where PremiumNAIC=? and PremiumStateCode=? and PremiumSequence=?";
			// Access preStmt parameters: inOutParm->Parameter->inutDTV->impl->value->value
			preStmt = connection.prepareStatement(sqlQuery);
			preStmt.setInt(1, Integer.parseInt(txtYear.getText()));
			preStmt.setFloat(2, Float.parseFloat(txtAmount.getText()));
			preStmt.setFloat(3, Float.parseFloat(txtFactor.getText()));
			preStmt.setInt(4, Integer.parseInt(txtNAIC.getText()));
			preStmt.setString(5, stateCd);
			preStmt.setInt(6, seq);
			preStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}

		}
	}

	// Create and set up number formats. These objects also parse numbers input by user.
	private void setUpFormats() {
		amtFmt = NumberFormat.getNumberInstance();
		amtFmt.setMinimumFractionDigits(2);
		pctFmt = NumberFormat.getNumberInstance();
		pctFmt.setMinimumFractionDigits(3);
		try {
			taxFmt = new MaskFormatter("##-#######");
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Invalid Tax Format");
		}
	}
}
