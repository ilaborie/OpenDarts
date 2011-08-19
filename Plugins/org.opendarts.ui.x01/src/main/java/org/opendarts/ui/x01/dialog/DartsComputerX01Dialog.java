package org.opendarts.ui.x01.dialog;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.UIJob;
import org.opendarts.core.model.dart.IComputerThrow;
import org.opendarts.core.model.dart.IDart;
import org.opendarts.core.model.dart.impl.ThreeDartsThrow;
import org.opendarts.core.model.game.IGameEntry;
import org.opendarts.core.model.player.IComputerPlayer;
import org.opendarts.core.service.game.IGameService;
import org.opendarts.core.x01.model.GameX01;
import org.opendarts.core.x01.model.GameX01Entry;
import org.opendarts.ui.dialog.ThreeDartsComputerDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ThreeDartsComputerDialog.
 */
public class DartsComputerX01Dialog extends ThreeDartsComputerDialog {

	/** The logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(DartsComputerX01Dialog.class);

	/** The Constant DELAY. */
	private static final int DELAY = 1 * 1000; // 1s 

	/** The entry. */
	private final IGameEntry entry;

	/** The player. */
	private final IComputerPlayer player;

	/** The score. */
	private final int score;

	/** The game service. */
	private final IGameService gameService;

	/** The game. */
	private final GameX01 game;

	/**
	 * Instantiates a new computer throw.
	 *
	 * @param parentShell the parent shell
	 * @param player the player
	 * @param game the game
	 * @param entry the entry
	 * @param gameService 
	 */
	public DartsComputerX01Dialog(Shell parentShell, IComputerPlayer player,
			GameX01 game, GameX01Entry entry) {
		super(parentShell, player, game, entry);
		this.player = player;
		this.entry = entry;
		this.game = game;
		this.gameService = game.getParentSet().getGameService();
		this.score = game.getScore(player);
	}

	/**
	 * Gets the form title.
	 *
	 * @return the form title
	 */
	@Override
	public String getFormTitle() {
		return MessageFormat.format("Round #{1} for {0} - Starting at {2}",
				this.player, this.entry.getRound(), this.score);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.FormDialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	/**
	 * Creates the button bar.
	 *
	 * @param parent the parent
	 * @return the control
	 */
	@Override
	protected Control createButtonBar(Composite parent) {
		return new Label(parent, SWT.NONE);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#getShellListener()
	 */
	/**
	 * Gets the shell listener.
	 *
	 * @return the shell listener
	 */
	@Override
	protected ShellListener getShellListener() {
		return new ShellAdapter() {

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ShellAdapter#shellClosed(org.eclipse.swt.events.ShellEvent)
			 */
			@Override
			public void shellClosed(ShellEvent event) {
				event.doit = false; // don't close now
				if (DartsComputerX01Dialog.this.canHandleShellCloseEvent()) {
					DartsComputerX01Dialog.this.handleShellCloseEvent();
				}
			}

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ShellAdapter#shellActivated(org.eclipse.swt.events.ShellEvent)
			 */
			@Override
			public void shellActivated(ShellEvent event) {
				DartsComputerX01Dialog dialog = DartsComputerX01Dialog.this;
				final IComputerThrow dartsThrow = dialog.gameService
						.getComputerDartsThrow(dialog.game, dialog.player);

				List<IDart> wishedList = dartsThrow.getWished();
				List<IDart> doneList = dartsThrow.getDone();

				// Throw darts
				ThrowDartsJob job;
				IDart wished;
				IDart done;
				int i;
				for (i = 0; i < wishedList.size(); i++) {
					wished = wishedList.get(i);
					done = doneList.get(i);
					if (wished != null) {
						job = new ThrowDartsJob(i, wished, done);
						job.schedule(DELAY * (i + 1));
					}
				}

				// Close
				UIJob closeJob = new UIJob("Closer") {
					@Override
					public IStatus runInUIThread(IProgressMonitor monitor) {
						if (DartsComputerX01Dialog.this.getDartThrow() == null) {
							DartsComputerX01Dialog.this
									.setDartThrow((ThreeDartsThrow) dartsThrow
											.getDartsThrow());
						}
						LOG.info("Throw: {}",
								DartsComputerX01Dialog.this.getDartThrow());
						DartsComputerX01Dialog.this.close();
						return Status.OK_STATUS;
					}
				};
				closeJob.schedule((i + 1) * DELAY);
			}
		};
	}

	/**
	 * The Class ThrowDartsJob.
	 */
	private class ThrowDartsJob extends UIJob {

		/** The index. */
		private final int index;
		private final IDart wished;
		private final IDart done;

		/**
		 * Instantiates a new throw darts job.
		 *
		 * @param score the score
		 * @param i the i
		 * @param done 
		 * @param wished 
		 */
		public ThrowDartsJob(int i, IDart wished, IDart done) {
			super("Throw Dart");
			this.wished = wished;
			this.done = done;
			this.index = i;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.progress.UIJob#runInUIThread(org.eclipse.core.runtime.IProgressMonitor)
		 */
		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			DartsComputerX01Dialog.this.displayWished(this.wished, this.index);
			DartsComputerX01Dialog.this.displayDart(this.done,
					DartsComputerX01Dialog.this.score, this.index);
			return Status.OK_STATUS;
		}

	}
}
