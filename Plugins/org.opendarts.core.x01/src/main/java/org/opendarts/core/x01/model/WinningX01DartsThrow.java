package org.opendarts.core.x01.model;

import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.dart.InvalidDartThrowException;
import org.opendarts.core.model.dart.impl.NoDart;
import org.opendarts.core.model.dart.impl.ThreeDartsThrow;

/**
 * The Class WinningX01DartsThrow.
 */
public class WinningX01DartsThrow extends ThreeDartsThrow {

	/** The nb dart to finish. */
	private final int nbDartToFinish;

	/**
	 * Instantiates a new winning x01 darts throw.
	 *
	 * @param score the score
	 * @param nbDartToFinish the nb dart to finish
	 * @throws InvalidDartThrowException the invalid dart throw exception
	 */
	public WinningX01DartsThrow(int score, int nbDartToFinish)
			throws InvalidDartThrowException {
		super(score);
		this.nbDartToFinish = nbDartToFinish;
		this.checkDartToFinish();
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.dart.ThreeDartsThrow#check()
	 */
	protected void checkDartToFinish() throws InvalidDartThrowException {
		super.check();
		if ((this.nbDartToFinish < 1) || (this.nbDartToFinish > 3)) {
			throw new InvalidDartThrowException("Should finish with 1-3 darts");
		}
	}

	/**
	 * Instantiates a new winning x01 darts throw.
	 *
	 * @param dart1 the dart1
	 * @param dart2 the dart2
	 * @param dart3 the dart3
	 * @throws InvalidDartThrowException 
	 */
	public WinningX01DartsThrow(IDart dart1, IDart dart2, IDart dart3)
			throws InvalidDartThrowException {
		super(dart1, dart2, dart3);
		this.nbDartToFinish = 3;
	}

	/**
	 * Instantiates a new winning x01 darts throw.
	 *
	 * @param dart1 the dart1
	 * @param dart2 the dart2
	 * @throws InvalidDartThrowException the invalid dart throw exception
	 */
	public WinningX01DartsThrow(IDart dart1, IDart dart2)
			throws InvalidDartThrowException {
		super(dart1, dart2, NoDart.NO_DART);
		this.nbDartToFinish = 2;
	}

	/**
	 * Instantiates a new winning x01 darts throw.
	 *
	 * @param dart1 the dart1
	 * @throws InvalidDartThrowException the invalid dart throw exception
	 */
	public WinningX01DartsThrow(IDart dart1) throws InvalidDartThrowException {
		super(dart1, NoDart.NO_DART, NoDart.NO_DART);
		this.nbDartToFinish = 1;
	}

	/**
	 * Instantiates a new winning x01 darts throw.
	 *
	 * @param dThrow the d throw
	 * @param nbDarts the nb darts
	 * @throws InvalidDartThrowException the invalid dart throw exception
	 */
	public WinningX01DartsThrow(ThreeDartsThrow dThrow, int nbDarts)
			throws InvalidDartThrowException {
		this(dThrow.getScore(), nbDarts);
		this.getInternalDarts().addAll(dThrow.getDarts());
		this.checkDartToFinish();
	}

	/**
	 * Instantiates a new winning x01 darts throw.
	 *
	 * @param darts the darts
	 * @throws InvalidDartThrowException the invalid dart throw exception
	 */
	public WinningX01DartsThrow(IDart[] darts) throws InvalidDartThrowException {
		super(darts);
		int nbDarts = 0;
		for (IDart dart : darts) {
			if ((dart != null) && !(dart instanceof NoDart)) {
				nbDarts++;
			}
		}
		this.nbDartToFinish = nbDarts;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.internal.model.dart.ThreeDartsThrow#toString()
	 */
	@Override
	public String toString() {
		return "Win: " + super.toString();
	}

	/**
	 * Gets the nb dart to finish.
	 *
	 * @return the nb dart to finish
	 */
	public int getNbDartToFinish() {
		return this.nbDartToFinish;
	}

}
