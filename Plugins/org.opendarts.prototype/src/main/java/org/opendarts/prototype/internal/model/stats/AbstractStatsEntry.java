package org.opendarts.prototype.internal.model.stats;

import java.text.MessageFormat;

import org.opendarts.prototype.model.dart.IDartsThrow;
import org.opendarts.prototype.model.game.IGame;
import org.opendarts.prototype.model.game.IGameEntry;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.model.stats.IStatValue;
import org.opendarts.prototype.model.stats.IStatsEntry;

/**
 * The Class StatsEntry.
 *
 * @param <T> the generic type
 */
public abstract class AbstractStatsEntry<T> implements IStatsEntry<T> {

	/** The key. */
	private final String key;

	/** The value. */
	private IStatValue<T> value;

	/**
	 * Instantiates a new stats entry.
	 *
	 * @param key the key
	 */
	public AbstractStatsEntry(String key) {
		super();
		this.key = key;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MessageFormat.format("{0} : {1}", key, value);
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.stats.IStatsEntry#getKey()
	 */
	@Override
	public String getKey() {
		return this.key;
	}

	/* (non-Javadoc)
	 * @see org.opendarts.prototype.model.stats.IStatsEntry#getValue()
	 */
	@Override
	public IStatValue<T> getValue() {
		return this.value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	protected void setValue(IStatValue<T> value) {
		this.value = value;
	}

	/**
	 * Handle darts throw.
	 *
	 * @param dartsThrow the darts throw
	 * @return true, if successful
	 */
	public boolean handleDartsThrow(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		return this.addNewInput(this.getInput(game, player, gameEntry,
				dartsThrow));
	}

	/**
	 * Undo darts throw.
	 *
	 * @param game the game
	 * @param player the player
	 * @param gameEntry the game entry
	 * @param dartsThrow the darts throw
	 * @return true, if successful
	 */
	public boolean undoDartsThrow(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow) {
		return this.undoNewInput(this.getUndoInput(game, player, gameEntry,
				dartsThrow));
	}

	/**
	 * Gets the input.
	 *
	 * @param game the game
	 * @param player the player
	 * @param gameEntry the game entry
	 * @param dartsThrow the darts throw
	 * @return the input
	 */
	protected abstract T getInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow);

	/**
	 * Gets the undo input.
	 *
	 * @param game the game
	 * @param player the player
	 * @param gameEntry the game entry
	 * @param dartsThrow the darts throw
	 * @return the undo input
	 */
	protected abstract T getUndoInput(IGame game, IPlayer player,
			IGameEntry gameEntry, IDartsThrow dartsThrow);

	/**
	 * Sets the value.
	 *
	 * @param input the input
	 * @return true, if updated, false otherwise
	 */
	protected boolean addNewInput(T input) {
		boolean result = false;
		StatsValue<T> value = (StatsValue<T>) this.getValue();
		if (value == null) {
			// new value
			value = new StatsValue<T>();
			value.setValue(input);
			this.setValue(value);
			result = true;
		} else {
			if (input != value.getValue()) {
				value.setValue(input);
				result = true;
			}
		}
		return result;
	}

	/**
	 * Undo new input.
	 *
	 * @param undoInput the undo input
	 * @return true, if successful
	 */
	protected boolean undoNewInput(T undoInput) {
		return this.addNewInput(undoInput);
	}
}
