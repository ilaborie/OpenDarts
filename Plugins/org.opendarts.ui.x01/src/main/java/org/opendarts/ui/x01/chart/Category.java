package org.opendarts.ui.x01.chart;

/**
 * The Class Category.
 */
public class Category implements Comparable<Category> {

	/** The name. */
	private final String name;

	/**
	 * Instantiates a new category.
	 *
	 * @param name the name
	 */
	public Category(String name) {
		super();
		this.name = name;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Category o) {
		if (o == null) {
			return 1;
		} else {
			return this.name.compareTo(o.name);
		}
	}

}
