package org.opendarts.prototype.internal.model.dart.x01;

import org.opendarts.prototype.internal.model.dart.ThreeDartThrow;
import org.opendarts.prototype.model.dart.IDart;
import org.opendarts.prototype.model.dart.InvalidDartThrowException;

/**
 * The Class WinningX01DartsThrow.
 */
public class BrokenX01DartsThrow extends ThreeDartThrow {

	/**
	 * Instantiates a new winning x01 darts throw.
	 *
	 * @param score the score
	 * @throws InvalidDartThrowException the invalid dart throw exception
	 */
	public BrokenX01DartsThrow(int score) throws InvalidDartThrowException {
		super(score);
	}

	/**
	 * Instantiates a new winning x01 darts throw.
	 *
	 * @param dart1 the dart1
	 * @param dart2 the dart2
	 * @param dart3 the dart3
	 * @throws InvalidDartThrowException 
	 */
	public BrokenX01DartsThrow(IDart dart1, IDart dart2, IDart dart3)
			throws InvalidDartThrowException {
		super(dart1, dart2, dart3);
	}

	/**
	 * Instantiates a new broken x01 darts throw.
	 *
	 * @param dThrow the d throw
	 * @throws InvalidDartThrowException 
	 */
	public BrokenX01DartsThrow(ThreeDartThrow dThrow)
			throws InvalidDartThrowException {
		this(dThrow.getScore());
		this.getInternalDarts().addAll(dThrow.getDarts());
	}

	/**
	 * Instantiates a new broken x01 darts throw.
	 *
	 * @param darts the darts
	 * @throws InvalidDartThrowException the invalid dart throw exception
	 */
	public BrokenX01DartsThrow(IDart[] darts) throws InvalidDartThrowException {
		super(darts);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.dart.ThreeDartThrow#toString()
	 */
	@Override
	public String toString() {
		return "Broken: " + super.toString();
	}
}
