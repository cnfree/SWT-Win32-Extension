
package org.sf.feeling.swt.win32.extension.ole.mediaplayer;

import java.util.HashMap;
import java.util.Map;


public class WMPMediaState
{

	private static Map stateTypeMap = new HashMap( );
	private int state;
	private String message;

	private WMPMediaState( int state, String message )
	{
		this.state = state;
		this.message = message;
		stateTypeMap.put( new Integer( state ), this );
	}

	public int getState( )
	{
		return state;
	}

	public String getMessage( )
	{
		return message;
	}

	public static WMPMediaState UNDEFINED = new WMPMediaState( 0, "UNDEFINED" );
	public static WMPMediaState STOPPED = new WMPMediaState( 1, "STOPPED" );
	public static WMPMediaState PAUSED = new WMPMediaState( 2, "PAUSED" );
	public static WMPMediaState PLAYING = new WMPMediaState( 3, "PLAYING" );
	public static WMPMediaState SCAN_FORWARD = new WMPMediaState( 4,
			"SCAN_FORWARD" );
	public static WMPMediaState SCAN_REVERSE = new WMPMediaState( 5,
			"SCAN_REVERSE" );
	public static WMPMediaState BUFFERING = new WMPMediaState( 6, "BUFFERING" );
	public static WMPMediaState WAITING = new WMPMediaState( 7, "WAITING" );
	public static WMPMediaState MEDIA_ENDED = new WMPMediaState( 8,
			"MEDIA_ENDED" );
	public static WMPMediaState TRANSITIONING = new WMPMediaState( 9,
			"TRANSITIONING" );
	public static WMPMediaState READY = new WMPMediaState( 10, "READY" );
	public static WMPMediaState RECONNECTING = new WMPMediaState( 11,
			"RECONNECTING" );
	
	public static WMPMediaState getMediaState( int state )
	{
		WMPMediaState mediaState = (WMPMediaState) stateTypeMap.get( new Integer( state ) );
		if( mediaState == null)
			mediaState = UNDEFINED;
		return mediaState;
	}
}
