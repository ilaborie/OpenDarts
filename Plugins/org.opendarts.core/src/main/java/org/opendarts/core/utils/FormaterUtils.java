package org.opendarts.core.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * The Class FormaterUtils.
 */
public final class FormaterUtils {
	
	/** The Constant formatters. */
	private static final FormaterUtils formatters = new FormaterUtils();
	
	/**
	 * Gets the formatters.
	 *
	 * @return the formatters
	 */
	public static FormaterUtils getFormatters() {
		return formatters;
	}

	// Date, Time formater
	/** Date formats. */
	private final ThreadLocal<DateFormat> datetimeFormat;

	/** The date format. */
	private final ThreadLocal<DateFormat> dateFormat;

	/** The time format. */
	private final ThreadLocal<DateFormat> timeFormat;

	/** The datetime number format. */
	private final ThreadLocal<DateFormat> datetimeNumberFormat;

	// Numeric, Decimal Formatter
	/** The number format. */
	private final ThreadLocal<NumberFormat> numberFormat;

	/** The decimal format. */
	private final ThreadLocal<NumberFormat> decimalFormat;

	/**
	 * Instantiates a new formater utils.
	 */
	private FormaterUtils() {
		super();
		this.dateFormat = new ThreadLocal<DateFormat>();
		this.datetimeFormat = new ThreadLocal<DateFormat>();
		this.datetimeNumberFormat = new ThreadLocal<DateFormat>();
		this.timeFormat = new ThreadLocal<DateFormat>();
		this.numberFormat = new ThreadLocal<NumberFormat>();
		this.decimalFormat = new ThreadLocal<NumberFormat>();
	}

	/**
	 * Inits the formaters.
	 */
	private void initFormaters() {
		// Formatter
		this.dateFormat.set(DateFormat.getDateInstance());
		this.datetimeFormat.set(DateFormat.getDateTimeInstance());

		DateFormat timeFormater = new SimpleDateFormat("H:mm:ss");
		timeFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.timeFormat.set(timeFormater);

		DateFormat dateNumberFormater = new SimpleDateFormat(
				"yyyyMMdd_HHmmss_SSS");
		this.datetimeNumberFormat.set(dateNumberFormater);

		// Number and Decimal Format
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setGroupingSeparator(' ');
		this.numberFormat.set(new DecimalFormat("#,##0", symbols));
		this.decimalFormat.set(new DecimalFormat("#,##0.00", symbols));
	}

	/**
	 * Gets the date format.
	 *
	 * @return the date format
	 */
	public DateFormat getDateFormat() {
		if (this.dateFormat.get() == null) {
			this.initFormaters();
		}
		return this.dateFormat.get();
	}

	/**
	 * Gets the datetime format.
	 *
	 * @return the datetime format
	 */
	public DateFormat getDatetimeFormat() {
		if (this.datetimeFormat.get() == null) {
			this.initFormaters();
		}
		return this.datetimeFormat.get();
	}

	/**
	 * Gets the datetime number format.
	 *
	 * @return the datetime number format
	 */
	public DateFormat getDatetimeNumberFormat() {
		if (this.datetimeNumberFormat.get() == null) {
			this.initFormaters();
		}
		return this.datetimeNumberFormat.get();
	}

	/**
	 * Gets the time format.
	 *
	 * @return the time format
	 */
	public DateFormat getTimeFormat() {
		if (this.timeFormat.get() == null) {
			this.initFormaters();
		}
		return this.timeFormat.get();
	}

	/**
	 * Gets the decimal format.
	 *
	 * @return the decimal format
	 */
	public NumberFormat getDecimalFormat() {
		if (this.decimalFormat.get() == null) {
			this.initFormaters();
		}
		return this.decimalFormat.get();
	}

	/**
	 * Gets the number format.
	 *
	 * @return the number format
	 */
	public NumberFormat getNumberFormat() {
		if (this.numberFormat.get() == null) {
			this.initFormaters();
		}
		return this.numberFormat.get();
	}

}
