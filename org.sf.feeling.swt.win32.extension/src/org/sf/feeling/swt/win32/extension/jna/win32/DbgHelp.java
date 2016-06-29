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
package org.sf.feeling.swt.win32.extension.jna.win32;

import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.Type;
import org.sf.feeling.swt.win32.extension.jna.datatype.LONG;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;

public class DbgHelp
{
	public static final String DLL_NAME = "DbgHelp.dll";

	/**
	 * <b>ImageRvaToVa</b>
	 * <pre>
	 
	 Locates a relative virtual address (RVA) within the image header of a file that is mapped as a file and returns the virtual address of the corresponding byte in the file.
	 
	 PVOID ImageRvaToVa(
	 PIMAGE_NT_HEADERS NtHeaders,
	 PVOID Base,
	 ULONG Rva,
	 PIMAGE_SECTION_HEADER* LastRvaSection
	 );
	 
	 Parameters
	 
	 NtHeaders
	 [in] A pointer to an IMAGE_NT_HEADERS structure. This structure can be obtained by calling the ImageNtHeader function.
	 Base
	 [in] The base address of an image that is mapped into memory through a call to the MapViewOfFile function.
	 Rva
	 [in] The relative virtual address to be located.
	 LastRvaSection
	 [in, out] A pointer to an IMAGE_SECTION_HEADER structure that specifies the last RVA section. This is an optional parameter. When specified, it points to a variable that contains the last section value used for the specified image to translate an RVA to a VA.
	 
	 Return Values
	 
	 If the function succeeds, the return value is the virtual address in the mapped file.
	 
	 If the function fails, the return value is NULL. To retrieve extended error information, call GetLastError.
	 </pre>
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	public static LONG ImageRvaToVa(Pointer NtHeaders, Pointer Base, LONG Rva, Pointer LastRvaSection) throws NativeException, IllegalAccessException
	{
		Native nImageRvaToVa = new Native(DLL_NAME, "ImageRvaToVa");
		nImageRvaToVa.setRetVal(Type.INT);
		nImageRvaToVa.setParameter(0, NtHeaders);
		nImageRvaToVa.setParameter(1, Base);
		nImageRvaToVa.setParameter(2, Rva.getValue());
		nImageRvaToVa.setParameter(3, LastRvaSection);
		nImageRvaToVa.invoke();
		return new LONG(nImageRvaToVa.getRetValAsInt());
	}
	
}
