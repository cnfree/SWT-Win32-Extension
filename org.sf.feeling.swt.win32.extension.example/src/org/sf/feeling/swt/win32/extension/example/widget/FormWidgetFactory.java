/*******************************************************************************
 * Copyright (c) 2007 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/
package org.sf.feeling.swt.win32.extension.example.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * A FormToolkit customized for use by tabbed property sheet page.
 */
public class FormWidgetFactory extends FormToolkit
{

	/**
	 * private constructor.
	 */
	private FormWidgetFactory()
	{
		super(Display.getCurrent());
	}

	private static FormWidgetFactory factory;

	public static FormWidgetFactory getInstance()
	{
		if (factory == null)
		{
			factory = new FormWidgetFactory();
			factory.setBorderStyle(SWT.NULL);
		}
		return factory;
	}

	/**
	 * Creates the tab folder as a part of the form.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param style
	 *            the tab folder style.
	 * @return the tab folder
	 */
	public CTabFolder createTabFolder(Composite parent, int style)
	{
		CTabFolder tabFolder = new CTabFolder(parent, style);
		return tabFolder;
	}

	/**
	 * Creates the tab item as a part of the tab folder.
	 * 
	 * @param tabFolder
	 *            the parent.
	 * @param style
	 *            the tab folder style.
	 * @return the tab item.
	 */
	public CTabItem createTabItem(CTabFolder tabFolder, int style)
	{
		CTabItem tabItem = new CTabItem(tabFolder, style);
		return tabItem;
	}

	/**
	 * Creates the list as a part of the form.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param style
	 *            the list style.
	 * @return the list.
	 */
	public List createList(Composite parent, int style)
	{
		List list = new org.eclipse.swt.widgets.List(parent, style);
		return list;
	}

	public Composite createComposite(Composite parent, int style)
	{
		Composite c = super.createComposite(parent, style);
		paintBordersFor(c);
		return c;
	}

	public Composite createComposite(Composite parent)
	{
		Composite c = createComposite(parent, SWT.NONE);
		return c;
	}

	public Button createButton(Composite parent, int style, boolean isFormStyle)
	{
		Button b;
		if (isFormStyle) b = super.createButton(parent, "", style);
		else
			b = new Button(parent, style);
		return b;
	}

	public Label createLabel(Composite parent, int style, boolean isFormStyle)
	{
		Label l;
		if (isFormStyle) l = super.createLabel(parent, "", style);
		else
			l = new Label(parent, style);
		return l;
	}

	public Label createLabel(Composite parent, boolean isFormStyle)
	{
		Label l;
		if (isFormStyle) l = super.createLabel(parent, "", SWT.NONE);
		else
			l = new Label(parent, SWT.NONE);
		return l;
	}

	/**
	 * Creates a plain composite as a part of the form.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param style
	 *            the composite style.
	 * @return the composite.
	 */
	public Composite createPlainComposite(Composite parent, int style)
	{
		Composite c = super.createComposite(parent, style);
		c.setBackground(parent.getBackground());
		paintBordersFor(c);
		return c;
	}

	/**
	 * Creates a scrolled composite as a part of the form.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param style
	 *            the composite style.
	 * @return the composite.
	 */
	public ScrolledComposite createScrolledComposite(Composite parent, int style)
	{
		ScrolledComposite scrolledComposite = new ScrolledComposite(parent, style);
		return scrolledComposite;
	}

	public Spinner createSpinner(Composite parent)
	{
		Spinner spinner = new Spinner(parent, SWT.NONE);
		adapt(spinner, true, false);
		return spinner;
	}

	public Scale createScale(Composite parent, int style)
	{
		Scale scale = new Scale(parent, style);
		adapt(scale, false, false);
		return scale;
	}

	/**
	 * Creates a combo box as a part of the form.
	 * 
	 * @param parent
	 *            the combo box parent.
	 * @param comboStyle
	 *            the combo box style.
	 * @return the combo box.
	 */
	public CCombo createCCombo(Composite parent, int comboStyle)
	{
		CCombo combo = new CCombo(parent, comboStyle);
		adapt(combo, true, false);
		return combo;
	}

	/**
	 * Creates a combo box as a part of the form.
	 * 
	 * @param parent
	 *            the combo box parent.
	 * @return the combo box.
	 */
	public CCombo createCCombo(Composite parent)
	{
		return createCCombo(parent, SWT.FLAT | SWT.READ_ONLY);
	}

	public CCombo createCCombo(Composite parent, boolean isReadOnly)
	{
		if (isReadOnly) return createCCombo(parent, SWT.FLAT | SWT.READ_ONLY);
		else
			return createCCombo(parent, SWT.FLAT);
	}

	/**
	 * Creates a group as a part of the form.
	 * 
	 * @param parent
	 *            the group parent.
	 * @param text
	 *            the group title.
	 * @return the composite.
	 */
	public Group createGroup(Composite parent, String text)
	{
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(text);
		group.setBackground(getColors().getBackground());
		group.setForeground(getColors().getForeground());
		return group;
	}

	/**
	 * Creates a flat form composite as a part of the form.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @return the composite.
	 */
	public Composite createFlatFormComposite(Composite parent)
	{
		Composite composite = createComposite(parent);
		FormLayout layout = new FormLayout();
		layout.marginWidth = ITabbedPropertyConstants.HSPACE + 2;
		layout.marginHeight = ITabbedPropertyConstants.VSPACE;
		layout.spacing = ITabbedPropertyConstants.VMARGIN + 1;
		composite.setLayout(layout);
		return composite;
	}

	/**
	 * Creates a label as a part of the form.
	 * 
	 * @param parent
	 *            the label parent.
	 * @param text
	 *            the label text.
	 * @return the label.
	 */
	public CLabel createCLabel(Composite parent, String text)
	{
		return createCLabel(parent, text, SWT.NONE);
	}

	/**
	 * Creates a label as a part of the form.
	 * 
	 * @param parent
	 *            the label parent.
	 * @param text
	 *            the label text.
	 * @param style
	 *            the label style.
	 * @return the label.
	 */
	public CLabel createCLabel(Composite parent, String text, int style)
	{
		final CLabel label = new CLabel(parent, style);
		label.setBackground(parent.getBackground());
		label.setText(text);
		return label;
	}

	public CLabel createCLabel(Composite parent, int style)
	{
		final CLabel label = new CLabel(parent, style);
		label.setBackground(parent.getBackground());
		return label;
	}

	public void dispose()
	{
		if (getColors() != null)
		{
			super.dispose();
		}
	}

	public void paintFormStyle(Composite composite)
	{
		if (composite instanceof TabbedPropertyTitle) return;
		Control[] children = composite.getChildren();
		for (int i = 0; i < children.length; i++)
		{
			if (children[i] instanceof Composite)
			{
				paintFormStyle((Composite) children[i]);
			}
		}
		FormWidgetFactory.getInstance().paintBordersFor(composite);
		adapt(composite);
	}

	private class BorderPainter implements PaintListener
	{

		public void paintControl(PaintEvent event)
		{
			Composite composite = (Composite) event.widget;
			Control[] children = composite.getChildren();
			for (int i = 0; i < children.length; i++)
			{
				Control c = children[i];
				boolean inactiveBorder = false;
				boolean textBorder = false;
				if (!c.isVisible()) continue;
				/*
				 * if (c.getEnabled() == false && !(c instanceof CCombo))
				 * continue;
				 */
				if (c instanceof Hyperlink) continue;
				Object flag = c.getData(KEY_DRAW_BORDER);
				if (flag != null)
				{
					if (flag.equals(Boolean.FALSE)) continue;
					if (flag.equals(TREE_BORDER)) inactiveBorder = true;
					else if (flag.equals(TEXT_BORDER)) textBorder = true;
				}
				if (getBorderStyle() == SWT.BORDER)
				{
					if (!inactiveBorder && !textBorder)
					{
						continue;
					}
					if (c instanceof Text || c instanceof Table || c instanceof Tree) continue;
				}
				if (!inactiveBorder
						&& (c instanceof Text || c instanceof CCombo || textBorder || c instanceof Spinner))
				{
					Rectangle b = c.getBounds();
					GC gc = event.gc;
					gc.setForeground(c.getBackground());
					gc.drawRectangle(b.x - 1, b.y - 1, b.width + 1, b.height + 1);
					// gc.setForeground(getBorderStyle() == SWT.BORDER ? colors
					// .getBorderColor() : colors.getForeground());
					gc.setForeground(getColors().getBorderColor());
					if (c instanceof CCombo || c instanceof Spinner) gc.drawRectangle(b.x - 2,
							b.y - 1, b.width + 2, b.height + 1);
					else
						gc.drawRectangle(b.x - 2, b.y - 2, b.width + 2, b.height + 3);
				}
				else if (inactiveBorder || c instanceof Table || c instanceof Tree
						|| c instanceof List)
				{
					Rectangle b = c.getBounds();
					GC gc = event.gc;
					gc.setForeground(getColors().getBorderColor());
					gc.drawRectangle(b.x - 1, b.y - 1, b.width + 1, b.height + 1);
				}
			}
		}
	}

	private BorderPainter borderPainter;

	public void paintBordersFor(Composite parent)
	{
		// if (borderStyle == SWT.BORDER)
		// return;
		if (borderPainter == null) borderPainter = new BorderPainter();
		parent.addPaintListener(borderPainter);
	}
}
