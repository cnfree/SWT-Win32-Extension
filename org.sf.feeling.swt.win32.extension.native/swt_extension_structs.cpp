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

#include "swt.h"
#include "swt_extension_structs.h"


#ifndef NO_LOGICALDRIVES
typedef struct LOGICALDRIVES_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID returnValue, lastError;
} LOGICALDRIVES_FID_CACHE;

LOGICALDRIVES_FID_CACHE LOGICALDRIVESFc;

void cacheLOGICALDRIVESFields(JNIEnv *env, jobject lpObject)
{
	if (LOGICALDRIVESFc.cached) return;
	LOGICALDRIVESFc.clazz = env->GetObjectClass(lpObject);
	LOGICALDRIVESFc.returnValue = env->GetFieldID(LOGICALDRIVESFc.clazz, "returnValue", "Z");
	LOGICALDRIVESFc.lastError = env->GetFieldID(LOGICALDRIVESFc.clazz, "lastError", "I");
	LOGICALDRIVESFc.cached = 1;
}

LOGICALDRIVES *getLOGICALDRIVESFields(JNIEnv *env, jobject lpObject, LOGICALDRIVES *lpStruct)
{
	if (!LOGICALDRIVESFc.cached) cacheLOGICALDRIVESFields(env, lpObject);
	lpStruct->returnValue = env->GetBooleanField(lpObject, LOGICALDRIVESFc.returnValue);
	lpStruct->lastError = env->GetIntField(lpObject, LOGICALDRIVESFc.lastError);
	return lpStruct;
}

void setLOGICALDRIVESFields(JNIEnv *env, jobject lpObject, LOGICALDRIVES *lpStruct)
{
	if (!LOGICALDRIVESFc.cached) cacheLOGICALDRIVESFields(env,lpObject);
	env->SetBooleanField(lpObject, LOGICALDRIVESFc.returnValue, (jbyte)lpStruct->returnValue);
	env->SetIntField(lpObject, LOGICALDRIVESFc.lastError, (jint)lpStruct->lastError);
}
#endif


#ifndef NO_DISKFREESPACE
typedef struct DISKFREESPACE_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID returnValue, lastError, freeBytesAvailable, totalNumberOfBytes, totalNumberOfFreeBytes;
} DISKFREESPACE_FID_CACHE;

DISKFREESPACE_FID_CACHE DISKFREESPACEFc;

void cacheDISKFREESPACEFields(JNIEnv *env, jobject lpObject)
{
	if (DISKFREESPACEFc.cached) return;
	DISKFREESPACEFc.clazz = env->GetObjectClass(lpObject);
	DISKFREESPACEFc.returnValue = env->GetFieldID(DISKFREESPACEFc.clazz, "returnValue", "Z");
	DISKFREESPACEFc.lastError = env->GetFieldID(DISKFREESPACEFc.clazz, "lastError", "I");
	DISKFREESPACEFc.freeBytesAvailable = env->GetFieldID(DISKFREESPACEFc.clazz, "freeBytesAvailable", "J");
	DISKFREESPACEFc.totalNumberOfBytes = env->GetFieldID(DISKFREESPACEFc.clazz, "totalNumberOfBytes", "J");
	DISKFREESPACEFc.totalNumberOfFreeBytes = env->GetFieldID(DISKFREESPACEFc.clazz, "totalNumberOfFreeBytes", "J");
	DISKFREESPACEFc.cached = 1;
}


DISKFREESPACE *getDISKFREESPACEFields(JNIEnv *env, jobject lpObject, DISKFREESPACE *lpStruct)
{
	if (!DISKFREESPACEFc.cached) cacheDISKFREESPACEFields(env, lpObject);
	lpStruct->returnValue = env->GetBooleanField(lpObject, DISKFREESPACEFc.returnValue);
	lpStruct->lastError = env->GetIntField(lpObject, DISKFREESPACEFc.lastError);
	lpStruct->freeBytesAvailable = env->GetLongField(lpObject, DISKFREESPACEFc.freeBytesAvailable);
	lpStruct->totalNumberOfBytes = env->GetLongField(lpObject, DISKFREESPACEFc.totalNumberOfBytes);
	lpStruct->totalNumberOfFreeBytes = env->GetLongField(lpObject, DISKFREESPACEFc.totalNumberOfFreeBytes);
	return lpStruct;
}

void setDISKFREESPACEFields(JNIEnv *env, jobject lpObject, DISKFREESPACE *lpStruct)
{
	if (!DISKFREESPACEFc.cached) cacheDISKFREESPACEFields(env, lpObject);
	env->SetBooleanField(lpObject, DISKFREESPACEFc.returnValue, (jbyte)lpStruct->returnValue);
	env->SetIntField(lpObject, DISKFREESPACEFc.lastError, (jint)lpStruct->lastError);
	env->SetLongField(lpObject, DISKFREESPACEFc.freeBytesAvailable, (jlong)lpStruct->freeBytesAvailable);
	env->SetLongField(lpObject, DISKFREESPACEFc.totalNumberOfBytes, (jlong)lpStruct->totalNumberOfBytes);
	env->SetLongField(lpObject, DISKFREESPACEFc.totalNumberOfFreeBytes, (jlong)lpStruct->totalNumberOfFreeBytes);
}
#endif


#ifndef NO_SYSTEM_INFO
typedef struct SYSTEM_INFO_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID dwOemId, dwPageSize, lpMinimumApplicationAddress, lpMaximumApplicationAddress, dwActiveProcessorMask,dwNumberOfProcessors,dwProcessorType,dwAllocationGranularity;
} SYSTEM_INFO_FID_CACHE;

SYSTEM_INFO_FID_CACHE SYSTEM_INFOFc;

void cacheSYSTEM_INFOFields(JNIEnv *env, jobject lpObject)
{
	if (SYSTEM_INFOFc.cached) return;
	SYSTEM_INFOFc.clazz = env->GetObjectClass(lpObject);
	SYSTEM_INFOFc.dwOemId = env->GetFieldID(SYSTEM_INFOFc.clazz, "dwOemId", "I");
	SYSTEM_INFOFc.dwPageSize = env->GetFieldID(SYSTEM_INFOFc.clazz, "dwPageSize", "I");
	SYSTEM_INFOFc.lpMinimumApplicationAddress = env->GetFieldID(SYSTEM_INFOFc.clazz, "lpMinimumApplicationAddress", "I");
	SYSTEM_INFOFc.lpMaximumApplicationAddress = env->GetFieldID(SYSTEM_INFOFc.clazz, "lpMaximumApplicationAddress", "I");
	SYSTEM_INFOFc.dwActiveProcessorMask = env->GetFieldID(SYSTEM_INFOFc.clazz, "dwActiveProcessorMask", "I");
	SYSTEM_INFOFc.dwNumberOfProcessors = env->GetFieldID(SYSTEM_INFOFc.clazz, "dwNumberOfProcessors", "I");
	SYSTEM_INFOFc.dwProcessorType = env->GetFieldID(SYSTEM_INFOFc.clazz, "dwProcessorType", "I");
	SYSTEM_INFOFc.dwAllocationGranularity = env->GetFieldID(SYSTEM_INFOFc.clazz, "dwAllocationGranularity", "I");
	SYSTEM_INFOFc.cached = 1;
}

void setSYSTEM_INFOFields(JNIEnv *env, jobject lpObject, SYSTEM_INFO *lpStruct)
{
	if (!SYSTEM_INFOFc.cached) cacheSYSTEM_INFOFields(env, lpObject);
	env->SetIntField(lpObject, SYSTEM_INFOFc.dwOemId, (jint)lpStruct->dwOemId);
	env->SetIntField(lpObject, SYSTEM_INFOFc.dwPageSize, (jint)lpStruct->dwPageSize);
	env->SetIntField(lpObject, SYSTEM_INFOFc.lpMinimumApplicationAddress, (jint)lpStruct->lpMinimumApplicationAddress);
	env->SetIntField(lpObject, SYSTEM_INFOFc.lpMaximumApplicationAddress, (jint)lpStruct->lpMaximumApplicationAddress);
	env->SetIntField(lpObject, SYSTEM_INFOFc.dwActiveProcessorMask, (jint)lpStruct->dwActiveProcessorMask);
	env->SetIntField(lpObject, SYSTEM_INFOFc.dwNumberOfProcessors, (jint)lpStruct->dwNumberOfProcessors);
	env->SetIntField(lpObject, SYSTEM_INFOFc.dwProcessorType, (jint)lpStruct->dwProcessorType);
	env->SetIntField(lpObject, SYSTEM_INFOFc.dwAllocationGranularity, (jint)lpStruct->dwAllocationGranularity);
}

SYSTEM_INFO *getSYSTEM_INFOFields(JNIEnv *env, jobject lpObject, SYSTEM_INFO *lpStruct)
{
	if (!SYSTEM_INFOFc.cached) cacheSYSTEM_INFOFields(env, lpObject);
	lpStruct->dwOemId = (DWORD)env->GetIntField(lpObject, SYSTEM_INFOFc.dwOemId);
	lpStruct->dwPageSize = (DWORD)env->GetIntField(lpObject, SYSTEM_INFOFc.dwPageSize);
	lpStruct->lpMinimumApplicationAddress = (LPVOID)env->GetIntField(lpObject, SYSTEM_INFOFc.lpMinimumApplicationAddress);
	lpStruct->lpMaximumApplicationAddress = (LPVOID)env->GetIntField(lpObject, SYSTEM_INFOFc.lpMaximumApplicationAddress);
	lpStruct->dwActiveProcessorMask = (DWORD)env->GetIntField(lpObject, SYSTEM_INFOFc.dwActiveProcessorMask);
	lpStruct->dwNumberOfProcessors = (DWORD)env->GetIntField(lpObject, SYSTEM_INFOFc.dwNumberOfProcessors);
	lpStruct->dwProcessorType = (DWORD)env->GetIntField(lpObject, SYSTEM_INFOFc.dwProcessorType);
	lpStruct->dwAllocationGranularity = (DWORD)env->GetIntField(lpObject, SYSTEM_INFOFc.dwAllocationGranularity);
	return lpStruct;
}
#endif


#ifndef NO_SHFILEOPSTRUCTW
typedef struct SHFILEOPSTRUCTW_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID hwnd, wFunc, pFrom, pTo, fFlags,fAnyOperationsAborted,hNameMappings,lpszProgressTitle;
} SHFILEOPSTRUCTW_FID_CACHE;

SHFILEOPSTRUCTW_FID_CACHE SHFILEOPSTRUCTWFc;

void cacheSHFILEOPSTRUCTWFields(JNIEnv *env, jobject lpObject)
{
	if (SHFILEOPSTRUCTWFc.cached) return;
	SHFILEOPSTRUCTWFc.clazz = env->GetObjectClass(lpObject);
	SHFILEOPSTRUCTWFc.hwnd = env->GetFieldID(SHFILEOPSTRUCTWFc.clazz, "hwnd", "I");
	SHFILEOPSTRUCTWFc.wFunc = env->GetFieldID(SHFILEOPSTRUCTWFc.clazz, "wFunc", "I");
	SHFILEOPSTRUCTWFc.pFrom = env->GetFieldID(SHFILEOPSTRUCTWFc.clazz, "pFrom", "I");
	SHFILEOPSTRUCTWFc.pTo = env->GetFieldID(SHFILEOPSTRUCTWFc.clazz, "pTo", "I");
	SHFILEOPSTRUCTWFc.fFlags = env->GetFieldID(SHFILEOPSTRUCTWFc.clazz, "fFlags", "I");
	SHFILEOPSTRUCTWFc.fAnyOperationsAborted = env->GetFieldID(SHFILEOPSTRUCTWFc.clazz, "fAnyOperationsAborted", "I");
	SHFILEOPSTRUCTWFc.hNameMappings = env->GetFieldID(SHFILEOPSTRUCTWFc.clazz, "hNameMappings", "I");
	SHFILEOPSTRUCTWFc.lpszProgressTitle = env->GetFieldID(SHFILEOPSTRUCTWFc.clazz, "lpszProgressTitle", "I");
	SHFILEOPSTRUCTWFc.cached = 1;
}

SHFILEOPSTRUCTW *getSHFILEOPSTRUCTWFields(JNIEnv *env, jobject lpObject, SHFILEOPSTRUCTW *lpStruct)
{
	if (!SHFILEOPSTRUCTWFc.cached) cacheSHFILEOPSTRUCTWFields(env, lpObject);
	lpStruct->hwnd = (HWND)env->GetIntField(lpObject, SHFILEOPSTRUCTWFc.hwnd);
	lpStruct->wFunc = (UINT)env->GetIntField(lpObject, SHFILEOPSTRUCTWFc.wFunc);
	lpStruct->pFrom = (LPCWSTR)env->GetIntField(lpObject, SHFILEOPSTRUCTWFc.pFrom);
	lpStruct->pTo = (LPCWSTR)env->GetIntField(lpObject, SHFILEOPSTRUCTWFc.pTo);
	lpStruct->fFlags = (FILEOP_FLAGS)env->GetIntField(lpObject, SHFILEOPSTRUCTWFc.fFlags);
	lpStruct->fAnyOperationsAborted = (BOOL)env->GetIntField(lpObject, SHFILEOPSTRUCTWFc.fAnyOperationsAborted);
	lpStruct->hNameMappings = (LPVOID)env->GetIntField(lpObject, SHFILEOPSTRUCTWFc.hNameMappings);
	lpStruct->lpszProgressTitle = (LPCWSTR)env->GetIntField(lpObject, SHFILEOPSTRUCTWFc.lpszProgressTitle);
	return lpStruct;
}

void setSHFILEOPSTRUCTWFields(JNIEnv *env, jobject lpObject, SHFILEOPSTRUCTW *lpStruct)
{
	if (!SHFILEOPSTRUCTWFc.cached) cacheSHFILEOPSTRUCTWFields(env, lpObject);
	env->SetIntField(lpObject, SHFILEOPSTRUCTWFc.hwnd, (jint)lpStruct->hwnd);
	env->SetIntField(lpObject, SHFILEOPSTRUCTWFc.pFrom, (jint)lpStruct->pFrom);
	env->SetIntField(lpObject, SHFILEOPSTRUCTWFc.pTo, (jint)lpStruct->pTo);
	env->SetIntField(lpObject, SHFILEOPSTRUCTWFc.fFlags, (jint)lpStruct->fFlags);
	env->SetIntField(lpObject, SHFILEOPSTRUCTWFc.fAnyOperationsAborted, (jint)lpStruct->fAnyOperationsAborted);
	env->SetIntField(lpObject, SHFILEOPSTRUCTWFc.hNameMappings, (jint)lpStruct->hNameMappings);
	env->SetIntField(lpObject, SHFILEOPSTRUCTWFc.lpszProgressTitle, (jint)lpStruct->lpszProgressTitle);
}

#endif


#ifndef NO_SHFILEOPSTRUCTA
typedef struct SHFILEOPSTRUCTA_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID hwnd, wFunc, pFrom, pTo, fFlags,fAnyOperationsAborted,hNameMappings,lpszProgressTitle;
} SHFILEOPSTRUCTA_FID_CACHE;

SHFILEOPSTRUCTA_FID_CACHE SHFILEOPSTRUCTAFc;

void cacheSHFILEOPSTRUCTAFields(JNIEnv *env, jobject lpObject)
{
	if (SHFILEOPSTRUCTAFc.cached) return;
	SHFILEOPSTRUCTAFc.clazz = env->GetObjectClass(lpObject);
	SHFILEOPSTRUCTAFc.hwnd = env->GetFieldID(SHFILEOPSTRUCTAFc.clazz, "hwnd", "I");
	SHFILEOPSTRUCTAFc.wFunc = env->GetFieldID(SHFILEOPSTRUCTAFc.clazz, "wFunc", "I");
	SHFILEOPSTRUCTAFc.pFrom = env->GetFieldID(SHFILEOPSTRUCTAFc.clazz, "pFrom", "I");
	SHFILEOPSTRUCTAFc.pTo = env->GetFieldID(SHFILEOPSTRUCTAFc.clazz, "pTo", "I");
	SHFILEOPSTRUCTAFc.fFlags = env->GetFieldID(SHFILEOPSTRUCTAFc.clazz, "fFlags", "I");
	SHFILEOPSTRUCTAFc.fAnyOperationsAborted = env->GetFieldID(SHFILEOPSTRUCTAFc.clazz, "fAnyOperationsAborted", "I");
	SHFILEOPSTRUCTAFc.hNameMappings = env->GetFieldID(SHFILEOPSTRUCTAFc.clazz, "hNameMappings", "I");
	SHFILEOPSTRUCTAFc.lpszProgressTitle = env->GetFieldID(SHFILEOPSTRUCTAFc.clazz, "lpszProgressTitle", "I");
	SHFILEOPSTRUCTAFc.cached = 1;
}

SHFILEOPSTRUCTA *getSHFILEOPSTRUCTAFields(JNIEnv *env, jobject lpObject, SHFILEOPSTRUCTA *lpStruct)
{
	if (!SHFILEOPSTRUCTAFc.cached) cacheSHFILEOPSTRUCTAFields(env, lpObject);
	lpStruct->hwnd = (HWND)env->GetIntField(lpObject, SHFILEOPSTRUCTAFc.hwnd);
	lpStruct->wFunc = (UINT)env->GetIntField(lpObject, SHFILEOPSTRUCTAFc.wFunc);
	lpStruct->pFrom = (LPCTSTR)env->GetIntField(lpObject, SHFILEOPSTRUCTAFc.pFrom);
	lpStruct->pTo = (LPCTSTR)env->GetIntField(lpObject, SHFILEOPSTRUCTAFc.pTo);
	lpStruct->fFlags = (FILEOP_FLAGS)env->GetIntField(lpObject, SHFILEOPSTRUCTAFc.fFlags);
	lpStruct->fAnyOperationsAborted = (BOOL)env->GetIntField(lpObject, SHFILEOPSTRUCTAFc.fAnyOperationsAborted);
	lpStruct->hNameMappings = (LPVOID)env->GetIntField(lpObject, SHFILEOPSTRUCTAFc.hNameMappings);
	lpStruct->lpszProgressTitle = (LPCTSTR)env->GetIntField(lpObject, SHFILEOPSTRUCTAFc.lpszProgressTitle);
	return lpStruct;
}

void setSHFILEOPSTRUCTAFields(JNIEnv *env, jobject lpObject, SHFILEOPSTRUCTA *lpStruct)
{
	if (!SHFILEOPSTRUCTAFc.cached) cacheSHFILEOPSTRUCTAFields(env, lpObject);
	env->SetIntField(lpObject, SHFILEOPSTRUCTAFc.hwnd, (jint)lpStruct->hwnd);
	env->SetIntField(lpObject, SHFILEOPSTRUCTAFc.pFrom, (jint)lpStruct->pFrom);
	env->SetIntField(lpObject, SHFILEOPSTRUCTAFc.pTo, (jint)lpStruct->pTo);
	env->SetIntField(lpObject, SHFILEOPSTRUCTAFc.fFlags, (jint)lpStruct->fFlags);
	env->SetIntField(lpObject, SHFILEOPSTRUCTAFc.fAnyOperationsAborted, (jint)lpStruct->fAnyOperationsAborted);
	env->SetIntField(lpObject, SHFILEOPSTRUCTAFc.hNameMappings, (jint)lpStruct->hNameMappings);
	env->SetIntField(lpObject, SHFILEOPSTRUCTAFc.lpszProgressTitle, (jint)lpStruct->lpszProgressTitle);
}

#endif

#ifndef NO_MEMORYSTATUS
typedef struct MEMORYSTATUS_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID dwLength, dwMemoryLoad, ullTotalPhys, ullAvailPhys, ullTotalPageFile,ullAvailPageFile,ullTotalVirtual,ullAvailVirtual;
} MEMORYSTATUS_FID_CACHE;

MEMORYSTATUS_FID_CACHE MEMORYSTATUSFc;

void cacheMEMORYSTATUSFields(JNIEnv *env, jobject lpObject)
{
	if (MEMORYSTATUSFc.cached) return;
	MEMORYSTATUSFc.clazz = env->GetObjectClass(lpObject);
	MEMORYSTATUSFc.dwLength = env->GetFieldID(MEMORYSTATUSFc.clazz, "dwLength", "I");
	MEMORYSTATUSFc.dwMemoryLoad = env->GetFieldID(MEMORYSTATUSFc.clazz, "dwMemoryLoad", "I");
	MEMORYSTATUSFc.ullTotalPhys = env->GetFieldID(MEMORYSTATUSFc.clazz, "ullTotalPhys", "J");
	MEMORYSTATUSFc.ullAvailPhys = env->GetFieldID(MEMORYSTATUSFc.clazz, "ullAvailPhys", "J");
	MEMORYSTATUSFc.ullTotalPageFile = env->GetFieldID(MEMORYSTATUSFc.clazz, "ullTotalPageFile", "J");
	MEMORYSTATUSFc.ullAvailPageFile = env->GetFieldID(MEMORYSTATUSFc.clazz, "ullAvailPageFile", "J");
	MEMORYSTATUSFc.ullTotalVirtual = env->GetFieldID(MEMORYSTATUSFc.clazz, "ullTotalVirtual", "J");
	MEMORYSTATUSFc.ullAvailVirtual = env->GetFieldID(MEMORYSTATUSFc.clazz, "ullAvailVirtual", "J");
	MEMORYSTATUSFc.cached = 1;
}

MEMORYSTATUSEX *getMEMORYSTATUSFields(JNIEnv *env, jobject lpObject, MEMORYSTATUSEX *lpStruct)
{
	if (!MEMORYSTATUSFc.cached) cacheMEMORYSTATUSFields( env,lpObject);
	lpStruct->dwLength = (DWORD)env->GetIntField( lpObject, MEMORYSTATUSFc.dwLength);
	lpStruct->dwMemoryLoad = (DWORD)env->GetIntField( lpObject, MEMORYSTATUSFc.dwMemoryLoad);
	lpStruct->ullTotalPhys = (DWORDLONG)env->GetLongField( lpObject, MEMORYSTATUSFc.ullTotalPhys);
	lpStruct->ullAvailPhys = (DWORDLONG)env->GetLongField( lpObject, MEMORYSTATUSFc.ullAvailPhys);
	lpStruct->ullTotalPageFile = (DWORDLONG)env->GetLongField( lpObject, MEMORYSTATUSFc.ullTotalPageFile);
	lpStruct->ullAvailPageFile = (DWORDLONG)env->GetLongField( lpObject, MEMORYSTATUSFc.ullAvailPageFile);
	lpStruct->ullTotalVirtual = (DWORDLONG)env->GetLongField( lpObject, MEMORYSTATUSFc.ullTotalVirtual);
	lpStruct->ullAvailVirtual = (DWORDLONG)env->GetLongField( lpObject, MEMORYSTATUSFc.ullAvailVirtual);
	return lpStruct;
}

void setMEMORYSTATUSFields(JNIEnv *env, jobject lpObject, MEMORYSTATUSEX *lpStruct)
{
	if (!MEMORYSTATUSFc.cached) cacheMEMORYSTATUSFields(env, lpObject);
	env->SetIntField(lpObject, MEMORYSTATUSFc.dwLength, (jint)lpStruct->dwLength);
	env->SetIntField(lpObject, MEMORYSTATUSFc.dwMemoryLoad, (jint)lpStruct->dwMemoryLoad);
	env->SetLongField(lpObject, MEMORYSTATUSFc.ullTotalPhys, (jlong)lpStruct->ullTotalPhys);
	env->SetLongField(lpObject, MEMORYSTATUSFc.ullAvailPhys, (jlong)lpStruct->ullAvailPhys);
	env->SetLongField(lpObject, MEMORYSTATUSFc.ullTotalPageFile, (jlong)lpStruct->ullTotalPageFile);
	env->SetLongField(lpObject, MEMORYSTATUSFc.ullAvailPageFile, (jlong)lpStruct->ullAvailPageFile);
	env->SetLongField(lpObject, MEMORYSTATUSFc.ullTotalVirtual, (jlong)lpStruct->ullTotalVirtual);
	env->SetLongField(lpObject, MEMORYSTATUSFc.ullAvailVirtual, (jlong)lpStruct->ullAvailVirtual);
}


#endif


#ifndef NO_SHFILEINFO
typedef struct SHFILEINFO_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID hIcon, iIcon, dwAttributes;
} SHFILEINFO_FID_CACHE;

SHFILEINFO_FID_CACHE SHFILEINFOFc;

void cacheSHFILEINFOFields(JNIEnv *env, jobject lpObject)
{
	if (SHFILEINFOFc.cached) return;
	SHFILEINFOFc.clazz = env->GetObjectClass( lpObject);
	SHFILEINFOFc.hIcon = env->GetFieldID( SHFILEINFOFc.clazz, "hIcon", "I");
	SHFILEINFOFc.iIcon = env->GetFieldID( SHFILEINFOFc.clazz, "iIcon", "I");
	SHFILEINFOFc.dwAttributes = env->GetFieldID( SHFILEINFOFc.clazz, "dwAttributes", "I");
	SHFILEINFOFc.cached = 1;
}

SHFILEINFO *getSHFILEINFOFields(JNIEnv *env, jobject lpObject, SHFILEINFO *lpStruct)
{
	if (!SHFILEINFOFc.cached) cacheSHFILEINFOFields( env,lpObject);
	lpStruct->hIcon = (HICON)env->GetIntField( lpObject, SHFILEINFOFc.hIcon);
	lpStruct->iIcon = env->GetIntField( lpObject, SHFILEINFOFc.iIcon);
	lpStruct->dwAttributes = env->GetIntField( lpObject, SHFILEINFOFc.dwAttributes);
	return lpStruct;
}

void setSHFILEINFOFields(JNIEnv *env, jobject lpObject, SHFILEINFO *lpStruct)
{
	if (!SHFILEINFOFc.cached) cacheSHFILEINFOFields( env,lpObject);
	env->SetIntField( lpObject, SHFILEINFOFc.hIcon, (jint)lpStruct->hIcon);
	env->SetIntField( lpObject, SHFILEINFOFc.iIcon, (jint)lpStruct->iIcon);
	env->SetIntField( lpObject, SHFILEINFOFc.dwAttributes, (jint)lpStruct->dwAttributes);
}
#endif

#ifndef NO_SHFILEINFOA
typedef struct SHFILEINFOA_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID szDisplayName, szTypeName;
} SHFILEINFOA_FID_CACHE;

SHFILEINFOA_FID_CACHE SHFILEINFOAFc;

void cacheSHFILEINFOAFields(JNIEnv *env, jobject lpObject)
{
	if (SHFILEINFOAFc.cached) return;
	cacheSHFILEINFOFields( env,lpObject);
	SHFILEINFOAFc.clazz = env->GetObjectClass( lpObject);
	SHFILEINFOAFc.szDisplayName = env->GetFieldID( SHFILEINFOAFc.clazz, "szDisplayName", "[B");
	SHFILEINFOAFc.szTypeName = env->GetFieldID( SHFILEINFOAFc.clazz, "szTypeName", "[B");
	SHFILEINFOAFc.cached = 1;
}

SHFILEINFOA *getSHFILEINFOAFields(JNIEnv *env, jobject lpObject, SHFILEINFOA *lpStruct)
{
	if (!SHFILEINFOAFc.cached) cacheSHFILEINFOAFields( env,lpObject);
	lpStruct->hIcon = (HICON)env->GetIntField( lpObject, SHFILEINFOFc.hIcon);
	lpStruct->iIcon = env->GetIntField( lpObject, SHFILEINFOFc.iIcon);
	lpStruct->dwAttributes = env->GetIntField( lpObject, SHFILEINFOFc.dwAttributes);
	{
		jbyteArray lpObject1 = (jbyteArray)env->GetObjectField( lpObject, SHFILEINFOAFc.szDisplayName);
		env->GetByteArrayRegion( lpObject1, 0, sizeof(lpStruct->szDisplayName), (jbyte *)lpStruct->szDisplayName);
	}
	{
		jbyteArray lpObject1 = (jbyteArray)env->GetObjectField( lpObject, SHFILEINFOAFc.szTypeName);
		env->GetByteArrayRegion( lpObject1, 0, sizeof(lpStruct->szTypeName), (jbyte *)lpStruct->szTypeName);
	}
	return lpStruct;
}

void setSHFILEINFOAFields(JNIEnv *env, jobject lpObject, SHFILEINFOA *lpStruct)
{
	if (!SHFILEINFOAFc.cached) cacheSHFILEINFOAFields( env,lpObject);
	env->SetIntField( lpObject, SHFILEINFOFc.hIcon, (jint)lpStruct->hIcon);
	env->SetIntField( lpObject, SHFILEINFOFc.iIcon, (jint)lpStruct->iIcon);
	env->SetIntField( lpObject, SHFILEINFOFc.dwAttributes, (jint)lpStruct->dwAttributes);
	{
		jbyteArray lpObject1 = (jbyteArray)env->GetObjectField( lpObject, SHFILEINFOAFc.szDisplayName);
		env->SetByteArrayRegion( lpObject1, 0, sizeof(lpStruct->szDisplayName), (jbyte *)lpStruct->szDisplayName);
	}
	{
		jbyteArray lpObject1 = (jbyteArray)env->GetObjectField( lpObject, SHFILEINFOAFc.szTypeName);
		env->SetByteArrayRegion( lpObject1, 0, sizeof(lpStruct->szTypeName), (jbyte *)lpStruct->szTypeName);
	}
}
#endif

#ifndef NO_SHFILEINFOW
typedef struct SHFILEINFOW_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID szDisplayName, szTypeName;
} SHFILEINFOW_FID_CACHE;

SHFILEINFOW_FID_CACHE SHFILEINFOWFc;

void cacheSHFILEINFOWFields(JNIEnv *env, jobject lpObject)
{
	if (SHFILEINFOWFc.cached) return;
	cacheSHFILEINFOFields( env,lpObject);
	SHFILEINFOWFc.clazz = env->GetObjectClass( lpObject);
	SHFILEINFOWFc.szDisplayName = env->GetFieldID( SHFILEINFOWFc.clazz, "szDisplayName", "[C");
	SHFILEINFOWFc.szTypeName = env->GetFieldID( SHFILEINFOWFc.clazz, "szTypeName", "[C");
	SHFILEINFOWFc.cached = 1;
}

SHFILEINFOW *getSHFILEINFOWFields(JNIEnv *env, jobject lpObject, SHFILEINFOW *lpStruct)
{
	if (!SHFILEINFOWFc.cached) cacheSHFILEINFOWFields( env,lpObject);
	lpStruct->hIcon = (HICON)env->GetIntField( lpObject, SHFILEINFOFc.hIcon);
	lpStruct->iIcon = env->GetIntField( lpObject, SHFILEINFOFc.iIcon);
	lpStruct->dwAttributes = env->GetIntField( lpObject, SHFILEINFOFc.dwAttributes);
	{
	jcharArray lpObject1 = (jcharArray)env->GetObjectField( lpObject, SHFILEINFOWFc.szDisplayName);
	env->GetCharArrayRegion( lpObject1, 0, sizeof(lpStruct->szDisplayName) / 2, (jchar *)lpStruct->szDisplayName);
	}
	{
	jcharArray lpObject1 = (jcharArray)env->GetObjectField( lpObject, SHFILEINFOWFc.szTypeName);
	env->GetCharArrayRegion( lpObject1, 0, sizeof(lpStruct->szTypeName) / 2, (jchar *)lpStruct->szTypeName);
	}
	return lpStruct;
}

void setSHFILEINFOWFields(JNIEnv *env, jobject lpObject, SHFILEINFOW *lpStruct)
{
	if (!SHFILEINFOWFc.cached) cacheSHFILEINFOWFields( env,lpObject);
	env->SetIntField( lpObject, SHFILEINFOFc.hIcon, (jint)lpStruct->hIcon);
	env->SetIntField( lpObject, SHFILEINFOFc.iIcon, (jint)lpStruct->iIcon);
	env->SetIntField( lpObject, SHFILEINFOFc.dwAttributes, (jint)lpStruct->dwAttributes);
	{
	jcharArray lpObject1 = (jcharArray)env->GetObjectField( lpObject, SHFILEINFOWFc.szDisplayName);
	env->SetCharArrayRegion( lpObject1, 0, sizeof(lpStruct->szDisplayName) / 2, (jchar *)lpStruct->szDisplayName);
	}
	{
	jcharArray lpObject1 = (jcharArray)env->GetObjectField( lpObject, SHFILEINFOWFc.szTypeName);
	env->SetCharArrayRegion( lpObject1, 0, sizeof(lpStruct->szTypeName) / 2, (jchar *)lpStruct->szTypeName);
	}
}
#endif


#ifndef NO_FLASHWINFO
typedef struct FLASHWINFO_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID cbSize, hwnd, dwFlags, uCount, dwTimeout;
} FLASHWINFO_FID_CACHE;

FLASHWINFO_FID_CACHE FLASHWINFOFc;

void cacheFLASHWINFOFields(JNIEnv *env, jobject lpObject)
{
	if (FLASHWINFOFc.cached) return;
	FLASHWINFOFc.clazz = env->GetObjectClass(lpObject);
	FLASHWINFOFc.cbSize = env->GetFieldID(FLASHWINFOFc.clazz, "cbSize", "I");
	FLASHWINFOFc.hwnd = env->GetFieldID(FLASHWINFOFc.clazz, "hwnd", "I");
	FLASHWINFOFc.dwFlags = env->GetFieldID(FLASHWINFOFc.clazz, "dwFlags", "I");
	FLASHWINFOFc.uCount = env->GetFieldID(FLASHWINFOFc.clazz, "uCount", "I");
	FLASHWINFOFc.dwTimeout = env->GetFieldID(FLASHWINFOFc.clazz, "dwTimeout", "I");
	FLASHWINFOFc.cached = 1;
}

FLASHWINFO *getFLASHWINFOFields(JNIEnv *env, jobject lpObject, FLASHWINFO *lpStruct)
{
	if (!FLASHWINFOFc.cached) cacheFLASHWINFOFields(env, lpObject);
	lpStruct->cbSize = env->GetIntField( lpObject, FLASHWINFOFc.cbSize);
	lpStruct->hwnd = (HWND)env->GetIntField( lpObject, FLASHWINFOFc.hwnd);
	lpStruct->dwFlags = env->GetIntField( lpObject, FLASHWINFOFc.dwFlags);
	lpStruct->uCount = env->GetIntField( lpObject, FLASHWINFOFc.uCount);
	lpStruct->dwTimeout = env->GetIntField(lpObject, FLASHWINFOFc.dwTimeout);
	return lpStruct;
}

void setFLASHWINFOFields(JNIEnv *env, jobject lpObject, FLASHWINFO *lpStruct)
{
	if (!FLASHWINFOFc.cached) cacheFLASHWINFOFields(env, lpObject);
	env->SetIntField( lpObject, FLASHWINFOFc.cbSize, (jint)lpStruct->cbSize);
	env->SetIntField( lpObject, FLASHWINFOFc.hwnd, (jint)lpStruct->hwnd);
	env->SetIntField( lpObject, FLASHWINFOFc.dwFlags, (jint)lpStruct->dwFlags);
	env->SetIntField( lpObject, FLASHWINFOFc.uCount, (jint)lpStruct->uCount);
	env->SetIntField( lpObject, FLASHWINFOFc.dwTimeout, (jint)lpStruct->dwTimeout);
}
#endif

#ifndef NO_APPBARDATA
typedef struct APPBARDATA_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID cbSize, hWnd, uCallbackMessage,uEdge,rcLeft,rcTop,rcRight,rcBottom,lParam;
} APPBARDATA_FID_CACHE;

APPBARDATA_FID_CACHE APPBARDATAFc;

void cacheAPPBARDATAFields(JNIEnv *env, jobject lpObject)
{
	if (APPBARDATAFc.cached) return;
	APPBARDATAFc.clazz = env->GetObjectClass(lpObject);
	APPBARDATAFc.cbSize = env->GetFieldID(APPBARDATAFc.clazz, "cbSize", "I");
	APPBARDATAFc.hWnd = env->GetFieldID(APPBARDATAFc.clazz, "hWnd", "I");
	APPBARDATAFc.uCallbackMessage = env->GetFieldID(APPBARDATAFc.clazz, "uCallbackMessage", "I");
	APPBARDATAFc.uEdge = env->GetFieldID(APPBARDATAFc.clazz, "uEdge", "I");
	APPBARDATAFc.rcLeft = env->GetFieldID(APPBARDATAFc.clazz, "rcLeft", "I");
	APPBARDATAFc.rcTop = env->GetFieldID(APPBARDATAFc.clazz, "rcTop", "I");
	APPBARDATAFc.rcRight = env->GetFieldID(APPBARDATAFc.clazz, "rcRight", "I");
	APPBARDATAFc.rcBottom = env->GetFieldID(APPBARDATAFc.clazz, "rcBottom", "I");
	APPBARDATAFc.lParam = env->GetFieldID(APPBARDATAFc.clazz, "lParam", "I");
	APPBARDATAFc.cached = 1;
}

APPBARDATA *getAPPBARDATAFields(JNIEnv *env, jobject lpObject, APPBARDATA *lpStruct)
{
	if (!APPBARDATAFc.cached) cacheAPPBARDATAFields(env, lpObject);
	lpStruct->cbSize = env->GetIntField( lpObject, APPBARDATAFc.cbSize);
	lpStruct->hWnd = (HWND)env->GetIntField( lpObject, APPBARDATAFc.hWnd);
	lpStruct->uCallbackMessage = env->GetIntField( lpObject, APPBARDATAFc.uCallbackMessage);
	lpStruct->uEdge = env->GetIntField( lpObject, APPBARDATAFc.uEdge);
	lpStruct->rc.left = env->GetIntField( lpObject, APPBARDATAFc.rcLeft);
	lpStruct->rc.top = env->GetIntField( lpObject, APPBARDATAFc.rcTop);
	lpStruct->rc.right = env->GetIntField( lpObject, APPBARDATAFc.rcRight);
	lpStruct->rc.bottom = env->GetIntField( lpObject, APPBARDATAFc.rcBottom);
	lpStruct->lParam = env->GetIntField(lpObject, APPBARDATAFc.lParam);
	return lpStruct;
}

void setAPPBARDATAFields(JNIEnv *env, jobject lpObject, APPBARDATA *lpStruct)
{
	if (!APPBARDATAFc.cached) cacheAPPBARDATAFields(env, lpObject);
	env->SetIntField( lpObject, APPBARDATAFc.cbSize, (jint)lpStruct->cbSize);
	env->SetIntField( lpObject, APPBARDATAFc.hWnd, (jint)lpStruct->hWnd);
	env->SetIntField( lpObject, APPBARDATAFc.uCallbackMessage, (jint)lpStruct->uCallbackMessage);
	env->SetIntField( lpObject, APPBARDATAFc.uEdge, (jint)lpStruct->uEdge);
	env->SetIntField( lpObject, APPBARDATAFc.rcLeft, (jint)lpStruct->rc.left);
	env->SetIntField( lpObject, APPBARDATAFc.rcTop, (jint)lpStruct->rc.top);
	env->SetIntField( lpObject, APPBARDATAFc.rcRight, (jint)lpStruct->rc.right);
	env->SetIntField( lpObject, APPBARDATAFc.rcBottom, (jint)lpStruct->rc.bottom);
	env->SetIntField( lpObject, APPBARDATAFc.lParam, (jint)lpStruct->lParam);
}
#endif


#ifndef NO_MOUSEHOOKSTRUCT
typedef struct MOUSEHOOKSTRUCT_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID pointX, pointY, hwnd, wHitTestCode, dwExtraInfo;
} MOUSEHOOKSTRUCT_FID_CACHE;

MOUSEHOOKSTRUCT_FID_CACHE MOUSEHOOKSTRUCTFc;

void cacheMOUSEHOOKSTRUCTFields(JNIEnv *env, jobject lpObject)
{
	if (MOUSEHOOKSTRUCTFc.cached) return;
	MOUSEHOOKSTRUCTFc.clazz = env->GetObjectClass(lpObject);
	MOUSEHOOKSTRUCTFc.pointX = env->GetFieldID(MOUSEHOOKSTRUCTFc.clazz, "pointX", "I");
	MOUSEHOOKSTRUCTFc.pointY = env->GetFieldID(MOUSEHOOKSTRUCTFc.clazz, "pointY", "I");
	MOUSEHOOKSTRUCTFc.hwnd = env->GetFieldID(MOUSEHOOKSTRUCTFc.clazz, "hwnd", "I");
	MOUSEHOOKSTRUCTFc.wHitTestCode = env->GetFieldID(MOUSEHOOKSTRUCTFc.clazz, "wHitTestCode", "I");
	MOUSEHOOKSTRUCTFc.dwExtraInfo = env->GetFieldID(MOUSEHOOKSTRUCTFc.clazz, "dwExtraInfo", "I");
	MOUSEHOOKSTRUCTFc.cached = 1;
}

MOUSEHOOKSTRUCT *getMOUSEHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, MOUSEHOOKSTRUCT *lpStruct)
{
	if (!MOUSEHOOKSTRUCTFc.cached) cacheMOUSEHOOKSTRUCTFields(env, lpObject);
	lpStruct->hwnd = (HWND)env->GetIntField( lpObject, MOUSEHOOKSTRUCTFc.hwnd);
	lpStruct->pt.x = env->GetIntField( lpObject, MOUSEHOOKSTRUCTFc.pointX);
	lpStruct->pt.y = env->GetIntField( lpObject, MOUSEHOOKSTRUCTFc.pointY);
	lpStruct->wHitTestCode = env->GetIntField(lpObject, MOUSEHOOKSTRUCTFc.wHitTestCode);
	lpStruct->dwExtraInfo = env->GetIntField(lpObject, MOUSEHOOKSTRUCTFc.dwExtraInfo);
	return lpStruct;
}

void setMOUSEHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, MOUSEHOOKSTRUCT *lpStruct)
{
	if (!MOUSEHOOKSTRUCTFc.cached) cacheMOUSEHOOKSTRUCTFields(env, lpObject);
	env->SetIntField( lpObject, MOUSEHOOKSTRUCTFc.hwnd, (jint)lpStruct->hwnd);
	env->SetIntField( lpObject, MOUSEHOOKSTRUCTFc.pointX, (jint)lpStruct->pt.x);
	env->SetIntField( lpObject, MOUSEHOOKSTRUCTFc.pointY, (jint)lpStruct->pt.y);
	env->SetIntField( lpObject, MOUSEHOOKSTRUCTFc.wHitTestCode, (jint)lpStruct->wHitTestCode);
	env->SetIntField( lpObject, MOUSEHOOKSTRUCTFc.dwExtraInfo, (jint)lpStruct->dwExtraInfo);
}
#endif


#ifndef NO_MSLLHOOKSTRUCT
typedef struct MSLLHOOKSTRUCT_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID pointX, pointY, mouseData, flags, time, dwExtraInfo;
} MSLLHOOKSTRUCT_FID_CACHE;

MSLLHOOKSTRUCT_FID_CACHE MSLLHOOKSTRUCTFc;

void cacheMSLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject)
{
	if (MSLLHOOKSTRUCTFc.cached) return;
	MSLLHOOKSTRUCTFc.clazz = env->GetObjectClass(lpObject);
	MSLLHOOKSTRUCTFc.pointX = env->GetFieldID(MSLLHOOKSTRUCTFc.clazz, "pointX", "I");
	MSLLHOOKSTRUCTFc.pointY = env->GetFieldID(MSLLHOOKSTRUCTFc.clazz, "pointY", "I");
	MSLLHOOKSTRUCTFc.mouseData = env->GetFieldID(MSLLHOOKSTRUCTFc.clazz, "mouseData", "I");
	MSLLHOOKSTRUCTFc.flags = env->GetFieldID(MSLLHOOKSTRUCTFc.clazz, "flags", "I");
	MSLLHOOKSTRUCTFc.time = env->GetFieldID(MSLLHOOKSTRUCTFc.clazz, "time", "I");
	MSLLHOOKSTRUCTFc.dwExtraInfo = env->GetFieldID(MSLLHOOKSTRUCTFc.clazz, "dwExtraInfo", "I");
	MSLLHOOKSTRUCTFc.cached = 1;
}

MSLLHOOKSTRUCT *getMSLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, MSLLHOOKSTRUCT *lpStruct)
{
	if (!MSLLHOOKSTRUCTFc.cached) cacheMSLLHOOKSTRUCTFields(env, lpObject);
	lpStruct->pt.x = env->GetIntField( lpObject, MSLLHOOKSTRUCTFc.pointX);
	lpStruct->pt.y = env->GetIntField( lpObject, MSLLHOOKSTRUCTFc.pointY);
	lpStruct->mouseData = env->GetIntField(lpObject, MSLLHOOKSTRUCTFc.mouseData);
	lpStruct->flags = env->GetIntField(lpObject, MSLLHOOKSTRUCTFc.flags);
	lpStruct->time = env->GetIntField(lpObject, MSLLHOOKSTRUCTFc.time);
	lpStruct->dwExtraInfo = env->GetIntField(lpObject, MSLLHOOKSTRUCTFc.dwExtraInfo);
	return lpStruct;
}

void setMSLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, MSLLHOOKSTRUCT *lpStruct)
{
	if (!MSLLHOOKSTRUCTFc.cached) cacheMSLLHOOKSTRUCTFields(env, lpObject);
	env->SetIntField( lpObject, MSLLHOOKSTRUCTFc.pointX, (jint)lpStruct->pt.x);
	env->SetIntField( lpObject, MSLLHOOKSTRUCTFc.pointY, (jint)lpStruct->pt.y);
	env->SetIntField( lpObject, MSLLHOOKSTRUCTFc.mouseData, (jint)lpStruct->mouseData);
	env->SetIntField( lpObject, MSLLHOOKSTRUCTFc.flags, (jint)lpStruct->flags);
	env->SetIntField( lpObject, MSLLHOOKSTRUCTFc.time, (jint)lpStruct->time);
	env->SetIntField( lpObject, MSLLHOOKSTRUCTFc.dwExtraInfo, (jint)lpStruct->dwExtraInfo);
}
#endif


#ifndef NO_KBDLLHOOKSTRUCT
typedef struct KBDLLHOOKSTRUCT_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID vkCode, scanCode, flags, time, dwExtraInfo;
} KBDLLHOOKSTRUCT_FID_CACHE;

KBDLLHOOKSTRUCT_FID_CACHE KBDLLHOOKSTRUCTFc;

void cacheKBDLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject)
{
	if (KBDLLHOOKSTRUCTFc.cached) return;
	KBDLLHOOKSTRUCTFc.clazz = env->GetObjectClass(lpObject);
	KBDLLHOOKSTRUCTFc.vkCode = env->GetFieldID(KBDLLHOOKSTRUCTFc.clazz, "vkCode", "I");
	KBDLLHOOKSTRUCTFc.scanCode = env->GetFieldID(KBDLLHOOKSTRUCTFc.clazz, "scanCode", "I");
	KBDLLHOOKSTRUCTFc.flags = env->GetFieldID(KBDLLHOOKSTRUCTFc.clazz, "flags", "I");
	KBDLLHOOKSTRUCTFc.time = env->GetFieldID(KBDLLHOOKSTRUCTFc.clazz, "time", "I");
	KBDLLHOOKSTRUCTFc.dwExtraInfo = env->GetFieldID(KBDLLHOOKSTRUCTFc.clazz, "dwExtraInfo", "I");
	KBDLLHOOKSTRUCTFc.cached = 1;
}

KBDLLHOOKSTRUCT *getKBDLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, KBDLLHOOKSTRUCT *lpStruct)
{
	if (!KBDLLHOOKSTRUCTFc.cached) cacheKBDLLHOOKSTRUCTFields(env, lpObject);
	lpStruct->vkCode = env->GetIntField(lpObject, KBDLLHOOKSTRUCTFc.vkCode);
	lpStruct->scanCode = env->GetIntField(lpObject, KBDLLHOOKSTRUCTFc.scanCode);
	lpStruct->flags = env->GetIntField(lpObject, KBDLLHOOKSTRUCTFc.flags);
	lpStruct->time = env->GetIntField(lpObject, KBDLLHOOKSTRUCTFc.time);
	lpStruct->dwExtraInfo = env->GetIntField(lpObject, KBDLLHOOKSTRUCTFc.dwExtraInfo);
	return lpStruct;
}

void setKBDLLHOOKSTRUCTFields(JNIEnv *env, jobject lpObject, KBDLLHOOKSTRUCT *lpStruct)
{
	if (!KBDLLHOOKSTRUCTFc.cached) cacheKBDLLHOOKSTRUCTFields(env, lpObject);
	env->SetIntField( lpObject, KBDLLHOOKSTRUCTFc.vkCode, (jint)lpStruct->vkCode);
	env->SetIntField( lpObject, KBDLLHOOKSTRUCTFc.scanCode, (jint)lpStruct->scanCode);
	env->SetIntField( lpObject, KBDLLHOOKSTRUCTFc.flags, (jint)lpStruct->flags);
	env->SetIntField( lpObject, KBDLLHOOKSTRUCTFc.time, (jint)lpStruct->time);
	env->SetIntField( lpObject, KBDLLHOOKSTRUCTFc.dwExtraInfo, (jint)lpStruct->dwExtraInfo);
}
#endif


#ifndef NO_EVENTMSG
typedef struct EVENTMSG_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID message, paramL, paramH, time, hwnd;
} EVENTMSG_FID_CACHE;

EVENTMSG_FID_CACHE EVENTMSGFc;

void cacheEVENTMSGFields(JNIEnv *env, jobject lpObject)
{
	if (EVENTMSGFc.cached) return;
	EVENTMSGFc.clazz = env->GetObjectClass(lpObject);
	EVENTMSGFc.message = env->GetFieldID(EVENTMSGFc.clazz, "message", "I");
	EVENTMSGFc.paramL = env->GetFieldID(EVENTMSGFc.clazz, "paramL", "I");
	EVENTMSGFc.paramH = env->GetFieldID(EVENTMSGFc.clazz, "paramH", "I");
	EVENTMSGFc.time = env->GetFieldID(EVENTMSGFc.clazz, "time", "I");
	EVENTMSGFc.hwnd = env->GetFieldID(EVENTMSGFc.clazz, "hwnd", "I");
	EVENTMSGFc.cached = 1;
}

EVENTMSG *getEVENTMSGFields(JNIEnv *env, jobject lpObject, EVENTMSG *lpStruct)
{
	if (!EVENTMSGFc.cached) cacheEVENTMSGFields(env, lpObject);
	lpStruct->message = (UINT)env->GetIntField( lpObject, EVENTMSGFc.message);
	lpStruct->paramL = (UINT)env->GetIntField( lpObject, EVENTMSGFc.paramL);
	lpStruct->paramH = (UINT)env->GetIntField( lpObject, EVENTMSGFc.paramH);
	lpStruct->time = (DWORD)env->GetIntField(lpObject, EVENTMSGFc.time);
	lpStruct->hwnd = (HWND)env->GetIntField(lpObject, EVENTMSGFc.hwnd);
	return lpStruct;
}

void setEVENTMSGFields(JNIEnv *env, jobject lpObject, EVENTMSG *lpStruct)
{
	if (!EVENTMSGFc.cached) cacheEVENTMSGFields(env, lpObject);
	env->SetIntField( lpObject, EVENTMSGFc.message, (jint)lpStruct->message);
	env->SetIntField( lpObject, EVENTMSGFc.paramL, (jint)lpStruct->paramL);
	env->SetIntField( lpObject, EVENTMSGFc.paramH, (jint)lpStruct->paramH);
	env->SetIntField( lpObject, EVENTMSGFc.time, (jint)lpStruct->time);
	env->SetIntField( lpObject, EVENTMSGFc.hwnd, (jint)lpStruct->hwnd);
}
#endif

#ifndef NO_POINT
typedef struct POINT_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID x, y;
} POINT_FID_CACHE;

POINT_FID_CACHE POINTFc;

void cachePOINTFields(JNIEnv *env, jobject lpObject)
{
	if (POINTFc.cached) return;
	POINTFc.clazz = env->GetObjectClass(lpObject);
	POINTFc.x = env->GetFieldID(POINTFc.clazz, "x", "I");
	POINTFc.y = env->GetFieldID(POINTFc.clazz, "y", "I");
	POINTFc.cached = 1;
}

POINT *getPOINTFields(JNIEnv *env, jobject lpObject, POINT *lpStruct)
{
	if (!POINTFc.cached) cachePOINTFields(env, lpObject);
	lpStruct->x = env->GetIntField(lpObject, POINTFc.x);
	lpStruct->y = env->GetIntField(lpObject, POINTFc.y);
	return lpStruct;
}

void setPOINTFields(JNIEnv *env, jobject lpObject, POINT *lpStruct)
{
	if (!POINTFc.cached) cachePOINTFields(env, lpObject);
	env->SetIntField(lpObject, POINTFc.x, (jint)lpStruct->x);
	env->SetIntField(lpObject, POINTFc.y, (jint)lpStruct->y);
}
#endif

#ifndef NO_RECT
typedef struct RECT_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID left, top, right, bottom;
} RECT_FID_CACHE;

RECT_FID_CACHE RECTFc;

void cacheRECTFields(JNIEnv *env, jobject lpObject)
{
	if (RECTFc.cached) return;
	RECTFc.clazz = env->GetObjectClass(lpObject);
	RECTFc.left = env->GetFieldID( RECTFc.clazz, "left", "I");
	RECTFc.top = env->GetFieldID( RECTFc.clazz, "top", "I");
	RECTFc.right = env->GetFieldID( RECTFc.clazz, "right", "I");
	RECTFc.bottom = env->GetFieldID( RECTFc.clazz, "bottom", "I");
	RECTFc.cached = 1;
}

RECT *getRECTFields(JNIEnv *env, jobject lpObject, RECT *lpStruct)
{
	if (!RECTFc.cached) cacheRECTFields(env, lpObject);
	lpStruct->left = env->GetIntField( lpObject, RECTFc.left);
	lpStruct->top = env->GetIntField( lpObject, RECTFc.top);
	lpStruct->right = env->GetIntField( lpObject, RECTFc.right);
	lpStruct->bottom = env->GetIntField( lpObject, RECTFc.bottom);
	return lpStruct;
}

void setRECTFields(JNIEnv *env, jobject lpObject, RECT *lpStruct)
{
	if (!RECTFc.cached) cacheRECTFields(env, lpObject);
	env->SetIntField( lpObject, RECTFc.left, (jint)lpStruct->left);
	env->SetIntField( lpObject, RECTFc.top, (jint)lpStruct->top);
	env->SetIntField( lpObject, RECTFc.right, (jint)lpStruct->right);
	env->SetIntField( lpObject, RECTFc.bottom, (jint)lpStruct->bottom);
}
#endif

#ifndef NO_MCI_OPEN_PARMSA
typedef struct MCI_OPEN_PARMSA_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID dwCallback, wDeviceID, lpstrDeviceType, lpstrElementName, lpstrAlias;
} MCI_OPEN_PARMSA_FID_CACHE;

MCI_OPEN_PARMSA_FID_CACHE MCI_OPEN_PARMSAFc;

void cacheMCI_OPEN_PARMSAFields(JNIEnv *env, jobject lpObject)
{
	if (MCI_OPEN_PARMSAFc.cached) return;
	MCI_OPEN_PARMSAFc.clazz = env->GetObjectClass(lpObject);
	MCI_OPEN_PARMSAFc.dwCallback = env->GetFieldID( MCI_OPEN_PARMSAFc.clazz, "dwCallback", "I");
	MCI_OPEN_PARMSAFc.wDeviceID = env->GetFieldID( MCI_OPEN_PARMSAFc.clazz, "wDeviceID", "I");
	MCI_OPEN_PARMSAFc.lpstrDeviceType = env->GetFieldID( MCI_OPEN_PARMSAFc.clazz, "lpstrDeviceType", "I");
	MCI_OPEN_PARMSAFc.lpstrElementName = env->GetFieldID( MCI_OPEN_PARMSAFc.clazz, "lpstrElementName", "I");
	MCI_OPEN_PARMSAFc.lpstrAlias = env->GetFieldID( MCI_OPEN_PARMSAFc.clazz, "lpstrAlias", "I");
	MCI_OPEN_PARMSAFc.cached = 1;
}

MCI_OPEN_PARMSA *getMCI_OPEN_PARMSAFields(JNIEnv *env, jobject lpObject, MCI_OPEN_PARMSA *lpStruct)
{
	if (!MCI_OPEN_PARMSAFc.cached) cacheMCI_OPEN_PARMSAFields(env, lpObject);
	lpStruct->dwCallback = env->GetIntField( lpObject, MCI_OPEN_PARMSAFc.dwCallback);
	lpStruct->wDeviceID = env->GetIntField( lpObject, MCI_OPEN_PARMSAFc.wDeviceID);
	lpStruct->lpstrDeviceType = (LPCSTR)env->GetIntField( lpObject, MCI_OPEN_PARMSAFc.lpstrDeviceType);
	lpStruct->lpstrElementName = (LPCSTR)env->GetIntField( lpObject, MCI_OPEN_PARMSAFc.lpstrElementName);
	lpStruct->lpstrAlias = (LPCSTR)env->GetIntField( lpObject, MCI_OPEN_PARMSAFc.lpstrAlias);
	return lpStruct;
}

void setMCI_OPEN_PARMSAFields(JNIEnv *env, jobject lpObject, MCI_OPEN_PARMSA *lpStruct)
{
	if (!MCI_OPEN_PARMSAFc.cached) cacheMCI_OPEN_PARMSAFields(env, lpObject);
	env->SetIntField( lpObject, MCI_OPEN_PARMSAFc.dwCallback, (jint)lpStruct->dwCallback);
	env->SetIntField( lpObject, MCI_OPEN_PARMSAFc.wDeviceID, (jint)lpStruct->wDeviceID);
	env->SetIntField( lpObject, MCI_OPEN_PARMSAFc.lpstrDeviceType, (jint)lpStruct->lpstrDeviceType);
	env->SetIntField( lpObject, MCI_OPEN_PARMSAFc.lpstrElementName, (jint)lpStruct->lpstrElementName);
	env->SetIntField( lpObject, MCI_OPEN_PARMSAFc.lpstrAlias, (jint)lpStruct->lpstrAlias);
}
#endif

#ifndef NO_MCI_OPEN_PARMSW
typedef struct MCI_OPEN_PARMSW_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID dwCallback, wDeviceID, lpstrDeviceType, lpstrElementName, lpstrAlias;
} MCI_OPEN_PARMSW_FID_CACHE;

MCI_OPEN_PARMSW_FID_CACHE MCI_OPEN_PARMSWFc;

void cacheMCI_OPEN_PARMSWFields(JNIEnv *env, jobject lpObject)
{
	if (MCI_OPEN_PARMSWFc.cached) return;
	MCI_OPEN_PARMSWFc.clazz = env->GetObjectClass(lpObject);
	MCI_OPEN_PARMSWFc.dwCallback = env->GetFieldID( MCI_OPEN_PARMSWFc.clazz, "dwCallback", "I");
	MCI_OPEN_PARMSWFc.wDeviceID = env->GetFieldID( MCI_OPEN_PARMSWFc.clazz, "wDeviceID", "I");
	MCI_OPEN_PARMSWFc.lpstrDeviceType = env->GetFieldID( MCI_OPEN_PARMSWFc.clazz, "lpstrDeviceType", "I");
	MCI_OPEN_PARMSWFc.lpstrElementName = env->GetFieldID( MCI_OPEN_PARMSWFc.clazz, "lpstrElementName", "I");
	MCI_OPEN_PARMSWFc.lpstrAlias = env->GetFieldID( MCI_OPEN_PARMSWFc.clazz, "lpstrAlias", "I");
	MCI_OPEN_PARMSWFc.cached = 1;
}

MCI_OPEN_PARMSW *getMCI_OPEN_PARMSWFields(JNIEnv *env, jobject lpObject, MCI_OPEN_PARMSW *lpStruct)
{
	if (!MCI_OPEN_PARMSWFc.cached) cacheMCI_OPEN_PARMSWFields(env, lpObject);
	lpStruct->dwCallback = env->GetIntField( lpObject, MCI_OPEN_PARMSWFc.dwCallback);
	lpStruct->wDeviceID = env->GetIntField( lpObject, MCI_OPEN_PARMSWFc.wDeviceID);
	lpStruct->lpstrDeviceType = (LPWSTR)env->GetIntField( lpObject, MCI_OPEN_PARMSWFc.lpstrDeviceType);
	lpStruct->lpstrElementName = (LPWSTR)env->GetIntField( lpObject, MCI_OPEN_PARMSWFc.lpstrElementName);
	lpStruct->lpstrAlias = (LPWSTR)env->GetIntField( lpObject, MCI_OPEN_PARMSWFc.lpstrAlias);
	return lpStruct;
}

void setMCI_OPEN_PARMSWFields(JNIEnv *env, jobject lpObject, MCI_OPEN_PARMSW *lpStruct)
{
	if (!MCI_OPEN_PARMSWFc.cached) cacheMCI_OPEN_PARMSWFields(env, lpObject);
	env->SetIntField( lpObject, MCI_OPEN_PARMSWFc.dwCallback, (jint)lpStruct->dwCallback);
	env->SetIntField( lpObject, MCI_OPEN_PARMSWFc.wDeviceID, (jint)lpStruct->wDeviceID);
	env->SetIntField( lpObject, MCI_OPEN_PARMSWFc.lpstrDeviceType, (jint)lpStruct->lpstrDeviceType);
	env->SetIntField( lpObject, MCI_OPEN_PARMSWFc.lpstrElementName, (jint)lpStruct->lpstrElementName);
	env->SetIntField( lpObject, MCI_OPEN_PARMSWFc.lpstrAlias, (jint)lpStruct->lpstrAlias);
}
#endif

#ifndef NO_MCI_STATUS_PARMS
typedef struct MCI_STATUS_PARMS_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID dwCallback, dwReturn, dwItem, dwTrack;
} MCI_STATUS_PARMS_FID_CACHE;

MCI_STATUS_PARMS_FID_CACHE MCI_STATUS_PARMSFc;

void cacheMCI_STATUS_PARMSFields(JNIEnv *env, jobject lpObject)
{
	if (MCI_STATUS_PARMSFc.cached) return;
	MCI_STATUS_PARMSFc.clazz = env->GetObjectClass(lpObject);
	MCI_STATUS_PARMSFc.dwCallback = env->GetFieldID( MCI_STATUS_PARMSFc.clazz, "dwCallback", "I");
	MCI_STATUS_PARMSFc.dwReturn = env->GetFieldID( MCI_STATUS_PARMSFc.clazz, "dwReturn", "I");
	MCI_STATUS_PARMSFc.dwItem = env->GetFieldID( MCI_STATUS_PARMSFc.clazz, "dwItem", "I");
	MCI_STATUS_PARMSFc.dwTrack = env->GetFieldID( MCI_STATUS_PARMSFc.clazz, "dwTrack", "I");
	MCI_STATUS_PARMSFc.cached = 1;
}

MCI_STATUS_PARMS *getMCI_STATUS_PARMSFields(JNIEnv *env, jobject lpObject, MCI_STATUS_PARMS *lpStruct)
{
	if (!MCI_STATUS_PARMSFc.cached) cacheMCI_STATUS_PARMSFields(env, lpObject);
	lpStruct->dwCallback = env->GetIntField( lpObject, MCI_STATUS_PARMSFc.dwCallback);
	lpStruct->dwReturn = env->GetIntField( lpObject, MCI_STATUS_PARMSFc.dwReturn);
	lpStruct->dwItem = env->GetIntField( lpObject, MCI_STATUS_PARMSFc.dwItem);
	lpStruct->dwTrack = env->GetIntField( lpObject, MCI_STATUS_PARMSFc.dwTrack);
	return lpStruct;
}

void setMCI_STATUS_PARMSFields(JNIEnv *env, jobject lpObject, MCI_STATUS_PARMS *lpStruct)
{
	if (!MCI_STATUS_PARMSFc.cached) cacheMCI_STATUS_PARMSFields(env, lpObject);
	env->SetIntField( lpObject, MCI_STATUS_PARMSFc.dwCallback, (jint)lpStruct->dwCallback);
	env->SetIntField( lpObject, MCI_STATUS_PARMSFc.dwReturn, (jint)lpStruct->dwReturn);
	env->SetIntField( lpObject, MCI_STATUS_PARMSFc.dwItem, (jint)lpStruct->dwItem);
	env->SetIntField( lpObject, MCI_STATUS_PARMSFc.dwTrack, (jint)lpStruct->dwTrack);
}
#endif

#ifndef NO_MSG
typedef struct MSG_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID hwnd, message, wParam, lParam, time, x, y;
} MSG_FID_CACHE;

MSG_FID_CACHE MSGFc;

void cacheMSGFields(JNIEnv *env, jobject lpObject)
{
	if (MSGFc.cached) return;
	MSGFc.clazz = env->GetObjectClass(lpObject);
	MSGFc.hwnd = env->GetFieldID(MSGFc.clazz, "hwnd", "I");
	MSGFc.message = env->GetFieldID(MSGFc.clazz, "message", "I");
	MSGFc.wParam = env->GetFieldID(MSGFc.clazz, "wParam", "I");
	MSGFc.lParam = env->GetFieldID(MSGFc.clazz, "lParam", "I");
	MSGFc.time = env->GetFieldID(MSGFc.clazz, "time", "I");
	MSGFc.x = env->GetFieldID(MSGFc.clazz, "x", "I");
	MSGFc.y = env->GetFieldID(MSGFc.clazz, "y", "I");
	MSGFc.cached = 1;
}

MSG *getMSGFields(JNIEnv *env, jobject lpObject, MSG *lpStruct)
{
	if (!MSGFc.cached) cacheMSGFields(env, lpObject);
	lpStruct->hwnd = (HWND)env->GetIntField( lpObject, MSGFc.hwnd);
	lpStruct->message = env->GetIntField( lpObject, MSGFc.message);
	lpStruct->wParam = env->GetIntField( lpObject, MSGFc.wParam);
	lpStruct->lParam = env->GetIntField( lpObject, MSGFc.lParam);
	lpStruct->time = env->GetIntField( lpObject, MSGFc.time);
	lpStruct->pt.x = env->GetIntField( lpObject, MSGFc.x);
	lpStruct->pt.y = env->GetIntField( lpObject, MSGFc.y);
	return lpStruct;
}

void setMSGFields(JNIEnv *env, jobject lpObject, MSG *lpStruct)
{
	if (!MSGFc.cached) cacheMSGFields(env, lpObject);
	env->SetIntField(lpObject, MSGFc.hwnd, (jint)lpStruct->hwnd);
	env->SetIntField(lpObject, MSGFc.message, (jint)lpStruct->message);
	env->SetIntField(lpObject, MSGFc.wParam, (jint)lpStruct->wParam);
	env->SetIntField(lpObject, MSGFc.lParam, (jint)lpStruct->lParam);
	env->SetIntField(lpObject, MSGFc.time, (jint)lpStruct->time);
	env->SetIntField(lpObject, MSGFc.x, (jint)lpStruct->pt.x);
	env->SetIntField(lpObject, MSGFc.y, (jint)lpStruct->pt.y);
}
#endif

#ifndef NO_MIXERCAPS
typedef struct MIXERCAPS_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID wMid, wPid, vDriverVersion, fdwSupport, cDestinations;
} MIXERCAPS_FID_CACHE;

MIXERCAPS_FID_CACHE MIXERCAPSFc;

void cacheMIXERCAPSFields(JNIEnv *env, jobject lpObject)
{
	if (MIXERCAPSFc.cached) return;
	MIXERCAPSFc.clazz = env->GetObjectClass( lpObject);
	MIXERCAPSFc.wMid = env->GetFieldID( MIXERCAPSFc.clazz, "wMid", "I");
	MIXERCAPSFc.wPid = env->GetFieldID( MIXERCAPSFc.clazz, "wPid", "I");
	MIXERCAPSFc.vDriverVersion = env->GetFieldID( MIXERCAPSFc.clazz, "vDriverVersion", "I");
	MIXERCAPSFc.fdwSupport = env->GetFieldID( MIXERCAPSFc.clazz, "fdwSupport", "I");
	MIXERCAPSFc.cDestinations = env->GetFieldID( MIXERCAPSFc.clazz, "cDestinations", "I");
	MIXERCAPSFc.cached = 1;
}

MIXERCAPS *getMIXERCAPSFields(JNIEnv *env, jobject lpObject, MIXERCAPS *lpStruct)
{
	if (!MIXERCAPSFc.cached) cacheMIXERCAPSFields( env,lpObject);
	lpStruct->wMid = (WORD)env->GetIntField( lpObject, MIXERCAPSFc.wMid);
	lpStruct->wPid = (WORD)env->GetIntField( lpObject, MIXERCAPSFc.wPid);
	lpStruct->vDriverVersion = (MMVERSION)env->GetIntField( lpObject, MIXERCAPSFc.vDriverVersion);
	lpStruct->fdwSupport = (DWORD)env->GetIntField( lpObject, MIXERCAPSFc.fdwSupport);
	lpStruct->cDestinations = (DWORD)env->GetIntField( lpObject, MIXERCAPSFc.cDestinations);
	return lpStruct;
}

void setMIXERCAPSFields(JNIEnv *env, jobject lpObject, MIXERCAPS *lpStruct)
{
	if (!MIXERCAPSFc.cached) cacheMIXERCAPSFields( env,lpObject);
	env->SetIntField( lpObject, MIXERCAPSFc.wMid, (jint)lpStruct->wMid);
	env->SetIntField( lpObject, MIXERCAPSFc.wPid, (jint)lpStruct->wPid);
	env->SetIntField( lpObject, MIXERCAPSFc.vDriverVersion, (jint)lpStruct->vDriverVersion);
	env->SetIntField( lpObject, MIXERCAPSFc.fdwSupport, (jint)lpStruct->fdwSupport);
	env->SetIntField( lpObject, MIXERCAPSFc.cDestinations, (jint)lpStruct->cDestinations);
}
#endif

#ifndef NO_MIXERCAPSA
typedef struct MIXERCAPSA_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID szPname;
} MIXERCAPSA_FID_CACHE;

MIXERCAPSA_FID_CACHE MIXERCAPSAFc;

void cacheMIXERCAPSAFields(JNIEnv *env, jobject lpObject)
{
	if (MIXERCAPSAFc.cached) return;
	cacheMIXERCAPSFields( env,lpObject);
	MIXERCAPSAFc.clazz = env->GetObjectClass( lpObject);
	MIXERCAPSAFc.szPname = env->GetFieldID( MIXERCAPSAFc.clazz, "szPname", "[B");
	MIXERCAPSAFc.cached = 1;
}

MIXERCAPSA *getMIXERCAPSAFields(JNIEnv *env, jobject lpObject, MIXERCAPSA *lpStruct)
{
	if (!MIXERCAPSAFc.cached) cacheMIXERCAPSAFields( env,lpObject);
	lpStruct->wMid = (WORD)env->GetIntField( lpObject, MIXERCAPSFc.wMid);
	lpStruct->wPid = (WORD)env->GetIntField( lpObject, MIXERCAPSFc.wPid);
	lpStruct->vDriverVersion = (MMVERSION)env->GetIntField( lpObject, MIXERCAPSFc.vDriverVersion);
	lpStruct->fdwSupport = (DWORD)env->GetIntField( lpObject, MIXERCAPSFc.fdwSupport);
	lpStruct->cDestinations = (DWORD)env->GetIntField( lpObject, MIXERCAPSFc.cDestinations);
	{
		jbyteArray lpObject1 = (jbyteArray)env->GetObjectField( lpObject, MIXERCAPSAFc.szPname);
		env->GetByteArrayRegion( lpObject1, 0, sizeof(lpStruct->szPname), (jbyte *)lpStruct->szPname);
	}
	return lpStruct;
}

void setMIXERCAPSAFields(JNIEnv *env, jobject lpObject, MIXERCAPSA *lpStruct)
{
	if (!MIXERCAPSAFc.cached) cacheMIXERCAPSAFields( env,lpObject);
	env->SetIntField( lpObject, MIXERCAPSFc.wMid, (jint)lpStruct->wMid);
	env->SetIntField( lpObject, MIXERCAPSFc.wPid, (jint)lpStruct->wPid);
	env->SetIntField( lpObject, MIXERCAPSFc.vDriverVersion, (jint)lpStruct->vDriverVersion);
	env->SetIntField( lpObject, MIXERCAPSFc.fdwSupport, (jint)lpStruct->fdwSupport);
	env->SetIntField( lpObject, MIXERCAPSFc.cDestinations, (jint)lpStruct->cDestinations);
	{
		jbyteArray lpObject1 = (jbyteArray)env->GetObjectField( lpObject, MIXERCAPSAFc.szPname);
		env->SetByteArrayRegion( lpObject1, 0, sizeof(lpStruct->szPname), (jbyte *)lpStruct->szPname);
	}
}
#endif

#ifndef NO_MIXERCAPSW
typedef struct MIXERCAPSW_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID szPname;
} MIXERCAPSW_FID_CACHE;

MIXERCAPSW_FID_CACHE MIXERCAPSWFc;

void cacheMIXERCAPSWFields(JNIEnv *env, jobject lpObject)
{
	if (MIXERCAPSWFc.cached) return;
	cacheMIXERCAPSFields( env,lpObject);
	MIXERCAPSWFc.clazz = env->GetObjectClass( lpObject);
	MIXERCAPSWFc.szPname = env->GetFieldID( MIXERCAPSWFc.clazz, "szPname", "[C");
	MIXERCAPSWFc.cached = 1;
}

MIXERCAPSW *getMIXERCAPSWFields(JNIEnv *env, jobject lpObject, MIXERCAPSW *lpStruct)
{
	if (!MIXERCAPSWFc.cached) cacheMIXERCAPSWFields( env,lpObject);
	lpStruct->wMid = (WORD)env->GetIntField( lpObject, MIXERCAPSFc.wMid);
	lpStruct->wPid = (WORD)env->GetIntField( lpObject, MIXERCAPSFc.wPid);
	lpStruct->vDriverVersion = (MMVERSION)env->GetIntField( lpObject, MIXERCAPSFc.vDriverVersion);
	lpStruct->fdwSupport = (DWORD)env->GetIntField( lpObject, MIXERCAPSFc.fdwSupport);
	lpStruct->cDestinations = (DWORD)env->GetIntField( lpObject, MIXERCAPSFc.cDestinations);
	{
	jcharArray lpObject1 = (jcharArray)env->GetObjectField( lpObject, MIXERCAPSWFc.szPname);
	env->GetCharArrayRegion( lpObject1, 0, sizeof(lpStruct->szPname) / 2, (jchar *)lpStruct->szPname);
	}
	return lpStruct;
}

void setMIXERCAPSWFields(JNIEnv *env, jobject lpObject, MIXERCAPSW *lpStruct)
{
	if (!MIXERCAPSWFc.cached) cacheMIXERCAPSWFields( env,lpObject);
	env->SetIntField( lpObject, MIXERCAPSFc.wMid, (jint)lpStruct->wMid);
	env->SetIntField( lpObject, MIXERCAPSFc.wPid, (jint)lpStruct->wPid);
	env->SetIntField( lpObject, MIXERCAPSFc.vDriverVersion, (jint)lpStruct->vDriverVersion);
	env->SetIntField( lpObject, MIXERCAPSFc.fdwSupport, (jint)lpStruct->fdwSupport);
	env->SetIntField( lpObject, MIXERCAPSFc.cDestinations, (jint)lpStruct->cDestinations);
	{
	jcharArray lpObject1 = (jcharArray)env->GetObjectField( lpObject, MIXERCAPSWFc.szPname);
	env->SetCharArrayRegion( lpObject1, 0, sizeof(lpStruct->szPname) / 2, (jchar *)lpStruct->szPname);
	}
}
#endif

#ifndef NO_MIXERLINE
typedef struct MIXERLINE_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID cbStruct, dwDestination, dwSource, dwLineID, fdwLine, dwUser, 
	dwComponentType, cChannels, cConnections, cControls;
} MIXERLINE_FID_CACHE;

MIXERLINE_FID_CACHE MIXERLINEFc;

void cacheMIXERLINEFields(JNIEnv *env, jobject lpObject)
{
	if (MIXERLINEFc.cached) return;
	MIXERLINEFc.clazz = env->GetObjectClass( lpObject);
	MIXERLINEFc.cbStruct = env->GetFieldID( MIXERLINEFc.clazz, "cbStruct", "I");
	MIXERLINEFc.dwDestination = env->GetFieldID( MIXERLINEFc.clazz, "dwDestination", "I");
	MIXERLINEFc.dwSource = env->GetFieldID( MIXERLINEFc.clazz, "dwSource", "I");
	MIXERLINEFc.dwLineID = env->GetFieldID( MIXERLINEFc.clazz, "dwLineID", "I");
	MIXERLINEFc.fdwLine = env->GetFieldID( MIXERLINEFc.clazz, "fdwLine", "I");
	MIXERLINEFc.dwUser = env->GetFieldID( MIXERLINEFc.clazz, "dwUser", "I");
	MIXERLINEFc.dwComponentType = env->GetFieldID( MIXERLINEFc.clazz, "dwComponentType", "I");
	MIXERLINEFc.cChannels = env->GetFieldID( MIXERLINEFc.clazz, "cChannels", "I");
	MIXERLINEFc.cConnections = env->GetFieldID( MIXERLINEFc.clazz, "cConnections", "I");
	MIXERLINEFc.cControls = env->GetFieldID( MIXERLINEFc.clazz, "cControls", "I");
	MIXERLINEFc.cached = 1;
}

MIXERLINE *getMIXERLINEFields(JNIEnv *env, jobject lpObject, MIXERLINE *lpStruct)
{
	if (!MIXERLINEFc.cached) cacheMIXERLINEFields( env,lpObject);
	lpStruct->cbStruct = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cbStruct);
	lpStruct->dwDestination = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwDestination);
	lpStruct->dwSource = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwSource);
	lpStruct->dwLineID = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwLineID);
	lpStruct->fdwLine = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.fdwLine);
	lpStruct->dwUser = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwUser);
	lpStruct->dwComponentType = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwComponentType);
	lpStruct->cChannels = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cChannels);
	lpStruct->cConnections = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cConnections);
	lpStruct->cControls = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cControls);
	return lpStruct;
}

void setMIXERLINEFields(JNIEnv *env, jobject lpObject, MIXERLINE *lpStruct)
{
	if (!MIXERLINEFc.cached) cacheMIXERLINEFields( env,lpObject);
	env->SetIntField( lpObject, MIXERLINEFc.cbStruct, (jint)lpStruct->cbStruct);
	env->SetIntField( lpObject, MIXERLINEFc.dwDestination, (jint)lpStruct->dwDestination);
	env->SetIntField( lpObject, MIXERLINEFc.dwSource, (jint)lpStruct->dwSource);
	env->SetIntField( lpObject, MIXERLINEFc.dwLineID, (jint)lpStruct->dwLineID);
	env->SetIntField( lpObject, MIXERLINEFc.fdwLine, (jint)lpStruct->fdwLine);
	env->SetIntField( lpObject, MIXERLINEFc.dwUser, (jint)lpStruct->dwUser);
	env->SetIntField( lpObject, MIXERLINEFc.dwComponentType, (jint)lpStruct->dwComponentType);
	env->SetIntField( lpObject, MIXERLINEFc.cChannels, (jint)lpStruct->cChannels);
	env->SetIntField( lpObject, MIXERLINEFc.cConnections, (jint)lpStruct->cConnections);
	env->SetIntField( lpObject, MIXERLINEFc.cControls, (jint)lpStruct->cControls);
}
#endif

#ifndef NO_MIXERLINEA
typedef struct MIXERLINEA_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID szShortName, szName;
} MIXERLINEA_FID_CACHE;

MIXERLINEA_FID_CACHE MIXERLINEAFc;

void cacheMIXERLINEAFields(JNIEnv *env, jobject lpObject)
{
	if (MIXERLINEAFc.cached) return;
	cacheMIXERLINEFields( env,lpObject);
	MIXERLINEAFc.clazz = env->GetObjectClass( lpObject);
	MIXERLINEAFc.szShortName = env->GetFieldID( MIXERLINEAFc.clazz, "szShortName", "[B");
	MIXERLINEAFc.szName = env->GetFieldID( MIXERLINEAFc.clazz, "szName", "[B");
	MIXERLINEAFc.cached = 1;
}

MIXERLINEA *getMIXERLINEAFields(JNIEnv *env, jobject lpObject, MIXERLINEA *lpStruct)
{
	if (!MIXERLINEAFc.cached) cacheMIXERLINEAFields( env,lpObject);
	lpStruct->cbStruct = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cbStruct);
	lpStruct->dwDestination = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwDestination);
	lpStruct->dwSource = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwSource);
	lpStruct->dwLineID = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwLineID);
	lpStruct->fdwLine = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.fdwLine);
	lpStruct->dwUser = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwUser);
	lpStruct->dwComponentType = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwComponentType);
	lpStruct->cChannels = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cChannels);
	lpStruct->cConnections = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cConnections);
	lpStruct->cControls = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cControls);
	{
		jbyteArray lpObject1 = (jbyteArray)env->GetObjectField( lpObject, MIXERLINEAFc.szShortName);
		env->GetByteArrayRegion( lpObject1, 0, sizeof(lpStruct->szShortName), (jbyte *)lpStruct->szShortName);
	}
	{
		jbyteArray lpObject1 = (jbyteArray)env->GetObjectField( lpObject, MIXERLINEAFc.szName);
		env->GetByteArrayRegion( lpObject1, 0, sizeof(lpStruct->szName), (jbyte *)lpStruct->szName);
	}
	return lpStruct;
}

void setMIXERLINEAFields(JNIEnv *env, jobject lpObject, MIXERLINEA *lpStruct)
{
	if (!MIXERLINEAFc.cached) cacheMIXERLINEAFields( env,lpObject);
	env->SetIntField( lpObject, MIXERLINEFc.cbStruct, (jint)lpStruct->cbStruct);
	env->SetIntField( lpObject, MIXERLINEFc.dwDestination, (jint)lpStruct->dwDestination);
	env->SetIntField( lpObject, MIXERLINEFc.dwSource, (jint)lpStruct->dwSource);
	env->SetIntField( lpObject, MIXERLINEFc.dwLineID, (jint)lpStruct->dwLineID);
	env->SetIntField( lpObject, MIXERLINEFc.fdwLine, (jint)lpStruct->fdwLine);
	env->SetIntField( lpObject, MIXERLINEFc.dwUser, (jint)lpStruct->dwUser);
	env->SetIntField( lpObject, MIXERLINEFc.dwComponentType, (jint)lpStruct->dwComponentType);
	env->SetIntField( lpObject, MIXERLINEFc.cChannels, (jint)lpStruct->cChannels);
	env->SetIntField( lpObject, MIXERLINEFc.cConnections, (jint)lpStruct->cConnections);
	env->SetIntField( lpObject, MIXERLINEFc.cControls, (jint)lpStruct->cControls);
	{
		jbyteArray lpObject1 = (jbyteArray)env->GetObjectField( lpObject, MIXERLINEAFc.szShortName);
		env->SetByteArrayRegion( lpObject1, 0, sizeof(lpStruct->szShortName), (jbyte *)lpStruct->szShortName);
	}
	{
		jbyteArray lpObject1 = (jbyteArray)env->GetObjectField( lpObject, MIXERLINEAFc.szName);
		env->SetByteArrayRegion( lpObject1, 0, sizeof(lpStruct->szName), (jbyte *)lpStruct->szName);
	}
}
#endif

#ifndef NO_MIXERLINEW
typedef struct MIXERLINEW_FID_CACHE {
	int cached;
	jclass clazz;
	jfieldID szShortName, szName;
} MIXERLINEW_FID_CACHE;

MIXERLINEW_FID_CACHE MIXERLINEWFc;

void cacheMIXERLINEWFields(JNIEnv *env, jobject lpObject)
{
	if (MIXERLINEWFc.cached) return;
	cacheMIXERLINEFields( env,lpObject);
	MIXERLINEWFc.clazz = env->GetObjectClass( lpObject);
	MIXERLINEWFc.szShortName = env->GetFieldID( MIXERLINEWFc.clazz, "szShortName", "[C");
	MIXERLINEWFc.szName = env->GetFieldID( MIXERLINEWFc.clazz, "szName", "[C");
	MIXERLINEWFc.cached = 1;
}

MIXERLINEW *getMIXERLINEWFields(JNIEnv *env, jobject lpObject, MIXERLINEW *lpStruct)
{
	if (!MIXERLINEWFc.cached) cacheMIXERLINEWFields( env,lpObject);
	lpStruct->cbStruct = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cbStruct);
	lpStruct->dwDestination = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwDestination);
	lpStruct->dwSource = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwSource);
	lpStruct->dwLineID = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwLineID);
	lpStruct->fdwLine = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.fdwLine);
	lpStruct->dwUser = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwUser);
	lpStruct->dwComponentType = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.dwComponentType);
	lpStruct->cChannels = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cChannels);
	lpStruct->cConnections = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cConnections);
	lpStruct->cControls = (DWORD)env->GetIntField( lpObject, MIXERLINEFc.cControls);
	{
	jcharArray lpObject1 = (jcharArray)env->GetObjectField( lpObject, MIXERLINEWFc.szShortName);
	env->GetCharArrayRegion( lpObject1, 0, sizeof(lpStruct->szShortName) / 2, (jchar *)lpStruct->szShortName);
	}
	{
	jcharArray lpObject1 = (jcharArray)env->GetObjectField( lpObject, MIXERLINEWFc.szName);
	env->GetCharArrayRegion( lpObject1, 0, sizeof(lpStruct->szName) / 2, (jchar *)lpStruct->szName);
	}
	return lpStruct;
}

void setMIXERLINEWFields(JNIEnv *env, jobject lpObject, MIXERLINEW *lpStruct)
{
	if (!MIXERLINEWFc.cached) cacheMIXERLINEWFields( env,lpObject);
	env->SetIntField( lpObject, MIXERLINEFc.cbStruct, (jint)lpStruct->cbStruct);
	env->SetIntField( lpObject, MIXERLINEFc.dwDestination, (jint)lpStruct->dwDestination);
	env->SetIntField( lpObject, MIXERLINEFc.dwSource, (jint)lpStruct->dwSource);
	env->SetIntField( lpObject, MIXERLINEFc.dwLineID, (jint)lpStruct->dwLineID);
	env->SetIntField( lpObject, MIXERLINEFc.fdwLine, (jint)lpStruct->fdwLine);
	env->SetIntField( lpObject, MIXERLINEFc.dwUser, (jint)lpStruct->dwUser);
	env->SetIntField( lpObject, MIXERLINEFc.dwComponentType, (jint)lpStruct->dwComponentType);
	env->SetIntField( lpObject, MIXERLINEFc.cChannels, (jint)lpStruct->cChannels);
	env->SetIntField( lpObject, MIXERLINEFc.cConnections, (jint)lpStruct->cConnections);
	env->SetIntField( lpObject, MIXERLINEFc.cControls, (jint)lpStruct->cControls);
	{
	jcharArray lpObject1 = (jcharArray)env->GetObjectField( lpObject, MIXERLINEWFc.szShortName);
	env->SetCharArrayRegion( lpObject1, 0, sizeof(lpStruct->szShortName) / 2, (jchar *)lpStruct->szShortName);
	}
	{
	jcharArray lpObject1 = (jcharArray)env->GetObjectField( lpObject, MIXERLINEWFc.szName);
	env->SetCharArrayRegion( lpObject1, 0, sizeof(lpStruct->szName) / 2, (jchar *)lpStruct->szName);
	}
}
#endif
