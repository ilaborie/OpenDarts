/*
 * 
 */
package org.opendarts.ui.stats.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.opendarts.ui.stats.model.IChart;

/**
 * The Class StatChartDialog.
 */
public class StatChartDialog extends Dialog {

	/** The charts. */
	private final List<IChart> charts;

	/**
	 * Instantiates a new stat chart dialog.
	 *
	 * @param shell the shell
	 * @param charts the charts
	 */
	public StatChartDialog(Shell shell, List<IChart> charts) {
		super(shell);
		this.charts = charts;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Charts");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayoutFactory.fillDefaults().applyTo(parent);

		Composite main = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(this.charts.size())
				.equalWidth(true).applyTo(main);

		ChartComposite frame;
		for (IChart chart : this.charts) {
			frame = new ChartComposite(main, SWT.NONE, chart.getChart(), true);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(frame);
		}

		// Separator
		Label lbl = new Label(parent, SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(lbl);
		return main;
	}

}
