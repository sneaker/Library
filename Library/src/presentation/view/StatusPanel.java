package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import presentation.model.ModelController;
import presentation.model.StatusModel;

public class StatusPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 7751541438788718395L;
	private StatusModel model;
	private JLabel label;
	
	public StatusPanel(ModelController controller) {
		model = controller.status_model;
		model.addObserver(this);
		setLayout(new BorderLayout());
		setBorder(new StatusBorder());
		label = new JLabel(model.getStatus());
		add(label, BorderLayout.WEST);
	}
	
	public void update(Observable o, Object arg) {
		label.setText(model.getStatus());
	}

	private final class StatusBorder extends EmptyBorder {
		private static final long serialVersionUID = 1L;
		
		private StatusBorder() {
			super(3, 0, 0, 0);
		}
		
		@Override
		public void paintBorder(Component c, Graphics g, int x, int y,
				int width, int height) {
			super.paintBorder(c, g, x, y, width, height);
			g.setColor(Color.GRAY);
			g.drawLine(x, y+2, x+width, y+2);
			g.setColor(Color.WHITE);
			g.drawLine(x, y+3, x+width, y+3);
		}
	}
}
