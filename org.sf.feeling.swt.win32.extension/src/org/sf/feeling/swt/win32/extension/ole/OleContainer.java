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

package org.sf.feeling.swt.win32.extension.ole;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleControlSite;
import org.eclipse.swt.ole.win32.OleFunctionDescription;
import org.eclipse.swt.ole.win32.OleParameterDescription;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * An abstract class that allow intercept ole control event.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public abstract class OleContainer implements IOleComponent
{

	private static final String LINE_SEPARATOR = System.getProperty( "line.separator" );

	protected static Variant createVariant( Object value )
	{
		if ( value instanceof Boolean )
		{
			return new Variant( ( (Boolean) value ).booleanValue( ) );
		}
		if ( value instanceof Byte )
		{
			return new Variant( ( (Byte) value ).byteValue( ) );
		}
		if ( value instanceof Short )
		{
			return new Variant( ( (Short) value ).shortValue( ) );
		}
		if ( value instanceof Character )
		{
			return new Variant( ( (Character) value ).charValue( ) );
		}
		if ( value instanceof Integer )
		{
			return new Variant( ( (Integer) value ).intValue( ) );
		}
		if ( value instanceof Long )
		{
			return new Variant( ( (Long) value ).intValue( ) );
		}
		if ( value instanceof Float )
		{
			return new Variant( ( (Float) value ).floatValue( ) );
		}
		if ( value instanceof Double )
		{
			return new Variant( ( (Double) value ).doubleValue( ) );
		}
		if ( value instanceof String || value == null )
		{
			return new Variant( (String) value );
		}
		if ( value instanceof OleAutomation )
		{
			return new Variant( (OleAutomation) value );
		}
		if ( value instanceof Variant )
		{
			return (Variant) value;
		}
		throw new IllegalArgumentException( "The value could not be converted to a Variant: "
				+ value );
	}

	private static void dispose( Variant variant )
	{
		if ( variant == null )
		{
			return;
		}
		variant.dispose( );
	}

	protected static Object getVariantValue( Variant variant )
	{
		if ( variant == null )
		{
			return null;
		}
		if ( ( variant.getType( ) & OLE.VT_BYREF ) == OLE.VT_BYREF )
		{
			return variant;
		}

		switch ( variant.getType( ) )
		{
			case OLE.VT_BOOL :
				return Boolean.valueOf( variant.getBoolean( ) );
			case OLE.VT_I1 :
				return new Byte( variant.getByte( ) );
			case OLE.VT_I2 :
				return new Short( variant.getShort( ) );
			case OLE.VT_UI2 :
				return new Character( variant.getChar( ) );
			case OLE.VT_I4 :
				return new Integer( variant.getInt( ) );
			case OLE.VT_I8 :
				return new Long( variant.getLong( ) );
			case OLE.VT_R4 :
				return new Float( variant.getFloat( ) );
			case OLE.VT_R8 :
				return new Double( variant.getDouble( ) );
			case OLE.VT_BSTR :
				return variant.getString( );
			case OLE.VT_DISPATCH :
				return variant.getAutomation( );
			default :
				return variant;
		}
	}

	protected Composite container;

	private Map oleTypeToDescriptionMap;

	/**
	 * @see org.eclipse.swt.widgets.Composite#Composite(Composite, int)
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot
	 *            be null)
	 * @param style
	 *            the style of widget to construct
	 */
	public OleContainer( Composite parent, int style )
	{
		container = new Composite( parent, style );
		GridLayout layout = new GridLayout( );
		layout.marginWidth = layout.marginHeight = 0;
		container.setLayout( layout );
	}

	/**
	 * Add a hook interceptor to listen ole control event.
	 * 
	 * @param interceptor
	 *            the interceptor used to listen ole control event.
	 */
	public abstract void addHookInterceptor( OleHookInterceptor interceptor );

	public StringBuffer dumpOleInterfaceDefinitions( )
	{
		oleTypeToDescriptionMap = new HashMap( );
		Field[] fields = OLE.class.getDeclaredFields( );
		for ( int i = 0; i < fields.length; i++ )
		{
			Field field = fields[i];
			String fieldName = field.getName( );
			Short value = null;
			if ( fieldName.startsWith( "VT_" ) )
			{
				try
				{
					value = (Short) field.get( null );
				}
				catch ( Exception e )
				{
				}
			}
			if ( value != null )
			{
				String fieldDescription = fieldName.substring( "VT_".length( ) )
						.toLowerCase( Locale.ENGLISH );
				oleTypeToDescriptionMap.put( value, fieldDescription );
			}
		}
		OleAutomation automation = new OleAutomation( getOleControlSite( ) );
		StringBuffer sb = new StringBuffer( );
		dumpOleInterfaceDefinitions( sb, automation, 0 );
		automation.dispose( );
		return sb;
	}

	private void dumpOleInterfaceDefinitions( StringBuffer sb,
			OleAutomation automation, int index )
	{
		List functionList = new ArrayList( );
		for ( int i = 0;; i++ )
		{
			OleFunctionDescription functionDescription = automation.getFunctionDescription( i );
			if ( functionDescription == null )
			{
				break;
			}
			functionList.add( functionDescription );
		}
		Collections.sort( functionList, new Comparator( ) {

			public int compare( Object o1, Object o2 )
			{

				return ( (OleFunctionDescription) o1 ).name.toLowerCase( Locale.ENGLISH )
						.compareTo( ( (OleFunctionDescription) o2 ).name.toLowerCase( Locale.ENGLISH ) );
			}
		} );
		for ( int z = 0; z < functionList.size( ); z++ )
		{
			OleFunctionDescription functionDescription = (OleFunctionDescription) functionList.get( z );
			for ( int j = 0; j < index; j++ )
			{
				sb.append( "  " );
			}
			sb.append( functionDescription.name ).append( "(" );
			for ( int i = 0; i < functionDescription.args.length; i++ )
			{
				OleParameterDescription param = functionDescription.args[i];
				if ( i > 0 )
				{
					sb.append( ", " );
				}
				sb.append( getTypeDescription( param.type ) )
						.append( ' ' )
						.append( param.name == null ? "arg" + i : param.name );
			}
			sb.append( "): " )
					.append( getTypeDescription( functionDescription.returnType ) )
					.append( LINE_SEPARATOR );
		}
		List propertyList = new ArrayList( );
		for ( int i = 1;; i++ )
		{
			String name = automation.getName( i );
			if ( name == null )
			{
				break;
			}
			propertyList.add( name );
		}
		Collections.sort( propertyList, new Comparator( ) {

			public int compare( Object o1, Object o2 )
			{
				return o1.toString( )
						.toLowerCase( Locale.ENGLISH )
						.compareTo( o2.toString( ).toLowerCase( Locale.ENGLISH ) );
			}
		} );
		for ( int z = 0; z < propertyList.size( ); z++ )
		{
			String propertyName = (String) propertyList.get( z );
			for ( int j = 0; j < index; j++ )
			{
				sb.append( "  " );
			}
			sb.append( propertyName ).append( LINE_SEPARATOR );
			Variant variantProperty = automation.getProperty( automation.getIDsOfNames( new String[]{
				propertyName
			} )[0] );
			if ( variantProperty != null
					&& variantProperty.getType( ) == OLE.VT_DISPATCH )
			{
				OleAutomation newAutomation = variantProperty.getAutomation( );
				dumpOleInterfaceDefinitions( sb, newAutomation, index + 1 );
				newAutomation.dispose( );
			}
			dispose( variantProperty );
		}
	}

	public Control getControl( )
	{
		return container;
	}

	/**
	 * the interceptor used to listen ole control event.
	 * 
	 * @return the interceptor used to listen ole control event.
	 */
	public abstract OleHookInterceptor getHookInterceptor( );

	public abstract OleControlSite getOleControlSite( );

	public abstract boolean isActivated( );

	public Object getOleProperty( String property )
	{
		return getOleProperty( new String[]{
			property
		}, new Object[0] );
	}

	public Object getOleProperty( String property, Object arg )
	{
		return getOleProperty( new String[]{
			property
		}, new Object[]{
			arg
		} );
	}

	public Object getOleProperty( String property, Object[] args )
	{
		return getOleProperty( new String[]{
			property
		}, args );
	}

	public Object getOleProperty( String[] propertyPath )
	{
		return getOleProperty( propertyPath, new Object[0] );
	}

	public Object getOleProperty( String[] propertyPath, Object arg )
	{
		return getOleProperty( propertyPath, new Object[]{
			arg
		} );
	}

	public Object getOleProperty( String[] propertyPath, Object[] vargs )
	{
		if ( !isActivated( ) )
			return null;
		OleAutomation automation = new OleAutomation( getOleControlSite( ) );
		int[] ids;
		for ( int i = 0; i < propertyPath.length; i++ )
		{
			ids = automation.getIDsOfNames( new String[]{
				propertyPath[i]
			} );
			if ( ids == null )
			{
				automation.dispose( );
				return null;
			}
			if ( i == propertyPath.length - 1 )
			{
				Variant[] params = new Variant[vargs.length];
				for ( int j = 0; j < vargs.length; j++ )
				{
					params[j] = createVariant( vargs[j] );
				}
				Variant propertyVariant = automation.getProperty( ids[0],
						params );
				for ( int j = 0; j < params.length; j++ )
				{
					Variant param = params[j];
					dispose( param );
				}
				Object result = getVariantValue( propertyVariant );
				dispose( propertyVariant );
				automation.dispose( );
				return result;
			}
			Variant variantProperty = automation.getProperty( ids[0] );
			OleAutomation newAutomation = variantProperty.getAutomation( );
			variantProperty.dispose( );
			automation.dispose( );
			automation = newAutomation;
		}
		automation.dispose( );
		return null;
	}

	private String getTypeDescription( short type )
	{
		String description = (String) oleTypeToDescriptionMap.get( new Short( type ) );
		if ( description == null )
		{
			description = "Unknown[" + String.valueOf( type ) + ']';
		}
		return description;
	}

	public void invokeOleFunction( String function )
	{
		invokeOleFunction( new String[]{
			function
		}, new Object[0], false );
	}

	public void invokeOleFunction( String function, Object arg )
	{
		invokeOleFunction( new String[]{
			function
		}, new Object[]{
			arg
		}, false );
	}

	public void invokeOleFunction( String function, Object[] args )
	{
		invokeOleFunction( new String[]{
			function
		}, args, false );
	}

	public void invokeOleFunction( String[] functionPath )
	{
		invokeOleFunction( functionPath, new Object[0], false );
	}

	public void invokeOleFunction( String[] functionPath, Object arg )
	{
		invokeOleFunction( functionPath, new Object[]{
			arg
		}, false );
	}

	public void invokeOleFunction( String[] functionPath, Object[] args )
	{
		if ( !isActivated( ) )
			return;
		invokeOleFunction( functionPath, args, false );
	}

	protected Object invokeOleFunction( String[] propertyPath, Object[] vargs,
			boolean withResult )
	{
		if ( !isActivated( ) )
			return null;
		OleAutomation automation = new OleAutomation( getOleControlSite( ) );
		int[] ids;
		for ( int i = 0; i < propertyPath.length; i++ )
		{
			ids = automation.getIDsOfNames( new String[]{
				propertyPath[i]
			} );
			if ( ids == null )
			{
				automation.dispose( );
				return null;
			}
			if ( i == propertyPath.length - 1 )
			{
				Variant[] params = new Variant[vargs.length];
				for ( int j = 0; j < vargs.length; j++ )
				{
					params[j] = createVariant( vargs[j] );
				}
				Object result;
				if ( withResult )
				{
					Variant resultVariant = automation.invoke( ids[0], params );
					result = getVariantValue( resultVariant );
					dispose( resultVariant );
				}
				else
				{
					result = null;
					automation.invokeNoReply( ids[0], params );
				}
				for ( int j = 0; j < params.length; j++ )
				{
					Variant param = params[j];
					dispose( param );
				}
				automation.dispose( );
				return result;
			}
			Variant variantProperty = automation.getProperty( ids[0] );
			OleAutomation newAutomation = variantProperty.getAutomation( );
			variantProperty.dispose( );
			automation.dispose( );
			automation = newAutomation;
		}
		automation.dispose( );
		return null;
	}

	public Object invokeOleFunctionWithResult( String function )
	{
		return invokeOleFunction( new String[]{
			function
		}, new Object[0], true );
	}

	public Object invokeOleFunctionWithResult( String function, Object arg )
	{
		return invokeOleFunction( new String[]{
			function
		}, new Object[]{
			arg
		}, true );
	}

	public Object invokeOleFunctionWithResult( String function, Object[] args )
	{
		return invokeOleFunction( new String[]{
			function
		}, args, true );
	}

	public Object invokeOleFunctionWithResult( String[] functionPath )
	{
		return invokeOleFunction( functionPath, new Object[0], true );
	}

	public Object invokeOleFunctionWithResult( String[] functionPath, Object arg )
	{
		return invokeOleFunction( functionPath, new Object[]{
			arg
		}, true );
	}

	public Object invokeOleFunctionWithResult( String[] functionPath,
			Object[] args )
	{
		return invokeOleFunction( functionPath, args, true );
	}

	public boolean setOleProperty( String property, Object arg )
	{
		return setOleProperty( new String[]{
			property
		}, new Object[]{
			arg
		} );
	}

	public boolean setOleProperty( String property, Object[] args )
	{
		return setOleProperty( new String[]{
			property
		}, args );
	}

	public boolean setOleProperty( String[] propertyPath, Object arg )
	{
		return setOleProperty( propertyPath, new Object[]{
			arg
		} );
	}

	public boolean setOleProperty( String[] propertyPath, Object[] args )
	{
		if ( !isActivated( ) )
			return false;
		OleAutomation automation = new OleAutomation( getOleControlSite( ) );
		int[] ids;
		for ( int i = 0; i < propertyPath.length; i++ )
		{
			ids = automation.getIDsOfNames( new String[]{
				propertyPath[i]
			} );
			if ( ids == null )
			{
				automation.dispose( );
				return false;
			}
			if ( i == propertyPath.length - 1 )
			{
				Variant[] params = new Variant[args.length];
				for ( int j = 0; j < args.length; j++ )
				{
					params[j] = createVariant( args[j] );
				}
				boolean result = automation.setProperty( ids[0], params );
				for ( int j = 0; j < params.length; j++ )
				{
					Variant param = params[j];
					dispose( param );
				}
				automation.dispose( );
				return result;
			}
			Variant variantProperty = automation.getProperty( ids[0] );
			OleAutomation newAutomation = variantProperty.getAutomation( );
			variantProperty.dispose( );
			automation.dispose( );
			automation = newAutomation;
		}
		automation.dispose( );
		return false;
	}
}
