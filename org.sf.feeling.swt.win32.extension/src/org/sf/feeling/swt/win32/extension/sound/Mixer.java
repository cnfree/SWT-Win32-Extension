
package org.sf.feeling.swt.win32.extension.sound;

import java.util.HashMap;

import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.MIXERCAPS;
import org.sf.feeling.swt.win32.internal.extension.MIXERCAPSA;
import org.sf.feeling.swt.win32.internal.extension.MIXERCAPSW;
import org.sf.feeling.swt.win32.internal.extension.MIXERHANDLE;
import org.sf.feeling.swt.win32.internal.extension.MIXERLINEA;
import org.sf.feeling.swt.win32.internal.extension.MIXERLINEW;
import org.sf.feeling.swt.win32.internal.extension.MIXERVOLUME;

/**
 * <b>Important</b>: Vista doesn't support Mixer.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class Mixer
{

	public static final int MMSYSERR_NOERROR = 0;

	public static final int CALLBACK_WINDOW = 0x10000;

	public static final int MIXER_OBJECTF_MIXER = 0;

	public static final int MIXERLINE_COMPONENTTYPE_DST_FIRST = 0x0;

	public static final int MIXERLINE_COMPONENTTYPE_SRC_FIRST = 0x1000;

	private static final int MIXERLINE_COMPONENTTYPE_SRC_LAST = ( MIXERLINE_COMPONENTTYPE_SRC_FIRST + 10 );

	public static final int NO_SOURCE = MIXERLINE_COMPONENTTYPE_SRC_LAST + 1;

	public static final int MIXERLINE_COMPONENTTYPE_DST_SPEAKERS = ( MIXERLINE_COMPONENTTYPE_DST_FIRST + 4 );

	public static final int MIXERLINE_COMPONENTTYPE_DST_WAVEIN = ( MIXERLINE_COMPONENTTYPE_DST_FIRST + 7 );

	public static final int MIXERLINE_COMPONENTTYPE_SRC_MICROPHONE = ( MIXERLINE_COMPONENTTYPE_SRC_FIRST + 3 );

	public static final int MIXERLINE_COMPONENTTYPE_SRC_LINE = ( MIXERLINE_COMPONENTTYPE_SRC_FIRST + 2 );

	public static final int MIXERLINE_COMPONENTTYPE_SRC_COMPACTDISC = ( MIXERLINE_COMPONENTTYPE_SRC_FIRST + 5 );

	public static final int MIXERLINE_COMPONENTTYPE_SRC_SYNTHESIZER = ( MIXERLINE_COMPONENTTYPE_SRC_FIRST + 4 );

	public static final int MIXERLINE_COMPONENTTYPE_SRC_WAVEOUT = ( MIXERLINE_COMPONENTTYPE_SRC_FIRST + 8 );

	public static final int TYPE_VOLUMECONTROL = MIXERLINE_COMPONENTTYPE_DST_SPEAKERS;

	public static final int TYPE_WAVE = MIXERLINE_COMPONENTTYPE_SRC_WAVEOUT;

	public static final int TYPE_MIDI = MIXERLINE_COMPONENTTYPE_SRC_SYNTHESIZER;

	public static final int TYPE_CDPLAYER = MIXERLINE_COMPONENTTYPE_SRC_COMPACTDISC;

	public static final int TYPE_MICROPHONE = MIXERLINE_COMPONENTTYPE_SRC_MICROPHONE;

	public static final int TYPE_LINEIN = MIXERLINE_COMPONENTTYPE_SRC_LINE;

	public static final int MIXER_OBJECTF_HANDLE = 0x80000000;

	public static final int MIXER_OBJECTF_HMIXER = ( MIXER_OBJECTF_HANDLE | MIXER_OBJECTF_MIXER );

	public static final int MIXER_GETLINEINFOF_DESTINATION = 0x00000000;

	public static final int MIXER_GETLINEINFOF_SOURCE = 0x00000001;

	public static final int MIXER_GETLINEINFOF_LINEID = 0x00000002;

	public static final int MIXER_GETLINEINFOF_COMPONENTTYPE = 0x00000003;

	public static final int MIXER_GETLINEINFOF_TARGETTYPE = 0x00000004;

	public static final int MAX_VOL_VALUE = 65535;

	private static HashMap mixerMap = new HashMap( );

	public static int mixerGetNumDevs( )
	{
		return Extension.MixerGetNumDevs( );
	}

	public static boolean mixerOpen( int hWnd, int deviceId )
	{
		int dwFlags = MIXER_OBJECTF_MIXER;
		if ( hWnd != 0 )
			dwFlags |= CALLBACK_WINDOW;
		MIXERHANDLE mixer = new MIXERHANDLE( );
		boolean result = Extension.MixerOpen( mixer, deviceId, hWnd, 0, dwFlags ) == MMSYSERR_NOERROR;
		if ( result )
			mixerMap.put( new Integer( deviceId ), new Integer( mixer.hMixer ) );
		return result;
	}

	public static boolean mixerClose( int deviceId )
	{
		if ( mixerMap.containsKey( new Integer( deviceId ) ) )
		{
			int hMixer = ( (Integer) mixerMap.get( new Integer( deviceId ) ) ).intValue( );
			boolean result = Extension.MixerClose( hMixer ) == MMSYSERR_NOERROR;
			if ( result )
			{
				mixerMap.remove( new Integer( deviceId ) );
			}
			return result;
		}
		return false;
	}

	public static int getMixerHandle( int deviceId )
	{
		if ( mixerMap.containsKey( new Integer( deviceId ) ) )
		{
			return ( (Integer) mixerMap.get( new Integer( deviceId ) ) ).intValue( );
		}
		return 0;
	}

	private static boolean mixerGetDevCaps( int deviceId, MIXERCAPS mixerCaps )
	{
		int hMixer = getMixerHandle( deviceId );
		if ( hMixer == 0 )
			return false;
		boolean result = false;
		if ( Extension.IsUnicode )
		{
			result = Extension.MixerGetDevCapsW( deviceId,
					(MIXERCAPSW) mixerCaps ) == MMSYSERR_NOERROR;
		}
		else
		{
			result = Extension.MixerGetDevCapsA( deviceId,
					(MIXERCAPSA) mixerCaps ) == MMSYSERR_NOERROR;
		}
		return result;
	}

	private static boolean mixerGetLineInfo( int deviceId, MixerLine mixerLine,
			int dwComponentType )
	{
		int hMixer = getMixerHandle( deviceId );
		if ( hMixer == 0 )
			return false;
		boolean result = false;
		if ( Extension.IsUnicode )
		{
			result = Extension.MixerGetLineInfoW( deviceId,
					(MIXERLINEW) mixerLine.mixerLine,
					dwComponentType ) == MMSYSERR_NOERROR;
		}
		else
		{
			result = Extension.MixerGetLineInfoA( deviceId,
					(MIXERLINEA) mixerLine.mixerLine,
					dwComponentType ) == MMSYSERR_NOERROR;
		}
		return result;
	}

	public static MixerCaps getMixerCaps( int deviceId )
	{
		MIXERCAPS mixerCaps = null;
		if ( Extension.IsUnicode )
			mixerCaps = new MIXERCAPSW( );
		else
			mixerCaps = new MIXERCAPSA( );
		if ( mixerGetDevCaps( deviceId, mixerCaps ) )
			return new MixerCaps( mixerCaps );
		return null;
	}

	public static boolean getMixerLine( int deviceId, MixerLine mixerLine,
			int dwComponentType )
	{
		return mixerGetLineInfo( deviceId, mixerLine, dwComponentType );
	}

	public static MixerLine getPlaybackMixerLine( int deviceId )
	{
		MixerLine mixerLine = new MixerLine( );
		mixerLine.setCbStruct( MixerLine.sizeof );
		mixerLine.setDwComponentType( MIXERLINE_COMPONENTTYPE_DST_SPEAKERS );
		if ( getMixerLine( deviceId, mixerLine, MIXER_OBJECTF_HMIXER
				| MIXER_GETLINEINFOF_COMPONENTTYPE ) )
			return mixerLine;
		else
			return null;
	}

	public static boolean isPlaybackDevice( int deviceId )
	{
		return getPlaybackMixerLine( deviceId ) != null;
	}

	public static MixerLine getRecordingMixerLine( int deviceId )
	{
		MixerLine mixerLine = new MixerLine( );
		mixerLine.setCbStruct( MixerLine.sizeof );
		mixerLine.setDwComponentType( MIXERLINE_COMPONENTTYPE_DST_WAVEIN );
		if ( getMixerLine( deviceId, mixerLine, MIXER_OBJECTF_HMIXER
				| MIXER_GETLINEINFOF_COMPONENTTYPE ) )
			return mixerLine;
		else
			return null;
	}

	public static boolean isRecordingDevice( int deviceId )
	{
		return getRecordingMixerLine( deviceId ) != null;
	}

	public static int getNumDestinationLine( int deviceId )
	{
		MixerCaps mixerCaps = getMixerCaps( deviceId );
		if ( mixerCaps != null )
		{
			return mixerCaps.getCDestinations( );
		}
		else
			return 0;
	}

	public static int getMasterVolume( int deviceId )
	{
		if ( !isPlaybackMixerMono( deviceId ) )
		{
			return Math.max( getMasterRightChannelVolume( deviceId ),
					getMasterLeftChannelVolume( deviceId ) );

		}
		else
		{
			return getMasterLeftChannelVolume( deviceId );
		}
	}

	public static boolean setMasterVolume( int deviceId, int value )
	{
		return setMixerVolume( deviceId, value, TYPE_VOLUMECONTROL );
	}

	public static boolean setMixerVolume( int deviceId, int value, int srcType )
	{
		if ( !isScrTypeMono( deviceId,
				MIXERLINE_COMPONENTTYPE_DST_SPEAKERS,
				srcType ) )
		{
			float balance = getPlaybackVolumeBalance( deviceId, srcType );
			if ( balance > 0 )
			{
				return setPlaybackVolume( deviceId,
						(int) ( value * ( 1.0f - balance ) ),
						value,
						srcType,
						false );
			}
			else
			{
				return setPlaybackVolume( deviceId,
						value,
						(int) ( value * ( 1.0f + balance ) ),
						srcType,
						false );
			}
		}
		else
		{
			MIXERVOLUME volume = new MIXERVOLUME( );
			volume.leftChannelVolume = value;
			return Extension.SetPlaybackVolume( deviceId, srcType, volume, true );
		}
	}

	public static int getMixerVolume( int deviceId, int srcType )
	{
		if ( !isScrTypeMono( deviceId,
				MIXERLINE_COMPONENTTYPE_DST_SPEAKERS,
				srcType ) )
		{
			return Math.max( getRightChannelVolume( deviceId, srcType ),
					getLeftChannelVolume( deviceId, srcType ) );

		}
		else
		{
			return getLeftChannelVolume( deviceId, srcType );
		}
	}

	public static boolean isPlaybackMixerMono( int deviceId )
	{
		MixerLine mixerLine = Mixer.getPlaybackMixerLine( deviceId );
		if ( mixerLine == null )
			return true;
		return mixerLine.getCChannels( ) <= 1;
	}

	public static boolean isRecordingMixerMono( int deviceId )
	{
		MixerLine mixerLine = Mixer.getRecordingMixerLine( deviceId );
		if ( mixerLine == null )
			return true;
		return mixerLine.getCChannels( ) <= 1;
	}

	public static boolean isScrTypeMono( int deviceId, int dstType, int srcType )
	{
		return getMixerLineChannels( deviceId, dstType, srcType ) < 2;
	}

	public static int getMasterLeftChannelVolume( int deviceId )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		if ( Extension.GetPlaybackVolume( deviceId,
				TYPE_VOLUMECONTROL,
				volume,
				isPlaybackMixerMono( deviceId ) ) )
		{
			return volume.leftChannelVolume;
		}
		else
			return 0;
	}

	public static int getMasterRightChannelVolume( int deviceId )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		if ( Extension.GetPlaybackVolume( deviceId,
				TYPE_VOLUMECONTROL,
				volume,
				isPlaybackMixerMono( deviceId ) ) )
		{
			return volume.rightChannelVolume;
		}
		else
			return 0;
	}

	public static int getLeftChannelVolume( int deviceId, int srcType )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		if ( Extension.GetPlaybackVolume( deviceId,
				srcType,
				volume,
				isPlaybackMixerMono( deviceId ) ) )
		{
			return volume.leftChannelVolume;
		}
		else
			return 0;
	}

	public static int getRightChannelVolume( int deviceId, int srcType )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		if ( Extension.GetPlaybackVolume( deviceId,
				srcType,
				volume,
				isPlaybackMixerMono( deviceId ) ) )
		{
			return volume.rightChannelVolume;
		}
		else
			return 0;
	}

	public static int[] getPlaybackVolume( int deviceId, int srcType,
			boolean mono )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		if ( Extension.GetPlaybackVolume( deviceId, srcType, volume, mono ) )
		{
			return new int[]{
					volume.leftChannelVolume, volume.rightChannelVolume
			};
		}
		else
			return new int[0];
	}

	public static float getMasterBalance( int deviceId )
	{
		return getPlaybackVolumeBalance( deviceId, TYPE_VOLUMECONTROL );
	}

	public static boolean setMasterBalance( int deviceId, float balance )
	{
		return setPlaybackVolumeBalance( deviceId, TYPE_VOLUMECONTROL, balance );
	}

	public static float getPlaybackVolumeBalance( int deviceId, int srcType )
	{
		int dwMax;

		int[] volume = getPlaybackVolume( deviceId, srcType, false );

		if ( volume.length < 2 )
			return 0;
		
		if ( volume[0] == 0 && volume[1] == 0 )
		{
			return 0;
		}

		if ( volume[0] < volume[1] )
			dwMax = volume[1];
		else
			dwMax = volume[0];

		return ( (float) volume[1] - (float) volume[0] ) / (float) dwMax;
	}

	public static float getRecordingVolumeBalance( int deviceId, int srcType )
	{
		int dwMax;

		int[] volume = getRecordingVolume( deviceId,
				srcType,
				isRecordingMixerMono( deviceId ) );

		if(volume.length < 2)
			return 0;
		
		if ( volume[0] == 0 && volume[1] == 0 )
		{
			return 0;
		}

		if ( volume[0] < volume[1] )
			dwMax = volume[1];
		else
			dwMax = volume[0];

		return ( (float) volume[1] - (float) volume[0] ) / (float) dwMax;
	}

	public static boolean setPlaybackVolumeBalance( int deviceId, int srcType,
			float balance )
	{
		if ( getMixerHandle( deviceId ) == 0 )
			return false;

		int[] volume = getPlaybackVolume( deviceId, srcType, true );

		if ( volume.length < 2 )
			return false;

		int[] newVolume = computingBalance( volume[0], volume[1], balance );

		return setPlaybackVolume( deviceId,
				newVolume[0],
				newVolume[1],
				srcType,
				isPlaybackMixerMono( deviceId ) );

	}

	public static boolean setRecordingVolumeBalance( int deviceId, int srcType,
			float banlance )
	{
		if ( getMixerHandle( deviceId ) == 0 )
			return false;

		int[] volume = getRecordingVolume( deviceId,
				srcType,
				isRecordingMixerMono( deviceId ) );

		if(volume.length < 2)
			return false;
		
		int[] newVolume = computingBalance( volume[0], volume[1], banlance );

		return setRecordingVolume( deviceId,
				newVolume[0],
				newVolume[1],
				srcType,
				isRecordingMixerMono( deviceId ) );

	}

	private static int[] computingBalance( int dwLeft, int dwRight,
			float flBalance )
	{
		int dwMax;

		if ( dwLeft < dwRight )
			dwMax = dwRight;
		else
			dwMax = dwLeft;

		if ( flBalance > 0 )
		{
			dwRight = dwMax;
			dwLeft = (int) ( (float) dwMax * ( 1.0f - flBalance ) );
		}
		else if ( flBalance < 0 )
		{
			dwLeft = dwMax;
			dwRight = (int) ( (float) dwMax * ( 1.0f + flBalance ) );
		}
		else
		{
			dwLeft = dwMax;
			dwRight = dwMax;
		}

		return new int[]{
				dwLeft, dwRight
		};
	}

	public static boolean setMasterLeftChannelVolume( int deviceId, int value )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		volume.leftChannelVolume = value;
		volume.rightChannelVolume = getMasterRightChannelVolume( deviceId );
		return Extension.SetPlaybackVolume( deviceId,
				TYPE_VOLUMECONTROL,
				volume,
				isPlaybackMixerMono( deviceId ) );
	}

	public static boolean setMasterRightChannelVolume( int deviceId, int value )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		volume.leftChannelVolume = getMasterLeftChannelVolume( deviceId );
		volume.rightChannelVolume = value;
		return Extension.SetPlaybackVolume( deviceId,
				TYPE_VOLUMECONTROL,
				volume,
				isPlaybackMixerMono( deviceId ) );
	}

	public static boolean setLeftChannelVolume( int deviceId, int srcType,
			int value )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		volume.leftChannelVolume = value;
		volume.rightChannelVolume = getMasterRightChannelVolume( deviceId );
		return Extension.SetPlaybackVolume( deviceId,
				srcType,
				volume,
				isPlaybackMixerMono( deviceId ) );
	}

	public static boolean setRightChannelVolume( int deviceId, int srcType,
			int value )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		volume.leftChannelVolume = getMasterLeftChannelVolume( deviceId );
		volume.rightChannelVolume = value;
		return Extension.SetPlaybackVolume( deviceId,
				srcType,
				volume,
				isPlaybackMixerMono( deviceId ) );
	}

	public static boolean setPlaybackVolume( int deviceId, int leftValue,
			int rightValue, int srcType, boolean mono )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		volume.leftChannelVolume = leftValue;
		volume.rightChannelVolume = rightValue;
		return Extension.SetPlaybackVolume( deviceId, srcType, volume, mono );
	}

	public static boolean setMicrophoneVolume( int deviceId, int value )
	{
		return setPlaybackVolume( deviceId,
				TYPE_MICROPHONE,
				value,
				0,
				isPlaybackMixerMono( deviceId ) );
	}

	public static int getMicrophoneVolume( int deviceId )
	{
		int[] volume = getPlaybackVolume( deviceId,
				TYPE_MICROPHONE,
				isPlaybackMixerMono( deviceId ) );
		if ( volume.length == 0 )
			return 0;
		return volume[0];
	}

	public static boolean setMicrophoneRecordingVolume( int deviceId, int value )
	{
		return setRecordingVolume( deviceId,
				TYPE_MICROPHONE,
				value,
				0,
				isRecordingMixerMono( deviceId ) );
	}

	public static boolean setRecordingVolume( int deviceId, int leftValue,
			int rightValue, int srcType, boolean mono )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		volume.leftChannelVolume = leftValue;
		volume.rightChannelVolume = rightValue;
		return Extension.SetRecordingVolume( deviceId, srcType, volume, mono );
	}

	public static int getMicrophoneRecordingVolume( int deviceId )
	{
		int[] volume = getRecordingVolume( deviceId,
				TYPE_MICROPHONE,
				isRecordingMixerMono( deviceId ) );
		if(volume.length == 0)
			return 0;
		return volume[0];
	}

	public static int[] getRecordingVolume( int deviceId, int srcType,
			boolean mono )
	{
		MIXERVOLUME volume = new MIXERVOLUME( );
		if ( Extension.GetRecordingVolume( deviceId, srcType, volume, mono ) )
		{
			return new int[]{
					volume.leftChannelVolume, volume.rightChannelVolume
			};
		}
		else
			return new int[0];
	}

	public static boolean setPlaybackMute( int deviceId, int srcType,
			boolean value )
	{
		return Extension.SetMixerMute( deviceId,
				MIXERLINE_COMPONENTTYPE_DST_SPEAKERS,
				srcType,
				value );
	}

	public static boolean setMasterMute( int deviceId, boolean value )
	{
		return Extension.SetMixerMasterMute( getMixerHandle( deviceId ), value );
	}

	public static boolean isMasterMute( int deviceId )
	{
		return Extension.IsMixerMasterMute( getMixerHandle( deviceId ) );
	}

	public static boolean isPlaybackMute( int deviceId, int srcType )
	{
		return Extension.IsMixerMute( deviceId,
				MIXERLINE_COMPONENTTYPE_DST_SPEAKERS,
				srcType );
	}

	public static boolean setVolumePlaybackMute( int deviceId, boolean value )
	{
		return setPlaybackMute( deviceId, TYPE_VOLUMECONTROL, value );
	}

	public static boolean isVolumeMute( int deviceId )
	{
		return isPlaybackMute( deviceId, TYPE_VOLUMECONTROL );
	}

	public static boolean setMicrophoneMute( int deviceId, boolean value )
	{
		return setPlaybackMute( deviceId, TYPE_MICROPHONE, value );
	}

	public static boolean isMicrophoneMute( int deviceId )
	{
		return isPlaybackMute( deviceId, TYPE_MICROPHONE );
	}

	public static int getDestinLineIndex( int deviceId, MixerLine line,
			int dstType )
	{
		// ----- find out how many destination lines are available -----

		MixerCaps caps = getMixerCaps( deviceId );
		if ( caps == null )
		{
			return -1;
		}

		int ndest = caps.getCDestinations( );
		for ( int i = 0; i < ndest; i++ )
		{
			line.setCbStruct( MixerLine.sizeof );
			line.setDwSource( 0 );
			line.setDwDestination( i );
			if ( !mixerGetLineInfo( deviceId,
					line,
					MIXER_GETLINEINFOF_DESTINATION ) )
			{
				return -1;
			}

			if ( line.getDwComponentType( ) == dstType )
			{
				return ( i );
			}
		}
		return -1;
	}

	public static int getSourceLineIndex( int deviceId, MixerLine line,
			int srcType )
	{
		int nconn = line.getCConnections( );
		int dstIndex = line.getDwDestination( );

		for ( int j = 0; j < nconn; j++ )
		{
			line.setCbStruct( MixerLine.sizeof );
			line.setDwSource( j );
			line.setDwDestination( dstIndex );
			if ( !mixerGetLineInfo( deviceId, line, MIXER_GETLINEINFOF_SOURCE ) )
			{
				return -1;
			}

			if ( line.getDwComponentType( ) == srcType )
			{
				return ( j );
			}
		}
		return -1;
	}

	public static int getMixerLineChannels( int deviceId, int dstType,
			int srcType )
	{
		MixerLine mixerLine = new MixerLine( );
		if ( getDestinLineIndex( deviceId, mixerLine, dstType ) > -1
				&& ( srcType == TYPE_VOLUMECONTROL || getSourceLineIndex( deviceId,
						mixerLine,
						srcType ) > -1 ) )
			return mixerLine.getCChannels( );
		else
			return 0;
	}

	public static boolean containControlType( int deviceId, int dstType,
			int srcType )
	{
		MixerLine mixerLine = new MixerLine( );
		if ( getDestinLineIndex( deviceId, mixerLine, dstType ) == -1
				|| getSourceLineIndex( deviceId, mixerLine, srcType ) == -1 )
			return false;
		else
			return true;
	}
}
