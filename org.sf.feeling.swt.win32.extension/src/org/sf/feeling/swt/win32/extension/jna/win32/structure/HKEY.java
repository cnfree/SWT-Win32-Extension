/*******************************************************************************
 * Copyright (c) 2011 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/
package org.sf.feeling.swt.win32.extension.jna.win32.structure;

import org.sf.feeling.swt.win32.extension.jna.datatype.win32.HANDLE;



/**
 * 	 HKEY Handles.
 */
public class HKEY extends HANDLE {
	public static final HKEY  HKEY_CLASSES_ROOT           = new HKEY(0x80000000) ;
	public static final HKEY  HKEY_CURRENT_USER           = new HKEY(0x80000001) ;
	public static final HKEY  HKEY_LOCAL_MACHINE          = new HKEY(0x80000002) ;
	public static final HKEY  HKEY_USERS                  = new HKEY(0x80000003) ;
	public static final HKEY  HKEY_PERFORMANCE_DATA       = new HKEY(0x80000004) ;
	public static final HKEY  HKEY_PERFORMANCE_TEXT       = new HKEY(0x80000050) ;
	public static final HKEY  HKEY_PERFORMANCE_NLSTEXT    = new HKEY(0x80000060) ;
	public static final HKEY  HKEY_CURRENT_CONFIG         = new HKEY(0x80000005) ;
	public static final HKEY  HKEY_DYN_DATA               = new HKEY(0x80000006) ;
	
	public HKEY(int value) {
		super(value);
	}
}

