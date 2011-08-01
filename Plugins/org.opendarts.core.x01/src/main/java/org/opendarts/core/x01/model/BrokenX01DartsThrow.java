package org.opendarts.core.x01.model;

import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.dart.InvalidDartThrowException;
import org.opendarts.core.model.dart.impl.ThreeDartsThrow;

/**
 * The Class WinningX01DartsThrow.
 */
public class BrokenX01DartsThrow extends ThreeDartsThrow {

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
	public BrokenX01DartsThrow(ThreeDartsThrow dThrow)
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
	 * @see org.opendarts.prototype.internal.model.dart.ThreeDartsThrow#toString()
	 */
	@Override
	public String toString() {
		return "Broken: " + super.toString();
	}
}
