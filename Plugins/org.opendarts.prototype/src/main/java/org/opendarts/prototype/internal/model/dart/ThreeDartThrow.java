package org.opendarts.prototype.internal.model.dart;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.opendarts.prototype.model.dart.IDart;
import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.dart.InvalidDartThrowException;

/**
 * The Class ThreeDartThrow.
 */
public class ThreeDartThrow implements IDartsThrow {

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
	public ThreeDartThrow(int score) throws InvalidDartThrowException {
		super();
		this.score = score;
		this.darts = new ArrayList<IDart>();
		this.check();
	}

	/**
	 * Instantiates a new three dart throw.
	 *
	 * @param value the value
	 * @throws InvalidDartThrowException 
	 * @throws NumberFormatException 
	 */
	public ThreeDartThrow(String value) throws NumberFormatException,
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

	/**
	 * Instantiates a new three dart throw.
	 *
	 * @param dart1 the dart1
	 * @param dart2 the dart2
	 * @param dart3 the dart3
	 * @throws InvalidDartThrowException 
	 */
	public ThreeDartThrow(IDart dart1, IDart dart2, IDart dart3)
			throws InvalidDartThrowException {
		this(dart1.getScore() + dart2.getScore() + dart3.getScore());
		this.darts.add(dart1);
		this.darts.add(dart2);
		this.darts.add(dart3);
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
