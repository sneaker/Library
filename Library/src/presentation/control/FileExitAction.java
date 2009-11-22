/**
 * 
 */
package presentation.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileExitAction implements ActionListener {
	private static final long serialVersionUID = 1L;
	public void actionPerformed(ActionEvent evt) {
		System.exit(0);
	}
}