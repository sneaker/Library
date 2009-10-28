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

import domain.Book;

public class ResultCellRenderer implements ListCellRenderer {

	private static final Color SHINY_BLUE = new Color(0xADD8E6);
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
		return "<html><p style='font-size:14pt; padding-left: 1.25cm; text-indent: -1cm;'><b>"
				+ getShortName(selectedBook.getTitle().getName())
				+ "</b><br />Autor: "
				+ selectedBook.getTitle().getAuthor()
				+ "<br />Verlag: "
				+ selectedBook.getTitle().getPublisher();
	}

	private String getShortName(String name) {
		JLabel l = new JLabel("<html><p style='font-size:14pt;'><b>" + name);
		double size = preferredWidth / l.getPreferredSize().getWidth();
		String points = "...";
		if (size > 1) {
			size = 1;
			points = "";
		}
		return name.substring(0, (int) Math.floor(name.length()*size)) + points;
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
		this.preferredWidth = Math.max(100, width - 50);
	}
}