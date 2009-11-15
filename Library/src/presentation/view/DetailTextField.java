/**
 * 
 */
package presentation.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Is a mixture between a JLabel and a JTextField and helpful for displaying
 * information that should be editable from time to time.
 */
class DetailTextField extends JTextField {
	private static final EmptyBorder EMPTY_BORDER = new EmptyBorder(3, 3, 3, 3);
	private static final Font DETAIL_TEXT_FONT = new Font("SansSerif", Font.PLAIN, 16);
	private static final Color SHINY_BLUE = new Color(0xADD8E6);
	private static final long serialVersionUID = 1674245771658079673L;
	private boolean isDisplayOnly;
	private boolean isEditable;
	private boolean isError;

	public DetailTextField() {
		super();
		setEditable(false);
		setBorder(EMPTY_BORDER);
		setFont(DETAIL_TEXT_FONT);
		addSelectionHighlightListener();
	}

	private void addSelectionHighlightListener() {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				repaintBackground();
			}
			@Override
			public void focusLost(FocusEvent e) {
				super.focusLost(e);
				repaintBackground();
			}
		});
	}

	/**
	 * Make this TextField look as if it were a label but you can still select
	 * and copy text.
	 */
	public void setEditable(boolean b) {
		isEditable = b;
		setVisible(b);
		super.setEditable(b);
		setBorder((b ? new JTextField().getBorder() : EMPTY_BORDER));
		repaintBackground();
	}
	
	public void setRed(boolean really) {
		setForeground((really ? new Color(0xcc, 0, 0) : Color.BLACK));
	}

	public void setDisplayOnly(boolean isDisplayOnly) {
		this.isDisplayOnly = isDisplayOnly;
	}

	public boolean isDisplayOnly() {
		return isDisplayOnly;
	}

	public void setError(boolean b) {
		isError = b;
		repaintBackground();
	}
	
	public boolean isError() {
		return isError;
	}

	private void repaintBackground() {
		if (!isEditable || isDisplayOnly)
			setBackground(new JPanel().getBackground());
		else if (isEditable && isFocusOwner() && !isError)
			setBackground(SHINY_BLUE);
		else if (isEditable && !isFocusOwner() && !isError)
			setBackground(Color.WHITE);
		else
			setBackground(Color.RED);
	}
}