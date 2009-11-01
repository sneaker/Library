package presentation.model;

import domain.Library;

public class ModelController {
	
	public ActiveUserPanelModel activeuser_model;
	
	public MainWindowModel main_model;
	
	public LibraryTabbedPaneModel tabbed_model;
	
	public TabSearchModel searchtab_model;
	public TabBookPanelModel booktab_model;
	public TabUserModel usertab_model;
	
	public ActionPanelModel action_model;
	public SearchResultListModel resultlist_model;
	
	public Library library;
	
	public ModelController(Library library) {
		this.library = library;
		
		main_model = new MainWindowModel(this);
		tabbed_model = new LibraryTabbedPaneModel(this);
		searchtab_model = new TabSearchModel(this);
		booktab_model = new TabBookPanelModel();
		usertab_model = new TabUserModel(this);
		action_model = new ActionPanelModel(this);
		resultlist_model = new SearchResultListModel(this);
	}
}
