
package org.sf.feeling.swt.win32.extension.example.page;

import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.sound.Mixer;
import org.sf.feeling.swt.win32.extension.sound.MixerCaps;
import org.sf.feeling.swt.win32.extension.sound.hook.MixerMsgHook;
import org.sf.feeling.swt.win32.internal.extension.util.ImageCache;

public class SoundPage extends SimpleTabPage implements Listener
{

	private CCombo audioCombo;

	private MixerMsgHook hook;

	private Scale volumeBalanceSlider;

	private Scale volumeSlider;

	private Button volumeMutebutton;

	private HashMap balanceScaleMap = new HashMap( );

	private HashMap volumeScaleMap = new HashMap( );

	private HashMap muteButtonMap = new HashMap( );

	private Composite audioDetailArea;

	public void buildUI( Composite parent )
	{
		super.buildUI( parent );

		TableWrapLayout layout = new TableWrapLayout( );
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.topMargin = 15;
		layout.verticalSpacing = 20;
		container.getBody( ).setLayout( layout );

		createTitle( );
		createAudioSelectedArea( );
		createVolumeArea( );
		handleAudioSelection( );
	}

	private void initAudioCard( )
	{
		int numDevs = Mixer.mixerGetNumDevs( );
		if ( numDevs > 0 )
		{
			for ( int i = 0; i < numDevs; i++ )
			{
				Mixer.mixerOpen( container.getShell( ).handle, i );
				MixerCaps mixCaps = Mixer.getMixerCaps( i );
				if ( mixCaps != null )
				{
					audioCombo.add( mixCaps.getSzPname( ), i );
					if ( i == 0 )
					{
						audioCombo.select( 0 );
					}
				}
			}
		}
	}

	private void createAudioSelectedArea( )
	{
		Composite audioCardArea = WidgetUtil.getToolkit( )
				.createComposite( container.getBody( ) );
		GridLayout layout = new GridLayout( );
		audioCardArea.setLayout( layout );
		layout.numColumns = 3;
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		WidgetUtil.createLabel( audioCardArea ).setText( "Select Audio:" );
		audioCombo = WidgetUtil.getToolkit( )
				.createCCombo( audioCardArea, true );
		GridData gd = new GridData( );
		gd.widthHint = 250;
		audioCombo.setLayoutData( gd );
		audioCombo.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				handleAudioSelection( );
			}

		} );
		initAudioCard( );

	}

	private void enableControl( Composite parent, boolean enable )
	{
		Control[] controls = parent.getChildren( );
		if ( controls != null )
		{
			for ( int i = 0; i < controls.length; i++ )
			{
				Control control = controls[i];
				if ( control instanceof Composite )
				{
					enableControl( (Composite) control, enable );
				}
				else
					control.setEnabled( enable );
			}
		}
		parent.setEnabled( enable );
	}

	private void createVolumeArea( )
	{
		if ( audioCombo.getSelectionIndex( ) > -1 )
		{
			audioDetailArea = WidgetUtil.getToolkit( )
					.createComposite( container.getBody( ) );
			GridLayout layout = new GridLayout( );
			layout.numColumns = 11;
			audioDetailArea.setLayout( layout );

			createVolumeControlArea( audioDetailArea );

			createVerticalSeparator( audioDetailArea );

			createVolumeTypeControlArea( audioDetailArea, Mixer.TYPE_WAVE );

			createVerticalSeparator( audioDetailArea );

			createVolumeTypeControlArea( audioDetailArea, Mixer.TYPE_MIDI );

			createVerticalSeparator( audioDetailArea );

			createVolumeTypeControlArea( audioDetailArea, Mixer.TYPE_CDPLAYER );

			createVerticalSeparator( audioDetailArea );

			createVolumeTypeControlArea( audioDetailArea, Mixer.TYPE_MICROPHONE );

			createVerticalSeparator( audioDetailArea );

			createVolumeTypeControlArea( audioDetailArea, Mixer.TYPE_LINEIN );

		}
	}

	private void createVerticalSeparator( Composite audioDetailArea )
	{
		WidgetUtil.getToolkit( )
				.createLabel( audioDetailArea, "", SWT.VERTICAL | SWT.SEPARATOR )
				.setLayoutData( new GridData( GridData.FILL_VERTICAL ) );
	}

	private void createVolumeControlArea( Composite audioDetailArea )
	{
		Composite volumeControlArea = WidgetUtil.getToolkit( )
				.createComposite( audioDetailArea );
		volumeControlArea.setLayout( new GridLayout( ) );
		WidgetUtil.getToolkit( ).createCLabel( volumeControlArea,
				"Volume Control" );
		new Label( volumeControlArea, SWT.SEPARATOR | SWT.HORIZONTAL ).setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
		WidgetUtil.getToolkit( ).createLabel( volumeControlArea, "Balance:" );
		Composite volumeControlBanlanceArea = WidgetUtil.getToolkit( )
				.createComposite( volumeControlArea );

		GridLayout layout = new GridLayout( );
		layout.numColumns = 5;
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		layout.horizontalSpacing = 1;
		volumeControlBanlanceArea.setLayout( layout );

		WidgetUtil.getToolkit( )
				.createLabel( volumeControlBanlanceArea, "", SWT.NONE )
				.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
		Label leftImageLabel = WidgetUtil.getToolkit( )
				.createLabel( volumeControlBanlanceArea, "", SWT.NONE );
		leftImageLabel.setImage( ImageCache.getImage( "/leftVolume.gif" ) );
		leftImageLabel.setLayoutData( new GridData( ) );

		volumeBalanceSlider = WidgetUtil.getToolkit( )
				.createScale( volumeControlBanlanceArea, SWT.HORIZONTAL );
		volumeBalanceSlider.setMinimum( 0 );
		volumeBalanceSlider.setMaximum( 200 );
		volumeBalanceSlider.setPageIncrement( 100 );
		GridData gd = new GridData( );
		gd.widthHint = 60;
		volumeBalanceSlider.setLayoutData( gd );
		volumeBalanceSlider.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( audioCombo.getSelectionIndex( ) > -1 )
				{
					Mixer.setMasterBalance( audioCombo.getSelectionIndex( ),
							( (float) ( volumeBalanceSlider.getSelection( ) - 100 ) ) / 100 );
				}
			}

		} );

		Label rightImageLabel = WidgetUtil.getToolkit( )
				.createLabel( volumeControlBanlanceArea, "" );
		rightImageLabel.setImage( ImageCache.getImage( "/rightVolume.gif" ) );
		rightImageLabel.setLayoutData( new GridData( ) );

		WidgetUtil.getToolkit( )
				.createLabel( volumeControlBanlanceArea, "", SWT.NONE )
				.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		WidgetUtil.getToolkit( ).createLabel( volumeControlArea, "Volume:" );
		volumeSlider = WidgetUtil.getToolkit( ).createScale( volumeControlArea,
				SWT.VERTICAL );
		volumeSlider.setMinimum( 0 );
		volumeSlider.setMaximum( 100 );
		volumeSlider.setPageIncrement( 20 );
		gd = new GridData( );
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.CENTER;
		gd.heightHint = 100;
		volumeSlider.setLayoutData( gd );
		volumeSlider.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( audioCombo.getSelectionIndex( ) > -1 )
				{
					Mixer.setMasterVolume( audioCombo.getSelectionIndex( ),
							(int) ( ( ( (float) ( 100 - volumeSlider.getSelection( ) ) ) / 100 ) * Mixer.MAX_VOL_VALUE ) );
				}
			}

		} );

		volumeMutebutton = WidgetUtil.getToolkit( )
				.createButton( volumeControlArea, "Mute all", SWT.CHECK );
		volumeMutebutton.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				Mixer.setMasterMute( audioCombo.getSelectionIndex( ),
						volumeMutebutton.getSelection( ) );
			}

		} );
	}

	private void createVolumeTypeControlArea( Composite audioDetailArea,
			final int srcType )
	{
		Composite volumeControlArea = WidgetUtil.getToolkit( )
				.createComposite( audioDetailArea );
		volumeControlArea.setLayout( new GridLayout( ) );
		WidgetUtil.getToolkit( ).createCLabel( volumeControlArea,
				getDisplayText( srcType ) );
		new Label( volumeControlArea, SWT.SEPARATOR | SWT.HORIZONTAL ).setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
		WidgetUtil.getToolkit( ).createLabel( volumeControlArea, "Balance:" );
		Composite volumeControlBanlanceArea = WidgetUtil.getToolkit( )
				.createComposite( volumeControlArea );

		GridLayout layout = new GridLayout( );
		layout.numColumns = 5;
		layout.marginHeight = 1;
		layout.marginWidth = 1;
		layout.horizontalSpacing = 1;
		volumeControlBanlanceArea.setLayout( layout );

		WidgetUtil.getToolkit( )
				.createLabel( volumeControlBanlanceArea, "", SWT.NONE )
				.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
		Label leftImageLabel = WidgetUtil.getToolkit( )
				.createLabel( volumeControlBanlanceArea, "", SWT.NONE );
		leftImageLabel.setImage( ImageCache.getImage( "/leftVolume.gif" ) );
		leftImageLabel.setLayoutData( new GridData( ) );

		final Scale balanceScale = WidgetUtil.getToolkit( )
				.createScale( volumeControlBanlanceArea, SWT.HORIZONTAL );
		balanceScale.setMinimum( 0 );
		balanceScale.setMaximum( 200 );
		balanceScale.setPageIncrement( 100 );
		GridData gd = new GridData( );
		gd.widthHint = 60;
		balanceScale.setLayoutData( gd );
		balanceScale.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( audioCombo.getSelectionIndex( ) > -1 )
				{
					Mixer.setPlaybackVolumeBalance( audioCombo.getSelectionIndex( ),
							srcType,
							( (float) ( balanceScale.getSelection( ) - 100 ) ) / 100 );
				}
			}

		} );
		balanceScaleMap.put( new Integer( srcType ), balanceScale );

		Label rightImageLabel = WidgetUtil.getToolkit( )
				.createLabel( volumeControlBanlanceArea, "" );
		rightImageLabel.setImage( ImageCache.getImage( "/rightVolume.gif" ) );
		rightImageLabel.setLayoutData( new GridData( ) );

		WidgetUtil.getToolkit( )
				.createLabel( volumeControlBanlanceArea, "", SWT.NONE )
				.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

		WidgetUtil.getToolkit( ).createLabel( volumeControlArea, "Volume:" );
		final Scale volumeScale = WidgetUtil.getToolkit( )
				.createScale( volumeControlArea, SWT.VERTICAL );
		volumeScale.setMinimum( 0 );
		volumeScale.setMaximum( 100 );
		volumeScale.setPageIncrement( 20 );
		gd = new GridData( );
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.CENTER;
		gd.heightHint = 100;
		volumeScale.setLayoutData( gd );
		volumeScale.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				if ( audioCombo.getSelectionIndex( ) > -1 )
				{
					Mixer.setMixerVolume( audioCombo.getSelectionIndex( ),
							(int) ( ( ( (float) ( 100 - volumeScale.getSelection( ) ) ) / 100 ) * Mixer.MAX_VOL_VALUE ),
							srcType );
				}
			}

		} );
		volumeScaleMap.put( new Integer( srcType ), volumeScale );

		final Button mutebutton = WidgetUtil.getToolkit( )
				.createButton( volumeControlArea, "Mute", SWT.CHECK );
		mutebutton.addSelectionListener( new SelectionAdapter( ) {

			public void widgetSelected( SelectionEvent e )
			{
				Mixer.setPlaybackMute( audioCombo.getSelectionIndex( ),
						srcType,
						mutebutton.getSelection( ) );
			}

		} );
		muteButtonMap.put( new Integer( srcType ), mutebutton );
	}

	private String getDisplayText( int srcType )
	{
		switch ( srcType )
		{
			case Mixer.TYPE_VOLUMECONTROL :
				return "Volume Control";
			case Mixer.TYPE_WAVE :
				return "Wave";
			case Mixer.TYPE_MIDI :
				return "SW Synth";
			case Mixer.TYPE_CDPLAYER :
				return "CD Player";
			case Mixer.TYPE_MICROPHONE :
				return "Microphone";
			case Mixer.TYPE_LINEIN :
				return "Line In";
			default :
				return "";
		}
	}

	public void refresh( )
	{
		if ( audioDetailArea == null || audioDetailArea.isDisposed( ) )
			return;
		if ( audioDetailArea != null )
			audioDetailArea.setVisible( audioCombo.getSelectionIndex( ) > -1 );
		if ( audioCombo.getSelectionIndex( ) > -1 )
		{
			if(Mixer.getPlaybackVolume( audioCombo.getSelectionIndex( ), Mixer.TYPE_VOLUMECONTROL, true ).length>=2)
			{
				volumeSlider.setEnabled( true );
				volumeBalanceSlider.setEnabled( true );
				volumeMutebutton.setEnabled( true );
				int value = (int) ( ( ( (float) Mixer.getMasterVolume( audioCombo.getSelectionIndex( ) ) ) / Mixer.MAX_VOL_VALUE ) * 100 );
				volumeSlider.setSelection( 100 - value );

				if ( Mixer.isPlaybackMixerMono( audioCombo.getSelectionIndex( ) ) )
				{
					volumeBalanceSlider.setEnabled( false );
					volumeBalanceSlider.setSelection( 100 );
				}
				else
				{
					volumeBalanceSlider.setEnabled( true );
					if ( !( volumeBalanceSlider.getSelection( ) != 100 && value == 0 ) )
						volumeBalanceSlider.setSelection( (int) ( Mixer.getMasterBalance( audioCombo.getSelectionIndex( ) ) * 100 ) + 100 );
				}
				volumeMutebutton.setSelection( Mixer.isMasterMute( audioCombo.getSelectionIndex( ) ) );
			}
			else{
				volumeSlider.setEnabled( false );
				volumeSlider.setSelection( 0 );
				volumeBalanceSlider.setEnabled( false );
				volumeBalanceSlider.setSelection( 100 );
				volumeMutebutton.setEnabled( false );
			}
			refreshMixerControl( Mixer.TYPE_WAVE );
			refreshMixerControl( Mixer.TYPE_MIDI );
			refreshMixerControl( Mixer.TYPE_CDPLAYER );
			refreshMixerControl( Mixer.TYPE_MICROPHONE );
			refreshMixerControl( Mixer.TYPE_LINEIN );

		}
	}

	private void refreshMixerControl( int srcType )
	{
		Scale balanceScale = ( (Scale) balanceScaleMap.get( new Integer( srcType ) ) );

		if ( !Mixer.containControlType( audioCombo.getSelectionIndex( ),
				Mixer.MIXERLINE_COMPONENTTYPE_DST_SPEAKERS,
				srcType ) )
		{
			balanceScale.setEnabled( false );
			balanceScale.setSelection( 100 );
			( (Scale) volumeScaleMap.get( new Integer( srcType ) ) ).setSelection( 0 );
			( (Scale) volumeScaleMap.get( new Integer( srcType ) ) ).setEnabled( false );
			( (Button) muteButtonMap.get( new Integer( srcType ) ) ).setEnabled( false );
			( (Button) muteButtonMap.get( new Integer( srcType ) ) ).setSelection( false );
			return;
		}

		int value = (int) ( ( ( (float) Mixer.getMixerVolume( audioCombo.getSelectionIndex( ),
				srcType ) ) / Mixer.MAX_VOL_VALUE ) * 100 );
		( (Scale) volumeScaleMap.get( new Integer( srcType ) ) ).setSelection( 100 - value );

		if ( Mixer.isScrTypeMono( audioCombo.getSelectionIndex( ),
				Mixer.MIXERLINE_COMPONENTTYPE_DST_SPEAKERS,
				srcType ) )
		{
			balanceScale.setEnabled( false );
			balanceScale.setSelection( 100 );
		}
		else
		{
			balanceScale.setEnabled( true );
			if ( !( balanceScale.getSelection( ) != 100 && value == 0 ) )
				balanceScale.setSelection( (int) ( Mixer.getPlaybackVolumeBalance( audioCombo.getSelectionIndex( ),
						srcType ) * 100 ) + 100 );
		}
		( (Button) muteButtonMap.get( new Integer( srcType ) ) ).setSelection( Mixer.isPlaybackMute( audioCombo.getSelectionIndex( ),
				srcType ) );
	}

	private void createTitle( )
	{
		WidgetUtil.createFormText( container.getBody( ),
				"<form><p>This page demonstrates how to set Windows Playback Volume. (<b>Note</b>: Can't use it on the Vista and later)</p></form>",
				true,
				false );
	}

	public String getDisplayName( )
	{
		return "Volume Control Example";
	}

	public void handleEvent( Event event )
	{
		refresh( );
	}

	public void activate( )
	{
		int numDevs = Mixer.mixerGetNumDevs( );
		if ( numDevs > 0 )
		{
			for ( int i = 0; i < numDevs; i++ )
			{
				Mixer.mixerOpen( container.getShell( ).handle, i );
			}
		}

		hook = new MixerMsgHook( container.getShell( ) );
		hook.installHook( );
		hook.addChangeListener( this );
	}

	public void deActivate( )
	{
		hook.removeChangeListener( this );
		hook.unInstallHook( );
		int numDevs = Mixer.mixerGetNumDevs( );
		if ( numDevs > 0 )
		{
			for ( int i = 0; i < numDevs; i++ )
			{
				Mixer.mixerClose( i );
			}
		}
	}

	private void handleAudioSelection( )
	{
		int deviceId = audioCombo.getSelectionIndex( );
		if ( deviceId == -1 )
			return;
		if ( Mixer.isPlaybackDevice( deviceId ) )
		{
			enableControl( audioDetailArea, true );
			refresh( );
		}
		else
		{
			enableControl( audioDetailArea, false );
		}
	}
}
