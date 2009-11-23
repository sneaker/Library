package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
		add(label, BorderLayout.CENTER);
		JTextField statistics = new JTextField();
		statistics.setText(getStatistics());
		statistics.setBorder(new EmptyBorder(1, 1, 1, 1));
		statistics.setEditable(false);
		statistics.setToolTipText(getStatisticsDetailed());
		add(statistics, BorderLayout.EAST);
	}

	private String getStatistics() {
		return "Statistik: | V: 23 | A: 12 | D: 1 | B: 1";
	}

	private String getStatisticsDetailed() {
		return "Verfügbar: 23, Ausgeliehen: 12, Defekt: 1, Beschädigt: 1";
	}
	
	public void update(Observable o, Object arg) {
		if (model.getTempStatus() != null)
			new TempStatusResetter(model.getTempStatus()).start();
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
	
	private final class TempStatusResetter extends Thread {
		private final String newStatus;

		private TempStatusResetter(String newStatus) {
			this.newStatus = newStatus;
		}

		public void run() {
			String before = newStatus;
			String sbefore = model.getStatus();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			if (!before.equals(model.getTempStatus()))
				return;
			if (model.getStatus() != sbefore)
			model.setTempStatus(null);
			model.fireDataChanged();
		}
	}
}
