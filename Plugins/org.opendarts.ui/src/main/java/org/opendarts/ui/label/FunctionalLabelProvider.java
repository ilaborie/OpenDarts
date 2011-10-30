package org.opendarts.ui.label;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.google.common.base.Function;

/**
 * The Class FunctionalLabelProvider.
 *
 * @param <E> the element type
 */
public class FunctionalLabelProvider<E> extends ColumnLabelProvider {

	/** The text func. */
	private final Function<E, String> textFunc;

	/** The clazz. */
	private Class<E> clazz;

	/** The img func. */
	private final Function<E, Image> imgFunc;

	/**
	 * Instantiates a new functional label provider.
	 *
	 * @param clazz the clazz
	 * @param textFunc the text func
	 */
	public FunctionalLabelProvider(Class<E> clazz, Function<E, String> textFunc) {
		this(clazz, textFunc, null);
	}

	/**
	 * Instantiates a new functional label provider.
	 *
	 * @param clazz the clazz
	 * @param textFunc the text func
	 * @param imgFunc the img func
	 */
	public FunctionalLabelProvider(Class<E> clazz,
			Function<E, String> textFunc, Function<E, Image> imgFunc) {
		super();
		this.clazz = clazz;
		this.textFunc = textFunc;
		this.imgFunc = imgFunc;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String result;
		if (element != null && this.clazz.isAssignableFrom(element.getClass())) {
			E elt = this.clazz.cast(element);
			result = textFunc.apply(elt);
		} else {
			result = super.getText(element);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		Image result;
		if (this.imgFunc != null && element != null
				&& this.clazz.isAssignableFrom(element.getClass())) {
			E elt = this.clazz.cast(element);
			result = this.imgFunc.apply(elt);
		} else {
			result = super.getImage(element);
		}
		return result;
	}

}
