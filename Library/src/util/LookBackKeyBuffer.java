package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Knows about the last few characters the user entered on his keyboard and
 * reports whether or not the typed text corresponds to an id of a book or user.
 */
public class LookBackKeyBuffer {
	/**
	 * ID needs a fixed length because it would be difficult to make all book
	 * id's prefix free to use them for the find-as-you-type feature. Change
	 * constructor accordingly if value is set to more than 12 characters.
	 */
	private static final int ID_LENGTH = 3;
	private static final String USER_ID_PREFIX = "us";
	private static final String BOOK_ID_PREFIX = "bk";
	private static final int BUFFER_SIZE = Math.max(USER_ID_PREFIX.length(),
			BOOK_ID_PREFIX.length())
			+ ID_LENGTH;
	public StringBuffer buffer;
	public int current;

	public LookBackKeyBuffer() {
		// Needed because int argument doesn't seem to work.
		buffer = new StringBuffer("000000000000");
		current = 0;
	}

	public void addChar(char keyChar) {
		buffer.setCharAt(current, keyChar);
		current = (current + 1) % BUFFER_SIZE;
	}

	public String getLastString() {
		return buffer.substring(current, BUFFER_SIZE)
				+ buffer.substring(0, current);
	}

	public boolean isBookID() {
		return isMatching(BOOK_ID_PREFIX + "[0-9]{" + ID_LENGTH + "}$");
	}

	public boolean isUserID() {
		return isMatching(USER_ID_PREFIX + "[0-9]{" + ID_LENGTH + "}$");
	}

	public boolean idTagStarted() {
		return isMatching(getBookOrUserRegex() + "$");
	}

	public boolean idTagPending() {
		return isMatching(getBookOrUserRegex() + "[0-9]{0," + ID_LENGTH + "}$");
	}
	
	public boolean wasLastIdCharacter() {
		return isMatching(getBookOrUserRegex() + "[0-9]{" + ID_LENGTH + "}$");
	}

	public String getRecentId() {
		Pattern pattern = Pattern.compile(getBookOrUserRegex() + "[0-9]{0,"
				+ ID_LENGTH + "}$");
		Matcher matcher = pattern.matcher(getLastString());
		if (matcher.find())
			return getLastString().substring(matcher.start());
		return "";
	}

	private String getBookOrUserRegex() {
		return "(" + BOOK_ID_PREFIX + "|" + USER_ID_PREFIX + ")";
	}

	private boolean isMatching(String regex) {
		Matcher matcher = Pattern.compile(regex).matcher(getLastString());
		if (matcher.find())
			return true;
		return false;
	}
}