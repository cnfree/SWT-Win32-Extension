/*******************************************************************************
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

package org.sf.feeling.swt.win32.extension.system;

import org.eclipse.swt.internal.win32.OSVERSIONINFO;
import org.eclipse.swt.internal.win32.OSVERSIONINFOA;
import org.eclipse.swt.internal.win32.OSVERSIONINFOW;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * This provides version information about underlying Windows platform.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class OSVersionInfo
{

	/*
	 * Members from OSVERSIONINFO structure
	 */
	private int majorVersion;
	private int minorVersion;
	private int buildNumber;
	private int platformId;
	private String cSDVersion = "";
	private static OSVersionInfo versionInfo = null;

	private OSVersionInfo( )
	{
	}

	/**
	 * Get a global OSVersionInfo instance.
	 * 
	 * @return a global OSVersionInfo instance.
	 */
	public synchronized static OSVersionInfo getInstance( )
	{
		if ( versionInfo == null )
		{
			OSVERSIONINFO info = new OSVERSIONINFOW( );
			info.dwOSVersionInfoSize = OSVERSIONINFOW.sizeof;
			if ( !Extension.GetVersionExW( (OSVERSIONINFOW) info ) )
			{
				info = new OSVERSIONINFOA( );
				info.dwOSVersionInfoSize = OSVERSIONINFOA.sizeof;
				Extension.GetVersionExA( (OSVERSIONINFOA) info );
			}
			versionInfo = new OSVersionInfo( );
			versionInfo.buildNumber = info.dwBuildNumber;
			versionInfo.majorVersion = info.dwMajorVersion;
			versionInfo.minorVersion = info.dwMinorVersion;
			versionInfo.platformId = info.dwPlatformId;
			if ( info instanceof OSVERSIONINFOW )
				versionInfo.cSDVersion = new String( ( (OSVERSIONINFOW) info ).szCSDVersion ).trim( );
			else
				versionInfo.cSDVersion = new String( ( (OSVERSIONINFOA) info ).szCSDVersion ).trim( );
		}
		return versionInfo;
	}

	/**
	 * Get OS build number.
	 * 
	 * @return OS build number.
	 */
	public int getBuildNumber( )
	{
		int result = buildNumber;
		if ( isNT( ) )
		{
			return result;
		}
		// Extract build number from the structure value.
		// On Windows 95/98/Me the low-order word contains the build number and
		// the high-order word contains the major and minor version numbers.
		return result & 0xFFFF;
	}

	/**
	 * Get OS major version.
	 * 
	 * @return OS major version.
	 */
	public int getMajor( )
	{
		return majorVersion;
	}

	/**
	 * Get OS minor version.
	 * 
	 * @return OS minor version.
	 */
	public int getMinor( )
	{
		return minorVersion;
	}

	/**
	 * Returns operating system platform, which can be one of the
	 * <code>VER_PLATFORM_XXX</code> values.
	 */
	public int getPlatformId( )
	{
		return platformId;
	}

	/**
	 * Returns additional version information string. For Windows NT/2000/XP
	 * this string represents Service Pack, or is empty if no Service Pack has
	 * been installed. For Windows 95/98/Me the result indicates additional
	 * version information. For example, " C" indicates Windows 95 OSR2 and " A"
	 * indicates Windows 98 Second Edition.
	 */
	public String getServicePack( )
	{
		return cSDVersion;
	}

	public boolean isWinCE( )
	{
		return getPlatformId( ) == Win32.VER_PLATFORM_WIN32_CE;
	}

	public boolean isNT( )
	{
		return getPlatformId( ) == Win32.VER_PLATFORM_WIN32_NT;
	}

	public boolean isWin95( )
	{
		return getPlatformId( ) == Win32.VER_PLATFORM_WIN32_WINDOWS
				&& getMajor( ) == 4
				&& getMinor( ) == 0;
	}

	public boolean isWin98( )
	{
		return getPlatformId( ) == Win32.VER_PLATFORM_WIN32_WINDOWS
				&& getMajor( ) == 4
				&& getMinor( ) == 10;
	}

	public boolean isWin2k( )
	{
		return getMajor( ) == 5
				&& getMinor( ) == 0
				&& getPlatformId( ) == Win32.VER_PLATFORM_WIN32_NT;
	}

	public boolean isWinNT4( )
	{
		return getMajor( ) == 4
				&& getPlatformId( ) == Win32.VER_PLATFORM_WIN32_NT;
	}

	public boolean isWinMe( )
	{
		return ( getPlatformId( ) == Win32.VER_PLATFORM_WIN32_WINDOWS )
				&& getMajor( ) == 4
				&& getMinor( ) == 90;
	}

	public boolean isWinXP( )
	{
		return getMajor( ) == 5
				&& getMinor( ) == 1
				&& getPlatformId( ) == Win32.VER_PLATFORM_WIN32_NT;
	}

	public boolean isWin2003( )
	{
		return getMajor( ) == 5
				&& getMinor( ) == 2
				&& getPlatformId( ) == Win32.VER_PLATFORM_WIN32_NT;
	}

	public boolean isWinVista( )
	{
		return getMajor( ) >= 6;
	}

	public String getWindowVersion( )
	{
		String version = "";
		if ( platformId == Win32.VER_PLATFORM_WIN32_NT )
		{
			version = "Windows NT 3";
			if ( majorVersion == 4 )
				version = "Windows NT 4";
			if ( majorVersion == 5 && minorVersion == 0 )
				version = "Windows 2000";
			if ( majorVersion == 5 && minorVersion == 1 )
				version = "Windows XP";
			if ( majorVersion == 6 && minorVersion == 0 )
				version = "Windows Vista";
			if ( majorVersion == 6 && minorVersion == 1 )
				version = "Windows 7";
		}
		else if ( platformId == Win32.VER_PLATFORM_WIN32_WINDOWS )
		{
			version = "Windows 95";
			if ( majorVersion == 4 && minorVersion == 10 )
				version = "Windows 98";
			else if ( majorVersion == 4 && minorVersion == 90 )
				version = "Windows ME";
		}
		return version;
	}

	public String toString( )
	{
		StringBuffer info = new StringBuffer( );
		info.append( getWindowVersion( ) );
		info.append( " " );
		info.append( getMajor( ) );
		info.append( "." );
		info.append( getMinor( ) );
		info.append( " " );
		info.append( getServicePack( ) );
		return info.toString( );

	}
}
