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

package org.sf.feeling.swt.win32.extension.hook;

import java.util.ArrayList;
import java.util.List;

import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.hook.data.HookData;
import org.sf.feeling.swt.win32.extension.hook.listener.HookEventListener;
import org.sf.feeling.swt.win32.extension.util.SortMap;
import org.sf.feeling.swt.win32.internal.extension.EventObject;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * This class enables to install various Windows hooks and retrieve information
 * from them using {@link HookEventListener}. All available hooks are
 * represented by the {@link Descriptor} class.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class Hook
{

	public static class Descriptor
	{

		/**
		 * Monitors keystroke messages.
		 */
		public static final Descriptor KEYBOARD = new Descriptor( Win32.WH_KEYBOARD,
				"KEYBOARD" );

		/**
		 * Monitors messages posted to a message queue.
		 */
		public static final Descriptor GETMESSAGE = new Descriptor( Win32.WH_GETMESSAGE,
				"GETMESSAGE" );

		/**
		 * Monitors messages before the system sends them to the destination
		 * window procedure.
		 */
		public static final Descriptor CALLWNDPROC = new Descriptor( Win32.WH_CALLWNDPROC,
				"CALLWNDPROC" );

		/**
		 * Receives notifications useful to a computer-based training (CBT)
		 * application.
		 */
		public static final Descriptor CBT = new Descriptor( Win32.WH_CBT,
				"CBT" );

		/**
		 * Monitors messages generated as a result of an input event in a dialog
		 * box, message box, menu, or scroll bar.
		 */
		public static final Descriptor SYSMSGFILTER = new Descriptor( Win32.WH_SYSMSGFILTER,
				"SYSMSGFILTER",
				true );

		/**
		 * Monitors mouse messages.
		 */
		public static final Descriptor MOUSE = new Descriptor( Win32.WH_MOUSE,
				"MOUSE" );

		/**
		 * Useful for debugging other hook procedures.
		 */
		public static final Descriptor DEBUG = new Descriptor( Win32.WH_DEBUG,
				"DEBUG" );

		/**
		 * Receives notifications useful to shell applications.
		 */
		public static final Descriptor SHELL = new Descriptor( Win32.WH_SHELL,
				"SHELL" );

		/**
		 * A hook procedure that will be called when the application's
		 * foreground thread is about to become idle. This hook is useful for
		 * performing low priority tasks during idle time.
		 */
		public static final Descriptor FOREGROUNDIDLE = new Descriptor( Win32.WH_FOREGROUNDIDLE,
				"FOREGROUNDIDLE" );

		/**
		 * Monitors messages after they have been processed by the destination
		 * window procedure.
		 */
		public static final Descriptor CALLWNDPROCRET = new Descriptor( Win32.WH_CALLWNDPROCRET,
				"CALLWNDPROCRET" );

		private boolean globalOnly;
		private String name = null;
		private int value;

		private Descriptor( int value, String name )
		{
			this.value = value;
			this.name = name;
		}

		private Descriptor( int value, String name, boolean globalOnly )
		{
			this.value = value;
			this.name = name;
			this.globalOnly = globalOnly;
		}

		/**
		 * Returns true if the HookDescriptor, which is determinated by this
		 * item, is global only.
		 * 
		 * @return true if the HookDescriptor is global only.
		 */
		public boolean isGlobalOnly( )
		{
			return globalOnly;
		}

		/**
		 * Returns the string descriptor.
		 * 
		 * @return the string descriptor.
		 */
		public String getName( )
		{
			return name;
		}

		public int getValue( )
		{
			return value;
		}
	}

	private final SortMap listeners = new SortMap( );

	private Descriptor descriptor;

	/**
	 * Hook described by {@link Descriptor#KEYBOARD} descriptor.
	 */
	public static final Hook KEYBOARD = new Hook( Descriptor.KEYBOARD );

	/**
	 * Hook described by {@link Descriptor#GETMESSAGE} descriptor.
	 */
	public static final Hook GETMESSAGE = new Hook( Descriptor.GETMESSAGE );

	/**
	 * Hook described by {@link Descriptor#CALLWNDPROC} descriptor.
	 */
	public static final Hook CALLWNDPROC = new Hook( Descriptor.CALLWNDPROC );

	/**
	 * Hook described by {@link Descriptor#SYSMSGFILTER} descriptor.
	 */
	public static final Hook SYSMSGFILTER = new Hook( Descriptor.SYSMSGFILTER );

	/**
	 * Hook described by {@link Descriptor#MOUSE} descriptor.
	 */
	public static final Hook MOUSE = new Hook( Descriptor.MOUSE );

	/**
	 * Hook described by {@link Descriptor#SHELL} descriptor.
	 */
	public static final Hook SHELL = new Hook( Descriptor.SHELL );

	/**
	 * Hook described by {@link Descriptor#FOREGROUNDIDLE} descriptor.
	 */
	public static final Hook FOREGROUNDIDLE = new Hook( Descriptor.FOREGROUNDIDLE );

	/**
	 * Hook described by {@link Descriptor#CALLWNDPROCRET} descriptor.
	 */
	public static final Hook CALLWNDPROCRET = new Hook( Descriptor.CALLWNDPROCRET );

	/**
	 * Creates a new instance of the hook by a specified hook descriptor.
	 * 
	 * @param descriptor
	 *            specifies a hook to install.
	 */
	private Hook( Descriptor descriptor )
	{
		this.descriptor = descriptor;
	}

	private Thread eventsThread = null;
	private HookEventLoop eventLoop = null;
	private boolean installed = false;
	private EventObject eventObject;
	private List refList = new ArrayList( );

	public synchronized boolean isInstalled( Object object )
	{
		if ( installed == false )
			return false;
		else
		{
			return refList.contains( object );
		}
	}

	/**
	 * Installs the hook.
	 */
	public synchronized void install( Object object )
	{
		if ( isInstalled( object ) )
			throw new IllegalStateException( "Hook is already installed." );

		if ( installed == false )
		{
			eventLoop = new HookEventLoop( );
			eventsThread = new Thread( eventLoop );
			eventsThread.start( );
		}

		refList.add( object );
	}

	/**
	 * Installs the hook.
	 * 
	 * @param global
	 *            If true will install a global system hook, else will install a
	 *            thread hook.
	 */
	public synchronized void install( Object object, boolean global )
	{
		if ( isInstalled( object ) )
			throw new IllegalStateException( "Hook is already installed." );

		if ( installed == false )
		{
			eventLoop = new HookEventLoop( global );
			eventsThread = new Thread( eventLoop );
			eventsThread.start( );
		}

		refList.add( object );
	}

	/**
	 * Uninstalls the hook.
	 */
	public synchronized void uninstall( Object object )
	{
		if ( !isInstalled( object ) )
			throw new IllegalStateException( "Hook is not installed." );

		refList.remove( object );

		if ( refList.size( ) == 0 )
		{
			eventLoop.uninstall( );
			try
			{
				eventsThread.join( );
				eventsThread = null;
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace( );
			}
		}
	}

	/**
	 * Adds a hook event listener.
	 * 
	 * @param listener
	 *            a hook event listener.
	 */
	public synchronized void addListener( Object object, HookEventListener listener )
	{
		if ( listeners.containsKey( object ) )
		{
			List list = (List) listeners.get( object );
			list.add( listener );
		}
		else
		{
			List list = new ArrayList( );
			list.add( listener );
			listeners.put( object, list );
		}
	}

	/**
	 * Removes a hook event listener.
	 * 
	 * @param listener
	 *            a hook event listener.
	 */
	public synchronized void removeListener( Object object, HookEventListener listener )
	{
		if ( listeners.containsKey( object ) )
		{
			List list = (List) listeners.get( object );
			list.remove( listener );
			if ( list.size( ) == 0 )
				listeners.remove( object );
		}
	}

	private class HookEventLoop implements Runnable
	{

		private boolean messageThreadAlive = false;
		private boolean global = true;

		public HookEventLoop( boolean global )
		{
			this.global = global;
		}

		public HookEventLoop( )
		{
		}

		/**
		 * Installs the hook.
		 */
		private void installHook( )
		{
			if ( global == false && descriptor.isGlobalOnly( ) )
				throw new UnsupportedOperationException( "The "
						+ descriptor.getName( )
						+ " hook doesn't support thread hook." );
			int threadId = 0;
			if ( global == false )
			{
				threadId = Extension.GetCurrentThreadId( );
			}
			Extension.InstallSystemHook( descriptor.getValue( ), threadId );
			installed = true;
		}

		private void unInstallHook( )
		{
			Extension.UnInstallSystemHook( descriptor.getValue( ) );
			installed = false;
		}

		/**
		 * Notifies listeners about a hook event.
		 */
		private void notifyListeners( HookData data )
		{
			for ( int i = 0; i < listeners.size( ) && messageThreadAlive; i++ )
			{
				List list = (List) listeners.get( i );
				for ( int j = 0; j < list.size( ); j++ )
				{
					( (HookEventListener) list.get( j ) ).acceptHookData( data );
				}
			}
		}

		/**
		 * Starts the event loop.
		 */
		public void run( )
		{
			messageThreadAlive = true;
			eventObject = new EventObject( descriptor.getName( ) );
			eventObject.reset( );
			eventLoop.installHook( );
			while ( messageThreadAlive )
			{
				eventObject.waitFor( );
				if ( messageThreadAlive )
				{
					HookData hookData = (HookData) Extension.ReadHookData( descriptor.getValue( ) );
					notifyListeners( hookData );
					eventObject.reset( );
				}
			}

			unInstallHook( );

			eventObject.reset( );
			eventObject.close( );
			installed = false;
		}

		/**
		 * Uninstalls the hook.
		 */
		private void uninstall( )
		{
			messageThreadAlive = false;
			eventObject.notifyEvent( );
		}
	}

}
