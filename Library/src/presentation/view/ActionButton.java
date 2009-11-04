/**
 * 
 */
package presentation.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ActionButton extends JButton {
	private final Color SYSTEM_BACKGROUND = new Color(0xeeeeee);
	private static final long serialVersionUID = 4216623296147904685L;

	public ActionButton(String string, String imagePath, String rolloverImagepath) {
		super(string);
		setIcon(new ImageIcon(imagePath));
		setRolloverIcon(new ImageIcon(rolloverImagepath));
		setBorder(null);
		setIconTextGap(6);
		setHorizontalAlignment(JButton.LEFT);
		setBackground(SYSTEM_BACKGROUND);
		setFocusPainted(false);
	}
	
	@Override
	public void paint(Graphics g) {
		if (hasFocus())
			setBackground(Color.LIGHT_GRAY);
		else
			setBackground(SYSTEM_BACKGROUND);
		super.paint(g);
	}
}