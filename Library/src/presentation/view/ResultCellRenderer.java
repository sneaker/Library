package presentation.view;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import domain.Book;
import domain.Customer;
import domain.Library;

public class ResultCellRenderer implements ListCellRenderer {

	private static final int TEXT_WIDTH_DIFFERENCE = 100;

	private int preferredWidth = 250;
	private Library library;

	public ResultCellRenderer(Library library) {
		this.library = library;
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int cellIndex, boolean isSelected, boolean cellHasFocus) {

		int index = cellIndex;
		if (index == -1) {
			if (list.isSelectionEmpty())
				return new JPanel();
			index = list.getSelectedIndex();
		}

		return getRenderedCell(value, isSelected);
	}

	private JPanel getRenderedCell(Object value, boolean isSelected) {
		JPanel cell = new JPanel();
		if (value instanceof Book)
			cell = new ResultCellBookPanel((Book) value, isSelected,
					preferredWidth, library);
		else if (value instanceof Customer)
			cell = new ResultCellUserPanel((Customer) value, isSelected,
					preferredWidth, library);
		return cell;
	}

	public void setPreferredWidth(int width) {
		this.preferredWidth = Math.max(100, width - TEXT_WIDTH_DIFFERENCE);
	}

}