package presentation.view;

import java.util.Observable;

import presentation.control.BookCreateActionListener;
import presentation.control.NewSearchText;
import presentation.control.ShowAvailableBooksAction;
import presentation.control.ShowDefectBooks;
import presentation.control.ShowLentBooksActionListener;
import presentation.control.UserCreateActionListener;
import presentation.model.ModelController;

/**
 * Stores the buttons which are shown on the main screen / on the search panel.
 * If any button should be enabled / disabled on this panel, edit the
 * update(a,b) method.
 */
public class ActionSearchPanel extends AbstractActionPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Get an instance of the action button panel for the search tab and start
	 * listening for events on those buttons.
	 * 
	 * @param controller
	 *            stores all models needed to execute the given tasks.
	 */
	public ActionSearchPanel(ModelController controller) {
		super(controller);
		controller.library.addObserver(this);
	}

	protected void initActionButtons() {
		initSearchButton();
		initNewUserButton();
		initNewBookButton();
		initSpacer();
		initShowAvailableBooks();
		initShowDefectBooks();
		initShowLentBooks();
	}

	private void initSearchButton() {
		buttons.put("newsearch", new ActionButton("Neue Recherche",
				"search32x32h.png", "search32x32.png"));
		buttons.get("newsearch").addActionListener(
				new NewSearchText(controller));
	}

	private void initNewUserButton() {
		buttons.put("newuser", new ActionButton("Benutzer erstellen",
				"newcustomer32x32h.png", "newcustomer32x32.png"));
		buttons.get("newuser").addActionListener(
				new UserCreateActionListener(controller));
	}

	private void initNewBookButton() {
		buttons.put("newbook", new ActionButton("Buch erstellen",
				"new32x32h.png", "new32x32.png"));
		buttons.get("newbook").addActionListener(
				new BookCreateActionListener(controller));
	}

	private void initSpacer() {
		buttons.put("none", new ActionButton("Spezialabfragen", "", ""));
		buttons.get("none").setDummyLabel();
	}

	private void initShowAvailableBooks() {
		buttons.put("available", new ActionButton("Verfügbare Bücher",
				"availablebooks32x32h.png", "availablebooks32x32.png"));
		buttons.get("available").addActionListener(
				new ShowAvailableBooksAction(controller));
	}

	private void initShowDefectBooks() {
		buttons.put("showdefekt", new ActionButton("Defekte Bücher",
				"wastebooks32x32h.png", "wastebooks32x32.png"));
		buttons.get("showdefekt").addActionListener(
				new ShowDefectBooks(controller));
	}

	private void initShowLentBooks() {
		buttons.put("showlent", new ActionButton("Ausgeliehene Bücher",
				"allloans32x32h.png", "allloans32x32.png"));
		buttons.get("showlent").addActionListener(
				new ShowLentBooksActionListener(controller));
	}

	public void update(Observable o, Object arg) {
	}
}
