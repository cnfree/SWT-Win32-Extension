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
import java.io.FileOutputStream;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.hook.data.struct.Msg;
import org.sf.feeling.swt.win32.extension.io.FileSystem;
import org.sf.feeling.swt.win32.extension.ole.OleHookInterceptor;
import org.sf.feeling.swt.win32.extension.ole.flash.Flash;
import org.sf.feeling.swt.win32.extension.ole.flash.listener.FlashEventAdapter;
import org.sf.feeling.swt.win32.extension.ole.flash.listener.FlashEventListener;
import org.sf.feeling.swt.win32.extension.ole.mediaplayer.MediaPlayer;
import org.sf.feeling.swt.win32.extension.ole.mediaplayer.WMPMediaState;
import org.sf.feeling.swt.win32.extension.ole.mediaplayer.listener.MediaPlayerEventAdapter;
import org.sf.feeling.swt.win32.extension.ole.mediaplayer.listener.MediaPlayerEventListener;
import org.sf.feeling.swt.win32.internal.extension.util.ColorCache;

public class OleControlPage extends TabPage
{

	private Composite container;

	private Text flashFileText;

	private Flash flash;

	private Text mediaPlayerFileText;

	private MediaPlayer mediaPlayer;

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

		CTabFolder tabFolder = new CTabFolder( container, SWT.FLAT );

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
		if ( Flash.canCreate( ) )
		{
			createFlashItem( tabFolder );
		}
		if ( MediaPlayer.canCreate( ) )
		{
			createMediaPlayerItem( tabFolder );
		}
	}

	private void createFlashItem( CTabFolder tabFolder )
	{
		CTabItem flashItem = new CTabItem( tabFolder, SWT.NONE );
		flashItem.setText( " Flash Player " );
		Composite composite = WidgetUtil.getToolkit( )
				.createComposite( tabFolder );
		composite.setLayout( new GridLayout( ) );
		createFlashFileSelectedArea( composite );
		createFlashArea( composite );
		flashItem.setControl( composite );
	}

	private void createMediaPlayerItem( CTabFolder tabFolder )
	{
		CTabItem mediaPlayerItem = new CTabItem( tabFolder, SWT.NONE );
		mediaPlayerItem.setText( " Media Player " );
		Composite composite = WidgetUtil.getToolkit( )
				.createComposite( tabFolder );
		composite.setLayout( new GridLayout( ) );
		createMediaPlayerFileSelectedArea( composite );
		createMediaPlayerArea( composite );
		mediaPlayerItem.setControl( composite );
	}

	private void createFlashFileSelectedArea( Composite parent )
	{
		Composite fileSelectedArea = WidgetUtil.getToolkit( )
				.createComposite( parent );
		GridLayout layout = new GridLayout( );
		fileSelectedArea.setLayout( layout );
		layout.numColumns = 3;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		WidgetUtil.createLabel( fileSelectedArea ).setText( "Select File:" );
		flashFileText = WidgetUtil.getToolkit( ).createText( fileSelectedArea,
				"" );
		flashFileText.forceFocus( );
		flashFileText.addKeyListener( new KeyAdapter( ) {

			public void keyPressed( KeyEvent e )
			{
				if ( e.keyCode == Win32.VK_RETURN )
				{
					if ( flashFileText.getText( ).trim( ).length( ) == 0 )
						playFlash( );
					else
						flash.loadMovie( 0, flashFileText.getText( ) );
				}
			}
		} );
		GridData gd = new GridData( );
		gd.widthHint = 250;
		flashFileText.setLayoutData( gd );
		Button button = WidgetUtil.getToolkit( )
				.createButton( fileSelectedArea, SWT.PUSH, true );
		button.setText( "Browse..." );
		button.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				playFlash( );
			}
		} );

	}

	private void createMediaPlayerFileSelectedArea( Composite parent )
	{
		Composite fileSelectedArea = WidgetUtil.getToolkit( )
				.createComposite( parent );
		GridLayout layout = new GridLayout( );
		fileSelectedArea.setLayout( layout );
		layout.numColumns = 3;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		WidgetUtil.createLabel( fileSelectedArea ).setText( "Select File:" );
		mediaPlayerFileText = WidgetUtil.getToolkit( )
				.createText( fileSelectedArea, "" );
		mediaPlayerFileText.forceFocus( );
		mediaPlayerFileText.addKeyListener( new KeyAdapter( ) {

			public void keyPressed( KeyEvent e )
			{
				if ( e.keyCode == Win32.VK_RETURN )
				{
					if ( mediaPlayerFileText.getText( ).trim( ).length( ) == 0 )
						playFlash( );
					else
						flash.loadMovie( 0, mediaPlayerFileText.getText( ) );
				}
			}
		} );
		GridData gd = new GridData( );
		gd.widthHint = 250;
		mediaPlayerFileText.setLayoutData( gd );
		Button button = WidgetUtil.getToolkit( )
				.createButton( fileSelectedArea, SWT.PUSH, true );
		button.setText( "Browse..." );
		button.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				playMediaPlayer( );
			}
		} );

	}

	private void playFlash( )
	{
		String ext[] = new String[]{
			"*.swf"
		};
		FileDialog fd = new FileDialog( container.getShell( ), SWT.OPEN );
		fd.setFilterExtensions( ext );
		String file = fd.open( );
		if ( file != null )
		{
			flashFileText.setText( file );
			flash.loadMovie( 0, file );
		}
	}

	private void playMediaPlayer( )
	{
		String ext[] = new String[]{
			"*.*"
		};
		FileDialog fd = new FileDialog( container.getShell( ), SWT.OPEN );
		fd.setFilterExtensions( ext );
		String file = fd.open( );
		if ( file != null )
		{
			mediaPlayerFileText.setText( file );
			mediaPlayer.load( file );
		}
	}

	private void createFlashArea( Composite parent )
	{
		FlashEventListener listener = new FlashEventAdapter( ) {

			public void onFSCommand( String command, String args )
			{
				MessageBox message = new MessageBox( container.getShell( ) );
				message.setText( "Receive Event" );
				message.setMessage( "Receive a FSCommand Event: command = "
						+ command
						+ ", value = "
						+ args );
				message.open( );
			}
		};
		flash = new Flash( parent, SWT.NONE, listener );

		flash.addHookInterceptor( new OleHookInterceptor( ) {

			public boolean intercept( Msg message, int code, int wParam,
					int lParam )
			{
				if ( message.getMessage( ) == Win32.WM_RBUTTONDOWN )
				{
					Point cursor = flash.getControl( )
							.getParent( )
							.toControl( Display.getCurrent( )
									.getCursorLocation( ) );
					if ( flash.getControl( ).getBounds( ).contains( cursor )
							&& flash.getControl( ).isVisible( ) )
					{
						MessageBox messageBox = new MessageBox( container.getShell( ) );
						messageBox.setText( "Intercept Flash Menu" );
						messageBox.setMessage( "Use hook to intercept flash system menu." );
						messageBox.open( );
						return true;
					}
				}
				return false;
			}

		} );

		GridData td = new GridData( GridData.FILL_VERTICAL );
		flash.getControl( ).setLayoutData( td );
		resize( flash.getControl( ) );
		flash.getControl( )
				.getParent( )
				.addControlListener( new ControlAdapter( ) {

					public void controlResized( ControlEvent e )
					{
						resize( flash.getControl( ) );
					}

				} );
		flash.getControl( )
				.getParent( )
				.addPaintListener( new PaintListener( ) {

					public void paintControl( PaintEvent e )
					{
						resize( flash.getControl( ) );
					}
				} );

		try
		{
			InputStream is = this.getClass( )
					.getResourceAsStream( "/flash.swf" );
			File file = new File( FileSystem.getTempPath( ) + "flash.swf" );
			if ( !file.exists( ) )
			{
				FileOutputStream fos = new FileOutputStream( file );
				byte[] b = new byte[1024];
				int len;
				while ( ( len = is.read( b ) ) > 0 )
				{
					fos.write( b, 0, len );
				}
				is.close( );
				fos.close( );
			}
			flash.loadMovie( 0, file.getAbsolutePath( ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
		}
	}

	private void createMediaPlayerArea( Composite parent )
	{
		final Label infoLabel = WidgetUtil.getToolkit( ).createLabel( parent,
				true );
		GridData gd = new GridData( );
		gd.exclude = true;
		infoLabel.setLayoutData( gd );
		infoLabel.getParent( ).layout( );
		MediaPlayerEventListener listener = new MediaPlayerEventAdapter( ) {

			public void playStateChange( WMPMediaState newState )
			{
				if ( newState == WMPMediaState.PLAYING )
				{
					infoLabel.setText( "Media Player Event Listener : Playing \""
							+ mediaPlayer.getCurrentMediaName( )
							+ "\"" );
					hiddenLabel( infoLabel, false );
				}
				else if ( newState == WMPMediaState.PAUSED )
				{
					infoLabel.setText( "Media Player Event Listener : Paused" );
					hiddenLabel( infoLabel, false );
				}
				else if ( newState == WMPMediaState.BUFFERING )
				{
					infoLabel.setText( "Media Player Event Listener : Buffering..." );
					hiddenLabel( infoLabel, false );
				}
				else
				{
					infoLabel.setText( "" );
					hiddenLabel( infoLabel, true );
				}
			}

			private void hiddenLabel( final Label infoLabel, boolean hidden )
			{
				if ( ( (GridData) infoLabel.getLayoutData( ) ).exclude != hidden )
				{
					infoLabel.setVisible( !hidden );
					GridData gd = new GridData( );
					gd.exclude = hidden;
					infoLabel.setLayoutData( gd );
					infoLabel.getParent( ).layout( );
				}
			}
		};
		mediaPlayer = new MediaPlayer( parent, SWT.NONE, listener );

		mediaPlayer.addHookInterceptor( new OleHookInterceptor( ) {

			public boolean intercept( Msg message, int code, int wParam,
					int lParam )
			{
				if ( message.getMessage( ) == Win32.WM_RBUTTONDOWN )
				{
					Point cursor = mediaPlayer.getControl( )
							.getParent( )
							.toControl( Display.getCurrent( )
									.getCursorLocation( ) );
					if ( mediaPlayer.getControl( )
							.getBounds( )
							.contains( cursor )
							&& mediaPlayer.getControl( ).isVisible( ) )
					{
						MessageBox messageBox = new MessageBox( container.getShell( ) );
						messageBox.setText( "Intercept Media Player Menu" );
						messageBox.setMessage( "Use hook to intercept Media Player system menu." );
						messageBox.open( );
						return true;
					}
				}
				return false;
			}

		} );

		GridData td = new GridData( GridData.FILL_VERTICAL );
		mediaPlayer.getControl( ).setLayoutData( td );
		resize( mediaPlayer.getControl( ) );
		mediaPlayer.getControl( )
				.getParent( )
				.addControlListener( new ControlAdapter( ) {

					public void controlResized( ControlEvent e )
					{
						resize( mediaPlayer.getControl( ) );
					}

				} );
		mediaPlayer.getControl( )
				.getParent( )
				.addPaintListener( new PaintListener( ) {

					public void paintControl( PaintEvent e )
					{
						resize( mediaPlayer.getControl( ) );
					}
				} );
	}

	private void createTitle( )
	{
		Label label = WidgetUtil.getToolkit( )
				.createLabel( container,
						"This page demonstrates how to use ole controls and observe ole events.",
						SWT.WRAP );
		GridData gd = new GridData( );
		gd.widthHint = 300;
		gd.horizontalAlignment = SWT.FILL;
		label.setLayoutData( gd );
	}

	public Composite getControl( )
	{
		return container;
	}

	public String getDisplayName( )
	{
		return "Ole Control Example";
	}

	private void resize( Control control )
	{
		GridData gd = (GridData) control.getLayoutData( );
		int width = (int) ( control.getSize( ).y * ( (float) 16 / 9 ) );
		width = width < control.getParent( ).getSize( ).x - 30 ? width
				: control.getParent( ).getSize( ).x - 30;
		gd.widthHint = width;
		control.setLayoutData( gd );
		control.getParent( ).layout( );
	}

}
