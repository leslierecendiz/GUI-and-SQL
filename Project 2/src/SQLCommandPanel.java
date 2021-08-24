import javax.swing.*;
import java.awt.*;

public class SQLCommandPanel extends JPanel
{
	private JTextArea commandBox;
	private GridBagLayout gridBagLayoutCommand;
	private final String title = "Enter An SQL Command";
	private final int width = 40;
	private final int height = 6;
	
	public SQLCommandPanel()
	{
		setupPanel();
	}
	
	private void setupPanel()
	{
		commandBox = new JTextArea(height, width);
		commandBox.setLineWrap(true);
		commandBox.setEditable(false);
		gridBagLayoutCommand = new GridBagLayout();
		
		this.setBorder(BorderFactory.createTitledBorder(title));
		this.setLayout(gridBagLayoutCommand);
		
		this.add(commandBox, new GridBagConstraints(0, 0, 0, 0, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
	}
	
	public String getSQLCommand()
	{
		return commandBox.getText();
	}
	public void setSQLCommand()
	{
		commandBox.setText(null);
	}
	public void setEditable()
	{
		commandBox.setEditable(true);
	}
}
