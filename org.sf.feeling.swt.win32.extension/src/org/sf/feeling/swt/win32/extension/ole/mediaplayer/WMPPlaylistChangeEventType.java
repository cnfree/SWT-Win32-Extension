
package org.sf.feeling.swt.win32.extension.ole.mediaplayer;

import java.util.HashMap;
import java.util.Map;

public class WMPPlaylistChangeEventType
{

	private static Map eventTypeMap = new HashMap( );
	private int type;
	private String message;

	private WMPPlaylistChangeEventType( int eventType, String message )
	{
		this.type = eventType;
		this.message = message;
		eventTypeMap.put( new Integer( eventType ), this );
	}

	public int getEventType( )
	{
		return type;
	}

	public String getMessage( )
	{
		return message;
	}

	public static WMPPlaylistChangeEventType UNKNOWN = new WMPPlaylistChangeEventType( 0,
			"UNKNOWN" );
	public static WMPPlaylistChangeEventType CLEAR = new WMPPlaylistChangeEventType( 1,
			"CLEAR" );
	public static WMPPlaylistChangeEventType INFOCHANGE = new WMPPlaylistChangeEventType( 2,
			"INFOCHANGE" );
	public static WMPPlaylistChangeEventType MOVE = new WMPPlaylistChangeEventType( 3,
			"MOVE" );
	public static WMPPlaylistChangeEventType DELETE = new WMPPlaylistChangeEventType( 4,
			"DELETE" );
	public static WMPPlaylistChangeEventType INSERT = new WMPPlaylistChangeEventType( 5,
			"INSERT" );
	public static WMPPlaylistChangeEventType APPEND = new WMPPlaylistChangeEventType( 6,
			"APPEND" );
	public static WMPPlaylistChangeEventType PRIVATE = new WMPPlaylistChangeEventType( 7,
			"PRIVATE" );
	public static WMPPlaylistChangeEventType NAMECHANGE = new WMPPlaylistChangeEventType( 8,
			"NAMECHANGE" );
	public static WMPPlaylistChangeEventType MORPH = new WMPPlaylistChangeEventType( 9,
			"MORPH" );
	public static WMPPlaylistChangeEventType SORT = new WMPPlaylistChangeEventType( 10,
			"SORT" );
	public static WMPPlaylistChangeEventType LAST = new WMPPlaylistChangeEventType( 11,
			"LAST" );

	public static WMPPlaylistChangeEventType getEventType( int eventType )
	{
		WMPPlaylistChangeEventType type = (WMPPlaylistChangeEventType) eventTypeMap.get( new Integer( eventType ) );
		if ( type == null )
			type = UNKNOWN;
		return type;
	}

}
