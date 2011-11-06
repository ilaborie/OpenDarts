package org.opendarts.ui.export.composite;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.opendarts.ui.export.service.impl.BasicExportOption;
import org.opendarts.ui.export.service.impl.BasicExportOption.ImageType;

/**
 * The Class BasicExportOptionComposite.
 */
public class BasicExportOptionComposite extends
		AbstractExportOptionComposite<BasicExportOption> implements
		SelectionListener, ISelectionChangedListener {

	/** The image type. */
	private ImageType imageType = ImageType.PNG;

	/** The export chart. */
	private boolean exportChart = true;

	/** The csv separator. */
	private char csvSeparator = ';';

	/** The export stats as csv. */
	private boolean exportStatsAsCsv = true;

	/** The zip. */
	private boolean zip = false;

	/** The btn zip. */
	private Button btnZip;

	/** The btn chart. */
	private Button btnChart;

	/** The btn stats. */
	private Button btnStats;

	/** The cb csv separator. */
	private ComboViewer cbCsvSeparator;

	/** The cb image types. */
	private ComboViewer cbImageTypes;

	/** The map. */
	private final Map<String, Character> map = new HashMap<String, Character>();

	/**
	 * Instantiates a new basic export option composite.
	 *
	 * @param parent the parent
	 */
	public BasicExportOptionComposite(Composite parent) {
		super(parent);
		map.put(";", ';');
		map.put(",", ',');
		map.put("<tab>", '\t');

		this.createContent(this);
		GridLayoutFactory.fillDefaults().applyTo(this);

		// Initial Values
		this.initValues();
	}

	/**
	 * Inits the values.
	 */
	private void initValues() {
		for (Entry<String, Character> entry : this.map.entrySet()) {
			if (entry.getValue().equals(this.csvSeparator)) {
				this.cbCsvSeparator.setSelection(new StructuredSelection(entry
						.getKey()));
				break;
			}
		}

		this.cbImageTypes.setSelection(new StructuredSelection(this.imageType));
	}

	/**
	 * Creates the content.
	 *
	 * @param parent the parent
	 */
	protected void createContent(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).margins(2, 2)
				.applyTo(group);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(group);
		group.setText("Options");

		Label lbl;
		// Chart
		this.btnChart = new Button(group, SWT.CHECK);
		this.btnChart.setText("Export Charts");
		this.btnChart.setSelection(this.exportChart);
		this.btnChart.addSelectionListener(this);

		lbl = new Label(group, SWT.WRAP);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).applyTo(lbl);
		lbl.setText("CSV separator");

		this.cbImageTypes = new ComboViewer(group);
		this.cbImageTypes.setContentProvider(new ArrayContentProvider());
		this.cbImageTypes.setLabelProvider(new ColumnLabelProvider());
		this.cbImageTypes.setInput(ImageType.values());
		this.cbImageTypes.addSelectionChangedListener(this);

		// Stats
		this.btnStats = new Button(group, SWT.CHECK);
		this.btnStats.setText("Export Stats");
		this.btnStats.setSelection(this.exportStatsAsCsv);
		this.btnStats.addSelectionListener(this);

		lbl = new Label(group, SWT.WRAP);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).applyTo(lbl);
		lbl.setText("Export Stats. as CSV");

		this.cbCsvSeparator = new ComboViewer(group);
		this.cbCsvSeparator.setContentProvider(new ArrayContentProvider());
		this.cbCsvSeparator.setLabelProvider(new ColumnLabelProvider());
		this.cbCsvSeparator.setInput(this.map.keySet());
		this.cbCsvSeparator.addSelectionChangedListener(this);

		// Zip
		this.btnZip = new Button(group, SWT.CHECK);
		this.btnZip.setText("Zip");
		this.btnZip.setSelection(this.zip);
		this.btnZip.addSelectionListener(this);
//		this.btnZip.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		Object src = e.getSource();
		if (this.btnZip.equals(src)) {
			this.zip = this.btnZip.getSelection();
		} else if (this.btnChart.equals(src)) {
			this.exportChart = this.btnChart.getSelection();
		} else if (this.btnStats.equals(src)) {
			this.exportStatsAsCsv = this.btnStats.getSelection();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		Object src = event.getSource();
		IStructuredSelection selection;
		if (this.cbImageTypes.equals(src)) {
			selection = (IStructuredSelection) this.cbImageTypes.getSelection();
			this.imageType = (ImageType) selection.getFirstElement();
		} else if (this.cbCsvSeparator.equals(src)) {
			selection = (IStructuredSelection) this.cbCsvSeparator
					.getSelection();
			String c = selection.getFirstElement().toString();
			this.csvSeparator = map.get(c);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// Nothing to do
	}

	/* (non-Javadoc)
	 * @see org.opendarts.ui.export.composite.AbstractExportOptionComposite#getExportOptions()
	 */
	@Override
	public BasicExportOption getExportOptions() {
		BasicExportOption option = new BasicExportOption();
		option.setChartImageType(imageType);
		option.setCsvSeparator(this.csvSeparator);
		option.setExportChart(this.exportChart);
		option.setExportStatsAsCsv(this.exportStatsAsCsv);
		option.setZip(this.zip);
		return option;
	}

}
