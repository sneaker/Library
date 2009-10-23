package presentation.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class SearchPane extends JPanel {

	private static final String DEFAULT_SEARCH_STRING = "Benutzernamen, Buchtitel oder Autor eingeben";
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String searchText = DEFAULT_SEARCH_STRING;
	private JPanel actionPane;

	public SearchPane() {

		contentPane = new JPanel();
		contentPane.add(new JTextField(searchText), BorderLayout.CENTER);
		contentPane.setBorder(new TitledBorder("Suchmaske"));
		contentPane.setPreferredSize(new Dimension(9999999, 100));
		add(contentPane);

		actionPane = new JPanel();
		actionPane.setBorder(new TitledBorder("Aktionen"));
		contentPane.setPreferredSize(new Dimension(200, 100));
		actionPane.add(new JButton("Irgendwas erstellen"));
		add(actionPane, BorderLayout.EAST);
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void resetSearchText() {
		searchText = DEFAULT_SEARCH_STRING;
	}

}
