package presentation.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import presentation.model.ModelController;

public class TabUserLoanJPanel extends JPanel implements Observer {
	public TabUserLoanJPanel(ModelController controller) {
		setBorder(new TitledBorder("Ausleihen"));
	}

	private static final long serialVersionUID = 1311911720974043461L;

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
