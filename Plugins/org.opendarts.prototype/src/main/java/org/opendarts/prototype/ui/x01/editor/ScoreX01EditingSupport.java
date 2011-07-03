/*
 * 
 */
package org.opendarts.prototype.ui.x01.editor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Shell;
import org.opendarts.prototype.internal.model.dart.ThreeDartThrow;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.internal.model.game.x01.GameX01Entry;
import org.opendarts.prototype.model.dart.InvalidDartThrowException;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.ui.x01.utils.DartThrowUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ScoreX01EditingSupport.
 */
public class ScoreX01EditingSupport extends EditingSupport {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(ScoreX01EditingSupport.class);

	/** The game. */
	private final GameX01 game;

	/** The player. */
	private final IPlayer player;

	/** The cell editor. */
	private final TextCellEditor cellEditor;

	/** The dart throw util. */
	private final DartThrowUtil dartThrowUtil;

	/**
	 * Instantiates a new score x01 editing support.
	 *
	 * @param game the game
	 * @param player the player
	 * @param viewer the viewer
	 */
	public ScoreX01EditingSupport(Shell parentShell, GameX01 game,
			IPlayer player, TableViewer viewer) {
		super(viewer);
		this.game = game;
		this.player = player;

		this.dartThrowUtil = new DartThrowUtil(parentShell, game, player);
		this.cellEditor = new TextCellEditor(viewer.getTable());
		this.cellEditor.setValidator(new ICellEditorValidator() {
			@Override
			public String isValid(Object value) {
				String result = null;
				if (!"".equals(value)) {
					try {
						new ThreeDartThrow((String) value);
					} catch (Exception e) {
						LOG.warn("Invalid dart throw", e);
						result = e.toString();
					}
				}
				return result;
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
	 */
	@Override
	protected CellEditor getCellEditor(Object element) {
		return this.cellEditor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.EditingSupport#canEdit(java.lang.Object)
	 */
	@Override
	protected boolean canEdit(Object element) {
		boolean result = (element instanceof GameX01Entry)
				&& (this.player.equals(this.game.getCurrentPlayer()));
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.EditingSupport#getValue(java.lang.Object)
	 */
	@Override
	protected Object getValue(Object element) {
		Object result = "";
		if (element instanceof GameX01Entry) {
			GameX01Entry entry = (GameX01Entry) element;
			ThreeDartThrow dartThrow = entry.getPlayerThrow().get(element);
			if (dartThrow != null) {
				result = String.valueOf(dartThrow.getScore());
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.EditingSupport#setValue(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void setValue(Object element, Object value) {
		if (!"".equals(value)) {
			if (element instanceof GameX01Entry) {
				GameX01Entry entry = (GameX01Entry) element;
			Integer leftScore = this.game.getScore(this.player);
			ThreeDartThrow dartThrow;
			try {
				dartThrow = this.dartThrowUtil.getDartThrow((String) value,
						leftScore);
					this.game.updatePlayerThrow(entry, this.player,
						dartThrow);
			} catch (NumberFormatException e) {
				// Should not arrived
				LOG.error("WTF !", e);
			} catch (InvalidDartThrowException e) {
				// Should not arrived
				LOG.error("WTF !", e);
			}	}
		}
	}
}
