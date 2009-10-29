package domain;

import javax.management.AttributeList;

/**
 * Every search result consists of a title and some lines of details.
 */
public interface Searchable {
	public String searchTitle();

	public AttributeList searchDetail();
}
