package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LookBackKeyBuffer {
	private static final int BUFFER_SIZE = 10;
	public StringBuffer buffer;
	public int current;

	public LookBackKeyBuffer() {
		buffer = new StringBuffer("0000000000");
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
		String bookIdRegex = "bk[0-9]{2}$";
		Pattern pattern = Pattern.compile(bookIdRegex);
		Matcher matcher = pattern.matcher(getLastString());
		if (matcher.find())
			return true;
		return false;
	}

	public boolean isUserID() {
		String userIdRegex = "us[0-9]{2}$";
		Pattern pattern = Pattern.compile(userIdRegex);
		Matcher matcher = pattern.matcher(getLastString());
		if (matcher.find())
			return true;
		return false;
	}

	public boolean idTagStarted() {
		String idRegex = "(bk|us)$";
		Pattern pattern = Pattern.compile(idRegex);
		Matcher matcher = pattern.matcher(getLastString());
		if (matcher.find())
			return true;
		return false;
	}
	
	public boolean idTagPending() {
		String idRegex = "(bk|us)[0-9]{0,3}$";
		Pattern pattern = Pattern.compile(idRegex);
		Matcher matcher = pattern.matcher(getLastString());
		if (matcher.find())
			return true;
		return false;
	}
	
	public String getRecentId() {
		String idRegex = "(bk|us)[0-9]{0,3}$";
		Pattern pattern = Pattern.compile(idRegex);
		Matcher matcher = pattern.matcher(getLastString());
		if (matcher.find())
			return getLastString().substring(matcher.start());
		return "";
	}
}