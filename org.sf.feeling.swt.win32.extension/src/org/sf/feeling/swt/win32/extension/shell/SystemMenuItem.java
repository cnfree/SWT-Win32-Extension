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

package org.sf.feeling.swt.win32.extension.shell;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.sf.feeling.swt.win32.extension.Win32;

/**
 * A SystemMenuItem instance can maintain a shell's system menu item status and
 * information.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class SystemMenuItem
{

	/*
	 * Max custom menu id.
	 */
	private static int MENU_ID = 0xF000;

	/**
	 * System Menu Move Item ID.
	 */
	public final static int ITEM_MOVE_ID = Win32.SC_MOVE;
	/**
	 * System Menu Maximize Item ID.
	 */
	public final static int ITEM_MAXIMIZE_ID = Win32.SC_MAXIMIZE;
	/**
	 * System Menu Minimize Item ID.
	 */
	public final static int ITEM_MINIMIZE_ID = Win32.SC_MINIMIZE;
	/**
	 * System Menu Size Item ID.
	 */
	public final static int ITEM_SIZE_ID = Win32.SC_SIZE;
	/**
	 * System Menu Restore Item ID.
	 */
	public final static int ITEM_RESTORE_ID = Win32.SC_RESTORE;
	/**
	 * System Menu Close Item ID.
	 */
	public final static int ITEM_CLOSE_ID = Win32.SC_CLOSE;

	private String text = "";
	private int style = SWT.NONE;
	private int id;
	private boolean selected = false;
	private boolean enabled = true;
	private Image image = null;

	private SystemMenuManager manager;

	/**
	 * Create a custom system menu item.
	 * 
	 * @param manager
	 *            System Menu manger, used for create a MenuItem and set/get
	 *            MenuItem information.
	 * @param style
	 *            MenuItem style(SWT.SEPARATOR or SWT.NONE)
	 */
	public SystemMenuItem( SystemMenuManager manager, int style )
	{
		this.style = style;
		this.id = --MENU_ID;
		this.manager = manager;
	}

	private SystemMenuItem( SystemMenuManager manager )
	{
		this.manager = manager;
	}

	/**
	 * Get menu item's text.
	 * 
	 * @return menu item's text.
	 */
	public String getText( )
	{
		if ( valueOf( manager, id ) != null )
			return manager.getText( id );
		return text;
	}

	/**
	 * Set specified image on the menu item. <br>
	 * <b>Important:</b>Image size must be smaller than 13*13.
	 * 
	 * @param image
	 *            the image will be setted on the menu item.
	 */
	public void setImage( Image image )
	{
		this.image = image;
		if ( valueOf( manager, id ) != null )
			manager.setImage( id, image );
	}

	/**
	 * Get the image on the menu item.
	 * 
	 * @return the image will be setted on the menu item.
	 */
	public Image getImage( )
	{
		return image;
	}

	/**
	 * Set specified text on the menu item.
	 * 
	 * @param text
	 *            the text will be setted on the menu item.
	 */
	public void setText( String text )
	{
		this.text = text;
		if ( valueOf( manager, id ) != null )
			manager.setText( id, text );
	}

	/**
	 * Get menu item style.
	 * 
	 * @return the menu item style.
	 */
	public int getStyle( )
	{
		return style;
	}

	/**
	 * Set menu item whether be checked.
	 * 
	 * @param selected
	 *            whether check the menu item.
	 */
	public void setSelection( boolean selected )
	{
		this.selected = selected;
		if ( manager.getItem( id ) != null )
		{
			manager.setSelection( id, selected );
		}
	};

	/**
	 * Get the menu item checked status.
	 * 
	 * @return the menu item checked status.
	 */
	public boolean getSelection( )
	{
		if ( manager.getItem( id ) != null )
		{
			return manager.getSelection( id );
		}
		else
			return selected;
	}

	/**
	 * Get the menu item id.
	 * 
	 * @return the menu item id.
	 */
	public Integer getId( )
	{
		return new Integer( id );
	}

	private List listeners = new ArrayList( );

	/**
	 * Add menu item event listener.
	 * 
	 * @param listener
	 *            a swt event listener.
	 */
	public void addListener( Listener listener )
	{
		listeners.add( listener );
	}

	/**
	 * Remove menu item event listener.
	 * 
	 * @param listener
	 *            the listener on the menu item.
	 */
	public void removeListener( Listener listener )
	{
		listeners.remove( listener );
	}

	/**
	 * Fire menu item selected event.
	 * 
	 * @param event
	 *            menu item selected event.
	 */
	public void fireSelectedEvent( Event event )
	{
		for ( int i = 0; i < listeners.size( ); i++ )
		{
			( (Listener) listeners.get( i ) ).handleEvent( event );
		}
	}

	/**
	 * Set the menu item whether enabled.
	 * 
	 * @param enabled
	 *            whether set the menu item enabled.
	 */
	public void setEnabled( boolean enabled )
	{
		this.enabled = enabled;
		if ( manager.getItem( id ) != null )
			manager.setEnabled( id, enabled );
	};

	/**
	 * Get the menu item enabled status.
	 * 
	 * @return the menu item enabled status.
	 */
	public boolean isEnabled( )
	{
		if ( manager.getItem( id ) != null )
			return manager.getEnabled( id );
		else
			return enabled;
	}

	/**
	 * Dispose the menu item.
	 */
	public void dispose( )
	{
		if ( valueOf( manager, id ) != null )
		{
			manager.removeItem( id );
		}
		listeners.clear( );
	}

	/**
	 * Return a SystemMenuItem object, if the system menu contains a menu item
	 * which menu-id equals specified menu-id, then doesn't create another
	 * instance.
	 * 
	 * @param manager
	 *            System Menu manger, used for create a MenuItem and set/get
	 *            MenuItem information.
	 * @param menuid
	 *            Id of The menu item object will be created.
	 * @return a SystemMenuItem object.
	 */
	public static SystemMenuItem valueOf( SystemMenuManager manager, int menuid )
	{
		switch ( menuid )
		{
			case ITEM_MOVE_ID :
			case ITEM_MAXIMIZE_ID :
			case ITEM_MINIMIZE_ID :
			case ITEM_SIZE_ID :
			case ITEM_RESTORE_ID :
			case ITEM_CLOSE_ID :
				SystemMenuItem item = new SystemMenuItem( manager );
				item.id = menuid;
				return item;
			default :
				if ( menuid >= 0xF000 )
					throw new IllegalArgumentException( "The menu id is illegal." );
				return manager.getItem( menuid );
		}
	}
}
