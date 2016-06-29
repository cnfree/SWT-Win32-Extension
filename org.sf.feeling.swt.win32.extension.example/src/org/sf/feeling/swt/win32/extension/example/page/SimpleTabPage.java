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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;

public class SimpleTabPage extends TabPage {

	protected ScrolledForm container;

	public void buildUI(Composite parent) {
		container = WidgetUtil.getToolkit().createScrolledForm(parent);
	}

	public Composite getControl() {
		return container;
	}

	public String getDisplayName() {
		return null;
	}

}
