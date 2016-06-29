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
package org.sf.feeling.swt.win32.extension.example;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.widgets.ShellWrapper;
import org.sf.feeling.swt.win32.internal.extension.util.ImageCache;

public class AboutDialog {
	public AboutDialog(Shell parent) {
		Shell shell = new Shell(parent, SWT.CLOSE|SWT.SYSTEM_MODAL);
		shell.setText("About SWT Win32 Extension");
		shell.setImages(new Image[] { ImageCache.getImage("/eclipse.png"),
				ImageCache.getImage("/eclipse32.png"),
				ImageCache.getImage("/eclipse48.png") });
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		shell.setLayout(layout);

		AboutItem item = scan("SWT Win32 Extension\n\nVersion: 1.1.0\n"
				+ "Build id: M20111208\n\n(c) Copyright cnfree.  "
				+ "All rights reserved.\n\nMore details please visit http://feeling.sf.net  ");

		createDialogArea(shell, item);
		shell.pack();
		shell.setLocation(parent.getLocation().x
				+ (parent.getSize().x - shell.getSize().x) / 2, parent
				.getLocation().y
				+ (parent.getSize().y - shell.getSize().y) / 2);
		if (Example.getInstance().getWrapper().isThemeInstalled()) {
			ShellWrapper wrapper = new ShellWrapper(shell);
			wrapper.installTheme(Example.getInstance().getWrapper().getTheme());
			wrapper.open();
		} else
			shell.open();
	}

	protected Control createDialogArea(Composite parent, AboutItem item) {

		// brand the about box if there is product info
		Image aboutImage = ImageCache.getImage("/eclipse_lg.gif");

		// create a composite which is the parent of the top area and the bottom
		// button bar, this allows there to be a second child of this composite
		// with
		// a banner background on top but not have on the bottom
		Composite workArea = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		workArea.setLayout(layout);
		workArea.setLayoutData(new GridData(GridData.FILL_BOTH));

		// page group
		Color background = JFaceColors.getBannerBackground(parent.getDisplay());
		Color foreground = JFaceColors.getBannerForeground(parent.getDisplay());

		// the image & text
		Composite topContainer = new Composite(workArea, SWT.NONE);
		topContainer.setBackground(background);
		topContainer.setForeground(foreground);

		layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		topContainer.setLayout(layout);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		topContainer.setLayoutData(data);

		// image on left side of dialog
		if (aboutImage != null) {
			Label imageLabel = new Label(topContainer, SWT.NONE);
			imageLabel.setBackground(background);
			imageLabel.setForeground(foreground);

			data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.BEGINNING;
			data.grabExcessHorizontalSpace = false;
			imageLabel.setLayoutData(data);
			imageLabel.setImage(aboutImage);
		}

		// there is no margins around the image, so insert an extra composite
		// here to provide some margins for the text.
		Composite textContainer = new Composite(topContainer, SWT.NONE);
		textContainer.setBackground(background);
		textContainer.setForeground(foreground);

		layout = new GridLayout();
		layout.numColumns = 1;
		textContainer.setLayout(layout);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		data.grabExcessHorizontalSpace = true;
		textContainer.setLayoutData(data);

		// text on the right
		StyledText text = new StyledText(textContainer, SWT.MULTI
				| SWT.READ_ONLY);
		text.setCaret(null);
		text.setFont(parent.getFont());
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		data.grabExcessHorizontalSpace = true;
		text.setText(item.getText());
		text.setLayoutData(data);
		text.setCursor(null);
		text.setBackground(background);
		text.setForeground(foreground);

		setLinkRanges(text, item.getLinkRanges());
		// addListeners(text);

		// horizontal bar
		Label bar = new Label(workArea, SWT.HORIZONTAL | SWT.SEPARATOR);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		bar.setLayoutData(data);

		// add image buttons for bundle groups that have them
		Composite bottom = new Composite(parent, SWT.NONE);
		layout = new GridLayout();
		layout.marginWidth += 5;
		bottom.setLayout(layout);
		bottom.setLayoutData(new GridData(GridData.FILL_BOTH));

		Button button = new Button(bottom, SWT.PUSH);
		button.setText("OK");
		GridData gd = new GridData();
		gd.horizontalAlignment = SWT.END;
		gd.widthHint = 60;
		gd.horizontalIndent = 20;
		gd.grabExcessHorizontalSpace = true;
		button.setLayoutData(gd);
		button.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				((Button) e.widget).getShell().close();
			}

		});
		button.setFocus();

		return workArea;
	}

	protected AboutItem scan(String s) {
		ArrayList linkRanges = new ArrayList();
		ArrayList links = new ArrayList();

		// slightly modified version of jface url detection
		// see org.eclipse.jface.text.hyperlink.URLHyperlinkDetector

		int urlSeparatorOffset = s.indexOf("://"); //$NON-NLS-1$
		while (urlSeparatorOffset >= 0) {

			boolean startDoubleQuote = false;

			// URL protocol (left to "://")
			int urlOffset = urlSeparatorOffset;
			char ch;
			do {
				urlOffset--;
				ch = ' ';
				if (urlOffset > -1)
					ch = s.charAt(urlOffset);
				startDoubleQuote = ch == '"';
			} while (Character.isUnicodeIdentifierStart(ch));
			urlOffset++;

			// Right to "://"
			StringTokenizer tokenizer = new StringTokenizer(s
					.substring(urlSeparatorOffset + 3), " \t\n\r\f<>", false); //$NON-NLS-1$
			if (!tokenizer.hasMoreTokens())
				return null;

			int urlLength = tokenizer.nextToken().length() + 3
					+ urlSeparatorOffset - urlOffset;

			if (startDoubleQuote) {
				int endOffset = -1;
				int nextDoubleQuote = s.indexOf('"', urlOffset);
				int nextWhitespace = s.indexOf(' ', urlOffset);
				if (nextDoubleQuote != -1 && nextWhitespace != -1)
					endOffset = Math.min(nextDoubleQuote, nextWhitespace);
				else if (nextDoubleQuote != -1)
					endOffset = nextDoubleQuote;
				else if (nextWhitespace != -1)
					endOffset = nextWhitespace;
				if (endOffset != -1)
					urlLength = endOffset - urlOffset;
			}

			linkRanges.add(new int[] { urlOffset, urlLength });
			links.add(s.substring(urlOffset, urlOffset + urlLength));

			urlSeparatorOffset = s.indexOf("://", urlOffset + urlLength + 1); //$NON-NLS-1$
		}
		return new AboutItem(s, (int[][]) linkRanges.toArray(new int[linkRanges
				.size()][2]), (String[]) links
				.toArray(new String[links.size()]));
	}

	protected void setLinkRanges(StyledText styledText, int[][] linkRanges) {
		Color fg = Display.getDefault().getSystemColor(SWT.COLOR_DARK_BLUE);
		for (int i = 0; i < linkRanges.length; i++) {
			StyleRange r = new StyleRange(linkRanges[i][0], linkRanges[i][1],
					fg, null);
			styledText.setStyleRange(r);
		}
	}
}
