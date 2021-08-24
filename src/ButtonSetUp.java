import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ButtonSetUp extends JPanel
{
	public JTextField connectionStatus;
	private JButton connect, clear, execute; 
	private FlowLayout flowLayoutButtons;
	private final int fieldLength = 53;
	private String connectionStatusString;
	private final String connectButtonText = "Connect to Database";
	private final String clearButtonText = "Clear SQL Command";
	private final String executeButtonText = "Execute SQL Command";
	
	
	
	public ButtonSetUp() {
		setupPanel();
	}

	private void setupPanel()
	{
		connectionStatus = new JTextField(fieldLength);
		connectionStatus.setText("No Connection Now");
		connectionStatus.setEditable(false);		
		connect = new JButton(connectButtonText);
		clear = new JButton(clearButtonText);
		clear.setEnabled(false);
		execute = new JButton(executeButtonText);
		execute.setEnabled(false);
		
		flowLayoutButtons = new FlowLayout();
		flowLayoutButtons.setAlignment(FlowLayout.LEADING);
		
		this.setLayout(flowLayoutButtons);
		this.add(connectionStatus);
		this.add(connect);
		this.add(clear);
		this.add(execute);
	}
	
	

	public JTextField getConnectionStatus() 
	{
		return connectionStatus;
	}

	public void setConnectionStatus(JTextField connectionStatus) 
	{
		this.connectionStatus = connectionStatus;
	}

	public String getConnectbuttontext() 
	{
		return connectButtonText;
	}

	public String getClearbuttontext() 
	{
		return clearButtonText;
	}

	public String getExecutebuttontext() 
	{
		return executeButtonText;
	}

	public void setConnectEnabled() 
	{
		connect.setEnabled(false);
	}

	public void setClearEnabled() 
	{
		clear.setEnabled(true);
	}

	public void setExecuteEnabled() 
	{
		execute.setEnabled(true);
		
	}

	public JButton getConnect() {
		return connect;
	}

	public JButton getClear() {
		return clear;
	}

	public JButton getExecute() {
		return execute;
	}
	
	
	
}