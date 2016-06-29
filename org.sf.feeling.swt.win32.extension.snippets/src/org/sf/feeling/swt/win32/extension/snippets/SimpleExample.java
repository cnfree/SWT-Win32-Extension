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
package org.sf.feeling.swt.win32.extension.snippets;

import java.net.InetAddress;
import java.text.NumberFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.registry.RegistryKey;
import org.sf.feeling.swt.win32.extension.registry.RootKey;
import org.sf.feeling.swt.win32.extension.shell.ShellIcon;
import org.sf.feeling.swt.win32.extension.shell.Windows;
import org.sf.feeling.swt.win32.extension.system.SystemInfo;
import org.sf.feeling.swt.win32.internal.extension.DISKFREESPACE;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.MEMORYSTATUS;
import org.sf.feeling.swt.win32.internal.extension.SYSTEMINFO;
import org.sf.feeling.swt.win32.internal.extension.util.ImageCache;

public class SimpleExample {

	private Shell shell;

	private ScrolledComposite composite;

	private Composite container;

	private Label cpuLabel;

	private FormToolkit toolkit = new FormToolkit(Display.getDefault());

	public SimpleExample() {
		shell = new Shell(Display.getDefault());

		shell.setLayout(new GridLayout());
		Image smallImage = ImageCache.getImage("/eclipse.png");
		Image largeImage = ImageCache.getImage("/eclipse32.png");
		shell.setImages(new Image[] { smallImage, largeImage });
		shell.setText("SWT Win32 Extension Example");

		createLabel().setText("System ICON:");

		composite = new ScrolledComposite(shell, SWT.V_SCROLL | SWT.H_SCROLL);
		composite.setExpandHorizontal(true);
		composite.setExpandVertical(true);
		container = new Composite(composite, SWT.NONE);
		composite.setContent(container);

		GridLayout layout = new GridLayout();
		layout.numColumns = 20;
		container.setLayout(layout);

		Image[] images = ShellIcon.getSystemIcons(ShellIcon.ICON_LARGE);
		for (int i = 0; i < 20; i++) {
			Label label = toolkit.createLabel(container, "");
			label.setImage(images[i]);
		}

		composite.addControlListener(new ControlAdapter() {

			public void controlResized(ControlEvent e) {
				composite.setMinSize(container.computeSize(SWT.DEFAULT,
						SWT.DEFAULT));
				container.layout();
			}
		});

		toolkit.paintBordersFor(container);
		toolkit.adapt(container);

		String info = "Drive	Type		Space		Available";
		createLabel().setText(info);
		String[] drives = Extension.GetLogicalDrives();
		for (int i = 0; i < drives.length; i++) {
			info = drives[i];
			int diskType = Extension.GetDriveType(drives[i]);
			switch (diskType) {
			case Win32.DRIVE_TYPE_UNKNOW:
				info += "	UNKNOW	";
				break;
			case Win32.DRIVE_TYPE_REMOVABLE:
				info += "	REMOVABLE";
				break;
			case Win32.DRIVE_TYPE_REMOTE:
				info += "	REMOTE Disk";
				break;
			case Win32.DRIVE_TYPE_RAMDISK:
				info += "	RAM DISK";
				break;
			case Win32.DRIVE_TYPE_NOT_EXIST:
				info += "	NOT EXIST";
				break;
			case Win32.DRIVE_TYPE_FIXED:
				info += "	Local Disk";
				break;
			case Win32.DRIVE_TYPE_CDROM:
				info += "	CD Drive";
				break;
			}
			DISKFREESPACE space = Extension.GetDiskFreeSpace(drives[i]);
			String totleSpace = format(space.totalNumberOfBytes);
			info += "	" + totleSpace;
			if (totleSpace.length() < 8)
				info += "		" + format(space.totalNumberOfFreeBytes);
			else
				info += "	" + format(space.totalNumberOfFreeBytes);
			Label label = createLabel();
			label.setText(info);
		}

		createLabel();

		SYSTEMINFO systemInfo = Extension.GetSystemInfo();

		Label label = createLabel();
		label.setText("Processor Type:		" + systemInfo.dwProcessorType);

		label = createLabel();
		label.setText("Processor Number:	" + systemInfo.dwNumberOfProcessors);

		label = createLabel();
		label.setText("Processor PageSize:	" + format(systemInfo.dwPageSize));

		cpuLabel = createLabel();

		Thread thread = new Thread() {
			public void run() {
				while (true) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							if (!cpuLabel.isDisposed())
								cpuLabel.setText("Processor Usages:	"
										+ Extension.GetCpuUsages() + " %");
						}
					});
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.setDaemon(true);
		thread.start();

		createLabel();

		MEMORYSTATUS memoryStatus = Extension.GlobalMemoryStatus();

		label = createLabel();
		label.setText("Memory Total:		" + memoryStatus.ullTotalPhys / (1024)
				+ " KB");

		label = createLabel();
		label.setText("Memory Available:	" + memoryStatus.ullTotalPhys / (1024)
				+ " KB");

		createLabel();

		RegistryKey key = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE,
				"SOFTWARE\\Microsoft\\Internet Explorer");
		if (key.exists() && key.hasValue("Version")) {
			label = createLabel();
			label.setText("Use registry key to get IE version:	"
					+ key.getValue("Version").getData());
		}

		createLabel();

		label = createLabel();
		label.setText("Windows Directory:	" + Extension.GetWindowsDirectory());

		label = createLabel();
		label.setText("System Directory:	" + Extension.GetSystemDirectory());

		label = createLabel();
		label.setText("System Temp Path:	"
				+ Extension.GetLongPathName(Extension.GetTempPath()));

		createLabel();

		label = createLabel();
		label.setText("Tray Bar's height:	"
				+ Windows.getWindowRect(Windows.getSystemTray()).height);

		createLabel();

		for (int i = 0; i < SystemInfo.getMACAddresses().length; i++) {
			label = createLabel();
			label.setText("MAC[" + i + "] Addresses:	"
					+ SystemInfo.getMACAddresses()[i]);
		}

		createLabel();
		try {
			InetAddress addr = InetAddress.getByName("www.google.cn");
			label = createLabel();
			label.setText("Pinging www.google.cn [" + addr.getHostAddress()
					+ "] with 32 bytes of data:");
			for (int i = 0; i < 4; i++) {
				createLabel().setText(
						"Reply from " + addr.getHostAddress()
								+ ": bytes=32 time="
								+ Extension.Ping("www.google.cn", 32) + "ms");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		container.layout();
		shell.layout();

		// Window.SetWindowTransparent(shell.handle, (byte)200);

		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent arg0) {
				Windows.hideWindowBlend(shell.handle, 1000);
			}

		});

		shell.setBackground(Display.getDefault()
				.getSystemColor(SWT.COLOR_WHITE));
		toolkit.paintBordersFor(shell);

		shell.open();
		while (!shell.isDisposed()) {
			if (!Display.getDefault().readAndDispatch())
				Display.getDefault().sleep();
		}
		Display.getDefault().dispose();
	}

	private Label createLabel() {
		Label label = toolkit.createLabel(shell, "");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return label;
	}

	private String format(float totalNumberOfFreeBytes) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(1);
		format.setMinimumFractionDigits(0);
		if (totalNumberOfFreeBytes < 1024)
			return format.format(totalNumberOfFreeBytes) + " byte";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if (totalNumberOfFreeBytes < 1024)
			return format.format(totalNumberOfFreeBytes) + " KB";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if (totalNumberOfFreeBytes < 1024)
			return format.format(totalNumberOfFreeBytes) + " MB";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if (totalNumberOfFreeBytes < 1024)
			return format.format(totalNumberOfFreeBytes) + " GB";
		return null;
	}

	public static void main(String[] args) {
		new SimpleExample();
	}
}
