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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.sf.feeling.swt.win32.extension.example.Example;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.hook.Hook;
import org.sf.feeling.swt.win32.extension.hook.data.HookData;
import org.sf.feeling.swt.win32.extension.hook.data.KeyboardHookData;
import org.sf.feeling.swt.win32.extension.hook.data.MouseHookData;
import org.sf.feeling.swt.win32.extension.hook.listener.HookEventListener;
import org.sf.feeling.swt.win32.extension.shell.Windows;

public class HookPage extends SimpleTabPage
{

	public void buildUI( Composite parent )
	{
		super.buildUI( parent );
		TableWrapLayout layout = new TableWrapLayout( );
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.topMargin = 15;
		container.getBody( ).setLayout( layout );

		createTitle( );
		WidgetUtil.createFormText( container.getBody( ) );
		createMouseHookArea( );
		WidgetUtil.createFormText( container.getBody( ) );
		createKeyBoardHookArea( );
	}

	public void deActivate( )
	{
		Windows.setWindowAlwaysOnTop( container.getShell( ).handle, false );

	}

	public void activate( )
	{
		Windows.setWindowAlwaysOnTop( container.getShell( ).handle, true );
	}

	private void createMouseHookArea( )
	{
		Section mouseHookSection = WidgetUtil.getToolkit( )
				.createSection( container.getBody( ), Section.EXPANDED );
		TableWrapData td = new TableWrapData( TableWrapData.FILL );
		mouseHookSection.setLayoutData( td );
		mouseHookSection.setText( "Mouse Hook:" );
		WidgetUtil.getToolkit( ).createCompositeSeparator( mouseHookSection );

		WidgetUtil.createFormText( container.getBody( ),
				"<form><p>This hook allows to intercept <b>all</b> mouse events.<br/>"
						+ "Press the \"Install\" button to install the Mouse Hook "
						+ "and the \"Uninstall\" button to uninstall it.</p></form>",
				true,
				false );

		Composite mouseHookArea = WidgetUtil.getToolkit( )
				.createComposite( container.getBody( ) );
		GridLayout layout = new GridLayout( );
		layout.numColumns = 2;
		layout.marginTop = 0;
		layout.verticalSpacing = 15;
		layout.marginBottom = 0;
		mouseHookArea.setLayout( layout );

		final Label label = WidgetUtil.getToolkit( )
				.createLabel( mouseHookArea,
						"Hook is not installed",
						SWT.CENTER );
		GridData data = new GridData( GridData.FILL_HORIZONTAL );
		data.horizontalSpan = 2;
		data.widthHint = 460;
		label.setLayoutData( data );

		final Button installButton = WidgetUtil.getToolkit( )
				.createButton( mouseHookArea, SWT.PUSH, true );
		installButton.setText( "Install" );
		data = new GridData( GridData.FILL_HORIZONTAL );
		data.horizontalAlignment = SWT.RIGHT;
		data.widthHint = 100;
		data.grabExcessHorizontalSpace = true;
		installButton.setLayoutData( data );

		final Button unInstallButton = WidgetUtil.getToolkit( )
				.createButton( mouseHookArea, SWT.PUSH, true );
		unInstallButton.setEnabled( false );
		unInstallButton.setText( "Uninstall" );
		data = new GridData( GridData.FILL_HORIZONTAL );
		data.horizontalAlignment = SWT.LEFT;
		data.widthHint = 100;
		data.grabExcessHorizontalSpace = true;
		unInstallButton.setLayoutData( data );

		installButton.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( !Hook.MOUSE.isInstalled( HookPage.this ) )
				{
					Hook.MOUSE.install( HookPage.this );
					installButton.setEnabled( false );
					unInstallButton.setEnabled( true );
				}
				label.setText( "Hook is installed successfully" );
			}
		} );

		unInstallButton.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( Hook.MOUSE.isInstalled( HookPage.this ) )
				{
					Hook.MOUSE.uninstall( HookPage.this );
					installButton.setEnabled( true );
					unInstallButton.setEnabled( false );
				}
				label.setText( "Hook is not installed" );
			}
		} );

		Hook.MOUSE.addListener( HookPage.this, new HookEventListener( ) {

			public void acceptHookData( final HookData hookData )
			{
				if ( hookData != null )
				{
					Display.getDefault( ).asyncExec( new Runnable( ) {

						public void run( )
						{
							if ( label.isDisposed( ) || Example.DIRTY )
								return;
							if ( Hook.MOUSE.isInstalled( HookPage.this ) )
								label.setText( "Mouse Coordinates: X = "
										+ ( (MouseHookData) hookData ).getPointX( )
										+ ", Y = "
										+ ( (MouseHookData) hookData ).getPointY( ) );
							else
								label.setText( "Hook is not installed" );
						};
					} );
				}
			}

		} );
	}

	private void createKeyBoardHookArea( )
	{
		Section mouseHookSection = WidgetUtil.getToolkit( )
				.createSection( container.getBody( ), Section.EXPANDED );
		TableWrapData td = new TableWrapData( TableWrapData.FILL );
		mouseHookSection.setLayoutData( td );
		mouseHookSection.setText( "Keyboard Hook:" );
		WidgetUtil.getToolkit( ).createCompositeSeparator( mouseHookSection );

		WidgetUtil.createFormText( container.getBody( ),
				"<form><p>This hook allows to intercept <b>all</b> keyboard events. Try typing some text in other window to see hook in action in Demo window."
						+ "<br/>Press the \"Install\" button to install the Keyboard Hook and the \"Uninstall\" button to uninstall it.</p></form>",
				true,
				false );

		Composite mouseHookArea = WidgetUtil.getToolkit( )
				.createComposite( container.getBody( ) );
		GridLayout layout = new GridLayout( );
		layout.numColumns = 2;
		layout.marginTop = 0;
		layout.verticalSpacing = 15;
		layout.marginBottom = 0;
		mouseHookArea.setLayout( layout );

		final Label label = WidgetUtil.getToolkit( )
				.createLabel( mouseHookArea,
						"Hook is not installed",
						SWT.CENTER );
		GridData data = new GridData( GridData.FILL_HORIZONTAL );
		data.horizontalSpan = 2;
		data.widthHint = 460;
		label.setLayoutData( data );

		final Button installButton = WidgetUtil.getToolkit( )
				.createButton( mouseHookArea, SWT.PUSH, true );
		installButton.setText( "Install" );
		data = new GridData( GridData.FILL_HORIZONTAL );
		data.horizontalAlignment = SWT.RIGHT;
		data.widthHint = 100;
		data.grabExcessHorizontalSpace = true;
		installButton.setLayoutData( data );

		final Button unInstallButton = WidgetUtil.getToolkit( )
				.createButton( mouseHookArea, SWT.PUSH, true );
		unInstallButton.setText( "Uninstall" );
		unInstallButton.setEnabled( false );
		data = new GridData( GridData.FILL_HORIZONTAL );
		data.horizontalAlignment = SWT.LEFT;
		data.widthHint = 100;
		data.grabExcessHorizontalSpace = true;
		unInstallButton.setLayoutData( data );

		installButton.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( !Hook.KEYBOARD.isInstalled( HookPage.this ) )
				{
					Hook.KEYBOARD.install( HookPage.this );
					installButton.setEnabled( false );
					unInstallButton.setEnabled( true );
				}
				label.setText( "Hook is installed successfully" );
			}
		} );

		unInstallButton.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( Hook.KEYBOARD.isInstalled( HookPage.this ) )
				{
					Hook.KEYBOARD.uninstall( HookPage.this );
					installButton.setEnabled( true );
					unInstallButton.setEnabled( false );
				}
				label.setText( "Hook is not installed" );
			}
		} );

		Hook.KEYBOARD.addListener( HookPage.this, new HookEventListener( ) {

			public void acceptHookData( final HookData hookData )
			{
				if ( hookData != null )
				{
					Display.getDefault( ).asyncExec( new Runnable( ) {

						public void run( )
						{
							if ( label.isDisposed( ) || Example.DIRTY )
								return;
							if ( Hook.KEYBOARD.isInstalled( HookPage.this ) )
								label.setText( "Virtual Key Code = "
										+ ( (KeyboardHookData) hookData ).getVirtualKeyCode( )
										+ ", Scan Code = "
										+ ( (KeyboardHookData) hookData ).getScanCode( )
										+ ", Extended Key = "
										+ ( (KeyboardHookData) hookData ).isExtendedKey( ) );
							else
								label.setText( "Hook is not installed" );
						};
					} );
				}
			}

		} );
	}

	private void createTitle( )
	{
		WidgetUtil.createFormText( container.getBody( ),
				"This page demonstrates how to hook Windows event." );
	}

	public String getDisplayName( )
	{
		return "Hooks Example";
	}

}