package domain;

public class Book {
	
	public enum Condition {NEW, GOOD, DAMAGED, WASTE }
	
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

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public long getInventoryNumber() {
		return inventoryNumber;
	}
}
