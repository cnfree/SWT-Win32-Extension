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

package org.sf.feeling.swt.win32.extension.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PROCESS_INFORMATION;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.STARTUPINFO;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.hook.Hook;
import org.sf.feeling.swt.win32.extension.hook.data.HookData;
import org.sf.feeling.swt.win32.extension.hook.listener.HookEventListener;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.shell.Windows;
import org.sf.feeling.swt.win32.extension.system.Kernel;
import org.sf.feeling.swt.win32.extension.widgets.listener.NativeControlStatusListener;
import org.sf.feeling.swt.win32.internal.extension.EventObject;
import org.sf.feeling.swt.win32.internal.extension.Extension;

public class NativeControl extends Canvas
{

	private static final int EXITED = 3;
	private static final int FAILED = 2;
	private static final int LOADED = 1;
	private static final int STARTED = 0;
	private static final int TERMINATED = 4;
	private static final int TERMINATED_FAILED = 5;

	private boolean autoStart = true;
	private int childInstanceId = 0;
	private int childWnd = 0;

	private EventObject eventObject;

	private boolean isCreatedNative = false;

	private List listeners;
	private PROCESS_INFORMATION lpProcessInformation;
	private STARTUPINFO lpStartupInfo;
	private boolean monitorRefresh = true;
	private int offsetX, offsetY;

	private boolean removeMenu = true;

	private String startCommand = null;

	private int state = -1;

	private boolean trace = false;

	private String wndClassName = null;

	private int wndIndex = 0;

	private String wndTitle = null;

	public NativeControl( Composite parent, int style )
	{
		super( parent, style );

		this.addPaintListener( new PaintListener( ) {

			public void paintControl( PaintEvent e )
			{
				Display.getDefault( ).asyncExec( new Runnable( ) {

					public void run( )
					{
						if ( autoStart )
						{
							createNativeWnd( );
						}
					}
				} );
			}
		} );

		this.addDisposeListener( new DisposeListener( ) {

			public void widgetDisposed( DisposeEvent e )
			{

				if ( childWnd != 0 )
				{
					Extension.SendMessage( childWnd, Extension.WM_CLOSE, 0, 0 );

				}
				if ( childInstanceId != 0 )
				{
					boolean kill = Kernel.killProcess( childInstanceId );
					if ( kill )
						fireStatusEvent( TERMINATED );
					else
						fireStatusEvent( TERMINATED_FAILED );
				}
				if ( eventObject != null )
				{
					eventObject.notifyEvent( );
					eventObject.close( );
				}
				if ( lpProcessInformation != null
						&& lpProcessInformation.hProcess != 0 )
					OS.CloseHandle( lpProcessInformation.hProcess );
				if ( lpProcessInformation != null
						&& lpProcessInformation.hThread != 0 )
					OS.CloseHandle( lpProcessInformation.hThread );

				if ( Hook.SHELL.isInstalled( NativeControl.this ) )
					Hook.SHELL.uninstall( NativeControl.this );

				if ( Hook.GETMESSAGE.isInstalled( NativeControl.this ) )
					Hook.GETMESSAGE.uninstall( NativeControl.this );
			}

		} );
		this.addTraverseListener( new TraverseListener( ) {

			public void keyTraversed( TraverseEvent event )
			{
				switch ( event.detail )
				{
					case SWT.TRAVERSE_TAB_NEXT :
					case SWT.TRAVERSE_TAB_PREVIOUS :
						event.doit = true;
				}
			}

		} );
		// this.addFocusListener( new FocusAdapter( ) {
		//
		// public void focusGained( FocusEvent arg0 )
		// {
		// //forceChildWndActive( );
		// }
		//
		// } );

		// for traverse
		this.addKeyListener( new KeyAdapter( ) {
		} );
	}

	public void addStatusListener( NativeControlStatusListener listener )
	{
		if ( listener == null )
			return;
		if ( listeners == null )
			listeners = new ArrayList( );
		if ( !listeners.contains( listener ) )
			listeners.add( listener );
	}

	private void createNativeWnd( )
	{
		if ( childWnd == 0 && !isCreatedNative )
		{
			isCreatedNative = true;
			Thread thread = new Thread( ) {

				public void run( )
				{
					childWnd = startAndReparent( startCommand );
					if ( childWnd != 0 && !Display.getDefault( ).isDisposed( ) )
					{
						final Display display = Display.getDefault( );
						display.syncExec( new Runnable( ) {

							public void run( )
							{
								if ( NativeControl.this.isDisposed( ) )
									return;

								NativeControl.this.addControlListener( new ControlAdapter( ) {

									public void controlResized( ControlEvent e )
									{
										Rectangle rect = getClientArea( );

										int width = rect.width;
										int height = rect.height;

										removeChildMenu( );
										
										Extension.ShowWindow( childWnd, Win32.SW_RESTORE );
										
										Extension.SetWindowPos( childWnd,
												0,
												rect.x + offsetX,
												rect.y + offsetY,
												width,
												height,
												Win32.SWP_NOZORDER
														| Win32.SWP_NOACTIVATE
														| Win32.SWP_ASYNCWINDOWPOS
														| Win32.SWP_SHOWWINDOW
														| Win32.SWP_NOSENDCHANGING
														| Win32.SWP_DEFERERASE );
									}
								} );

								fireStatusEvent( LOADED );

								if ( monitorRefresh )
								{
									Thread thread = new Thread( ) {

										Point size = null;

										public void run( )
										{

											final int[] exit = new int[]{
												100
											};
											while ( exit[0] > 0 )
											{
												try
												{
													Thread.sleep( exit[0] );
												}
												catch ( InterruptedException e )
												{
													e.printStackTrace( );
												}
												Display.getDefault( )
														.syncExec( new Runnable( ) {

															public void run( )
															{
																if ( NativeControl.this.isDisposed( ) )
																{
																	exit[0] = 0;
																	return;
																}
																Rectangle rect = Windows.getWindowRect( childWnd );
																Point point = toControl( rect.x,
																		rect.y );
																Point newSize = new Point( rect.width,
																		rect.height );
																if ( point.x != offsetX
																		|| point.y != offsetY
																		|| !newSize.equals( size ) )
																{
																	size = newSize;
																	refresh( );
																	exit[0] = 100;
																}
																else
																{
																	exit[0] = 500;
																}
															}
														} );
											}

										}
									};
									thread.setDaemon( true );
									thread.start( );
								}
							}
						} );
					}
				}
			};
			thread.setDaemon( true );
			thread.start( );
		}
	}

	public boolean isCreatedNative( )
	{
		return isCreatedNative;
	}

	void fireStatusEvent( int type )
	{
		state = type;
		final Event event = new Event( );
		event.type = type;
		event.widget = this;
		event.time = Extension.GetTickCount( );

		if ( listeners != null )
		{
			Display.getDefault( ).syncExec( new Runnable( ) {

				public void run( )
				{
					for ( int i = 0; i < listeners.size( ); i++ )
					{
						final NativeControlStatusListener listener = (NativeControlStatusListener) listeners.get( i );
						switch ( event.type )
						{
							case STARTED :
								listener.handleStartEvent( event );
								break;
							case LOADED :
								listener.handleLoadedEvent( event );
								break;
							case FAILED :
								listener.handleFailedEvent( event );
								break;
							case EXITED :
								listener.handleExitedEvent( event );
								break;
							case TERMINATED :
								listener.handleTerminatedEvent( event );
								break;
							case TERMINATED_FAILED :
								event.detail = childInstanceId;
								listener.handleTerminatFailedEvent( event );
								break;
						}
					}
				}
			} );
		}
	}

	public int getChildeProcessHandle( )
	{
		if ( lpProcessInformation != null )
		{
			return lpProcessInformation.hProcess;
		}
		return 0;
	}

	public int getChildProcessId( )
	{
		return childInstanceId;
	}

	public int getChildWndHandle( )
	{
		return childWnd;
	}

	public boolean isAutoStart( )
	{
		return autoStart;
	}

	public boolean isMonitorRefresh( )
	{
		return monitorRefresh;
	}

	public String getStartCommand( )
	{
		return startCommand;
	}

	public String getWndClassName( )
	{
		return wndClassName;
	}

	public int getWndIndex( )
	{
		return wndIndex;
	}

	public String getWndTitle( )
	{
		return wndTitle;
	}

	public void monitorRefresh( boolean monitor )
	{
		this.monitorRefresh = monitor;
	}

	public void refresh( )
	{
		notifyListeners( SWT.Resize, new Event( ) );
	}

	public void removeChildWndMenu( boolean removeMenu )
	{
		this.removeMenu = removeMenu;
	}

	public void removeStatusListener( NativeControlStatusListener listener )
	{
		if ( listener == null )
			return;
		if ( listeners == null )
			return;
		else
		{
			listeners.remove( listener );
			if ( listeners.size( ) == 0 )
				listeners = null;
		}
	}

	public void setChildWndOffset( int offsetX, int offsetY )
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public void setStartCommand( String startCommand, boolean autoStart )
	{
		this.startCommand = startCommand;
		this.autoStart = autoStart;
	}

	public void setStartParameters( String startCommand, String wndClassName,
			String wndTitle, int wndIndex, boolean autoStart )
	{
		this.startCommand = startCommand;
		this.wndClassName = wndClassName;
		this.wndTitle = wndTitle;
		this.wndIndex = wndIndex;
		this.autoStart = autoStart;
	}

	public void setWndClassName( String wndClassName )
	{
		this.wndClassName = wndClassName;
	}

	public void setWndIndex( int wndIndex )
	{
		this.wndIndex = wndIndex;
	}

	public void setWndTitle( String wndTitle )
	{
		this.wndTitle = wndTitle;
	}

	public void start( )
	{
		createNativeWnd( );
	}

	protected int startAndReparent( final String startCommand )
	{
		if ( startCommand == null )
			return 0;
		fireStatusEvent( STARTED );
		final int[] hChildWnd = new int[1];

		Pointer commandCStr;
		try
		{
			commandCStr = Pointer.createPointerFromString( startCommand, Extension.IsUnicode );
		}
		catch ( NativeException e )
		{
			e.printStackTrace();
			return 0;
		}

		eventObject = new EventObject( "NativeControl@"
				+ NativeControl.this.hashCode( ) );

		eventObject.reset( );

		Hook.SHELL.addListener( NativeControl.this, new HookEventListener( ) {

			int index = 0;

			public void acceptHookData( HookData data )
			{
				if ( childInstanceId != 0
						&& ( data.getNCode( ) == Win32.HSHELL_WINDOWCREATED
								|| data.getNCode( ) == Win32.HSHELL_WINDOWACTIVATED || ( state < LOADED && data.getNCode( ) == Win32.HSHELL_REDRAW ) ) )
				{
					int hwnd = data.getWParam( );
					int[] pid = new int[1];
					Extension.GetWindowThreadProcessId( hwnd, pid );
					if ( pid[0] == childInstanceId )
					{
						String name = Windows.getClassName( hwnd );
						if ( wndClassName != null )
						{
							if ( name == null
									|| !name.startsWith( wndClassName ) )
							{
								if ( trace )
								{
									System.out.println( "Window Class Name = "
											+ name );
								}
								return;
							}
						}

						String title = Windows.getWindowText( hwnd );
						if ( wndTitle != null )
						{
							if ( title == null || !title.startsWith( wndTitle ) )
							{
								if ( trace )
								{
									System.out.println( "Window Title = "
											+ title );
								}
								return;
							}
						}

						if ( wndIndex > 0 )
						{
							index++;
							if ( index < wndIndex )
							{
								if ( trace )
								{
									System.out.println( "Index = " + index );
								}
								return;
							}
						}
						hChildWnd[0] = hwnd;
						Extension.ShowWindow( hwnd, Win32.SW_HIDE );

						if ( eventObject != null )
						{
							Extension.SetEvent( eventObject.getEventHandle( ) );
						}
					}
				}
			}
		} );
		Hook.SHELL.install( NativeControl.this );

		lpStartupInfo = new STARTUPINFO( );
		lpStartupInfo.cb = STARTUPINFO.sizeof;
		lpProcessInformation = new PROCESS_INFORMATION( );
		lpStartupInfo.wShowWindow = Win32.SW_HIDE;
		lpStartupInfo.dwFlags = Win32.STARTF_USESHOWWINDOW;
		lpStartupInfo.wShowWindow = Win32.SW_SHOWMINIMIZED;
		if ( !Extension.CreateProcess( 0,
				commandCStr.getPointer( ),
				0,
				0,
				true,
				0,
				0,
				0,
				lpStartupInfo,
				lpProcessInformation ) )
		{
			System.err.println( "ERROR: Cannot launch child process" );
			if ( Hook.SHELL.isInstalled( NativeControl.this ) )
				Hook.SHELL.uninstall( NativeControl.this );
			if ( Hook.GETMESSAGE.isInstalled( NativeControl.this ) )
				Hook.GETMESSAGE.uninstall( NativeControl.this );
			fireStatusEvent( FAILED );
			return 0;
		}

		childInstanceId = lpProcessInformation.dwProcessId;

		Thread thread = new Thread( ) {

			public void run( )
			{
				if ( lpProcessInformation != null
						&& lpProcessInformation.hProcess != 0
						&& Extension.WaitForSingleObject( lpProcessInformation.hProcess,
								Win32.INFINITE_TIMEOUT ) == 0 )
				{
					fireStatusEvent( EXITED );
					childInstanceId = 0;
					if ( Display.getDefault( ).isDisposed( ) )
						return;
					Display.getDefault( ).syncExec( new Runnable( ) {

						public void run( )
						{
							if ( !NativeControl.this.isDisposed( ) )
							{
								NativeControl.this.dispose( );
							}
						}
					} );
				}
			}
		};
		thread.setDaemon( true );
		thread.start( );

		eventObject.waitFor( 60000 );

		if ( hChildWnd[0] != 0 )
		{

			RECT rect = new RECT( );
			Extension.GetWindowRect( handle, rect );
			Extension.ShowWindow( hChildWnd[0], Win32.SW_HIDE );
			Extension.SetParent( hChildWnd[0], handle );
			Extension.SetWindowLong( hChildWnd[0],
					Win32.GWL_STYLE,
					Win32.WS_POPUP );

			Display.getDefault( ).asyncExec( new Runnable( ) {

				public void run( )
				{
					Extension.ShowWindow( hChildWnd[0], Win32.SW_RESTORE );

					removeChildMenu( );

					Rectangle rect = ( (Composite) ( NativeControl.this ) ).getClientArea( );

					int width = rect.width;
					int height = rect.height;

					Extension.SetWindowLong( hChildWnd[0],
							Win32.GWL_STYLE,
							Win32.WS_POPUP );

					Extension.SetWindowPos( childWnd,
							0,
							rect.x + offsetX,
							rect.y + offsetY,
							width,
							height,
							Win32.SWP_NOZORDER
									| Win32.SWP_NOACTIVATE
									| Win32.SWP_ASYNCWINDOWPOS
									| Win32.SWP_SHOWWINDOW
									| Win32.SWP_NOSENDCHANGING
									| Win32.SWP_DEFERERASE );

				}
			} );

		}

		return hChildWnd[0];
	}

	public void traceChildWndInfo( boolean trace )
	{
		this.trace = trace;
	}

	public void forceChildWndActive( )
	{
		if ( childWnd != 0 )
		{
			Extension.SetForegroundWindow( childWnd );
			NativeControl.this.getShell( ).forceFocus( );
		}
	}

	private void removeChildMenu( )
	{
		if ( removeMenu )
		{
			int menuHandle = Extension.GetMenu( childWnd );
			if ( menuHandle != 0 )
			{
				int count = Extension.GetMenuItemCount( menuHandle );
				for ( int i = 0; i < count; i++ )
					Extension.RemoveMenu( menuHandle,
							0,
							Win32.MF_BYPOSITION );
				Extension.DestroyMenu( menuHandle );
				Extension.SetMenu( childWnd, 0 );
			}
		}
	}

}
