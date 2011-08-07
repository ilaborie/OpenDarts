package org.opendarts.core.model.dart.impl;

import java.util.ArrayList;
import java.util.List;

import org.opendarts.core.model.dart.IComputerThrow;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.dart.IDartsThrow;

/**
 * The Class ComputerThrow.
 */
public class ComputerThrow implements IComputerThrow {

	/** The wished. */
	private final List<IDart> wished;
	
	/** The done. */
	private final List<IDart> done;
	
	/** The dart throw. */
	private final IDartsThrow dartThrow;
	
	/**
	 * Instantiates a new computer throw.
	 *
	 * @param dartsThrow the darts throw
	 * @param wished the wished
	 * @param done the done
	 */
	public ComputerThrow(IDartsThrow dartsThrow,List<IDart> wished, List<IDart> done) {
		super();
		this.dartThrow = dartsThrow;
		this.wished = new ArrayList<IDart>(wished);
		this.done = new ArrayList<IDart>(done);
	}
	

	/* (non-Javadoc)
	 * @see org.opendarts.core.model.dart.IComputerThrow#getDartsThrow()
	 */
	@Override
	public IDartsThrow getDartsThrow() {
		return this.dartThrow;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.model.dart.IComputerThrow#getWished()
	 */
	@Override
	public List<IDart> getWished() {
		return this.wished;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.core.model.dart.IComputerThrow#getDone()
	 */
	@Override
	public List<IDart> getDone() {
		return this.done;
	}
}
