package presentation.model;

import java.util.Observable;

public class BookDetailPanelModel extends Observable implements LibraryModel {

	public MainWindowModel model;
	public BookDetailPanelModel(MainWindowModel model2) {
		model = model2;
	}

	private static final long serialVersionUID = 1L;
	
	public MainWindowModel getMainWindowModel() {
		return model;
	}

}
