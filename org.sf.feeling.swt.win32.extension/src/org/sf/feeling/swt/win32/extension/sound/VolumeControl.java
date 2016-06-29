
package org.sf.feeling.swt.win32.extension.sound;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.sf.feeling.swt.win32.extension.sound.hook.MixerMsgHook;

/**
 * <b>Important</b>:Can't use VolumeControl on the Windows Vista.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class VolumeControl
{

	private static final int deviceId = 0;

	public static final int MAX_VOL_VALUE = Mixer.MAX_VOL_VALUE;

	private static MixerMsgHook hook;

	public static boolean openControl( Shell shell )
	{
		boolean result = Mixer.mixerOpen( shell.handle, deviceId );
		hook = new MixerMsgHook( shell );
		hook.installHook( );
		shell.addDisposeListener( new DisposeListener( ) {

			public void widgetDisposed( DisposeEvent e )
			{
				colseControl( );
			}
		} );
		return result;
	}

	public static boolean isControlOpened( )
	{
		return Mixer.getMixerHandle( deviceId ) != 0;
	}

	public static void addChangeListener( Listener listener )
	{
		if ( hook != null )
			hook.addChangeListener( listener );
	}

	public static void removeChangeListener( Listener listener )
	{
		if ( hook != null )
			hook.removeChangeListener( listener );
	}

	public static boolean colseControl( )
	{
		if ( hook != null )
			hook.unInstallHook( );
		return Mixer.mixerClose( deviceId );
	}

	public static int getVolume( )
	{
		return Mixer.getMasterVolume( deviceId );
	}

	public static boolean setVolume( int volume )
	{
		return Mixer.setMasterVolume( deviceId, volume );
	}

	public static float getBalance( )
	{
		return Mixer.getMasterBalance( deviceId );
	}

	public static boolean setBalance( float balance )
	{
		return Mixer.setMasterBalance( deviceId, balance );
	}

	public static float getLeftChannelVolume( )
	{
		return Mixer.getMasterLeftChannelVolume( deviceId );
	}

	public static boolean setLeftChannelVolume( int value )
	{
		return Mixer.setMasterLeftChannelVolume( deviceId, value );
	}

	public static float getRightChannelVolume( )
	{
		return Mixer.getMasterRightChannelVolume( deviceId );
	}

	public static boolean setRightChannelVolume( int value )
	{
		return Mixer.setMasterRightChannelVolume( deviceId, value );
	}

	public static boolean isMono( )
	{
		return Mixer.isPlaybackMixerMono( deviceId );
	}

	public static boolean isMute( )
	{
		return Mixer.isMasterMute( deviceId );
	}

	public static boolean setMute( boolean mute )
	{
		return Mixer.setMasterMute( deviceId, mute );
	}

	public static int getMicrophoneVolume( )
	{
		return Mixer.getMicrophoneVolume( deviceId );
	}

	public static boolean setMicrophoneVolume( int value )
	{
		return Mixer.setMicrophoneVolume( deviceId, value );
	}

	public static boolean isMicrophoneMute( )
	{
		return Mixer.isMicrophoneMute( deviceId );
	}

	public static boolean setMicrophoneMute( boolean mute )
	{
		return Mixer.setMicrophoneMute( deviceId, mute );
	}

}
