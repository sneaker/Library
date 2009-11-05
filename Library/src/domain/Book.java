package domain;

import javax.management.Attribute;
import javax.management.AttributeList;

public class Book implements Searchable {

	public enum Condition {
		NEW, GOOD, DAMAGED, WASTE
	}

	public static long nextInvertoryNumber = 1;

	private final long inventoryNumber;
	private final Title title;
	private Condition condition;

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

	public String getConditionString() {
		if (condition == Condition.NEW)
			return "Neuwertig";
		if (condition == Condition.GOOD)
			return "Intakt";
		if (condition == Condition.DAMAGED)
			return "Beschädigt";
		if (condition == Condition.WASTE)
			return "Ausgemustert";
		return "";
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
}
