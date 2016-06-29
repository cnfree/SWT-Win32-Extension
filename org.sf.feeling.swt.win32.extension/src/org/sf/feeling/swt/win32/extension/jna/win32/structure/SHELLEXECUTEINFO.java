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

import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.datatype.LONG;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.HANDLE;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.Structure;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.NullPointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.GlobalMemoryBlock;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;

/**
 * <pre>
 * typedef struct _SHELLEXECUTEINFO {
 * 	DWORD cbSize;
 * 	ULONG fMask;
 * 	HWND hwnd;
 * 	LPCTSTR lpVerb;
 * 	LPCTSTR lpFile;
 * 	LPCTSTR lpParameters;
 * 	LPCTSTR lpDirectory;
 * 	int nShow;
 * 	HINSTANCE hInstApp;
 * 	LPVOID lpIDList;
 * 	LPCTSTR lpClass;
 * 	HKEY hkeyClass;
 * 	DWORD dwHotKey;
 * 	union {
 * 		HANDLE hIcon;
 * 		HANDLE hMonitor;
 * 	} DUMMYUNIONNAME;
 * 	HANDLE hProcess;
 * } SHELLEXECUTEINFO, *LPSHELLEXECUTEINFO;
 * </pre>
 */
public class SHELLEXECUTEINFO extends Structure
{

	// SHELLEXECUTEINFO fMask-constants
	public static final int SEE_MASK_CLASSKEY = 0x3; // Struktur wird mit dem
														// Handle des Registry -

	public static final int SEE_MASK_CLASSNAME = 0x1; // Struktur wird mit dem
														// Klassennamen oder
														// GUID

	public static final int SEE_MASK_CONNECTNETDRV = 0x80; // Struktur verbindet
															// den PC mit einem

	public static final int SEE_MASK_DOENVSUBST = 0x200; // Struktur wird mit
															// Umgebungsvariablen
															// des

	public static final int SEE_MASK_FLAG_DDEWAIT = 0x100; // die Funktion
															// wartet, dass die
															// DDE ihre

	public static final int SEE_MASK_FLAG_NO_UI = 0x400; // die Funktion zeigt
															// keine
	// Fehler-Dialogboxen an
	public static final int SEE_MASK_HOTKEY = 0x20;
	public static final int SEE_MASK_ICON = 0x10;
	// Standard-Icons der Anwendung
	public static final int SEE_MASK_IDLIST = 0x4; // benutzt die
													// lpIDLIST-Option um das
													// Programm zu
	// starten, das in ihr beschrieben ist
	public static final int SEE_MASK_INVOKEIDLIST = 0xC; // benutzt die
															// lpIDLIST-Option
															// um das
	// Programm zu starten, das in ihr beschrieben ist
	public static final int SEE_MASK_NOCLOSEPROCESS = 0x40;
	// mit dem Process-Handle der gestarteten Anwendung

	// SHELLEXECUTEINFO hInstApp return constants
	public static final int SE_ERR_ACCESSDENIED = 5; // Zugriff verweigert
	public static final int SE_ERR_ASSOCINCOMPLETE = 27; // Dateityp ist nicht
															// ausreichend
															// assoziiert
	public static final int SE_ERR_DDEBUSY = 30; // DDE konnte nicht gestartet
													// werden
	public static final int SE_ERR_DDEFAIL = 29; // DDE ist gescheitert
	public static final int SE_ERR_DDETIMEOUT = 28; // DDE-Zeitlimit wurde
													// ereicht
	public static final int SE_ERR_DLLNOTFOUND = 32;
	public static final int SE_ERR_FNF = 2; // Datei wurde nicht gefunden
	public static final int SE_ERR_NOASSOC = 31; // Dateityp ist nicht
													// assoziiert
	public static final int SE_ERR_OOM = 8;
	public static final int SE_ERR_PNF = 3; // Pfad wurde nicht gefunden
	public static final int SE_ERR_SHARE = 26;
	// verwendet wird

	// WaitForSingleObject dwMillisecond-constant
	public static final int INFINITE = 0xFFFF; // infinite wait

	// WaitForSingleObject return constant
	public static final int WAIT_ABANDONED = 0x80; // der Mutex, der in hHanlde
													// angegeben ist wird

	// Bestandteil des aufrufenden Threads und ist nicht mehr im
	// signalisierenden Status
	public static final int WAIT_FAILED = 0xFFFFFFFF; // die Funktion ist
														// gescheitert
	public static final int WAIT_OBJECT_0 = 0x0; // das Objekt. das in hHandle
													// spezifiziert ist,
	// ist in einem signalisierendem Status
	public static final int WAIT_TIMEOUT = 0x102;
	// Thread-Statuses ist abgelaufen

	public int fMask;
	public HWND hwnd;
	public String lpVerb;
	public String lpFile;
	public String lpParameters;
	public String lpDirectory;
	public int nShow;
	public LONG hInstApp;
	public ITEMID lpIDList;
	public String lpClass;
	public HKEY hkeyClass;
	public int dwHotKey;
	public int union;
	public HANDLE hProcess;

	private final Pointer lpVerbPointer;
	private final Pointer lpFilePointer;
	private final Pointer lpParametersPointer;
	private final Pointer lpDirectoryPointer;
	private final Pointer lpClassPointer;

	/** Creates a new instance of SHELLEXECUTEINFO */
	public SHELLEXECUTEINFO( ) throws NativeException
	{
		super( );
		lpVerbPointer = new Pointer( MemoryBlockFactory.createMemoryBlock( 256 ) );
		lpFilePointer = new Pointer( MemoryBlockFactory.createMemoryBlock( 256 ) );
		lpParametersPointer = new Pointer( MemoryBlockFactory.createMemoryBlock( 256 ) );
		lpDirectoryPointer = new Pointer( MemoryBlockFactory.createMemoryBlock( 256 ) );
		lpClassPointer = new Pointer( MemoryBlockFactory.createMemoryBlock( 256 ) );
	}

	private void toPointer( ) throws NativeException
	{
		lpVerbPointer.zeroMemory( );
		if ( lpVerb != null )
		{
			lpVerbPointer.setStringAt( 0, lpVerb );
		}
		lpFilePointer.zeroMemory( );
		if ( lpFile != null )
		{
			lpFilePointer.setStringAt( 0, lpFile );
		}
		lpParametersPointer.zeroMemory( );
		if ( lpParameters != null )
		{
			lpParametersPointer.setStringAt( 0, lpParameters );
		}
		lpDirectoryPointer.zeroMemory( );
		if ( lpDirectory != null )
		{
			lpDirectoryPointer.setStringAt( 0, lpDirectory );
		}
		lpClassPointer.zeroMemory( );
		if ( lpClass != null )
		{
			lpClassPointer.setStringAt( 0, lpClass );
		}

		offset = 0;

		offset += pointer.setIntAt( offset, getSizeOf( ) );
		offset += pointer.setIntAt( offset, fMask );
		offset += pointer.setIntAt( offset,
				hwnd == null ? NullPointer.NULL.getPointer( ) : hwnd.getValue( ) );
		offset += pointer.setIntAt( offset,
				lpVerb == null ? NullPointer.NULL.getPointer( )
						: lpVerbPointer.getPointer( ) );
		offset += pointer.setIntAt( offset,
				lpFile == null ? NullPointer.NULL.getPointer( )
						: lpFilePointer.getPointer( ) );
		offset += pointer.setIntAt( offset,
				lpParameters == null ? NullPointer.NULL.getPointer( )
						: lpParametersPointer.getPointer( ) );
		offset += pointer.setIntAt( offset,
				lpDirectory == null ? NullPointer.NULL.getPointer( )
						: lpDirectoryPointer.getPointer( ) );
		offset += pointer.setIntAt( offset, nShow );
		offset += pointer.setIntAt( offset,
				hInstApp == null ? NullPointer.NULL.getPointer( )
						: hInstApp.getValue( ) );
		offset += pointer.setIntAt( offset,
				lpIDList == null ? NullPointer.NULL.getPointer( )
						: Integer.parseInt( lpIDList.getSpecialPath( ) ) );
		offset += pointer.setIntAt( offset,
				lpClass == null ? NullPointer.NULL.getPointer( )
						: lpClassPointer.getPointer( ) );
		offset += pointer.setIntAt( offset,
				hkeyClass == null ? NullPointer.NULL.getPointer( )
						: hkeyClass.getValue( ) );
		offset += pointer.setIntAt( offset, dwHotKey );
		offset += pointer.setIntAt( offset, union );
		pointer.setIntAt( offset,
				hProcess == null ? NullPointer.NULL.getPointer( )
						: hProcess.getValue( ) );

		offset = 0;
	}

	private void fromPointer( ) throws NativeException
	{
		getNextInt( );
		fMask = getNextInt( );
		hwnd = new HWND( getNextInt( ) );

		int nextInt = getNextInt( );
		if ( nextInt != 0 )
		{
			lpVerb = Native.getMemoryAsString( nextInt, 256 );
		}

		nextInt = getNextInt( );
		if ( nextInt != 0 )
		{
			lpFile = Native.getMemoryAsString( nextInt, 256 );
		}
		nextInt = getNextInt( );
		if ( nextInt != 0 )
		{
			lpParameters = Native.getMemoryAsString( nextInt, 256 );
		}
		nextInt = getNextInt( );
		if ( nextInt != 0 )
		{
			lpDirectory = Native.getMemoryAsString( nextInt, 256 );
		}

		nShow = getNextInt( );
		hInstApp = new LONG( getNextInt( ) );

		lpIDList = new ITEMID( );
		lpIDList.setData( Native.getMemory( getNextInt( ), 5 ) ); // is this
																	// correct??

		nextInt = getNextInt( );
		if ( nextInt != 0 )
		{
			lpClass = Native.getMemoryAsString( nextInt, 256 );
		}

		hkeyClass = new HKEY( getNextInt( ) );
		dwHotKey = getNextInt( );
		union = getNextInt( );
		hProcess = new HANDLE( getNextInt( ) );
	}

	public Pointer createPointer( ) throws NativeException
	{
		if ( pointer == null )
		{
			pointer = new Pointer( new GlobalMemoryBlock( getSizeOf( ) ) );
		}
		toPointer( );
		return pointer;
	}

	public int getSizeOf( )
	{
		return sizeOf( );
	}

	public static int sizeOf( )
	{
		return 60;
	}

	public Object getValueFromPointer( ) throws NativeException
	{
		fromPointer( );
		return this;
	}

	public SHELLEXECUTEINFO getValue( ) throws NativeException
	{
		return (SHELLEXECUTEINFO) getValueFromPointer( );
	}
}
