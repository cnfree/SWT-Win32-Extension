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

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.example.Example;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.CanonicalPIDL;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.DesktopFolder;
import org.sf.feeling.swt.win32.extension.shell.IShellFolder.RelativePIDL;
import org.sf.feeling.swt.win32.extension.shell.ShellFolder;
import org.sf.feeling.swt.win32.extension.shell.ShellIcon;

public class ShellFolderPage extends SimpleTabPage
{

	public void buildUI( Composite parent )
	{
		super.buildUI( parent );
		TableWrapLayout layout = new TableWrapLayout( );
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.topMargin = 15;
		layout.verticalSpacing = 20;
		container.getBody( ).setLayout( layout );

		createTitle( );
		createShellFolderArea( );
	}

	private void createShellFolderArea( )
	{
		TableWrapData td;
		Section shellFolderSection = WidgetUtil.getToolkit( )
				.createSection( container.getBody( ), Section.EXPANDED );
		td = new TableWrapData( TableWrapData.FILL );
		shellFolderSection.setLayoutData( td );
		shellFolderSection.setText( "Shell Folder Icon List:" );
		WidgetUtil.getToolkit( ).createCompositeSeparator( shellFolderSection );
		Composite shellFolderClient = WidgetUtil.getToolkit( )
				.createComposite( shellFolderSection );
		GridLayout layout = new GridLayout( );
		layout.numColumns = 4;
		shellFolderClient.setLayout( layout );

		createCLabel( shellFolderClient,
				getDisplayName( ShellFolder.COMPUTER ),
				ShellFolder.COMPUTER );
		createCLabel( shellFolderClient,
				getDisplayName( ShellFolder.DESKTOP ),
				ShellFolder.DESKTOP );
		createCLabel( shellFolderClient,
				getDisplayName( ShellFolder.NETWORK ),
				ShellFolder.NETWORK );
		createCLabel( shellFolderClient,
				getDisplayName( ShellFolder.INTERNET ),
				ShellFolder.INTERNET );
		createCLabel( shellFolderClient,
				getDisplayName( ShellFolder.PRINTERS ),
				ShellFolder.PRINTERS );
		createCLabel( shellFolderClient,
				getDisplayName( ShellFolder.CONTROLS ),
				ShellFolder.CONTROLS );
		createCLabel( shellFolderClient,
				getDisplayName( ShellFolder.FAVORITES ),
				ShellFolder.FAVORITES );
		if ( Win32.getWin32Version( ) < Win32.VERSION( 6, 0 ) )
		{
			createCLabel( shellFolderClient,
					getDisplayName( ShellFolder.COMMON_PROGRAMS ),
					ShellFolder.COMMON_PROGRAMS );
		}
		else
		{
			createCLabel( shellFolderClient,
					getDisplayName( ShellFolder.MYVIDEO ),
					ShellFolder.MYVIDEO );
			createCLabel( shellFolderClient,
					getDisplayName( ShellFolder.MYMUSIC ),
					ShellFolder.MYMUSIC );
			createCLabel( shellFolderClient,
					getDisplayName( ShellFolder.MYPICTURES ),
					ShellFolder.MYPICTURES );
		}
		
		shellFolderSection.setClient( shellFolderClient );

	}

	private String getDisplayName( ShellFolder shellFolder )
	{
		DesktopFolder desktop = IShellFolder.getDesktopFolder( Example.getInstance( )
				.getShell( ).handle );
		CanonicalPIDL handle = ShellFolder.getFolderPIDL( shellFolder );
		if ( Win32.getWin32Version( ) < Win32.VERSION( 6, 0 ) )
		{
			IShellFolder folder = IShellFolder.getRelativeParent( desktop,
					handle );
			RelativePIDL relativeHandle = IShellFolder.getRelativePIDL( handle );
			if ( relativeHandle != null )
			{
				String displayPath = folder.getPIDLDisplayName( relativeHandle,
						IShellFolder.SHGDNF.INFOLDER );
				return displayPath + ":";
			}
			else
			{
				return "";
			}
		}
		else
		{
			String displayPath = IShellFolder.getPIDLDisplayName( desktop,
					handle,
					IShellFolder.SHGDNF.INFOLDER );
			return displayPath + ":";
		}
	}

	public String getDisplayName( )
	{
		return "Shell Folders";
	}

	private void createTitle( )
	{
		WidgetUtil.createFormText( container.getBody( ),
				"<form><p>This page lists some of your system folders by using the ShellFolder class functions.<br/><br/><b>NOTE:</b> "
						+ "Along with ShellFolder path this page demonstrates shell folder icons extracted from system and converted to java images.</p></form>",
				true,
				false );
	}

	protected CLabel createCLabel( Composite parent, String text,
			ShellFolder shellFolder )
	{
		WidgetUtil.getToolkit( ).createLabel( parent, text );
		CLabel label = WidgetUtil.getToolkit( ).createCLabel( parent, "" );
		label.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
		Image image = ShellIcon.getSysFolderIcon( shellFolder.getFolderID( ),
				ShellIcon.ICON_LARGE );
		if ( image != null && !image.isDisposed( ) )
			label.setImage( image );
		return label;
	}
}
