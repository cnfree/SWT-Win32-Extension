
package org.sf.feeling.swt.win32.extension.jna.datatype;

public class DataType
{
	public static final int INT = 0;
	public static final int UINT = 1;
	public static final int INT8 = 2;
	public static final int UINT8 = 3;
	public static final int INT16 = 4;
	public static final int UINT16 = 5;
	public static final int INT32 = 6;
	public static final int UINT32 = 7;
	public static final int INT64 = 8;
	public static final int UINT64 = 9;
	public static final int bool = 10;
	public static final int CHAR = 12;
	public static final int UCHAR = 13;
	public static final int SCHAR = 14;
	public static final int SHORT = 15;
	public static final int USHORT = 16;
	public static final int LONG = 17;
	public static final int ULONG = 18;
	public static final int LONGLONG = 19;
	public static final int ULONGLONG = 20;
	public static final int FLOAT = 21;
	public static final int DOUBLE = 22;
	public static final int LONGDOUBLE = 23;
	public static final int WCHAR_T = 24;

	public static final int IntPtr = INT;
	public static final int WORD = USHORT;
	public static final int ATOM = WORD;
	public static final int BOOL = INT;
	public static final int BYTE = UCHAR;
	public static final int BOOLEAN = BYTE;
	public static final int DWORD = ULONG;
	public static final int COLORREF = DWORD;
	public static final int DWORDLONG = ULONGLONG;
	public static final int DWORD32 = UINT;
	public static final int DWORD64 = UINT64;
	public static final int LPARAM = LONG;
	public static final int WCHAR = WCHAR_T;
	public static final int WPARAM = UINT;
	public static final int HANDLE = IntPtr;
	public static final int HBITMAP = IntPtr;
	public static final int HBRUSH = IntPtr;
	public static final int HCONV = IntPtr;
	public static final int HCONVLIST = IntPtr;
	public static final int HCURSOR = IntPtr;
	public static final int HDC = IntPtr;
	public static final int HDDEDATA = IntPtr;
	public static final int HDESK = IntPtr;
	public static final int HDROP = IntPtr;
	public static final int HDWP = IntPtr;
	public static final int HENHMETAFILE = IntPtr;
	public static final int HFILE = INT;
	public static final int HFONT = IntPtr;
	public static final int HGIDOBJ = IntPtr;
	public static final int HGLOBAL = IntPtr;
	public static final int HHOOK = IntPtr;
	public static final int HICON = IntPtr;
	public static final int HINSTANCE = IntPtr;
	public static final int HKEY = IntPtr;
	public static final int HKL = IntPtr;
	public static final int HLOCAL = IntPtr;
	public static final int HMENU = IntPtr;
	public static final int HMETAFILE = IntPtr;
	public static final int HMODULE = IntPtr;
	public static final int HMONITOR = IntPtr;
	public static final int HPALETTE = IntPtr;
	public static final int HPEN = IntPtr;
	public static final int HRESULT = LONG;
	public static final int HRGN = IntPtr;
	public static final int HRSRC = IntPtr;
	public static final int HSZ = IntPtr;
	public static final int HWINSTA = IntPtr;
	public static final int HWND = IntPtr;

	public static final int sizeof( int type )
	{
		switch ( type )
		{
			case INT :
				return 4;
			case UINT :
				return 4;
			case INT8 :
				return 1;
			case UINT8 :
				return 1;
			case INT16 :
				return 2;
			case UINT16 :
				return 2;
			case INT32 :
				return 4;
			case UINT32 :
				return 4;
			case INT64 :
				return 8;
			case UINT64 :
				return 8;
			case bool :
				return 1;
			case CHAR :
				return 1;
			case UCHAR :
				return 1;
			case SCHAR :
				return 1;
			case SHORT :
				return 2;
			case USHORT :
				return 2;
			case LONG :
				return 4;
			case ULONG :
				return 4;
			case LONGLONG :
				return 8;
			case ULONGLONG :
				return 8;
			case FLOAT :
				return 4;
			case DOUBLE :
				return 8;
			case LONGDOUBLE :
				return 8;
			case WCHAR_T :
				return 2;
			default :
				throw new IllegalArgumentException( "Unknown Data Type:" + type );
		}
	}
}
