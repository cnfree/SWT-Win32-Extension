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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.shell.ShellFolder;
import org.sf.feeling.swt.win32.extension.shell.ShellLink;

public class ShellLinkPage extends SimpleTabPage
{

	public void buildUI( Composite parent )
	{
		super.buildUI( parent );
		TableWrapLayout layout = new TableWrapLayout( );
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.topMargin = 0;
		container.getBody( ).setLayout( layout );

		createTitle( );
		WidgetUtil.createFormText( container.getBody( ) );
		createCreateLinkArea( );
		WidgetUtil.createFormText( container.getBody( ) );
		createResolveLinkArea( );
	}

	private void createResolveLinkArea( )
	{
		Section resolveLinkSection = WidgetUtil.getToolkit( )
				.createSection( container.getBody( ), Section.EXPANDED );
		TableWrapData td = new TableWrapData( TableWrapData.FILL );
		resolveLinkSection.setLayoutData( td );
		resolveLinkSection.setText( "Resolve Link:" );
		WidgetUtil.getToolkit( ).createCompositeSeparator( resolveLinkSection );

		WidgetUtil.createFormText( container.getBody( ),
				"Also, you can select a link file and press \"Resolve link\" button to see where an original file is located." );
		Composite fileSelectedArea = WidgetUtil.getToolkit( )
				.createComposite( container.getBody( ) );
		GridLayout layout = new GridLayout( );
		fileSelectedArea.setLayout( layout );
		layout.numColumns = 4;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		WidgetUtil.createLabel( fileSelectedArea, "Select File:" );
		final Text fileText = WidgetUtil.getToolkit( )
				.createText( fileSelectedArea, "" );
		GridData gd = new GridData( );
		gd.widthHint = 220;
		fileText.setLayoutData( gd );
		Button button = WidgetUtil.getToolkit( )
				.createButton( fileSelectedArea, SWT.PUSH, true );
		button.setText( "Browse..." );
		button.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				FileDialog dialog = new FileDialog( container.getShell( ),
						SWT.OPEN );
				dialog.setFilterExtensions( new String[]{
					"*.lnk"
				} );
				String file = dialog.open( );
				if ( file != null )
				{
					fileText.setText( file );
				}
			}
		} );
		final Button resolveButton = WidgetUtil.getToolkit( )
				.createButton( fileSelectedArea, SWT.PUSH, true );
		resolveButton.setText( "Resolve Link" );
		resolveButton.setEnabled( false );

		final FormText messageLabel = WidgetUtil.createFormText( container.getBody( ) );
		final FormText targetLabel = WidgetUtil.createFormText( container.getBody( ) );
		final FormText argumentsLabel = WidgetUtil.createFormText( container.getBody( ) );

		resolveButton.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				String file = ShellLink.getShortCutTarget( fileText.getText( )
						.trim( ) );
				String arguments = ShellLink.getShortCutArguments( fileText.getText( )
						.trim( ) );
				if ( file != null && file.length( ) > 0 )
				{
					messageLabel.setText( "Resolve link successful.",
							false,
							false );
					targetLabel.setText( "Link target: " + file, false, false );
					if ( arguments != null && arguments.trim( ).length( ) > 0 )
						argumentsLabel.setText( "Link arguments: " + arguments,
								false,
								false );
					else
						argumentsLabel.setText( "", false, false );
				}
				else
				{
					messageLabel.setText( "Resolve link failed.", false, false );
					targetLabel.setText( "", false, false );
					argumentsLabel.setText( "", false, false );
				}
				container.reflow( true );
				container.layout( );
			}
		} );

		fileText.addModifyListener( new ModifyListener( ) {

			public void modifyText( ModifyEvent e )
			{
				String file = fileText.getText( ).trim( );
				if ( file.length( ) > 0 )
				{
					resolveButton.setEnabled( true );
				}
				else
					resolveButton.setEnabled( false );
			}

		} );

	}

	private void createCreateLinkArea( )
	{
		Section createLinkSection = WidgetUtil.getToolkit( )
				.createSection( container.getBody( ), Section.EXPANDED );
		TableWrapData td = new TableWrapData( TableWrapData.FILL );
		createLinkSection.setLayoutData( td );
		createLinkSection.setText( "Create Link:" );
		WidgetUtil.getToolkit( ).createCompositeSeparator( createLinkSection );

		WidgetUtil.createFormText( container.getBody( ),
				"Select a file that you want to create the link for and click \"Create link\" button. The link will be created on your desktop." );
		Composite fileSelectedArea = WidgetUtil.getToolkit( )
				.createComposite( container.getBody( ) );
		GridLayout layout = new GridLayout( );
		fileSelectedArea.setLayout( layout );
		layout.numColumns = 4;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		WidgetUtil.createLabel( fileSelectedArea ).setText( "Select File:" );
		final Text fileText = WidgetUtil.getToolkit( )
				.createText( fileSelectedArea, "" );
		GridData gd = new GridData( );
		gd.widthHint = 220;
		fileText.setLayoutData( gd );
		Button button = WidgetUtil.getToolkit( )
				.createButton( fileSelectedArea, SWT.PUSH, true );
		button.setText( "Browse..." );
		button.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				FileDialog dialog = new FileDialog( container.getShell( ),
						SWT.OPEN );
				String file = dialog.open( );
				if ( file != null )
				{
					fileText.setText( file );
				}
			}
		} );
		final Button createButton = WidgetUtil.getToolkit( )
				.createButton( fileSelectedArea, SWT.PUSH, true );
		createButton.setText( "Create Link" );

		createButton.setEnabled( false );

		fileText.addModifyListener( new ModifyListener( ) {

			public void modifyText( ModifyEvent e )
			{
				String file = fileText.getText( ).trim( );
				if ( file.length( ) > 0 )
				{
					createButton.setEnabled( true );
				}
				else
					createButton.setEnabled( false );
			}

		} );

		final FormText messageLabel = WidgetUtil.createFormText( container.getBody( ) );

		createButton.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				String fileName = new File( fileText.getText( ) ).getName( );
				fileName = fileName.substring( 0, fileName.lastIndexOf( "." ) );
				if ( ShellLink.createShortCut( fileText.getText( ),
						ShellFolder.DESKTOP.getAbsolutePath( container.handle )
								+ File.separator
								+ fileName
								+ ".lnk" ) )
				{
					ShellLink.setShortCutDescription( ShellFolder.DESKTOP.getAbsolutePath( container.handle )
							+ File.separator
							+ fileName
							+ ".lnk",
							"Created link by SWT Win32 Extension" );
					messageLabel.setText( "Create link successful.",
							false,
							false );
				}
				else
					messageLabel.setText( "Create link failed.", false, false );

			}
		} );
	}

	private void createTitle( )
	{
		WidgetUtil.createFormText( container.getBody( ),
				"This page demonstrates how to create and resolve link files." );
	}

	public String getDisplayName( )
	{
		return "Shell Links";
	}
}
