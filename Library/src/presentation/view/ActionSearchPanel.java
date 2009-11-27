package presentation.view;

import java.util.Observable;

import presentation.control.BookCreateActionListener;
import presentation.control.NewSearchAction;
import presentation.control.ShowAvailableBooksAction;
import presentation.control.ShowDefectBooks;
import presentation.control.ShowLentBooksActionListener;
import presentation.control.ShowOnlyUserActionListener;
import presentation.control.UserCreateActionListener;
import presentation.model.ControllerFacade;

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
	public ActionSearchPanel(ControllerFacade controller) {
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
		initShowUserOnly();
	}

	private void initSearchButton() {
		buttons.put("newsearch", new ActionButton("Neue Recherche",
				"search32x32h.png", "search32x32.png"));
		buttons.get("newsearch").addActionListener(
				new NewSearchAction(controller));
		buttons.get("newsearch").setMnemonic('n');
	}

	private void initNewUserButton() {
		buttons.put("newuser", new ActionButton("Benutzer erstellen",
				"newcustomer32x32h.png", "newcustomer32x32.png"));
		buttons.get("newuser").addActionListener(
				new UserCreateActionListener(controller));
		buttons.get("newuser").setMnemonic('u');
	}

	private void initNewBookButton() {
		buttons.put("newbook", new ActionButton("Buch erstellen",
				"newbook32x32h.png", "newbook32x32.png"));
		buttons.get("newbook").addActionListener(
				new BookCreateActionListener(controller));
		buttons.get("newbook").setMnemonic('h');
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
		buttons.get("available").setMnemonic('v');
	}

	private void initShowDefectBooks() {
		buttons.put("showdefekt", new ActionButton("Defekte Bücher",
				"wastebooks32x32h.png", "wastebooks32x32.png"));
		buttons.get("showdefekt").addActionListener(
				new ShowDefectBooks(controller));
		buttons.get("showdefekt").setMnemonic('f');
	}

	private void initShowLentBooks() {
		buttons.put("showlent", new ActionButton("Ausgeliehene Bücher",
				"allloans32x32h.png", "allloans32x32.png"));
		buttons.get("showlent").addActionListener(
				new ShowLentBooksActionListener(controller));
		buttons.get("showlent").setMnemonic('g');
	}
	
	private void initShowUserOnly() {
		buttons.put("showusers", new ActionButton("Nur Benutzer zeigen", "user32x32.png", "user32x32.png"));
		buttons.get("showusers").addActionListener(new ShowOnlyUserActionListener(controller));
		buttons.get("showusers").setMnemonic('b');
	}

	public void update(Observable o, Object arg) {
	}
}
