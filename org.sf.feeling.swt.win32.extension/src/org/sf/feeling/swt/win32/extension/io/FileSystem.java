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

package org.sf.feeling.swt.win32.extension.io;

import java.io.File;

import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.jna.win32.Kernel32;
import org.sf.feeling.swt.win32.internal.extension.DISKFREESPACE;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * This class is a utility class about File System.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class FileSystem
{

	public static final int DRIVE_TYPE_CDROM = Win32.DRIVE_TYPE_CDROM;
	public static final int DRIVE_TYPE_FIXED = Win32.DRIVE_TYPE_FIXED;
	public static final int DRIVE_TYPE_NOT_EXIST = Win32.DRIVE_TYPE_NOT_EXIST;
	public static final int DRIVE_TYPE_RAMDISK = Win32.DRIVE_TYPE_RAMDISK;
	public static final int DRIVE_TYPE_REMOTE = Win32.DRIVE_TYPE_REMOTE;
	public static final int DRIVE_TYPE_REMOVABLE = Win32.DRIVE_TYPE_REMOVABLE;
	public static final int DRIVE_TYPE_UNKNOW = Win32.DRIVE_TYPE_UNKNOW;

	/**
	 * Returns the drive type of a specified disk.
	 * 
	 * @param diskName
	 *            is a drive letter like A:\, C:\ etc. i.e. root folder. If the
	 *            passed path is not a root folder, the function will not
	 *            determine the drive type and will return DRIVE_TYPE_NOT_EXIST
	 *            type.
	 * @return the drive type.
	 */
	public static int getDriveType( String diskName )
	{
		return Extension.GetDriveType( diskName );
	}

	/**
	 * Returns the drive type of a specified disk.
	 * 
	 * @param drive
	 *            is a root file like A:\, C:\ etc., otherwise if the passed
	 *            file is not a root folder, the function will not determine the
	 *            drive type and will return DRIVE_TYPE_NOT_EXIST type.
	 * @return drive type.
	 */
	public static int getDriveType( File drive )
	{
		return Extension.GetDriveType( drive.getAbsolutePath( ) );
	}

	/**
	 * Returns the amount of space available on the disk in bytes.
	 * 
	 * @param path
	 *            specifies the directory on the disk.
	 * @return the number of free bytes available on the disk.
	 */
	public static long getDiskFreeSpace( String path )
	{
		DISKFREESPACE diskSpace = Extension.GetDiskFreeSpace( path );
		return diskSpace.freeBytesAvailable;
	}

	/**
	 * Returns the amount of space available on the disk in bytes.
	 * 
	 * @param path
	 *            specifies the directory on the disk.
	 * @return the number of free bytes available on the disk.
	 */
	public static long getDiskFreeSpace( File path )
	{
		return getDiskFreeSpace( path.getAbsolutePath( ) );
	}

	/**
	 * Returns the total size of the disk in bytes.
	 * 
	 * @param path
	 *            specifies the directory on the disk.
	 * @return the number of bytes available on the disk.
	 */
	public static long getDiskTotalSize( String path )
	{
		DISKFREESPACE diskSpace = Extension.GetDiskFreeSpace( path );
		return diskSpace.totalNumberOfBytes;
	}

	/**
	 * Returns the total size of the disk in bytes.
	 * 
	 * @param path
	 *            specifies directory on the disk.
	 * @return the number of bytes available on the disk.
	 */
	public static long getDiskTotalSize( File path )
	{
		return getDiskTotalSize( path.getAbsolutePath( ) );
	}

	/**
	 * Returns the logical drives' name.
	 * 
	 * @return the logical drives' name.
	 */
	public static String[] getLogicalDrives( )
	{
		return Extension.GetLogicalDrives( );
	}

	/**
	 * Returns the volume serial number of specified drive.
	 * 
	 * @param path
	 *            the path of specified drive
	 * @return the volume serial number of specified drive.
	 */
	public static String getVolumeSerialNumber( String path )
	{
		return Extension.GetVolumeSerialNumber( path );
	}

	/**
	 * Returns the volume serial number of specified drive.
	 * 
	 * @param path
	 *            the path of specified drive
	 * @return the volume serial number of specified drive.
	 */
	public static String getVolumeSerialNumber( File path )
	{
		return getVolumeSerialNumber( path.getAbsolutePath( ) );
	}

	/**
	 * Returns the volume label of specified drive.
	 * 
	 * @param path
	 *            the path of specified drive
	 * @return the volume label of specified drive.
	 */
	public static String getVolumeLabel( String path )
	{
		return Extension.GetVolumeLabel( path );
	}

	/**
	 * Returns the volume label of specified drive.
	 * 
	 * @param path
	 *            the path of specified drive
	 * @return the volume label of specified drive.
	 */
	public static String getVolumeLabel( File path )
	{
		return getVolumeLabel( path.getAbsolutePath( ) );
	}

	/**
	 * Set the volume label of specified drive.
	 * 
	 * @param path
	 *            the path of specified drive
	 * @param label
	 *            the text of label
	 */
	public static boolean setVolumeLabel( String path, String label )
	{
		return Extension.SetVolumeLabel( path, label );
	}

	/**
	 * Set the volume label of specified drive.
	 * 
	 * @param path
	 *            the path of specified drive
	 * @param label
	 *            the text of label
	 */
	public static boolean setVolumeLabel( File path, String label )
	{
		return setVolumeLabel( path.getAbsolutePath( ), label );
	}

	/**
	 * Get the application running directory.
	 * 
	 * @return the application running directory.
	 */
	public static String getCurrentDirectory( )
	{
		return Extension.GetCurrentDirectory( );
	}

	/**
	 * Set the application running directory.
	 * 
	 * @param path
	 *            the path of directory
	 * @return if set the directory successfully.
	 */
	public static boolean setCurrentDirectory( String path )
	{
		return Extension.SetCurrentDirectory( path );
	}

	/**
	 * Set the application running directory.
	 * 
	 * @param path
	 *            the path of directory
	 * @return if set the directory successfully.
	 */
	public static boolean setCurrentDirectory( File path )
	{
		return setCurrentDirectory( path.getAbsolutePath( ) );
	}

	/**
	 * Get windows system directory
	 * 
	 * @return system directory
	 */
	public static String getSystemDirectory( )
	{
		return Extension.GetSystemDirectory( );
	}

	/**
	 * Get windows directory
	 * 
	 * @return windows directory
	 */
	public static String getWindowsDirectory( )
	{
		return Extension.GetWindowsDirectory( );
	}

	/**
	 * Get windows temp directory
	 * 
	 * @return windows temp directory
	 */
	public static String getTempPath( )
	{
		return Extension.GetTempPath( );
	}

	/**
	 * Transform windows short path name into long path name.
	 * 
	 * @param shortPathName
	 *            windows short path name
	 * @return windows long path name
	 */
	public static String getLongPathName( String shortPathName )
	{
		return Extension.GetLongPathName( shortPathName );
	}

	public static String getShortPathName( String longPathName )
			throws IllegalArgumentException
	{
		try
		{
			File file = new File( longPathName );
			if ( file.exists( ) )
				return Kernel32.GetShortPathName( longPathName );
		}
		catch ( Exception e )
		{
			throw new IllegalArgumentException( );
		}
		throw new IllegalArgumentException( "The path is not exist." );
	}

}
