package presentation.model;

import java.util.Observable;

import domain.Customer;

public class TabUserModel extends Observable {

	private final ModelController controller;
	private boolean isLoanSelected;
	private boolean isEditing;
	private Customer backupCustomer;
	private boolean isError;

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
				+ getActiveCustomer().getSurname() + ", "
				+ getActiveCustomer().getName() + "\"";
	}

	public void fireDataChanged() {
		setChanged();
		notifyObservers();
	}

	public boolean isLoanSelected() {
		return isLoanSelected;
	}

	public void setLoanSelected(boolean b) {
		isLoanSelected = b;
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
			controller.status_model.setTempStatus("Fehler: Konnte Änderungen nicht rückgängig machen!");
			return;
		}
		controller.activeuser_model.getCustomer().copyContent(backupCustomer);
		controller.status_model.setTempStatus("Erfolg: Änderungen am Benutzer rückgängig gemacht.");
	}

	public void setError(boolean b) {
		isError = b;
		fireDataChanged();
	}
	
	public boolean isError() {
		return isError;
	}
}
