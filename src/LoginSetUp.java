import javax.swing.*;
import java.awt.*;

public class LoginSetUp extends JPanel {
	
	private final int rows = 4;
	private final int cols = 2;
	private final int vGap = 7;
	private final int hGap = 1;
	
	private JLabel driverLabel, urlLabel, 
		userLabel, passwordLabel;
	private JComboBox<String> driver, dbURL;
	private JTextField userField, passwordField;
	
	private GridLayout gridLayoutLogin;
	private final String title = "Enter Database Information";
	private final String jdbcDriver = "JDBC Driver:";
	private final static String driverText = "com.mysql.jdbc.Driver";
	private final String dbURLString = "Database URL:";
	private final String dbURLBoxString = "jdbc:mysql://localhost:3306/project3?useTimezone=true&serverTimezone=UTC";
	private final String username = "Username:";
	private final String passwordText = "Password:";
	private final int fieldLength = 27;
	Dimension fieldDimension = new Dimension(300, 20);
	Dimension labelDimension = new Dimension(90, 20);
	
	public LoginSetUp() {
		setupPanel();
	}
	
	private void setupPanel(){
		
		driverLabel = new JLabel(jdbcDriver);
		driverLabel.setPreferredSize(labelDimension);
		urlLabel = new JLabel(dbURLString);
		urlLabel.setPreferredSize(labelDimension);
		userLabel = new JLabel(username);
		passwordLabel = new JLabel(passwordText);
		driver = new JComboBox<String>();
		driver.addItem(driverText);
		driver.setPreferredSize(fieldDimension);
		dbURL = new JComboBox<String>();
		dbURL.addItem(dbURLBoxString);
		dbURL.setPreferredSize(fieldDimension);
		userField = new JTextField(fieldLength);
		passwordField = new JTextField(fieldLength);
//		passwordField = new JPasswordField(10);
		
		
		gridLayoutLogin = new GridLayout(rows, cols);
		this.setBorder(BorderFactory.createTitledBorder(title));
		
		this.setLayout(gridLayoutLogin);
		
		
		this.add(driverLabel);
		this.add(driver);
		this.add(urlLabel);
		this.add(dbURL);
		this.add(userLabel);
		this.add(userField);
		this.add(passwordLabel);
		this.add(passwordField);
		
		gridLayoutLogin.setVgap(vGap);
		gridLayoutLogin.setHgap(hGap);
	}


	public static String getDriver() 
	{
		return driverText;
	}

	public String getDbURL() 
	{
		return dbURLBoxString;
	}

	public String getUserField() 
	{
		return userField.getText();
	}

	public String getPassField() 
	{
		return passwordField.getText();
	}
	
	
	public void turnOffFields()
	{
		userField.setEditable(false);
		passwordField.setEditable(false);
	}
}