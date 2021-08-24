import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import java.sql.Connection;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class SimpleJDBC extends JPanel 
{
	ButtonSetUp buttons;
	GridBagLayout gridBagLayoutMain;
	JTable tableDisplay;
	JScrollPane scroll;
	LoginSetUp login;
	SQLCommandPanel sqlCommands;
	public Connection c;
	
//	final static boolean shouldFill = true; 

	public static void main(String[] args) {
		DBFrame db = DBFrame.getInstance();
		db.setVisible(true);
//		DBFrame frame = new DBFrame();
	}
	
	public SimpleJDBC(){
        setupGui();
    }
	
	private void setupGui(){
		
		gridBagLayoutMain = new GridBagLayout();
		login = new LoginSetUp();
		sqlCommands = new SQLCommandPanel();
		buttons = new ButtonSetUp();
		tableDisplay = new JTable();
		tableDisplay.setAutoCreateRowSorter(true);
		
//		this.setLayout(new GridBagLayout());  
//		GridBagConstraints c = new GridBagConstraints();  
//		if (shouldFill) {  
//		//natural height, maximum width  
//		c.gridwidth = GridBagConstraints.HORIZONTAL;  
//		}  
		
		this.add(login, new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0, GridBagConstraints.NORTHWEST, 
				GridBagConstraints.BOTH, new Insets(0, 0 , 0, 0), 0, 0));
		this.add(sqlCommands);
		this.add(buttons, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, 
					GridBagConstraints.NONE, new Insets(0, 0 , 0, 0), 0, 0));
		buttons.setOpaque(true);
		buttons.setBackground(Color.orange);
		
		scroll = new JScrollPane(tableDisplay);
		scroll.setPreferredSize(new Dimension(1250, 230));
		this.add(scroll, new GridBagConstraints(0, 3, 0, 0, 0.0, 0.0, GridBagConstraints.PAGE_END, 
				GridBagConstraints.NONE, new Insets(0, 0 , 0, 0), 0, 0));
		tableDisplay.setVisible(false);
		scroll.setVisible(false);
	}
}
