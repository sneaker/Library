package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import util.TextUtils;

import domain.Book;

public class ResultCellRenderer implements ListCellRenderer {

	private static final int TEXT_WIDTH_DIFFERENCE = 100;
	private static final Color SHINY_BLUE = new Color(0xADD8E6);
	private static final String TITLE_FORMAT = "<html><p style='font-size:14pt; padding-left: 1.25cm; text-indent: -1cm;'>";
	private int preferredWidth;

	public Component getListCellRendererComponent(JList list, Object value,
			int cellIndex, boolean isSelected, boolean cellHasFocus) {

		int index = cellIndex;
		if (index == -1) {
			if (list.isSelectionEmpty())
				return new JPanel();
			index = list.getSelectedIndex();
		}

		// TODO: Unterscheiden zwischen Book / Usern [Martin]
		Book selectedBook = ((Book) list.getModel().getElementAt(index));

		JPanel c = new JPanel();
		c.setLayout(new BorderLayout());
		JLabel l = new JLabel(getFormattedTitle(selectedBook));
		l.setIcon(new ImageIcon("img" + File.separatorChar + "book.png"));
		l.setFont(new Font(null, Font.PLAIN, 14));
		c.add(l, BorderLayout.WEST);

		if (isSelected) {
			String actions = getFormattedActions(selectedBook);
			JLabel actionLabel = new JLabel("<html>"
					+ selectedBook.getCondition().toString() + actions);
			actionLabel.setFont(new Font(null, Font.PLAIN, 14));
			c.add(actionLabel, BorderLayout.EAST);

			c.setBackground(SHINY_BLUE);
			return c;
		}

		c.setBackground(Color.WHITE);

		return c;
	}

	private String getFormattedTitle(Book selectedBook) {
		return TITLE_FORMAT
				+ "<b>"
				+ TextUtils.cutText(selectedBook.getTitle().getName(),
						preferredWidth, TITLE_FORMAT + "<b>")
				+ "</b><br />Autor: " + selectedBook.getTitle().getAuthor()
				+ "<br />Verlag: " + selectedBook.getTitle().getPublisher();
	}

	private String getFormattedActions(Book selectedBook) {
		String actions = "<p style=\"color:blue;\">Ausleihen<br /> &nbsp;";
		// for (MyListAction a : selectedBook.getActions()) {
		// actions += "<b>&rarr;</b>" + a.getName() + "<br />";
		// }
		actions += "</p>";
		return actions;
	}

	public void setPreferredWidth(int width) {
		this.preferredWidth = Math.max(100, width - TEXT_WIDTH_DIFFERENCE);
	}
}