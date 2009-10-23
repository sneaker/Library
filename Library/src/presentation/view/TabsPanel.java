package presentation.view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TabsPanel extends JTabbedPane {

	public static int SEARCH = 0;
	public static int BOOK = 1;
	public static int USER = 2;
	private static final long serialVersionUID = 1L;
	private JPanel[] tabPanel;
	private String[][] tabInformation = {
			{ "Recherche", "img/search.png",
					"Suchen nach Benutzern oder Büchern" },
			{ "Buch", "img/book.png", "Details eines Buches anzeigen" },
			{ "Benutzer", "img/user.png",
					"Personalien und Ausleihen eines Benutzers anzeigen" } };

	public TabsPanel() {
		initGUI();
		initChangeListener();
	}

	private void initChangeListener() {
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// TODO: Change "active Tab" in WindowModel 
			}
		});
	}

	private void initGUI() {
		tabPanel = new JPanel[tabInformation.length];
		
		for (int i = 0; i < tabInformation.length; i++) {
			this.addTab(null, null, tabPanel[i], tabInformation[i][2]);
			ImageIcon image = new ImageIcon(tabInformation[i][1]);
			JLabel paneTitle = new JLabel(tabInformation[i][0], image,
					JLabel.TRAILING);
			paneTitle.setVerticalTextPosition(JLabel.BOTTOM);
			paneTitle.setHorizontalTextPosition(JLabel.CENTER);
			this.setTabComponentAt(i, paneTitle);
		}
		
		this.setTabPlacement(JTabbedPane.LEFT);
	}

	public void switchTo(int newTabIndex) {
		if (newTabIndex >= getTabCount())
			return;
		setSelectedIndex(newTabIndex);
	}

}
