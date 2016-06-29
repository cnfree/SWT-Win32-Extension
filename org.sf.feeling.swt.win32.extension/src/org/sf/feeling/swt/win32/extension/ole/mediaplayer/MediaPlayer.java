
package org.sf.feeling.swt.win32.extension.ole.mediaplayer;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.OleEvent;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.OleListener;
import org.eclipse.swt.widgets.Composite;
import org.sf.feeling.swt.win32.extension.ole.OleContainer;
import org.sf.feeling.swt.win32.extension.ole.OleHookInterceptor;
import org.sf.feeling.swt.win32.extension.ole.OleMsgHook;
import org.sf.feeling.swt.win32.extension.ole.mediaplayer.listener.MediaPlayerEventListener;
import org.sf.feeling.swt.win32.extension.registry.RegistryKey;
import org.sf.feeling.swt.win32.extension.registry.RootKey;
import org.sf.feeling.swt.win32.internal.extension.util.ColorCache;

public class MediaPlayer extends OleContainer
{

	// ////////////////////////////////////////////////////////////////////
	// DIID_WMPCoreEvents
	// ////////////////////////////////////////////////////////////////////
	static final int WMPCOREEVENT_BASE = 5000;
	static final int DISPID_WMPCOREEVENT_OPENSTATECHANGE = ( WMPCOREEVENT_BASE + 1 );
	static final int DISPID_WMPCOREEVENT_STATUSCHANGE = ( WMPCOREEVENT_BASE + 2 );

	static final int WMPCOREEVENT_CONTROL_BASE = 5100;
	static final int DISPID_WMPCOREEVENT_PLAYSTATECHANGE = ( WMPCOREEVENT_CONTROL_BASE + 1 );
	static final int DISPID_WMPCOREEVENT_AUDIOLANGUAGECHANGE = ( WMPCOREEVENT_CONTROL_BASE + 2 );

	static final int WMPCOREEVENT_SEEK_BASE = 5200;
	static final int DISPID_WMPCOREEVENT_ENDOFSTREAM = ( WMPCOREEVENT_SEEK_BASE + 1 );
	static final int DISPID_WMPCOREEVENT_POSITIONCHANGE = ( WMPCOREEVENT_SEEK_BASE + 2 );
	static final int DISPID_WMPCOREEVENT_MARKERHIT = ( WMPCOREEVENT_SEEK_BASE + 3 );
	static final int DISPID_WMPCOREEVENT_DURATIONUNITCHANGE = ( WMPCOREEVENT_SEEK_BASE + 4 );

	static final int WMPCOREEVENT_CONTENT_BASE = 5300;
	static final int DISPID_WMPCOREEVENT_SCRIPTCOMMAND = ( WMPCOREEVENT_CONTENT_BASE + 1 );

	static final int WMPCOREEVENT_NETWORK_BASE = 5400;
	static final int DISPID_WMPCOREEVENT_DISCONNECT = ( WMPCOREEVENT_NETWORK_BASE + 1 );
	static final int DISPID_WMPCOREEVENT_BUFFERING = ( WMPCOREEVENT_NETWORK_BASE + 2 );
	static final int DISPID_WMPCOREEVENT_NEWSTREAM = ( WMPCOREEVENT_NETWORK_BASE + 3 );

	static final int WMPCOREEVENT_ERROR_BASE = 5500;
	static final int DISPID_WMPCOREEVENT_ERROR = ( WMPCOREEVENT_ERROR_BASE + 1 );

	static final int WMPCOREEVENT_WARNING_BASE = 5600;
	static final int DISPID_WMPCOREEVENT_WARNING = ( WMPCOREEVENT_WARNING_BASE + 1 );

	static final int WMPCOREEVENT_CDROM_BASE = 5700;
	static final int DISPID_WMPCOREEVENT_CDROMMEDIACHANGE = ( WMPCOREEVENT_CDROM_BASE + 1 );

	static final int WMPCOREEVENT_PLAYLIST_BASE = 5800;
	static final int DISPID_WMPCOREEVENT_PLAYLISTCHANGE = ( WMPCOREEVENT_PLAYLIST_BASE + 1 );
	static final int DISPID_WMPCOREEVENT_MEDIACHANGE = ( WMPCOREEVENT_PLAYLIST_BASE + 2 );
	static final int DISPID_WMPCOREEVENT_CURRENTMEDIAITEMAVAILABLE = ( WMPCOREEVENT_PLAYLIST_BASE + 3 );
	static final int DISPID_WMPCOREEVENT_CURRENTPLAYLISTCHANGE = ( WMPCOREEVENT_PLAYLIST_BASE + 4 );
	static final int DISPID_WMPCOREEVENT_CURRENTPLAYLISTITEMAVAILABLE = ( WMPCOREEVENT_PLAYLIST_BASE + 5 );
	static final int DISPID_WMPCOREEVENT_CURRENTITEMCHANGE = ( WMPCOREEVENT_PLAYLIST_BASE + 6 );
	static final int DISPID_WMPCOREEVENT_MEDIACOLLECTIONCHANGE = ( WMPCOREEVENT_PLAYLIST_BASE + 7 );
	static final int DISPID_WMPCOREEVENT_MEDIACOLLECTIONATTRIBUTESTRINGADDED = ( WMPCOREEVENT_PLAYLIST_BASE + 8 );
	static final int DISPID_WMPCOREEVENT_MEDIACOLLECTIONATTRIBUTESTRINGREMOVED = ( WMPCOREEVENT_PLAYLIST_BASE + 9 );
	static final int DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONCHANGE = ( WMPCOREEVENT_PLAYLIST_BASE + 10 );
	static final int DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONPLAYLISTADDED = ( WMPCOREEVENT_PLAYLIST_BASE + 11 );
	static final int DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONPLAYLISTREMOVED = ( WMPCOREEVENT_PLAYLIST_BASE + 12 );
	static final int DISPID_WMPCOREEVENT_MEDIACOLLECTIONCONTENTSCANADDEDITEM = ( WMPCOREEVENT_PLAYLIST_BASE + 13 );
	static final int DISPID_WMPCOREEVENT_MEDIACOLLECTIONCONTENTSCANPROGRESS = ( WMPCOREEVENT_PLAYLIST_BASE + 14 );
	static final int DISPID_WMPCOREEVENT_MEDIACOLLECTIONSEARCHFOUNDITEM = ( WMPCOREEVENT_PLAYLIST_BASE + 15 );
	static final int DISPID_WMPCOREEVENT_MEDIACOLLECTIONSEARCHPROGRESS = ( WMPCOREEVENT_PLAYLIST_BASE + 16 );
	static final int DISPID_WMPCOREEVENT_MEDIACOLLECTIONSEARCHCOMPLETE = ( WMPCOREEVENT_PLAYLIST_BASE + 17 );
	static final int DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONPLAYLISTSETASDELETED = ( WMPCOREEVENT_PLAYLIST_BASE + 18 );
	static final int DISPID_WMPCOREEVENT_MODECHANGE = ( WMPCOREEVENT_PLAYLIST_BASE + 19 );
	static final int DISPID_WMPCOREEVENT_MEDIACOLLECTIONATTRIBUTESTRINGCHANGED = ( WMPCOREEVENT_PLAYLIST_BASE + 20 );
	static final int DISPID_WMPCOREEVENT_MEDIAERROR = ( WMPCOREEVENT_PLAYLIST_BASE + 21 );
	static final int DISPID_WMPCOREEVENT_DOMAINCHANGE = ( WMPCOREEVENT_PLAYLIST_BASE + 22 );
	static final int DISPID_WMPCOREEVENT_OPENPLAYLISTSWITCH = ( WMPCOREEVENT_PLAYLIST_BASE + 23 );

	// ////////////////////////////////////////////////////////////////////
	// DIID_WMPOCXEvents
	//
	// These are the events that will be fired from OCX itself
	// ////////////////////////////////////////////////////////////////////
	static final int WMPOCXEVENT_BASE = 6500;
	static final int DISPID_WMPOCXEVENT_SWITCHEDTOPLAYERAPPLICATION = ( WMPOCXEVENT_BASE + 1 );
	static final int DISPID_WMPOCXEVENT_SWITCHEDTOCONTROL = ( WMPOCXEVENT_BASE + 2 );
	static final int DISPID_WMPOCXEVENT_PLAYERDOCKEDSTATECHANGE = ( WMPOCXEVENT_BASE + 3 );
	static final int DISPID_WMPOCXEVENT_PLAYERRECONNECT = ( WMPOCXEVENT_BASE + 4 );
	static final int DISPID_WMPOCXEVENT_CLICK = ( WMPOCXEVENT_BASE + 5 );
	static final int DISPID_WMPOCXEVENT_DOUBLECLICK = ( WMPOCXEVENT_BASE + 6 );
	static final int DISPID_WMPOCXEVENT_KEYDOWN = ( WMPOCXEVENT_BASE + 7 );
	static final int DISPID_WMPOCXEVENT_KEYPRESS = ( WMPOCXEVENT_BASE + 8 );
	static final int DISPID_WMPOCXEVENT_KEYUP = ( WMPOCXEVENT_BASE + 9 );
	static final int DISPID_WMPOCXEVENT_MOUSEDOWN = ( WMPOCXEVENT_BASE + 10 );
	static final int DISPID_WMPOCXEVENT_MOUSEMOVE = ( WMPOCXEVENT_BASE + 11 );
	static final int DISPID_WMPOCXEVENT_MOUSEUP = ( WMPOCXEVENT_BASE + 12 );
	static final int DISPID_WMPOCXEVENT_DEVICECONNECT = ( WMPOCXEVENT_BASE + 13 );
	static final int DISPID_WMPOCXEVENT_DEVICEDISCONNECT = ( WMPOCXEVENT_BASE + 14 );
	static final int DISPID_WMPOCXEVENT_DEVICESTATUSCHANGE = ( WMPOCXEVENT_BASE + 15 );
	static final int DISPID_WMPOCXEVENT_DEVICESYNCSTATECHANGE = ( WMPOCXEVENT_BASE + 16 );
	static final int DISPID_WMPOCXEVENT_DEVICESYNCERROR = ( WMPOCXEVENT_BASE + 17 );
	static final int DISPID_WMPOCXEVENT_CREATEPARTNERSHIPCOMPLETE = ( WMPOCXEVENT_BASE + 18 );

	public static boolean canCreate( )
	{
		RegistryKey key = new RegistryKey( RootKey.HKEY_CLASSES_ROOT,
				"WMPlayer.OCX" );
		if ( !key.exists( ) )
			return false;
		if ( key.hasSubkey( "CurVer" ) )
			return true;
		else
			return false;
	}

	protected OleFrame oleFrame;

	protected OleControlSite oleControlSite;

	protected boolean created = false;

	protected boolean activated = false;

	private CLabel errorLabel;

	private OleHookInterceptor interceptor;

	public MediaPlayer( Composite parent )
	{
		this( parent, SWT.NO_BACKGROUND, null );
	}

	public MediaPlayer( Composite parent, int style )
	{
		this( parent, style, null );
	}

	public MediaPlayer( Composite parent, int style,
			final MediaPlayerEventListener listener )
	{
		super( parent, style );
		oleFrame = new OleFrame( container, SWT.NONE );
		oleFrame.setLayoutData( new GridData( GridData.FILL_BOTH ) );
		try
		{
			// Create an Automation object for access to extended capabilities
			oleControlSite = new OleControlSite( oleFrame,
					SWT.NONE,
					"WMPlayer.OCX" );

			created = true;
			activate( );
		}
		catch ( Throwable ex )
		{
			ex.printStackTrace( );
			if ( oleFrame != null && !oleFrame.isDisposed( ) )
			{
				oleFrame.setVisible( false );
				( (GridData) oleFrame.getLayoutData( ) ).exclude = true;
			}

			errorLabel = new CLabel( container, SWT.CENTER );
			errorLabel.setForeground( ColorCache.getInstance( ).getColor( 255,
					0,
					0 ) );
			errorLabel.setText( "Create Media Player control failed." );
			errorLabel.setLayoutData( new GridData( GridData.FILL_BOTH ) );
			container.layout( );

			return;
		}

		final OleMsgHook hook = new OleMsgHook( this );
		oleControlSite.addDisposeListener( new DisposeListener( ) {

			public void widgetDisposed( DisposeEvent e )
			{
				hook.unInstallHook( );
			}

		} );
		hook.installHook( );

		if ( listener != null )
		{
			OleListener oleListener = new OleListener( ) {

				public void handleEvent( OleEvent event )
				{
					switch ( event.type )
					{
						case DISPID_WMPCOREEVENT_OPENSTATECHANGE :
						{
							listener.openStateChange( WMPMediaState.getMediaState( event.arguments[0].getInt( ) ) );
							break;
						}
						case DISPID_WMPCOREEVENT_PLAYSTATECHANGE :
						{
							listener.playStateChange( WMPMediaState.getMediaState( event.arguments[0].getInt( ) ) );
							break;
						}
						case DISPID_WMPCOREEVENT_AUDIOLANGUAGECHANGE :
						{
							listener.audioLanguageChange( event.arguments[0].getInt( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_STATUSCHANGE :
						{
							listener.statusChange( );
							break;
						}
						case DISPID_WMPCOREEVENT_SCRIPTCOMMAND :
						{
							listener.scriptCommand( event.arguments[0].getString( ),
									event.arguments[1].getString( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_NEWSTREAM :
						{
							listener.newStream( );
							break;
						}
						case DISPID_WMPCOREEVENT_DISCONNECT :
						{
							listener.disconnect( event.arguments[0].getInt( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_BUFFERING :
						{
							listener.buffering( event.arguments[0].getBoolean( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_ERROR :
						{
							listener.error( );
							break;
						}
						case DISPID_WMPCOREEVENT_WARNING :
						{
							listener.warning( event.arguments[2].getInt( ) /* WarningType */,
									event.arguments[0].getInt( )/* Param */,
									event.arguments[1].getString( ) /* Description */);
							break;
						}
						case DISPID_WMPCOREEVENT_ENDOFSTREAM :
						{
							listener.endOfStream( event.arguments[0].getInt( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_POSITIONCHANGE :
						{
							listener.positionChange( event.arguments[0].getDouble( ) /* oldPosition */,
									event.arguments[1].getDouble( ) /* newPosition */);
							break;
						}
						case DISPID_WMPCOREEVENT_MARKERHIT :
						{
							listener.markerHit( event.arguments[0].getInt( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_DURATIONUNITCHANGE :
						{
							listener.durationUnitChange( event.arguments[0].getInt( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_CDROMMEDIACHANGE :
						{
							listener.cdromMediaChange( event.arguments[0].getInt( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_PLAYLISTCHANGE :
						{
							listener.playlistChange( event.arguments[0].getAutomation( ) /* Playlist */,
									WMPPlaylistChangeEventType.getEventType( event.arguments[1].getInt( ) ) /* change */);
							break;
						}
						case DISPID_WMPCOREEVENT_CURRENTPLAYLISTCHANGE :
						{
							listener.currentPlaylistChange( WMPPlaylistChangeEventType.getEventType( event.arguments[0].getInt( ) ) );
							break;
						}
						case DISPID_WMPCOREEVENT_CURRENTPLAYLISTITEMAVAILABLE :
						{
							listener.currentPlaylistItemAvailable( event.arguments[0].getString( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_MEDIACHANGE :
						{
							listener.mediaChange( event.arguments[0].getAutomation( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_CURRENTMEDIAITEMAVAILABLE :
						{
							listener.currentMediaItemAvailable( event.arguments[0].getString( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_CURRENTITEMCHANGE :
						{
							listener.currentItemChange( event.arguments[0].getAutomation( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_MEDIACOLLECTIONCHANGE :
						{
							listener.mediaCollectionChange( );
							break;
						}
						case DISPID_WMPCOREEVENT_MEDIACOLLECTIONATTRIBUTESTRINGADDED :
						{
							listener.mediaCollectionAttributeStringAdded( event.arguments[0].getString( ),
									event.arguments[1].getString( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_MEDIACOLLECTIONATTRIBUTESTRINGREMOVED :
						{
							listener.mediaCollectionAttributeStringRemoved( event.arguments[0].getString( ),
									event.arguments[1].getString( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_MEDIACOLLECTIONATTRIBUTESTRINGCHANGED :
						{
							listener.mediaCollectionAttributeStringChanged( event.arguments[0].getString( ),
									event.arguments[1].getString( ),
									event.arguments[2].getString( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONCHANGE :
						{
							listener.playlistCollectionChange( );
							break;
						}
						case DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONPLAYLISTADDED :
						{
							listener.playlistCollectionPlaylistAdded( event.arguments[0].getString( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONPLAYLISTREMOVED :
						{
							listener.playlistCollectionPlaylistRemoved( event.arguments[0].getString( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONPLAYLISTSETASDELETED :
						{
							listener.playlistCollectionPlaylistSetAsDeleted( event.arguments[0].getString( ),
									event.arguments[1].getBoolean( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_MODECHANGE :
						{
							listener.modeChange( event.arguments[0].getString( ),
									event.arguments[1].getBoolean( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_MEDIAERROR :
						{
							listener.mediaError( event.arguments[0].getAutomation( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_OPENPLAYLISTSWITCH :
						{
							listener.openPlaylistSwitch( event.arguments[0].getAutomation( ) );
							break;
						}
						case DISPID_WMPCOREEVENT_DOMAINCHANGE :
						{
							listener.domainChange( event.arguments[0].getString( ) );
							break;
						}
						case DISPID_WMPOCXEVENT_SWITCHEDTOPLAYERAPPLICATION :
						{
							listener.switchedToPlayerApplication( );
							break;
						}
						case DISPID_WMPOCXEVENT_SWITCHEDTOCONTROL :
						{
							listener.switchedToControl( );
							break;
						}
						case DISPID_WMPOCXEVENT_PLAYERDOCKEDSTATECHANGE :
						{
							listener.playerDockedStateChange( );
							break;
						}
						case DISPID_WMPOCXEVENT_PLAYERRECONNECT :
						{
							listener.playerReconnect( );
							break;
						}
						case DISPID_WMPOCXEVENT_CLICK :
						{
							listener.click( event.arguments[0].getShort( ), /* nButton */
									event.arguments[1].getShort( ) /* nShiftState */,
									event.arguments[2].getInt( ) /* fX */,
									event.arguments[3].getInt( )/* fY */);
							break;
						}
						case DISPID_WMPOCXEVENT_DOUBLECLICK :
						{
							listener.doubleClick( event.arguments[0].getShort( ) /* nButton */,
									event.arguments[1].getShort( ) /* nShiftState */,
									event.arguments[2].getInt( ) /* fX */,
									event.arguments[3].getInt( ) /* fY */);
							break;
						}
						case DISPID_WMPOCXEVENT_KEYDOWN :
						{
							listener.keyDown( event.arguments[0].getShort( ),
									event.arguments[1].getShort( ) );
							break;
						}
						case DISPID_WMPOCXEVENT_KEYPRESS :
						{
							listener.keyPress( event.arguments[0].getShort( ) );
							break;
						}
						case DISPID_WMPOCXEVENT_KEYUP :
						{
							listener.keyUp( event.arguments[0].getShort( ),
									event.arguments[1].getShort( ) );
							break;
						}
						case DISPID_WMPOCXEVENT_MOUSEDOWN :
						{
							listener.mouseDown( event.arguments[0].getShort( ) /* nButton */,
									event.arguments[1].getShort( ) /* nShiftState */,
									event.arguments[2].getInt( ) /* fX */,
									event.arguments[3].getInt( ) /* fY */);
							break;
						}
						case DISPID_WMPOCXEVENT_MOUSEMOVE :
						{
							listener.mouseMove( event.arguments[0].getShort( ) /* nButton */,
									event.arguments[1].getShort( ) /* nShiftState */,
									event.arguments[2].getInt( ) /* fX */,
									event.arguments[3].getInt( ) /* fY */);
							break;
						}
						case DISPID_WMPOCXEVENT_MOUSEUP :
						{
							listener.mouseUp( event.arguments[0].getShort( ) /* nButton */,
									event.arguments[1].getShort( ) /* nShiftState */,
									event.arguments[2].getInt( ) /* fX */,
									event.arguments[3].getInt( ) /* fY */);
							break;
						}
					}
				}
			};

			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_OPENSTATECHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_PLAYSTATECHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_AUDIOLANGUAGECHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_STATUSCHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_SCRIPTCOMMAND,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_NEWSTREAM,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_DISCONNECT,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_BUFFERING,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_BUFFERING,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_BUFFERING,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_BUFFERING,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_ERROR,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_WARNING,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_ENDOFSTREAM,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_POSITIONCHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_MARKERHIT,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_DURATIONUNITCHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_CDROMMEDIACHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_PLAYLISTCHANGE,
					oleListener );

			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_CURRENTPLAYLISTCHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_CURRENTPLAYLISTITEMAVAILABLE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_MEDIACHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_CURRENTMEDIAITEMAVAILABLE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_CURRENTITEMCHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_MEDIACOLLECTIONCHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_MEDIACOLLECTIONATTRIBUTESTRINGADDED,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_MEDIACOLLECTIONATTRIBUTESTRINGREMOVED,
					oleListener );

			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_MEDIACOLLECTIONATTRIBUTESTRINGCHANGED,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONCHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONPLAYLISTADDED,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONPLAYLISTREMOVED,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_PLAYLISTCOLLECTIONPLAYLISTSETASDELETED,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_MODECHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_MEDIAERROR,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_OPENPLAYLISTSWITCH,
					oleListener );

			oleControlSite.addEventListener( DISPID_WMPCOREEVENT_DOMAINCHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_SWITCHEDTOPLAYERAPPLICATION,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_SWITCHEDTOCONTROL,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_PLAYERDOCKEDSTATECHANGE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_PLAYERRECONNECT,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_CLICK,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_DOUBLECLICK,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_KEYDOWN,
					oleListener );

			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_KEYPRESS,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_KEYUP,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_MOUSEDOWN,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_MOUSEMOVE,
					oleListener );
			oleControlSite.addEventListener( DISPID_WMPOCXEVENT_MOUSEUP,
					oleListener );
		}
	}

	public boolean activate( )
	{
		if ( !created )
			return false;

		// in place activate the ActiveX control
		activated = ( oleControlSite.doVerb( OLE.OLEIVERB_INPLACEACTIVATE ) == OLE.S_OK );

		return activated;
	}

	public void addHookInterceptor( OleHookInterceptor interceptor )
	{
		this.interceptor = interceptor;
	}

	public void addPlayList( File urls[] )
	{
		for ( int i = 0; i < urls.length; i++ )
		{
			invokeOleFunction( new String[]{
					"currentPlaylist", "appendItem", "newMedia"
			}, urls[i].getAbsolutePath( ) );
		}
	}

	public void closeMedia( )
	{
		invokeOleFunction( "close" );
	}

	/**
	 * Cleanup
	 */
	public void dispose( )
	{
		if ( activated )
		{
			oleControlSite.deactivateInPlaceClient( );
			activated = false;
		}

		container.dispose( );
	}

	public String getCurrentMediaSourceURL( )
	{
		return (String) getOleProperty( new String[]{
				"currentMedia", "sourceURL"
		} );
	}

	public String getCurrentMediaName( )
	{
		return (String) getOleProperty( new String[]{
				"currentMedia", "name"
		} );
	}

	/**
	 * Get the current position on the timeline.
	 * 
	 * @return the current position in milliseconds, or -1 in case of failure.
	 */
	public int getAbsolutePosition( )
	{
		try
		{
			return (int) Math.round( ( (Double) getOleProperty( new String[]{
					"controls", "currentPosition"
			} ) ).doubleValue( ) * 1000 );
		}
		catch ( IllegalStateException e )
		{
			// Invalid UI thread is an illegal state
			throw e;
		}
		catch ( Exception e )
		{
			return -1;
		}
	}

	/**
	 * Get the duration in milliseconds of the current media.
	 * 
	 * @return the duration in milliseconds, or -1 in case of failure.
	 */
	public int getDuration( )
	{
		try
		{
			return (int) Math.round( ( (Double) getOleProperty( new String[]{
					"currentMedia", "duration"
			} ) ).doubleValue( ) * 1000 );
		}
		catch ( IllegalStateException e )
		{
			// Invalid UI thread is an illegal state
			throw e;
		}
		catch ( Exception e )
		{
			return -1;
		}
	}

	public OleHookInterceptor getHookInterceptor( )
	{
		return interceptor;
	}

	/**
	 * Get the state of the media.
	 * 
	 * @return the state of the media.
	 */
	public WMPMediaState getMediaState( )
	{
		return WMPMediaState.getMediaState( ( (Integer) getOleProperty( "playState" ) ).intValue( ) );
	}

	public OleControlSite getOleControlSite( )
	{
		if ( !isActivated( ) )
			return null;
		return oleControlSite;
	}

	public OleFrame getOleFrame( )
	{
		if ( !isActivated( ) )
			return null;
		return oleFrame;
	}

	/**
	 * Get the playCount, as a value strictly greater than 0.
	 * 
	 * @return the play count, strictly greater than 0, or -1 in case of
	 *         failure.
	 */
	public int getPlayCount( )
	{
		try
		{
			return ( (Integer) getOleProperty( new String[]{
					"settings", "playCount"
			} ) ).intValue( );
		}
		catch ( IllegalStateException e )
		{
			// Invalid UI thread is an illegal state
			throw e;
		}
		catch ( Exception e )
		{
			return -1;
		}
	}

	/**
	 * Get the speed factor that is applied when a media is played, as a value
	 * strictly greater than 0.
	 * 
	 * @return the speed factor, strictly greater than 0, or NaN in case of
	 *         failure.
	 */
	public float getPlaySpeedFactor( )
	{
		try
		{
			return ( (Double) getOleProperty( new String[]{
					"settings", "rate"
			} ) ).floatValue( );
		}
		catch ( IllegalStateException e )
		{
			// Invalid UI thread is an illegal state
			throw e;
		}
		catch ( Exception e )
		{
			return Float.NaN;
		}
	}

	/**
	 * Get the stereo balance.
	 * 
	 * @return the stereo balance, between -100 and 100, with 0 being the
	 *         default, or -1 in case of failure. When mute, the balance is
	 *         still returned.
	 */
	public int getStereoBalance( )
	{
		try
		{
			return ( (Integer) getOleProperty( new String[]{
					"settings", "balance"
			} ) ).intValue( );
		}
		catch ( IllegalStateException e )
		{
			// Invalid UI thread is an illegal state
			throw e;
		}
		catch ( Exception e )
		{
			return -1;
		}
	}

	/**
	 * Get the volume, as a value between 0 and 100.
	 * 
	 * @return the volume, between 0 and 100, or -1 in case of failure. When
	 *         mute, the volume is still returned.
	 */
	public int getVolume( )
	{
		try
		{
			return ( (Integer) getOleProperty( new String[]{
					"settings", "volume"
			} ) ).intValue( );
		}
		catch ( IllegalStateException e )
		{
			// Invalid UI thread is an illegal state
			throw e;
		}
		catch ( Exception e )
		{
			return -1;
		}
	}

	public boolean isActivated( )
	{
		return activated;
	}

	/**
	 * Indicate whether loading some media should start playing automatically.
	 * 
	 * @return true if loading some media should start playing automatically.
	 */
	public boolean isAutoStart( )
	{
		return Boolean.TRUE.equals( getOleProperty( new String[]{
				"settings", "autoStart"
		} ) );
	}

	/**
	 * Indicate whether the control bar is visible.
	 * 
	 * @return true if the control bar is visible.
	 */
	public boolean isControlBarVisible( )
	{
		return Boolean.TRUE.booleanValue( ) == "full".equals( getOleProperty( "uiMode" ) );
	}

	/**
	 * Indicate whether the video is in full screen mode.
	 * 
	 * @return true if the video is in full screen mode.
	 */
	public boolean isFullScreen( )
	{
		return Boolean.TRUE.equals( getOleProperty( "fullScreen" ) );
	}

	/**
	 * Indicate whether audio is mute.
	 * 
	 * @return true if audio is mute.
	 */
	public boolean isMute( )
	{
		return Boolean.TRUE.equals( getOleProperty( new String[]{
				"settings", "mute"
		} ) );
	}

	/**
	 * Indicate if the pause functionality is enabled.
	 * 
	 * @return true if the pause functionality is enabled.
	 */
	public boolean isPauseEnabled( )
	{
		return Boolean.TRUE.equals( getOleProperty( new String[]{
				"controls", "isAvailable"
		}, "Pause" ) );
	}

	/**
	 * Indicate if the play functionality is enabled.
	 * 
	 * @return true if the play functionality is enabled.
	 */
	public boolean isPlayEnabled( )
	{
		return Boolean.TRUE.equals( getOleProperty( new String[]{
				"controls", "isAvailable"
		}, "Play" ) );
	}

	/**
	 * Indicate if the stop functionality is enabled.
	 * 
	 * @return true if the stop functionality is enabled.
	 */
	public boolean isStopEnabled( )
	{
		return Boolean.TRUE.equals( getOleProperty( new String[]{
				"controls", "isAvailable"
		}, "Stop" ) );
	}

	/**
	 * Indicate whether the video is stretched to fit.
	 * 
	 * @return true if the video is stretched to fit.
	 */
	public boolean isStretchToFit( )
	{
		return Boolean.TRUE.equals( getOleProperty( "stretchToFit" ) );
	}

	/**
	 * Load a file.
	 * 
	 * @param resourcePath
	 *            the path or URL to the file.
	 */
	public void load( String resourcePath )
	{
		setOleProperty( "url", resourcePath == null ? "" : resourcePath );
	}

	/**
	 * Pause the currently playing media.
	 */
	public void pause( )
	{
		invokeOleFunction( new String[]{
				"controls", "Pause"
		} );
	}

	/**
	 * Start playing the loaded media.
	 */
	public void play( )
	{
		invokeOleFunction( new String[]{
				"controls", "Play"
		} );
	}

	public void play( String url )
	{
		Object result = invokeOleFunctionWithResult( "newMedia", url );
		setOleProperty( "currentMedia", result );
		play( );
	}

	public void playList( File urls[] )
	{
		addPlayList( urls );
		setMode( "loop", true );
		play( );
	}

	/**
	 * Set the current position on the timeline.
	 * 
	 * @param time
	 *            The current position in milliseconds.
	 */
	public void setAbsolutePosition( int time )
	{
		setOleProperty( new String[]{
				"controls", "currentPosition"
		}, new Double( time / 1000d ) );
	}

	/**
	 * Set whether loaded media should automatically start.
	 * 
	 * @param isAutoStart
	 *            true if the media should start playing automatically when
	 *            loaded, false otherwise.
	 */
	public void setAutoStart( boolean isAutoStart )
	{
		setOleProperty( new String[]{
				"settings", "autoStart"
		}, Boolean.valueOf( isAutoStart ) );
	}

	/**
	 * Set whether the control bar is visible.
	 * 
	 * @param isControlBarVisible
	 *            true if the control bar should be visible, false otherwise.
	 */
	public void setControlBarVisible( boolean isControlBarVisible )
	{
		setOleProperty( "uiMode", isControlBarVisible ? "full" : "none" );
	}

	void setErrorDialogsEnabled( boolean isErrorDialogEnabled )
	{
		setOleProperty( new String[]{
				"settings", "enableErrorDialogs"
		}, Boolean.valueOf( isErrorDialogEnabled ) );
	}

	/**
	 * Set whether the video is playing in full screen mode.
	 * 
	 * @param isFullScreen
	 *            true if the full screen mode should be active, false
	 *            otherwise.
	 */
	public void setFullScreen( boolean isFullScreen )
	{
		setOleProperty( "fullScreen", Boolean.valueOf( isFullScreen ) );
	}

	/**
	 * autoRewind Mode - indicating whether the tracks are rewound to the
	 * beginning after playing to the end. Default state is true.
	 * 
	 * loop Mode - indicating whether the sequence of tracks repeats itself.
	 * Default state is false.
	 * 
	 * showFrame Mode - indicating whether the nearest video key frame is
	 * displayed at the current position when not playing. Default state is
	 * false. Has no effect on audio tracks.
	 * 
	 * shuffle Mode - indicating whether the tracks are played in random order.
	 * Default state is false.
	 * 
	 * @param mode
	 * @param flag
	 */
	public void setMode( String mode, boolean flag )
	{
		invokeOleFunction( new String[]{
				"settings", "setMode"
		}, new Object[]{
				mode, Boolean.valueOf( flag )
		} );
	}

	/**
	 * Set whether audio is mute.
	 * 
	 * @param isMute
	 *            true if audio should be mute, false otherwise.
	 */
	public void setMute( boolean isMute )
	{
		setOleProperty( new String[]{
				"settings", "mute"
		}, Boolean.valueOf( isMute ) );
	}

	/**
	 * Set the play count.
	 * 
	 * @param playCount
	 *            the new playCount, with a value stricly greater than 0.
	 */
	public void setPlayCount( int playCount )
	{
		if ( playCount <= 0 )
		{
			throw new IllegalArgumentException( "The play count must be strictly greater than 0" );
		}
		setOleProperty( new String[]{
				"settings", "playCount"
		}, new Integer( playCount ) );
	}

	/**
	 * Set the speed factor that is applied when a media is played.
	 * 
	 * @param speedFactor
	 *            the speed factor, with a value strictly greater than 0.
	 */
	public void setPlaySpeedFactor( float speedFactor )
	{
		if ( speedFactor <= 0 )
		{
			throw new IllegalArgumentException( "The rate must be strictly greater than 0!" );
		}
		setOleProperty( new String[]{
				"settings", "rate"
		}, new Double( (double) speedFactor ) );
	}

	/**
	 * @param stereoBalance
	 *            The stereo balance between -100 and 100, with 0 being the
	 *            default.
	 */
	public void setStereoBalance( int stereoBalance )
	{
		if ( stereoBalance < 100 || stereoBalance > 100 )
		{
			throw new IllegalArgumentException( "The stereo balance must be between -100 and 100" );
		}
		setOleProperty( new String[]{
				"settings", "balance"
		}, new Integer( stereoBalance ) );
	}

	/**
	 * Set whether the video is stretched to fit.
	 * 
	 * @param isStretchToFit
	 *            true if the video is stretched to fit, false otherwise.
	 */
	public void setStretchToFit( boolean isStretchToFit )
	{
		setOleProperty( "stretchToFit", Boolean.valueOf( isStretchToFit ) );
	}

	/**
	 * Set the volume.
	 * 
	 * @param volume
	 *            the new volume, with a value between 0 and 100.
	 */
	public void setVolume( int volume )
	{
		if ( volume < 0 || volume > 100 )
		{
			throw new IllegalArgumentException( "The volume must be between 0 and 100" );
		}
		setOleProperty( new String[]{
				"settings", "volume"
		}, new Integer( volume ) );
	}

	/**
	 * Stop the currently playing media.
	 */
	public void stop( )
	{
		invokeOleFunction( new String[]{
				"controls", "Stop"
		} );
	}

	public void setErrorInfo( String errorInfo )
	{
		if ( errorLabel != null && !errorLabel.isDisposed( ) )
			errorLabel.setText( errorInfo );
	}

	public void setErrorImage( Image image )
	{
		if ( errorLabel != null && !errorLabel.isDisposed( ) )
			errorLabel.setImage( image );
	}

	public void setErrorFont( Font font )
	{
		if ( errorLabel != null && !errorLabel.isDisposed( ) )
			errorLabel.setFont( font );
	}

	public void setErrorForeground( Color color )
	{
		if ( errorLabel != null && !errorLabel.isDisposed( ) )
			errorLabel.setForeground( color );
	}

	public void setErrorBackground( Color color )
	{
		if ( errorLabel != null && !errorLabel.isDisposed( ) )
			errorLabel.setBackground( color );
	}
}
