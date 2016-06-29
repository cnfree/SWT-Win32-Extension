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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.io.CDDrive;
import org.sf.feeling.swt.win32.extension.io.FileSystem;
import org.sf.feeling.swt.win32.extension.io.hook.CDDriveMsgHook;
import org.sf.feeling.swt.win32.extension.io.listener.CDDriveChangeListener;
import org.sf.feeling.swt.win32.extension.shell.ShellIcon;
import org.sf.feeling.swt.win32.extension.system.OSVersionInfo;

public class FileSystemPage extends SimpleTabPage implements
		CDDriveChangeListener
{

	private CDDriveMsgHook hook;

	private List cdDriveList = new ArrayList( );

	private List cdDriveControlList = new ArrayList( );

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
		createFileSystemInfo( );
	}

	private void createFileSystemInfo( )
	{
		TableWrapData td;
		Section fileInfoSection = WidgetUtil.getToolkit( )
				.createSection( container.getBody( ), Section.EXPANDED );
		td = new TableWrapData( TableWrapData.FILL );
		fileInfoSection.setLayoutData( td );
		fileInfoSection.setText( "File System Information:" );
		WidgetUtil.getToolkit( ).createCompositeSeparator( fileInfoSection );
		Composite fileInfoClient = WidgetUtil.getToolkit( )
				.createComposite( fileInfoSection );
		GridLayout layout = new GridLayout( );
		layout.numColumns = 6;
		fileInfoClient.setLayout( layout );

		WidgetUtil.createLabel( fileInfoClient );
		WidgetUtil.createBoldLabel( fileInfoClient ).setText( "Type" );
		WidgetUtil.createBoldLabel( fileInfoClient ).setText( "Total Size" );
		WidgetUtil.createBoldLabel( fileInfoClient ).setText( "Free Space" );
		WidgetUtil.createBoldLabel( fileInfoClient ).setText( "Serial No." );
		WidgetUtil.createLabel( fileInfoClient );
		String[] drives = FileSystem.getLogicalDrives( );
		for ( int i = 0; i < drives.length; i++ )
		{
			int diskType = FileSystem.getDriveType( drives[i] );
			CLabel driveLabel = WidgetUtil.getToolkit( )
					.createCLabel( fileInfoClient, "" );
			driveLabel.setData( drives[i] );
			driveLabel.setImage( ShellIcon.getFileAssociatedIcons( driveLabel.handle,
					new File( drives[i] ) ) );
			driveLabel.setText( FileSystem.getVolumeLabel( drives[i] )
					+ " ("
					+ drives[i]
					+ ")" );
			driveLabel.setForeground( driveLabel.getParent( ).getForeground( ) );
			Label volumeTypeLabel = WidgetUtil.createLabel( fileInfoClient );
			volumeTypeLabel.setText( getVolumeType( diskType ) );
			String totleSpace = format( FileSystem.getDiskTotalSize( drives[i] ) );
			Label totleSpaceLabel = WidgetUtil.createLabel( fileInfoClient );
			totleSpaceLabel.setText( totleSpace );
			Label freeSpaceLabel = WidgetUtil.createLabel( fileInfoClient );
			freeSpaceLabel.setText( format( FileSystem.getDiskFreeSpace( drives[i] ) ) );
			Label serialNumberLabel = WidgetUtil.createLabel( fileInfoClient );
			serialNumberLabel.setText( FileSystem.getVolumeSerialNumber( drives[i] ) );
			if ( diskType == FileSystem.DRIVE_TYPE_CDROM )
			{
				cdDriveControlList.add( driveLabel );
				cdDriveControlList.add( volumeTypeLabel );
				cdDriveControlList.add( totleSpaceLabel );
				cdDriveControlList.add( freeSpaceLabel );
				cdDriveControlList.add( serialNumberLabel );

				final int driveId = CDDrive.getDeviceID( drives[i].substring( 0,
						2 ) );

				Button button = WidgetUtil.createButton( fileInfoClient,
						"Eject" );
				GridData gd = new GridData( );
				gd.widthHint = 60;
				button.setLayoutData( gd );

				button.setData( new Integer( driveId ) );
				if ( CDDrive.isDoorOpened( driveId ) )
					button.setText( "Load" );

				button.addSelectionListener( new SelectionAdapter( ) {

					public void widgetSelected( SelectionEvent e )
					{
						if ( !CDDrive.isDoorOpened( driveId ) )
							CDDrive.openDoor( driveId );
						else
							CDDrive.closeDoor( driveId );
					}

				} );
				cdDriveList.add( button );

				if ( OSVersionInfo.getInstance( ).getMajor( ) >= 6 )
					button.setVisible( false );
			}
			else
				WidgetUtil.createLabel( fileInfoClient );
		}

		fileInfoSection.setClient( fileInfoClient );

	}

	private String format( float totalNumberOfFreeBytes )
	{
		NumberFormat format = NumberFormat.getInstance( );
		format.setMaximumFractionDigits( 1 );
		format.setMinimumFractionDigits( 0 );
		if ( totalNumberOfFreeBytes < 1024 )
			return format.format( totalNumberOfFreeBytes ) + " Bytes";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if ( totalNumberOfFreeBytes < 1024 )
			return format.format( totalNumberOfFreeBytes ) + " KB";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if ( totalNumberOfFreeBytes < 1024 )
			return format.format( totalNumberOfFreeBytes ) + " MB";
		totalNumberOfFreeBytes = totalNumberOfFreeBytes / 1024;
		if ( totalNumberOfFreeBytes < 1024 )
			return format.format( totalNumberOfFreeBytes ) + " GB";
		return null;
	}

	private String getVolumeType( int diskType )
	{
		switch ( diskType )
		{
			case FileSystem.DRIVE_TYPE_CDROM :
				return "CD-ROM";
			case FileSystem.DRIVE_TYPE_FIXED :
				return "Local Disk";
			case FileSystem.DRIVE_TYPE_RAMDISK :
				return "RAM Disk";
			case FileSystem.DRIVE_TYPE_REMOTE :
				return "Remote Drive";
			case FileSystem.DRIVE_TYPE_REMOVABLE :
				return "Removable Drive";
			default :
				return "Unknown";
		}
	}

	private void createTitle( )
	{
		WidgetUtil.createFormText( container.getBody( ),
				"This page demonstrates FileSystem class features to retrieve file system information." );
	}

	public String getDisplayName( )
	{
		return "File System Example";
	}

	public void refresh( )
	{
		new Thread( ) {

			public void run( )
			{
				try
				{
					Thread.sleep( 1000 );
				}
				catch ( InterruptedException e )
				{
				}
				Display.getDefault( ).asyncExec( new Runnable( ) {

					public void run( )
					{
						for ( int i = 0; i < cdDriveList.size( ); i++ )
						{
							CLabel driveLabel = ( (CLabel) cdDriveControlList.get( i * 5 ) );
							String drive = (String) driveLabel.getData( );
							driveLabel.setImage( ShellIcon.getFileAssociatedIcons( driveLabel.handle,
									new File( drive ) ) );
							driveLabel.setText( FileSystem.getVolumeLabel( drive )
									+ " ("
									+ drive
									+ ")" );

							String totleSpace = format( FileSystem.getDiskTotalSize( drive ) );
							( (Label) cdDriveControlList.get( i * 5 + 2 ) ).setText( totleSpace );

							String freeSpace = format( FileSystem.getDiskFreeSpace( drive ) );
							( (Label) cdDriveControlList.get( i * 5 + 3 ) ).setText( freeSpace );

							String serialNumber = FileSystem.getVolumeSerialNumber( drive );
							( (Label) cdDriveControlList.get( i * 5 + 4 ) ).setText( serialNumber );

							Button button = (Button) cdDriveList.get( i );
							int driveId = ( (Integer) button.getData( ) ).intValue( );
							if ( CDDrive.isDoorOpened( driveId ) )
								button.setText( "Load" );
							else
								button.setText( "Eject" );
							container.layout( );
						}
					}
				} );
			}
		}.start( );

	}

	public void activate( )
	{
		if ( hook == null )
		{
			hook = new CDDriveMsgHook( container.getShell( ) );
		}
		hook.installHook( );
		hook.addChangeListener( this );
	}

	public void deActivate( )
	{
		hook.removeChangeListener( this );
		hook.unInstallHook( );
	}

	public void driveLoaded( Event event )
	{
		refresh( );
	}

	public void driveEjected( Event event )
	{
		refresh( );
	}
}
