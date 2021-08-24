
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrorMessageDisplay 
{
	private JFrame oFrame;
	private String errorMessage;
	private String title;
	private int choice;
	
	
	public ErrorMessageDisplay(String title, String errorMessage, int type)
	{
		this.oFrame = oFrame;
		this.errorMessage = errorMessage;
		this.title = title;
		
		switch(type)
		{
			case 1:
				displayError();
				break;
			
			case 2:
				displayInfo();
				break;
			
			case 3:
				displayYesNo();
				
			case 4:
				commandDenied();
			default:
				break;
		}
	}

	private void displayError(){
		JOptionPane.showMessageDialog(oFrame, errorMessage, title,  JOptionPane.ERROR_MESSAGE);
	}
	
	private void displayInfo(){
		JOptionPane.showMessageDialog(oFrame, errorMessage, title, JOptionPane.INFORMATION_MESSAGE); 
	}
	
	private void displayYesNo(){
		Object[] options = {"Yes", "No"};
		
		oFrame.setVisible(false);
		setChoice(JOptionPane.showOptionDialog(oFrame, errorMessage, title, JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,options[1]));
		//oFrame.setVisible(true);
	}
	
	private void commandDenied() {
		JOptionPane.showMessageDialog(oFrame, errorMessage, title,  JOptionPane.ERROR_MESSAGE);
	}
	
	
	public int getChoice()
	{
		return choice;
	}
	
	
	public void setChoice(int choice)
	{
		this.choice = choice;
	}
}
