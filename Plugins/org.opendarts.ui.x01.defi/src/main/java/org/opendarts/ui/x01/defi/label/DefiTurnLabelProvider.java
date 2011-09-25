package org.opendarts.ui.x01.defi.label;

import org.opendarts.ui.x01.label.TurnLabelProvider;

/**
 * The Class DefiTurnLabelProvider.
 */
public class DefiTurnLabelProvider extends TurnLabelProvider {
	
	/**
	 * Instantiates a new defi turn label provider.
	 *
	 * @param useFont the use font
	 */
	public DefiTurnLabelProvider(boolean useFont) {
		super(useFont);
	}
	
	/**
	 * Instantiates a new defi turn label provider.
	 */
	public DefiTurnLabelProvider() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.opendarts.ui.x01.label.TurnLabelProvider#getPattern()
	 */
	@Override
	protected String getPattern() {
		return "#{0}";
	}

}
