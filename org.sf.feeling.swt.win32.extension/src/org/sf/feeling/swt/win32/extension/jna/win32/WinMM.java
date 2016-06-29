/*******************************************************************************
 * Copyright (c) 2011 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/

package org.sf.feeling.swt.win32.extension.jna.win32;

import org.sf.feeling.swt.win32.extension.jna.Callback;
import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.Type;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

public class WinMM
{

	/**
     *
     */
	private static final int RETURN_LENGTH = 1024;
	public static final String DLL_NAME = "winmm.dll";

	/**
	 * mciSendString (ByVal lpstrCommand As String, ByVal lpstrReturnString As
	 * String, ByVal uReturnLength As Integer, ByVal hwndCallback As Integer) As
	 * Integer
	 * <p>
	 * Simple VB Sample
	 * </p>
	 * 
	 * <pre>
	 * Private Sub Button1_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles Button1.Click
	 *      
	 *      ' record from microphone
	 *      
	 *      mciSendString("open new Type waveaudio Alias recsound", "", 0, 0)
	 *      
	 *      mciSendString("record recsound", "", 0, 0)
	 *      
	 *      End Sub
	 *      
	 *      
	 *      
	 *      Private Sub Button2_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles Button2.Click
	 *      
	 *      ' stop  and save
	 *      
	 *      mciSendString("save recsound c:\record.wav", "", 0, 0)
	 *      
	 *      mciSendString("close recsound", "", 0, 0)
	 *      
	 *      End Sub
	 *      
	 *      
	 *      
	 *      Private Sub Button3_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles Button3.Click
	 *      
	 *      ' play audio
	 *      
	 *      My.Computer.Audio.Play("c:\record.wav", AudioPlayMode.Background)
	 *      
	 *      End Sub
	 * </pre>
	 * 
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	public static final String mciSendString( String lpstrCommand,
			Callback hwndCallback ) throws NativeException,
			IllegalAccessException
	{
		Native mciSendString = new Native( DLL_NAME, "mciSendStringA" );
		mciSendString.setRetVal( Type.INT );
		Pointer lpstrReturnString = new Pointer( MemoryBlockFactory.createMemoryBlock( RETURN_LENGTH ) );
		try
		{
			int pos = 0;
			mciSendString.setParameter( pos++, lpstrCommand );
			mciSendString.setParameter( pos++, lpstrReturnString );
			mciSendString.setParameter( pos++, RETURN_LENGTH );
			mciSendString.setParameter( pos++, hwndCallback == null ? 0
					: hwndCallback.getCallbackAddress( ) );
			mciSendString.invoke( );
			final int retValAsInt = mciSendString.getRetValAsInt( );
			if ( retValAsInt == 0 )
			{
				return lpstrReturnString.getAsString( );
			}
			else
			{
				return mciGetErrorString( retValAsInt );
			}
		}
		finally
		{
			lpstrReturnString.dispose( );
		}
	}

	public static final String mciGetErrorString( int dwError )
			throws NativeException, IllegalAccessException
	{
		Native mciGetErrorString = new Native( DLL_NAME, "mciGetErrorStringA" );
		mciGetErrorString.setRetVal( Type.INT );
		Pointer lpstrReturnString = new Pointer( MemoryBlockFactory.createMemoryBlock( RETURN_LENGTH ) );
		try
		{
			int pos = 0;
			mciGetErrorString.setParameter( pos++, dwError );
			mciGetErrorString.setParameter( pos++, lpstrReturnString );
			mciGetErrorString.setParameter( pos++, RETURN_LENGTH );
			mciGetErrorString.invoke( );
			if ( mciGetErrorString.getRetValAsInt( ) == 0 )
			{
				return lpstrReturnString.getAsString( );
			}
			else
			{
				throw new RuntimeException( "mciGetErrorString failed with code "
						+ mciGetErrorString.getRetValAsInt( ) );
			}
		}
		finally
		{
			lpstrReturnString.dispose( );
		}
	}

	public static final void OpenTray( String driveLetter )
			throws NativeException, IllegalAccessException
	{
		final String message = "open "
				+ driveLetter
				+ " type cdaudio alias cdaudio";
		mciSendString( message, null );
		mciSendString( "set CDAudio door open", null );
	}

	public static final void CloseTray( String driveLetter )
			throws NativeException, IllegalAccessException
	{
		mciSendString( "open " + driveLetter + " type cdaudio alias cdaudio",
				null );
		mciSendString( "set CDAudio door close", null );
	}
}
