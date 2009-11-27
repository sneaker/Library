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
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import presentation.model.ControllerFacade;
import presentation.model.StatusModel;

/**
 * Displays the status text and some statistics of the library.
 */
public class StatusPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 7751541438788718395L;
	private StatusModel model;
	private JLabel label;
	private JTextField statistics;
	private final ControllerFacade controller;

	public StatusPanel(ControllerFacade controller) {
		this.controller = controller;
		model = controller.status_model;
		model.addObserver(this);
		controller.library.addObserver(this);
		initGui();
	}

	private void initGui() {
		setLayout(new BorderLayout());
		setBorder(new StatusBorder());
		initStatusDisplay();
		initStatistics();
	}

	private void initStatusDisplay() {
		label = new JLabel(model.getStatus());
		add(label, BorderLayout.CENTER);
	}

	private void initStatistics() {
		statistics = new JTextField();
		statistics.setText(getStatistics());
		statistics.setBorder(new EmptyBorder(1, 1, 1, 1));
		statistics.setEditable(false);
		statistics.setToolTipText(getStatisticsTooltip());
		add(statistics, BorderLayout.EAST);
	}

	private String getStatistics() {
		return "Statistik: | V: " + controller.library.getAvailableBooks().size() + " | A: " + controller.library.getLentBooks().size() + " | D: " + controller.library.getWasteBooks().size() + " | B: " + controller.library.getDamagedBooks().size();
	}

	private String getStatisticsTooltip() {
		return "Verfügbar: " + controller.library.getAvailableBooks().size() + ", Ausgeliehen: " + controller.library.getLentBooks().size() + ", Ausgemustert: " + controller.library.getWasteBooks().size() + ", Beschädigt: " + controller.library.getDamagedBooks().size();
	}

	public void update(Observable o, Object arg) {
		updateStatus();
		updateStatistics();
	}

	private void updateStatus() {
		if (model.getTempStatus() != null)
			new TempStatusResetter(model.getTempStatus()).execute();
		label.setText(model.getStatus());
	}

	private void updateStatistics() {
		statistics.setText(getStatistics());
		statistics.setToolTipText(getStatisticsTooltip());
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
			g.drawLine(x, y + 2, x + width, y + 2);
			g.setColor(Color.WHITE);
			g.drawLine(x, y + 3, x + width, y + 3);
		}
	}

	/**
	 * Resets the status panel to the last static status after a few seconds.
	 */
	class TempStatusResetter extends SwingWorker<String, Object> {
		private static final int TEMP_STATUS_DELAY = 2000;
		private String newStatus;
		private String backup;

		private TempStatusResetter(String newStatus) {
			this.newStatus = newStatus;
			this.backup = model.getStatus();
		}

		@Override
		public String doInBackground() {
			try {
				Thread.sleep(TEMP_STATUS_DELAY);
			} catch (InterruptedException e) {
			}
			return "";
		}

		@Override
		protected void done() {
			if (!newStatus.equals(model.getTempStatus()))
				return;
			if (model.getStatus() != backup)
				model.setTempStatus(null);
			model.fireDataChanged();
		}
	}
}
