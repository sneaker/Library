package presentation.model;

import java.util.Observable;

import domain.Book;
import domain.Customer;

public class ActionPanelModel extends Observable {

	private ModelController controller;

	public ActionPanelModel(ModelController controller) {
		this.controller = controller;
	}

	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}

	public void changetoSearch() {
		controller.setSearchTabActive();
		controller.resetSearchText();
		
		setChanged();
		notifyObservers();
	}

	public void lendBook() {
		Customer activeuser = controller.getActiveCustomer();
		Book activebook = controller.booktab_model.getActiveBook();
		if (controller.library.isCustomerLocked(activeuser)) {
			controller.status_model
					.setTempStatus("Keine Ausleihe möglich: Benutzer hat noch aktive Mahnungen.");
			return;
		}
		if ((activeuser != null) && (activebook != null)) {
			controller.library.createAndAddLoan(activeuser, activebook);
		} else if (activeuser == null) {
			controller.status_model
					.setTempStatus("Keine Ausleihe möglich: erst ausleihenden Benutzer auswählen!");
			controller.setSearchTabActive();
		} else {
			controller.setSearchTabActive();
		}
		controller.booktab_model.lendActiveBook();
		fireDataChanged();
		controller.status_model.setTempStatus("Buch wurde ausgeliehen: "
				+ controller.booktab_model.getActiveBook().getTitle().getName()
				+ getDecoratedCustomerName("für: "));
	}

	public void returnBook() {
		controller.library.returnBook(controller.booktab_model.getActiveBook());
		fireDataChanged();
		controller.status_model.setTempStatus("Zurück in der Bibliothek: "
				+ controller.booktab_model.getActiveBook().getTitle().getName()
				+ getDecoratedCustomerName("von: "));
	}

	private String getDecoratedCustomerName(String vor) {
		if (controller.getActiveCustomer() == null)
			return "";
		return " (" + vor + controller.getActiveCustomer().getFullName() + ")";
	}

	public void fireDataChanged() {
		setChanged();
		notifyObservers();
	}

	public void clearuser() {
		controller.resetActiveCustomer();
	}

	public void showUser() {
		controller.showUser();
	}

	public void editUserSettings() {
		controller.usertab_model.setEditing(true);
		controller.usertab_model.backupCustomerContent();
	}

	public void editUserSettingsOk() {
		controller.status_model
				.setTempStatus("Erfolg: Änderungen erfolgreich gespeichert.");
		controller.usertab_model.setEditing(false);
		controller.library.fireDataChanged();
	}

	public void editUserSettingsCancel() {
		controller.usertab_model.restoreCustomerContent();
		controller.usertab_model.setEditing(false);
	}
}
