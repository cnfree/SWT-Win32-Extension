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
package org.sf.feeling.swt.win32.extension.example.page;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.io.FileSystem;
import org.sf.feeling.swt.win32.extension.shell.ShellIcon;

public class IconPage extends SimpleTabPage
{

	private Composite iconsClient;

	private Text fileText;

	public void buildUI(Composite parent)
	{
		super.buildUI(parent);
		TableWrapLayout layout = new TableWrapLayout();
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.topMargin = 15;
		layout.verticalSpacing = 20;
		container.getBody().setLayout(layout);

		createTitle();
		createFileSelectedArea();
		createFileIconsArea();
	}

	private void createFileIconsArea()
	{
		TableWrapData td;
		Section fileInfoSection = WidgetUtil.getToolkit().createSection(container.getBody(),
				Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		fileInfoSection.setLayoutData(td);
		fileInfoSection.setText("File Icons List:");
		WidgetUtil.getToolkit().createCompositeSeparator(fileInfoSection);
		iconsClient = WidgetUtil.getToolkit().createComposite(fileInfoSection);
		GridLayout layout = new GridLayout();
		layout.numColumns = 10;
		iconsClient.setLayout(layout);

		Image[] images = ShellIcon.getSystemIcons(ShellIcon.ICON_LARGE);
		for (int i = 0; i < images.length; i++)
		{
			Label label = WidgetUtil.getToolkit().createLabel(iconsClient, true);
			label.setImage(images[i]);
			label.setData(images[i]);
		}

		fileInfoSection.setClient(iconsClient);
	}

	private void createFileSelectedArea()
	{
		Composite fileSelectedArea = WidgetUtil.getToolkit().createComposite(
				container.getBody());
		GridLayout layout = new GridLayout();
		fileSelectedArea.setLayout(layout);
		layout.numColumns = 3;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		WidgetUtil.createLabel(fileSelectedArea).setText("Select File:");
		fileText = WidgetUtil.getToolkit().createText(fileSelectedArea,
				FileSystem.getSystemDirectory() + "\\shell32.dll");
		GridData gd = new GridData();
		gd.widthHint = 250;
		fileText.setLayoutData(gd);
		Button button = WidgetUtil.getToolkit().createButton(fileSelectedArea, SWT.PUSH, true);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				showFileIcons();
			}
		});
	}

	private void showFileIcons()
	{
		String ext[] = new String[] { "*.dll;*.exe;*.ico" };
		FileDialog fd = new FileDialog(container.getShell(), SWT.OPEN);
		fd.setFilterExtensions(ext);
		String file = fd.open();
		if (file != null)
		{
			fileText.setText(file);
			Control[] children = iconsClient.getChildren();
			for (int i = 0; i < children.length; i++)
			{
				((Image) children[i].getData()).dispose();
				children[i].dispose();
			}
			Image[] images = ShellIcon.getFileIcons(new File(file), ShellIcon.ICON_LARGE);
			for (int i = 0; i < images.length; i++)
			{
				Label label = WidgetUtil.getToolkit().createLabel(iconsClient, true);
				label.setImage(images[i]);
				label.setData(images[i]);
			}
			container.reflow(true);
			iconsClient.layout();
		}
	}

	public String getDisplayName()
	{
		return "Change Icon Example";
	}

	private void createTitle()
	{
		WidgetUtil.createFormText(container.getBody(),
				"This page demonstrates how to extract icons from exe, dll, ico files.");
	}
}
