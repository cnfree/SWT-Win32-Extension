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

import java.net.InetAddress;
import java.text.NumberFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.sf.feeling.swt.win32.extension.example.Example;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.io.FileSystem;
import org.sf.feeling.swt.win32.extension.io.Network;
import org.sf.feeling.swt.win32.extension.registry.RegistryKey;
import org.sf.feeling.swt.win32.extension.registry.RootKey;
import org.sf.feeling.swt.win32.extension.shell.ShellFolder;
import org.sf.feeling.swt.win32.extension.shell.ShellIcon;
import org.sf.feeling.swt.win32.extension.system.DllVersionInfo;
import org.sf.feeling.swt.win32.extension.system.FileVersionInfo;
import org.sf.feeling.swt.win32.extension.system.MemoryStatus;
import org.sf.feeling.swt.win32.extension.system.OSVersionInfo;
import org.sf.feeling.swt.win32.extension.system.SystemInfo;
import org.sf.feeling.swt.win32.extension.system.SystemVariables;
import org.sf.feeling.swt.win32.internal.extension.util.ColorCache;
import org.sf.feeling.swt.win32.internal.extension.util.ImageCache;

public class SystemPage extends TabPage
{

	private Composite container;

	public void buildUI( Composite parent )
	{
		container = WidgetUtil.getToolkit( ).createComposite( parent );
		GridLayout layout = new GridLayout( );
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.marginTop = 0;
		layout.verticalSpacing = 15;
		container.setLayout( layout );

		createTitle( );

		CTabFolder tabFolder = new CTabFolder( container, SWT.NONE );

		tabFolder.setSelectionBackground( new Color[]{
				ColorCache.getInstance( ).getColor( 230, 233, 240 ),
				ColorCache.getInstance( ).getColor( 157, 167, 195 ),
				ColorCache.getInstance( ).getColor( 242, 244, 247 ),
		}, new int[]{
			75
		}, true );

		tabFolder.setSimple( false );;
		tabFolder.setUnselectedCloseVisible( true );

		tabFolder.setLayoutData( new GridData( GridData.FILL_BOTH ) );

		createProcessorInfo( tabFolder );
		createMemoryInfo( tabFolder );
		createSystemInfo( tabFolder );
		createFileSystemInfo( tabFolder );
		createNetworkInfo( tabFolder );
		tabFolder.setSelection( 0 );

	}

	private void createTitle( )
	{
		Label label = WidgetUtil.getToolkit( ).createLabel( container,
				"This page demonstrates how to get system information.",
				SWT.WRAP );
		GridData gd = new GridData( );
		gd.widthHint = 300;
		gd.horizontalAlignment = SWT.FILL;
		label.setLayoutData( gd );
	}

	private void createMemoryInfo( CTabFolder tabFolder )
	{
		CTabItem memoryItem = new CTabItem( tabFolder, SWT.NONE );
		memoryItem.setText( "Memory" );
		memoryItem.setImage( ImageCache.getImage( "memory.png" ) );
		Composite memoryClient = WidgetUtil.getToolkit( )
				.createComposite( tabFolder );

		GridLayout layout = new GridLayout( );
		layout.numColumns = 2;
		memoryClient.setLayout( layout );

		GridData gd = new GridData( GridData.FILL_HORIZONTAL );

		Label label = WidgetUtil.createLabel( memoryClient );
		label.setText( "Physical Memory Total:" );
		GridData gd1 = new GridData( );
		gd1.widthHint = 200;
		label.setLayoutData( gd1 );

		Label totalPhysicalLabel = WidgetUtil.createLabel( memoryClient );
		totalPhysicalLabel.setText( MemoryStatus.getInstance( ).getTotalPhys( )
				/ ( 1024 )
				+ " KB" );
		totalPhysicalLabel.setLayoutData( gd );

		WidgetUtil.createLabel( memoryClient )
				.setText( "Physical Memory Available:" );
		final Label physicalMemoryLabel = WidgetUtil.createLabel( memoryClient );
		physicalMemoryLabel.setLayoutData( gd );

		WidgetUtil.createLabel( memoryClient )
				.setText( "Virtual Memory Total:" );

		Label totalVirtualLabel = WidgetUtil.createLabel( memoryClient );
		totalVirtualLabel.setText( MemoryStatus.getInstance( )
				.getTotalVirtual( ) / ( 1024 ) + " KB" );
		totalVirtualLabel.setLayoutData( gd );

		WidgetUtil.createLabel( memoryClient )
				.setText( "Virtual Memory Available:" );
		final Label virtualMemoryLabel = WidgetUtil.createLabel( memoryClient );
		virtualMemoryLabel.setText( MemoryStatus.getInstance( )
				.getAvailVirtual( ) / ( 1024 ) + " KB" );

		Thread memoryThread = new Thread( ) {

			public void run( )
			{
				while ( !Example.DIRTY )
				{
					if ( Display.getDefault( ) != null )
						Display.getDefault( ).asyncExec( new Runnable( ) {

							public void run( )
							{
								if ( !physicalMemoryLabel.isDisposed( ) )
								{
									MemoryStatus.getInstance( ).refreshStatus( );
									physicalMemoryLabel.setText( MemoryStatus.getInstance( )
											.getAvailPhys( )
											/ ( 1024 )
											+ " KB" );
									virtualMemoryLabel.setText( MemoryStatus.getInstance( )
											.getAvailVirtual( )
											/ ( 1024 )
											+ " KB" );
								}
							}
						} );
					try
					{
						Thread.sleep( 1000 );
					}
					catch ( Exception e )
					{
						e.printStackTrace( );
					}
				}
			}
		};

		memoryThread.start( );

		memoryItem.setControl( memoryClient );
	}

	private void createSystemInfo( CTabFolder tabFolder )
	{
		CTabItem systemItem = new CTabItem( tabFolder, SWT.NONE );
		systemItem.setText( "System" );
		systemItem.setImage( ShellIcon.getSysFolderIcon( ShellFolder.COMPUTER,
				ShellIcon.ICON_SMALL ) );
		Composite systemClient = WidgetUtil.getToolkit( )
				.createComposite( tabFolder );

		GridLayout layout = new GridLayout( );
		layout.numColumns = 2;
		layout.horizontalSpacing = 30;
		systemClient.setLayout( layout );

		WidgetUtil.createLabel( systemClient ).setText( "OS Version:" );
		Label osVersionInfoLabel = WidgetUtil.createLabel( systemClient );
		osVersionInfoLabel.setText( OSVersionInfo.getInstance( ).toString( ) );
		osVersionInfoLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		SystemInfo systemInfo = SystemInfo.getInstance( );

		WidgetUtil.createLabel( systemClient ).setText( "Computer Name:" );
		Label computeNameInfoLabel = WidgetUtil.createLabel( systemClient );
		computeNameInfoLabel.setText( systemInfo.getComputerName( ) );
		computeNameInfoLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		WidgetUtil.createLabel( systemClient ).setText( "User Name:" );
		Label userNameInfoLabel = WidgetUtil.createLabel( systemClient );
		userNameInfoLabel.setText( systemInfo.getUserName( ) );
		userNameInfoLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		SystemVariables var = SystemVariables.getInstance( );

		Label variableLabel = WidgetUtil.createLabel( systemClient );
		variableLabel.setText( "System Variable:" );
		GridData gd = new GridData( );
		gd.verticalAlignment = SWT.BEGINNING;
		variableLabel.setLayoutData( gd );

		Label variableInfoLabel = WidgetUtil.getToolkit( )
				.createLabel( systemClient, SWT.WRAP, true );
		variableInfoLabel.setText( "Path=" + var.getValue( "Path" ) );
		variableInfoLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		RegistryKey key = new RegistryKey( RootKey.HKEY_LOCAL_MACHINE,
				"SOFTWARE\\Microsoft\\Internet Explorer" );
		if ( key.exists( ) && key.hasValue( "Version" ) )
		{
			WidgetUtil.createLabel( systemClient ).setText( "IE Version:" );
			Label ieVersionInfoLabel = WidgetUtil.createLabel( systemClient );
			ieVersionInfoLabel.setText( (String) key.getValue( "Version" )
					.getData( ) );
			ieVersionInfoLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
		}

		systemItem.setControl( systemClient );
	}

	private void createFileSystemInfo( CTabFolder tabFolder )
	{
		CTabItem fileSystemItem = new CTabItem( tabFolder, SWT.NONE );
		fileSystemItem.setText( "File System" );
		fileSystemItem.setImage( ShellIcon.getSysFolderIcon( ShellFolder.TEMPLATES,
				ShellIcon.ICON_SMALL ) );
		Composite fileSystemClient = WidgetUtil.getToolkit( )
				.createComposite( tabFolder );

		GridLayout layout = new GridLayout( );
		layout.numColumns = 2;
		layout.horizontalSpacing = 30;
		fileSystemClient.setLayout( layout );

		WidgetUtil.createLabel( fileSystemClient )
				.setText( "Current Directory:" );
		Label currentDirLabel = WidgetUtil.createLabel( fileSystemClient );
		currentDirLabel.setText( FileSystem.getCurrentDirectory( ) );
		currentDirLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		WidgetUtil.createLabel( fileSystemClient )
				.setText( "Windows Directory:" );
		Label windowsDirLabel = WidgetUtil.createLabel( fileSystemClient );
		windowsDirLabel.setText( FileSystem.getWindowsDirectory( ) );
		windowsDirLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		WidgetUtil.createLabel( fileSystemClient )
				.setText( "System Directory:" );
		Label systemDirLabel = WidgetUtil.createLabel( fileSystemClient );
		systemDirLabel.setText( FileSystem.getSystemDirectory( ) );
		systemDirLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		WidgetUtil.createLabel( fileSystemClient ).setText( "Temp Directory:" );
		Label tempDirLabel = WidgetUtil.createLabel( fileSystemClient );
		tempDirLabel.setText( FileSystem.getLongPathName( FileSystem.getTempPath( ) ) );
		tempDirLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		DllVersionInfo dllInfo = new DllVersionInfo( );
		dllInfo.loadVersionInfo( "shell32.dll" );

		Label dllVersioinLabel = WidgetUtil.createLabel( fileSystemClient );
		dllVersioinLabel.setText( "DLL Version:" );
		GridData gd = new GridData( );
		gd.verticalAlignment = SWT.BEGINNING;
		dllVersioinLabel.setLayoutData( gd );

		Label dllVersionInfoLabel = WidgetUtil.getToolkit( )
				.createLabel( fileSystemClient, SWT.WRAP, true );
		dllVersionInfoLabel.setText( "shell32.dll version: "
				+ dllInfo.getMajorVersion( )
				+ "."
				+ dllInfo.getMinorVersion( )
				+ "."
				+ dllInfo.getBuildNumber( ) );
		dllVersionInfoLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		Label fileVersionLabel = WidgetUtil.createLabel( fileSystemClient );
		fileVersionLabel.setText( "File Version:" );
		gd = new GridData( );
		gd.verticalAlignment = SWT.BEGINNING;
		fileVersionLabel.setLayoutData( gd );

		FileVersionInfo fileInfo = new FileVersionInfo( );
		fileInfo.loadVersionInfo( "shell32.dll" );

		Label fileVersionInfoLabel = WidgetUtil.getToolkit( )
				.createLabel( fileSystemClient, SWT.WRAP, true );
		fileVersionInfoLabel.setText( "shell32.dll version: "
				+ fileInfo.getFileVersion( ) );
		fileVersionInfoLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		fileSystemItem.setControl( fileSystemClient );
	}

	private void createNetworkInfo( CTabFolder tabFolder )
	{
		CTabItem networkItem = new CTabItem( tabFolder, SWT.NONE );
		networkItem.setText( "Network" );
		networkItem.setImage( ShellIcon.getSysFolderIcon( ShellFolder.NET_CONNECTIONS,
				ShellIcon.ICON_SMALL ) );
		final Composite networkClient = WidgetUtil.getToolkit( )
				.createComposite( tabFolder );

		GridLayout layout = new GridLayout( );
		layout.numColumns = 2;
		layout.horizontalSpacing = 30;
		networkClient.setLayout( layout );

		for ( int i = 0; i < SystemInfo.getMACAddresses( ).length; i++ )
		{
			WidgetUtil.createLabel( networkClient ).setText( "MAC["
					+ i
					+ "] Addresses:" );
			Label currentDirLabel = WidgetUtil.createLabel( networkClient );
			currentDirLabel.setText( SystemInfo.getMACAddresses( )[i] );
			currentDirLabel.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
		}
		Thread thread = new Thread( "Network" ) {

			public void run( )
			{
				try
				{
					Thread.sleep( 5000 );
					InetAddress addr = InetAddress.getByName( "www.google.com" );
					final String ip = addr.getHostAddress( );
					final int[] times = new int[4];
					for ( int i = 0; i < 4; i++ )
					{
						times[i] = Network.ping( "www.google.com", 32 );
					}
					Display.getDefault( ).asyncExec( new Runnable( ) {

						public void run( )
						{
							if ( networkClient == null
									|| networkClient.isDisposed( ) )
								return;
							Composite pingContainer = WidgetUtil.getToolkit( )
									.createComposite( networkClient );
							GridData gd = new GridData( GridData.FILL_HORIZONTAL );
							gd.horizontalSpan = 2;
							pingContainer.setLayoutData( gd );

							GridLayout layout = new GridLayout( );
							layout.marginWidth = 0;
							layout.marginHeight = 10;
							pingContainer.setLayout( layout );

							Label label = WidgetUtil.createLabel( pingContainer );
							label.setText( "Pinging www.google.com ["
									+ ip
									+ "] with 32 bytes of data:" );
							pingContainer.getParent( ).layout( );
							for ( int i = 0; i < 4; i++ )
							{

								if ( times[i] > 0 )
								{
									WidgetUtil.createLabel( pingContainer )
											.setText( "Reply from "
													+ ip
													+ ": bytes=32 time="
													+ times[i]
													+ "ms" );
								}
								else
								{
									WidgetUtil.createLabel( pingContainer )
											.setText( "Request timeout" );
								}
							}

							pingContainer.getParent( ).layout( );
						}
					} );

				}
				catch ( Exception e )
				{
					e.printStackTrace( );
				}
			}
		};
		thread.setDaemon( true );
		thread.start( );

		networkItem.setControl( networkClient );
	}

	private void createProcessorInfo( CTabFolder tabFolder )
	{
		CTabItem processorItem = new CTabItem( tabFolder, SWT.NONE );
		processorItem.setText( "Processor" );
		processorItem.setImage( ImageCache.getImage( "cpu.png" ) );
		Composite processorClient = WidgetUtil.getToolkit( )
				.createComposite( tabFolder );

		GridLayout layout = new GridLayout( );
		layout.numColumns = 2;
		processorClient.setLayout( layout );

		GridData gd = new GridData( GridData.FILL_HORIZONTAL );

		Label label = WidgetUtil.createLabel( processorClient );
		label.setText( "Processor Type:" );
		GridData gd1 = new GridData( );
		gd1.widthHint = 160;
		label.setLayoutData( gd1 );

		Label typeLabel = WidgetUtil.createLabel( processorClient );
		typeLabel.setText( "" + SystemInfo.getInstance( ).getProcessorType( ) );
		typeLabel.setLayoutData( gd );

		WidgetUtil.createLabel( processorClient ).setText( "Processor Number:" );
		Label numberLabel = WidgetUtil.createLabel( processorClient );
		numberLabel.setText( ""
				+ SystemInfo.getInstance( ).getNumberOfProcessors( ) );
		numberLabel.setLayoutData( gd );

		WidgetUtil.createLabel( processorClient )
				.setText( "Processor PageSize:" );
		Label pageSizeLabel = WidgetUtil.createLabel( processorClient );
		pageSizeLabel.setText( format( SystemInfo.getInstance( ).getPageSize( ) ) );
		pageSizeLabel.setLayoutData( gd );

		WidgetUtil.createLabel( processorClient ).setText( "Processor Usages:" );
		final Label cpuLabel = WidgetUtil.createLabel( processorClient );
		cpuLabel.setLayoutData( gd );
		Thread processorThread = new Thread( ) {

			public void run( )
			{
				while ( !Example.DIRTY )
				{
					if ( Display.getDefault( ) != null )
						Display.getDefault( ).asyncExec( new Runnable( ) {

							public void run( )
							{
								if ( !cpuLabel.isDisposed( ) )
									cpuLabel.setText( ""
											+ SystemInfo.getCpuUsages( )
											+ " %" );
							}
						} );
					try
					{
						Thread.sleep( 1000 );
					}
					catch ( Exception e )
					{
						e.printStackTrace( );
					}
				}
			}
		};
		processorThread.start( );

		RegistryKey key = new RegistryKey( RootKey.HKEY_LOCAL_MACHINE,
				"HARDWARE\\DESCRIPTION\\SYSTEM\\CentralProcessor\\0" );
		if ( key.exists( ) && key.hasValue( "VendorIdentifier" ) )
		{
			WidgetUtil.createLabel( processorClient )
					.setText( "Processor Vendor:" );
			Label vendorLabel = WidgetUtil.createLabel( processorClient );
			vendorLabel.setText( key.getValue( "VendorIdentifier" )
					.getData( )
					.toString( )
					.trim( ) );
			vendorLabel.setLayoutData( gd );
		}
		if ( key.exists( ) && key.hasValue( "ProcessorNameString" ) )
		{
			WidgetUtil.createLabel( processorClient )
					.setText( "Processor Name:" );
			Label nameLabel = WidgetUtil.createLabel( processorClient );
			nameLabel.setText( key.getValue( "ProcessorNameString" )
					.getData( )
					.toString( )
					.trim( ) );
			nameLabel.setLayoutData( gd );
		}
		if ( key.exists( ) && key.hasValue( "Identifier" ) )
		{
			WidgetUtil.createLabel( processorClient )
					.setText( "Processor Identifier:" );
			Label identifierLabel = WidgetUtil.createLabel( processorClient );
			identifierLabel.setText( key.getValue( "Identifier" )
					.getData( )
					.toString( )
					.trim( ) );
			identifierLabel.setLayoutData( gd );
		}
		if ( key.exists( ) && key.hasValue( "~Mhz" ) )
		{
			WidgetUtil.createLabel( processorClient )
					.setText( "Processor Frequency:" );
			Label frequenceLabel = WidgetUtil.createLabel( processorClient );
			frequenceLabel.setText( key.getValue( "~Mhz" )
					.getData( )
					.toString( )
					.trim( )
					+ " MHz" );
			frequenceLabel.setLayoutData( gd );
		}

		WidgetUtil.createLabel( processorClient ).setText( "Processor ID:" );
		Label cpuIdLabel = WidgetUtil.createLabel( processorClient );
		cpuIdLabel.setText( SystemInfo.getCPUID( ) );
		cpuIdLabel.setLayoutData( gd );

		processorItem.setControl( processorClient );
	}

	public String getDisplayName( )
	{
		return "System Info Example";
	}

	private String format( float totalNumberOfFreeBytes )
	{
		NumberFormat format = NumberFormat.getInstance( );
		format.setMaximumFractionDigits( 1 );
		format.setMinimumFractionDigits( 0 );
		if ( totalNumberOfFreeBytes < 1024 )
			return format.format( totalNumberOfFreeBytes ) + " byte";
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

	public Composite getControl( )
	{
		return container;
	}
}
