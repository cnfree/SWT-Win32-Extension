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

import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class FileVersionInfo
{

	private String companyName;
	private String fileDescription;
	private String fileVersion;
	private String internalName;
	private String legalCopyright;
	private String originalFilename;
	private String productName;
	private String productVersion;
	private String comments;
	private String legalTrademarks;
	private String privateBuild;
	private String specialBuild;
	private int[] versions;

	public String getCompanyName( )
	{
		return companyName;
	}

	public String getFileDescription( )
	{
		return fileDescription;
	}

	public String getFileVersion( )
	{
		return fileVersion;
	}

	public String getInternalName( )
	{
		return internalName;
	}

	public String getLegalCopyright( )
	{
		return legalCopyright;
	}

	public String getOriginalFilename( )
	{
		return originalFilename;
	}

	public String getProductName( )
	{
		return productName;
	}

	public String getProductVersion( )
	{
		return productVersion;
	}

	public String getComments( )
	{
		return comments;
	}

	public String getLegalTrademarks( )
	{
		return legalTrademarks;
	}

	public String getPrivateBuild( )
	{
		return privateBuild;
	}

	public String getSpecialBuild( )
	{
		return specialBuild;
	}

	public int getMajorVersion( )
	{
		return versions[0];
	}

	public int getMinorVersion( )
	{
		return versions[1];
	}

	public int[] getVersionDetail( )
	{
		return versions;
	}

	/**
	 * Create a new FileVersionInfo instance.
	 * 
	 */
	public FileVersionInfo( )
	{
	}

	/**
	 * Load the specified dll or exe file.
	 * 
	 * @param fileName
	 *            the name of library to be loaded.
	 */
	public void loadVersionInfo( String fileName )
	{
		int[] version = Extension.GetFileVersionInfo( fileName );
		if ( version != null )
		{
			int handle = version[0];
			companyName = Extension.GetFileVersionInfoValue( handle,
					"CompanyName" );
			fileDescription = Extension.GetFileVersionInfoValue( handle,
					"FileDescription" );
			fileVersion = Extension.GetFileVersionInfoValue( handle,
					"FileVersion" );
			internalName = Extension.GetFileVersionInfoValue( handle,
					"InternalName" );
			legalCopyright = Extension.GetFileVersionInfoValue( handle,
					"LegalCopyright" );
			originalFilename = Extension.GetFileVersionInfoValue( handle,
					"OriginalFilename" );
			productName = Extension.GetFileVersionInfoValue( handle,
					"ProductName" );
			productVersion = Extension.GetFileVersionInfoValue( handle,
					"ProductVersion" );
			comments = Extension.GetFileVersionInfoValue( handle, "Comments" );
			legalTrademarks = Extension.GetFileVersionInfoValue( handle,
					"LegalTrademarks" );
			privateBuild = Extension.GetFileVersionInfoValue( handle,
					"PrivateBuild" );
			specialBuild = Extension.GetFileVersionInfoValue( handle,
					"SpecialBuild" );
			versions = new int[4];
			System.arraycopy( version, 1, versions, 0, 4 );
			Extension.FileVersionInfo_delete( handle );
		}
		else
		{
			companyName = null;
			fileDescription = null;
			fileVersion = null;
			internalName = null;
			legalCopyright = null;
			originalFilename = null;
			productName = null;
			productVersion = null;
			comments = null;
			legalTrademarks = null;
			privateBuild = null;
			specialBuild = null;
			versions = null;
		}
	}
}
