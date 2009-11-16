/**
 * 
 */
package presentation.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import util.ResManager;

public class ActionButton extends JButton {

	private static final long serialVersionUID = 1L;
	private final Color SYSTEM_BACKGROUND = new Color(0xeeeeee);
	
	public ActionButton(String string, String imagePath, String rolloverImagepath) {
		super(string);
		setIcon(ResManager.getImage(imagePath));
		setRolloverIcon(ResManager.getImage(rolloverImagepath));
		setBorder(new EmptyBorder(5, 10, 5, 5));
		setIconTextGap(6);
		setHorizontalAlignment(JButton.LEFT);
		setBackground(SYSTEM_BACKGROUND);
		setFocusPainted(false);
	}
	
	public void setDummyLabel() {
		setEnabled(false);
		setFont(new Font(getFont().getName(), Font.BOLD, 14));
		setForeground(Color.BLACK);
		setHorizontalTextPosition(SwingConstants.CENTER);
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