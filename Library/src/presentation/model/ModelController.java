package presentation.model;

import javax.swing.DefaultListModel;

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
}
