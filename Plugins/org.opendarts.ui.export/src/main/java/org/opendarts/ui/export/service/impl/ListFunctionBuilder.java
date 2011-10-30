package org.opendarts.ui.export.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;

/**
 * The Class ListBuilderFunction.
 *
 * @param <E> the element type
 * @param <F> the generic type
 */
public class ListFunctionBuilder<E, F> implements Function<E, List<F>> {

	/** The functions. */
	private final Function<E, F>[] functions;

	/**
	 * Instantiates a new list builder function.
	 *
	 * @param functions the functions
	 */
	public ListFunctionBuilder(Function<E, F>... functions) {
		super();
		this.functions = functions;
	}

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	public List<F> apply(E input) {
		List<F> list = new ArrayList<F>();
		for (Function<E, F> func : functions) {
			list.add(func.apply(input));
		}
		return list;
	}

}
