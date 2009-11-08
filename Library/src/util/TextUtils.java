package util;

import javax.swing.JLabel;

/**
 * Tools for formatting text for display.
 */
public class TextUtils {

	/**
	 * Cuts a (html-formatted) text for a JLabel so it fits into a specified
	 * place.
	 * 
	 * @param longText
	 *            the text to make shorter
	 * @param preferredWidth
	 *            the size the text should fit in (set to zero if negative)
	 * @param format
	 *            the (html) format which is prepended to the long text to
	 *            determine its size
	 * @return a text which should fit into a panel with the specified size
	 */
	public static String cutText(final String longText, int preferredWidth,
			String format) {
		String text = longText.replaceAll("\\<.*?>", "");
		double size = Math.max(0, preferredWidth)
				/ new JLabel(format + text).getPreferredSize().getWidth();
		String dots = "...";
		if (size > 1) {
			size = 1;
			dots = "";
		}
		return longText.substring(0, (int) Math.min(longText.length(), Math
				.max(0, Math.floor(longText.length() * size))))
				+ dots;
	}
}
