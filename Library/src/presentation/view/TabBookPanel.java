package presentation.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import presentation.model.ModelController;
import presentation.model.TabBookPanelModel;
import util.TextUtils;

public class TabBookPanel extends JPanel implements Observer {

	private static final String TITLE_FORMAT = "<html><p style='font-size:14pt; padding-left: 1.25cm; text-indent: -1cm;'>";
	private static final String NO_BOOK_ACTIVE_TEXT = "Kein Buch ausgewählt, bitte unter Recherche ein Buch suchen und auswählen, um seine Details hier anzuzeigen.";
	private static final long serialVersionUID = 1L;
	private TabBookPanelModel model;
	private JLabel detailPanel;
	private JPanel contentPanel;
	private ModelController controller;
	private ActionBookPanel bookpanel;

	public TabBookPanel(ModelController controller) {
		setLayout(new BorderLayout());
		this.controller = controller;

		model = controller.booktab_model;
		model.addObserver(this);
		
		this.controller.booktab_model = model;
		
		initContentPanel();
		bookpanel = new ActionBookPanel(controller);
		add(bookpanel, BorderLayout.EAST);
	}

	private void initContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setBorder(new TitledBorder("Katalogdaten"));
		contentPanel.setLayout(new BorderLayout());

		detailPanel = new JLabel(NO_BOOK_ACTIVE_TEXT);
		contentPanel.add(detailPanel, BorderLayout.WEST);

		add(contentPanel, BorderLayout.CENTER);
	}

	public TabBookPanelModel getModel() {
		return model;
	}

	public void update(Observable o, Object arg) {
		detailPanel.setText(getBookDetailPanelText());
	}

	private String getBookDetailPanelText() {
		if (model.getActiveBook() == null)
			return NO_BOOK_ACTIVE_TEXT;
		return TITLE_FORMAT
				+ "<b>"
				+ formatTitle(TextUtils
						.cutText(model.getActiveBook().getTitle().getName(),
								getWidth(), TITLE_FORMAT + "<b>"))
				+ "</b><br />Autor: "
				+ model.getActiveBook().getTitle().getAuthor()
				+ "<br />Verlag: "
				+ model.getActiveBook().getTitle().getPublisher() + "</p>";
	}

	private String formatTitle(String title) {
		return TITLE_FORMAT + "<b>" + title + "</b>";
	}

	/**
	 * Dynamically adapt title length to size of list by telling the cell
	 * renderer its size.
	 */
	public void paint(Graphics g) {
		detailPanel.setText(getBookDetailPanelText());
		super.paint(g);
	}
}
