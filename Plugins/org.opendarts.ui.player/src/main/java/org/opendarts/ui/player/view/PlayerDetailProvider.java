package org.opendarts.ui.player.view;

import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.opendarts.core.model.player.IPlayer;

/**
 * The Class ElementDetailProvider.
 */
public class PlayerDetailProvider implements IDetailsPageProvider {

	/** The session page. */
	private final IDetailsPage playerPage;
	private final IDetailsPage computerPage;

	/**
	 * Instantiates a new element detail provider.
	 */
	public PlayerDetailProvider() {
		super();
		this.playerPage = new PlayerDetailPage();
		this.computerPage = new ComputerDetailPage();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPageProvider#getPageKey(java.lang.Object)
	 */
	@Override
	public Object getPageKey(Object object) {
		Object result = null;
		IPlayer player = (IPlayer) object;
		if (player.isComputer()) {
			result = "computer";
		} else {
			result = "player";
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPageProvider#getPage(java.lang.Object)
	 */
	@Override
	public IDetailsPage getPage(Object key) {
		IDetailsPage result;
		if ("computer".equals(key)) {
			result = this.computerPage;
		} else {
			result = this.playerPage;
		}
		return result;
	}

}
