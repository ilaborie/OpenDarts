package org.opendarts.ui.export.service.impl;

import java.io.StringWriter;

import org.opendarts.core.utils.FormaterUtils;

import com.google.common.base.Function;
import com.google.common.io.Closeables;

/**
 * The Class EscapeCsvFuntion.
 */
public class EscapeCsvFuntion implements Function<Object, String> {

	/** The null value. */
	private final String nullValue;
	private final FormaterUtils formatters;

	/**
	 * Instantiates a new escape csv funtion.
	 */
	public EscapeCsvFuntion() {
		this("-");
	}

	/**
	 * Instantiates a new escape csv funtion.
	 *
	 * @param nullValue the null value
	 */
	public EscapeCsvFuntion(String nullValue) {
		super();
		this.formatters =FormaterUtils.getFormatters(); 
		this.nullValue = nullValue;
	}

	/* (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	@Override
	public String apply(Object input) {
		String result;
		if (input == null) {
			result = this.nullValue;
		} else if (input instanceof String) {
			String s = (String) input;
			// Escape "
			StringWriter sw = new StringWriter();
			try {
				sw.write('"');
				for (int i = 0; i < s.length(); i++) {
					char c = s.charAt(i);
					if (c == '"') {
						sw.write('"'); // escape double quote
					}
					sw.write(c);
				}
				sw.write('"');
				result = sw.toString();
			} finally {
				Closeables.closeQuietly(sw);
			}
		} else if (input instanceof Long) {
			result = this.formatters.getNumberFormat().format(((Long) input).longValue());
		} else if (input instanceof Integer) {
			result = this.formatters.getNumberFormat().format(((Integer) input).intValue());
		} else if (input instanceof Float) {
			result = this.formatters.getDecimalFormat().format(((Float) input).floatValue());
		} else if (input instanceof Double) {
			result = this.formatters.getDecimalFormat().format(((Double) input).doubleValue());
		} else {
			result = String.valueOf(input);
		}
		return result;
	}

}
