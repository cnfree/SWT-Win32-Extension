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
package org.sf.feeling.swt.win32.extension;

import org.eclipse.swt.SWT;
import org.sf.feeling.swt.win32.extension.shell.ShellIcon;
import org.sf.feeling.swt.win32.extension.shell.ShellSystem;
import org.sf.feeling.swt.win32.internal.extension.EventObject;
import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * This class provides access to a small number of SWT Win32 Extension
 * system-wide methods, and in addition defines the public constants provided by
 * SWT Win32 Extension.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public class Win32 extends SWT {
	public static final int ABS_DOWNDISABLED = 8;

	public static final int ABS_DOWNHOT = 6;

	public static final int ABS_DOWNNORMAL = 5;

	public static final int ABS_DOWNPRESSED = 7;

	public static final int ABS_LEFTDISABLED = 12;

	public static final int ABS_LEFTHOT = 10;

	public static final int ABS_LEFTNORMAL = 9;

	public static final int ABS_LEFTPRESSED = 11;

	public static final int ABS_RIGHTDISABLED = 16;

	public static final int ABS_RIGHTHOT = 14;

	public static final int ABS_RIGHTNORMAL = 13;

	public static final int ABS_RIGHTPRESSED = 15;

	public static final int ABS_UPDISABLED = 4;

	public static final int ABS_UPHOT = 2;

	public static final int ABS_UPNORMAL = 1;

	public static final int ABS_UPPRESSED = 3;

	public static final int AC_SRC_ALPHA = 1;

	public static final int AC_SRC_OVER = 0;

	// public static final int ACTCTX_FLAG_RESOURCE_NAME_VALID = 0x00000008;
	// public static final int ACTCTX_FLAG_SET_PROCESS_DEFAULT = 0x00000010;
	public static final int ALTERNATE = 1;

	/**
	 * Notifies the system that an appbar has been activated.
	 */
	public static final int AMB_ACTIVATE = 0x00000006;

	/**
	 * Retrieves the handle to the autohide appbar associated with a particular
	 * edge of the screen.
	 */
	public static final int AMB_GETAUTOHIDEBAR = 0x00000007;

	/**
	 * Retrieves the autohide and always-on-top states of the Microsoft Windows
	 * taskbar.
	 */
	public static final int AMB_GETSTATE = 0x00000004;

	/**
	 * Retrieves the bounding rectangle of the Windows taskbar.
	 */
	public static final int AMB_GETTASKBARPOS = 0x00000005;

	/**
	 * Registers a new appbar.
	 */
	public static final int AMB_NEW = 0x00000000;

	/**
	 * Requests the size and screen position for an appbar.
	 */
	public static final int AMB_QUERYPOS = 0x00000002;

	/**
	 * Unregisters an appbar.
	 * 
	 */
	public static final int AMB_REMOVE = 0x00000001;

	/**
	 * Registers or unregisters an autohide appbar for an edge of the screen.
	 * 
	 */
	public static final int AMB_SETAUTOHIDEBAR = 0x00000008;

	/**
	 * Sets the size and screen position of an appbar.
	 * 
	 */
	public static final int AMB_SETPOS = 0x00000003;

	/**
	 * Sets the state of the appbar's autohide and always-on-top attributes.
	 * 
	 */
	public static final int AMB_SETSTATE = 0x0000000a;

	/**
	 * Notifies the system when an appbar's position has changed.
	 * 
	 */
	public static final int AMB_WINDOWPOSCHANGED = 0x00000009;

	public static final int ASSOCF_NOTRUNCATE = 0x00000020;

	public static final int ASSOCSTR_COMMAND = 1;

	public static final int ASSOCSTR_DEFAULTICON = 15;

	public static final int ASSOCSTR_FRIENDLYAPPNAME = 4;

	public static final int ASSOCSTR_FRIENDLYDOCNAME = 3;

	public static final int ATTR_CONVERTED = 0x02;

	public static final int ATTR_FIXEDCONVERTED = 0x05;

	public static final int ATTR_INPUT = 0x00;

	public static final int ATTR_INPUT_ERROR = 0x04;

	public static final int ATTR_TARGET_CONVERTED = 0x01;

	public static final int ATTR_TARGET_NOTCONVERTED = 0x03;

	public static final int AW_ACTIVATE = 0x00020000;

	public static final int AW_BLEND = 0x00080000;

	public static final int AW_CENTER = 0x00000010;

	public static final int AW_HIDE = 0x00010000;

	public static final int AW_HOR_NEGATIVE = 0x00000002;

	public static final int AW_HOR_POSITIVE = 0x00000001;

	public static final int AW_SLIDE = 0x00040000;

	public static final int AW_VER_NEGATIVE = 0x00000008;

	public static final int AW_VER_POSITIVE = 0x00000004;

	public static final int BCM_FIRST = 0x1600;

	public static final int BCM_GETIDEALSIZE = BCM_FIRST + 0x1;

	public static final int BCM_GETIMAGELIST = BCM_FIRST + 0x3;

	public static final int BCM_GETNOTE = BCM_FIRST + 0xa;

	public static final int BCM_GETNOTELENGTH = BCM_FIRST + 0xb;

	public static final int BCM_SETIMAGELIST = BCM_FIRST + 0x2;

	public static final int BCM_SETNOTE = BCM_FIRST + 0x9;

	public static final int BDR_INNER = 0x000c;

	public static final int BDR_OUTER = 0x0003;

	public static final int BDR_RAISED = 0x0005;

	public static final int BDR_RAISEDINNER = 0x0004;

	public static final int BDR_RAISEDOUTER = 0x0001;

	public static final int BDR_SUNKEN = 0x000a;

	public static final int BDR_SUNKENINNER = 0x0008;

	public static final int BDR_SUNKENOUTER = 0x0002;

	public static final int BF_ADJUST = 0x2000;

	public static final int BF_BOTTOM = 0x0008;

	public static final int BF_LEFT = 0x0001;

	public static final int BF_RIGHT = 0x0004;

	public static final int BF_TOP = 0x0002;

	public static final int BF_RECT = (BF_LEFT | BF_TOP | BF_RIGHT | BF_BOTTOM);

	public static final int BFFM_INITIALIZED = 0x1;

	public static final int BFFM_SETSELECTION = Extension.IsUnicode ? 0x467
			: 0x466;

	public static final int BFFM_VALIDATEFAILED = Extension.IsUnicode ? 0x4
			: 0x3;

	public static final int BFFM_VALIDATEFAILEDA = 0x3;

	public static final int BFFM_VALIDATEFAILEDW = 0x4;

	public static final int BI_BITFIELDS = 3;

	public static final int BI_RGB = 0;

	public static final int BIF_EDITBOX = 0x10;

	public static final int BIF_NEWDIALOGSTYLE = 0x40;

	public static final int BIF_RETURNONLYFSDIRS = 0x1;

	public static final int BIF_VALIDATE = 0x20;

	public static final int BITSPIXEL = 0xc;

	public static final int BLACK_BRUSH = 4;

	public static final int BLACKNESS = 0x42;

	public static final int BM_CLICK = 0xf5;

	public static final int BM_GETCHECK = 0xf0;

	public static final int BM_GETIMAGE = 0x00F6;

	public static final int BM_GETSTATE = 0x00F2;

	public static final int BM_SETCHECK = 0x00F1;

	public static final int BM_SETIMAGE = 0x00F7;

	public static final int BM_SETSTATE = 0x00F3;

	public static final int BM_SETSTYLE = 0x00F4;

	public static final int BN_CLICKED = 0x0;

	public static final int BN_DBLCLK = 5;

	public static final int BN_DISABLE = 4;

	public static final int BN_DOUBLECLICKED = 5;

	public static final int BN_HILITE = 2;

	public static final int BN_KILLFOCUS = 7;

	public static final int BN_PAINT = 1;

	public static final int BN_PUSHED = 2;

	public static final int BN_SETFOCUS = 6;

	public static final int BN_UNHILITE = 3;

	public static final int BN_UNPUSHED = 2;

	public static final int BP_CHECKBOX = 3;

	public static final int BP_GROUPBOX = 4;

	public static final int BP_PUSHBUTTON = 1;

	public static final int BP_RADIOBUTTON = 2;

	public static final int BPBF_COMPATIBLEBITMAP = 0;

	public static final int BPBF_DIB = 1;

	public static final int BPBF_TOPDOWNDIB = 2;

	public static final int BPBF_TOPDOWNMONODIB = 3;

	public static final int BPPF_ERASE = 0x0001;

	public static final int BPPF_NOCLIP = 0x0002;

	public static final int BPPF_NONCLIENT = 0x0004;

	public static final int BS_3STATE = 0x00000005;

	public static final int BS_AUTO3STATE = 0x00000006;

	public static final int BS_AUTOCHECKBOX = 0x00000003;

	public static final int BS_AUTORADIOBUTTON = 0x00000009;

	public static final int BS_BITMAP = 0x00000080;

	public static final int BS_BOTTOM = 0x00000800;

	public static final int BS_CENTER = 0x300;

	public static final int BS_CHECKBOX = 0x2;

	public static final int BS_COMMANDLINK = 0xe;

	public static final int BS_DEFPUSHBUTTON = 0x1;

	public static final int BS_FLAT = 0x00008000;

	public static final int BS_GROUPBOX = 0x00000007;

	public static final int BS_ICON = 0x00000040;

	public static final int BS_LEFT = 0x00000100;

	public static final int BS_LEFTTEXT = 0x00000020;

	public static final int BS_MULTILINE = 0x00002000;

	public static final int BS_NOTIFY = 0x4000;

	public static final int BS_OWNERDRAW = 0xb;

	public static final int BS_PATTERN = 0x3;

	public static final int BS_PUSHBUTTON = 0x0;

	public static final int BS_PUSHLIKE = 0x00001000;

	public static final int BS_RADIOBUTTON = 0x00000004;

	public static final int BS_RIGHT = 0x00000200;

	public static final int BS_RIGHTBUTTON = 0x00000020;

	public static final int BS_SOLID = 0x0;

	public static final int BS_TEXT = 0x00000000;

	public static final int BS_TOP = 0x00000400;

	public static final int BS_USERBUTTON = 0x00000008;

	public static final int BS_VCENTER = 0x00000C00;

	public static final int BST_CHECKED = 0x0001;

	public static final int BST_FOCUS = 0x0008;

	public static final int BST_INDETERMINATE = 0x0002;

	public static final int BST_PUSHED = 0x0004;

	public static final int BST_UNCHECKED = 0x0000;

	public static final int BTNS_AUTOSIZE = 0x10;

	public static final int BTNS_BUTTON = 0x0;

	public static final int BTNS_CHECK = 0x2;

	public static final int BTNS_CHECKGROUP = 0x6;

	public static final int BTNS_DROPDOWN = 0x8;

	public static final int BTNS_GROUP = 0x4;

	public static final int BTNS_SEP = 0x1;

	public static final int BTNS_SHOWTEXT = 0x40;

	public static final int BUTTON_IMAGELIST_ALIGN_CENTER = 4;

	public static final int BUTTON_IMAGELIST_ALIGN_LEFT = 0;

	public static final int BUTTON_IMAGELIST_ALIGN_RIGHT = 1;

	public static final int CB_ADDSTRING = 0x0143;

	public static final int CB_DELETESTRING = 0x0144;

	public static final int CB_DIR = 0x0145;

	public static final int CB_ERR = 0xffffffff;

	public static final int CB_ERRSPACE = 0xfffffffe;

	public static final int CB_FINDSTRING = 0x014C;

	public static final int CB_FINDSTRINGEXACT = 0x0158;

	public static final int CB_GETCOUNT = 0x0146;

	public static final int CB_GETCURSEL = 0x0147;

	public static final int CB_GETDROPPEDCONTROLRECT = 0x0152;

	public static final int CB_GETDROPPEDSTATE = 0x0157;

	public static final int CB_GETDROPPEDWIDTH = 0x015f;

	public static final int CB_GETEDITSEL = 0x0140;

	public static final int CB_GETEXTENDEDUI = 0x0156;

	public static final int CB_GETHORIZONTALEXTENT = 0x015d;

	public static final int CB_GETITEMDATA = 0x0150;

	public static final int CB_GETITEMHEIGHT = 0x0154;

	public static final int CB_GETLBTEXT = 0x0148;

	public static final int CB_GETLBTEXTLEN = 0x0149;

	public static final int CB_GETLOCALE = 0x015A;

	public static final int CB_GETTOPINDEX = 0x015b;

	public static final int CB_INITSTORAGE = 0x0161;

	public static final int CB_INSERTSTRING = 0x014A;

	public static final int CB_LIMITTEXT = 0x0141;

	public static final int CB_MSGMAX = 0x0162;

	public static final int CB_OKAY = 0;

	public static final int CB_RESETCONTENT = 0x014B;

	public static final int CB_SELECTSTRING = 0x014D;

	public static final int CB_SETCURSEL = 0x014E;

	public static final int CB_SETDROPPEDWIDTH = 0x0160;

	public static final int CB_SETEDITSEL = 0x0142;

	public static final int CB_SETEXTENDEDUI = 0x0155;

	public static final int CB_SETHORIZONTALEXTENT = 0x015e;

	public static final int CB_SETITEMDATA = 0x0151;

	public static final int CB_SETITEMHEIGHT = 0x0153;

	public static final int CB_SETLOCALE = 0x0159;

	public static final int CB_SETTOPINDEX = 0x015c;

	public static final int CB_SHOWDROPDOWN = 0x014F;

	public static final int CBN_CLOSEUP = 8;

	public static final int CBN_DBLCLK = 2;

	public static final int CBN_DROPDOWN = 7;

	public static final int CBN_EDITCHANGE = 5;

	public static final int CBN_EDITUPDATE = 6;

	public static final int CBN_ERRSPACE = (-1);

	public static final int CBN_KILLFOCUS = 4;

	public static final int CBN_SELCHANGE = 1;

	public static final int CBN_SELENDCANCEL = 10;

	public static final int CBN_SELENDOK = 9;

	public static final int CBN_SETFOCUS = 0x3;

	public static final int CBS_AUTOHSCROLL = 0x0040;

	public static final int CBS_CHECKEDDISABLED = 8;

	public static final int CBS_CHECKEDHOT = 6;

	public static final int CBS_CHECKEDNORMAL = 5;

	public static final int CBS_CHECKEDPRESSED = 7;

	public static final int CBS_DISABLENOSCROLL = 0x0800;

	public static final int CBS_DROPDOWN = 0x0002;

	public static final int CBS_DROPDOWNLIST = 0x0003;

	public static final int CBS_HASSTRINGS = 0x0200;

	public static final int CBS_LOWERCASE = 0x4000;

	public static final int CBS_MIXEDDISABLED = 0;

	public static final int CBS_MIXEDHOT = 0;

	public static final int CBS_MIXEDNORMAL = 9;

	public static final int CBS_MIXEDPRESSED = 0;

	public static final int CBS_NOINTEGRALHEIGHT = 0x0400;

	public static final int CBS_OEMCONVERT = 0x0080;

	public static final int CBS_OWNERDRAWFIXED = 0x0010;

	public static final int CBS_OWNERDRAWVARIABLE = 0x0020;

	public static final int CBS_SIMPLE = 0x0001;

	public static final int CBS_SORT = 0x0100;

	public static final int CBS_UNCHECKEDDISABLED = 4;

	public static final int CBS_UNCHECKEDHOT = 2;

	public static final int CBS_UNCHECKEDNORMAL = 1;

	public static final int CBS_UNCHECKEDPRESSED = 3;

	public static final int CBS_UPPERCASE = 0x2000;

	public static final int CBXS_DISABLED = 4;

	public static final int CBXS_HOT = 2;

	public static final int CBXS_NORMAL = 1;

	public static final int CBXS_PRESSED = 3;

	public static final int CC_ANYCOLOR = 0x100;

	public static final int CC_ENABLEHOOK = 0x10;

	public static final int CC_FULLOPEN = 0x2;

	public static final int CC_RGBINIT = 0x1;

	public static final int CCHILDREN_SCROLLBAR = 5;

	public static final int CCM_FIRST = 0x2000;

	public static final int CCM_SETBKCOLOR = 0x2001;

	public static final int CCM_SETVERSION = 0x2007;

	public static final int CCS_NODIVIDER = 0x40;

	public static final int CCS_NORESIZE = 0x4;

	public static final int CCS_VERT = 0x80;

	public static final int CDDS_ITEM = 0x00010000;

	public static final int CDDS_POSTERASE = 0x00000004;

	public static final int CDDS_POSTPAINT = 0x00000002;

	public static final int CDDS_PREERASE = 0x00000003;

	public static final int CDDS_ITEMPOSTPAINT = CDDS_ITEM | CDDS_POSTPAINT;

	public static final int CDDS_PREPAINT = 0x00000001;

	public static final int CDDS_ITEMPREPAINT = CDDS_ITEM | CDDS_PREPAINT;

	public static final int CDDS_SUBITEM = 0x00020000;

	public static final int CDDS_SUBITEMPOSTPAINT = CDDS_ITEMPOSTPAINT
			| CDDS_SUBITEM;

	public static final int CDDS_SUBITEMPREPAINT = CDDS_ITEMPREPAINT
			| CDDS_SUBITEM;

	public static final int CDIS_CHECKED = 0x0008;

	public static final int CDIS_DEFAULT = 0x0020;

	public static final int CDIS_DISABLED = 0x0004;

	public static final int CDIS_FOCUS = 0x0010;

	public static final int CDIS_GRAYED = 0x0002;

	public static final int CDIS_HOT = 0x0040;

	public static final int CDIS_INDETERMINATE = 0x0100;

	public static final int CDIS_MARKED = 0x0080;

	public static final int CDIS_SELECTED = 0x0001;

	public static final int CDM_FIRST = 0x0400 + 100;

	public static final int CDM_GETSPEC = CDM_FIRST;

	public static final int CDN_FIRST = -601;

	public static final int CDN_SELCHANGE = CDN_FIRST - 1;

	public static final int CDRF_DODEFAULT = 0x00000000;

	public static final int CDRF_DOERASE = 0x00000008;

	public static final int CDRF_NEWFONT = 0x00000002;

	public static final int CDRF_NOTIFYITEMDRAW = 0x00000020;

	public static final int CDRF_NOTIFYPOSTERASE = 0x00000040;

	public static final int CDRF_NOTIFYPOSTPAINT = 0x00000010;

	public static final int CDRF_NOTIFYSUBITEMDRAW = 0x00000020;

	public static final int CDRF_SKIPDEFAULT = 0x04;

	public static final int CDRF_SKIPPOSTPAINT = 0x00000100;

	public static final int CF_EFFECTS = 0x100;

	public static final int CF_INITTOLOGFONTSTRUCT = 0x40;

	public static final int CF_SCREENFONTS = 0x1;

	public static final int CF_TEXT = 0x1;

	public static final int CF_UNICODETEXT = 13;

	public static final int CF_USESTYLE = 0x80;

	public static final int CFE_AUTOCOLOR = 0x40000000;

	public static final int CFE_ITALIC = 0x2;

	public static final int CFE_STRIKEOUT = 0x8;

	public static final int CFE_UNDERLINE = 0x4;

	public static final int CFM_BOLD = 0x1;

	public static final int CFM_CHARSET = 0x8000000;

	public static final int CFM_COLOR = 0x40000000;

	public static final int CFM_FACE = 0x20000000;

	public static final int CFM_ITALIC = 0x2;

	public static final int CFM_SIZE = 0x80000000;

	public static final int CFM_STRIKEOUT = 0x8;

	public static final int CFM_UNDERLINE = 0x4;

	public static final int CFM_WEIGHT = 0x400000;

	public static final int CFS_CANDIDATEPOS = 0x0040;

	public static final int CFS_EXCLUDE = 0x0080;

	public static final int CFS_POINT = 0x2;

	public static final int CFS_RECT = 0x1;

	public static final int CLR_DEFAULT = 0xff000000;

	public static final int CLR_INVALID = 0xffffffff;

	public static final int CLR_NONE = 0xffffffff;

	public static final int CLSCTX_INPROC_SERVER = 1;

	public static final int COL_SYS_MASK = 0x80000000;

	public static final int COMPLEXREGION = 0x3;

	public static final int CP_ACP = 0x0;

	public static final int CP_DROPDOWNBUTTON = 1;

	public static final int CP_INSTALLED = 0x1;

	public static final int CP_UTF8 = 65001;

	public static final int CPS_COMPLETE = 0x1;

	public static final int CS_BYTEALIGNCLIENT = 4096;

	public static final int CS_BYTEALIGNWINDOW = 0x2000;

	public static final int CS_CLASSDC = 64;

	public static final int CS_DBLCLKS = 0x8;

	public static final int CS_DROPSHADOW = 0x20000;

	public static final int CS_GLOBALCLASS = 0x4000;

	public static final int CS_HREDRAW = 0x2;

	public static final int CS_KEYCVTWINDOW = 4;

	public static final int CS_NOCLOSE = 512;

	public static final int CS_NOKEYCVT = 256;

	public static final int CS_OWNDC = 32;

	public static final int CS_PARENTDC = 128;

	public static final int CS_PUBLICCLASS = 16384;

	public static final int CS_SAVEBITS = 2048;

	public static final int CS_VREDRAW = 0x1;

	public static final int CSIDL_ADMINTOOLS = 48;

	public static final int CSIDL_ALTSTARTUP = 29;

	public static final int CSIDL_APPDATA = 26;

	public static final int CSIDL_BITBUCKET = 10;

	public static final int CSIDL_CDBURN_AREA = 59;

	public static final int CSIDL_COMMON_ADMINTOOLS = 47;

	public static final int CSIDL_COMMON_ALTSTARTUP = 30;

	public static final int CSIDL_COMMON_APPDATA = 35;

	public static final int CSIDL_COMMON_DESKTOPDIRECTORY = 25;

	public static final int CSIDL_COMMON_DOCUMENTS = 46;

	public static final int CSIDL_COMMON_FAVORITES = 31;

	public static final int CSIDL_COMMON_MUSIC = 53;

	public static final int CSIDL_COMMON_PICTURES = 54;

	public static final int CSIDL_COMMON_PROGRAMS = 23;

	public static final int CSIDL_COMMON_STARTMENU = 22;

	public static final int CSIDL_COMMON_STARTUP = 24;

	public static final int CSIDL_COMMON_TEMPLATES = 45;

	public static final int CSIDL_COMMON_VIDEO = 55;

	public static final int CSIDL_COMPUTER = 17;

	public static final int CSIDL_CONTROLS = 3;

	public static final int CSIDL_COOKIES = 33;

	public static final int CSIDL_DESKTOP = 0;

	public static final int CSIDL_DESKTOPDIRECTORY = 16;

	public static final int CSIDL_DRIVES = 17;

	public static final int CSIDL_FAVORITES = 6;

	public static final int CSIDL_FONTS = 20;

	public static final int CSIDL_HISTORY = 34;

	public static final int CSIDL_INTERNET = 1;

	public static final int CSIDL_INTERNET_CACHE = 32;

	public static final int CSIDL_LOCAL_APPDATA = 28;

	public static final int CSIDL_MYDOCUMENTS = 12;

	public static final int CSIDL_MYMUSIC = 13;

	public static final int CSIDL_MYPICTURES = 39;

	public static final int CSIDL_MYVIDEO = 14;

	public static final int CSIDL_NET_CONNECTIONS = 49;

	public static final int CSIDL_NETHOOD = 19;

	public static final int CSIDL_NETWORK = 18;

	public static final int CSIDL_PERSONAL = 5;

	public static final int CSIDL_PRINTERS = 4;

	public static final int CSIDL_PROFILE = 40;

	public static final int CSIDL_PROFILES = 62;

	public static final int CSIDL_PROGRAM_FILES = 38;

	public static final int CSIDL_PROGRAM_FILES_COMMON = 43;

	public static final int CSIDL_PROGRAMS = 2;

	public static final int CSIDL_RECENT = 8;

	public static final int CSIDL_SEND_TO = 9;

	public static final int CSIDL_START_MENU = 11;

	public static final int CSIDL_STARTUP = 7;

	public static final int CSIDL_SYSTEM = 37;

	public static final int CSIDL_TEMPLATES = 21;

	public static final int CSIDL_WINDOWS = 36;

	public static final int CW_USEDEFAULT = 0x80000000;

	public static final int DATE_LONGDATE = 0x00000002;

	public static final int DATE_SHORTDATE = 0x00000001;

	public static final int DATE_YEARMONTH = 0x00000008; // #if(WINVER >=

	// 0x0500)

	public static final String DATETIMEPICK_CLASS = "SysDateTimePick32"; //$NON-NLS-1$

	public static final int DCX_CACHE = 0x2;

	public static final int DCX_CLIPCHILDREN = 0x8;

	public static final int DCX_CLIPSIBLINGS = 0x10;

	public static final int DEFAULT_CHARSET = 0x1;

	public static final int DEFAULT_GUI_FONT = 0x11;

	public static final int DFC_BUTTON = 0x4;

	public static final int DFC_SCROLL = 0x3;

	public static final int DFCS_BUTTONCHECK = 0x0;

	public static final int DFCS_CHECKED = 0x400;

	public static final int DFCS_FLAT = 0x4000;

	public static final int DFCS_INACTIVE = 0x100;

	public static final int DFCS_PUSHED = 0x200;

	public static final int DFCS_SCROLLDOWN = 0x1;

	public static final int DFCS_SCROLLLEFT = 0x2;

	public static final int DFCS_SCROLLRIGHT = 0x3;

	public static final int DFCS_SCROLLUP = 0x0;

	public static final int DI_NOMIRROR = 0x10;

	public static final int DI_NORMAL = 0x3;

	public static final int DIB_RGB_COLORS = 0x0;

	public static final int DISP_E_EXCEPTION = 0x80020009;

	public static final int DLGC_BUTTON = 0x2000;

	public static final int DLGC_HASSETSEL = 0x8;

	public static final int DLGC_STATIC = 0x100;

	public static final int DLGC_WANTALLKEYS = 0x4;

	public static final int DLGC_WANTARROWS = 0x1;

	public static final int DLGC_WANTCHARS = 0x80;

	public static final int DLGC_WANTTAB = 0x2;

	public static final int DM_SETDEFID = 0x401;

	/**
	 * CD/VCD/DVD drive (value is 5).
	 */
	public static final int DRIVE_TYPE_CDROM = 5;

	/**
	 * Hard drive (value is 3).
	 */
	public static final int DRIVE_TYPE_FIXED = 3;

	/**
	 * Not exist type drive (value is 1).
	 */
	public static final int DRIVE_TYPE_NOT_EXIST = 1;

	/**
	 * RAM drive (value is 6).
	 */
	public static final int DRIVE_TYPE_RAMDISK = 6;

	/**
	 * remote network drive (value is 4).
	 */
	public static final int DRIVE_TYPE_REMOTE = 4;

	/**
	 * Removable drive (value is 2).
	 */
	public static final int DRIVE_TYPE_REMOVABLE = 2;

	/**
	 * Unknow type drive (value is 0).
	 */
	public static final int DRIVE_TYPE_UNKNOW = 0;

	public static final int DSS_DISABLED = 0x20;

	public static final int DST_BITMAP = 0x4;

	public static final int DST_ICON = 0x3;

	public static final int DSTINVERT = 0x550009;

	public static final int DT_BOTTOM = 0x8;

	public static final int DT_CALCRECT = 0x400;

	public static final int DT_CENTER = 0x1;

	public static final int DT_EDITCONTROL = 0x2000;

	public static final int DT_ENDELLIPSIS = 32768;

	public static final int DT_EXPANDTABS = 0x40;

	public static final int DT_HIDEPREFIX = 0x100000;

	public static final int DT_LEFT = 0x0;

	public static final int DT_NOPREFIX = 0x800;

	public static final int DT_RASPRINTER = 0x2;

	public static final int DT_RIGHT = 0x2;

	public static final int DT_SINGLELINE = 0x20;

	public static final int DT_TOP = 0;

	public static final int DT_VCENTER = 4;

	public static final int DT_WORDBREAK = 0x10;

	public static final int DTM_FIRST = 0x1000;

	public static final int DTM_GETSYSTEMTIME = DTM_FIRST + 1;

	public static final int DTM_SETFORMAT = Extension.IsUnicode ? DTM_FIRST + 50
			: DTM_FIRST + 5;

	public static final int DTM_SETSYSTEMTIME = DTM_FIRST + 2;

	public static final int DTN_FIRST = 0xFFFFFD08;

	public static final int DTN_DATETIMECHANGE = DTN_FIRST + 1;

	public static final int DTS_LONGDATEFORMAT = 0x0004;

	public static final int DTS_SHORTDATECENTURYFORMAT = 0x000C;

	public static final int DTS_SHORTDATEFORMAT = 0x0000;

	public static final int DTS_TIMEFORMAT = 0x0009;

	public static final int DTS_UPDOWN = 0x0001;

	public static final int DWM_BB_BLURREGION = 0x2;

	public static final int DWM_BB_ENABLE = 0x1;

	public static final int DWM_BB_TRANSITIONONMAXIMIZED = 0x4;

	public static final int E_POINTER = 0x80004003;

	public static final int EBNGC_HOT = 2;

	public static final int EBNGC_NORMAL = 1;

	public static final int EBNGC_PRESSED = 3;

	public static final int EBP_HEADERBACKGROUND = 1;

	public static final int EBP_NORMALGROUPBACKGROUND = 5;

	public static final int EBP_NORMALGROUPCOLLAPSE = 6;

	public static final int EBP_NORMALGROUPEXPAND = 7;

	public static final int EBP_NORMALGROUPHEAD = 8;

	public static final int EC_LEFTMARGIN = 0x0001;

	public static final int EC_RIGHTMARGIN = 0x0002;

	public static final int EC_USEFONTINFO = 0xffff;

	public static final int ECO_AUTOHSCROLL = 0x80;

	public static final int ECOOP_AND = 0x3;

	public static final int ECOOP_OR = 0x2;

	public static final int EDGE_BUMP = (BDR_RAISEDOUTER | BDR_SUNKENINNER);

	public static final int EDGE_ETCHED = (BDR_SUNKENOUTER | BDR_RAISEDINNER);

	public static final int EDGE_RAISED = (BDR_RAISEDOUTER | BDR_RAISEDINNER);

	public static final int EDGE_SUNKEN = (BDR_SUNKENOUTER | BDR_SUNKENINNER);

	public static final int EIMES_CANCELCOMPSTRINFOCUS = 0x0002;

	public static final int EIMES_COMPLETECOMPSTRKILLFOCUS = 0x0004;

	public static final int EIMES_GETCOMPSTRATONCE = 0x0001;

	public static final int EM_CANUNDO = 0x00C6;

	public static final int EM_CHARFROMPOS = 0x00D7;

	public static final int EM_DISPLAYBAND = 0x433;

	public static final int EM_EMPTYUNDOBUFFER = 0x00CD;

	public static final int EM_FMTLINES = 0x00C8;

	public static final int EM_GETFIRSTVISIBLELINE = 0x00CE;

	public static final int EM_GETHANDLE = 0x00BD;

	public static final int EM_GETIMESTATUS = 0x00D9;

	public static final int EM_GETLIMITTEXT = 0x00D5;

	public static final int EM_GETLINE = 0x00C4;

	public static final int EM_GETLINECOUNT = 0x00BA;

	public static final int EM_GETMARGINS = 0x00D4;

	public static final int EM_GETMODIFY = 0x00B8;

	public static final int EM_GETPASSWORDCHAR = 0x00D2;

	public static final int EM_GETRECT = 0x00B2;

	public static final int EM_GETSCROLLPOS = 0x4dd;

	public static final int EM_GETSEL = 0xb0;

	public static final int EM_GETTHUMB = 0x00BE;

	public static final int EM_GETWORDBREAKPROC = 0x00D1;

	public static final int EM_LIMITTEXT = 0xc5;

	public static final int EM_LINEFROMCHAR = 0xc9;

	public static final int EM_LINEINDEX = 0xbb;

	public static final int EM_LINELENGTH = 0xc1;

	public static final int EM_LINESCROLL = 0xb6;

	public static final int EM_POSFROMCHAR = 0xd6;

	public static final int EM_REPLACESEL = 0xc2;

	public static final int EM_SCROLL = 0x00B5;

	public static final int EM_SCROLLCARET = 0xb7;

	public static final int EM_SETBKGNDCOLOR = 0x443;

	public static final int EM_SETCUEBANNER = 0x1500 + 1;

	public static final int EM_SETHANDLE = 0x00BC;

	public static final int EM_SETIMESTATUS = 0x00D8;

	public static final int EM_SETLIMITTEXT = 0xc5;

	public static final int EM_SETMARGINS = 211;

	public static final int EM_SETMODIFY = 0x00B9;

	public static final int EM_SETOPTIONS = 0x44d;

	public static final int EM_SETPARAFORMAT = 0x447;

	public static final int EM_SETPASSWORDCHAR = 0xcc;

	public static final int EM_SETREADONLY = 0xcf;

	public static final int EM_SETRECT = 0x00B3;

	public static final int EM_SETRECTNP = 0x00B4;

	public static final int EM_SETSEL = 0xb1;

	public static final int EM_SETTABSTOPS = 0xcb;

	public static final int EM_SETWORDBREAKPROC = 0x00D0;

	public static final int EM_UNDO = 199;

	public static final int EMSIS_COMPOSITIONSTRING = 0x0001;

	public static final int EN_ALIGN_LTR_EC = 0x0700;

	public static final int EN_ALIGN_RTL_EC = 0x0701;

	public static final int EN_CHANGE = 0x300;

	public static final int EN_ERRSPACE = 0x0500;

	public static final int EN_HSCROLL = 0x0601;

	public static final int EN_KILLFOCUS = 0x0200;

	public static final int EN_MAXTEXT = 0x0501;

	public static final int EN_SETFOCUS = 0x0100;

	public static final int EN_UPDATE = 0x0400;

	public static final int EN_VSCROLL = 0x0602;

	public static final int EP_EDITTEXT = 1;

	public static final int ERROR_NO_MORE_ITEMS = 0x103;

	public static final int ES_AUTOHSCROLL = 0x0080;

	public static final int ES_AUTOVSCROLL = 0x40;

	public static final int ES_CENTER = 0x0001;

	public static final int ES_LEFT = 0x0000;

	public static final int ES_LOWERCASE = 0x0010;

	public static final int ES_MULTILINE = 0x0004;

	public static final int ES_NOHIDESEL = 0x100;

	public static final int ES_NUMBER = 0x2000;

	public static final int ES_OEMCONVERT = 0x0400;

	public static final int ES_PASSWORD = 0x0020;

	public static final int ES_READONLY = 0x800;

	public static final int ES_RIGHT = 0x0002;

	public static final int ES_UPPERCASE = 0x0008;

	public static final int ES_WANTRETURN = 0x1000;

	public static final int ESB_DISABLE_BOTH = 0x3;

	public static final int ESB_ENABLE_BOTH = 0x0;

	public static final int ETO_CLIPPED = 0x4;

	public static final int ETS_DISABLED = 4;

	public static final int ETS_FOCUSED = 5;

	public static final int ETS_HOT = 2;

	public static final int ETS_NORMAL = 1;

	public static final int ETS_READONLY = 6;

	public static final int ETS_SELECTED = 3;

	public static final int EVENT_OBJECT_FOCUS = 0x8005;

	public static final int EVENT_OBJECT_LOCATIONCHANGE = 0x800B;

	// public static final int EVENT_OBJECT_SELECTION = 0x8006;
	public static final int EVENT_OBJECT_SELECTIONWITHIN = 0x8009;

	public static final int EVENT_OBJECT_VALUECHANGE = 0x800E;

	public static final int FALT = 0x10;

	public static final int FCONTROL = 0x8;

	public static final int FE_FONTSMOOTHINGCLEARTYPE = 0x0002;

	public static final int FEATURE_DISABLE_NAVIGATION_SOUNDS = 21;

	public static final int FILE_ATTRIBUTE_ARCHIVE = 0x00000020;

	public static final int FILE_ATTRIBUTE_COMPRESSED = 0x00000800;

	public static final int FILE_ATTRIBUTE_DEVICE = 0x00000040;

	public static final int FILE_ATTRIBUTE_DIRECTORY = 0x00000010;

	public static final int FILE_ATTRIBUTE_ENCRYPTED = 0x00004000;

	public static final int FILE_ATTRIBUTE_HIDDEN = 0x00000002;

	public static final int FILE_ATTRIBUTE_NORMAL = 0x00000080;

	public static final int FILE_ATTRIBUTE_NOT_CONTENT_INDEXED = 0x00002000;

	public static final int FILE_ATTRIBUTE_OFFLINE = 0x00001000;

	public static final int FILE_ATTRIBUTE_READONLY = 0x00000001;

	public static final int FILE_ATTRIBUTE_REPARSE_POINT = 0x00000400;

	public static final int FILE_ATTRIBUTE_SPARSE_FILE = 0x00000200;

	public static final int FILE_ATTRIBUTE_SYSTEM = 0x00000004;

	public static final int FILE_ATTRIBUTE_TEMPORARY = 0x00000100;

	public static final int FNERR_BUFFERTOOSMALL = 0x3003;

	public static final int FNERR_INVALIDFILENAME = 0x3002;

	/**
	 * Copies the files specified in the pFrom member to the location specified
	 * in the pTo member.
	 */
	public static final int FO_COPY = 0x2;

	/**
	 * Deletes the files specified in pFrom (pTo is ignored.)
	 */
	public static final int FO_DELETE = 0x3;

	/**
	 * Moves the files specified in pFrom to the location specified in pTo.
	 */
	public static final int FO_MOVE = 0x1;

	/**
	 * Renames the files specified in pFrom.
	 */
	public static final int FO_RENAME = 0x4;

	/**
	 * Preserve Undo information.
	 */
	public static final int FOF_ALLOWUNDO = 0x40;

	/**
	 * Not currently implemented
	 */
	public static final int FOF_CONFIRMMOUSE = 0x2;

	/**
	 * Handle to the parent window for the progress dialog box.
	 */
	public static final int FOF_CREATEPROGRESSDLG = 0x0;

	/**
	 * Perform the operation on files only if a wildcard file name (*.*) is
	 * specified.
	 */
	public static final int FOF_FILESONLY = 0x80;

	/**
	 * Multiple file operation
	 */
	public static final int FOF_MULTIDESTFILES = 0x1;

	/**
	 * Respond with Yes to All for any dialog box that is displayed
	 */
	public static final int FOF_NOCONFIRMATION = 0x10;

	/**
	 * Does not confirm the creation of a new directory if the operation
	 * requires one to be created.
	 */
	public static final int FOF_NOCONFIRMMKDIR = 0x200;

	/**
	 * Give the file being operated on a new name in a move, copy, or rename
	 * operation if a file with the target name already exists
	 */
	public static final int FOF_RENAMEONCOLLISION = 0x8;

	/**
	 * Does not display a progress dialog box
	 */
	public static final int FOF_SILENT = 0x4;

	/**
	 * Displays a progress dialog box but does not show the file names.
	 */
	public static final int FOF_SIMPLEPROGRESS = 0x100;

	/**
	 * If FOF_RENAMEONCOLLISION is specified, the hNameMappings member will be
	 * filled in if any files were renamed.
	 */
	public static final int FOF_WANTMAPPINGHANDLE = 0x20;

	public static final int FORMAT_MESSAGE_ALLOCATE_BUFFER = 0x00000100;

	public static final int FORMAT_MESSAGE_FROM_SYSTEM = 0x00001000;

	public static final int FORMAT_MESSAGE_IGNORE_INSERTS = 0x00000200;

	public static final int FR_PRIVATE = 0x10;

	public static final int FSHIFT = 0x4;

	public static final int FVIRTKEY = 0x1;

	public static final int GBS_DISABLED = 2;

	public static final int GBS_NORMAL = 1;

	public static final int GCS_COMPATTR = 0x0010;

	public static final int GCS_COMPCLAUSE = 0x0020;

	public static final int GCS_COMPSTR = 0x8;

	public static final int GCS_CURSORPOS = 0x0080;

	public static final int GCS_RESULTSTR = 0x800;

	public static final int GDT_VALID = 0;

	public static final int GET_FEATURE_FROM_PROCESS = 0x2;

	public static final int GLPS_CLOSED = 1;

	public static final int GLPS_OPENED = 2;

	public static final int GM_ADVANCED = 2;

	public static final int GMDI_USEDISABLED = 0x1;

	public static final int GMEM_FIXED = 0x0;

	public static final int GMEM_ZEROINIT = 0x40;

	public static final int GN_CONTEXTMENU = 1000;

	public static final int GPTR = 0x40;

	public static final int GRADIENT_FILL_RECT_H = 0x0;

	public static final int GRADIENT_FILL_RECT_V = 0x1;

	public static final int GT_DEFAULT = 0x0;

	public static final int GTL_NUMBYTES = 0x10;

	public static final int GTL_NUMCHARS = 0x8;

	public static final int GTL_PRECISE = 0x2;

	public static final int GUI_16BITTASK = 0x20;

	public static final int GUI_CARETBLINKING = 0x1;

	public static final int GUI_INMENUMODE = 0x4;

	public static final int GUI_INMOVESIZE = 0x2;

	public static final int GUI_POPUPMENUMODE = 0x10;

	public static final int GUI_SYSTEMMENUMODE = 0x8;

	public static final int GW_CHILD = 0x5;

	public static final int GW_HWNDFIRST = 0x0;

	public static final int GW_HWNDLAST = 0x1;

	public static final int GW_HWNDNEXT = 0x2;

	public static final int GW_HWNDPREV = 0x3;

	public static final int GW_OWNER = 0x4;

	public static final int GWL_EXSTYLE = 0xffffffec;

	public static final int GWL_HWNDPARENT = -8;

	public static final int GWL_ID = -12;

	public static final int GWL_STYLE = 0xfffffff0;

	public static final int GWL_USERDATA = 0xffffffeb;

	public static final int GWL_WNDPROC = 0xfffffffc;

	public static final int GWLP_HWNDPARENT = -8;

	public static final int GWLP_ID = -12;

	public static final int GWLP_USERDATA = 0xffffffeb;

	public static final int GWLP_WNDPROC = 0xfffffffc;

	public static final int HBMMENU_CALLBACK = 0xffffffff;

	public static final int HC_ACTION = 0;

	public static final int HC_GETNEXT = 1;

	public static final int HC_NOREM = 3;

	public static final int HC_NOREMOVE = 3;

	public static final int HC_SKIP = 2;

	public static final int HC_SYSMODALOFF = 5;

	public static final int HC_SYSMODALON = 4;

	public static final int HCBT_ACTIVATE = 5;

	public static final int HCBT_CLICKSKIPPED = 6;

	public static final int HCBT_CREATEWND = 3;

	public static final int HCBT_DESTROYWND = 4;

	public static final int HCBT_KEYSKIPPED = 7;

	public static final int HCBT_MINMAX = 1;

	public static final int HCBT_MOVESIZE = 0;

	public static final int HCBT_QS = 2;

	public static final int HCBT_SETFOCUS = 9;

	public static final int HCBT_SYSCOMMAND = 8;

	public static final int HCF_HIGHCONTRASTON = 0x1;

	public static final int HDF_BITMAP = 0x2000;

	public static final int HDF_BITMAP_ON_RIGHT = 0x1000;

	public static final int HDF_CENTER = 2;

	public static final int HDF_IMAGE = 0x0800;

	public static final int HDF_JUSTIFYMASK = 0x3;

	public static final int HDF_LEFT = 0;

	public static final int HDF_RIGHT = 1;

	public static final int HDF_SORTDOWN = 0x0200;

	public static final int HDF_SORTUP = 0x0400;

	public static final int HDI_BITMAP = 0x0010;

	public static final int HDI_FORMAT = 0x4;

	public static final int HDI_IMAGE = 32;

	public static final int HDI_ORDER = 0x80;

	public static final int HDI_TEXT = 0x2;

	public static final int HDI_WIDTH = 0x1;

	public static final int HDM_FIRST = 0x1200;

	public static final int HDM_DELETEITEM = HDM_FIRST + 2;

	public static final int HDM_GETBITMAPMARGIN = HDM_FIRST + 21;

	public static final int HDM_GETITEMA = HDM_FIRST + 3;

	public static final int HDM_GETITEMCOUNT = 0x1200;

	public static final int HDM_GETITEMRECT = HDM_FIRST + 7;

	public static final int HDM_GETITEMW = HDM_FIRST + 11;

	public static final int HDM_GETITEM = Extension.IsUnicode ? HDM_GETITEMW
			: HDM_GETITEMA;

	public static final int HDM_GETORDERARRAY = HDM_FIRST + 17;

	public static final int HDM_HITTEST = HDM_FIRST + 6;

	public static final int HDM_INSERTITEMA = HDM_FIRST + 1;

	public static final int HDM_INSERTITEMW = HDM_FIRST + 10;

	public static final int HDM_INSERTITEM = Extension.IsUnicode ? HDM_INSERTITEMW
			: HDM_INSERTITEMA;

	public static final int HDM_LAYOUT = HDM_FIRST + 5;

	public static final int HDM_ORDERTOINDEX = HDM_FIRST + 15;

	public static final int HDM_SETIMAGELIST = HDM_FIRST + 8;

	public static final int HDM_SETITEMA = HDM_FIRST + 4;

	public static final int HDM_SETITEMW = HDM_FIRST + 12;

	public static final int HDM_SETITEM = Extension.IsUnicode ? HDM_SETITEMW
			: HDM_SETITEMA;

	public static final int HDM_SETORDERARRAY = HDM_FIRST + 18;

	public static final int HDN_FIRST = 0xfffffed4;

	public static final int HDN_BEGINDRAG = HDN_FIRST - 10;

	public static final int HDN_BEGINTRACK = Extension.IsUnicode ? 0xfffffeba
			: 0xfffffece;

	public static final int HDN_BEGINTRACKA = 0xfffffece;

	public static final int HDN_BEGINTRACKW = 0xfffffeba;

	public static final int HDN_DIVIDERDBLCLICKA = HDN_FIRST - 5;

	public static final int HDN_DIVIDERDBLCLICKW = HDN_FIRST - 25;

	public static final int HDN_ENDDRAG = HDN_FIRST - 11;

	public static final int HDN_DIVIDERDBLCLICK = Extension.IsUnicode ? HDN_DIVIDERDBLCLICKW
			: HDN_DIVIDERDBLCLICKA;

	public static final int HDN_ITEMCHANGED = Extension.IsUnicode ? 0xfffffebf
			: 0xfffffed3;

	public static final int HDN_ITEMCHANGEDA = 0xfffffed3;

	public static final int HDN_ITEMCHANGEDW = 0xfffffebf;

	public static final int HDN_ITEMCHANGINGA = HDN_FIRST;

	public static final int HDN_ITEMCHANGINGW = HDN_FIRST - 20;

	public static final int HDN_ITEMCLICKA = HDN_FIRST - 2;

	public static final int HDN_ITEMCLICKW = HDN_FIRST - 22;

	public static final int HDN_ITEMDBLCLICKA = HDN_FIRST - 3;

	public static final int HDN_ITEMDBLCLICKW = HDN_FIRST - 23;

	public static final int HDN_ITEMDBLCLICK = Extension.IsUnicode ? HDN_ITEMDBLCLICKW
			: HDN_ITEMDBLCLICKA;

	public static final int HDS_BUTTONS = 0x2;

	public static final int HDS_DRAGDROP = 0x0040;

	public static final int HDS_FULLDRAG = 0x80;

	public static final int HDS_HIDDEN = 0x8;

	// public static final int HEAP_ZERO_MEMORY = 0x8;
	public static final int HELPINFO_MENUITEM = 0x2;

	public static final int HHT_ONDIVIDER = 0x4;

	public static final int HHT_ONDIVOPEN = 0x8;

	public static final int HICF_ARROWKEYS = 0x2;

	public static final int HINST_COMMCTRL = 0xffffffff;

	public static final int HKEY_CLASSES_ROOT = 0x80000000;

	public static final int HKEY_CURRENT_USER = 0x80000001;

	public static final int HKEY_LOCAL_MACHINE = 0x80000002;

	public static final int HORZRES = 0x8;

	public static final int HSHELL_ACTIVATESHELLWINDOW = 3;

	public static final int HSHELL_GETMINRECT = 5;

	public static final int HSHELL_LANGUAGE = 8;

	public static final int HSHELL_REDRAW = 6;

	public static final int HSHELL_TASKMAN = 7;

	public static final int HSHELL_WINDOWACTIVATED = 4;

	public static final int HSHELL_WINDOWCREATED = 1;

	public static final int HSHELL_WINDOWDESTROYED = 2;

	public static final int HTBORDER = 0x12;

	public static final int HTCAPTION = 0x2;

	public static final int HTCLIENT = 0x1;

	public static final int HTERROR = -2;

	public static final int HTHSCROLL = 0x6;

	public static final int HTMENU = 0x5;

	public static final int HTNOWHERE = 0x0;

	public static final int HTSYSMENU = 0x3;

	public static final int HTTRANSPARENT = 0xffffffff;

	public static final int HTVSCROLL = 0x7;

	public static final int HWND_BOTTOM = 0x1;

	public static final int HWND_NOTOPMOST = -2;

	public static final int HWND_TOP = 0x0;

	public static final int HWND_TOPMOST = 0xffffffff;

	public static final int I_IMAGECALLBACK = -1;

	public static final int I_IMAGENONE = -2;

	public static final int ICC_COOL_CLASSES = 0x400;

	public static final int ICC_DATE_CLASSES = 0x100;

	public static final int ICM_NOTOPEN = 0x0;

	/**
	 * The large icon type (value is 0), the icon dimension is 32*32.
	 * 
	 * @see ShellIcon#getSystemIcons
	 * @see ShellIcon#getSysFolderIcon
	 * @see ShellIcon#getFileIcons
	 */
	public static final int ICON_LARGE = 0;

	/**
	 * The small icon type (value is 1), the icon dimension is 16*16.
	 * 
	 * @see ShellIcon#getSystemIcons
	 * @see ShellIcon#getSysFolderIcon
	 * @see ShellIcon#getFileIcons
	 */
	public static final int ICON_SMALL = 1;

	public static final int IDABORT = 0x3;

	public static final int IDANI_CAPTION = 3;

	public static final int IDB_STD_SMALL_COLOR = 0x0;

	public static final int IDC_APPSTARTING = 0x7f8a;

	public static final int IDC_ARROW = 0x7f00;

	public static final int IDC_CROSS = 0x7f03;

	public static final int IDC_HAND = 0x7f89;

	public static final int IDC_HELP = 0x7f8b;

	public static final int IDC_IBEAM = 0x7f01;

	public static final int IDC_NO = 0x7f88;

	public static final int IDC_SIZE = 0x7f80;

	public static final int IDC_SIZEALL = 0x7f86;

	public static final int IDC_SIZENESW = 0x7f83;

	public static final int IDC_SIZENS = 0x7f85;

	public static final int IDC_SIZENWSE = 0x7f82;

	public static final int IDC_SIZEWE = 0x7f84;

	public static final int IDC_UPARROW = 0x7f04;

	public static final int IDC_WAIT = 0x7f02;

	public static final int IDI_APPLICATION = 32512;

	public static final int IDNO = 0x7;

	public static final int IDOK = 0x1;

	public static final int IDRETRY = 0x4;

	public static final int IDYES = 0x6;

	public static final int ILC_COLOR = 0x0;

	public static final int ILC_COLOR16 = 0x10;

	public static final int ILC_COLOR24 = 0x18;

	public static final int ILC_COLOR32 = 0x20;

	public static final int ILC_COLOR4 = 0x4;

	public static final int ILC_COLOR8 = 0x8;

	public static final int ILC_MASK = 0x1;

	public static final int ILC_MIRROR = 0x2000;

	public static final int ILD_NORMAL = 0x0;

	public static final int ILD_SELECTED = 0x4;

	public static final int IMAGE_BITMAP = 0x0;

	public static final int IMAGE_CURSOR = 0x2;

	public static final int IMAGE_ICON = 0x1;

	public static final int IME_CMODE_FULLSHAPE = 0x8;

	public static final int IME_CMODE_KATAKANA = 0x2;

	public static final int IME_CMODE_NATIVE = 0x1;

	public static final int IME_CMODE_ROMAN = 0x10;

	public static final int IMEMOUSE_LDOWN = 1;

	public static final int INFINITE = 0xffffffff;

	/**
	 * Specifies the infinite timeout value for {@link EventObject#waitFor}
	 */
	public static final int INFINITE_TIMEOUT = 0xFFFFFFFF;

	public static final int INPUT_KEYBOARD = 1;

	public static final int INPUT_MOUSE = 0;

	public static final int INTERNET_OPTION_END_BROWSER_SESSION = 42;

	public static final int KEY_ENUMERATE_SUB_KEYS = 0x8;

	public static final int KEY_NOTIFY = 0x10;

	public static final int KEY_QUERY_VALUE = 0x1;

	public static final int KEY_READ = 0x20019;

	public static final int KEYEVENTF_KEYUP = 0x0002;

	public static final int KEYEVENTF_KEYDOWN = 0x0000;

	public static final int KEYEVENTF_EXTENDEDKEY = 0x0001;

	public static final int L_MAX_URL_LENGTH = 2084;

	// public static final int LANG_KOREAN = 0x12;
	public static final int LANG_NEUTRAL = 0x0;

	public static final int LANG_USER_DEFAULT = 1 << 10;

	public static final int LAYOUT_BITMAPORIENTATIONPRESERVED = 0x8;

	public static final int LAYOUT_RTL = 0x1;

	public static final int LB_ADDFILE = 0x0196;

	public static final int LB_ADDSTRING = 0x180;

	public static final int LB_DELETESTRING = 0x182;

	public static final int LB_DIR = 0x018D;

	public static final int LB_ERR = (-1);

	public static final int LB_ERRSPACE = 0xfffffffe;

	public static final int LB_FINDSTRING = 0x018F;

	public static final int LB_FINDSTRINGEXACT = 0x1a2;

	public static final int LB_GETANCHORINDEX = 0x019D;

	public static final int LB_GETCARETINDEX = 0x019F;

	public static final int LB_GETCOUNT = 0x18b;

	public static final int LB_GETCURSEL = 0x0188;

	public static final int LB_GETHORIZONTALEXTENT = 0x193;

	public static final int LB_GETITEMDATA = 0x0199;

	public static final int LB_GETITEMHEIGHT = 0x01A1;

	public static final int LB_GETITEMRECT = 0x198;

	public static final int LB_GETLOCALE = 0x01A6;

	public static final int LB_GETSEL = 0x0187;

	public static final int LB_GETSELCOUNT = 0x190;

	public static final int LB_GETSELITEMS = 0x0191;

	public static final int LB_GETTEXT = 0x189;

	public static final int LB_GETTEXTLEN = 0x018A;

	public static final int LB_GETTOPINDEX = 0x18e;

	public static final int LB_INITSTORAGE = 0x01A8;

	public static final int LB_INSERTSTRING = 0x181;

	public static final int LB_ITEMFROMPOINT = 0x01A9;

	public static final int LB_MSGMAX = 0x01B0;

	public static final int LB_OKAY = 0;

	public static final int LB_RESETCONTENT = 0x0184;

	public static final int LB_SELECTSTRING = 0x018C;

	public static final int LB_SELITEMRANGE = 0x019B;

	public static final int LB_SELITEMRANGEEX = 0x183;

	public static final int LB_SETANCHORINDEX = 0x019C;

	public static final int LB_SETCARETINDEX = 0x19e;

	public static final int LB_SETCOLUMNWIDTH = 0x0195;

	public static final int LB_SETCOUNT = 0x01A7;

	public static final int LB_SETCURSEL = 0x0186;

	public static final int LB_SETHORIZONTALEXTENT = 0x194;

	public static final int LB_SETITEMDATA = 0x019A;

	public static final int LB_SETITEMHEIGHT = 0x01A0;

	public static final int LB_SETLOCALE = 0x01A5;

	public static final int LB_SETSEL = 0x0185;

	public static final int LB_SETTABSTOPS = 0x0192;

	public static final int LB_SETTOPINDEX = 0x0197;

	public static final int LBN_DBLCLK = 0x2;

	public static final int LBN_ERRSPACE = (-2);

	public static final int LBN_KILLFOCUS = 5;

	public static final int LBN_SELCANCEL = 3;

	public static final int LBN_SELCHANGE = 1;

	public static final int LBN_SETFOCUS = 4;

	public static final int LBS_DISABLENOSCROLL = 0x1000;

	public static final int LBS_EXTENDEDSEL = 0x0800;

	public static final int LBS_HASSTRINGS = 0x0040;

	public static final int LBS_MULTICOLUMN = 0x0200;

	public static final int LBS_MULTIPLESEL = 0x0008;

	public static final int LBS_NODATA = 0x2000;

	public static final int LBS_NOINTEGRALHEIGHT = 0x0100;

	public static final int LBS_NOREDRAW = 0x0004;

	public static final int LBS_NOSEL = 0x4000;

	public static final int LBS_NOTIFY = 0x0001;

	public static final int LBS_OWNERDRAWFIXED = 0x0010;

	public static final int LBS_OWNERDRAWVARIABLE = 0x0020;

	public static final int LBS_SORT = 0x0002;

	public static final int LBS_STANDARD = (0x0001 | 0x0002 | 0x200000 | 0x800000);

	public static final int LBS_USETABSTOPS = 0x0080;

	public static final int LBS_WANTKEYBOARDINPUT = 0x0400;

	public static final int LCID_SUPPORTED = 0x2;

	public static final int LF_FACESIZE = 32;

	public static final int LGRPID_ARABIC = 0xd;

	public static final int LGRPID_HEBREW = 0xc;

	public static final int LGRPID_INSTALLED = 1;

	public static final int LIF_ITEMINDEX = 0x1;

	public static final int LIF_STATE = 0x2;

	public static final int LIS_ENABLED = 0x2;

	public static final int LIS_FOCUSED = 0x1;

	public static final int LISS_HOT = 0x2;

	public static final int LISS_SELECTED = 0x3;

	public static final int LISS_SELECTEDNOTFOCUS = 0x5;

	public static final int LM_GETIDEALHEIGHT = 0x701;

	public static final int LM_GETITEM = 0x703;

	public static final int LM_SETITEM = 0x702;

	public static final int LOCALE_IDATE = 0x00000021;

	public static final int LOCALE_IDEFAULTANSICODEPAGE = 0x1004;

	public static final int LOCALE_ITIME = 0x00000023;

	public static final int LOCALE_RETURN_NUMBER = 0x20000000; // #if(WINVER >=

	// 0x0400)
	public static final int LOCALE_S1159 = 0x00000028;

	public static final int LOCALE_S2359 = 0x00000029;

	public static final int LOCALE_SDAYNAME1 = 0x0000002A; // long name for

	// Monday
	public static final int LOCALE_SDAYNAME2 = 0x0000002B; // long name for

	// Tuesday
	public static final int LOCALE_SDAYNAME3 = 0x0000002C; // long name for

	// Wednesday
	public static final int LOCALE_SDAYNAME4 = 0x0000002D; // long name for

	// Thursday
	public static final int LOCALE_SDAYNAME5 = 0x0000002E; // long name for

	// Friday
	public static final int LOCALE_SDAYNAME6 = 0x0000002F; // long name for

	// Saturday
	public static final int LOCALE_SDAYNAME7 = 0x00000030; // long name for

	// Sunday
	public static final int LOCALE_SDECIMAL = 14;

	public static final int LOCALE_SISO3166CTRYNAME = 0x5a;

	public static final int LOCALE_SISO639LANGNAME = 0x59;

	public static final int LOCALE_SLONGDATE = 0x00000020;

	public static final int LOCALE_SMONTHNAME1 = 0x00000038; // long name for

	// January
	public static final int LOCALE_SMONTHNAME10 = 0x00000041; // long name for

	// October
	public static final int LOCALE_SMONTHNAME11 = 0x00000042; // long name for

	// November
	public static final int LOCALE_SMONTHNAME12 = 0x00000043; // long name for

	// December
	public static final int LOCALE_SMONTHNAME2 = 0x00000039; // long name for

	// February
	public static final int LOCALE_SMONTHNAME3 = 0x0000003A; // long name for

	// March
	public static final int LOCALE_SMONTHNAME4 = 0x0000003B; // long name for

	// April
	public static final int LOCALE_SMONTHNAME5 = 0x0000003C; // long name for

	// May
	public static final int LOCALE_SMONTHNAME6 = 0x0000003D; // long name for

	// June
	public static final int LOCALE_SMONTHNAME7 = 0x0000003E; // long name for

	// July
	public static final int LOCALE_SMONTHNAME8 = 0x0000003F; // long name for

	// August
	public static final int LOCALE_SMONTHNAME9 = 0x00000040; // long name for

	// September
	public static final int LOCALE_SSHORTDATE = 0x0000001F;

	public static final int LOCALE_STIMEFORMAT = 0x00001003;

	public static final int LOCALE_SYEARMONTH = 0x00001006; // #if(WINVER >=

	// 0x0500)
	public static final int LOCALE_USER_DEFAULT = 1024;

	public static final int LOGPIXELSX = 0x58;

	public static final int LOGPIXELSY = 0x5a;

	public static final int LPSTR_TEXTCALLBACK = 0xffffffff;

	public static final int LR_DEFAULTCOLOR = 0x0;

	public static final int LR_SHARED = 0x8000;

	public static final int LVCF_FMT = 0x1;

	public static final int LVCF_IMAGE = 0x10;

	public static final int LVCF_TEXT = 0x4;

	public static final int LVCF_WIDTH = 0x2;

	public static final int LVCFMT_BITMAP_ON_RIGHT = 0x1000;

	public static final int LVCFMT_CENTER = 0x2;

	public static final int LVCFMT_IMAGE = 0x800;

	public static final int LVCFMT_JUSTIFYMASK = 0x3;

	public static final int LVCFMT_LEFT = 0x0;

	public static final int LVCFMT_RIGHT = 0x1;

	public static final int LVHT_ONITEM = 0xe;

	public static final int LVHT_ONITEMICON = 0x2;

	public static final int LVHT_ONITEMLABEL = 0x4;

	public static final int LVHT_ONITEMSTATEICON = 0x8;

	public static final int LVIF_IMAGE = 0x2;

	public static final int LVIF_INDENT = 0x10;

	public static final int LVIF_STATE = 0x8;

	public static final int LVIF_TEXT = 0x1;

	public static final int LVIR_BOUNDS = 0x0;

	public static final int LVIR_ICON = 0x1;

	public static final int LVIR_LABEL = 0x2;

	public static final int LVIR_SELECTBOUNDS = 0x3;

	public static final int LVIS_DROPHILITED = 0x8;

	public static final int LVIS_FOCUSED = 0x1;

	public static final int LVIS_SELECTED = 0x2;

	public static final int LVIS_STATEIMAGEMASK = 0xf000;

	public static final int LVM_APPROXIMATEVIEWRECT = 0x1040;

	public static final int LVM_FIRST = 0x1000;

	public static final int LVM_CREATEDRAGIMAGE = LVM_FIRST + 33;

	public static final int LVM_DELETEALLITEMS = 0x1009;

	public static final int LVM_DELETECOLUMN = 0x101c;

	public static final int LVM_DELETEITEM = 0x1008;

	public static final int LVM_ENSUREVISIBLE = 0x1013;

	public static final int LVM_GETBKCOLOR = 0x1000;

	public static final int LVM_GETCOLUMN = Extension.IsUnicode ? 0x105f
			: 0x1019;

	public static final int LVM_GETCOLUMNORDERARRAY = LVM_FIRST + 59;

	public static final int LVM_GETCOLUMNWIDTH = 0x101d;

	public static final int LVM_GETCOUNTPERPAGE = 0x1028;

	public static final int LVM_GETEXTENDEDLISTVIEWSTYLE = 0x1037;

	public static final int LVM_GETHEADER = 0x101f;

	public static final int LVM_GETIMAGELIST = 0x1002;

	public static final int LVM_GETITEM = Extension.IsUnicode ? 0x104b : 0x1005;

	public static final int LVM_GETITEMA = 0x1005;

	public static final int LVM_GETITEMCOUNT = 0x1004;

	public static final int LVM_GETITEMRECT = 0x100e;

	public static final int LVM_GETITEMSTATE = 0x102c;

	public static final int LVM_GETITEMW = 0x104b;

	public static final int LVM_GETNEXTITEM = 0x100c;

	public static final int LVM_GETSELECTEDCOLUMN = LVM_FIRST + 174;

	public static final int LVM_GETSELECTEDCOUNT = 0x1032;

	public static final int LVM_GETSTRINGWIDTH = Extension.IsUnicode ? 0x1057
			: 0x1011;

	public static final int LVM_GETSUBITEMRECT = 0x1038;

	public static final int LVM_GETTEXTCOLOR = 0x1023;

	public static final int LVM_GETTOOLTIPS = 0x104e;

	public static final int LVM_GETTOPINDEX = 0x1027;

	public static final int LVM_HITTEST = 0x1012;

	public static final int LVM_INSERTCOLUMN = Extension.IsUnicode ? 0x1061
			: 0x101b;

	public static final int LVM_INSERTITEM = Extension.IsUnicode ? 0x104d
			: 0x1007;

	public static final int LVM_REDRAWITEMS = LVM_FIRST + 21;

	public static final int LVM_SCROLL = 0x1014;

	public static final int LVM_SETBKCOLOR = 0x1001;

	public static final int LVM_SETCALLBACKMASK = LVM_FIRST + 11;

	public static final int LVM_SETCOLUMN = Extension.IsUnicode ? 0x1060
			: 0x101a;

	public static final int LVM_SETCOLUMNORDERARRAY = LVM_FIRST + 58;

	public static final int LVM_SETCOLUMNWIDTH = 0x101e;

	public static final int LVM_SETEXTENDEDLISTVIEWSTYLE = 0x1036;

	public static final int LVM_SETIMAGELIST = 0x1003;

	public static final int LVM_SETITEM = Extension.IsUnicode ? 0x104c : 0x1006;

	public static final int LVM_SETITEMCOUNT = LVM_FIRST + 47;

	public static final int LVM_SETITEMSTATE = 0x102b;

	public static final int LVM_SETSELECTEDCOLUMN = LVM_FIRST + 140;

	public static final int LVM_SETSELECTIONMARK = LVM_FIRST + 67;

	public static final int LVM_SETTEXTBKCOLOR = 0x1026;

	public static final int LVM_SETTEXTCOLOR = 0x1024;

	public static final int LVM_SETTOOLTIPS = LVM_FIRST + 74;

	public static final int LVM_SUBITEMHITTEST = LVM_FIRST + 57;

	public static final int LVN_BEGINDRAG = 0xffffff93;

	public static final int LVN_BEGINRDRAG = 0xffffff91;

	public static final int LVN_COLUMNCLICK = 0xffffff94;

	public static final int LVN_FIRST = 0xffffff9c;

	public static final int LVN_GETDISPINFOA = LVN_FIRST - 50;

	public static final int LVN_GETDISPINFOW = LVN_FIRST - 77;

	public static final int LVN_ITEMACTIVATE = 0xffffff8e;

	public static final int LVN_ITEMCHANGED = 0xffffff9b;

	public static final int LVN_MARQUEEBEGIN = 0xffffff64;

	public static final int LVN_ODFINDITEMA = LVN_FIRST - 52;

	public static final int LVN_ODFINDITEMW = LVN_FIRST - 79;

	public static final int LVN_ODSTATECHANGED = LVN_FIRST - 15;

	public static final int LVNI_FOCUSED = 0x1;

	public static final int LVNI_SELECTED = 0x2;

	public static final int LVP_LISTITEM = 1;

	public static final int LVS_EX_DOUBLEBUFFER = 0x10000;

	public static final int LVS_EX_FULLROWSELECT = 0x20;

	public static final int LVS_EX_GRIDLINES = 0x1;

	public static final int LVS_EX_HEADERDRAGDROP = 0x10;

	public static final int LVS_EX_LABELTIP = 0x4000;

	public static final int LVS_EX_ONECLICKACTIVATE = 0x40;

	public static final int LVS_EX_SUBITEMIMAGES = 0x2;

	public static final int LVS_EX_TRACKSELECT = 0x8;

	public static final int LVS_EX_TRANSPARENTBKGND = 0x800000;

	public static final int LVS_EX_TWOCLICKACTIVATE = 0x80;

	public static final int LVS_LIST = 0x3;

	public static final int LVS_NOCOLUMNHEADER = 0x4000;

	public static final int LVS_NOSCROLL = 0x2000;

	public static final int LVS_OWNERDATA = 0x1000;

	public static final int LVS_OWNERDRAWFIXED = 0x400;

	public static final int LVS_REPORT = 0x1;

	public static final int LVS_SHAREIMAGELISTS = 0x40;

	public static final int LVS_SHOWSELALWAYS = 0x8;

	public static final int LVS_SINGLESEL = 0x4;

	public static final int LVSCW_AUTOSIZE = 0xffffffff;

	public static final int LVSCW_AUTOSIZE_USEHEADER = 0xfffffffe;

	public static final int LVSICF_NOINVALIDATEALL = 0x1;

	public static final int LVSICF_NOSCROLL = 0x2;

	public static final int LVSIL_SMALL = 0x1;

	public static final int LVSIL_STATE = 0x2;

	public static final int LWA_ALPHA = 0x00000002;

	public static final int LWA_COLORKEY = 0x00000001;

	// public static final int MAX_PATH = 260;
	public static final int MA_NOACTIVATE = 0x3;

	public static final int MAX_LINKID_TEXT = 48;

	// public static final int MANIFEST_RESOURCE_ID = 2;
	public static final int MB_ABORTRETRYIGNORE = 0x2;

	public static final int MB_APPLMODAL = 0x0;

	public static final int MB_ICONERROR = 0x10;

	public static final int MB_ICONINFORMATION = 0x40;

	public static final int MB_ICONQUESTION = 0x20;

	public static final int MB_ICONWARNING = 0x30;

	public static final int MB_OK = 0x0;

	public static final int MB_OKCANCEL = 0x1;

	public static final int MB_PRECOMPOSED = 0x1;

	public static final int MB_RETRYCANCEL = 0x5;

	public static final int MB_RTLREADING = 0x100000;

	public static final int MB_SYSTEMMODAL = 0x1000;

	public static final int MB_TASKMODAL = 0x2000;

	public static final int MB_TOPMOST = 0x00040000;

	public static final int MB_YESNO = 0x4;

	public static final int MB_YESNOCANCEL = 0x3;

	public static final int MCM_FIRST = 0x1000;

	public static final int MCM_GETCURSEL = MCM_FIRST + 1;

	public static final int MCM_GETMINREQRECT = MCM_FIRST + 9;

	public static final int MCM_SETCURSEL = MCM_FIRST + 2;

	public static final int MCN_FIRST = 0xFFFFFD12;

	public static final int MCN_SELCHANGE = MCN_FIRST + 1;

	public static final int MCS_NOTODAY = 0x0010;

	public static final int MDIS_ALLCHILDSTYLES = 0x0001;

	public static final int MF_BYCOMMAND = 0x0;

	public static final int MF_BYPOSITION = 0x400;

	public static final int MF_CHECKED = 0x8;

	public static final int MF_DISABLED = 0x2;

	public static final int MF_ENABLED = 0x0;

	public static final int MF_GRAYED = 0x1;

	public static final int MF_HILITE = 0x80;

	public static final int MF_POPUP = 0x10;

	public static final int MF_STRING = 0x0;

	public static final int MF_BITMAP = 0x4;

	public static final int MF_SEPARATOR = 0x800;

	public static final int MF_SYSMENU = 0x2000;

	public static final int MF_UNCHECKED = 0x0;

	public static final int MFS_CHECKED = 0x8;

	public static final int MFS_DISABLED = 0x3;

	public static final int MFS_GRAYED = 0x3;

	public static final int MFT_RADIOCHECK = 0x200;

	public static final int MFT_RIGHTJUSTIFY = 0x4000;

	public static final int MFT_RIGHTORDER = 0x2000;

	public static final int MFT_SEPARATOR = 0x800;

	public static final int MFT_STRING = 0x0;

	public static final int MIIM_BITMAP = 0x80;

	public static final int MIIM_DATA = 0x20;

	public static final int MIIM_ID = 0x2;

	public static final int MIIM_STATE = 0x1;

	public static final int MIIM_STRING = 0x40;

	public static final int MIIM_SUBMENU = 0x4;

	public static final int MIIM_TYPE = 0x10;

	public static final int MIM_BACKGROUND = 0x2;

	public static final int MIM_STYLE = 0x10;

	public static final int MK_ALT = 0x20;

	public static final int MK_CONTROL = 0x8;

	public static final int MK_LBUTTON = 0x1;

	public static final int MK_MBUTTON = 0x10;

	public static final int MK_RBUTTON = 0x2;

	public static final int MK_SHIFT = 0x4;

	public static final int MK_XBUTTON1 = 0x20;

	public static final int MK_XBUTTON2 = 0x40;

	public static final int MM_TEXT = 0x1;

	public static final int MNC_CLOSE = 0x1;

	public static final int MNS_CHECKORBMP = 0x4000000;

	public static final int MONITOR_DEFAULTTONEAREST = 0x2;

	public static final int MONITORINFOF_PRIMARY = 0x1;

	public static final String MONTHCAL_CLASS = "SysMonthCal32"; //$NON-NLS-1$

	public static final int MOUSEEVENTF_ABSOLUTE = 0x8000;

	public static final int MOUSEEVENTF_LEFTDOWN = 0x0002;

	public static final int MOUSEEVENTF_LEFTUP = 0x0004;

	public static final int MOUSEEVENTF_MIDDLEDOWN = 0x0020;

	public static final int MOUSEEVENTF_MIDDLEUP = 0x0040;

	public static final int MOUSEEVENTF_MOVE = 0x0001;

	public static final int MOUSEEVENTF_RIGHTDOWN = 0x0008;

	public static final int MOUSEEVENTF_RIGHTUP = 0x0010;

	public static final int MOUSEEVENTF_VIRTUALDESK = 0x4000;

	public static final int MOUSEEVENTF_WHEEL = 0x0800;

	public static final int MOUSEEVENTF_XDOWN = 0x0080;

	public static final int MOUSEEVENTF_XUP = 0x0100;

	public static final int MSGF_COMMCTRL_BEGINDRAG = 0x4200;

	public static final int MSGF_COMMCTRL_DRAGSELECT = 0x4202;

	public static final int MSGF_COMMCTRL_SIZEHEADER = 0x4201;

	public static final int MSGF_COMMCTRL_TOOLBARCUST = 0x4203;

	public static final int MSGF_DDEMGR = 32769;

	public static final int MSGF_DIALOGBOX = 0;

	public static final int MSGF_MAINLOOP = 8;

	public static final int MSGF_MAX = 8;

	public static final int MSGF_MENU = 2;

	public static final int MSGF_MESSAGEBOX = 1;

	public static final int MSGF_MOVE = 3;

	public static final int MSGF_NEXTWINDOW = 6;

	public static final int MSGF_SCROLLBAR = 5;

	public static final int MSGF_SIZE = 4;

	public static final int MSGF_USER = 4096;

	public static final int MWMO_INPUTAVAILABLE = 0x4;

	public static final int NI_COMPOSITIONSTR = 0x15;

	public static final int NIF_ICON = 0x00000002;

	public static final int NIF_INFO = 0x00000010;

	public static final int NIF_MESSAGE = 0x00000001;

	public static final int NIF_STATE = 0x00000008;

	public static final int NIF_TIP = 0x00000004;

	public static final int NIIF_ERROR = 0x00000003;

	public static final int NIIF_INFO = 0x00000001;

	public static final int NIIF_NONE = 0x00000000;

	public static final int NIIF_WARNING = 0x00000002;

	public static final int NIM_ADD = 0x00000000;

	public static final int NIM_DELETE = 0x00000002;

	public static final int NIM_MODIFY = 0x00000001;

	public static final int NIN_BALLOONHIDE = 0x400 + 3;

	public static final int NIN_BALLOONSHOW = 0x400 + 2;

	public static final int NIN_BALLOONTIMEOUT = 0x400 + 4;

	public static final int NIN_BALLOONUSERCLICK = 0x400 + 5;

	public static final int NIN_SELECT = 0x400 + 0;

	public static final int NINF_KEY = 0x1;

	public static final int NIS_HIDDEN = 0x00000001;

	public static final int NIN_KEYSELECT = NIN_SELECT | NINF_KEY;

	public static final int NM_CLICK = 0xfffffffe;

	public static final int NM_FIRST = 0x0;

	public static final int NM_CUSTOMDRAW = NM_FIRST - 12;

	public static final int NM_DBLCLK = 0xfffffffd;

	public static final int NM_RECOGNIZEGESTURE = NM_FIRST - 16;

	public static final int NM_RELEASEDCAPTURE = NM_FIRST - 16;

	public static final int NM_RETURN = 0xfffffffc;

	public static final int NOTSRCCOPY = 0x330008;

	public static final int NULL_BRUSH = 0x5;

	public static final int NULL_PEN = 0x8;

	public static final int NULLREGION = 0x1;

	public static final int NUMRESERVED = 106;

	public static final int OBJ_BITMAP = 0x7;

	public static final int OBJ_FONT = 0x6;

	public static final int OBJ_PEN = 0x1;

	public static final int OBJID_CARET = 0xFFFFFFF8;

	public static final int OBJID_CLIENT = 0xFFFFFFFC;

	public static final int OBJID_HSCROLL = 0xFFFFFFFA;

	public static final int OBJID_MENU = 0xFFFFFFFD;

	public static final int OBJID_VSCROLL = 0xFFFFFFFB;

	public static final int OBJID_WINDOW = 0x00000000;

	public static final int OBM_CHECKBOXES = 0x7ff7;

	public static final int ODS_SELECTED = 0x1;

	public static final int ODT_MENU = 0x1;

	public static final int OFN_ALLOWMULTISELECT = 0x200;

	public static final int OFN_ENABLEHOOK = 0x20;

	public static final int OFN_EXPLORER = 0x80000;

	public static final int OFN_HIDEREADONLY = 0x4;

	public static final int OFN_NOCHANGEDIR = 0x8;

	public static final int OIC_BANG = 0x7F03;

	public static final int OIC_HAND = 0x7F01;

	public static final int OIC_INFORMATION = 0x7F04;

	public static final int OIC_QUES = 0x7F02;

	public static final int OIC_WINLOGO = 0x7F05;

	public static final int OPAQUE = 0x2;

	public static final int PATCOPY = 0xf00021;

	public static final int PATINVERT = 0x5a0049;

	public static final int PBM_GETPOS = 0x408;

	public static final int PBM_GETRANGE = 0x407;

	public static final int PBM_GETSTATE = 0x400 + 17;

	public static final int PBM_SETBARCOLOR = 0x409;

	public static final int PBM_SETBKCOLOR = 0x2001;

	public static final int PBM_SETMARQUEE = 0x400 + 10;

	public static final int PBM_SETPOS = 0x402;

	public static final int PBM_SETRANGE32 = 0x406;

	public static final int PBM_SETSTATE = 0x400 + 16;

	public static final int PBM_STEPIT = 0x405;

	public static final int PBS_DEFAULTED = 5;

	public static final int PBS_DISABLED = 4;

	public static final int PBS_HOT = 2;

	public static final int PBS_MARQUEE = 0x08;

	public static final int PBS_NORMAL = 1;

	public static final int PBS_PRESSED = 3;

	public static final int PBS_SMOOTH = 0x1;

	public static final int PBS_VERTICAL = 0x4;

	public static final int PBST_ERROR = 0x0002;

	public static final int PBST_NORMAL = 0x0001;

	public static final int PBST_PAUSED = 0x0003;

	public static final int PD_ALLPAGES = 0x0;

	public static final int PD_COLLATE = 0x10;

	public static final int PD_PAGENUMS = 0x2;

	public static final int PD_PRINTTOFILE = 0x20;

	public static final int PD_RETURNDC = 0x100;

	public static final int PD_SELECTION = 0x1;

	public static final int PD_USEDEVMODECOPIESANDCOLLATE = 0x40000;

	public static final int PFM_TABSTOPS = 0x10;

	public static final int PHYSICALHEIGHT = 0x6f;

	public static final int PHYSICALOFFSETX = 0x70;

	public static final int PHYSICALOFFSETY = 0x71;

	public static final int PHYSICALWIDTH = 0x6e;

	public static final int PLANES = 0xe;

	public static final int PM_NOREMOVE = 0;

	public static final int PM_NOYIELD = 0x2;

	public static final int PP_BAR = 1;

	public static final int PP_BARVERT = 2;

	public static final int PP_CHUNK = 3;

	public static final int PP_CHUNKVERT = 4;

	public static final int PRF_CHILDREN = 16;

	public static final int PRF_CLIENT = 0x4;

	public static final int PRF_ERASEBKGND = 0x8;

	public static final int PRF_NONCLIENT = 0x2;

	public static final String PROGRESS_CLASS = "msctls_progress32"; //$NON-NLS-1$

	public static final int PROGRESSCHUNKSIZE = 2411;

	public static final int PROGRESSSPACESIZE = 2412;

	public static final int PS_DASH = 0x1;

	public static final int PS_DASHDOT = 0x3;

	public static final int PS_DASHDOTDOT = 0x4;

	public static final int PS_DOT = 0x2;

	public static final int PS_ENDCAP_FLAT = 0x200;

	public static final int PS_ENDCAP_MASK = 0xF00;

	public static final int PS_ENDCAP_ROUND = 0x000;

	public static final int PS_ENDCAP_SQUARE = 0x100;

	public static final int PS_GEOMETRIC = 0x10000;

	public static final int PS_JOIN_BEVEL = 0x1000;

	public static final int PS_JOIN_MASK = 0xF000;

	public static final int PS_JOIN_MITER = 0x2000;

	public static final int PS_JOIN_ROUND = 0x0000;

	public static final int PS_SOLID = 0x0;

	public static final int PS_STYLE_MASK = 0xf;

	public static final int PS_TYPE_MASK = 0x000f0000;

	public static final int PS_USERSTYLE = 0x7;

	public static final int PT_BEZIERTO = 4;

	public static final int PT_CLOSEFIGURE = 1;

	public static final int PT_LINETO = 2;

	public static final int PT_MOVETO = 6;

	public static final int QS_MOUSEBUTTON = 0x0004;

	public static final int QS_MOUSEMOVE = 0x0002;

	public static final int QS_PAINT = 0x0020;

	public static final int QS_POSTMESSAGE = 0x0008;

	public static final int QS_SENDMESSAGE = 0x0040;

	public static final int QS_TIMER = 0x0010;

	public static final int QS_KEY = 0x0001;

	public static final int QS_MOUSE = QS_MOUSEMOVE | QS_MOUSEBUTTON;

	public static final int QS_ALLINPUT = QS_MOUSEMOVE | QS_MOUSEBUTTON
			| QS_KEY | QS_POSTMESSAGE | QS_TIMER | QS_PAINT | QS_SENDMESSAGE;

	public static final int QS_HOTKEY = 0x0080;

	public static final int QS_INPUT = QS_KEY | QS_MOUSE;

	public static final int PM_QS_INPUT = QS_INPUT << 16;

	public static final int PM_QS_PAINT = QS_PAINT << 16;

	public static final int PM_QS_POSTMESSAGE = (QS_POSTMESSAGE | QS_HOTKEY | QS_TIMER) << 16;

	public static final int PM_QS_SENDMESSAGE = QS_SENDMESSAGE << 16;

	public static final int PM_REMOVE = 0x1;

	public static final int R2_COPYPEN = 0xd;

	public static final int R2_XORPEN = 0x7;

	public static final int RASTER_FONTTYPE = 0x1;

	public static final int RASTERCAPS = 0x26;

	public static final int RB_DELETEBAND = 0x402;

	public static final int RB_GETBANDBORDERS = 0x422;

	public static final int RB_GETBANDCOUNT = 0x40c;

	public static final int RB_GETBANDINFO = Extension.IsUnicode ? 0x41c
			: 0x41d;

	public static final int RB_GETBANDMARGINS = 0x428;

	public static final int RB_GETBARHEIGHT = 0x41b;

	public static final int RB_GETBKCOLOR = 0x414;

	public static final int RB_GETRECT = 0x409;

	public static final int RB_GETTEXTCOLOR = 0x416;

	public static final int RB_IDTOINDEX = 0x410;

	public static final int RB_INSERTBAND = Extension.IsUnicode ? 0x40a : 0x401;

	public static final int RB_MOVEBAND = 0x427;

	public static final int RB_SETBANDINFO = Extension.IsUnicode ? 0x40b
			: 0x406;

	public static final int RB_SETBKCOLOR = 0x413;

	public static final int RB_SETTEXTCOLOR = 0x415;

	public static final int RBBIM_CHILD = 0x10;

	public static final int RBBIM_CHILDSIZE = 0x20;

	public static final int RBBIM_COLORS = 0x2;

	public static final int RBBIM_HEADERSIZE = 0x800;

	public static final int RBBIM_ID = 0x100;

	public static final int RBBIM_IDEALSIZE = 0x200;

	public static final int RBBIM_SIZE = 0x40;

	public static final int RBBIM_STYLE = 0x1;

	public static final int RBBIM_TEXT = 0x4;

	public static final int RBBS_BREAK = 0x1;

	public static final int RBBS_GRIPPERALWAYS = 0x80;

	public static final int RBBS_NOGRIPPER = 0x00000100;

	public static final int RBBS_USECHEVRON = 0x00000200;

	public static final int RBBS_VARIABLEHEIGHT = 0x40;

	public static final int RBN_FIRST = 0xfffffcc1;

	public static final int RBN_BEGINDRAG = RBN_FIRST - 4;

	public static final int RBN_CHEVRONPUSHED = RBN_FIRST - 10;

	public static final int RBN_CHILDSIZE = RBN_FIRST - 8;

	public static final int RBN_HEIGHTCHANGE = 0xfffffcc1;

	public static final int RBS_BANDBORDERS = 0x400;

	public static final int RBS_DBLCLKTOGGLE = 0x8000;

	public static final int RBS_VARHEIGHT = 0x200;

	public static final int RC_BITBLT = 0x1;

	public static final int RC_PALETTE = 0x100;

	public static final int RDW_ALLCHILDREN = 0x0080;

	public static final int RDW_ERASE = 0x4;

	public static final int RDW_ERASENOW = 0x0200;

	public static final int RDW_FRAME = 0x0400;

	public static final int RDW_INTERNALPAINT = 0x0002;

	public static final int RDW_INVALIDATE = 0x0001;

	public static final int RDW_NOCHILDREN = 0x0040;

	public static final int RDW_NOERASE = 0x0020;

	public static final int RDW_NOFRAME = 0x0800;

	public static final int RDW_NOINTERNALPAINT = 0x0010;

	public static final int RDW_UPDATENOW = 0x0100;

	public static final int RDW_VALIDATE = 0x0008;

	public static final int READ_CONTROL = 0x20000;

	public static final String REBARCLASSNAME = "ReBarWindow32"; //$NON-NLS-1$

	public static final int RGN_AND = 0x1;

	public static final int RGN_COPY = 5;

	public static final int RGN_DIFF = 0x4;

	public static final int RGN_ERROR = 0;

	public static final int RGN_OR = 0x2;

	public static final int RP_BAND = 3;

	public static final int S_OK = 0x0;

	public static final int SB_BOTH = 0x3;

	public static final int SB_BOTTOM = 0x7;

	public static final int SB_CTL = 0x2;

	public static final int SB_ENDSCROLL = 0x8;

	public static final int SB_HORZ = 0x0;

	public static final int SB_LINEDOWN = 0x1;

	public static final int SB_LINEUP = 0x0;

	public static final int SB_PAGEDOWN = 0x3;

	public static final int SB_PAGEUP = 0x2;

	public static final int SB_THUMBPOSITION = 0x4;

	public static final int SB_THUMBTRACK = 0x5;

	public static final int SB_TOP = 0x6;

	public static final int SB_VERT = 0x1;

	public static final int SBM_ENABLE_ARROWS = 0x00E4;

	public static final int SBM_GETPOS = 0x00E1;

	public static final int SBM_GETRANGE = 0x00E3;

	public static final int SBM_GETSCROLLINFO = 0x00EA;

	public static final int SBM_SETPOS = 0x00E0;

	public static final int SBM_SETRANGE = 0x00E2;

	public static final int SBM_SETRANGEREDRAW = 0x00E6;

	public static final int SBM_SETSCROLLINFO = 0x00E9;

	public static final int SBP_ARROWBTN = 0x1;

	public static final int SBP_GRIPPERHORZ = 8;

	public static final int SBP_GRIPPERVERT = 9;

	public static final int SBP_LOWERTRACKHORZ = 4;

	public static final int SBP_LOWERTRACKVERT = 6;

	public static final int SBP_SIZEBOX = 10;

	public static final int SBP_THUMBBTNHORZ = 2;

	public static final int SBP_THUMBBTNVERT = 3;

	public static final int SBP_UPPERTRACKHORZ = 5;

	public static final int SBP_UPPERTRACKVERT = 7;

	public static final int SBS_BOTTOMALIGN = 0x0004;

	public static final int SBS_HORZ = 0x0000;

	public static final int SBS_LEFTALIGN = 0x0002;

	public static final int SBS_RIGHTALIGN = 0x0004;

	public static final int SBS_SIZEBOX = 0x0008;

	public static final int SBS_SIZEBOXBOTTOMRIGHTALIGN = 0x0004;

	public static final int SBS_SIZEBOXTOPLEFTALIGN = 0x0002;

	public static final int SBS_SIZEGRIP = 0x0010;

	public static final int SBS_TOPALIGN = 0x0002;

	public static final int SBS_VERT = 0x0001;

	public static final int SC_CLOSE = 0xf060;

	public static final int SC_HSCROLL = 0xf080;

	public static final int SC_KEYMENU = 0xf100;

	public static final int SC_MAXIMIZE = 0xf030;

	public static final int SC_MINIMIZE = 0xf020;

	public static final int SC_NEXTWINDOW = 0xF040;

	public static final int SC_RESTORE = 0xf120;

	public static final int SC_SIZE = 0xf000;

	public static final int SC_TASKLIST = 0xf130;

	public static final int SC_VSCROLL = 0xf070;

	public static final int SC_MOVE = 0xF010;

	public static final int SCF_ALL = 0x4;

	public static final int SCF_DEFAULT = 0x0;

	public static final int SCF_SELECTION = 0x1;

	public static final int SCRBS_DISABLED = 4;

	public static final int SCRBS_HOT = 2;

	public static final int SCRBS_NORMAL = 1;

	public static final int SCRBS_PRESSED = 3;

	public static final int SEM_FAILCRITICALERRORS = 0x1;

	public static final int SET_FEATURE_ON_PROCESS = 0x2;

	public static final int SF_RTF = 0x2;

	public static final int SFGAO_SHARE = 0x20000;

	public static final int SHCMBF_HIDDEN = 0x2;

	public static final int SHCMBM_GETSUBMENU = 0x591;

	public static final int SHCMBM_OVERRIDEKEY = 0x400 + 403;

	public static final int SHCMBM_SETSUBMENU = 0x590;

	public static final String SHELL_TRAY = "Shell_TrayWnd";

	/**
	 * apply the appropriate overlays
	 */
	public static final int SHGFI_ADDOVERLAYS = 0x000000020;

	/**
	 * get icon specified attributes
	 */
	public static final int SHGFI_ATTR_SPECIFIED = 0x000020000;

	/**
	 * get attributes
	 */
	public static final int SHGFI_ATTRIBUTES = 0x000000800;

	/**
	 * get display name
	 */
	public static final int SHGFI_DISPLAYNAME = 0x000000200;

	/**
	 * return exe type
	 */
	public static final int SHGFI_EXETYPE = 0x000002000;

	/**
	 * get icon
	 */
	public static final int SHGFI_ICON = 0x000000100;

	/**
	 * get icon location
	 */
	public static final int SHGFI_ICONLOCATION = 0x000001000;

	/**
	 * get large icon
	 */
	public static final int SHGFI_LARGEICON = 0x000000000;

	/**
	 * put a link overlay on icon
	 */
	public static final int SHGFI_LINKOVERLAY = 0x000008000;

	/**
	 * get open icon
	 */
	public static final int SHGFI_OPENICON = 0x000000002;

	/**
	 * get the index of the overlay
	 */
	public static final int SHGFI_OVERLAYINDEX = 0x000000040;

	/**
	 * pszPath is a pidl
	 */
	public static final int SHGFI_PIDL = 0x000000008;

	/**
	 * show icon in selected state
	 */
	public static final int SHGFI_SELECTED = 0x000010000;

	/**
	 * get shell size icon
	 */
	public static final int SHGFI_SHELLICONSIZE = 0x000000004;

	/**
	 * get small icon
	 */
	public static final int SHGFI_SMALLICON = 0x000000001;

	/**
	 * get system icon index
	 */
	public static final int SHGFI_SYSICONINDEX = 0x000004000;

	/**
	 * get type name
	 */
	public static final int SHGFI_TYPENAME = 0x000000400;

	/**
	 * use passed dwFileAttribute
	 */
	public static final int SHGFI_USEFILEATTRIBUTES = 0x000000010;

	public static final int SHMBOF_NODEFAULT = 0x1;

	public static final int SHMBOF_NOTIFY = 0x2;

	public static final int SHRG_RETURNCMD = 0x1;

	public static final int SIF_ALL = (0x0001 | 0x0002 | 0x0004 | 0x0010);

	public static final int SIF_DISABLENOSCROLL = 0x0008;

	public static final int SIF_PAGE = 0x0002;

	public static final int SIF_POS = 0x4;

	public static final int SIF_RANGE = 0x0001;

	public static final int SIF_TRACKPOS = 0x10;

	public static final int SIP_DOWN = 1;

	public static final int SIP_UP = 0;

	public static final int SIPF_ON = 0x1;

	public static final int SIZE_MAXIMIZED = 2;

	public static final int SIZE_MINIMIZED = 1;

	public static final int SIZE_RESTORED = 0x0;

	public static final int SIZEPALETTE = 104;

	public static final int SM_CMONITORS = 80;

	public static final int SM_CMOUSEBUTTONS = 43;

	public static final int SM_CXBORDER = 0x5;

	public static final int SM_CXCURSOR = 0xd;

	public static final int SM_CXDOUBLECLK = 36;

	public static final int SM_CXEDGE = 0x2d;

	public static final int SM_CXHSCROLL = 0x15;

	public static final int SM_CXICON = 0x0b;

	public static final int SM_CXMINTRACK = 34;

	public static final int SM_CXSCREEN = 0x0;

	public static final int SM_CXSMICON = 49;

	public static final int SM_CXVIRTUALSCREEN = 78;

	public static final int SM_CXVSCROLL = 0x2;

	public static final int SM_CYBORDER = 0x6;

	public static final int SM_CYCURSOR = 0xe;

	public static final int SM_CYDOUBLECLK = 37;

	public static final int SM_CYHSCROLL = 0x3;

	public static final int SM_CYICON = 0x0c;

	public static final int SM_CYMENU = 0xf;

	public static final int SM_CYMINTRACK = 35;

	public static final int SM_CYSCREEN = 0x1;

	public static final int SM_CYSMICON = 50;

	public static final int SM_CYVIRTUALSCREEN = 79;

	public static final int SM_CYVSCROLL = 0x14;
	
	public static final int SM_CYCAPTION = 4;
	
	public static final int SM_CXSIZEFRAME = 32;
	
	public static final int SM_CXSIZE = 30;
	
	public static final int SM_CYSIZE = 31;

	public static final int SM_XVIRTUALSCREEN = 76;

	public static final int SM_YVIRTUALSCREEN = 77;

	public static final int SPI_GETCARETWIDTH = 0x2006;

	public static final int SPI_GETDROPSHADOW = 0x1024;

	// public static final int SM_DBCSENABLED = 0x2A;
	// public static final int SM_IMMENABLED = 0x52;
	public static final int SPI_GETFONTSMOOTHINGTYPE = 0x200A;

	public static final int SPI_GETHIGHCONTRAST = 66;

	public static final int SPI_GETMENUANIMATION = 0x1002;

	public static final int SPI_GETNONCLIENTMETRICS = 41;

	public static final int SPI_GETWHEELSCROLLLINES = 104;

	public static final int SPI_GETWORKAREA = 0x30;

	public static final int SPI_SETHIGHCONTRAST = 67;

	public static final int SPI_SETSIPINFO = 224;

	public static final int SRCAND = 0x8800c6;

	public static final int SRCCOPY = 0xcc0020;

	public static final int SRCINVERT = 0x660046;

	public static final int SRCPAINT = 0xee0086;

	public static final int SS_BITMAP = 0x0000000E;

	public static final int SS_BLACKFRAME = 0x00000007;

	public static final int SS_BLACKRECT = 0x00000004;

	public static final int SS_CENTER = 0x00000001;

	public static final int SS_CENTERIMAGE = 0x200;

	public static final int SS_EDITCONTROL = 0x2000;

	public static final int SS_ELLIPSISMASK = 0x0000C000;

	public static final int SS_ENDELLIPSIS = 0x00004000;

	public static final int SS_ENHMETAFILE = 0x0000000F;

	public static final int SS_ETCHEDFRAME = 0x00000012;

	public static final int SS_ETCHEDHORZ = 0x00000010;

	public static final int SS_ETCHEDVERT = 0x00000011;

	public static final int SS_GRAYFRAME = 0x00000008;

	public static final int SS_GRAYRECT = 0x00000005;

	public static final int SS_ICON = 0x00000003;

	public static final int SS_LEFT = 0x0;

	public static final int SS_LEFTNOWORDWRAP = 0x0000000C;

	public static final int SS_NOPREFIX = 0x00000080;

	public static final int SS_NOTIFY = 0x00000100;

	public static final int SS_OWNERDRAW = 0xd;

	public static final int SS_PATHELLIPSIS = 0x00008000;

	public static final int SS_REALSIZEIMAGE = 0x00000800;

	public static final int SS_RIGHT = 0x2;

	public static final int SS_RIGHTJUST = 0x00000400;

	public static final int SS_SIMPLE = 0x0000000B;

	public static final int SS_SUNKEN = 0x00001000;

	public static final int SS_TYPEMASK = 0x0000001F;

	public static final int SS_USERITEM = 0x0000000A;

	public static final int SS_WHITEFRAME = 0x00000009;

	public static final int SS_WHITERECT = 0x00000006;

	public static final int SS_WORDELLIPSIS = 0x0000C000;

	public static final int STANDARD_RIGHTS_READ = 0x20000;

	public static final int STARTF_USESHOWWINDOW = 0x1;

	/**
	 * Always-on-top on, autohide off (value is 2).
	 */
	public static final int STATE_ALWAYSONTOP = 0x0000002;

	/**
	 * Autohide on, always-on-top off (value is 1).
	 */
	public static final int STATE_AUTOHIDE = 0x0000001;

	/**
	 * Autohide and always-on-top both on (value is 1|2).
	 */
	public static final int STATE_AUTOHIDE_ALWAYSONTOP = STATE_AUTOHIDE
			| STATE_ALWAYSONTOP;

	/**
	 * The null state (value is 0).
	 */
	public static final int STATE_NONE = 0x0000000;

	public static final int STATE_SYSTEM_INVISIBLE = 0x00008000;

	public static final int STATE_SYSTEM_OFFSCREEN = 0x00010000;

	public static final int STATE_SYSTEM_UNAVAILABLE = 0x00000001;

	/**
	 * Specifies the abandoned status value of {@link EventObject#waitFor}
	 */
	public static final int STATUS_ABANDONED_WAIT_0 = 0x00000080;

	/**
	 * Specifies the timeout status value of {@link EventObject#waitFor}
	 */
	public static final int STATUS_TIMEOUT = 0x00000102;

	/**
	 * Specifies the status value of {@link EventObject#waitFor} functions.
	 */
	public static final int STATUS_WAIT_0 = 0x00000000;

	public static final int STD_COPY = 0x1;

	public static final int STD_CUT = 0x0;

	public static final int STD_FILENEW = 0x6;

	public static final int STD_FILEOPEN = 0x7;

	public static final int STD_FILESAVE = 0x8;

	public static final int STD_PASTE = 0x2;

	public static final int STM_GETICON = 0x0171;

	public static final int STM_GETIMAGE = 0x0173;

	public static final int STM_MSGMAX = 0x0174;

	public static final int STM_SETICON = 0x0170;

	public static final int STM_SETIMAGE = 0x0172;

	public static final int STN_CLICKED = 0;

	public static final int STN_DBLCLK = 1;

	public static final int STN_DISABLE = 3;

	public static final int STN_ENABLE = 2;

	public static final int SW_ERASE = 0x4;

	public static final int SW_HIDE = 0x0;

	public static final int SW_INVALIDATE = 0x2;

	public static final int SW_MINIMIZE = 0x6;

	public static final int SW_PARENTOPENING = 0x3;

	public static final int SW_RESTORE = Extension.IsWinCE ? 0xd : 0x9;

	public static final int SW_SCROLLCHILDREN = 0x1;

	public static final int SW_SHOW = 0x5;

	public static final int SW_SHOWMAXIMIZED = Extension.IsWinCE ? 0xb : 0x3;

	public static final int SW_SHOWMINIMIZED = 0x2;

	public static final int SW_SHOWMINNOACTIVE = 0x7;

	public static final int SW_SHOWNA = 0x8;

	public static final int SW_SHOWNOACTIVATE = 0x4;

	public static final int SWP_ASYNCWINDOWPOS = 0x4000;

	public static final int SWP_DRAWFRAME = 0x20;

	public static final int SWP_FRAMECHANGED = 0x20;

	public static final int SWP_HIDEWINDOW = 0x80;

	public static final int SWP_NOACTIVATE = 0x10;

	public static final int SWP_NOCOPYBITS = 0x100;

	public static final int SWP_NOMOVE = 0x2;

	public static final int SWP_NOREDRAW = 0x8;

	public static final int SWP_NOSIZE = 0x1;

	public static final int SWP_NOZORDER = 0x4;

	public static final int SWP_SHOWWINDOW = 0x40;

	public static final int SYNCHRONIZE = 0x100000;

	public static final int SYSRGN = 0x4;

	public static final int SYSTEM_FONT = 0xd;

	public static final int TABP_BODY = 10;

	public static final int TABP_PANE = 9;

	public static final int TABP_TABITEM = 1;

	public static final int TABP_TABITEMBOTHEDGE = 4;

	public static final int TABP_TABITEMLEFTEDGE = 2;

	public static final int TABP_TABITEMRIGHTEDGE = 3;

	public static final int TABP_TOPTABITEM = 5;

	public static final int TABP_TOPTABITEMBOTHEDGE = 8;

	public static final int TABP_TOPTABITEMLEFTEDGE = 6;

	public static final int TABP_TOPTABITEMRIGHTEDGE = 7;

	public static final int TB_ADDSTRING = Extension.IsUnicode ? 0x44d : 0x41c;

	public static final int TB_AUTOSIZE = 0x421;

	public static final int TB_BUTTONCOUNT = 0x418;

	public static final int TB_BUTTONSTRUCTSIZE = 0x41e;

	public static final int TB_COMMANDTOINDEX = 0x419;

	public static final int TB_DELETEBUTTON = 0x416;

	public static final int TB_ENDTRACK = 0x8;

	public static final int TB_GETBUTTON = 0x417;

	public static final int TB_GETBUTTONINFO = Extension.IsUnicode ? 0x43f
			: 0x441;

	public static final int TB_GETBUTTONSIZE = 0x43a;

	public static final int TB_GETBUTTONTEXT = Extension.IsUnicode ? 0x44b
			: 0x42d;

	public static final int TB_GETDISABLEDIMAGELIST = 0x437;

	public static final int TB_GETEXTENDEDSTYLE = 0x400 + 85;

	public static final int TB_GETHOTIMAGELIST = 0x435;

	public static final int TB_GETHOTITEM = 0x0400 + 71;

	public static final int TB_GETIMAGELIST = 0x431;

	public static final int TB_GETITEMRECT = 0x41d;

	public static final int TB_GETPADDING = 0x0400 + 86;

	public static final int TB_GETROWS = 0x428;

	public static final int TB_GETSTATE = 0x412;

	public static final int TB_GETTOOLTIPS = 0x423;

	public static final int TB_INSERTBUTTON = Extension.IsUnicode ? 0x443
			: 0x415;

	public static final int TB_LOADIMAGES = 0x432;

	public static final int TB_MAPACCELERATOR = 0x0400 + (Extension.IsUnicode ? 90
			: 78);

	public static final int TB_SETBITMAPSIZE = 0x420;

	public static final int TB_SETBUTTONINFO = Extension.IsUnicode ? 0x440
			: 0x442;

	public static final int TB_SETBUTTONSIZE = 0x41f;

	public static final int TB_SETDISABLEDIMAGELIST = 0x436;

	public static final int TB_SETEXTENDEDSTYLE = 0x454;

	public static final int TB_SETHOTIMAGELIST = 0x434;

	public static final int TB_SETHOTITEM = 0x0400 + 72;

	public static final int TB_SETIMAGELIST = 0x430;

	public static final int TB_SETPARENT = 0x400 + 37;

	public static final int TB_SETROWS = 0x427;

	public static final int TB_SETSTATE = 0x411;

	public static final int TB_THUMBPOSITION = 0x4;

	public static final int TBIF_COMMAND = 0x20;

	public static final int TBIF_IMAGE = 0x1;

	public static final int TBIF_LPARAM = 0x10;

	public static final int TBIF_SIZE = 0x40;

	public static final int TBIF_STATE = 0x4;

	public static final int TBIF_STYLE = 0x8;

	public static final int TBIF_TEXT = 0x2;

	public static final int TBM_GETLINESIZE = 0x418;

	public static final int TBM_GETPAGESIZE = 0x416;

	public static final int TBM_GETPOS = 0x400;

	public static final int TBM_GETRANGEMAX = 0x402;

	public static final int TBM_GETRANGEMIN = 0x401;

	public static final int TBM_GETTHUMBRECT = 0x419;

	public static final int TBM_SETLINESIZE = 0x417;

	public static final int TBM_SETPAGESIZE = 0x415;

	public static final int TBM_SETPOS = 0x405;

	public static final int TBM_SETRANGEMAX = 0x408;

	public static final int TBM_SETRANGEMIN = 0x407;

	public static final int TBM_SETTICFREQ = 0x414;

	public static final int TBN_DROPDOWN = 0xfffffd3a;

	public static final int TBN_FIRST = 0xfffffd44;

	public static final int TBN_HOTITEMCHANGE = 0xFFFFFD37;

	public static final int TBS_AUTOTICKS = 0x1;

	public static final int TBS_BOTH = 0x8;

	public static final int TBS_DOWNISLEFT = 0x0400;

	public static final int TBS_HORZ = 0x0;

	public static final int TBS_VERT = 0x2;

	public static final int TBSTATE_CHECKED = 0x1;

	public static final int TBSTATE_ENABLED = 0x4;

	public static final int TBSTATE_PRESSED = 0x02;

	public static final int TBSTYLE_AUTOSIZE = 0x10;

	public static final int TBSTYLE_CUSTOMERASE = 0x2000;

	public static final int TBSTYLE_DROPDOWN = 0x8;

	public static final int TBSTYLE_EX_DOUBLEBUFFER = 0x80;

	public static final int TBSTYLE_EX_DRAWDDARROWS = 0x1;

	public static final int TBSTYLE_EX_HIDECLIPPEDBUTTONS = 0x10;

	public static final int TBSTYLE_EX_MIXEDBUTTONS = 0x8;

	public static final int TBSTYLE_FLAT = 0x800;

	public static final int TBSTYLE_LIST = 0x1000;

	public static final int TBSTYLE_TOOLTIPS = 0x100;

	public static final int TBSTYLE_TRANSPARENT = 0x8000;

	public static final int TBSTYLE_WRAPABLE = 0x200;

	public static final int TCI_SRCCHARSET = 0x1;

	public static final int TCI_SRCCODEPAGE = 0x2;

	public static final int TCIF_IMAGE = 0x2;

	public static final int TCIF_TEXT = 0x1;

	public static final int TCM_ADJUSTRECT = 0x1328;

	public static final int TCM_DELETEITEM = 0x1308;

	public static final int TCM_GETCURSEL = 0x130b;

	public static final int TCM_GETITEMCOUNT = 0x1304;

	public static final int TCM_GETITEMRECT = 0x130a;

	public static final int TCM_GETTOOLTIPS = 0x132d;

	public static final int TCM_INSERTITEM = Extension.IsUnicode ? 0x133e
			: 0x1307;

	public static final int TCM_SETCURSEL = 0x130c;

	public static final int TCM_SETIMAGELIST = 0x1303;

	public static final int TCM_SETITEM = Extension.IsUnicode ? 0x133d : 0x1306;

	public static final int TCN_SELCHANGE = 0xfffffdd9;

	public static final int TCN_SELCHANGING = 0xfffffdd8;

	public static final int TCS_BOTTOM = 0x0002;

	public static final int TCS_FOCUSNEVER = 0x8000;

	public static final int TCS_MULTILINE = 0x200;

	public static final int TCS_TABS = 0x0;

	public static final int TCS_TOOLTIPS = 0x4000;

	public static final int TECHNOLOGY = 0x2;

	public static final int TF_ATTR_CONVERTED = 2;

	public static final int TF_ATTR_FIXEDCONVERTED = 5;

	public static final int TF_ATTR_INPUT = 0;

	public static final int TF_ATTR_INPUT_ERROR = 4;

	public static final int TF_ATTR_OTHER = -1;

	public static final int TF_ATTR_TARGET_CONVERTED = 1;

	public static final int TF_ATTR_TARGET_NOTCONVERTED = 3;

	public static final int TF_CT_COLORREF = 2;

	public static final int TF_CT_NONE = 0;

	public static final int TF_CT_SYSCOLOR = 1;

	public static final int TF_LS_DASH = 3;

	public static final int TF_LS_DOT = 2;

	public static final int TF_LS_NONE = 0;

	public static final int TF_LS_SOLID = 1;

	public static final int TF_LS_SQUIGGLE = 4;

	public static final int TIME_NOSECONDS = 0x2;

	public static final int TIS_DISABLED = 4;

	public static final int TIS_FOCUSED = 5;

	public static final int TIS_HOT = 2;

	public static final int TIS_NORMAL = 1;

	public static final int TIS_SELECTED = 3;

	public static final int TKP_THUMB = 3;

	public static final int TKP_THUMBBOTTOM = 4;

	public static final int TKP_THUMBLEFT = 7;

	public static final int TKP_THUMBRIGHT = 8;

	public static final int TKP_THUMBTOP = 5;

	public static final int TKP_THUMBVERT = 6;

	public static final int TKP_TICS = 9;

	public static final int TKP_TICSVERT = 10;

	public static final int TKP_TRACK = 1;

	public static final int TKP_TRACKVERT = 2;

	public static final int TME_HOVER = 0x1;

	public static final int TME_LEAVE = 0x2;

	public static final int TME_QUERY = 0x40000000;

	public static final int TMPF_VECTOR = 0x2;

	public static final int TMT_CONTENTMARGINS = 3602;

	public static final String TOOLBARCLASSNAME = "ToolbarWindow32"; //$NON-NLS-1$

	public static final String TOOLTIPS_CLASS = "tooltips_class32"; //$NON-NLS-1$

	public static final int TP_BUTTON = 1;

	public static final int TP_DROPDOWNBUTTON = 2;

	public static final int TP_SEPARATOR = 5;

	public static final int TP_SEPARATORVERT = 6;

	public static final int TP_SPLITBUTTON = 3;

	public static final int TP_SPLITBUTTONDROPDOWN = 4;

	public static final int TPM_LEFTBUTTON = 0x0000;

	public static final int TPM_RIGHTBUTTON = 0x0002;

	public static final int TPM_LEFTALIGN = 0x0000;

	public static final int TPM_CENTERALIGN = 0x0004;

	public static final int TPM_RIGHTALIGN = 0x0008;

	public static final int TPM_TOPALIGN = 0x0000;

	public static final int TPM_VCENTERALIGN = 0x0010;

	public static final int TPM_BOTTOMALIGN = 0x0020;

	public static final int TPM_HORIZONTAL = 0x0000;

	public static final int TPM_VERTICAL = 0x0040;

	public static final int TPM_NONOTIFY = 0x0080;

	public static final int TPM_RETURNCMD = 0x0100;

	public static final int TPM_RECURSE = 0x0001;

	public static final String TRACKBAR_CLASS = "msctls_trackbar32"; //$NON-NLS-1$

	public static final int TRANSPARENT = 0x1;

	public static final String TRAY_NOTIFY = "TrayNotifyWnd";

	public static final String TRAY_SYSPAGER = "SysPager";

	public static final String TRAY_TOOLBAR = "ToolbarWindow32";

	public static final int TREIS_DISABLED = 4;

	public static final int TREIS_HOT = 2;

	public static final int TREIS_NORMAL = 1;

	public static final int TREIS_SELECTED = 3;

	public static final int TREIS_SELECTEDNOTFOCUS = 5;

	public static final int TS_CHECKED = 5;

	public static final int TS_DISABLED = 4;

	public static final int TS_DRAW = 2;

	public static final int TS_HOT = 2;

	public static final int TS_HOTCHECKED = 6;

	public static final int TS_MIN = 0;

	public static final int TS_NORMAL = 1;

	public static final int TS_PRESSED = 3;

	public static final int TS_TRUE = 1;

	public static final int TTDT_AUTOMATIC = 0;

	public static final int TTDT_AUTOPOP = 2;

	public static final int TTDT_INITIAL = 3;

	public static final int TTDT_RESHOW = 1;

	public static final int TTF_ABSOLUTE = 0x80;

	public static final int TTF_IDISHWND = 0x1;

	public static final int TTF_RTLREADING = 0x4;

	public static final int TTF_SUBCLASS = 0x10;

	public static final int TTF_TRACK = 0x20;

	public static final int TTF_TRANSPARENT = 0x100;

	public static final int TTI_ERROR = 3;

	public static final int TTI_INFO = 1;

	public static final int TTI_NONE = 0;

	public static final int TTI_WARNING = 2;

	public static final int TTM_ACTIVATE = 0x400 + 1;

	public static final int TTM_ADDTOOL = Extension.IsUnicode ? 0x432 : 0x404;

	public static final int TTM_ADJUSTRECT = 0x400 + 31;

	public static final int TTM_DELTOOL = Extension.IsUnicode ? 0x433 : 0x405;

	public static final int TTM_GETCURRENTTOOL = 0x400 + (Extension.IsUnicode ? 59
			: 15);

	public static final int TTM_GETCURRENTTOOLA = 0x400 + 15;

	public static final int TTM_GETCURRENTTOOLW = 0x400 + 59;

	public static final int TTM_GETDELAYTIME = 0x400 + 21;

	public static final int TTM_GETTOOLINFO = 0x400 + (Extension.IsUnicode ? 53
			: 8);

	public static final int TTM_NEWTOOLRECT = 0x400 + (Extension.IsUnicode ? 52
			: 6);

	public static final int TTM_POP = 0x400 + 28;

	public static final int TTM_SETDELAYTIME = 0x400 + 3;

	public static final int TTM_SETMAXTIPWIDTH = 0x418;

	public static final int TTM_SETTITLE = 0x400 + (Extension.IsUnicode ? 33
			: 32);

	public static final int TTM_SETTITLEA = 0x400 + 32;

	public static final int TTM_SETTITLEW = 0x400 + 33;

	public static final int TTM_TRACKACTIVATE = 1041;

	public static final int TTM_TRACKPOSITION = 1042;

	public static final int TTM_UPDATE = 0x41D;

	public static final int TTN_FIRST = 0xfffffdf8;

	public static final int TTN_GETDISPINFO = Extension.IsUnicode ? 0xfffffdee
			: 0xfffffdf8;

	public static final int TTN_GETDISPINFOA = 0xfffffdf8;

	public static final int TTN_GETDISPINFOW = 0xfffffdee;

	public static final int TTN_POP = TTN_FIRST - 2;

	public static final int TTN_SHOW = TTN_FIRST - 1;

	public static final int TTS_ALWAYSTIP = 0x1;

	public static final int TTS_BALLOON = 0x40;

	public static final int TTS_NOANIMATE = 0x10;

	public static final int TTS_NOFADE = 0x20;

	public static final int TTS_NOPREFIX = 0x02;

	public static final int TV_FIRST = 0x1100;

	public static final int TVE_COLLAPSE = 0x1;

	public static final int TVE_COLLAPSERESET = 0x8000;

	public static final int TVE_EXPAND = 0x2;

	public static final int TVGN_CARET = 0x9;

	public static final int TVGN_CHILD = 0x4;

	public static final int TVGN_DROPHILITED = 0x8;

	public static final int TVGN_FIRSTVISIBLE = 0x5;

	public static final int TVGN_LASTVISIBLE = 0xa;

	public static final int TVGN_NEXT = 0x1;

	public static final int TVGN_NEXTVISIBLE = 0x6;

	public static final int TVGN_PARENT = 0x3;

	public static final int TVGN_PREVIOUS = 0x2;

	public static final int TVGN_PREVIOUSVISIBLE = 0x7;

	public static final int TVGN_ROOT = 0x0;

	public static final int TVHT_ONITEM = 0x46;

	public static final int TVHT_ONITEMBUTTON = 16;

	public static final int TVHT_ONITEMICON = 0x2;

	public static final int TVHT_ONITEMINDENT = 0x8;

	public static final int TVHT_ONITEMLABEL = 0x4;

	public static final int TVHT_ONITEMRIGHT = 0x20;

	public static final int TVHT_ONITEMSTATEICON = 0x40;

	public static final int /* long */TVI_FIRST = -0x0FFFF;

	public static final int /* long */TVI_LAST = -0x0FFFE;

	public static final int /* long */TVI_ROOT = -0x10000;

	public static final int /* long */TVI_SORT = -0x0FFFD;

	public static final int TVIF_HANDLE = 0x10;

	public static final int TVIF_IMAGE = 0x2;

	public static final int TVIF_INTEGRAL = 0x0080;

	public static final int TVIF_PARAM = 0x4;

	public static final int TVIF_SELECTEDIMAGE = 0x20;

	public static final int TVIF_STATE = 0x8;

	public static final int TVIF_TEXT = 0x1;

	public static final int TVIS_DROPHILITED = 0x8;

	public static final int TVIS_EXPANDED = 0x20;

	public static final int TVIS_SELECTED = 0x2;

	public static final int TVIS_STATEIMAGEMASK = 0xf000;

	public static final int TVM_CREATEDRAGIMAGE = TV_FIRST + 18;

	public static final int TVM_DELETEITEM = 0x1101;

	public static final int TVM_ENSUREVISIBLE = 0x1114;

	public static final int TVM_EXPAND = 0x1102;

	public static final int TVM_GETBKCOLOR = 0x111f;

	public static final int TVM_GETCOUNT = 0x1105;

	public static final int TVM_GETEXTENDEDSTYLE = TV_FIRST + 45;

	public static final int TVM_GETIMAGELIST = 0x1108;

	public static final int TVM_GETITEM = Extension.IsUnicode ? 0x113e : 0x110c;

	public static final int TVM_GETITEMHEIGHT = 0x111c;

	public static final int TVM_GETITEMRECT = 0x1104;

	public static final int TVM_GETITEMSTATE = TV_FIRST + 39;

	public static final int TVM_GETNEXTITEM = 0x110a;

	public static final int TVM_GETTEXTCOLOR = 0x1120;

	public static final int TVM_GETTOOLTIPS = TV_FIRST + 25;

	public static final int TVM_GETVISIBLECOUNT = TV_FIRST + 16;

	public static final int TVM_HITTEST = 0x1111;

	public static final int TVM_INSERTITEM = Extension.IsUnicode ? 0x1132
			: 0x1100;

	public static final int TVM_MAPACCIDTOHTREEITEM = TV_FIRST + 42;

	public static final int TVM_MAPHTREEITEMTOACCID = TV_FIRST + 43;

	public static final int TVM_SELECTITEM = 0x110b;

	public static final int TVM_SETBKCOLOR = 0x111d;

	public static final int TVM_SETEXTENDEDSTYLE = TV_FIRST + 44;

	public static final int TVM_SETIMAGELIST = 0x1109;

	public static final int TVM_SETINSERTMARK = 0x111a;

	public static final int TVM_SETITEM = Extension.IsUnicode ? 0x113f : 0x110d;

	public static final int TVM_SETITEMHEIGHT = TV_FIRST + 27;

	public static final int TVM_SETSCROLLTIME = TV_FIRST + 33;

	public static final int TVM_SETTEXTCOLOR = 0x111e;

	public static final int TVM_SORTCHILDREN = TV_FIRST + 19;

	public static final int TVM_SORTCHILDRENCB = TV_FIRST + 21;

	public static final int TVN_BEGINDRAGA = 0xfffffe69;

	public static final int TVN_BEGINDRAGW = 0xfffffe38;

	public static final int TVN_BEGINRDRAGA = 0xfffffe68;

	public static final int TVN_BEGINRDRAGW = 0xfffffe37;

	public static final int TVN_FIRST = 0xfffffe70;

	public static final int TVN_GETDISPINFOA = TVN_FIRST - 3;

	public static final int TVN_GETDISPINFOW = TVN_FIRST - 52;

	public static final int TVN_ITEMCHANGINGA = TVN_FIRST - 16;

	public static final int TVN_ITEMCHANGINGW = TVN_FIRST - 17;

	public static final int TVN_ITEMEXPANDEDA = TVN_FIRST - 6;

	public static final int TVN_ITEMEXPANDEDW = TVN_FIRST - 55;

	public static final int TVN_ITEMEXPANDINGA = 0xfffffe6b;

	public static final int TVN_ITEMEXPANDINGW = 0xfffffe3a;

	public static final int TVN_SELCHANGEDA = 0xfffffe6e;

	public static final int TVN_SELCHANGEDW = 0xfffffe3d;

	public static final int TVN_SELCHANGINGA = 0xfffffe6f;

	public static final int TVN_SELCHANGINGW = 0xfffffe3e;

	public static final int TVP_GLYPH = 2;

	public static final int TVP_TREEITEM = 1;

	public static final int TVS_DISABLEDRAGDROP = 0x10;

	public static final int TVS_EX_AUTOHSCROLL = 0x0020;

	public static final int TVS_EX_DIMMEDCHECKBOXES = 0x0200;

	public static final int TVS_EX_DOUBLEBUFFER = 0x0004;

	public static final int TVS_EX_DRAWIMAGEASYNC = 0x0400;

	public static final int TVS_EX_EXCLUSIONCHECKBOXES = 0x0100;

	public static final int TVS_EX_FADEINOUTEXPANDOS = 0x0040;

	public static final int TVS_EX_MULTISELECT = 0x0002;

	public static final int TVS_EX_NOINDENTSTATE = 0x0008;

	public static final int TVS_EX_PARTIALCHECKBOXES = 0x0080;

	public static final int TVS_EX_RICHTOOLTIP = 0x0010;

	public static final int TVS_FULLROWSELECT = 0x1000;

	public static final int TVS_HASBUTTONS = 0x1;

	public static final int TVS_HASLINES = 0x2;

	public static final int TVS_LINESATROOT = 0x4;

	public static final int TVS_NOHSCROLL = 0x8000;

	public static final int TVS_NONEVENHEIGHT = 0x4000;

	public static final int TVS_NOTOOLTIPS = 0x80;

	public static final int TVS_SHOWSELALWAYS = 0x20;

	public static final int TVS_TRACKSELECT = 0x200;

	public static final int TVSIL_NORMAL = 0x0;

	public static final int TVSIL_STATE = 0x2;

	public static final int UDM_GETACCEL = 0x046C;

	public static final int UDM_GETPOS = 0x468;

	public static final int UDM_GETPOS32 = 0x0472;

	public static final int UDM_GETRANGE32 = 0x0470;

	public static final int UDM_SETACCEL = 0x046B;

	public static final int UDM_SETPOS = 0x467;

	public static final int UDM_SETPOS32 = 0x0471;

	public static final int UDM_SETRANGE32 = 0x046f;

	public static final int UDN_DELTAPOS = -722;

	public static final int UDS_ALIGNLEFT = 0x008;

	public static final int UDS_ALIGNRIGHT = 0x004;

	public static final int UDS_AUTOBUDDY = 0x0010;

	public static final int UDS_WRAP = 0x0001;

	public static final int UIS_CLEAR = 2;

	public static final int UIS_INITIALIZE = 3;

	public static final int UIS_SET = 1;

	public static final int UISF_HIDEACCEL = 0x2;

	public static final int UISF_HIDEFOCUS = 0x1;

	public static final String UPDOWN_CLASS = "msctls_updown32"; //$NON-NLS-1$

	public static final int USP_E_SCRIPT_NOT_IN_FONT = 0x80040200;

	/**
	 * Windows CE.
	 */
	public static final int VER_PLATFORM_WIN32_CE = 3;

	/**
	 * Windows NT, Windows 2000, Windows XP, or Windows .NET Server 2003 family.
	 */
	public static final int VER_PLATFORM_WIN32_NT = 2;

	/**
	 * Windows 95, Windows 98, or Windows Me.
	 */
	public static final int VER_PLATFORM_WIN32_WINDOWS = 1;

	/**
	 * Win32s on Windows 3.1.
	 */
	public static final int VER_PLATFORM_WIN32s = 0;

	public static final int VERTRES = 0xa;

	public static final int VK_ADD = 0x6B;

	public static final int VK_APP1 = 0xc1;

	public static final int VK_APP2 = 0xc2;

	public static final int VK_APP3 = 0xc3;

	public static final int VK_APP4 = 0xc4;

	public static final int VK_APP5 = 0xc5;

	public static final int VK_APP6 = 0xc6;

	public static final int VK_BACK = 0x8;

	public static final int VK_CANCEL = 0x3;

	public static final int VK_CAPITAL = 0x14;

	public static final int VK_CONTROL = 0x11;

	public static final int VK_DECIMAL = 0x6E;

	public static final int VK_DELETE = 0x2e;

	public static final int VK_DIVIDE = 0x6f;

	public static final int VK_DOWN = 0x28;

	public static final int VK_END = 0x23;

	public static final int VK_ESCAPE = 0x1b;

	public static final int VK_F1 = 0x70;

	public static final int VK_F10 = 0x79;

	public static final int VK_F11 = 0x7a;

	public static final int VK_F12 = 0x7b;

	public static final int VK_F13 = 0x7c;

	public static final int VK_F14 = 0x7d;

	public static final int VK_F15 = 0x7e;

	public static final int VK_F2 = 0x71;

	public static final int VK_F3 = 0x72;

	public static final int VK_F4 = 0x73;

	public static final int VK_F5 = 0x74;

	public static final int VK_F6 = 0x75;

	public static final int VK_F7 = 0x76;

	public static final int VK_F8 = 0x77;

	public static final int VK_F9 = 0x78;

	public static final int VK_HOME = 0x24;

	public static final int VK_INSERT = 0x2d;

	public static final int VK_LBUTTON = 0x1;

	public static final int VK_LEFT = 0x25;

	public static final int VK_MBUTTON = 0x4;

	public static final int VK_MENU = 0x12;

	public static final int VK_MULTIPLY = 0x6A;

	public static final int VK_N = 0x4e;

	public static final int VK_NEXT = 0x22;

	public static final int VK_NUMLOCK = 0x90;

	public static final int VK_NUMPAD0 = 0x60;

	public static final int VK_NUMPAD1 = 0x61;

	public static final int VK_NUMPAD2 = 0x62;

	public static final int VK_NUMPAD3 = 0x63;

	public static final int VK_NUMPAD4 = 0x64;

	public static final int VK_NUMPAD5 = 0x65;

	public static final int VK_NUMPAD6 = 0x66;

	public static final int VK_NUMPAD7 = 0x67;

	public static final int VK_NUMPAD8 = 0x68;

	public static final int VK_NUMPAD9 = 0x69;

	public static final int VK_O = 0x4f;

	public static final int VK_PAUSE = 0x13;

	public static final int VK_PRIOR = 0x21;

	public static final int VK_RBUTTON = 0x2;

	public static final int VK_RETURN = 0xd;

	public static final int VK_RIGHT = 0x27;

	public static final int VK_SCROLL = 0x91;

	public static final int VK_SEPARATOR = 0x6C;

	public static final int VK_SHIFT = 0x10;

	public static final int VK_SNAPSHOT = 0x2C;

	public static final int VK_SPACE = 0x20;

	public static final int VK_SUBTRACT = 0x6D;

	public static final int VK_TAB = 0x9;

	public static final int VK_UP = 0x26;

	public static final int VK_XBUTTON1 = 0x05;

	public static final int VK_XBUTTON2 = 0x06;
	
	public static final int VK_LWIN = 0x5B;
	
	public static final int VK_RWIN = 0x5C;

	public static final String WC_HEADER = "SysHeader32"; //$NON-NLS-1$

	public static final String WC_LINK = "SysLink"; //$NON-NLS-1$

	public static final String WC_LISTVIEW = "SysListView32"; //$NON-NLS-1$

	public static final String WC_TABCONTROL = "SysTabControl32"; //$NON-NLS-1$

	public static final String WC_TREEVIEW = "SysTreeView32"; //$NON-NLS-1$

	public static final int WH_CALLWNDPROC = 4;

	public static final int WH_CALLWNDPROCRET = 12;

	public static final int WH_CBT = 5;

	public static final int WH_DEBUG = 9;

	public static final int WH_FOREGROUNDIDLE = 11;

	public static final int WH_GETMESSAGE = 0x3;

	public static final int WH_HARDWARE = 8;

	public static final int WH_JOURNALPLAYBACK = 1;

	public static final int WH_JOURNALRECORD = 0;

	public static final int WH_KEYBOARD = 2;

	public static final int WH_KEYBOARD_LL = 13;

	public static final int WH_MOUSE = 7;

	public static final int WH_MOUSE_LL = 14;

	public static final int WH_MSGFILTER = 0xFFFFFFFF;

	public static final int WH_SHELL = 10;

	public static final int WH_SYSMSGFILTER = 6;

	public static final int WHEEL_DELTA = 120;

	public static final int WHEEL_PAGESCROLL = 0xFFFFFFFF;

	public static final int WHITE_BRUSH = 0;

	public static final int WINDING = 2;

	public static final int WM_ACTIVATE = 0x0006;

	public static final int WM_ACTIVATEAPP = 0x1c;

	public static final int WM_AFXFIRST = 0x0360;

	public static final int WM_AFXLAST = 0x037F;

	public static final int WM_APP = 0x8000;

	public static final int WM_ASKCBFORMATNAME = 0x030C;

	public static final int WM_CANCELJOURNAL = 0x004B;

	public static final int WM_CANCELMODE = 0x001F;

	public static final int WM_CAPTURECHANGED = 0x0215;

	public static final int WM_CHANGECBCHAIN = 0x030D;

	public static final int WM_CHANGEUISTATE = 0x0127;

	public static final int WM_CHAR = 0x102;

	public static final int WM_CHARTOITEM = 0x002F;

	public static final int WM_CHILDACTIVATE = 0x0022;

	public static final int WM_CLEAR = 0x0303;

	public static final int WM_CLOSE = 0x10;

	public static final int WM_COALESCE_FIRST = 0x0390;

	public static final int WM_COALESCE_LAST = 0x039F;

	public static final int WM_COMMAND = 0x111;

	public static final int WM_COMPACTING = 0x0041;

	public static final int WM_COMPAREITEM = 0x0039;

	public static final int WM_CONTEXTMENU = 0x007B;

	public static final int WM_COPY = 0x301;

	public static final int WM_COPYDATA = 0x004A;

	public static final int WM_CREATE = 0x0001;

	public static final int WM_CTLCOLOR = 0x0019;

	public static final int WM_CTLCOLORBTN = 0x0135;

	public static final int WM_CTLCOLORDLG = 0x136;

	public static final int WM_CTLCOLOREDIT = 0x0133;

	public static final int WM_CTLCOLORLISTBOX = 0x134;

	public static final int WM_CTLCOLORMSGBOX = 0x0132;

	public static final int WM_CTLCOLORSCROLLBAR = 0x137;

	public static final int WM_CTLCOLORSTATIC = 0x0138;

	public static final int WM_CUT = 0x300;

	public static final int WM_DDE_ACK = 0x03E4;

	public static final int WM_DDE_ADVISE = 0x03E2;

	public static final int WM_DDE_DATA = 0x03E5;

	public static final int WM_DDE_EXECUTE = 0x03E8;

	public static final int WM_DDE_FIRST = 0x03E0;

	public static final int WM_DDE_INITIATE = 0x03E0;

	public static final int WM_DDE_LAST = 0x03E8;

	public static final int WM_DDE_POKE = 0x03E7;

	public static final int WM_DDE_REQUEST = 0x03E6;

	public static final int WM_DDE_TERMINATE = 0x03E1;

	public static final int WM_DDE_UNADVISE = 0x03E3;

	public static final int WM_DEADCHAR = 0x0103;

	public static final int WM_DELETEITEM = 0x002D;

	public static final int WM_DESTROY = 0x0002;

	public static final int WM_DESTROYCLIPBOARD = 0x0307;

	public static final int WM_DEVICECHANGE = 0x0219;

	public static final int WM_DEVMODECHANGE = 0x001B;

	public static final int WM_DISPLAYCHANGE = 0x007E;

	public static final int WM_DRAWCLIPBOARD = 0x0308;

	public static final int WM_DRAWITEM = 0x002B;

	public static final int WM_DROPFILES = 0x0233;

	public static final int WM_ENABLE = 0x000A;

	public static final int WM_ENDSESSION = 0x0016;

	public static final int WM_ENTERIDLE = 0x121;

	public static final int WM_ENTERMENULOOP = 0x0211;

	public static final int WM_ENTERSIZEMOVE = 0x0231;

	public static final int WM_ERASEBKGND = 0x0014;

	public static final int WM_EXITMENULOOP = 0x0212;

	public static final int WM_EXITSIZEMOVE = 0x0232;

	public static final int WM_FONTCHANGE = 0x001D;

	public static final int WM_GETDLGCODE = 0x0087;

	public static final int WM_GETFONT = 0x31;

	public static final int WM_GETHOTKEY = 0x0033;

	public static final int WM_GETICON = 0x007F;

	public static final int WM_GETMINMAXINFO = 0x0024;

	public static final int WM_GETOBJECT = 0x003D;

	public static final int WM_GETTEXT = 0x000D;

	public static final int WM_GETTEXTLENGTH = 0x000E;

	public static final int WM_HANDHELDFIRST = 0x0358;

	public static final int WM_HANDHELDLAST = 0x035F;

	public static final int WM_HELP = 0x0053;

	public static final int WM_HOTKEY = 0x0312;

	public static final int WM_HSCROLL = 0x0114;

	public static final int WM_HSCROLLCLIPBOARD = 0x030E;

	public static final int WM_ICONERASEBKGND = 0x0027;

	public static final int WM_IME_CHAR = 0x0286;

	public static final int WM_IME_COMPOSITION = 0x10f;

	public static final int WM_IME_COMPOSITION_START = 0x010D;

	public static final int WM_IME_COMPOSITIONFULL = 0x0284;

	public static final int WM_IME_CONTROL = 0x0283;

	public static final int WM_IME_ENDCOMPOSITION = 0x010E;

	public static final int WM_IME_KEYDOWN = 0x0290;

	public static final int WM_IME_KEYLAST = 0x010F;

	public static final int WM_IME_KEYUP = 0x0291;

	public static final int WM_IME_NOTIFY = 0x0282;

	public static final int WM_IME_REQUEST = 0x0288;

	public static final int WM_IME_SELECT = 0x0285;

	public static final int WM_IME_SETCONTEXT = 0x0281;

	public static final int WM_IME_STARTCOMPOSITION = 0x010D;

	public static final int WM_INITDIALOG = 0x0110;

	public static final int WM_INITMENU = 0x0116;

	public static final int WM_INITMENUPOPUP = 0x0117;

	public static final int WM_INPUTLANGCHANGE = 0x51;

	public static final int WM_INPUTLANGCHANGEREQUEST = 0x0050;

	public static final int WM_KEYDOWN = 0x0100;

	public static final int WM_KEYFIRST = 0x100;

	public static final int WM_KEYLAST = 0x0108;

	public static final int WM_KEYUP = 0x101;

	public static final int WM_KILLFOCUS = 0x0008;

	public static final int WM_LBUTTONDBLCLK = 0x203;

	public static final int WM_LBUTTONDOWN = 0x0201;

	public static final int WM_LBUTTONUP = 0x202;

	public static final int WM_MBUTTONDBLCLK = 0x0209;

	public static final int WM_MBUTTONDOWN = 0x207;

	public static final int WM_MBUTTONUP = 0x0208;

	public static final int WM_MDIACTIVATE = 0x0222;

	public static final int WM_MDICASCADE = 0x0227;

	public static final int WM_MDICREATE = 0x0220;

	public static final int WM_MDIDESTROY = 0x0221;

	public static final int WM_MDIGETACTIVE = 0x0229;

	public static final int WM_MDIICONARRANGE = 0x0228;

	public static final int WM_MDIMAXIMIZE = 0x0225;

	public static final int WM_MDINEXT = 0x0224;

	public static final int WM_MDIREFRESHMENU = 0x0234;

	public static final int WM_MDIRESTORE = 0x0223;

	public static final int WM_MDISETMENU = 0x0230;

	public static final int WM_MDITILE = 0x0226;

	public static final int WM_MEASUREITEM = 0x002C;

	public static final int WM_MENUCHAR = 0x120;

	public static final int WM_MENUCOMMAND = 0x0126;

	public static final int WM_MENUDRAG = 0x0123;

	public static final int WM_MENUGETOBJECT = 0x0124;

	public static final int WM_MENURBUTTONUP = 0x0122;

	public static final int WM_MENUSELECT = 0x011F;

	public static final int WM_MOUSEACTIVATE = 0x21;

	public static final int WM_MOUSEFIRST = 0x0200;

	public static final int WM_MOUSEHOVER = 0x2a1;

	public static final int WM_MOUSELAST = 0x20d;

	public static final int WM_MOUSELEAVE = 0x02A3;

	public static final int WM_MOUSEMOVE = 0x200;

	public static final int WM_MOUSEWHEEL = 0x020A;

	public static final int WM_MOVE = 0x3;

	public static final int WM_MOVING = 0x0216;

	public static final int WM_NCACTIVATE = 0x0086;

	public static final int WM_NCCALCSIZE = 0x83;

	public static final int WM_NCCREATE = 0x0081;

	public static final int WM_NCDESTROY = 0x0082;

	public static final int WM_NCHITTEST = 0x0084;

	public static final int WM_NCLBUTTONDBLCLK = 0x00A3;

	public static final int WM_NCLBUTTONDOWN = 0x00A1;

	public static final int WM_NCLBUTTONUP = 0x00A2;

	public static final int WM_NCMBUTTONDBLCLK = 0x00A9;

	public static final int WM_NCMBUTTONDOWN = 0x00A7;

	public static final int WM_NCMBUTTONUP = 0x00A8;

	public static final int WM_NCMOUSEMOVE = 0x00A0;

	public static final int WM_NCPAINT = 0x0085;

	public static final int WM_NCRBUTTONDBLCLK = 0x00A6;

	public static final int WM_NCRBUTTONDOWN = 0x00A4;

	public static final int WM_NCRBUTTONUP = 0x00A5;

	public static final int WM_NEXTDLGCTL = 0x0028;

	public static final int WM_NEXTMENU = 0x0213;

	public static final int WM_NOTIFY = 0x004E;

	public static final int WM_NOTIFYFORMAT = 0x0055;

	public static final int WM_NULL = 0x0000;

	public static final int WM_PAINT = 0x000F;

	public static final int WM_PAINTCLIPBOARD = 0x0309;

	public static final int WM_PAINTICON = 0x0026;

	public static final int WM_PALETTECHANGED = 0x0311;

	public static final int WM_PALETTEISCHANGING = 0x0310;

	public static final int WM_PARENTNOTIFY = 0x0210;

	public static final int WM_PASTE = 0x302;

	public static final int WM_PENWINFIRST = 0x0380;

	public static final int WM_PENWINLAST = 0x038F;

	public static final int WM_POWER = 0x0048;

	public static final int WM_POWERBROADCAST = 536;

	public static final int WM_PRINT = 0x0317;

	public static final int WM_PRINTCLIENT = 0x0318;

	public static final int WM_QUERYDRAGICON = 0x0037;

	public static final int WM_QUERYENDSESSION = 0x0011;

	public static final int WM_QUERYNEWPALETTE = 0x30f;

	public static final int WM_QUERYOPEN = 0x0013;

	public static final int WM_QUERYUISTATE = 0x129;

	public static final int WM_QUEUESYNC = 0x0023;

	public static final int WM_QUIT = 0x0012;

	public static final int WM_RBUTTONDBLCLK = 0x0206;

	public static final int WM_RBUTTONDOWN = 0x204;

	public static final int WM_RBUTTONUP = 0x0205;

	public static final int WM_RENDERALLFORMATS = 0x0306;

	public static final int WM_RENDERFORMAT = 0x0305;

	public static final int WM_SETCURSOR = 0x0020;

	public static final int WM_SETFOCUS = 0x7;

	public static final int WM_SETFONT = 0x0030;

	public static final int WM_SETHOTKEY = 0x0032;

	public static final int WM_SETICON = 0x0080;

	public static final int WM_SETREDRAW = 0xb;

	public static final int WM_SETTEXT = 0x000C;

	public static final int WM_SETTINGCHANGE = 0x1A;

	public static final int WM_SHOWWINDOW = 0x0018;

	public static final int WM_SIZE = 0x5;

	public static final int WM_SIZECLIPBOARD = 0x030B;

	public static final int WM_SIZING = 0x0214;

	public static final int WM_SPOOLERSTATUS = 0x002A;

	public static final int WM_STYLECHANGED = 0x007D;

	public static final int WM_STYLECHANGING = 0x007C;

	public static final int WM_SYNCPAINT = 0x0088;

	public static final int WM_SYSCHAR = 0x0106;

	public static final int WM_SYSCOLORCHANGE = 0x15;

	public static final int WM_SYSCOMMAND = 0x0112;

	public static final int WM_SYSDEADCHAR = 0x0107;

	public static final int WM_SYSKEYDOWN = 0x0104;

	public static final int WM_SYSKEYUP = 0x105;

	public static final int WM_SYSTEMERROR = 0x0017;

	public static final int WM_TCARD = 0x0052;

	public static final int WM_THEMECHANGED = 0x031a;

	public static final int WM_TIMECHANGE = 0x001E;

	public static final int WM_TIMER = 0x0113;

	public static final int WM_UNDO = 0x304;

	public static final int WM_UNINITMENUPOPUP = 0x0125;

	public static final int WM_UPDATEUISTATE = 0x0128;

	public static final int WM_USER = 0x0400;

	public static final int WM_USERCHANGED = 0x0054;

	public static final int WM_VKEYTOITEM = 0x002E;

	public static final int WM_VSCROLL = 0x0115;

	public static final int WM_VSCROLLCLIPBOARD = 0x030A;

	public static final int WM_WINDOWPOSCHANGED = 0x0047;

	public static final int WM_WINDOWPOSCHANGING = 0x46;

	public static final int WM_WININICHANGE = 0x001A;

	public static final int WM_XBUTTONDBLCLK = 0x020D;

	public static final int WM_XBUTTONDOWN = 0x020B;

	public static final int WM_XBUTTONUP = 0x020C;

	/**
	 * Wallpaper display center style (value is 0).
	 * 
	 * @see ShellSystem#setWallPaper
	 */
	public static final int WPSTYLE_CENTER = 0;

	/**
	 * Wallpaper display max style (value is 3).
	 * 
	 * @see ShellSystem#setWallPaper
	 */
	public static final int WPSTYLE_MAX = 3;

	/**
	 * Wallpaper display stretch style (value is 2).
	 * 
	 * @see ShellSystem#setWallPaper
	 */
	public static final int WPSTYLE_STRETCH = 2;

	/**
	 * Wallpaper display tile style (value is 1).
	 * 
	 * @see ShellSystem#setWallPaper
	 */
	public static final int WPSTYLE_TILE = 1;

	public static final int WS_BORDER = 0x800000;

	public static final int WS_CAPTION = 0xc00000;

	public static final int WS_CHILD = 0x40000000;

	public static final int WS_CLIPCHILDREN = 0x2000000;

	public static final int WS_CLIPSIBLINGS = 0x4000000;

	public static final int WS_DISABLED = 0x4000000;

	public static final int WS_DLGFRAME = 0x400000;

	public static final int WS_EX_ACCEPTFILES = 0x10;

	public static final int WS_EX_APPWINDOW = 0x40000;

	public static final int WS_EX_CAPTIONOKBTN = 0x80000000;

	public static final int WS_EX_CLIENTEDGE = 0x200;

	public static final int WS_EX_COMPOSITED = 0x2000000;

	public static final int WS_EX_CONTEXTHELP = 0x400;

	public static final int WS_EX_CONTROLPARENT = 0x10000;

	public static final int WS_EX_DLGMODALFRAME = 0x1;

	public static final int WS_EX_LAYERED = 0x00080000;

	public static final int WS_EX_LAYOUTRTL = 0x00400000;

	public static final int WS_EX_LEFT = 0x0;

	public static final int WS_EX_LEFTSCROLLBAR = 0x4000;

	public static final int WS_EX_LTRREADING = 0x0;

	public static final int WS_EX_MDICHILD = 0x40;

	public static final int WS_EX_NOACTIVATE = 0x08000000;

	public static final int WS_EX_NOINHERITLAYOUT = 0x00100000;

	public static final int WS_EX_NOPARENTNOTIFY = 0x4;

	public static final int WS_EX_OVERLAPPEDWINDOW = 0x300;

	public static final int WS_EX_PALETTEWINDOW = 0x188;

	public static final int WS_EX_RIGHT = 0x1000;

	public static final int WS_EX_RIGHTSCROLLBAR = 0x0;

	public static final int WS_EX_RTLREADING = 0x2000;

	public static final int WS_EX_STATICEDGE = 0x20000;

	public static final int WS_EX_TOOLWINDOW = 0x80;

	public static final int WS_EX_TOPMOST = 0x8;

	public static final int WS_EX_TRANSPARENT = 0x20;

	public static final int WS_EX_WINDOWEDGE = 0x100;

	public static final int WS_GROUP = 0x20000;

	public static final int WS_HSCROLL = 0x100000;

	public static final int WS_ICONIC = 0x20000000;

	public static final int WS_MAXIMIZE = 0x1000000;

	public static final int WS_MAXIMIZEBOX = Extension.IsWinCE ? 0x20000
			: 0x10000;

	public static final int WS_MINIMIZE = 0x20000000;

	public static final int WS_MINIMIZEBOX = Extension.IsWinCE ? 0x10000
			: 0x20000;

	public static final int WS_OVERLAPPED = Extension.IsWinCE ? WS_BORDER
			| WS_CAPTION : 0x0;

	public static final int WS_OVERLAPPEDWINDOW = 0xCF0000;

	public static final int WS_POPUP = 0x80000000;

	public static final int WS_POPUPWINDOW = 0x80880000;

	public static final int WS_SIZEBOX = 0x40000;

	public static final int WS_SYSMENU = 0x80000;

	public static final int WS_TABSTOP = 0x10000;

	public static final int WS_THICKFRAME = 0x40000;

	public static final int WS_TILED = 0x0;

	public static final int WS_VISIBLE = 0x10000000;

	public static final int WS_VSCROLL = 0x200000;

	public static final int XBUTTON1 = 0x1;

	public static final int XBUTTON2 = 0x2;

	public static final int SYS_COLOR_INDEX_FLAG = 0x0;

	public static final int COLOR_3DDKSHADOW = 0x15 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_3DFACE = 0xf | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_3DHIGHLIGHT = 0x14 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_3DHILIGHT = 0x14 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_3DLIGHT = 0x16 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_3DSHADOW = 0x10 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_ACTIVECAPTION = 0x2 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_BTNFACE = 0xf | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_BTNHIGHLIGHT = 0x14 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_BTNSHADOW = 0x10 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_BTNTEXT = 0x12 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_CAPTIONTEXT = 0x9 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_GRADIENTACTIVECAPTION = 0x1b | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_GRADIENTINACTIVECAPTION = 0x1c | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_GRAYTEXT = 0x11 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_HIGHLIGHT = 0xd | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_HIGHLIGHTTEXT = 0xe | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_HOTLIGHT = 26 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_INACTIVECAPTION = 0x3 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_INACTIVECAPTIONTEXT = 0x13 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_INFOBK = 0x18 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_INFOTEXT = 0x17 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_MENU = 0x4 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_MENUTEXT = 0x7 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_SCROLLBAR = 0x0 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_WINDOW = 0x5 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_WINDOWFRAME = 0x6 | SYS_COLOR_INDEX_FLAG;

	public static final int COLOR_WINDOWTEXT = 0x8 | SYS_COLOR_INDEX_FLAG;

	public static final int COLORONCOLOR = 0x3;

	public static final int GCL_STYLE = -26;

	public static final int WM_NCXBUTTONUP = 0x00AC;
	
	public static final int WM_NCXBUTTONDBLCLK = 0x00AD;

	public static final int WM_NCXBUTTONDOWN = 0x00AB;

	public static final int SEE_MASK_IDLIST = 0x00000004;

	public static final int SWP_NOSENDCHANGING =0x0400;

	public static final int SWP_DEFERERASE = 0x2000;


	public static int getWin32Version() {
		return Extension.WIN32_VERSION;
	}

	public static int VERSION(int major, int minor) {
		return Extension.VERSION(major, minor);
	}
}
