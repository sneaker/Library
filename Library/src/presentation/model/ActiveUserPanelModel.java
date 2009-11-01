package presentation.model;

import java.util.Observable;

public class ActiveUserPanelModel extends Observable {

		public void setNewActiveUser(String user) {
			setChanged();
			notifyObservers(user);
		}
}
