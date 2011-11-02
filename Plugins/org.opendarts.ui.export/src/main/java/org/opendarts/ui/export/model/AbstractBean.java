package org.opendarts.ui.export.model;

import java.util.List;

import org.opendarts.core.model.player.IPlayer;
import org.opendarts.core.model.player.func.PlayerToStringFunction;
import org.opendarts.core.utils.FormaterUtils;
import org.opendarts.ui.service.IGameUiProvider;
import org.opendarts.ui.stats.OpenDartsStatsUiPlugin;
import org.opendarts.ui.stats.service.IStatsUiProvider;

/**
 * The Class AbstractBean.
 */
public abstract class AbstractBean<E> {

	private static IStatsUiProvider statsUiProvider;

	private static IGameUiProvider gameUiProvider;

	// Format
	private final FormaterUtils formatters;

	// Functions
	/** The player to string func. */
	private final PlayerToStringFunction playerToStringFunc;

	/** The element. */
	private final E element;

	static {
		statsUiProvider = OpenDartsStatsUiPlugin
				.getService(IStatsUiProvider.class);
		gameUiProvider = OpenDartsStatsUiPlugin
				.getService(IGameUiProvider.class);
	}

	/**
	 * Instantiates a new abstract bean.
	 */
	public AbstractBean(E element) {
		super();
		this.element = element;
		// Formatter
		this.formatters = FormaterUtils.getFormatters();
		// Functions
		this.playerToStringFunc = new PlayerToStringFunction();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.getElement().toString();
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return this.normalize(this.getName());
	}

	/**
	 * Normalize.
	 *
	 * @param string the string
	 * @return the string
	 */
	public String normalize(String string) {
		String result = string.replace(':', '_');
		result = result.replace('/', '_');
		result = result.replace('\\', '_');
		result = result.replace('!', '_');
		result = result.replace('?', '_');
		return result.trim();
	}

	/**
	 * Gets the element.
	 *
	 * @return the element
	 */
	public E getElement() {
		return this.element;
	}

	public abstract String getRootName();

	/**
	 * Gets the player list.
	 *
	 * @return the player list
	 */
	public abstract List<IPlayer> getPlayerList();

	/**
	 * Gets the stats.
	 *
	 * @return the stats
	 */
	public abstract List<Stats<E>> getStats();


	/**
	 * Gets the formatters.
	 *
	 * @return the formatters
	 */
	public FormaterUtils getFormatters() {
		return this.formatters;
	}

	/**
	 * Gets the player to string func.
	 *
	 * @return the player to string func
	 */
	protected PlayerToStringFunction getPlayerToStringFunc() {
		return this.playerToStringFunc;
	}

	/**
	 * Gets the stats ui provider.
	 *
	 * @return the stats ui provider
	 */
	public IStatsUiProvider getStatsUiProvider() {
		return statsUiProvider;
	}

	/**
	 * Gets the game ui provider.
	 *
	 * @return the game ui provider
	 */
	public IGameUiProvider getGameUiProvider() {
		return gameUiProvider;
	}

}
