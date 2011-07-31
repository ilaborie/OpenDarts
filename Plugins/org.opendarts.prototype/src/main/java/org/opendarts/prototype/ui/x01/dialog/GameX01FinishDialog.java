package org.opendarts.prototype.ui.x01.dialog;

import java.text.MessageFormat;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.opendarts.prototype.internal.model.dart.x01.DartX01Util;
import org.opendarts.prototype.internal.model.game.x01.GameX01;
import org.opendarts.prototype.model.player.IPlayer;
import org.opendarts.prototype.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class GameX01FinishDialog.
 */
public class GameX01FinishDialog extends FormDialog implements
		SelectionListener, KeyListener {

	/** The nb dart. */
	private int nbDarts;

	/** The game. */
	private final GameX01 game;

	/** The player. */
	private final IPlayer player;

	/** The toolkit. */
	private final OpenDartsFormsToolkit toolkit;

	/** The score. */
	private final int score;

	/** The btn one. */
	private Button btnOne;

	/** The btn two. */
	private Button btnTwo;

	/** The btn three. */
	private Button btnThree;

	/** The btn broken. */
	private Button btnBroken;

	/** The could finish one. */
	private final boolean couldFinishOne;

	/** The could finish two. */
	private final boolean couldFinishTwo;

	/** The could finish three. */
	private final boolean couldFinishThree;

	/**
	 * Instantiates a new game x501 finish dialog.
	 *
	 * @param parentShell the parent shell
	 * @param game the game
	 * @param player the player
	 * @param score the score
	 */
	public GameX01FinishDialog(Shell parentShell, GameX01 game, IPlayer player,
			int score) {
		super(parentShell);
		this.game = game;
		this.player = player;
		this.score = score;
		this.toolkit = OpenDartsFormsToolkit.getToolkit();

		// What can be done
		this.couldFinishOne = DartX01Util.couldFinish(this.score, 1);
		this.couldFinishTwo = this.couldFinishOne
				|| DartX01Util.couldFinish(this.score, 2);
		this.couldFinishThree = this.couldFinishTwo
				|| DartX01Util.couldFinish(this.score, 3);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.FormDialog#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm mform) {
		String title = MessageFormat.format("{0} Finished ?", this.game,
				this.player);
		ScrolledForm form = mform.getForm();
		form.setText(title);
		this.toolkit.decorateFormHeading(form.getForm());

		Composite body = form.getBody();
		GridLayoutFactory.fillDefaults().margins(5, 5).applyTo(body);
		body.addKeyListener(this);

		// Message
		String msg = MessageFormat.format("{0} has finished with {1} ?",
				this.player, this.score);
		Label lbl = this.toolkit.createLabel(body, msg);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(lbl);

		// main composite
		Composite main = this.toolkit.createComposite(body);
		GridLayoutFactory.fillDefaults().margins(10, 10).numColumns(3)
				.equalWidth(true).applyTo(main);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(main);

		GridDataFactory btnData = GridDataFactory.fillDefaults();
		// Finish Buttons
		this.btnOne = this.toolkit.createButton(main, "One Dart", SWT.PUSH);
		this.btnOne.setEnabled(this.couldFinishOne);
		this.btnOne.addSelectionListener(this);
		btnData.copy().applyTo(this.btnOne);

		this.btnTwo = this.toolkit.createButton(main, "Two Dart", SWT.PUSH);
		this.btnTwo.setEnabled(this.couldFinishTwo);
		this.btnTwo.addSelectionListener(this);
		btnData.copy().applyTo(this.btnTwo);

		this.btnThree = this.toolkit.createButton(main, "Three Dart", SWT.PUSH);
		this.btnThree.setEnabled(this.couldFinishThree);
		this.btnThree.addSelectionListener(this);
		btnData.copy().applyTo(this.btnThree);

		lbl = this.toolkit.createLabel(main, "", SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().span(3, 1).grab(true, false)
				.applyTo(lbl);

		this.btnBroken = this.toolkit.createButton(main, "Broken", SWT.PUSH);
		this.btnBroken.addSelectionListener(this);
		btnData.copy().applyTo(this.btnBroken);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		Object src = e.getSource();
		if (this.btnBroken.equals(src)) {
			this.end(0);
		} else if (this.btnOne.equals(src)) {
			this.end(1);
		} else if (this.btnTwo.equals(src)) {
			this.end(2);
		} else if (this.btnThree.equals(src)) {
			this.end(3);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.keyCode) {
			case SWT.KEYPAD_0:
				this.end(0);
				break;
			case SWT.KEYPAD_1:
				if (this.couldFinishOne) {
					this.end(1);
				}
				break;
			case SWT.KEYPAD_2:
				if (this.couldFinishTwo) {
					this.end(2);
				}
				break;
			case SWT.KEYPAD_3:
				if (this.couldFinishThree) {
					this.end(3);
				}
				break;
			default:
				e.doit = true;
				break;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// Nothing to do
	}

	/**
	 * End.
	 *
	 * @param nbDarts the nb darts
	 */
	protected void end(int nbDarts) {
		this.nbDarts = nbDarts;
		this.close();
	}

	/**
	 * Gets the nb darts.
	 *
	 * @return the nb darts
	 */
	public int getNbDarts() {
		return this.nbDarts;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.FormDialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createButtonBar(Composite parent) {
		return new Label(parent, SWT.NONE);
	}

}
