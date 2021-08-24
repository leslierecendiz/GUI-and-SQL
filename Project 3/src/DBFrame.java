import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class DBFrame extends JFrame
{
	public static DBFrame appFrame = null;
	SimpleJDBC mainPanel;
	public GridBagLayout gridBagLayoutAppFrame;
	ButtonHandler bh = new ButtonHandler(this);
	public Connection c;
	ResultSetTableModel rstm;

	private DBFrame()
	{
		setupGUI();
	}
	public static DBFrame getInstance()
	{
		if(appFrame == null)
		{
			appFrame = new DBFrame();
		}
		return appFrame;
	}
	
	public void setupGUI()
	{
//		JPanel p1 = new JPanel();
		this.setTitle("SQL Client App - (MJL - CNT 4714 Spring 2021)");
		this.setPreferredSize(new Dimension(2170,360));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gridBagLayoutAppFrame = new GridBagLayout();
		mainPanel = new SimpleJDBC();
		this.setLayout(gridBagLayoutAppFrame);
		this.getContentPane().add(mainPanel, new GridBagConstraints(0,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0, 0, 0, 0),0,0));
		mainPanel.buttons.getConnect().addActionListener(bh);
		mainPanel.buttons.getExecute().addActionListener(bh);
		mainPanel.buttons.getClear().addActionListener(bh);
		pack();
	}
	
	//Button Handlers
	
	class ButtonHandler implements ActionListener
	{
		DBFrame frame;
		
		public ButtonHandler(DBFrame frame) 
		{
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getActionCommand().equalsIgnoreCase(mainPanel.buttons.getConnectbuttontext()))
			{
				try 
				{
					c = DriverManager.getConnection(mainPanel.login.getDbURL(), mainPanel.login.getUserField(), mainPanel.login.getPassField());
					
					mainPanel.buttons.connectionStatus.setText("Connected to " + c.getCatalog());
					mainPanel.buttons.setConnectEnabled();
					mainPanel.buttons.setClearEnabled();
					mainPanel.buttons.setExecuteEnabled();
					mainPanel.login.turnOffFields();
					mainPanel.sqlCommands.setEditable();
					
					
				} catch (SQLException e) 
				{
						new ErrorMessageDisplay("Error","Please Enter A Valid Username & Password",1);
				}
			}
			if(ae.getActionCommand().equalsIgnoreCase(mainPanel.buttons.getExecutebuttontext()))
			{
				
				try 
				{
					StringBuilder firstLetter = new StringBuilder();
				    firstLetter.append(mainPanel.sqlCommands.getSQLCommand().charAt(0));
				    if(firstLetter.toString().equalsIgnoreCase("s"))
					{
						mainPanel.tableDisplay.setModel(new ResultSetTableModel(c, mainPanel.sqlCommands.getSQLCommand()));
						if(mainPanel.tableDisplay.getRowCount() == 0)
						{
							throw new SQLException("No Results Found!");
						}
						mainPanel.tableDisplay.setVisible(true);
						mainPanel.scroll.setVisible(true);
						frame.setPreferredSize(new Dimension(1170, 390));
						frame.pack();
						//frame.setResizable(false);
					}
					else
					{
						new ResultSetTableModel(c, mainPanel.sqlCommands.getSQLCommand());
					}
				} 
				catch (ClassNotFoundException | SQLException | StringIndexOutOfBoundsException e)
				{
					if(e.getMessage().contains("String index out of range"))
						new ErrorMessageDisplay("Error", "Please Enter a Query", 1);
					else
						new ErrorMessageDisplay("Error", e.getMessage(),1);
				}
			}
			if(ae.getActionCommand().equalsIgnoreCase(mainPanel.buttons.getClearbuttontext()))
			{
				mainPanel.sqlCommands.setSQLCommand();
			}
		}
	}
}
