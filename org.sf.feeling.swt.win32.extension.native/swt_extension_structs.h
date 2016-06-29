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

#include "swt_extension.h"

#ifndef NO_LOGICALDRIVES

struct LOGICALDRIVES {
	boolean returnValue; 
	int lastError;
} ;

void cacheLOGICALDRIVESFields(JNIEnv *env, jobject lpObject);
LOGICALDRIVES *getLOGICALDRIVESFields(JNIEnv *env, jobject lpObject, LOGICALDRIVES *lpStruct);
void setLOGICALDRIVESFields(JNIEnv *env, jobject lpObject, LOGICALDRIVES *lpStruct);
#define LOGICALDRIVES_sizeof() sizeof(LOGICALDRIVES)
#else
#define cacheLOGICALDRIVESFields(a,b)
#define getLOGICALDRIVESFields(a,b,c) NULL
#define setLOGICALDRIVESFields(a,b,c)
#define LOGICALDRIVES_sizeof() 0
#endif

#ifndef NO_DISKFREESPACE

struct DISKFREESPACE {
	boolean returnValue;
	int lastError;
	_int64 freeBytesAvailable;
	_int64 totalNumberOfBytes;
	_int64 totalNumberOfFreeBytes;
} ;

void cacheDISKFREESPACEFields(JNIEnv *env, jobject lpObject);
DISKFREESPACE *getDISKFREESPACEFields(JNIEnv *env, jobject lpObject, DISKFREESPACE *lpStruct);
void setDISKFREESPACEFields(JNIEnv *env, jobject lpObject, DISKFREESPACE *lpStruct);
#define DISKFREESPACE_sizeof() sizeof(DISKFREESPACE)
#else
#define cacheDISKFREESPACEFields(a,b)
#define getDISKFREESPACEFields(a,b,c) NULL
#define setDISKFREESPACEFields(a,b,c)
#define DISKFREESPACE_sizeof() 0
#endif


#ifndef NO_SYSTEM_INFO
void cacheSYSTEM_INFOFields(JNIEnv *env, jobject lpObject);
SYSTEM_INFO *getSYSTEM_INFOFields(JNIEnv *env, jobject lpObject, SYSTEM_INFO *lpStruct);
void setSYSTEM_INFOFields(JNIEnv *env, jobject lpObject, SYSTEM_INFO *lpStruct);
#define SYSTEM_INFO_sizeof() sizeof(SYSTEM_INFO)
#else
#define cacheSYSTEM_INFOFields(a,b)
#define getSYSTEM_INFOFields(a,b,c) NULL
#define setSYSTEM_INFOFields(a,b,c)
#define SYSTEM_INFO_sizeof() 0
#endif

#ifndef NO_MEMORYSTATUS
void cacheMEMORYSTATUSFields(JNIEnv *env, jobject lpObject);
MEMORYSTATUSEX *getMEMORYSTATUSFields(JNIEnv *env, jobject lpObject, MEMORYSTATUSEX *lpStruct);
void setMEMORYSTATUSFields(JNIEnv *env, jobject lpObject, MEMORYSTATUSEX *lpStruct);
#define MEMORYSTATUS_sizeof() sizeof(MEMORYSTATUSEX)
#else
#define cacheMEMORYSTATUSFields(a,b)
#define getMEMORYSTATUSFields(a,b,c) NULL
#define setMEMORYSTATUSFields(a,b,c)
#define MEMORYSTATUS_sizeof() 0
#endif

#ifndef NO_SHFILEOPSTRUCTW
void cacheSHFILEOPSTRUCTWFields(JNIEnv *env, jobject lpObject);
SHFILEOPSTRUCTW *getSHFILEOPSTRUCTWFields(JNIEnv *env, jobject lpObject, SHFILEOPSTRUCTW *lpStruct);
void setSHFILEOPSTRUCTWFields(JNIEnv *env, jobject lpObject, SHFILEOPSTRUCTW *lpStruct);
#define SHFILEOPSTRUCTW_sizeof() sizeof(SHFILEOPSTRUCTW)
#else
#define cacheSHFILEOPSTRUCTWFields(a,b)
#define setSHFILEOPSTRUCTWFields(a,b,c)
#define SHFILEOPSTRUCTW_sizeof() 0
#endif

#ifndef NO_SHFILEOPSTRUCTA
void cacheSHFILEOPSTRUCTAFields(JNIEnv *env, jobject lpObject);
SHFILEOPSTRUCTA *getSHFILEOPSTRUCTAFields(JNIEnv *env, jobject lpObject, SHFILEOPSTRUCTA *lpStruct);
void setSHFILEOPSTRUCTAFields(JNIEnv *env, jobject lpObject, SHFILEOPSTRUCTA *lpStruct);
#define SHFILEOPSTRUCTA_sizeof() sizeof(SHFILEOPSTRUCTA)
#else
#define cacheSHFILEOPSTRUCTAFields(a,b)
#define setSHFILEOPSTRUCTAFields(a,b,c)
#define SHFILEOPSTRUCTA_sizeof() 0
#endif

#ifndef NO_SHFILEINFO
void cacheSHFILEINFOFields(JNIEnv *env, jobject lpObject);
SHFILEINFO *getSHFILEINFOFields(JNIEnv *env, jobject lpObject, SHFILEINFO *lpStruct);
void setSHFILEINFOFields(JNIEnv *env, jobject lpObject, SHFILEINFO *lpStruct);
#define SHFILEINFO_sizeof() sizeof(SHFILEINFO)
#else
#define cacheSHFILEINFOFields(a,b)
#define getSHFILEINFOFields(a,b,c) NULL
#define setSHFILEINFOFields(a,b,c)
#define SHFILEINFO_sizeof() 0
#endif

#ifndef NO_SHFILEINFOA
void cacheSHFILEINFOAFields(JNIEnv *env, jobject lpObject);
SHFILEINFOA *getSHFILEINFOAFields(JNIEnv *env, jobject lpObject, SHFILEINFOA *lpStruct);
void setSHFILEINFOAFields(JNIEnv *env, jobject lpObject, SHFILEINFOA *lpStruct);
#define SHFILEINFOA_sizeof() sizeof(SHFILEINFOA)
#else
#define cacheSHFILEINFOAFields(a,b)
#define getSHFILEINFOAFields(a,b,c) NULL
#define setSHFILEINFOAFields(a,b,c)
#define SHFILEINFOA_sizeof() 0
#endif

#ifndef NO_SHFILEINFOW
void cacheSHFILEINFOWFields(JNIEnv *env, jobject lpObject);
SHFILEINFOW *getSHFILEINFOWFields(JNIEnv *env, jobject lpObject, SHFILEINFOW *lpStruct);
void setSHFILEINFOWFields(JNIEnv *env, jobject lpObject, SHFILEINFOW *lpStruct);
#define SHFILEINFOW_sizeof() sizeof(SHFILEINFOW)
#else
#define cacheSHFILEINFOWFields(a,b)
#define getSHFILEINFOWFields(a,b,c) NULL
#define setSHFILEINFOWFields(a,b,c)
#define SHFILEINFOW_sizeof() 0
#endif

#ifndef NO_FLASHWINFO
void cacheFLASHWINFOFields(JNIEnv *env, jobject lpObject);
FLASHWINFO *getFLASHWINFOFields(JNIEnv *env, jobject lpObject, FLASHWINFO *lpStruct);
void setFLASHWINFOFields(JNIEnv *env, jobject lpObject, FLASHWINFO *lpStruct);
#define FLASHWINFO_sizeof() sizeof(FLASHWINFO)
#else
#define cacheFLASHWINFOFields(a,b)
#define getFLASHWINFOFields(a,b,c) NULL
#define setFLASHWINFOFields(a,b,c)
#define FLASHWINFO_sizeof() 0
#endif

#ifndef NO_APPBARDATA
void cacheAPPBARDATAFields(JNIEnv *env, jobject lpObject);
APPBARDATA *getAPPBARDATAFields(JNIEnv *env, jobject lpObject, APPBARDATA *lpStruct);
void setAPPBARDATAFields(JNIEnv *env, jobject lpObject, APPBARDATA *lpStruct);
#define APPBARDATA_sizeof() sizeof(APPBARDATA)
#else
#define cacheAPPBARDATAFields(a,b)
#define getAPPBARDATAFields(a,b,c) NULL
#define setAPPBARDATAFields(a,b,c)
#define APPBARDATA_sizeof() 0
#endif

#ifndef NO_MOUSEHOOKSTRUCT
void cacheMOUSEHOOKSTRUCTFields(JNIEnv *env, jobject lpObject);
MOUSEHOOKSTRUCT *getMOUSEHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, MOUSEHOOKSTRUCT *lpStruct);
void setMOUSEHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, MOUSEHOOKSTRUCT *lpStruct);
#define MOUSEHOOKSTRUCT_sizeof() sizeof(MOUSEHOOKSTRUCT)
#else
#define cacheMOUSEHOOKSTRUCTFields(a,b)
#define getMOUSEHOOKSTRUCTFields(a,b,c) NULL
#define setMOUSEHOOKSTRUCTFields(a,b,c)
#define MOUSEHOOKSTRUCT_sizeof() 0
#endif

#ifndef NO_MSLLHOOKSTRUCT
void cacheMSLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject);
MSLLHOOKSTRUCT *getMSLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, MSLLHOOKSTRUCT *lpStruct);
void setMSLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, MSLLHOOKSTRUCT *lpStruct);
#define MSLLHOOKSTRUCT_sizeof() sizeof(MSLLHOOKSTRUCT)
#else
#define cacheMSLLHOOKSTRUCTFields(a,b)
#define getMSLLHOOKSTRUCTFields(a,b,c) NULL
#define setMSLLHOOKSTRUCTFields(a,b,c)
#define MSLLHOOKSTRUCT_sizeof() 0
#endif

#ifndef NO_KBDLLHOOKSTRUCT
void cacheKBDLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject);
KBDLLHOOKSTRUCT *getKBDLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, KBDLLHOOKSTRUCT *lpStruct);
void setKBDLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, KBDLLHOOKSTRUCT *lpStruct);
#define KBDLLHOOKSTRUCT_sizeof() sizeof(KBDLLHOOKSTRUCT)
#else
#define cacheKBDLLHOOKSTRUCTFields(a,b)
#define getKBDLLHOOKSTRUCTFields(a,b,c) NULL
#define setKBDLLHOOKSTRUCTFields(a,b,c)
#define KBDLLHOOKSTRUCT_sizeof() 0
#endif

#ifndef NO_EVENTMSG
void cacheEVENTMSGFields(JNIEnv *env, jobject lpObject);
EVENTMSG *getEVENTMSGFields(JNIEnv *env, jobject lpObject, EVENTMSG *lpStruct);
void setEVENTMSGFields(JNIEnv *env, jobject lpObject, EVENTMSG *lpStruct);
#define EVENTMSG_sizeof() sizeof(EVENTMSG)
#else
#define cacheEVENTMSGFields(a,b)
#define getEVENTMSGFields(a,b,c) NULL
#define setEVENTMSGFields(a,b,c)
#define EVENTMSG_sizeof() 0
#endif

#ifndef NO_HOOKSTRUCT
typedef struct   
{   
  HHOOK hkb[15];
  WPARAM wParam[15];
  LPARAM lParam[15];
  INT nCode[15];
  MOUSEHOOKSTRUCT mouseHookStruct;
  MSG msg;
} HOOKSTRUCT;
#endif

#ifndef NO_POINT
void cachePOINTFields(JNIEnv *env, jobject lpObject);
POINT *getPOINTFields(JNIEnv *env, jobject lpObject, POINT *lpStruct);
void setPOINTFields(JNIEnv *env, jobject lpObject, POINT *lpStruct);
#define POINT_sizeof() sizeof(POINT)
#else
#define cachePOINTFields(a,b)
#define getPOINTFields(a,b,c) NULL
#define setPOINTFields(a,b,c)
#define POINT_sizeof() 0
#endif

#ifndef NO_RECT
void cacheRECTFields(JNIEnv *env, jobject lpObject);
RECT *getRECTFields(JNIEnv *env, jobject lpObject, RECT *lpStruct);
void setRECTFields(JNIEnv *env, jobject lpObject, RECT *lpStruct);
#define RECT_sizeof() sizeof(RECT)
#else
#define cacheRECTFields(a,b)
#define getRECTFields(a,b,c) NULL
#define setRECTFields(a,b,c)
#define RECT_sizeof() 0
#endif

#ifndef NO_MCI_OPEN_PARMSA
void cacheMCI_OPEN_PARMSAFields(JNIEnv *env, jobject lpObject);
MCI_OPEN_PARMSA *getMCI_OPEN_PARMSAFields(JNIEnv *env, jobject lpObject, MCI_OPEN_PARMSA *lpStruct);
void setMCI_OPEN_PARMSAFields(JNIEnv *env, jobject lpObject, MCI_OPEN_PARMSA *lpStruct);
#define MCI_OPEN_PARMSA_sizeof() sizeof(MCI_OPEN_PARMSA)
#else
#define cacheMCI_OPEN_PARMSAFields(a,b)
#define getMCI_OPEN_PARMSAFields(a,b,c) NULL
#define setMCI_OPEN_PARMSAFields(a,b,c)
#define MCI_OPEN_PARMSA_sizeof() 0
#endif

#ifndef NO_MCI_OPEN_PARMSW
void cacheMCI_OPEN_PARMSWFields(JNIEnv *env, jobject lpObject);
MCI_OPEN_PARMSW *getMCI_OPEN_PARMSWFields(JNIEnv *env, jobject lpObject, MCI_OPEN_PARMSW *lpStruct);
void setMCI_OPEN_PARMSWFields(JNIEnv *env, jobject lpObject, MCI_OPEN_PARMSW *lpStruct);
#define MCI_OPEN_PARMSW_sizeof() sizeof(MCI_OPEN_PARMSW)
#else
#define cacheMCI_OPEN_PARMSWFields(a,b)
#define getMCI_OPEN_PARMSWFields(a,b,c) NULL
#define setMCI_OPEN_PARMSWFields(a,b,c)
#define MCI_OPEN_PARMSW_sizeof() 0
#endif

#ifndef NO_MCI_STATUS_PARMS
void cacheMCI_STATUS_PARMSFields(JNIEnv *env, jobject lpObject);
MCI_STATUS_PARMS *getMCI_STATUS_PARMSFields(JNIEnv *env, jobject lpObject, MCI_STATUS_PARMS *lpStruct);
void setMCI_STATUS_PARMSFields(JNIEnv *env, jobject lpObject, MCI_STATUS_PARMS *lpStruct);
#define MCI_STATUS_PARMS_sizeof() sizeof(MCI_STATUS_PARMS)
#else
#define cacheMCI_STATUS_PARMSFields(a,b)
#define getMCI_STATUS_PARMSFields(a,b,c) NULL
#define setMCI_STATUS_PARMSFields(a,b,c)
#define MCI_STATUS_PARMS_sizeof() 0
#endif

#ifndef NO_MSG
void cacheMSGFields(JNIEnv *env, jobject lpObject);
MSG *getMSGFields(JNIEnv *env, jobject lpObject, MSG *lpStruct);
void setMSGFields(JNIEnv *env, jobject lpObject, MSG *lpStruct);
#define MSG_sizeof() sizeof(MSG)
#else
#define cacheMSGFields(a,b)
#define getMSGFields(a,b,c) NULL
#define setMSGFields(a,b,c)
#define MSG_sizeof() 0
#endif

#ifndef NO_MIXERCAPS
void cacheMIXERCAPSFields(JNIEnv *env, jobject lpObject);
MIXERCAPS *getMIXERCAPSFields(JNIEnv *env, jobject lpObject, MIXERCAPS *lpStruct);
void setMIXERCAPSFields(JNIEnv *env, jobject lpObject, MIXERCAPS *lpStruct);
#define MIXERCAPS_sizeof() sizeof(MIXERCAPS)
#else
#define cacheMIXERCAPSFields(a,b)
#define getMIXERCAPSFields(a,b,c) NULL
#define setMIXERCAPSFields(a,b,c)
#define MIXERCAPS_sizeof() 0
#endif

#ifndef NO_MIXERCAPSA
void cacheMIXERCAPSAFields(JNIEnv *env, jobject lpObject);
MIXERCAPSA *getMIXERCAPSAFields(JNIEnv *env, jobject lpObject, MIXERCAPSA *lpStruct);
void setMIXERCAPSAFields(JNIEnv *env, jobject lpObject, MIXERCAPSA *lpStruct);
#define MIXERCAPSA_sizeof() sizeof(MIXERCAPSA)
#else
#define cacheMIXERCAPSAFields(a,b)
#define getMIXERCAPSAFields(a,b,c) NULL
#define setMIXERCAPSAFields(a,b,c)
#define MIXERCAPSA_sizeof() 0
#endif

#ifndef NO_MIXERCAPSW
void cacheMIXERCAPSWFields(JNIEnv *env, jobject lpObject);
MIXERCAPSW *getMIXERCAPSWFields(JNIEnv *env, jobject lpObject, MIXERCAPSW *lpStruct);
void setMIXERCAPSWFields(JNIEnv *env, jobject lpObject, MIXERCAPSW *lpStruct);
#define MIXERCAPSW_sizeof() sizeof(MIXERCAPSW)
#else
#define cacheMIXERCAPSWFields(a,b)
#define getMIXERCAPSWFields(a,b,c) NULL
#define setMIXERCAPSWFields(a,b,c)
#define MIXERCAPSW_sizeof() 0
#endif


#ifndef NO_MIXERLINE
void cacheMIXERLINEFields(JNIEnv *env, jobject lpObject);
MIXERLINEW *getMIXERLINEWFields(JNIEnv *env, jobject lpObject, MIXERLINEW *lpStruct);
void setMIXERLINEFields(JNIEnv *env, jobject lpObject, MIXERLINE *lpStruct);
#define MIXERLINE_sizeof() sizeof(MIXERLINE)
#else
#define cacheMIXERLINEFields(a,b)
#define getMIXERLINEWFields(a,b,c) NULL
#define setMIXERLINEFields(a,b,c)
#define MIXERLINE_sizeof() 0
#endif

#ifndef NO_MIXERLINEA
void cacheMIXERLINEAFields(JNIEnv *env, jobject lpObject);
MIXERLINEA *getMIXERLINEAFields(JNIEnv *env, jobject lpObject, MIXERLINEA *lpStruct);
void setMIXERLINEAFields(JNIEnv *env, jobject lpObject, MIXERLINEA *lpStruct);
#define MIXERLINEA_sizeof() sizeof(MIXERLINEA)
#else
#define cacheMIXERLINEAFields(a,b)
#define getMIXERLINEAFields(a,b,c) NULL
#define setMIXERLINEAFields(a,b,c)
#define MIXERLINEA_sizeof() 0
#endif

#ifndef NO_MIXERLINEW
void cacheMIXERLINEWFields(JNIEnv *env, jobject lpObject);
MIXERLINEW *getMIXERLINEWFields(JNIEnv *env, jobject lpObject, MIXERLINEW *lpStruct);
void setMIXERLINEWFields(JNIEnv *env, jobject lpObject, MIXERLINEW *lpStruct);
#define MIXERLINEW_sizeof() sizeof(MIXERLINEW)
#else
#define cacheMIXERLINEWFields(a,b)
#define getMIXERLINEWFields(a,b,c) NULL
#define setMIXERLINEWFields(a,b,c)
#define MIXERLINEW_sizeof() 0
#endif
