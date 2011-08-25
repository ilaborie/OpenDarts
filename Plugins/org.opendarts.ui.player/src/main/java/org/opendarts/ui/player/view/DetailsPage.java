package org.opendarts.ui.player.view;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.opendarts.ui.utils.OpenDartsFormsToolkit;

/**
 * The Class SessionDetailsPage.
 */
public abstract class DetailsPage<E> implements IDetailsPage {

	private OpenDartsFormsToolkit toolkit;
	private Section section;
	private Section sectionDetail;

	/**
	 * Instantiates a new session details page.
	 */
	public DetailsPage() {
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
		this.section.setText("Session");

		// Section body
		Composite client = this.toolkit.createComposite(this.section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(client);

		// Detail
		this.createDetail(client);

		// End section definition
		this.toolkit.paintBordersFor(client);
		this.section.setClient(client);
	}

	/**
	 * Gets the section.
	 *
	 * @return the section
	 */
	protected Section getSection() {
		return this.section;
	}

	/**
	 * Gets the toolkit.
	 *
	 * @return the toolkit
	 */
	public OpenDartsFormsToolkit getToolkit() {
		return this.toolkit;
	}

	/**
	 * Creates the status.
	 *
	 * @param client the client
	 */
	protected void createDetail(Composite parent) {
		this.sectionDetail = this.toolkit.createSection(parent,
				Section.DESCRIPTION);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(section);
		section.setText("Detail");
		section.marginWidth = 10;

		this.toolkit.createCompositeSeparator(section);

		Composite client = this.toolkit.createComposite(section, SWT.WRAP);
		GridLayoutFactory.fillDefaults().applyTo(client);

		this.createInternalDetail(client);

		this.toolkit.paintBordersFor(client);
		section.setClient(client);
	}

	/**
	 * Creates the internal detail.
	 *
	 * @param parent the client
	 */
	protected abstract void createInternalDetail(Composite parent);

	/**
	 * Gets the section detail.
	 *
	 * @return the section detail
	 */
	public Section getSectionDetail() {
		return this.sectionDetail;
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
