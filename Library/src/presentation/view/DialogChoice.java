/**
 * 
 */
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

public class DialogChoice extends JPanel {
	private static final long serialVersionUID = 1352776255213495535L;
	private JLabel msgLabel;

	public DialogChoice(String message, String[] options,
			Action[] buttonActions, KeyStroke[] buttonKeys) {

		setLayout(new GridBagLayout());
		setBorder(new LineBorder(Color.BLACK));
		setBackground(DialogFactory.SHINY_BLUE);
		setPreferredSize(new Dimension(
				(int) (DialogFactory.ASPECT_RATIO_X * DialogFactory.DIALOG_SIZE_MULTIPLICATOR),
				(int) (DialogFactory.ASPECT_RATIO_Y * DialogFactory.DIALOG_SIZE_MULTIPLICATOR)));

		msgLabel = new JLabel(message);
		msgLabel.setIcon(ResManager.getImage("book64x64.png"));
		msgLabel.setVerticalTextPosition(SwingConstants.TOP);
		msgLabel.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = options.length + 1;
		c.insets = new Insets(10, 3, 0, 3);
		add(msgLabel, c);

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

			add(buttons[i], c);
			i++;
		}
	}
}