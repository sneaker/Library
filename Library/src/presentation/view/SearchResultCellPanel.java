package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.management.Attribute;
import javax.swing.JLabel;
import javax.swing.JPanel;

import presentation.model.ModelController;
import util.ResManager;
import util.TextUtils;
import domain.Book;
import domain.Customer;
import domain.Library;
import domain.Loan;
import domain.Searchable;

/**
 * Holds the graphical elements of an item in the ResultList so changes to the
 * appearance of the result list are preferrably made here.
 */
abstract class SearchResultCellPanel extends JPanel {
	private static final Color SHINY_BLUE = new Color(0xADD8E6);
	private static final String TITLE_FORMAT = "<html><p style='font-size:14pt; padding-left: 1.75cm; text-indent: -1cm;'>";
	private static final long serialVersionUID = -8035455214107649755L;
	protected static final String IMG_CHECK16X16 = "check16x16.png";
	protected static final String IMG_EXCLAMATION16X16 = "exclamation16x16.png";
	protected static final String IMG_WARNING16X16 = "warning16x16.png";
	private final int preferredWidth;
	protected final Library library;

	public SearchResultCellPanel(Searchable active, boolean isSelected,
			int preferredWidth, Library library) {
		this.preferredWidth = preferredWidth;
		this.library = library;
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		if (isSelected)
			setBackground(SHINY_BLUE);

		JLabel l = new JLabel(getFormattedTitle(active));
		l.setIcon(ResManager.getImage(getSymbolPath()));
		l.setFont(new Font(null, Font.PLAIN, 14));
		add(l, BorderLayout.WEST);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(getStatusImage(), 43, 38, null);
	}

	/**
	 * Determine the Symbol displayed to associate this Result type.
	 * 
	 * @return path to the symbol image.
	 */
	abstract String getSymbolPath();

	/**
	 * Determine the state of this result and return an image representing it.
	 * 
	 * @return image representing the result's status
	 */
	abstract Image getStatusImage();

	private String getFormattedTitle(Searchable selected) {
		String text = TITLE_FORMAT
				+ fat(TextUtils.cutText(selected.searchTitle(), preferredWidth,
						TITLE_FORMAT + "<b>"));
		text += "<p style='padding-left: 3cm;'>";
		for (Attribute att : selected.searchDetail().asList()) {
			text += att.getName() + ": ";
			text += TextUtils.cutText(att.getValue().toString(),
					preferredWidth - 100, "");
			text += "<br />";
		}
		text += "</p>";
		return text;
	}

	private String fat(String toBeFormattedFatText) {
		return "<b>" + toBeFormattedFatText + "</b>";
	}
}

/**
 * Visually representing a result showing a book.
 */
class ResultCellBookPanel extends SearchResultCellPanel {
	private static final String IMG_ADD32X32 = "add32x32.png";
	private static final String IMG_RETURN32X32 = "return32x32.png";
	private static final String IMG_AGENDA32X32 = "agenda32x32.png";
	protected static final String IMG_SYMBOL = "book64x64.png";
	private static final long serialVersionUID = -8375612543994217556L;
	private Book active;
	private final boolean isSelected;
	private final ModelController controller;

	public ResultCellBookPanel(Searchable active, boolean isSelected,
			int preferredWidth, ModelController controller) {
		super(active, isSelected, preferredWidth, controller.library);
		this.controller = controller;
		if (active instanceof Book)
			this.active = (Book) active;
		if (active instanceof Loan)
			this.active = ((Loan) active).getBook();
		this.isSelected = isSelected;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (!isSelected)
			return;

		if (library.isBookLent(active)) {
			g.drawImage(ResManager.getImage(IMG_AGENDA32X32).getImage(),
					getX() + 70, getHeight() - 37, null);
			g.drawImage(ResManager.getImage(IMG_RETURN32X32).getImage(),
					getX() + 110, getHeight() - 37, null);
			return;
		}
		if (controller.activeuser_model.getCustomer() == null)
			return;
		g.drawImage(ResManager.getImage(IMG_ADD32X32).getImage(), getX() + 70,
				getHeight() - 37, null);
	}

	@Override
	protected Image getStatusImage() {
		String image = "";
		if (!library.isBookLent(active))
			image = IMG_CHECK16X16;
		if (active.getCondition().equals(Book.Condition.DAMAGED))
			image = IMG_WARNING16X16;
		if (active.getCondition().equals(Book.Condition.WASTE))
			image = IMG_EXCLAMATION16X16;
		return ResManager.getImage(image).getImage();
	}

	@Override
	protected final String getSymbolPath() {
		return IMG_SYMBOL;
	}
}

/**
 * Visually representing a result showing a user.
 */
class ResultCellUserPanel extends SearchResultCellPanel {
	private static final String IMG_SYMBOL = "user64x64.png";
	private static final String IMG_ADD32X32 = "add32x32.png";
	private static final String IMG_DISABLE_USER32X32 = "disablecustomer32x32.png";
	private static final long serialVersionUID = -8375612543994217556L;
	private final Customer active;
	private boolean isSelected;
	private final ModelController controller;

	public ResultCellUserPanel(Customer active, boolean isSelected,
			int preferredWidth, ModelController controller) {
		super(active, isSelected, preferredWidth, controller.library);
		this.active = active;
		this.isSelected = isSelected;
		this.controller = controller;
	}

	protected Image getStatusImage() {
		String image = "";
		if (library.getCustomerLoans(active).size() >= 3)
			image = IMG_EXCLAMATION16X16;
		else
			image = IMG_CHECK16X16;
		return ResManager.getImage(image).getImage();
	}

	@Override
	protected String getSymbolPath() {
		return IMG_SYMBOL;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		String action = null;
		if (!isSelected) {
			if (controller.activeuser_model.getCustomer() == active) {
				action = IMG_DISABLE_USER32X32;
			}
		} else {
			if (controller.activeuser_model.isCustomerActive()
					&& controller.activeuser_model.getCustomer().equals(active)) {
				action = IMG_DISABLE_USER32X32;
			} else {
				action = IMG_ADD32X32;
			}
		}
		g.drawImage(ResManager.getImage(action).getImage(), getX() + 70,
				getHeight() - 37, null);
	}
}
