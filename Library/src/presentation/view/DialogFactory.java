package presentation.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import util.ResManager;

public class DialogFactory {
	private static final int DIALOG_SIZE_MULTIPLICATOR = 25;
	private static final int ASPECT_RATIO_Y = 9;
	private static final int ASPECT_RATIO_X = 16;
	private static final Color SHINY_BLUE = new Color(0xADD8E6);

	public static JPanel createQuestionDialog(String message) {
		JPanel bg = new JPanel();
	    bg.setLayout(new GridBagLayout());
	    bg.setBorder(new LineBorder(Color.BLACK));
	    bg.setBackground(SHINY_BLUE);
	    bg.setPreferredSize(new Dimension((int)(ASPECT_RATIO_X * DIALOG_SIZE_MULTIPLICATOR), (int)(ASPECT_RATIO_Y * DIALOG_SIZE_MULTIPLICATOR)));
	    
	    JLabel l = new JLabel("<html><font size=5>" + message);
	    l.setIcon(ResManager.getImage("book64x64.png"));
	    l.setVerticalTextPosition(SwingConstants.TOP);
	    l.setVerticalAlignment(SwingConstants.TOP);
	    GridBagConstraints c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = 0;
	    c.anchor = GridBagConstraints.FIRST_LINE_START;
	    c.fill = GridBagConstraints.BOTH;
	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.insets = new Insets(10, 3, 0, 3);
	    bg.add(l, c);

	    c = new GridBagConstraints();
	    c.gridx = 0;
	    c.gridy = 1;
	    c.anchor = GridBagConstraints.LAST_LINE_END;
	    c.weightx = 1.0;
	    c.weighty = 0.0;
	    c.insets = new Insets(10, 10, 10, 10);
	    JButton glassButton = new JButton("OK");
	    glassButton.setMnemonic('O');
	    bg.add(glassButton, c);
		return bg;
	}
}
