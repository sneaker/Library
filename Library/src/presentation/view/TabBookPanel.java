package presentation.view;

import java.awt.BorderLayout;
import java.util.Observable;

import javax.swing.JLabel;

import presentation.model.ActionPanelModel;
import presentation.model.TabBookPanelModel;

public class TabBookPanel extends TabAbstractPanel {

	private static final long serialVersionUID = 1L;
	private TabBookPanelModel model;

	public TabBookPanel(ActionPanelModel action_panel_model) {
		super(action_panel_model);
		model = new TabBookPanelModel();
		model.addObserver(this);
		initContentPane();
	}

	private void initContentPane() {
	}

	public TabBookPanelModel getModel() {
		return model;
	}

	public void update(Observable o, Object arg) {
		add(makeBookDetailPanel(), BorderLayout.NORTH);
	}

	private JLabel makeBookDetailPanel() {
		if (model.getActiveBook() == null)
			return new JLabel(
					"Aktives Buch ist nicht verfügbar, wähle ein anderes Buch.");
		return new JLabel(
				"<html><p style='font-size:14pt; padding-left: 1.25cm; text-indent: -1cm;'><b>"
						+ getShortName(model.getActiveBook().getTitle()
								.getName()) + "</b><br />Autor: "
						+ model.getActiveBook().getTitle().getAuthor()
						+ "<br />Verlag: "
						+ model.getActiveBook().getTitle().getPublisher()
						+ "</p>");
	}

	private String getShortName(String name) {
		final String longTitle = formatTitle(name);
		double size = this.getWidth()
				/ new JLabel(longTitle).getPreferredSize().getWidth();
		String dots = "...";
		if (size > 1) {
			size = 1;
			dots = "";
		}
		return name.substring(0, (int) Math.max(0, Math.floor(name.length() * size))) + dots;
	}

	private String formatTitle(String title) {
		return "<html><p style='font-size:14pt; padding-left: 1.25cm; text-indent: -1cm;'><b>"
				+ title + "</b>";
	}
}