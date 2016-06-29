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

import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

public class DEV_BROADCAST_HDR extends Structure {
	public static final int DBT_DEVTYP_OEM = 0x00000000;
	public static final int DBT_DEVTYP_DEVNODE = 0x00000001;
	public static final int DBT_DEVTYP_VOLUME = 0x00000002;
	public static final int DBT_DEVTYP_PORT = 0x00000003;
	public static final int DBT_DEVTYP_NET = 0x00000004;
	public static final int DBT_DEVTYP_DEVICEINTERFACE = 0x00000005;
	public static final int DBT_DEVTYP_HANDLE = 0x00000006;

			

	protected DWORD	dbch_size = new DWORD(getSizeOf());
	protected DWORD	dbch_devicetype = new DWORD(0);
	protected DWORD	dbch_reserved = new DWORD(0);

	public DEV_BROADCAST_HDR() {
		super();
		mValue = this;
	}
	public DEV_BROADCAST_HDR(Pointer ptr) {
		this();
		this.pointer = ptr;
		try {
			getValueFromPointer();
		} catch (NativeException e) {
			throw new RuntimeException( e );
		}
	}
	
	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		return pointer;
	}

	public int getSizeOf() {
		return getSize();
	}

	public static int getSize() {
		return 3*4;
	}
	
	public Object getValueFromPointer() throws NativeException {
		offset = 0;
		dbch_size.setValue(getNextInt());
		dbch_devicetype.setValue(getNextInt());
		dbch_reserved.setValue(getNextInt());
		return this;
	}
	
	public DWORD getDbch_size() {
		return dbch_size;
	}

	public DWORD getDbch_devicetype() {
		return dbch_devicetype;
	}

	public DWORD getDbch_reserved() {
		return dbch_reserved;
	}


}
