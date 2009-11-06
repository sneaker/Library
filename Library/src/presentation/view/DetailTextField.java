/**
 * 
 */
package presentation.view;

import java.awt.Font;

import javax.swing.JTextField;

/**
 * Is a mixture between a JLabel and a JTextField and helpful for displaying
 * information that should be editable from time to time.
 */
class DetailTextField extends JTextField {
	private static final Font DETAIL_TEXT_FONT = new Font("SansSerif", Font.PLAIN, 16);
	private static final long serialVersionUID = 1674245771658079673L;

	public DetailTextField() {
		super();
		setEditable(false);
		setBorder(null);
		setFont(DETAIL_TEXT_FONT);
	}

	/**
	 * Make this TextField look as if it were a label but you can still select
	 * and copy text.
	 */
	public void setEditable(boolean b) {
		super.setEditable(b);
		setVisible(b);
		setBorder((b ? new JTextField().getBorder() : null));
	}
}