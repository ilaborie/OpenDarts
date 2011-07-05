package org.opendarts.prototype.internal.model.dart;

import org.opendarts.prototype.model.dart.DartSector;
import org.opendarts.prototype.model.dart.DartZone;

/**
 * The Class NoDart.
 */
public class NoDart extends Dart {

	/** The Constant NO_DART. */
	public static final NoDart NO_DART = new NoDart();

	/**
	 * Instantiates a new no dart.
	 */
	private NoDart() {
		super(DartSector.NONE, DartZone.NONE);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.dart.Dart#toString()
	 */
	@Override
	public String toString() {
		return "-";
	}

}
