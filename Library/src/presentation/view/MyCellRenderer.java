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

public class MyCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value,
			int cellIndex, boolean isSelected, boolean cellHasFocus) {

		int index = cellIndex;
		if (index == -1) {
			if (list.isSelectionEmpty())
				return new JPanel();
			index = list.getSelectedIndex();
		}

		Book selectedBook = ((Book) list.getModel().getElementAt(index));

		JPanel c = new JPanel();
		c.setLayout(new BorderLayout());
		JLabel l = new JLabel("<html>" + "<b>" + selectedBook.getTitle()
				+ "</b></html>");
		l.setIcon(new ImageIcon("img" + File.separatorChar + "book.png"));
		l.setFont(new Font(null, Font.PLAIN, 14));
		c.add(l, BorderLayout.WEST);

		if (isSelected) {
			String actions = getFormattedActions(selectedBook);
			JLabel actionLabel = new JLabel("<html>"
					+ selectedBook.getCondition().toString() + actions
					+ "</html>");
			actionLabel.setFont(new Font(null, Font.PLAIN, 14));
			c.add(actionLabel, BorderLayout.EAST);

			c.setBackground(new Color(0xADD8E6));
			return c;
		}

		c.setBackground(Color.WHITE);

		return c;
	}

	private String getFormattedActions(Book selectedBook) {
		String actions = "<p style=\"color:blue;\">Ausleihen<br /> &nbsp;";
		// for (MyListAction a : selectedBook.getActions()) {
		// actions += "<b>&rarr;</b>" + a.getName() + "<br />";
		// }
		actions += "</p>";
		return actions;
	}
}