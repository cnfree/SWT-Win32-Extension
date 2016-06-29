/*******************************************************************************
 * Copyright (c) 2007 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http:/**www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/

package org.sf.feeling.swt.win32.extension.ole.mediaplayer.listener;

import org.eclipse.swt.ole.win32.OleAutomation;
import org.sf.feeling.swt.win32.extension.ole.mediaplayer.WMPMediaState;
import org.sf.feeling.swt.win32.extension.ole.mediaplayer.WMPPlaylistChangeEventType;

/**
 * Media Player event listener
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public interface MediaPlayerEventListener
{

	/**
	 * Sent when the control changes OpenState
	 */
	void openStateChange( WMPMediaState newState );

	/**
	 * Sent when the control changes PlayState
	 */
	void playStateChange( WMPMediaState newState );

	/**
	 * Sent when the audio language changes
	 */
	void audioLanguageChange( int langID );

	/**
	 * Sent when the status string changes
	 */
	void statusChange( );

	/**
	 * Sent when a synchronized command or URL is received
	 */
	void scriptCommand( String scType, String param );

	/**
	 * Sent when a new stream is encountered (obsolete)
	 */
	void newStream( );

	/**
	 * Sent when the control is disconnected from the server (obsolete)
	 */
	void disconnect( int result );

	/**
	 * Sent when the control begins or ends buffering
	 */
	void buffering( boolean start );

	/**
	 * Sent when the control has an error condition
	 */
	void error( );

	/**
	 * Sent when the control has an warning condition (obsolete)
	 */
	void warning( int warningType, int param, String description );

	/**
	 * Sent when the media has reached end of stream
	 */
	void endOfStream( int result );

	/**
	 * Indicates that the current position of the movie has changed
	 */
	void positionChange( double oldPosition, double newPosition );

	/**
	 * Sent when a marker is reached
	 */
	void markerHit( int markerNum );

	/**
	 * Indicates that the unit used to express duration and position has changed
	 */
	void durationUnitChange( int newDurationUnit );

	/**
	 * Indicates that the CD ROM media has changed
	 */
	void cdromMediaChange( int cdromNum );

	/**
	 * Sent when a playlist changes
	 */
	void playlistChange( OleAutomation playlist,
			WMPPlaylistChangeEventType change );

	/**
	 * Sent when the current playlist changes
	 */
	void currentPlaylistChange( WMPPlaylistChangeEventType change );

	/**
	 * Sent when a current playlist item becomes available
	 */
	void currentPlaylistItemAvailable( String itemName );

	/**
	 * Sent when a media object changes
	 */
	void mediaChange( OleAutomation item );

	/**
	 * Sent when a current media item becomes available
	 */
	void currentMediaItemAvailable( String itemName );

	/**
	 * Sent when the item selection on the current playlist changes
	 */
	void currentItemChange( OleAutomation pdispMedia );

	/**
	 * Sent when the media collection needs to be requeried
	 */
	void mediaCollectionChange( );

	/**
	 * Sent when an attribute string is added in the media collection
	 */
	void mediaCollectionAttributeStringAdded( String attribName,
			String attribVal );

	/**
	 * Sent when an attribute string is removed from the media collection
	 */
	void mediaCollectionAttributeStringRemoved( String attribName,
			String attribVal );

	/**
	 * Sent when an attribute string is changed in the media collection
	 */
	void mediaCollectionAttributeStringChanged( String attribName,
			String oldAttribVal, String newAttribVal );

	/**
	 * Sent when playlist collection needs to be requeried
	 */
	void playlistCollectionChange( );

	/**
	 * Sent when a playlist is added to the playlist collection
	 */
	void playlistCollectionPlaylistAdded( String playlistName );

	/**
	 * Sent when a playlist is removed from the playlist collection
	 */
	void playlistCollectionPlaylistRemoved( String playlistName );

	/**
	 * Sent when a playlist has been set or reset as deleted
	 */
	void playlistCollectionPlaylistSetAsDeleted( String playlistName,
			boolean isDeleted );

	/**
	 * Playlist playback mode has changed
	 */
	void modeChange( String modeName, boolean newValue );

	/**
	 * Sent when the media object has an error condition
	 */
	void mediaError( OleAutomation pMediaObject );

	/**
	 * Current playlist switch with no open state change
	 */
	void openPlaylistSwitch( OleAutomation item );

	/**
	 * Sent when the current DVD domain changes
	 */
	void domainChange( String domain );

	/**
	 * Sent when display switches to player application
	 */
	void switchedToPlayerApplication( );

	/**
	 * Sent when display switches to control
	 */
	void switchedToControl( );

	/**
	 * Sent when the player docks or undocks
	 */
	void playerDockedStateChange( );

	/**
	 * Sent when the OCX reconnects to the player
	 */
	void playerReconnect( );

	/**
	 * Occurs when a user clicks the mouse
	 */
	void click( short nButton, short nShiftState, int fX, int fY );

	/**
	 * Occurs when a user double-clicks the mouse
	 */
	void doubleClick( short nButton, short nShiftState, int fX, int fY );

	/**
	 * Occurs when a key is pressed
	 */
	void keyDown( short nKeyCode, short nShiftState );

	/**
	 * Occurs when a key is pressed and released
	 */
	void keyPress( short nKeyAscii );

	/**
	 * Occurs when a key is released
	 */
	void keyUp( short nKeyCode, short nShiftState );

	/**
	 * Occurs when a mouse button is pressed
	 */
	void mouseDown( short nButton, short nShiftState, int fX, int fY );

	/**
	 * Occurs when a mouse pointer is moved
	 */
	void mouseMove( short nButton, short nShiftState, int fX, int fY );

	/**
	 * Occurs when a mouse button is released
	 */
	void mouseUp( short nButton, short nShiftState, int fX, int fY );
}
