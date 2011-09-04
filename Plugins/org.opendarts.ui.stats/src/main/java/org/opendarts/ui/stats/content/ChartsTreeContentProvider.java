package org.opendarts.ui.stats.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.model.session.ISession;
import org.opendarts.core.model.session.ISessionListener;
import org.opendarts.core.model.session.ISet;
import org.opendarts.core.model.session.ISetListener;
import org.opendarts.core.model.session.SessionEvent;
import org.opendarts.core.model.session.SetEvent;
import org.opendarts.core.service.session.ISessionService;
import org.opendarts.core.stats.model.IElementStats;
import org.opendarts.core.stats.service.IStatsProvider;
import org.opendarts.core.stats.service.IStatsService;
import org.opendarts.ui.OpenDartsUiPlugin;
import org.opendarts.ui.stats.OpenDartsStatsUiPlugin;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.stats.service.IStatsUiProvider;
import org.opendarts.ui.stats.service.IStatsUiService;

/**
 * The Class StatsTreeContentProvider.
 */
public class ChartsTreeContentProvider implements ITreeContentProvider,
		ISetListener, ISessionListener {

	/** The viewer. */
	private final TreeViewer viewer;

	/** The session service. */
	private ISessionService sessionService;

	/** The provider. */
	private final IStatsUiProvider provider;

	/** The stats provider. */
	private final IStatsProvider statsProvider;

	/**
	 * Instantiates a new stats tree content provider.
	 * 
	 * @param viewer
	 *            the viewer
	 */
	public ChartsTreeContentProvider(TreeViewer viewer) {
		super();
		this.viewer = viewer;

		this.provider = OpenDartsStatsUiPlugin
				.getService(IStatsUiProvider.class);
		this.statsProvider = OpenDartsStatsUiPlugin
				.getService(IStatsProvider.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		this.sessionService = (ISessionService) inputElement;
		this.sessionService.addListener(this);
		return sessionService.getAllSessions().toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		List<Object> list = new ArrayList<Object>();
		if (parentElement instanceof ISession) {
			ISession session = (ISession) parentElement;
			session.addListener(this);
			// Add Set
			for (ISet set : session.getAllGame()) {
				set.addListener(this);
				list.add(set);
			}
			// Add all charts
			list.addAll(this.getSessionChart(session));
		} else if (parentElement instanceof ISet) {
			ISet set = (ISet) parentElement;
			// Add game
			list.addAll(set.getAllGame());
			// Add all charts
			list.addAll(this.getSetChart(set));
		} else if (parentElement instanceof IGame) {
			IGame game = (IGame) parentElement;
			list.addAll(this.getGameChart(game));
		}

		return list.toArray();
	}

	/**
	 * Gets the session chart.
	 *
	 * @param session the session
	 * @return the session chart
	 */
	private List<IChart> getSessionChart(ISession session) {
		List<IChart> result = new ArrayList<IChart>();

		IElementStats<ISession> sesStats;
		IStatsUiService stUiService;
		for (IStatsService stService : statsProvider.getAllStatsService()) {
			sesStats = stService.getSessionStats(session);
			stUiService = this.provider.getStatsUiService(stService);
			for (String key : sesStats.getStatsKeys()) {
				result.addAll(stUiService.getCharts(session, key));
			}
		}

		return result;
	}

	/**
	 * Gets the sets the chart.
	 *
	 * @param set the set
	 * @return the sets the chart
	 */
	private Collection<IChart> getSetChart(ISet set) {
		List<IChart> result = new ArrayList<IChart>();

		IElementStats<ISet> setStats;
		IStatsUiService stUiService;
		List<IChart> charts;
		for (IStatsService stService : statsProvider.getAllStatsService()) {
			setStats = stService.getSetStats(set);
			stUiService = this.provider.getStatsUiService(stService);
			for (String key : setStats.getStatsKeys()) {
				charts = stUiService.getCharts(set, key);
				if (charts != null) {
					result.addAll(charts);
				}
			}
		}

		return result;
	}

	/**
	 * Gets the game chart.
	 *
	 * @param game the game
	 * @return the game chart
	 */
	private List<IChart> getGameChart(IGame game) {
		List<IChart> result = new ArrayList<IChart>();

		IElementStats<IGame> gameStats;
		IStatsUiService stUiService;
		for (IStatsService stService : statsProvider.getAllStatsService()) {
			gameStats = stService.getGameStats(game);
			stUiService = this.provider.getStatsUiService(stService);
			for (String key : gameStats.getStatsKeys()) {
				result.addAll(stUiService.getCharts(game, key));
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	@Override
	public Object getParent(Object element) {
		Object result = null;
		if (element instanceof ISession) {
			result = OpenDartsUiPlugin.getService(ISessionService.class);
		} else if (element instanceof ISet) {
			result = ((ISet) element).getParentSession();
		} else if (element instanceof IGame) {
			result = ((IGame) element).getParentSet();
		} else if (element instanceof IChart) {
			return ((IChart) element).getElement();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		return this.getChildren(element).length > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// Nothing to dispose
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.core.model.session.ISessionListener#notifySessionEvent(
	 * org.opendarts.core.model.session.SessionEvent)
	 */
	@Override
	public void notifySessionEvent(SessionEvent event) {
		ISession session = event.getSession();
		switch (event.getType()) {
			case SESSION_CANCELED:
			case SESSION_FINISHED:
			case SESSION_INITIALIZED:
				this.viewer.refresh(session);
				break;
			case NEW_CURRENT_SET:
				this.viewer.add(session, event.getSet());
				break;
			default:
				break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.core.model.session.ISessionListener#sessionCreated(org.
	 * opendarts.core.model.session.ISession)
	 */
	@Override
	public void sessionCreated(ISession session) {
		this.viewer.add(this.sessionService, session);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opendarts.core.model.session.ISetListener#notifySetEvent(org.opendarts
	 * .core.model.session.SetEvent)
	 */
	@Override
	public void notifySetEvent(SetEvent event) {
		switch (event.getType()) {
			case SET_CANCELED:
			case SET_FINISHED:
			case SET_INITIALIZED:
				this.viewer.refresh(event.getSet());
				break;
			case NEW_CURRENT_GAME:
				this.viewer.add(event.getSet(), event.getGame());
				break;
			default:
				break;
		}
	}
}
