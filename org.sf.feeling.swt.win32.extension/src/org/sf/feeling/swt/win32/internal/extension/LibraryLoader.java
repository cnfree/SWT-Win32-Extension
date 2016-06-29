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

package org.sf.feeling.swt.win32.internal.extension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class LibraryLoader
{

	private static HashMap nativeLibraryPath = new HashMap( );

	/**
	 * Ensure our unpacked native library gets cleaned up if this class gets
	 * garbage-collected.
	 */
	static final Object finalizer = new Object( ) {

		protected void finalize( )
		{
			deleteNativeLibraries( );
		}
	};

	/**
	 * Remove any unpacked native library. Forcing the class loader to unload it
	 * first is required on Windows, since the temporary native library can't be
	 * deleted until the native library is unloaded. Any deferred execution we
	 * might install at this point would prevent the Native class and its class
	 * loader from being GC'd, so we instead force the native library unload
	 * just a little bit prematurely.
	 */
	private static boolean deleteNativeLibrary( String path )
	{
		boolean unpacked = false;

		Object object = nativeLibraryPath.get( path );
		if ( Boolean.TRUE.equals( object ) )
			unpacked = true;

		if ( path == null || !unpacked )
			return true;
		File flib = new File( path );
		if ( flib.delete( ) )
		{
			nativeLibraryPath.remove( path );
			return true;
		}
		try
		{
			ClassLoader cl = LibraryLoader.class.getClassLoader( );
			Field f = ClassLoader.class.getDeclaredField( "nativeLibraries" );
			f.setAccessible( true );
			List libs = (List) f.get( cl );
			for ( Iterator i = libs.iterator( ); i.hasNext( ); )
			{
				Object lib = i.next( );
				f = lib.getClass( ).getDeclaredField( "name" );
				f.setAccessible( true );
				String name = (String) f.get( lib );
				if ( name.equals( path )
						|| name.indexOf( path ) != -1
						|| name.equals( flib.getCanonicalPath( ) ) )
				{
					Method m = lib.getClass( ).getDeclaredMethod( "finalize",
							new Class[0] );
					m.setAccessible( true );
					m.invoke( lib, new Object[0] );
					if ( unpacked )
					{
						if ( flib.exists( ) )
						{
							if ( flib.delete( ) )
							{
								nativeLibraryPath.remove( path );
								unpacked = false;
								return true;
							}
							return false;
						}
					}
					return true;
				}
			}
		}
		catch ( Exception e )
		{
		}
		return false;
	}

	private static void deleteNativeLibraries( )
	{
		String[] paths = (String[]) nativeLibraryPath.keySet( )
				.toArray( new String[0] );
		for ( int i = 0; i < paths.length; i++ )
		{
			deleteNativeLibrary( paths[i] );
		}
	}

	private LibraryLoader( )
	{
	}

	static void loadNativeLibrary( String libName )
	{
		String bootPath = System.getProperty( "java.library.path" );
		if ( bootPath != null )
		{
			String[] dirs = bootPath.split( File.pathSeparator );
			for ( int i = 0; i < dirs.length; ++i )
			{
				String path = new File( new File( dirs[i] ),
						System.mapLibraryName( libName ) ).getAbsolutePath( );
				try
				{
					System.load( path );
					nativeLibraryPath.put( path, Boolean.FALSE );
					return;
				}
				catch ( UnsatisfiedLinkError ex )
				{
				}
			}
		}
		try
		{
			System.loadLibrary( libName );
			nativeLibraryPath.put( libName, Boolean.FALSE );
		}
		catch ( UnsatisfiedLinkError e )
		{
			loadNativeLibraryFromJar( libName );
		}
	}

	private static void loadNativeLibraryFromJar( String libName )
	{
		String resourceName = "/" + libName + ".dll";
		URL url = LibraryLoader.class.getResource( resourceName );

		if ( url == null )
		{
			throw new UnsatisfiedLinkError( libName
					+ " ("
					+ resourceName
					+ ") not found in resource path" );
		}

		File lib = null;
		if ( url.getProtocol( ).toLowerCase( ).equals( "file" ) )
		{
			try
			{
				lib = new File( new URI( url.toString( ) ) );
			}
			catch ( URISyntaxException e )
			{
				lib = new File( url.getPath( ) );
			}
			if ( !lib.exists( ) )
			{
				throw new Error( "File URL "
						+ url
						+ " could not be properly decoded" );
			}
		}
		else
		{
			InputStream is = LibraryLoader.class.getResourceAsStream( resourceName );
			if ( is == null )
			{
				throw new Error( "Can't obtain " + libName + " InputStream" );
			}

			FileOutputStream fos = null;
			try
			{
				// Suffix is required on windows, or library fails to load
				// Let Java pick the suffix, except on windows, to avoid
				// problems with Web Start.
				lib = File.createTempFile( libName, ".dll" );
				lib.deleteOnExit( );
				ClassLoader cl = LibraryLoader.class.getClassLoader( );
				if ( cl == null
						|| cl.equals( ClassLoader.getSystemClassLoader( ) ) )
				{
					Runtime.getRuntime( )
							.addShutdownHook( new DeleteNativeLibrary( lib ) );
				}
				fos = new FileOutputStream( lib );
				int count;
				byte[] buf = new byte[1024];
				while ( ( count = is.read( buf, 0, buf.length ) ) > 0 )
				{
					fos.write( buf, 0, count );
				}
			}
			catch ( IOException e )
			{
				throw new Error( "Failed to create temporary file for "
						+ libName
						+ " library: "
						+ e );
			}
			finally
			{
				try
				{
					is.close( );
				}
				catch ( IOException e )
				{
				}
				if ( fos != null )
				{
					try
					{
						fos.close( );
					}
					catch ( IOException e )
					{
					}
				}
			}
		}
		System.load( lib.getAbsolutePath( ) );
		nativeLibraryPath.put( lib.getAbsolutePath( ), Boolean.TRUE );
	}

	/** For internal use only. */
	public static class DeleteNativeLibrary extends Thread
	{

		private final File file;

		public DeleteNativeLibrary( File file )
		{
			this.file = file;
		}

		public void run( )
		{
			// If we can't force an unload/delete, spawn a new process
			// to do so
			if ( !deleteNativeLibrary( file.getAbsolutePath( ) ) )
			{
				try
				{
					Runtime.getRuntime( ).exec( new String[]{
							System.getProperty( "java.home" ) + "/bin/java",
							"-cp",
							System.getProperty( "java.class.path" ),
							getClass( ).getName( ),
							file.getAbsolutePath( ),
					} );
				}
				catch ( IOException e )
				{
					e.printStackTrace( );
				}
			}
		}

		public static void main( String[] args )
		{
			if ( args.length == 1 )
			{
				File file = new File( args[0] );
				if ( file.exists( ) )
				{
					long start = System.currentTimeMillis( );
					while ( !file.delete( ) && file.exists( ) )
					{
						try
						{
							Thread.sleep( 10 );
						}
						catch ( InterruptedException e )
						{
						}
						if ( System.currentTimeMillis( ) - start > 5000 )
						{
							System.err.println( "Could not remove temp file: "
									+ file.getAbsolutePath( ) );
							break;
						}
					}
				}
			}
			System.exit( 0 );
		}
	}

}
