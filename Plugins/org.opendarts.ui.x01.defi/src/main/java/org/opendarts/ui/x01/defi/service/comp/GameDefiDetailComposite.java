/*
 * 
 */
package org.opendarts.ui.x01.defi.service.comp;

import java.text.MessageFormat;
import java.util.Date;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.opendarts.core.model.game.IGame;
import org.opendarts.core.x01.defi.model.GameX01Defi;
import org.opendarts.core.x01.defi.model.GameX01DefiDefinition;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;
import org.opendarts.ui.x01.utils.comp.GameDetailComposite;

/**
 * The Class SessionDetailComposite.
 */
public class GameDefiDetailComposite extends Composite {
	private final GameX01Defi game;
	private final OpenDartsFormsToolkit toolkit;

	private Text txtTotalTime;
	private Text txtNbThrow;
	private Text txtNbDarts;
	private Text txtSecondsByThrow;
	private Text txtPointsBySeconds;

	/**
	 * Instantiates a new session detail composite.
	 *
	 * @param parent the parent
	 * @param game2 
	 */
	public GameDefiDetailComposite(Composite parent, IGame game) {
		super(parent, SWT.NONE);
		this.toolkit = OpenDartsFormsToolkit.getToolkit();
		this.game = (GameX01Defi) game;
		GridLayoutFactory.fillDefaults().applyTo(this);
		this.createContents();
	}

	/**
	 * Creates the contents.
	 */
	private void createContents() {
		// Defi info
		Composite cmpDetail = this.toolkit.createComposite(this);
		GridDataFactory.fillDefaults().grab(true, false);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(cmpDetail);

		Label label;

		// Total time
		label = this.toolkit.createLabel(cmpDetail, "Total time:");
		GridDataFactory.fillDefaults().applyTo(label);

		this.txtTotalTime = this.toolkit.createText(cmpDetail,
				this.getTotalTime());
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(this.txtTotalTime);

		// nb throw
		label = this.toolkit.createLabel(cmpDetail, "Nb Throws:");
		GridDataFactory.fillDefaults().applyTo(label);

		this.txtNbThrow = this.toolkit.createText(cmpDetail, this.getNbThrow());
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(this.txtNbThrow);

		// nb Darts
		label = this.toolkit.createLabel(cmpDetail, "Nb Darts:");
		GridDataFactory.fillDefaults().applyTo(label);

		this.txtNbDarts = this.toolkit.createText(cmpDetail, this.getNbDarts());
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(this.txtNbDarts);

		// seconds by throw
		label = this.toolkit.createLabel(cmpDetail, "Seconds by Throw:");
		GridDataFactory.fillDefaults().applyTo(label);

		this.txtSecondsByThrow = this.toolkit.createText(cmpDetail,
				this.getSecondsByThrow());
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(this.txtSecondsByThrow);

		// point by Seconds
		label = this.toolkit.createLabel(cmpDetail, "Point by Seconds:");
		GridDataFactory.fillDefaults().applyTo(label);

		this.txtPointsBySeconds = this.toolkit.createText(cmpDetail,
				this.getPointsBySecond());
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(this.txtPointsBySeconds);

		// Classic detail
		label = toolkit.createSeparator(this, SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(label);

		Composite cmpTable = this.toolkit.createComposite(cmpDetail);
		GridLayoutFactory.fillDefaults().applyTo(cmpTable);
		GridDataFactory.fillDefaults().span(2, 1).applyTo(cmpTable);
		new GameDetailComposite(cmpTable, this.game);
	}

	/**
	 * Gets the points by second.
	 *
	 * @return the points by second
	 */
	private String getPointsBySecond() {
		int points = this.game.getScoreToDo();
		double duration = (double) this.game.getDuration() / (double) 1000;

		double pointBySec = ((double) points) / (duration);
		return MessageFormat.format("{0}", pointBySec);
	}

	/**
	 * Gets the seconds by throw.
	 *
	 * @return the seconds by throw
	 */
	private String getSecondsByThrow() {
		double duration = (double) this.game.getDuration() / (double) 1000;
		double nbThrows = (double) this.game.getNbDartToFinish()
				/ (double) (this.game.getPlayers().size()*3);

		double secByThrow = duration / nbThrows ;

		return MessageFormat.format("{0}", secByThrow);
	}

	/**
	 * Gets the nb darts.
	 *
	 * @return the nb darts
	 */
	private String getNbDarts() {
		Integer nbDarts = this.game.getNbDartToFinish();
		return MessageFormat.format("{0}", nbDarts);
	}

	/**
	 * Gets the nb throw.
	 *
	 * @return the nb throw
	 */
	private String getNbThrow() {
		int size = this.game.getGameEntries().size();
		return String.valueOf(size);
	}

	/**
	 * Gets the total time.
	 *
	 * @return the total time
	 */
	private String getTotalTime() {
		long duration = this.game.getDuration();
		Date date = new Date(duration);
		return ((GameX01DefiDefinition) this.game.getParentSet()
				.getGameDefinition()).getTimeFormatter().format(date);
	}

}
