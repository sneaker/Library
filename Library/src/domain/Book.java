package domain;

import javax.management.Attribute;
import javax.management.AttributeList;

public class Book implements Searchable, Cloneable {

	public enum Condition {
		NEW, GOOD, DAMAGED, WASTE;
		private static final String TXT_WASTE = "Ausgemustert";
		private static final String TXT_DAMAGED = "Beschädigt";
		private static final String TXT_GOOD = "Intakt";
		private static final String TXT_NEW = "Neuwertig";

		public static String getConditionString(Condition c) {
			if (c == Condition.NEW)
				return TXT_NEW;
			if (c == Condition.GOOD)
				return TXT_GOOD;
			if (c == Condition.DAMAGED)
				return TXT_DAMAGED;
			if (c == Condition.WASTE)
				return TXT_WASTE;
			return "";
		}

		public static String[] getConditionStrings() {
			return new String[] { TXT_NEW, TXT_GOOD, TXT_DAMAGED, TXT_WASTE };
		}

		public static Condition getCondition(String item) {
			if (TXT_NEW.equals(item))
				return NEW;
			if (TXT_GOOD.equals(item))
				return GOOD;
			if (TXT_DAMAGED.equals(item))
				return DAMAGED;
			return WASTE;
		}
	}

	public static long nextInvertoryNumber = 1;

	private long inventoryNumber;
	private final Title title;
	private Condition condition;
	private String conditionComment = "";

	public Book(Title title) {
		this.title = title;
		inventoryNumber = nextInvertoryNumber++;
		condition = Condition.NEW;
	}

	public Title getTitle() {
		return title;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public long getInventoryNumber() {
		return inventoryNumber;
	}

	public String searchTitle() {
		return getTitle().getName();
	}

	public AttributeList searchDetail() {
		AttributeList list = new AttributeList();
		list.add(new Attribute("Autor",
				(title.getAuthor() == null ? "kein Autor eingetragen" : title
						.getAuthor())));
		list.add(new Attribute("Verlag",
				(title.getPublisher() == null ? "kein Verlag eingetragen"
						: title.getPublisher())));
		return list;
	}

	public String getConditionComment() {
		return conditionComment;
	}

	public void setConditionComment(String newConditionComment) {
		this.conditionComment = newConditionComment;
	}

	/**
	 * Creates a "deep copy" of this book which is not altered when the original
	 * instance is changed. The title of the book is also copied.
	 * 
	 * @return a copy of this instance.
	 */
	@Override
	public Book clone() {
		Book result = new Book((Title) getTitle().clone());
		result.setCondition(condition);
		result.setInventoryNumber(inventoryNumber);
		return result;
	}

	/**
	 * Use this with caution, should always be unique unless you're making a
	 * backup.
	 */
	private void setInventoryNumber(long newInventoryNumber) {
		this.inventoryNumber = newInventoryNumber;
	}

	/**
	 * Restore book contents from a backup copy. Create copies with the
	 * clone()-Function.
	 * 
	 * @param backup
	 *            The book whose contents have to be copied for this instance.
	 */
	public void copyContentFrom(Book backup) {
		getTitle().setName(backup.getTitle().getName());
		getTitle().setAuthor(backup.getTitle().getAuthor());
		getTitle().setPublisher(backup.getTitle().getPublisher());
		setCondition(backup.getCondition());
	}

	public String getConditionString() {
		return Condition.getConditionString(getCondition());
	}
}
