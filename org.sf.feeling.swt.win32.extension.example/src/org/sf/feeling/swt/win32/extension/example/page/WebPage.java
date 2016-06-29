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

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;

public class WebPage extends TabPage
{

	private Browser browser;

	public void buildUI(Composite parent)
	{
		browser = new Browser(parent, SWT.NONE);
		browser.setUrl("http://feeling.sf.net");
	}

	public Composite getControl()
	{
		return browser;
	}

	public String getDisplayName()
	{
		return "Project Website";
	}

}
