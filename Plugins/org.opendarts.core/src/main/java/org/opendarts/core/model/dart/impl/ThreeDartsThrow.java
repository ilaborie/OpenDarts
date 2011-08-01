package org.opendarts.core.model.dart.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.dart.IDartsThrow;
import org.opendarts.core.model.dart.InvalidDartThrowException;

/**
 * The Class ThreeDartsThrow.
 */
public class ThreeDartsThrow implements IDartsThrow {

	/** The score. */
	private final int score;

	/** The darts. */
	private final List<IDart> darts;

	/**
	 * Instantiates a new three dart throw.
	 *
	 * @param score the score
	 * @throws InvalidDartThrowException 
	 */
	public ThreeDartsThrow(int score) throws InvalidDartThrowException {
		super();
		this.score = score;
		this.darts = new ArrayList<IDart>();
		this.check();
	}

	/**
	 * Instantiates a new three dart throw.
	 *
	 * @param dart1 the dart1
	 * @param dart2 the dart2
	 * @param dart3 the dart3
	 * @throws InvalidDartThrowException 
	 */
	public ThreeDartsThrow(IDart dart1, IDart dart2, IDart dart3)
			throws InvalidDartThrowException {
		this(dart1.getScore() + dart2.getScore() + dart3.getScore());
		this.darts.add(dart1);
		this.darts.add(dart2);
		this.darts.add(dart3);
	}

	/**
	 * Instantiates a new three dart throw.
	 *
	 * @param darts the darts
	 * @throws InvalidDartThrowException the invalid dart throw exception
	 */
	public ThreeDartsThrow(IDart[] darts) throws InvalidDartThrowException {
		this(getScore(darts));
		this.darts.addAll(Arrays.asList(darts));
	}

	/**
	 * Gets the score.
	 *
	 * @param darts the darts
	 * @return the score
	 */
	protected static int getScore(IDart[] darts) {
		int score = 0;
		for (IDart dart : darts) {
			if (dart != null) {
				score += dart.getScore();
			}
		}
		return score;
	}

	/**
	 * Instantiates a new three dart throw.
	 *
	 * @param value the value
	 * @throws InvalidDartThrowException 
	 * @throws NumberFormatException 
	 */
	public ThreeDartsThrow(String value) throws NumberFormatException,
			InvalidDartThrowException {
		this(Integer.parseInt(value));
	}

	/**
	 * Check score.
	 *
	 * @throws InvalidDartThrowException the invalid dart throw exception
	 */
	protected void check() throws InvalidDartThrowException {
		List<Integer> invalid = Arrays.asList(172, 173, 175, 176, 178, 179);
		if (this.score < 0) {
			throw new InvalidDartThrowException("Score should be > 0");
		} else if (this.score > 180) {
			throw new InvalidDartThrowException("Score should be ² 180");
		} else if (invalid.contains(this.score)) {
			throw new InvalidDartThrowException("Score should be " + this.score);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result;
		if (this.darts.isEmpty()) {
			result = String.valueOf(this.score);
		} else {
			result = MessageFormat.format("{0} - {1}", this.score, this.darts);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.dart.IDartsThrow#getScore()
	 */
	@Override
	public int getScore() {
		return this.score;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.dart.IDartsThrow#getList()
	 */
	@Override
	public List<IDart> getDarts() {
		return Collections.unmodifiableList(this.darts);
	}

	/**
	 * Gets the internal darts.
	 *
	 * @return the internal darts
	 */
	protected List<IDart> getInternalDarts() {
		return this.darts;
	}

}
