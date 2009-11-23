package presentation.model;

import javax.swing.DefaultListModel;

import domain.Book;
import domain.Customer;
import domain.Library;

public class ModelController {

	public ActiveUserPanelModel activeuser_model;
	public MainWindowModel main_model;
	public LibraryTabbedPaneModel tabbed_model;
	public TabSearchModel searchtab_model;
	public TabBookModel booktab_model;
	public TabUserModel usertab_model;
	public ActionPanelModel action_model;
	public SearchResultListModel resultlist_model;
	public Library library;
	public StatusModel status_model;
	public DefaultListModel loanModel;

	public ModelController(Library library) {
		this.library = library;

		loanModel = new DefaultListModel();
		activeuser_model = new ActiveUserPanelModel(this);
		main_model = new MainWindowModel(this);
		tabbed_model = new LibraryTabbedPaneModel(this);
		searchtab_model = new TabSearchModel(this);
		booktab_model = new TabBookModel(this);
		usertab_model = new TabUserModel(this);
		action_model = new ActionPanelModel(this);
		resultlist_model = new SearchResultListModel(this);
		status_model = new StatusModel(this);
	}

	public Customer getActiveCustomer() {
		return activeuser_model.getActiveCustomer();
	}
	
	public void setActiveCustomer(Customer selected) {
		activeuser_model.setActiveCustomer(selected);
	}
	
	public void resetActiveCustomer() {
		activeuser_model.resetActiveCustomer();
	}
	
	public boolean isCustomerActive() {
		return activeuser_model.isCustomerActive();
	}
	
	protected String getSearchStatus() {
		return searchtab_model.getStatus();
	}

	public String getCustomerStatus() {
		return usertab_model.getStatus();
	}

	public String getBookStatus() {
		return booktab_model.getStatus();
	}
	
	public void showUser() {
		resultlist_model.showUser();
	}
	
	public void showAvailableBooks() {
		resultlist_model.showavailableBooks();
	}

	public void showDefektBooks() {
		resultlist_model.showDefectBooks();
	}

	public void showLentBooks() {
		resultlist_model.showLentBooks();
	}

	public void selectSingleElement() {
		resultlist_model.selectSingleElement();
	}
	
	public void forwardKeyEvent(char c, String wholestring) {
		resultlist_model.forwardKeyEvent(c, wholestring);
	}

	public void forwarDelEvent(char c, String wholestring) {
		resultlist_model.forwardDelEvent(c, wholestring);
	}
	
	public void resetSearchText() {
		resultlist_model.resetSearch();
		searchtab_model.resetSearchText();
	}
	
	public void setTitle(String title) {
		main_model.setTitle(title);
	}

	public void setActiveBook(Book activeBook) {
		booktab_model.setActiveBook(activeBook);
	}

	public void setBookTabActive() {
		tabbed_model.setBookTabActive();
	}
}
