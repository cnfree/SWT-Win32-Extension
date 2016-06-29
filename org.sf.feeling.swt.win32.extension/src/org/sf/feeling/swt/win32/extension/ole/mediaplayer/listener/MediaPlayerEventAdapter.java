
package org.sf.feeling.swt.win32.extension.ole.mediaplayer.listener;

import org.eclipse.swt.ole.win32.OleAutomation;
import org.sf.feeling.swt.win32.extension.ole.mediaplayer.WMPMediaState;
import org.sf.feeling.swt.win32.extension.ole.mediaplayer.WMPPlaylistChangeEventType;

public class MediaPlayerEventAdapter implements MediaPlayerEventListener
{

	public void openStateChange( WMPMediaState newState )
	{
		// TODO Auto-generated method stub
		
	}

	public void playStateChange( WMPMediaState newState )
	{
		// TODO Auto-generated method stub
		
	}

	public void audioLanguageChange( int langID )
	{
		// TODO Auto-generated method stub
		
	}

	public void statusChange( )
	{
		// TODO Auto-generated method stub
		
	}

	public void scriptCommand( String scType, String param )
	{
		// TODO Auto-generated method stub
		
	}

	public void newStream( )
	{
		// TODO Auto-generated method stub
		
	}

	public void disconnect( int result )
	{
		// TODO Auto-generated method stub
		
	}

	public void buffering( boolean start )
	{
		// TODO Auto-generated method stub
		
	}

	public void error( )
	{
		// TODO Auto-generated method stub
		
	}

	public void warning( int warningType, int param, String description )
	{
		// TODO Auto-generated method stub
		
	}

	public void endOfStream( int result )
	{
		// TODO Auto-generated method stub
		
	}

	public void positionChange( double oldPosition, double newPosition )
	{
		// TODO Auto-generated method stub
		
	}

	public void markerHit( int markerNum )
	{
		// TODO Auto-generated method stub
		
	}

	public void durationUnitChange( int newDurationUnit )
	{
		// TODO Auto-generated method stub
		
	}

	public void cdromMediaChange( int cdromNum )
	{
		// TODO Auto-generated method stub
		
	}

	public void playlistChange( OleAutomation playlist,
			WMPPlaylistChangeEventType change )
	{
		// TODO Auto-generated method stub
		
	}

	public void currentPlaylistChange( WMPPlaylistChangeEventType change )
	{
		// TODO Auto-generated method stub
		
	}

	public void currentPlaylistItemAvailable( String itemName )
	{
		// TODO Auto-generated method stub
		
	}

	public void mediaChange( OleAutomation item )
	{
		// TODO Auto-generated method stub
		
	}

	public void currentMediaItemAvailable( String itemName )
	{
		// TODO Auto-generated method stub
		
	}

	public void currentItemChange( OleAutomation pdispMedia )
	{
		// TODO Auto-generated method stub
		
	}

	public void mediaCollectionChange( )
	{
		// TODO Auto-generated method stub
		
	}

	public void mediaCollectionAttributeStringAdded( String attribName,
			String attribVal )
	{
		// TODO Auto-generated method stub
		
	}

	public void mediaCollectionAttributeStringRemoved( String attribName,
			String attribVal )
	{
		// TODO Auto-generated method stub
		
	}

	public void mediaCollectionAttributeStringChanged( String attribName,
			String oldAttribVal, String newAttribVal )
	{
		// TODO Auto-generated method stub
		
	}

	public void playlistCollectionChange( )
	{
		// TODO Auto-generated method stub
		
	}

	public void playlistCollectionPlaylistAdded( String playlistName )
	{
		// TODO Auto-generated method stub
		
	}

	public void playlistCollectionPlaylistRemoved( String playlistName )
	{
		// TODO Auto-generated method stub
		
	}

	public void playlistCollectionPlaylistSetAsDeleted( String playlistName,
			boolean isDeleted )
	{
		// TODO Auto-generated method stub
		
	}

	public void modeChange( String modeName, boolean newValue )
	{
		// TODO Auto-generated method stub
		
	}

	public void mediaError( OleAutomation pMediaObject )
	{
		// TODO Auto-generated method stub
		
	}

	public void openPlaylistSwitch( OleAutomation item )
	{
		// TODO Auto-generated method stub
		
	}

	public void domainChange( String domain )
	{
		// TODO Auto-generated method stub
		
	}

	public void switchedToPlayerApplication( )
	{
		// TODO Auto-generated method stub
		
	}

	public void switchedToControl( )
	{
		// TODO Auto-generated method stub
		
	}

	public void playerDockedStateChange( )
	{
		// TODO Auto-generated method stub
		
	}

	public void playerReconnect( )
	{
		// TODO Auto-generated method stub
		
	}

	public void click( short nButton, short nShiftState, int fX, int fY )
	{
		// TODO Auto-generated method stub
		
	}

	public void doubleClick( short nButton, short nShiftState, int fX, int fY )
	{
		// TODO Auto-generated method stub
		
	}

	public void keyDown( short nKeyCode, short nShiftState )
	{
		// TODO Auto-generated method stub
		
	}

	public void keyPress( short nKeyAscii )
	{
		// TODO Auto-generated method stub
		
	}

	public void keyUp( short nKeyCode, short nShiftState )
	{
		// TODO Auto-generated method stub
		
	}

	public void mouseDown( short nButton, short nShiftState, int fX, int fY )
	{
		// TODO Auto-generated method stub
		
	}

	public void mouseMove( short nButton, short nShiftState, int fX, int fY )
	{
		// TODO Auto-generated method stub
		
	}

	public void mouseUp( short nButton, short nShiftState, int fX, int fY )
	{
		// TODO Auto-generated method stub
		
	}


}
