package presentation.model;

import java.util.Observable;

import domain.Customer;
import domain.Loan;

public class TabUserModel extends Observable {

	private final ModelController controller;
	private boolean isEditing;
	private Customer backupCustomer;
	private Loan activeLoan;
	private boolean isErrorAtPlace;
	private boolean isErrorAtTitle;
	private boolean isErrorAtAddress;
	private Customer lastCustomer;

	public TabUserModel(ModelController controller) {
		this.controller = controller;
	}

	private Customer getActiveCustomer() {
		return controller.activeuser_model.getCustomer();
	}

	public String getStatus() {
		if (getActiveCustomer() == null)
			return "Benutzerdetails (kein Benutzer ausgewählt)";
		return "Anzeigen der Benutzerdetails von \""
				+ getActiveCustomer().getFullName() + "\"";
	}

	public void fireDataChanged() {
		setChanged();
		notifyObservers();
	}

	public void setEditing(boolean b) {
		isEditing = b;
		fireDataChanged();
	}

	public boolean isEditing() {
		return isEditing;
	}

	public void backupCustomerContent() {
		backupCustomer = controller.activeuser_model.getCustomer().clone();
	}

	public void restoreCustomerContent() {
		if (backupCustomer == null) {
			controller.status_model
					.setTempStatus("Fehler: Konnte Änderungen nicht rückgängig machen!");
			return;
		}
		controller.activeuser_model.getCustomer().copyContent(backupCustomer);
		controller.status_model
				.setTempStatus("Änderungen am Benutzer rückgängig gemacht.");
	}

	public boolean isError() {
		return isErrorAtPlace || isErrorAtTitle || isErrorAtAddress;
	}

	public void setActiveLoan(Loan newActive) {
		this.activeLoan = newActive;
		fireDataChanged();
	}

	public Loan getActiveLoan() {
		return this.activeLoan;
	}

	public boolean isLoanSelected() {
		return activeLoan != null;
	}

	public void setErrorAtPlace(boolean b) {
		isErrorAtPlace = b;
		fireDataChanged();
	}

	public boolean isErrorAtPlace() {
		return isErrorAtPlace;
	}

	public void setErrorAtTitle(boolean b) {
		isErrorAtTitle = b;
		fireDataChanged();
	}

	public boolean isErrorAtTitle() {
		return isErrorAtTitle;
	}

	public void setErrorAtAddress(boolean b) {
		isErrorAtAddress = b;
		fireDataChanged();
	}

	public boolean isErrorAtAddress() {
		return isErrorAtAddress;
	}

	public void setLastCustomer(Customer lastCustomer) {
		this.lastCustomer = lastCustomer;
	}

	public boolean isSameCustomer() {
		if (lastCustomer == null)
			return false;
		return lastCustomer.equals(controller.activeuser_model.getCustomer());
	}
}
