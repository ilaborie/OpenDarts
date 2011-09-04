package org.opendarts.ui.stats.view;

import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IDetailsPageProvider;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISet;
import org.opendarts.ui.stats.model.IChart;

/**
 * The Class ElementDetailProvider.
 */
public class ElementDetailProvider implements IDetailsPageProvider {

	/** The session page. */
	private final IDetailsPage sessionPage;
	private final IDetailsPage setPage;
	private final IDetailsPage gamePage;
	private final IDetailsPage chartPage;

	/**
	 * Instantiates a new element detail provider.
	 */
	public ElementDetailProvider() {
		super();
		this.sessionPage = new SessionDetailsPage();
		this.setPage = new SetDetailsPage();
		this.gamePage = new GameDetailsPage();
		this.chartPage = new ChartDetailsPage();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPageProvider#getPageKey(java.lang.Object)
	 */
	@Override
	public Object getPageKey(Object object) {
		Object result = null;
		if (object instanceof ISession) {
			result = ISession.class;
		} else if (object instanceof ISet) {
			result = ISet.class;
		} else if (object instanceof IGame) {
			result = IGame.class;
		} else if (object instanceof IChart) {
			result = IChart.class;
		} else {
			result = object.getClass();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPageProvider#getPage(java.lang.Object)
	 */
	@Override
	public IDetailsPage getPage(Object key) {
		IDetailsPage result;
		if (ISession.class.equals(key)) {
			result = this.sessionPage;
		} else if (ISet.class.equals(key)) {
			result = this.setPage;
		} else if (IGame.class.equals(key)) {
			result = this.gamePage;
		} else if (IChart.class.equals(key)) {
			result = this.chartPage;
		} else {
			result = null;
		}
		return result;
	}

}
