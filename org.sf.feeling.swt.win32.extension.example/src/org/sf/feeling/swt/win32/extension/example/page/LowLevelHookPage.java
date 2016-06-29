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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.hook.JournalPlaybackHook;
import org.sf.feeling.swt.win32.extension.hook.JournalRecordHook;
import org.sf.feeling.swt.win32.extension.hook.data.JournalHookData;
import org.sf.feeling.swt.win32.extension.hook.interceptor.InterceptorFlag;
import org.sf.feeling.swt.win32.extension.hook.interceptor.JournalHookInterceptor;
import org.sf.feeling.swt.win32.extension.system.WindowsSession;

public class LowLevelHookPage extends SimpleTabPage
{

	private Text fileText;

	private Button recordButton;

	private Button playBackButton;

	private int startTime;

	private ObjectOutputStream oos;

	private ObjectInputStream ois;

	private boolean moveNext = true;

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
		createFileChooseArea();
		createRunButtonArea();

		initHook();
	}

	private void initHook()
	{
		JournalRecordHook.addHookInterceptor(new JournalHookInterceptor()
		{

			public InterceptorFlag intercept(JournalHookData hookData)
			{

				if (hookData.getNCode() == Win32.HC_ACTION)
				{
					hookData.getStruct().setTime(hookData.getTime() - startTime);
					try
					{
						oos.writeObject(hookData);
						oos.flush();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
					return new InterceptorFlag(0);
				}
				return InterceptorFlag.TRUE;
			}
		});

		JournalPlaybackHook.addHookInterceptor(new JournalHookInterceptor()
		{

			private JournalHookData fileHookData;

			public InterceptorFlag intercept(JournalHookData hookData)
			{
				switch (hookData.getNCode())
				{
				case Win32.HC_GETNEXT:
					if (moveNext)
					{
						fileHookData = null;
						try
						{
							fileHookData = (JournalHookData) ois.readObject();
						} catch (Exception e)
						{
							e.printStackTrace();
						}
						if (fileHookData == null)
						{
							moveNext = true;
							JournalPlaybackHook.unInstallHook();
							playBackButton.setEnabled(true);
							recordButton.setEnabled(true);
							container.getShell().forceActive();
							return new InterceptorFlag(0);
						}
						moveNext = false;
					}

					hookData.getStruct().setHwnd(fileHookData.getHwnd());
					hookData.getStruct().setMessage(fileHookData.getMessage());
					hookData.getStruct().setParamH(fileHookData.getParamH());
					hookData.getStruct().setParamL(fileHookData.getParamL());
					hookData.getStruct().setTime(startTime + fileHookData.getTime());
					hookData.getStruct().saveToPoint(hookData.getLParam());
					int delta = hookData.getTime() - WindowsSession.getTickCount();
					if (delta > 0)
					{
						return new InterceptorFlag(delta);
					}
					else
					{
						return new InterceptorFlag(0);
					}

				case Win32.HC_SKIP:
					moveNext = true;
					break;
				default:
					return InterceptorFlag.TRUE;
				}
				return new InterceptorFlag(0);
			}
		});
	}

	private void createRunButtonArea()
	{
		Composite runButtonArea = WidgetUtil.getToolkit().createComposite(container.getBody());
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		runButtonArea.setLayoutData(td);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		runButtonArea.setLayout(layout);

		recordButton = WidgetUtil.getToolkit().createButton(runButtonArea, SWT.PUSH, true);
		recordButton.setText("Record");

		recordButton.addSelectionListener(new SelectionAdapter()
		{

			public void widgetSelected(SelectionEvent e)
			{
				if (JournalRecordHook.isInstalled())
				{
					JournalRecordHook.unInstallHook();
					recordButton.setText("Record");
					playBackButton.setEnabled(true);
					try
					{
						oos.writeObject(null);
						oos.close();
					} catch (Exception e1)
					{
						e1.printStackTrace();
					}
				}
				else
				{
					File file = new File(fileText.getText());
					if (!(file.exists() && file.canWrite() && file.isFile()))
					{
						MessageBox message = new MessageBox(container.getShell(),
								SWT.ICON_ERROR);
						message.setMessage("Please enter a valid filename to record.");
						message.setText("Error");
						message.open();
						return;
					}
					try
					{
						oos = new ObjectOutputStream(new FileOutputStream(file));
					} catch (Exception e1)
					{
						e1.printStackTrace();
					}
					recordButton.setText("Stop Record");
					playBackButton.setEnabled(false);
					startTime = WindowsSession.getTickCount();
					JournalRecordHook.installHook();
				}
			}

		});

		playBackButton = WidgetUtil.getToolkit().createButton(runButtonArea, SWT.PUSH, true);
		playBackButton.setText("Playback");

		playBackButton.addSelectionListener(new SelectionAdapter()
		{

			public void widgetSelected(SelectionEvent e)
			{
				File file = new File(fileText.getText());
				if (!(file.exists() && file.canRead() && file.isFile()))
				{
					MessageBox message = new MessageBox(container.getShell(), SWT.ICON_ERROR);
					message.setMessage("Please enter a valid filename to playback.");
					message.setText("Error");
					message.open();
					return;
				}
				try
				{
					ois = new ObjectInputStream(new FileInputStream(file));
				} catch (Exception e1)
				{
					MessageBox message = new MessageBox(container.getShell(), SWT.ICON_ERROR);
					message.setMessage("Please select a valid format file to playback.");
					message.setText("Error");
					message.open();
					return;
				}
				playBackButton.setEnabled(false);
				recordButton.setEnabled(false);
				startTime = WindowsSession.getTickCount();
				JournalPlaybackHook.installHook();
			}

		});

		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.END;
		gd.widthHint = 100;
		recordButton.setLayoutData(gd);

		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.BEGINNING;
		gd.widthHint = 100;
		playBackButton.setLayoutData(gd);

	}

	private void createFileChooseArea()
	{
		TableWrapData td;
		Section fileChooseSection = WidgetUtil.getToolkit().createSection(container.getBody(),
				Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		fileChooseSection.setLayoutData(td);
		fileChooseSection.setText("A simple Recorder/Player:");
		WidgetUtil.getToolkit().createCompositeSeparator(fileChooseSection);
		Composite fileChooseClient = WidgetUtil.getToolkit()
				.createComposite(fileChooseSection);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginTop = 10;
		fileChooseClient.setLayout(layout);

		WidgetUtil.getToolkit().createLabel(fileChooseClient, "Select File:");
		fileText = WidgetUtil.getToolkit().createText(fileChooseClient, "");
		GridData gd = new GridData();
		gd.widthHint = 300;
		fileText.setLayoutData(gd);
		Button button = WidgetUtil.getToolkit().createButton(fileChooseClient, SWT.PUSH, true);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog dialog = new FileDialog(container.getShell(), SWT.OPEN);
				String file = dialog.open();
				if (file != null)
				{
					fileText.setText(file);
				}
			}
		});

		fileChooseSection.setClient(fileChooseClient);
	}

	private void createTitle()
	{
		WidgetUtil
				.createFormText(container.getBody(),
						"This page demonstrates a simple Recorder/Player by using SWT Win32 Extension low level hooks.");
	}

	public String getDisplayName()
	{
		return "Low Level Hooks Example";
	}
}
