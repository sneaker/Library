package presentation.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import presentation.control.MarkDefectActionListener;
import presentation.model.ModelController;
import presentation.model.TabBookModel;
import domain.Book;
import domain.Book.Condition;

/**
 * Displays formatted details of a book (without loan details).
 */
public class TabBookDetailJPanel extends JPanel implements Observer {
	private static final Font COMMENT_FONT = new Font("SansSerif", Font.PLAIN,
			16);
	private static final long serialVersionUID = -5782308158955619340L;
	private static final String TITLE = "Katalogdaten";
	private static final String AUTHOR_LABEL_TEXT = "Autor: ";
	private static final String CONDITION_LABEL_TEXT = "Zustand: ";
	private static final String PUBLISHER_LABEL_TEXT = "Verlag: ";
	private static final Font DETAIL_LABEL_FONT = new Font("SansSerif",
			Font.BOLD, 16);
	private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
	private static final String INACTIVE_TEXT = "Kein Buch ausgewählt. \n\n1) Klicke auf \"Recherche\".\n2) Suche nach einem Buch.\n3) Klicke auf das gesuchte Buch.\n\nDie Details des Buches werden dann hier angezeigt.";
	private JTextArea titleText;
	private JLabel authorLabel;
	private JLabel publishLabel;
	private DetailTextField titleTextEditable;
	private DetailTextField authorText;
	private DetailTextField publishText;
	private JLabel conditionLabel;
	private DetailTextField conditionText;
	private TabBookModel bmodel;
	private final ModelController controller;
	private JComboBox conditionCombo;
	private JTextArea commentText;

	public TabBookDetailJPanel(ModelController controller) {
		this.controller = controller;
		bmodel = controller.booktab_model;
		controller.booktab_model.addObserver(this);
		controller.library.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		setLayout(new GridBagLayout());
		setBorder(new TitledBorder(TITLE));

		initTitle();
		initAuthorText();
		initPublishText();
		initConditionText();
		initComment();
	}

	private void initTitle() {
		titleTextEditable = new DetailTextField();
		titleTextEditable.setFont(TITLE_FONT);
		titleTextEditable
				.addKeyListener(new ValidateBookTitleKeyListener());

		titleText = new JTextArea();
		titleText.setText(INACTIVE_TEXT);
		titleText.setBackground(this.getBackground());
		titleText.setEditable(false);
		titleText.setFont(TITLE_FONT);
		titleText.setLineWrap(true);
		titleText.setWrapStyleWord(true);
		add(titleText, getTitleGridBagConstraints());
	}

	private GridBagConstraints getTitleGridBagConstraints() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0.00001;
		return c;
	}

	private void initAuthorText() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		authorLabel = new JLabel(AUTHOR_LABEL_TEXT);
		authorLabel.setVisible(false);
		authorLabel.setFont(DETAIL_LABEL_FONT);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.00001;
		c.weighty = 0.00001;
		add(authorLabel, c);

		authorText = new DetailTextField();
		authorText.setEditable(false);
		authorText.addKeyListener(new ValidateBookAuthorKeyListener());
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0.00001;
		add(authorText, c);
	}

	private void initPublishText() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		publishLabel = new JLabel(PUBLISHER_LABEL_TEXT);
		publishLabel.setVisible(false);
		publishLabel.setFont(DETAIL_LABEL_FONT);
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.0001;
		add(publishLabel, c);

		publishText = new DetailTextField();
		publishText.setEditable(false);
		publishText.addKeyListener(new ValidateBookPublisherKeyListener());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 1.0;
		add(publishText, c);
	}

	private void initConditionText() {
		GridBagConstraints c = new GridBagConstraints();
		conditionLabel = new JLabel(CONDITION_LABEL_TEXT);
		conditionLabel.setVisible(false);
		conditionLabel.setFont(DETAIL_LABEL_FONT);

		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0;
		add(conditionLabel, c);

		conditionText = new DetailTextField();
		conditionCombo = new JComboBox(Book.Condition.getConditionStrings());
		conditionText.setEditable(false);
		add(conditionText, getConditionGridBagConstraints());
	}

	private GridBagConstraints getConditionGridBagConstraints() {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 1.0;
		c.weighty = 0.0001;
		return c;
	}

	private void initComment() {
		commentText = new JTextArea();
		commentText.setEditable(false);
		commentText.setBackground(this.getBackground());
		commentText.setBorder(new EmptyBorder(3, 3, 3, 3));
		commentText.setFont(COMMENT_FONT);
		commentText.setLineWrap(true);
		commentText.setWrapStyleWord(true);
		commentText.addKeyListener(new ValidateBookCommentKeyListener());

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 4;
		c.weightx = 0.0;
		c.weighty = 1.0;
		add(commentText, c);
	}

	private void setDetailsVisibility(boolean isBookActive) {
		authorLabel.setVisible(isBookActive);
		authorText.setVisible(isBookActive);
		publishLabel.setVisible(isBookActive);
		publishText.setVisible(isBookActive);
		conditionLabel.setVisible(isBookActive);
		conditionText.setVisible(isBookActive);
		commentText.setVisible(isBookActive);
	}

	private void updateDetails() {
		conditionText.setRed(bmodel.getActiveBook().getCondition().equals(
				Book.Condition.WASTE));
		conditionText.setText(bmodel.getActiveBook().getConditionString());
		titleText.setText(bmodel.getActiveBook().getTitle().getName());
		authorText.setText(bmodel.getActiveBook().getTitle().getAuthor());
		publishText.setText(bmodel.getActiveBook().getTitle().getPublisher());
		commentText.setText(bmodel.getActiveBook().getConditionComment());
	}

	private void updateErrors() {
		titleTextEditable.setError(controller.booktab_model.isErrorAtTitle());
		authorText.setError(controller.booktab_model.isErrorAtAuthor());
		publishText.setError(controller.booktab_model.isErrorAtPublisher());
		conditionText.setError(controller.booktab_model.isErrorAtCondition());
	}

	public void update(Observable o, Object arg) {
		if (arg instanceof Condition) {
			String c = Book.Condition.getConditionString((Condition)arg);
			if (!conditionCombo.getSelectedItem().equals(c))
				conditionCombo.setSelectedItem(c);
		}
		boolean isBookActive = bmodel.getActiveBook() != null;
		setEditable(isBookActive && controller.booktab_model.isEditing());
		updateTitle(isBookActive);
		setDetailsVisibility(isBookActive);
		updateErrors();

		stopEditingWhenBookChanged();

		if (isBookActive && !controller.booktab_model.isEditing())
			updateDetails();
	}

	private void stopEditingWhenBookChanged() {
		if (!controller.booktab_model.isSameBook()
				&& controller.booktab_model.isEditing()
				&& controller.booktab_model.getActiveBook() != null) {
			setEditable(false);
			controller.booktab_model.setEditing(false);
			controller.status_model
					.setTempStatus("Veränderte Buchdaten wurden automatisch gesichert.");
		}
		controller.booktab_model.setLastBook(controller.booktab_model
				.getActiveBook());
	}

	private void updateTitle(boolean isBookActive) {
		if (isBookActive)
			titleText.setText(controller.booktab_model.getActiveBook()
					.getTitle().getName());
		else
			titleText.setText(INACTIVE_TEXT);
	}

	private void setEditable(boolean editable) {
		setTitleTextEditable(editable);
		authorText.setEditable(editable);
		publishText.setEditable(editable);
		setConditionEditable(editable);
		setCommentTextEditable(editable);
		getParent().validate();
	}

	private void setCommentTextEditable(boolean editable) {
		commentText.setEditable(editable);
		if (editable)
			commentText.setBackground(new JTextArea().getBackground());
		else
			commentText.setBackground(this.getBackground());
	}

	private void setConditionEditable(boolean editable) {
		if (editable) {
			if (!isAncestorOf(conditionText))
				return;
			remove(conditionText);
			add(conditionCombo, getConditionGridBagConstraints());

			Condition co = controller.booktab_model.getActiveBook()
					.getCondition();
			conditionCombo.setSelectedIndex(conditionToIndex(co));

			conditionCombo.addItemListener(new ValidateConditionCombo());
			conditionCombo.repaint();
		} else {
			if (!isAncestorOf(conditionCombo))
				return;
			remove(conditionCombo);
			add(conditionText, getConditionGridBagConstraints());
		}
	}

	private int conditionToIndex(Condition co) {
		int index = 0;
		switch (co) {
		case NEW: {
			index = 0;
			break;
		}
		case GOOD: {
			index = 1;
			break;
		}
		case DAMAGED: {
			index = 2;
			break;
		}
		case WASTE: {
			index = 3;
			break;
		}
		}
		return index;
	}

	private void setTitleTextEditable(boolean editable) {
		if (editable) {
			if (!isAncestorOf(titleText))
				return;
			remove(titleText);
			add(titleTextEditable, getTitleGridBagConstraints());
			titleTextEditable.setText(controller.booktab_model.getActiveBook()
					.getTitle().getName());
			titleTextEditable.setEditable(true);
			titleTextEditable.requestFocus();
		} else {
			if (!isAncestorOf(titleTextEditable))
				return;
			remove(titleTextEditable);
			add(titleText, getTitleGridBagConstraints());
		}
	}

	/**
	 * Validates the text field it's listening to and saves changed book title
	 * name into the active book title.
	 */
	public class ValidateBookTitleKeyListener extends KeyAdapter {
		public void keyReleased(java.awt.event.KeyEvent e) {
			super.keyReleased(e);
			if (!(e.getComponent() instanceof JTextComponent))
				return;
			JTextComponent origin = (JTextComponent) e.getComponent();
			String newTitle = origin.getText();
			boolean ok = newTitle.length() != 0;
			controller.booktab_model.setErrorAtTitle(!ok);
			controller.booktab_model.getActiveBook().getTitle().setName(
					newTitle);
			controller.booktab_model.fireDataChanged();
		}
	}

	/**
	 * Validates the text field it's listening to and saves changed author name
	 * into the active book title.
	 */
	public class ValidateBookAuthorKeyListener extends KeyAdapter {
		public void keyReleased(java.awt.event.KeyEvent e) {
			super.keyReleased(e);
			if (!(e.getComponent() instanceof JTextComponent))
				return;
			JTextComponent origin = (JTextComponent) e.getComponent();
			String newAuthor = origin.getText();
			boolean ok = newAuthor.length() != 0;
			controller.booktab_model.setErrorAtAuthor(!ok);
			controller.booktab_model.getActiveBook().getTitle().setAuthor(
					newAuthor);
			controller.booktab_model.fireDataChanged();
		}
	}

	/**
	 * Validates the text field it's listening to and saves changed publisher
	 * into the active book title.
	 */
	public class ValidateBookPublisherKeyListener extends KeyAdapter {
		public void keyReleased(java.awt.event.KeyEvent e) {
			super.keyReleased(e);
			if (!(e.getComponent() instanceof JTextComponent))
				return;
			JTextComponent origin = (JTextComponent) e.getComponent();
			String newPublisher = origin.getText();
			boolean ok = newPublisher.length() != 0;
			controller.booktab_model.setErrorAtPublisher(!ok);
			controller.booktab_model.getActiveBook().getTitle().setPublisher(
					newPublisher);
			controller.booktab_model.fireDataChanged();
		}
	}

	/**
	 * Reacts to changes made to the condition combo box while editing. When
	 * selecting "WASTE" a dialog is showed asking the user whether he / she
	 * wants to send a bill to the last customer who damaged the book.
	 */
	private final class ValidateConditionCombo implements ItemListener {
		private Condition oldCondition;

		public void itemStateChanged(ItemEvent e) {
			Condition newCondition = Book.Condition.getCondition((String)e.getItem());
			oldCondition = controller.booktab_model.getActiveBook().getCondition();
			if (newCondition == Book.Condition.WASTE && oldCondition != Book.Condition.WASTE) {
				new MarkDefectActionListener(controller, oldCondition).actionPerformed(new ActionEvent(newCondition, 0, ""));
			} else {
				controller.booktab_model.getActiveBook().setCondition(
						newCondition);
				controller.status_model.setTempStatus("Buch markiert als "
						+ Book.Condition.getConditionString(newCondition));
			}
			oldCondition = newCondition;
		}
	}

	public class ValidateBookCommentKeyListener extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			if (!(e.getComponent() instanceof JTextComponent))
				return;
			JTextComponent origin = (JTextComponent) e.getComponent();
			String newComment = origin.getText();
			controller.booktab_model.getActiveBook().setConditionComment(
					newComment);
			controller.booktab_model.fireDataChanged();
		}
	}
}