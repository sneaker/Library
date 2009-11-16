package presentation.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import util.ResManager;

public class DialogFactory {
	private static final int DIALOG_SIZE_MULTIPLICATOR = 28;
	private static final int ASPECT_RATIO_Y = 9;
	private static final int ASPECT_RATIO_X = 16;
	private static final Color SHINY_BLUE = new Color(0xADD8E6);

	public static JPanel createQuestionDialog(String message) {
		JPanel bg = new JPanel();
		bg.setLayout(new GridBagLayout());
		bg.setBorder(new LineBorder(Color.BLACK));
		bg.setBackground(SHINY_BLUE);
		bg.setPreferredSize(new Dimension(
				(int) (ASPECT_RATIO_X * DIALOG_SIZE_MULTIPLICATOR),
				(int) (ASPECT_RATIO_Y * DIALOG_SIZE_MULTIPLICATOR)));

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

	/**
	 * Create a dialog showing different options.
	 * 
	 * @param message
	 *            The question to be posed
	 * @param options
	 *            The buttons wich should be shown to offer different options
	 * @param buttonActions
	 *            The actions which should be performed when clicking on a
	 *            button; must have the same size as options
	 * @param buttonKeys
	 *            keys can be null; must have the same size as options
	 * @return the dialog as a JPanel which can be displayed on the
	 *         rootGlassPane.
	 */
	public static JPanel createChoiceDialog(String message, String[] options,
			Action[] buttonActions, KeyStroke[] buttonKeys) {
		JPanel bg = new JPanel();
		bg.setLayout(new GridBagLayout());
		bg.setBorder(new LineBorder(Color.BLACK));
		bg.setBackground(SHINY_BLUE);
		bg.setPreferredSize(new Dimension(
				(int) (ASPECT_RATIO_X * DIALOG_SIZE_MULTIPLICATOR),
				(int) (ASPECT_RATIO_Y * DIALOG_SIZE_MULTIPLICATOR)));

		JLabel l = new JLabel(message);
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
		c.gridwidth = options.length + 1;
		c.insets = new Insets(10, 3, 0, 3);
		bg.add(l, c);

		int i = 0;
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.gridy = 1;
		c.weighty = 0.0;
		c.insets = new Insets(10, 0, 10, 10);
		JButton[] buttons = new JButton[options.length];
		for (String option : options) {
			c.gridx = i + 1;
			c.weightx = (i == 0 ? 1.0 : 0.0);
			buttons[i] = new JButton();
			buttons[i].setText(option.replaceAll("&", ""));
			buttons[i].setMnemonic(option.charAt(Math.max(0, option
					.indexOf('&') + 1)));
			if (buttonActions[i] != null)
				buttons[i].addActionListener(buttonActions[i]);

			if (buttonKeys[i] != null) {
				String name = "action" + Math.random();
				InputMap inputMap = buttons[i]
						.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
				inputMap.put(buttonKeys[i], name);
				ActionMap actionMap = buttons[i].getActionMap();
				buttons[i].setActionMap(actionMap);
				actionMap.put(name, buttonActions[i]);
			}

			bg.add(buttons[i], c);
			i++;
		}

		return bg;
	}

}
