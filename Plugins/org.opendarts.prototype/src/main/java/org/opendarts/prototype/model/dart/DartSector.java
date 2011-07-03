package org.opendarts.prototype.model.dart;

/**
 * The Enum DartSector.
 */
public enum DartSector {

	/** The ONE. */
	ONE,

	/** The TWO. */
	TWO,

	/** The THREE. */
	THREE,

	/** The FOUR. */
	FOUR,

	/** The FIVE. */
	FIVE,

	/** The SIX. */
	SIX,

	/** The SEVEN. */
	SEVEN,

	/** The EIGHT. */
	EIGHT,

	/** The NINE. */
	NINE,

	/** The TEN. */
	TEN,

	/** The ELEVEN. */
	ELEVEN,

	/** The TWELVE. */
	TWELVE,

	/** The THIRTEEN. */
	THIRTEEN,

	/** The FOURTEEN. */
	FOURTEEN,

	/** The FIVETEEN. */
	FIVETEEN,

	/** The SIXTEEN. */
	SIXTEEN,

	/** The SEVENTEEN. */
	SEVENTEEN,

	/** The EIGHTEEN. */
	EIGHTEEN,

	/** The NINETEEN. */
	NINETEEN,

	/** The TWENTY. */
	TWENTY,

	/** The BULL. */
	BULL,

	/** The OU t_ o f_ target. */
	OUT_OF_TARGET,

	/** The UNLUCK y_ dart. */
	UNLUCKY_DART;

	/**
	 * Gets the base score.
	 *
	 * @return the base score
	 */
	public int getBaseScore() {
		int baseScore;
		switch (this) {
			case BULL:
				baseScore = 25;
				break;
			case EIGHT:
				baseScore = 8;
				break;
			case EIGHTEEN:
				baseScore = 18;
				break;
			case ELEVEN:
				baseScore = 11;
				break;
			case FIVE:
				baseScore = 5;
				break;
			case FIVETEEN:
				baseScore = 15;
				break;
			case FOUR:
				baseScore = 4;
				break;
			case FOURTEEN:
				baseScore = 14;
				break;
			case NINE:
				baseScore = 9;
				break;
			case NINETEEN:
				baseScore = 19;
				break;
			case ONE:
				baseScore = 1;
				break;
			case SEVEN:
				baseScore = 7;
				break;
			case SEVENTEEN:
				baseScore = 17;
				break;
			case SIX:
				baseScore = 6;
				break;
			case SIXTEEN:
				baseScore = 16;
				break;
			case TEN:
				baseScore = 10;
				break;
			case THIRTEEN:
				baseScore = 13;
				break;
			case THREE:
				baseScore = 3;
				break;
			case TWELVE:
				baseScore = 12;
				break;
			case TWENTY:
				baseScore = 20;
				break;
			case TWO:
				baseScore = 2;
				break;
			case OUT_OF_TARGET:
			case UNLUCKY_DART:
			default:
				baseScore = 0;
		}
		return baseScore;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		String result;
		switch (this) {
			case OUT_OF_TARGET:
				result = "Out";
				break;
			case UNLUCKY_DART:
				result = "UnluckyDart";
				break;
			default:
				result = String.valueOf(this.getBaseScore());
		}
		return result;
	}

}
