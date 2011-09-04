package org.opendarts.ui.stats.view;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.opendarts.ui.stats.model.IChart;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class ChartDetailsPage.
 */
public class ChartDetailsPage implements IDetailsPage {

	/** The toolkit. */
	private OpenDartsFormsToolkit toolkit;

	/** The client. */
	private Composite client;

	/** The section. */
	private Section section;

	private IChart chart;

	/**
	 * Instantiates a new session details page.
	 */
	public ChartDetailsPage() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#initialize(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	public void initialize(IManagedForm form) {
		this.toolkit = (OpenDartsFormsToolkit) form.getToolkit();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createContents(Composite parent) {
		GridLayoutFactory.fillDefaults().margins(5, 5).applyTo(parent);
		// Section
		this.section = this.toolkit.createSection(parent,
				ExpandableComposite.TITLE_BAR);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.section);
		this.section.setText("Chart");

		// Section body
		this.client = this.toolkit.createComposite(this.section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(client);

		// End section definition
		this.toolkit.paintBordersFor(client);
		this.section.setClient(client);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse.ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		this.chart = (IChart) ((IStructuredSelection) selection)
				.getFirstElement();
		for (Control ctrl : this.client.getChildren()) {
			ctrl.dispose();
		}
		if (this.chart != null) {
			this.section.setText(this.chart.getName());
			ChartComposite frame = new ChartComposite(this.client, SWT.NONE,
					chart.getChart(), true);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(frame);
			this.client.layout(true, true);
		} else {
			this.section.setText("No Chart");
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#setFormInput(java.lang.Object)
	 */
	@Override
	public boolean setFormInput(Object input) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#refresh()
	 */
	@Override
	public void refresh() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#isStale()
	 */
	@Override
	public boolean isStale() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#commit(boolean)
	 */
	@Override
	public void commit(boolean onSave) {
		// Nothing to do
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.forms.IFormPart#dispose()
	 */
	@Override
	public void dispose() {
		// Nothing to dispose
	}

}
