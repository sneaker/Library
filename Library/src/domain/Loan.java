package domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.management.Attribute;
import javax.management.AttributeList;

public class Loan implements Searchable {

	public static final int MAX_LEND_DAYS = 14;
	private Book book;
	private Customer customer;
	private GregorianCalendar pickupDate, returnDate;

	public Loan(Customer customer, Book book) {
		this.book = book;
		this.customer = customer;
		pickupDate = new GregorianCalendar();
	}

	public boolean isLent() {
		return returnDate == null;
	}

	public boolean returnBook() {
		try {
			returnBook(new GregorianCalendar());
		} catch (IllegalLoanOperationException e) {
			return false;
		}
		return true;
	}

	public void returnBook(GregorianCalendar returnDate)
			throws IllegalLoanOperationException {
		if (returnDate.before(pickupDate)) {
			throw new IllegalLoanOperationException(
					"Return Date is before pickupDate");
		}
		this.returnDate = returnDate;
	}

	public void setPickupDate(GregorianCalendar pickupDate)
			throws IllegalLoanOperationException {
		if (!isLent()) {
			throw new IllegalLoanOperationException("Loan is already retuned");
		}
		this.pickupDate = pickupDate;
	}

	public GregorianCalendar getPickupDate() {
		return pickupDate;
	}

	public GregorianCalendar getReturnDate() {
		return returnDate;
	}

	public Book getBook() {
		return book;
	}

	public Customer getCustomer() {
		return customer;
	}

	@Override
	public String toString() {
		return "Loan of: " + book.getTitle().getName() + "\tFrom: "
				+ customer.getName() + " " + customer.getSurname()
				+ "\tPick up: " + getFormattedDate(pickupDate) + "\tReturn: "
				+ getFormattedDate(returnDate) + "\tDays: "
				+ getDaysOfLoanDuration();
	}

	private String getFormattedDate(GregorianCalendar date) {
		if (date != null) {
			DateFormat f = SimpleDateFormat.getDateInstance();
			return f.format(date.getTime());
		}
		return "00.00.00";
	}

	public int getDaysOfLoanDuration() {
		if (returnDate != null)
			return (int) (returnDate.getTimeInMillis() - pickupDate
					.getTimeInMillis())
					/ 1000 / 60 / 60 / 24;
		return -1;
	}

	public int getDaysSincePickup() {
		if (isLent())
			return (new GregorianCalendar()).get(GregorianCalendar.DAY_OF_YEAR)
					- pickupDate.get(GregorianCalendar.DAY_OF_YEAR);
		return returnDate.get(GregorianCalendar.DAY_OF_YEAR)
				- pickupDate.get(GregorianCalendar.DAY_OF_YEAR);
	}

	public boolean isOverdue() {
		if (returnDate != null)
			return false;
		if ((new GregorianCalendar()).get(GregorianCalendar.DAY_OF_YEAR)
				- pickupDate.get(GregorianCalendar.DAY_OF_YEAR) > MAX_LEND_DAYS)
			return true;
		return false;
	}

	/**
	 * Delta days are the number of days away from the date when the book
	 * had/has to be returned.
	 * 
	 * @return the days this loan is over its time limit if isOverdue().
	 * @return the days left for bringing back this book if isOverdue() is
	 *         false.
	 */
	public int getOverdueDeltaDays() {
		return Math.abs(getDaysSincePickup() - MAX_LEND_DAYS);
	}

	private String redText(String text) {
		return "<font color=red>" + text + "</font>";
	}

	public AttributeList searchDetail() {
		AttributeList result = new AttributeList();
		String date = getPickupDateText();
		result.add(getLendSinceAttribute(date));
		result.add(getStatusAttribute());
		return result;
	}

	private String getPickupDateText() {
		return getPickupDate().get(GregorianCalendar.DAY_OF_MONTH) + "."
				+ getPickupDate().get(GregorianCalendar.MONTH) + " "
				+ getPickupDate().get(GregorianCalendar.YEAR);
	}

	private Attribute getStatusAttribute() {
		return new Attribute("Status", (isOverdue() ? redText("überfällig")
				+ " seit " : "Rückgabe in ")
				+ getOverdueDeltaDays()
				+ (getOverdueDeltaDays() == 1 ? " Tag" : " Tagen"));
	}

	private Attribute getLendSinceAttribute(String date) {
		return new Attribute("Ausleihdatum", date
				+ (isLent() ? ", vor " + getDaysSincePickup() : ", für "
						+ getDaysOfLoanDuration()) + " Tag(en)");
	}

	public String searchTitle() {
		return book.getTitle().getName();
	}
}
