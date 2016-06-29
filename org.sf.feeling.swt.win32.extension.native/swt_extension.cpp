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

#include <swt.h>
#include <swt_extension.h>
#include <swt_extension_structs.h>
#include <swt_extension_registry.h>
#include <swt_extension_function.h>

extern "C" {

#define Swt_extension_NATIVE(func) Java_org_sf_feeling_swt_win32_internal_extension_Extension_##func
#define Swt_extension2_NATIVE(func) Java_org_sf_feeling_swt_win32_internal_extension_Extension2_##func
#define Swt_registryKey_NATIVE(func) Java_org_sf_feeling_swt_win32_extension_registry_RegistryKey_##func
#define Swt_registryKeyIterator_NATIVE(func) Java_org_sf_feeling_swt_win32_extension_registry_KeyIterator_##func
#define Swt_registryValueIterator_NATIVE(func) Java_org_sf_feeling_swt_win32_extension_registry_ValueIterator_##func



#ifndef NO_ShowCursor
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(ShowCursor)
	(JNIEnv *env, jclass that, jboolean arg0)
{
	jboolean rc = 0;
	rc = (jboolean)ShowCursor(arg0);
	return rc;
}
#endif


#ifndef NO_GetDiskFreeSpace
JNIEXPORT void JNICALL Swt_extension_NATIVE(GetDiskFreeSpace)
	(JNIEnv *env, jobject that, jstring drive, jobject diskFreeSpace)
{
	DISKFREESPACE _diskFreeSpace, *lpDiskFreeSpace=NULL;
	char *lpdrive=NULL;
	if (drive) if ((lpdrive = jstringToNative(env,drive))==NULL) goto fail;
	if (diskFreeSpace) if ((lpDiskFreeSpace = getDISKFREESPACEFields(env, diskFreeSpace, &_diskFreeSpace)) == NULL) goto fail;

	ULARGE_INTEGER freeBytesAvailable;
	ULARGE_INTEGER totalNumberOfBytes;
	ULARGE_INTEGER totalNumberOfFreeBytes;
	if (!GetDiskFreeSpaceEx(lpdrive,&freeBytesAvailable,&totalNumberOfBytes,&totalNumberOfFreeBytes)) {
		int lastError = GetLastError();
		lpDiskFreeSpace->lastError = lastError;
		lpDiskFreeSpace->returnValue = JNI_FALSE;
	}
	else {
		lpDiskFreeSpace->lastError = 0;
		lpDiskFreeSpace->returnValue = JNI_TRUE;
		lpDiskFreeSpace->freeBytesAvailable = (_int64)freeBytesAvailable.QuadPart;
		lpDiskFreeSpace->totalNumberOfBytes = (_int64)totalNumberOfBytes.QuadPart;
		lpDiskFreeSpace->totalNumberOfFreeBytes = (_int64)totalNumberOfFreeBytes.QuadPart;
	}
	if (diskFreeSpace && lpDiskFreeSpace) setDISKFREESPACEFields(env, diskFreeSpace, lpDiskFreeSpace);
	fail:
	if(lpdrive!=NULL)delete[] lpdrive;
	return;
}
#endif

#ifndef NO_GetSystemInfo
JNIEXPORT void JNICALL Swt_extension_NATIVE(GetSystemInfo)
	(JNIEnv *env, jclass that,  jobject systemInfo)
{
	SYSTEM_INFO _sYSTEM_INFO, *lpSYSTEM_INFO=NULL;
	if (systemInfo) if ((lpSYSTEM_INFO = getSYSTEM_INFOFields(env, systemInfo, &_sYSTEM_INFO)) == NULL) goto fail;
	GetSystemInfo(lpSYSTEM_INFO);
	if (systemInfo && lpSYSTEM_INFO) setSYSTEM_INFOFields(env, systemInfo, lpSYSTEM_INFO);
	fail:
		return;
}
#endif

#ifndef NO_GlobalMemoryStatus
JNIEXPORT void JNICALL Swt_extension_NATIVE(GlobalMemoryStatus)
	(JNIEnv *env, jclass that,  jobject memoryStatus)
{
	MEMORYSTATUSEX _mEMORYSTATUS, *lpMEMORYSTATUS=NULL;
	if (memoryStatus) if ((lpMEMORYSTATUS = getMEMORYSTATUSFields(env, memoryStatus, &_mEMORYSTATUS)) == NULL) goto fail;
	GlobalMemoryStatusEx(lpMEMORYSTATUS);
	if (memoryStatus && lpMEMORYSTATUS) setMEMORYSTATUSFields(env, memoryStatus, lpMEMORYSTATUS);
	fail:
		return;
}
#endif


#ifndef NO_GetDriveType
JNIEXPORT jint JNICALL Swt_extension_NATIVE(GetDriveType)
	(JNIEnv *env, jclass that, jstring drive)
{
	jint rc = 0;
	char *lpdrive=NULL;
	if (drive) if ((lpdrive = jstringToNative(env,drive))==NULL) goto fail;
	rc = (jint)GetDriveType(lpdrive);
	fail:
	if(lpdrive!=NULL)
		delete[] lpdrive;
	return rc;
}
#endif

#ifndef NO_GetLogicalDrives
JNIEXPORT jobjectArray JNICALL Swt_extension_NATIVE(GetLogicalDrives)
	(JNIEnv *env, jclass that)
{
	DWORD dStrLength;
	char szDrives[256];
	jobjectArray rc = NULL;

	memset(szDrives,'\0',sizeof(szDrives));
	dStrLength = GetLogicalDriveStrings(sizeof(szDrives),szDrives);

	if (dStrLength) {
		int i, nDriveCount, nOffset = 0;
		nDriveCount = dStrLength/4;
		rc = env->NewObjectArray(nDriveCount,env->FindClass("java/lang/String"),nativeTojstring(env,""));

		for (i = 0; i < nDriveCount; i++){
				env->SetObjectArrayElement(rc, i, nativeTojstring(env,szDrives));
				szDrives[0] = szDrives[nOffset += 4];
		}
	}

	return rc;
}
#endif



#ifndef NO_GetSystemDirectory
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetSystemDirectory)
	(JNIEnv *env, jclass that)
{
	char systemDirectory[256];
	jstring rc = NULL;
	memset(systemDirectory,'\0',sizeof(systemDirectory));
	GetSystemDirectory(systemDirectory,sizeof(systemDirectory));
	rc = nativeTojstring(env,systemDirectory);
	return  rc;
}
#endif



#ifndef NO_GetVolumeLabel
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetVolumeLabel)
	(JNIEnv *env, jclass that, jstring drive)
{
	jstring rc = NULL;
	char szVolumeName[256];
	char *lpdrive = NULL;
	DWORD maxCompLen = 0, fileSystemFlags = 0;
	if (drive) if ((lpdrive = jstringToNative(env,drive))==NULL) goto fail;
	memset(szVolumeName,'\0',sizeof(szVolumeName));
	GetVolumeInformation(lpdrive,szVolumeName,sizeof(szVolumeName),NULL,&maxCompLen,&fileSystemFlags,NULL,0);
	rc = nativeTojstring(env,szVolumeName);
	fail:
	if(lpdrive!=NULL)
		delete[] lpdrive;
	return  rc;
}
#endif

#ifndef NO_GetVolumeSerialNumber
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetVolumeSerialNumber)
	(JNIEnv *env, jclass that, jstring drive)
{
	char cSerialNumber[16];
	DWORD dwSerialNumber;
	char *lpdrive = NULL;
	DWORD maxCompLen = 0, fileSystemFlags = 0;
	jstring rc = NULL;
	if (drive) if ((lpdrive = jstringToNative(env,drive))==NULL) goto fail;
	if(GetVolumeInformation(lpdrive,NULL,0,&dwSerialNumber,&maxCompLen,&fileSystemFlags,NULL,0)==0)
		rc = nativeTojstring(env,"0");
	else 
	{	sprintf(cSerialNumber, "%u", dwSerialNumber);
		rc = nativeTojstring(env,cSerialNumber);
	}
	fail:
	if(lpdrive!=NULL)
		delete[] lpdrive;
	return  rc;
}
#endif

#ifndef NO_SetVolumeLabel
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(SetVolumeLabel)
	(JNIEnv *env, jclass that, jstring drive, jstring label)
{
	jboolean rc = false;
	char *lpdrive = NULL, *lplabel = NULL;
	if (drive) if ((lpdrive = jstringToNative(env,drive))==NULL) goto fail;
	if (label) if ((lplabel = jstringToNative(env,label))==NULL) goto fail;
	rc = (jboolean)SetVolumeLabel(lpdrive,lplabel);
	fail:
	if(lpdrive!=NULL)
		delete[] lpdrive;
	if(lplabel!=NULL)
		delete[] lplabel;
	return rc;
}
#endif

#ifndef NO_GetCurrentDirectory
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetCurrentDirectory)
	(JNIEnv *env, jclass that)
{
	jstring rc = NULL;
	char szCurrentDir[_MAX_PATH];
	memset(szCurrentDir,'\0',sizeof(szCurrentDir));
	GetCurrentDirectory(_MAX_PATH,szCurrentDir);
	rc = nativeTojstring(env,szCurrentDir);
	return rc;
}
#endif

#ifndef NO_SetCurrentDirectory
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(SetCurrentDirectory)
	(JNIEnv *env, jclass that, jstring directory)
{
	jboolean rc = false;
	char *str = NULL;
	if (directory) if ((str = jstringToNative(env,directory))==NULL) goto fail;
	rc = (jboolean)SetCurrentDirectory(str);
	fail:
	if(str!=NULL)
		delete[] str;
	return rc;
}
#endif

#ifndef NO_ExistsKey
JNIEXPORT jboolean JNICALL Swt_registryKey_NATIVE(ExistsKey)
	(JNIEnv *env, jobject obj)
{
	bool bKeyExists = false;
	int nRootKey = getRootKey(env, obj);
	jclass CRegistryKey = env->GetObjectClass(obj);
	jfieldID fid_path = env->GetFieldID(CRegistryKey, "path", jfd_String);
	if(fid_path == NULL)return bKeyExists; 
	jstring path = (jstring)env->GetObjectField(obj, fid_path);
	if(path == NULL) return bKeyExists; 
	char *szPath = NULL;
	if (path) if ((szPath = jstringToNative(env,path))==NULL) goto fail;
	HKEY hkey;
	if(RegOpenKeyEx((HKEY)nRootKey, szPath, 0, KEY_READ, &hkey) == ERROR_SUCCESS) {
		RegCloseKey(hkey);
		bKeyExists = true;
	}
	fail:
	if(szPath!=NULL)
		delete[] szPath;
	return bKeyExists;
}
#endif

#ifndef NO_CreateKey
JNIEXPORT void JNICALL Swt_registryKey_NATIVE(CreateKey)
	(JNIEnv *env, jobject obj)
{
	 int nRootKey = getRootKey(env, obj);
	 jclass CRegistryKey = env->GetObjectClass(obj);
	 jfieldID fid_path = env->GetFieldID(CRegistryKey, "path", jfd_String);
	 if(fid_path == NULL) return; 

	 jstring path = (jstring)env->GetObjectField(obj, fid_path);
	 if(path == NULL) return; 

	 char *szPath = NULL;
	 if (path) if ((szPath = jstringToNative(env,path))==NULL) goto fail;

	 HKEY hkey;
	 DWORD dwDisposition;
	 if(RegCreateKeyEx((HKEY)nRootKey, szPath, 0, NULL, REG_OPTION_NON_VOLATILE, KEY_ALL_ACCESS, NULL, &hkey, &dwDisposition) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Error creating registry key");
	 }
	 else {
		  RegCloseKey(hkey);
		  if(dwDisposition == REG_OPENED_EXISTING_KEY) {
				env->ThrowNew(env->FindClass(jcd_RegistryException), "Attempted to create existing key");
		  }
	 }
	 fail:
	 if(szPath!=NULL)
		delete[] szPath;
	 return;
}
#endif


#ifndef NO_DeleteKey
JNIEXPORT void JNICALL Swt_registryKey_NATIVE(DeleteKey)
	(JNIEnv *env, jobject obj)
{
	 int nRootKey = getRootKey(env, obj);

	 jclass CRegistryKey = env->GetObjectClass(obj);

	 jfieldID fid_path = env->GetFieldID(CRegistryKey, "path", jfd_String);
	 if(fid_path == NULL) return; 

	 jstring path = (jstring)env->GetObjectField(obj, fid_path);
	 if(path == NULL) return; 

	 char *szPath = NULL;
	 if (path) if ((szPath = jstringToNative(env,path))==NULL) goto fail;

	 if(SHDeleteKey((HKEY)nRootKey, szPath) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Error deleting registry key");
	 }
	 fail:
	 if(szPath!=NULL)
		delete[] szPath;
	 return;
}
#endif

#ifndef NO_HasSubkeys
JNIEXPORT jboolean JNICALL Swt_registryKey_NATIVE(HasSubkeys)
	(JNIEnv *env, jobject obj)
{
	 bool bHasSubkeys = false;

	 int nRootKey = getRootKey(env, obj);

	 jclass CRegistryKey = env->GetObjectClass(obj);

	 jfieldID fid_path = env->GetFieldID(CRegistryKey, "path", jfd_String);
	 if(fid_path == NULL) return bHasSubkeys;
	 jstring path = (jstring)env->GetObjectField(obj, fid_path);
	 if(path == NULL) return bHasSubkeys; 
	 char *szPath = NULL;
	 if (path) szPath = jstringToNative(env,path);
	 if(szPath == NULL) szPath = "";
	 HKEY hkey;
	 if(RegOpenKeyEx((HKEY)nRootKey, szPath, 0, KEY_READ, &hkey) != ERROR_SUCCESS) {
		  bHasSubkeys = false;
	 }
	 else {
		  DWORD dwCount;
		  DWORD dwMaxLen;
		  if(RegQueryInfoKey(hkey, NULL, NULL, NULL, (LPDWORD)&dwCount, (LPDWORD)&dwMaxLen, NULL, NULL, NULL, NULL, NULL, NULL) != ERROR_SUCCESS) {
				bHasSubkeys = false;
		  }
		  else if(dwCount > 0) {
				bHasSubkeys = true;
		  }

		  RegCloseKey(hkey);
	 }
	 if(szPath!=NULL)
		delete[] szPath;
	 return bHasSubkeys;
}
#endif

#ifndef NO_HasKeyValues
JNIEXPORT jboolean JNICALL Swt_registryKey_NATIVE(HasKeyValues)
	(JNIEnv *env, jobject obj)
{
	 bool bHasValues = false;

	 int nRootKey = getRootKey(env, obj);

	 jclass CRegistryKey = env->GetObjectClass(obj);

	 jfieldID fid_path = env->GetFieldID(CRegistryKey, "path", jfd_String);
	 if(fid_path == NULL) return bHasValues;

	 jstring path = (jstring)env->GetObjectField(obj, fid_path);
	 if(path == NULL) return bHasValues;

	 char *szPath = NULL;
	 if (path) if ((szPath = jstringToNative(env,path))==NULL) goto fail;

	 HKEY hkey;
	 if(RegOpenKeyEx((HKEY)nRootKey, szPath, 0, KEY_READ, &hkey) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Error opening registry key");
	 }
	 else {
		  DWORD dwCount;
		  DWORD dwMaxLen;
		  if(RegQueryInfoKey(hkey, NULL, NULL, NULL, NULL, NULL, (LPDWORD)&dwCount, (LPDWORD)&dwMaxLen, NULL, NULL, NULL, NULL) != ERROR_SUCCESS) {
				bHasValues = false;
		  }
		  RegCloseKey(hkey);
		  bHasValues = true;
	 }
	fail:
	if(szPath!=NULL)
		delete[] szPath;
	 return bHasValues;
}
#endif

#ifndef NO_HasKeyValue
JNIEXPORT jboolean JNICALL Swt_registryKey_NATIVE(HasKeyValue)
	(JNIEnv *env, jobject obj, jstring name)
{
	 bool bHasValue = false;

	 int nRootKey = getRootKey(env, obj);

	 jclass CRegistryKey = env->GetObjectClass(obj);

	 jfieldID fid_path = env->GetFieldID(CRegistryKey, "path", jfd_String);
	 if(fid_path == NULL)  return bHasValue;

	 jstring path = (jstring)env->GetObjectField(obj, fid_path);
	 if(path == NULL)  return bHasValue;

	 char *szPath = NULL;
	 if (path) if ((szPath = jstringToNative(env,path))==NULL) goto fail;

	 HKEY hkey;
	 if(RegOpenKeyEx((HKEY)nRootKey, szPath, 0, KEY_READ, &hkey) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Error opening registry key");
	 } 
	 else {
		  char *szName = NULL;
		  if (name) szName = jstringToNative(env,name);
		  if(szName!=NULL){
		  	if(RegQueryValueEx(hkey, szName, NULL, NULL, NULL, NULL) != ERROR_SUCCESS) {
				bHasValue = false;
		  	} 
		 	else {
				bHasValue = true;
		  	}
		  	delete[] szName; 
		  }
		  RegCloseKey(hkey);
	 } 
	 fail:
	 if(szPath!=NULL)
		delete[] szPath;
	 return bHasValue;
} 
#endif

#ifndef NO_GetKeyValue
JNIEXPORT jobject JNICALL Swt_registryKey_NATIVE(GetKeyValue)
	(JNIEnv *env, jobject obj, jstring name)
{
	 
	 int nRootKey = getRootKey(env, obj);

	 jclass CRegistryKey = env->GetObjectClass(obj);

	 jfieldID fid_path = env->GetFieldID(CRegistryKey, "path", jfd_String);
	 if(fid_path == NULL) return NULL;

	 jstring path = (jstring)env->GetObjectField(obj, fid_path);
	 if(path == NULL) return NULL;

	 char *szPath = NULL;
	 if (path) if ((szPath = jstringToNative(env,path))==NULL){
	 	 env->ThrowNew(env->FindClass(jcd_RegistryException), "Error registry key path");
		 return NULL;
	 }

	 HKEY hkey;
	 if(RegOpenKeyEx((HKEY)nRootKey, szPath, 0, KEY_READ, &hkey) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Error opening registry key");
		  delete[] szPath;
		  return NULL;
	 } 
	 else {
	 	  delete[] szPath;
		  char *szName = jstringToNative(env,name);
		  if(szName == NULL){
		  	env->ThrowNew(env->FindClass(jcd_RegistryException), "Error registry value name");
		 	return NULL;
		  }
		  DWORD type;
		  DWORD size;
		  if(RegQueryValueEx(hkey, szName, NULL, &type, (LPBYTE)NULL, &size) != ERROR_SUCCESS) {
				env->ThrowNew(env->FindClass(jcd_RegistryException), "Error retreiving registry value");
				RegCloseKey(hkey);
				delete[] szName;
				return NULL;
		  }
		  
		  if(isUnicode(env))size*= 2;
		  
		  char *data = (char*)malloc(size);

		  if(RegQueryValueEx(hkey, szName, NULL, &type, (LPBYTE)data, &size) != ERROR_SUCCESS) {
				env->ThrowNew(env->FindClass(jcd_RegistryException), "Error retreiving registry value");
				free(data);
				RegCloseKey(hkey);;
				delete[] szName;
				return NULL;
		  } 

		  RegCloseKey(hkey);
		  
		  delete[] szName;

		  char* szField;            
		  jobject dataValue;            
		  jclass CInteger;           
		  jmethodID mid_IntegerConstructor;  
		  char *tmp;               
		  DWORD required;            
		  switch(type) {
				case REG_NONE:
				case REG_BINARY:
					 if(type == REG_NONE) {
						  szField = "REG_NONE";
					 } 
					 else {
						  szField = "REG_BINARY";
					 } 

					 dataValue = env->NewByteArray(size);
					 if(dataValue == NULL) { return NULL; }

					 env->SetByteArrayRegion((jbyteArray)dataValue, 0, size, (signed char*)data);
					 break;

				case REG_DWORD:
				case REG_DWORD_BIG_ENDIAN:
					 if(type == REG_DWORD) {
						  szField = "REG_DWORD";
					 }
					 else {
						  szField = "REG_DWORD_BIG_ENDIAN";
					 } 

					 CInteger = env->FindClass(jcd_Integer);
					 if(CInteger == NULL) { return NULL; }

					 mid_IntegerConstructor = env->GetMethodID(CInteger, "<init>", "(I)V");
					 if(mid_IntegerConstructor == NULL) { return NULL; }

					 dataValue = env->NewObject(CInteger, mid_IntegerConstructor, *(long*)data);
					 break;

				case REG_SZ:
					 szField = "REG_SZ";
					 dataValue = nativeTojstring(env,data);
					 break;

				case REG_EXPAND_SZ:
					 szField = "REG_EXPAND_SZ";

					 // determine required size
					 required = ExpandEnvironmentStrings(data, NULL, 0);
					 
					 if(isUnicode(env))required*= 2;
					 
					 tmp = (char*)malloc(required);

					 // expand the strings
					 if(!ExpandEnvironmentStrings(data, tmp, required)) {
						  env->ThrowNew(env->FindClass(jcd_RegistryException), "Error expanding registry strings");
						  return NULL;
					 } // if

					 dataValue = nativeTojstring(env,tmp);
					 break;

				case REG_MULTI_SZ:
					 szField = "REG_MULTI_SZ";
					 env->ThrowNew(env->FindClass(jcd_RegistryException), "Unsupported data type");
					 return NULL;
				default:
					 env->ThrowNew(env->FindClass(jcd_RegistryException), "Unsupported data type");
					 return NULL;
		  } 

		  jclass CValueType = env->FindClass(jcd_ValueType);
		  if(CValueType == NULL) return NULL;

		  jfieldID fid_type = env->GetStaticFieldID(CValueType, szField, jfd_ValueType);
		  if(fid_type == NULL) return NULL;
		  jobject typeValue = env->GetStaticObjectField(CValueType, fid_type);

		  jclass CRegistryValue = env->FindClass(jcd_RegistryValue);
		  if(CRegistryValue == NULL) return NULL;

		  jmethodID mid_Constructor = env->GetMethodID(CRegistryValue, "<init>", "()V");
		  if(mid_Constructor == NULL) return NULL;
		  jobject registryValue = env->NewObject(CRegistryValue, mid_Constructor);

		  jmethodID mid_setName = env->GetMethodID(CRegistryValue, "setName", "(Ljava/lang/String;)V");
		  if(mid_setName == NULL) return NULL;
		  env->CallObjectMethod(registryValue, mid_setName, name);

		  jmethodID mid_setType = env->GetMethodID(CRegistryValue, "setType", "(Lorg/sf/feeling/swt/win32/extension/registry/ValueType;)V");
		  if(mid_setType == NULL) return NULL;
		  env->CallObjectMethod(registryValue, mid_setType, typeValue);

		  jmethodID mid_setData = env->GetMethodID(CRegistryValue, "setData", "(Ljava/lang/Object;)V");
		  if(mid_setData == NULL) return NULL;
		  env->CallObjectMethod(registryValue, mid_setData, dataValue);

		  return registryValue;
	 } 
} 
#endif

#ifndef NO_SetKeyValue
JNIEXPORT void JNICALL Swt_registryKey_NATIVE(SetKeyValue)
	(JNIEnv *env, jobject obj, jobject value)
{
	 int nRootKey = getRootKey(env, obj);

	 jclass CRegistryKey = env->GetObjectClass(obj);

	 jfieldID fid_path = env->GetFieldID(CRegistryKey, "path", jfd_String);
	 if(fid_path == NULL) return;

	 jstring path = (jstring)env->GetObjectField(obj, fid_path);
	 if(path == NULL) return;

	 char *szPath = jstringToNative(env,path);
	 if(szPath == NULL){
	 	env->ThrowNew(env->FindClass(jcd_RegistryException), "Error registry key path");
	 	return;
	 } 

	 HKEY hkey;
	 if(RegOpenKeyEx((HKEY)nRootKey, szPath, 0, KEY_ALL_ACCESS, &hkey) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Error opening registry key");
		  delete[] szPath;
		  return;
	 } 
	 else {
	 	  delete[] szPath;
		  jclass CRegistryValue = env->GetObjectClass(value);
		  if(CRegistryValue == NULL) return;

		  jmethodID mid_getName = env->GetMethodID(CRegistryValue, "getName", "()Ljava/lang/String;");
		  if(mid_getName == NULL) return;
		  jstring name = (jstring)env->CallObjectMethod(value, mid_getName);

		  jmethodID mid_getType = env->GetMethodID(CRegistryValue, "getType", "()Lorg/sf/feeling/swt/win32/extension/registry/ValueType;");
		  if(mid_getType == NULL) return;
		  jobject type = env->CallObjectMethod(value, mid_getType);

		  jmethodID mid_getData = env->GetMethodID(CRegistryValue, "getData", "()Ljava/lang/Object;");
		  if(mid_getData == NULL) return;
		  jobject data = env->CallObjectMethod(value, mid_getData);

		  jclass CValueType = env->GetObjectClass(type);
		  if(CValueType == NULL) return;

		  jmethodID mid_getValue = env->GetMethodID(CValueType, "getValue", "()I");
		  if(mid_getValue == NULL) return;
		  int nValue = env->CallIntMethod(type, mid_getValue);

		  char *szName = jstringToNative(env,name);
		  if(szName == NULL){
		  	 env->ThrowNew(env->FindClass(jcd_RegistryException), "Error registry value name");
		  	 delete[] szName;
		 	 return;
		  }
		  switch(nValue) {
				case REG_NONE:
				case REG_BINARY:
					 if(env->IsAssignableFrom(env->GetObjectClass(data), env->FindClass(jfd_byteArray))) {
						  const char* dataValue = (char*)env->GetByteArrayElements((jbyteArray)data, NULL);
						  DWORD size = env->GetArrayLength((jarray)data);

						  if(RegSetValueEx(hkey, szName, 0, (DWORD)nValue, (LPBYTE)dataValue, size) != ERROR_SUCCESS) {
								env->ThrowNew(env->FindClass(jcd_RegistryException), "Error setting registry value");
						  } 

						  env->ReleaseByteArrayElements((jbyteArray)data, (signed char*)dataValue, 0);
					 } 
					 else {
						  env->ThrowNew(env->FindClass(jcd_RegistryException), "Incompatible data types");
					 }
					 break;

				case REG_DWORD:
				case REG_DWORD_BIG_ENDIAN:
					 if(env->IsAssignableFrom(env->GetObjectClass(data), env->FindClass(jcd_Integer))) {
						  jmethodID mid_intValue = env->GetMethodID(env->GetObjectClass(data), "intValue", "()I");
						  int ivalue = env->CallIntMethod(data, mid_intValue);
						  const char* dataValue = (char*)&ivalue;
						  int size = 4;

						  if(RegSetValueEx(hkey, szName, 0, (DWORD)nValue, (LPBYTE)dataValue, size) != ERROR_SUCCESS) {
								env->ThrowNew(env->FindClass(jcd_RegistryException), "Error setting registry value");
						  }
					 } 
					 else {
						  env->ThrowNew(env->FindClass(jcd_RegistryException), "Incompatible data types");
					 } 
					 break;

				case REG_SZ:
				case REG_EXPAND_SZ:
					 if(env->IsAssignableFrom(env->GetObjectClass(data), env->FindClass(jcd_String))) {
						  char* dataValue = jstringToNative(env,(jstring)data);
						  if(dataValue == NULL){
						  		env->ThrowNew(env->FindClass(jcd_RegistryException), "Error registry value");
						  }
						  else{
						  	jclass clsstring = env->FindClass("java/lang/String");
  					  	jmethodID mid = env->GetMethodID(clsstring, "getBytes","()[B"); 
						  	jbyteArray barr = (jbyteArray)env->CallObjectMethod((jstring)data, mid);
						  	jsize alen = env->GetArrayLength(barr);
						  	int size = alen + 1;
						  	if(RegSetValueEx(hkey, szName, 0, (DWORD)nValue, (LPBYTE)dataValue, size) != ERROR_SUCCESS) {
								env->ThrowNew(env->FindClass(jcd_RegistryException), "Error setting registry value");
						  	}
						  	delete[]  dataValue;
						  }
					 } 
					 else {
						  env->ThrowNew(env->FindClass(jcd_RegistryException), "Incompatible data types");
					 } 
					 break;

				case REG_MULTI_SZ:
						  env->ThrowNew(env->FindClass(jcd_RegistryException), "Not implemented");
					 break;

				default:
						  env->ThrowNew(env->FindClass(jcd_RegistryException), "Unsupported value type");
					 break;
		  } 
		  RegCloseKey(hkey);
		  delete[] szName;
	 } 
}
#endif

#ifndef NO_DeleteKeyValue
JNIEXPORT void JNICALL Swt_registryKey_NATIVE(DeleteKeyValue)
	(JNIEnv *env, jobject obj, jstring name)
{
	 int nRootKey = getRootKey(env, obj);

	 jclass CRegistryKey = env->GetObjectClass(obj);

	 jfieldID fid_path = env->GetFieldID(CRegistryKey, "path", jfd_String);
	 if(fid_path == NULL) return;

	 jstring path = (jstring)env->GetObjectField(obj, fid_path);
	 if(path == NULL) return;

	 char *szPath = jstringToNative(env,path);
	 if(szPath == NULL){
	  env->ThrowNew(env->FindClass(jcd_RegistryException), "Error registry key path");
	  return;
	 }
	 
	 HKEY hkey;
	 if(RegOpenKeyEx((HKEY)nRootKey, szPath, 0, KEY_ALL_ACCESS, &hkey) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Error opening registry key");
		  if(szPath != NULL) delete[] szPath;
	 } 
	 else {
	 	  if(szPath != NULL) delete[] szPath;
		  char *szName = jstringToNative(env,name);
		  if(szName == NULL){
	  	 env->ThrowNew(env->FindClass(jcd_RegistryException), "Error registry value name");
	 	 	 return;
	 	  }
		  if(RegDeleteValue(hkey, szName) != ERROR_SUCCESS) {
				env->ThrowNew(env->FindClass(jcd_RegistryException), "Error deleting registry key value");
		  }
		  RegCloseKey(hkey);
		  delete[] szName;
	 } 
} 
#endif

#ifndef NO_KeyIteratorHasNext
JNIEXPORT jboolean JNICALL Swt_registryKeyIterator_NATIVE(KeyIteratorHasNext)
	(JNIEnv *env, jobject obj)
{
	 jclass CKeyIterator = env->GetObjectClass(obj);
	 if(CKeyIterator == NULL) return false;

	 jfieldID fid_index = env->GetFieldID(CKeyIterator, "index", jfd_int);
	 if(fid_index == NULL) return false;
	 int index = env->GetIntField(obj, fid_index);

	 int count;
	 if(index == -1)  {
		  count = startKeyIteration(env, obj);
		  index = 0;
	 } 
	 else {
		  jfieldID fid_count = env->GetFieldID(CKeyIterator, "count", jfd_int);
		  if(fid_count == NULL) return false;
		  count = env->GetIntField(obj, fid_count);
	 } 

	 return index < count;
} 
#endif

#ifndef NO_KeyIteratorGetNext
JNIEXPORT jstring JNICALL Swt_registryKeyIterator_NATIVE(KeyIteratorGetNext)
	(JNIEnv *env, jobject obj)
{
	 jclass CKeyIterator = env->GetObjectClass(obj);
	 if(CKeyIterator == NULL) return NULL;

	 jfieldID fid_index = env->GetFieldID(CKeyIterator, "index", jfd_int);
	 if(fid_index == NULL) return NULL;
	 int index = env->GetIntField(obj, fid_index);

	 int count;
	 if(index == -1)  {
		  count = startKeyIteration(env, obj);
		  index = 0;
	 } 
	 else {
		  jfieldID fid_count = env->GetFieldID(CKeyIterator, "count", jfd_int);
		  if(fid_count == NULL) return NULL;
		  count = env->GetIntField(obj, fid_count);
	 } 

	 if(index >= count) {
		  env->ThrowNew(env->FindClass(jcd_utilNoSuchElementException), "past end of enumeration");
		  return NULL;
	 }

	 jfieldID fid_maxsize = env->GetFieldID(CKeyIterator, "maxsize", jfd_int);
	 if(fid_maxsize == NULL) return NULL;
	 int maxsize = env->GetIntField(obj, fid_maxsize);

	 jfieldID fid_hkey = env->GetFieldID(CKeyIterator, "hkey", jfd_int);
	 if(fid_hkey == NULL) return NULL;
	 HKEY hkey = (HKEY)env->GetIntField(obj, fid_hkey);
	 
	 if(isUnicode(env))maxsize*=2;
	 
	 char *cret = (char*)malloc(maxsize);

	 if(RegEnumKey(hkey, index, cret, maxsize) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Enum value failed");
		  free(cret);
		  RegCloseKey(hkey);
		  env->SetIntField(obj, fid_index, count);
		  return NULL;
	 } 

	 jstring ret = nativeTojstring(env, cret);

	 index++;
	 env->SetIntField(obj, fid_index, index);

	 if(index == count) {
		  RegCloseKey(hkey);
	 }

	 return ret;
}
#endif


#ifndef NO_KeyValueIteratorHasNext
JNIEXPORT jboolean JNICALL Swt_registryValueIterator_NATIVE(KeyValueIteratorHasNext)
	(JNIEnv *env, jobject obj)
{
	 jclass CValueIterator = env->GetObjectClass(obj);
	 if(CValueIterator == NULL) return false;

	 jfieldID fid_index = env->GetFieldID(CValueIterator, "index", jfd_int);
	 if(fid_index == NULL) return false;
	 int index = env->GetIntField(obj, fid_index);

	 int count;
	 if(index == -1)  {
		 count = startValueIteration(env, obj);
		  index = 0;
	 } 
	 else {
		  jfieldID fid_count = env->GetFieldID(CValueIterator, "count", jfd_int);
		  if(fid_count == NULL) return false;
		  count = env->GetIntField(obj, fid_count);
	 }

	 return index < count;
}
#endif

#ifndef NO_KeyValueIteratorGetNext
JNIEXPORT jstring JNICALL Swt_registryValueIterator_NATIVE(KeyValueIteratorGetNext)
	(JNIEnv *env, jobject obj)
{
	 jclass CValueIterator = env->GetObjectClass(obj);
	 if(CValueIterator == NULL) return NULL;
	 jfieldID fid_index = env->GetFieldID(CValueIterator, "index", jfd_int);
	 if(fid_index == NULL) return NULL;
	 int index = env->GetIntField(obj, fid_index);

	 int count;
	 if(index == -1)  {
		  count = startValueIteration(env, obj);
		  index = 0;
	 }
	 else {
		  jfieldID fid_count = env->GetFieldID(CValueIterator, "count", jfd_int);
		  if(fid_count == NULL) return NULL;
		  count = env->GetIntField(obj, fid_count);
	 }

	 if(index >= count) {
		  env->ThrowNew(env->FindClass(jcd_utilNoSuchElementException), "past end of enumeration");
		  return NULL;
	 }

	 jfieldID fid_maxsize = env->GetFieldID(CValueIterator, "maxsize", jfd_int);
	 if(fid_maxsize == NULL) return NULL;
	 int maxsize = env->GetIntField(obj, fid_maxsize);

	 jfieldID fid_hkey = env->GetFieldID(CValueIterator, "hkey", jfd_int);
	 if(fid_hkey == NULL) return NULL;
	 HKEY hkey = (HKEY)env->GetIntField(obj, fid_hkey);
	 
	 if(isUnicode(env))maxsize*= 2;
	 
	 char *cret = (char*)malloc(maxsize);

	 if(RegEnumValue(hkey, index, cret, (LPDWORD)&maxsize, NULL, NULL, NULL, NULL) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Enum value failed");
		  free(cret);
		  RegCloseKey(hkey);
		  env->SetIntField(obj, fid_index, count);
		  return NULL;
	 }

	 jstring ret = nativeTojstring(env,cret);

	 index++;
	 env->SetIntField(obj, fid_index, index);

	 if(index == count) {
		  RegCloseKey(hkey);
	 }

	return ret;
}
#endif

int CreateShortCut(LPCSTR pszShortcutFileToLink, LPCSTR pszLinkFileName) 
  { 
    HRESULT hres; 
    int dwResult = 0; 
    IShellLink *psl; 
      
    hres = CoCreateInstance(CLSID_ShellLink, NULL, CLSCTX_INPROC_SERVER, 
      IID_IShellLink, (void **)&psl); 
    if (SUCCEEDED(hres)) 
    { 
      IPersistFile *ppf;  
      hres = psl->QueryInterface(IID_IPersistFile, (void **)&ppf); 
      if (SUCCEEDED(hres)) 
      {   
        hres = psl->SetPath(pszShortcutFileToLink); 
        if(SUCCEEDED(hres)) 
        { 
        	LPWSTR wsz = (LPWSTR)malloc(MAX_PATH*sizeof(WCHAR)); 
        	MultiByteToWideChar(CP_ACP, 0, pszLinkFileName, -1, wsz, MAX_PATH*sizeof(WCHAR));  
          	hres = ppf->Save(wsz, TRUE); 
          	if(!SUCCEEDED(hres)) 
           	dwResult = SHELLLINK_ERROR_SAVE; 
        } 
        else 
          dwResult = SHELLLINK_ERROR_SETPATH; 
        ppf->Release(); 
      } 
      psl->Release(); 
    } 
    return dwResult; 
  } 
  
  
#ifndef NO_CreateShortCut
JNIEXPORT jint JNICALL Swt_extension_NATIVE(CreateShortCut)
	(JNIEnv *env, jclass that, jstring sourceFile,jstring linkFile)
{
	jint rc = 0;
	
	char *lpSourceFile=NULL;
	char *lpLinkFile=NULL;
	char *lpLinkDescriptor=NULL;
	if (sourceFile) if ((lpSourceFile = jstringToNative(env,sourceFile))==NULL) goto fail;
	if (linkFile) if ((lpLinkFile = jstringToNative(env,linkFile))==NULL) goto fail;
	
	rc = (jint)CreateShortCut(lpSourceFile,lpLinkFile);
	
	fail:
	if(lpSourceFile!=NULL)delete[] lpSourceFile;
	if(lpLinkFile!=NULL)delete[] lpLinkFile;
	return rc;
}
#endif

IShellLink* getIShellLink(LPCSTR pszShortcutFileToLink){
	IShellLink* psl;
	HRESULT hres;
	hres = CoCreateInstance(CLSID_ShellLink, NULL, CLSCTX_INPROC_SERVER,IID_IShellLink, (LPVOID*) &psl);
	if (SUCCEEDED(hres))
	{
		IPersistFile* ppf;
		hres = psl->QueryInterface( IID_IPersistFile, (LPVOID *) &ppf);
		if (SUCCEEDED(hres))
		{
			LPWSTR wsz = (LPWSTR)malloc(MAX_PATH*sizeof(WCHAR)); 
			MultiByteToWideChar(CP_ACP, MB_PRECOMPOSED, pszShortcutFileToLink, -1, wsz,MAX_PATH*sizeof(WCHAR));
			hres = ppf->Load(wsz, 0);
			ppf->Release();
			if (SUCCEEDED(hres))
			{
				return psl;
			}
		}
		psl->Release();
	}
	return NULL;
}


#ifndef NO_GetShortCutTarget
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetShortCutTarget)
	(JNIEnv *env, jclass that, jstring linkFile)
{
	jstring rc = NULL;
	
	char *lpLinkFile=NULL;
	if (linkFile) if ((lpLinkFile = jstringToNative(env,linkFile))==NULL) goto fail;
	IShellLink* psl; 
	psl = getIShellLink(lpLinkFile);
	if(psl==NULL)goto fail;
	char sourceFileName[1024];
	memset(sourceFileName,'\0',sizeof(sourceFileName));
	psl->GetPath(sourceFileName, sizeof(sourceFileName), NULL, SLGP_UNCPRIORITY);
	rc = nativeTojstring(env,sourceFileName);
	psl->Release();
	fail:
	if(lpLinkFile!=NULL)delete[] lpLinkFile;
	return rc;
}
#endif

#ifndef NO_SetShortCutTarget
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SetShortCutTarget)
	(JNIEnv *env, jclass that, jstring linkFile, jstring linkTarget)
{	
	jint rc = -1;
	char *lpLinkFile=NULL;
	char *lpLinkTarget=NULL;
	IShellLink* psl; 
	IPersistFile *ppf;  
	HRESULT hres;  

	if (linkFile) if ((lpLinkFile = jstringToNative(env,linkFile))==NULL) goto fail;
	if (linkTarget) if ((lpLinkTarget = jstringToNative(env,linkTarget))==NULL) goto fail;

	psl = getIShellLink(lpLinkFile);
	if(psl==NULL)goto fail;
	
	hres = psl->QueryInterface(IID_IPersistFile, (void **)&ppf); 
	
	if (SUCCEEDED(hres)) 
  {   
   hres = psl->SetPath(lpLinkTarget); 
   if(SUCCEEDED(hres)) 
   { 
   		LPWSTR wsz = (LPWSTR)malloc(MAX_PATH*sizeof(WCHAR)); 
     	MultiByteToWideChar(CP_ACP, 0, lpLinkFile, -1, wsz, MAX_PATH*sizeof(WCHAR));  
     	hres = ppf->Save(wsz, TRUE); 
     	if(!SUCCEEDED(hres)) 
      	rc = 0;  
    	ppf->Release(); 
    } 
    psl->Release(); 
  }	
  
	fail:
	if(lpLinkFile!=NULL)delete[] lpLinkFile;
	if(lpLinkTarget!=NULL)delete[] lpLinkTarget;
	return rc;
}
#endif

#ifndef NO_GetShortCutWorkingDirectory
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetShortCutWorkingDirectory)
	(JNIEnv *env, jclass that, jstring linkFile)
{
	jstring rc = NULL;
	
	char *lpLinkFile=NULL;
	if (linkFile) if ((lpLinkFile = jstringToNative(env,linkFile))==NULL) goto fail;
	IShellLink* psl; 
	psl = getIShellLink(lpLinkFile);
	if(psl==NULL)goto fail;
	char sourceFileName[1024];
	memset(sourceFileName,'\0',sizeof(sourceFileName));
	psl->GetPath(sourceFileName, sizeof(sourceFileName), NULL, SLGP_UNCPRIORITY);
	rc = nativeTojstring(env,sourceFileName);
	psl->Release();
	fail:
	if(lpLinkFile!=NULL)delete[] lpLinkFile;
	return rc;
}
#endif

#ifndef NO_SetShortCutWorkingDirectory
JNIEXPORT int JNICALL Swt_extension_NATIVE(SetShortCutWorkingDirectory)
	(JNIEnv *env, jclass that, jstring linkFile, jstring linkWorkingDirectory)
{	
	jint rc = -1;
	char *lpLinkFile=NULL;
	char *lpLinkWorkingDirectory=NULL;
	IPersistFile *ppf;  
	HRESULT hres;  
	IShellLink* psl; 
	
	if (linkFile) if ((lpLinkFile = jstringToNative(env,linkFile))==NULL) goto fail;
	if (linkWorkingDirectory) if ((lpLinkWorkingDirectory = jstringToNative(env,linkWorkingDirectory))==NULL) goto fail;
	
	
	psl = getIShellLink(lpLinkFile);
	if(psl==NULL)goto fail;
 	
 	hres = psl->QueryInterface(IID_IPersistFile, (void **)&ppf); 
  
  if (SUCCEEDED(hres)) 
  {   
   hres = psl->SetWorkingDirectory(lpLinkWorkingDirectory); 
   if(SUCCEEDED(hres)) 
   { 
   	 LPWSTR wsz = (LPWSTR)malloc(MAX_PATH*sizeof(WCHAR)); 
     MultiByteToWideChar(CP_ACP, 0, lpLinkFile, -1, wsz, MAX_PATH*sizeof(WCHAR));  
     hres = ppf->Save(wsz, TRUE); 
     if(!SUCCEEDED(hres)) 
      rc = 0;  
     ppf->Release(); 
    } 
    psl->Release(); 
  }	
  
	fail:
	if(lpLinkFile!=NULL)delete[] lpLinkFile;
	if(lpLinkWorkingDirectory!=NULL)delete[] lpLinkWorkingDirectory;
	return rc;
}
#endif

#ifndef NO_GetShortCutDescription
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetShortCutDescription)
	(JNIEnv *env, jclass that, jstring linkFile)
{
	jstring rc = NULL;
	
	char *lpLinkFile=NULL;
	if (linkFile) if ((lpLinkFile = jstringToNative(env,linkFile))==NULL) goto fail;
	IShellLink* psl; 
	psl = getIShellLink(lpLinkFile);
	if(psl==NULL)goto fail;
	char description[1024];
	memset(description,'\0',sizeof(description));
	psl->GetDescription(description, sizeof(description));
	rc = nativeTojstring(env,description);
	psl->Release();
	fail:
	if(lpLinkFile!=NULL)delete[] lpLinkFile;
	return rc;
}
#endif

#ifndef NO_SetShortCutDescription
JNIEXPORT int JNICALL Swt_extension_NATIVE(SetShortCutDescription)
	(JNIEnv *env, jclass that, jstring linkFile, jstring linkDescription)
{	
	jint rc = -1;
	
	char *lpLinkFile=NULL;
	char *lpLinkDescription=NULL;
	
	IPersistFile *ppf;  
	HRESULT hres;  
	IShellLink* psl; 
	
	if (linkFile) if ((lpLinkFile = jstringToNative(env,linkFile))==NULL) goto fail;
	if (linkDescription) if ((lpLinkDescription = jstringToNative(env,linkDescription))==NULL) goto fail;
	psl = getIShellLink(lpLinkFile);
	if(psl==NULL)goto fail;
	
	hres = psl->QueryInterface(IID_IPersistFile, (void **)&ppf); 
  
  if (SUCCEEDED(hres)) 
  {   
   hres = psl->SetDescription(lpLinkDescription); 
   if(SUCCEEDED(hres)) 
   { 
   	 LPWSTR wsz = (LPWSTR)malloc(MAX_PATH*sizeof(WCHAR)); 
     MultiByteToWideChar(CP_ACP, 0, lpLinkFile, -1, wsz, MAX_PATH*sizeof(WCHAR));  
     hres = ppf->Save(wsz, TRUE); 
     if(!SUCCEEDED(hres)) 
      rc = 0;  
     ppf->Release(); 
    } 
    psl->Release(); 
  }	
  
	fail:
	if(lpLinkFile!=NULL)delete[] lpLinkFile;
	if(lpLinkDescription!=NULL)delete[] lpLinkDescription;
	return rc;
}
#endif


#ifndef NO_GetShortCutArguments
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetShortCutArguments)
	(JNIEnv *env, jclass that, jstring linkFile)
{
	jstring rc = NULL;
	char *lpLinkFile=NULL;
	if (linkFile) if ((lpLinkFile = jstringToNative(env,linkFile))==NULL) goto fail;
	IShellLink* psl; 
	psl = getIShellLink(lpLinkFile);
	if(psl==NULL)goto fail;
	char sourceFileName[1024];
	memset(sourceFileName,'\0',sizeof(sourceFileName));
	psl->GetArguments(sourceFileName, sizeof(sourceFileName));
	rc = nativeTojstring(env,sourceFileName);
	psl->Release();
	fail:
	if(lpLinkFile!=NULL)delete[] lpLinkFile;
	return rc;
}
#endif

#ifndef NO_SetShortCutArguments
JNIEXPORT int JNICALL Swt_extension_NATIVE(SetShortCutArguments)
	(JNIEnv *env, jclass that, jstring linkFile, jstring linkArguments)
{	
	jint rc = -1;
	char *lpLinkFile=NULL;
	char *lpLinkArguments=NULL;
	
	IPersistFile *ppf;  
	HRESULT hres;  
	IShellLink* psl; 
	
	if (linkFile) if ((lpLinkFile = jstringToNative(env,linkFile))==NULL) goto fail;
	if (linkArguments) if ((lpLinkArguments = jstringToNative(env,linkArguments))==NULL) goto fail;
	psl = getIShellLink(lpLinkFile);
	if(psl==NULL)goto fail;
	
	hres = psl->QueryInterface(IID_IPersistFile, (void **)&ppf); 
  
  if (SUCCEEDED(hres)) 
  {   
   hres = psl->SetArguments(lpLinkArguments); 
   if(SUCCEEDED(hres)) 
   { 
	 LPWSTR wsz = (LPWSTR)malloc(MAX_PATH*sizeof(WCHAR)); 
     MultiByteToWideChar(CP_ACP, 0, lpLinkFile, -1, wsz, MAX_PATH*sizeof(WCHAR));  
     hres = ppf->Save(wsz, TRUE); 
     if(!SUCCEEDED(hres)) 
      rc = 0;  
     ppf->Release(); 
    } 
    psl->Release(); 
  }	
  
	fail:
	if(lpLinkFile!=NULL)delete[] lpLinkFile;
	if(lpLinkArguments!=NULL)delete[] lpLinkArguments;
	return rc;
}
#endif


#ifndef NO_SHFileOperationW
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SHFileOperationW)
	(JNIEnv *env, jclass that, jobject shFileOpStruct)
{
	SHFILEOPSTRUCTW _shFileOpStruct, *lpShFileOpStruct=NULL;
	jint rc = 0;
	if (shFileOpStruct) if ((lpShFileOpStruct = getSHFILEOPSTRUCTWFields(env, shFileOpStruct, &_shFileOpStruct)) == NULL) goto fail;
	rc = (jint) SHFileOperationW (lpShFileOpStruct);
fail:
	return rc;
}
#endif

#ifndef NO_SHFileOperationA
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SHFileOperationA)
	(JNIEnv *env, jclass that, jobject shFileOpStruct)
{
	SHFILEOPSTRUCTA _shFileOpStruct, *lpShFileOpStruct=NULL;
	jint rc = 0;
	if (shFileOpStruct) if ((lpShFileOpStruct = getSHFILEOPSTRUCTAFields(env, shFileOpStruct, &_shFileOpStruct)) == NULL) goto fail;
	rc = (jint) SHFileOperationA (lpShFileOpStruct);
fail:
	return rc;
}
#endif

#ifndef NO_FlashWindow
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(FlashWindow)
	(JNIEnv *env, jclass that,jint hwnd, jboolean bInvert)
{
	jboolean rc = 0;
	rc = (jboolean) FlashWindow ((HWND)hwnd,bInvert);
	return rc;
}
#endif

#ifndef NO_FlashWindowEx
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(FlashWindowEx)
	(JNIEnv *env, jclass that, jobject flashInfoStruct)
{
	FLASHWINFO _flashInfoStruct, *lpFlashInfoStruct=NULL;
	jboolean rc = 0;
	if (flashInfoStruct) if ((lpFlashInfoStruct = getFLASHWINFOFields(env, flashInfoStruct, &_flashInfoStruct)) == NULL) goto fail;
	rc = (jboolean) FlashWindowEx (lpFlashInfoStruct);
	fail:
	return rc;
}
#endif

#ifndef NO_GetWindowsDirectory
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetWindowsDirectory)
	(JNIEnv *env, jclass that)
{
	char windowsDirectory[256];
	jstring rc = NULL;
	memset(windowsDirectory,'\0',sizeof(windowsDirectory));
	GetWindowsDirectory(windowsDirectory,sizeof(windowsDirectory));
	rc = nativeTojstring(env,windowsDirectory);
	return  rc;
}
#endif

#ifndef NO_GetTempPath
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetTempPath)
	(JNIEnv *env, jclass that)
{
	char tempPath[256];
	jstring rc = NULL;
	memset(tempPath,'\0',sizeof(tempPath));
	GetTempPath(sizeof(tempPath),tempPath);
	rc = nativeTojstring(env,tempPath);
	return  rc;
}
#endif


#ifndef NO_GetLongPathName
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetLongPathName)
	(JNIEnv *env, jclass that,jstring shortPath)
{
	char longPath[256];
	char *shortstr = NULL;
	jstring rc = NULL;
	typedef BOOL (WINAPI *lpfn) (const char* shortPath, char* longPath, long bufferSize);
	
	static int initialized = 0;
	static HMODULE hm = NULL;
	static lpfn fp = NULL;
	rc = 0;
	if (!initialized) {
		if (!hm) hm = LoadLibrary(GetLongPathName_LIB);
		if (hm) fp = (lpfn)GetProcAddress(hm, "GetLongPathNameA");
		initialized = 1;
	}
	if (fp) {
		memset(longPath,'\0',sizeof(longPath));
		shortstr = jstringToNative(env,shortPath);
		if(shortstr == NULL)
			return shortPath;
		fp(shortstr,longPath,sizeof(longPath));
		rc = nativeTojstring(env,longPath);
	}
	else rc = shortPath;
	if(shortstr!=NULL)delete[] shortstr;
	return  rc;
}
#endif

#ifndef NO_SetWallPaper
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(SetWallPaper)
	(JNIEnv *env, jclass that,jcharArray picturePath,int style)
{
	HRESULT hr;
	IActiveDesktop* pIAD;	
	jchar *picturestr = NULL;

	hr = CoCreateInstance( CLSID_ActiveDesktop, NULL, CLSCTX_INPROC_SERVER,IID_IActiveDesktop, (void**)&pIAD);
	if ( !SUCCEEDED(hr) )
	return false;

	COMPONENTSOPT co;
	co.dwSize = sizeof(COMPONENTSOPT);
	co.fActiveDesktop = TRUE;
	co.fEnableComponents = TRUE;
	hr = pIAD->SetDesktopItemOptions(&co, 0);
	
	WALLPAPEROPT wp;
	wp.dwSize = sizeof(WALLPAPEROPT);
	wp.dwStyle = style;
	hr = pIAD->SetWallpaperOptions(&wp, 0);
	
	picturestr = env->GetCharArrayElements(picturePath, NULL);
	hr = pIAD->SetWallpaper((LPCWSTR)picturestr, 0);
	env->ReleaseCharArrayElements(picturePath, picturestr, 0);
	
	hr = pIAD->ApplyChanges(AD_APPLY_ALL);
	
	pIAD->Release(); 
	return true;
}
#endif

#ifndef NO_SHGetPathFromIDList
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(SHGetPathFromIDList)
	(JNIEnv *env, jclass that,jint pid)
{
	char temp[256];
	jstring rc = NULL;
	memset(temp,'\0',sizeof(temp));
  	SHGetPathFromIDList((LPITEMIDLIST)pid,temp);	
	rc = nativeTojstring(env,temp);
	return  rc;
}
#endif

#ifndef NO_SHGetSpecialFolderLocation
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SHGetSpecialFolderLocation)
	(JNIEnv *env, jclass that,jint folderId)
{
	LPITEMIDLIST pid;
	jint rc = NULL;
	SHGetSpecialFolderLocation(0, folderId, &pid);
	rc = (jint)pid;
	return  rc;
}
#endif

#ifndef NO_SHGetFileInfoA
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SHGetFileInfoA)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jobject arg2, jint arg3, jint arg4)
{
	SHFILEINFOA _arg2, *lparg2=NULL;
	jint rc = 0;
	if (arg2) if ((lparg2 = getSHFILEINFOAFields(env, arg2, &_arg2)) == NULL) goto fail;
	rc = (jint)SHGetFileInfoA((LPCSTR)arg0, arg1, (SHFILEINFOA *)lparg2, arg3, arg4);
fail:
	if (arg2 && lparg2) setSHFILEINFOAFields(env, arg2, lparg2);
	return rc;
}
#endif

#ifndef NO_SHGetFileInfoW
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SHGetFileInfoW)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jobject arg2, jint arg3, jint arg4)
{
	SHFILEINFOW _arg2, *lparg2=NULL;
	jint rc = 0;
	if (arg2) if ((lparg2 = getSHFILEINFOWFields(env, arg2, &_arg2)) == NULL) goto fail;
	rc = (jint)SHGetFileInfoW((LPWSTR)arg0, arg1, (SHFILEINFOW *)lparg2, arg3, arg4);
fail:
	if (arg2 && lparg2) setSHFILEINFOWFields(env, arg2, lparg2);
	return rc;
}
#endif

#ifndef NO_GetMACID
JNIEXPORT jintArray JNICALL Swt_extension_NATIVE(GetMACID)
	(JNIEnv *env, jclass that)
{
	NCB ncb;
	UCHAR uRetCode;
	LANA_ENUM lana_enum;
	jint buffer[255] ;
	jintArray macids = NULL;
	
	memset( &ncb, 0, sizeof(ncb) );
	//memset( &lana_enum, 0, sizeof(lana_enum)); 
	
	ncb.ncb_command = NCBENUM; 
	ncb.ncb_buffer = (unsigned char *) &lana_enum; 
	ncb.ncb_length = sizeof(LANA_ENUM); 

	
	uRetCode = Netbios( &ncb ); 
	if ( uRetCode == 0 )
	{
		macids = env->NewIntArray(lana_enum.length);
		for(int i=0;i<lana_enum.length;i++)buffer[i] = (jint)lana_enum.lana[i];
		env->SetIntArrayRegion(macids, 0, lana_enum.length, buffer);
	}
	return macids;
}
#endif

#ifndef NO_GetMACAddress
JNIEXPORT jintArray JNICALL Swt_extension_NATIVE(GetMACAddress)
	(JNIEnv *env, jclass that,jint MACID)
{
	typedef struct _ASTAT_{
		ADAPTER_STATUS adapt;
		NAME_BUFFER NameBuff [30];
	} ASTAT, * PASTAT;

	ASTAT Adapter;
	NCB ncb;
	UCHAR uRetCode;
	jintArray macAddresses = NULL;
	jint buffer[6] ;
	memset( &ncb, 0, sizeof(ncb) );
	ncb.ncb_command = NCBRESET;
	ncb.ncb_lana_num = (UCHAR)MACID ;

	uRetCode = Netbios( &ncb );
	if(uRetCode == 0){
		memset( &ncb, 0, sizeof(ncb) );
		ncb.ncb_command = NCBASTAT;
		ncb.ncb_lana_num = (UCHAR)MACID; 
		strcpy((char *)ncb.ncb_callname,"*" );
		ncb.ncb_buffer = (unsigned char *) &Adapter; 
		ncb.ncb_length = sizeof(Adapter);
		uRetCode = Netbios( &ncb );
		if ( uRetCode == 0 ){
			macAddresses = env->NewIntArray(6);
			buffer[0] = ((jint)Adapter.adapt.adapter_address[0]);
			buffer[1] = ((jint)Adapter.adapt.adapter_address[1]);
			buffer[2] = ((jint)Adapter.adapt.adapter_address[2]);
			buffer[3] = ((jint)Adapter.adapt.adapter_address[3]);
			buffer[4] = ((jint)Adapter.adapt.adapter_address[4]);
			buffer[5] = ((jint)Adapter.adapt.adapter_address[5]);
			env->SetIntArrayRegion(macAddresses, 0, 6, buffer);
		}
	}
	return macAddresses;
}
#endif

#ifndef NO_GetCPUID
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetCPUID)
	(JNIEnv *env, jclass that)
{
	char szCPUID[129] = {NULL}; 
	char szTmp[33] = {NULL}; 
	unsigned long s1 = 0, 
	s2 = 0; 
	_asm 
	{ 
		mov eax,01h 
		xor edx,edx 
		cpuid 
		mov s1,edx 
		mov s2,eax 
	} 
	sprintf(szTmp, "%08X%08X-", s1, s2); 
	strcpy(szCPUID, szTmp); 
	_asm 
	{ 
		mov eax,03h 
		xor ecx,ecx 
		xor edx,edx 
		cpuid 
		mov s1,edx 
		mov s2,ecx 
	} 
	sprintf(szTmp, "%08X%08X", s1, s2); 
	strcat(szCPUID, szTmp); 

	return nativeTojstring(env,szCPUID); 
}
#endif



#define SystemBasicInformation   0
#define SystemPerformanceInformation 2
#define SystemTimeInformation    3

#define Li2Double(x) ((double)((x).HighPart) * 4.294967296E9 + (double)((x).LowPart))

typedef struct
{
  DWORD dwUnknown1;
  ULONG uKeMaximumIncrement;
  ULONG uPageSize;
  ULONG uMmNumberOfPhysicalPages;
  ULONG uMmLowestPhysicalPage;
  ULONG uMmHighestPhysicalPage;
  ULONG uAllocationGranularity;
  PVOID pLowestUserAddress;
  PVOID pMmHighestUserAddress;
  ULONG uKeActiveProcessors;
  BYTE  bKeNumberProcessors;
  BYTE  bUnknown2;
  WORD  wUnknown3;
} SYSTEM_BASIC_INFORMATION;

typedef struct
{
  LARGE_INTEGER liIdleTime;
  DWORD     dwSpare[76];
} SYSTEM_PERFORMANCE_INFORMATION;

typedef struct
{
  LARGE_INTEGER liKeBootTime;
  LARGE_INTEGER liKeSystemTime;
  LARGE_INTEGER liExpTimeZoneBias;
  ULONG   uCurrentTimeZoneId;
  DWORD   dwReserved;
} SYSTEM_TIME_INFORMATION;


int GetCpuUsages9X()
{
	unsigned long usages;
	HKEY hkey;
  DWORD dwDataSize;
  DWORD dwType;
  DWORD dwCpuUsage;

  // starting the counter
  if ( RegOpenKeyEx( HKEY_DYN_DATA,
         "PerfStats\\StartStat",
         0,KEY_ALL_ACCESS,
         &hkey ) != ERROR_SUCCESS
		)
	{
   return 0;
	}

  dwDataSize=sizeof(DWORD);
  RegQueryValueEx( hkey,
       "KERNEL\\CPUUsage",
       NULL,&dwType,
       (LPBYTE)&dwCpuUsage,
       &dwDataSize );

  RegCloseKey(hkey);
  
  // geting current counter's value
  if ( RegOpenKeyEx( HKEY_DYN_DATA,
         "PerfStats\\StatData",
         0,KEY_READ,
         &hkey ) != ERROR_SUCCESS)
	{
   return 0;
	}

  dwDataSize=sizeof(DWORD);
  RegQueryValueEx( hkey,
       "KERNEL\\CPUUsage",
        NULL,&dwType,
        (LPBYTE)&dwCpuUsage,
        &dwDataSize );

  usages = dwCpuUsage;
  RegCloseKey(hkey);
  
  // stoping the counter
  if ( RegOpenKeyEx( HKEY_DYN_DATA,
         "PerfStats\\StopStat",
         0,KEY_ALL_ACCESS,
         &hkey ) != ERROR_SUCCESS)
	{
   return 0;
	}

  dwDataSize=sizeof(DWORD);
  RegQueryValueEx( hkey,
       "KERNEL\\CPUUsage",
       NULL,&dwType,
       (LPBYTE)&dwCpuUsage,
       &dwDataSize );

  RegCloseKey(hkey);
  
  return usages;
}

LARGE_INTEGER m_liOldIdleTime;
LARGE_INTEGER  m_liOldSystemTime;

int GetCpuUsagesVistaOrLater()
{
	unsigned long usages;
	LARGE_INTEGER  idleTime;
  LARGE_INTEGER  kernelTime;
  LARGE_INTEGER  userTime;

  double     dbIdleTime;
  double     dbSystemTime;

  typedef BOOL ( __stdcall * pfnGetSystemTimes)( LPFILETIME lpIdleTime, LPFILETIME lpKernelTime, LPFILETIME lpUserTime );
  
  pfnGetSystemTimes s_pfnGetSystemTimes = (pfnGetSystemTimes)GetProcAddress(
              GetModuleHandle("Kernel32"), "GetSystemTimes");

  if (!s_pfnGetSystemTimes)
	{
    return 0;
	}

 	s_pfnGetSystemTimes( (LPFILETIME)&idleTime, (LPFILETIME)&kernelTime, (LPFILETIME)&userTime );


   // if it's a first call - skip it
   if (m_liOldIdleTime.QuadPart != 0)
   {
   dbIdleTime = Li2Double(idleTime) - Li2Double(m_liOldIdleTime);
   dbSystemTime = Li2Double(kernelTime) + Li2Double(userTime)
   		 - Li2Double(m_liOldSystemTime);

   dbIdleTime = dbIdleTime / dbSystemTime;
   dbIdleTime = 100.0 - dbIdleTime * 100.0 + 0.5;
   usages = (UINT)dbIdleTime;
   }

   // store new CPU's idle and system time
   m_liOldIdleTime = idleTime;
   m_liOldSystemTime.QuadPart = kernelTime.QuadPart+userTime.QuadPart;

	 return usages;
}

int GetCpuUsagesNt()
{
	unsigned long usages;
	SYSTEM_PERFORMANCE_INFORMATION SysPerfInfo;
  SYSTEM_TIME_INFORMATION    SysTimeInfo;
  SYSTEM_BASIC_INFORMATION   SysBaseInfo;
  double         dbIdleTime;
  double         dbSystemTime;
  LONG         status;
  typedef LONG (WINAPI *PROCNTQSI)(UINT,PVOID,ULONG,PULONG);
  PROCNTQSI NtQuerySystemInformation;
  
  NtQuerySystemInformation = (PROCNTQSI)GetProcAddress(
              GetModuleHandle("ntdll"),
               "NtQuerySystemInformation"
               );

  if (!NtQuerySystemInformation)
	{
    return 0;
	}

  // get number of processors in the system
  status = NtQuerySystemInformation(SystemBasicInformation,
		          &SysBaseInfo,sizeof(SysBaseInfo),NULL);

  if (status != NO_ERROR)
	{
    return 0;
	}

	 status = NtQuerySystemInformation(SystemTimeInformation,
		           &SysTimeInfo,sizeof(SysTimeInfo),0);
   if (status!=NO_ERROR)
	 {
    return 0;
	 }

   // get new CPU's idle time
   status = NtQuerySystemInformation(SystemPerformanceInformation,
		           &SysPerfInfo,sizeof(SysPerfInfo),NULL);

   if (status != NO_ERROR)
	 {
    return 0;
	 }

   // if it's a first call - skip it
   if (m_liOldIdleTime.QuadPart != 0)
   {
   // CurrentValue = NewValue - OldValue
   dbIdleTime = Li2Double(SysPerfInfo.liIdleTime) - Li2Double(m_liOldIdleTime);
   dbSystemTime = Li2Double(SysTimeInfo.liKeSystemTime) - Li2Double(m_liOldSystemTime);

    // CurrentCpuIdle = IdleTime / SystemTime
    dbIdleTime = dbIdleTime / dbSystemTime;

    // CurrentCpuUsage% = 100 - (CurrentCpuIdle * 100) / NumberOfProcessors
    dbIdleTime = 100.0 - dbIdleTime * 100.0 / (double)SysBaseInfo.bKeNumberProcessors + 0.5;

    usages = (UINT)dbIdleTime;
   }

   // store new CPU's idle and system time
   m_liOldIdleTime = SysPerfInfo.liIdleTime;
   m_liOldSystemTime = SysTimeInfo.liKeSystemTime;

	return usages;
}


BOOL IsNt()
{
	DWORD winVer = GetVersion();
	if(winVer<0x80000000)
	{
	 return TRUE;
	}
	else return FALSE;
}


BOOL IsVistaOrLater()
{
	DWORD winVer = GetVersion();
	int dwWindowsMajorVersion = (DWORD)(LOBYTE(LOWORD(winVer)));  
	if(dwWindowsMajorVersion >=6)
		return TRUE;
	else return FALSE;
}



int useNum;

#ifndef NO_GetCpuUsages
JNIEXPORT jint JNICALL Swt_extension_NATIVE(GetCpuUsages)
	(JNIEnv *env, jclass that)
{
	jint rc;
	
	if(IsVistaOrLater())
	{
		if(useNum == 0){
			m_liOldIdleTime.QuadPart = 0;
  		m_liOldSystemTime.QuadPart = 0;
  		useNum++;
		}
		rc = GetCpuUsagesVistaOrLater();
	}
	else if(IsNt())
	{
		if(useNum == 0){
			m_liOldIdleTime.QuadPart = 0;
  		m_liOldSystemTime.QuadPart = 0;
  		useNum++;
		}
		rc = GetCpuUsagesNt();
	}
	else
	{
		rc = GetCpuUsages9X();
	}
	
	return rc;
}
#endif


#pragma pack(4) 

#define ICMP_ECHO 8 
#define ICMP_ECHOREPLY 0 
#define ICMP_MIN 8 // minimum 8 byte icmp packet (just header) 
/* The IP header */ 
typedef struct iphdr { 
	unsigned int h_len:4; // length of the header 
	unsigned int version:4; // Version of IP 
	unsigned char tos; // Type of service 
	unsigned short total_len; // total length of the packet 
	unsigned short ident; // unique identifier 
	unsigned short frag_and_flags; // flags 
	unsigned char ttl; 
	unsigned char proto; // protocol (TCP, UDP etc) 
	unsigned short checksum; // IP checksum 
	unsigned int sourceIP; 
	unsigned int destIP; 
}IpHeader; 


typedef struct icmphdr { 
	BYTE i_type; 
	BYTE i_code; /* type sub code */ 
	USHORT i_cksum; 
	USHORT i_id; 
	USHORT i_seq; 
	ULONG timestamp; 
}IcmpHeader; 

#define STATUS_FAILED 0xFFFF 
#define DEF_PACKET_SIZE  32
#define MAX_PACKET 1024 
#define xmalloc(s) HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY,(s)) 
#define xfree(p) HeapFree (GetProcessHeap(),0,(p)) 


void fill_icmp_data(char * icmp_data, int datasize){ 
	IcmpHeader *icmp_hdr; 
	char *datapart; 
	icmp_hdr = (IcmpHeader*)icmp_data; 
	icmp_hdr->i_type = ICMP_ECHO; 
	icmp_hdr->i_code = 0; 
	icmp_hdr->i_id = (USHORT)GetCurrentProcessId(); 
	icmp_hdr->i_cksum = 0; 
	icmp_hdr->i_seq = 0; 
	datapart = icmp_data + sizeof(IcmpHeader); 
	memset(datapart,'\0', datasize - sizeof(IcmpHeader)); 
} 

USHORT checksum(USHORT *buffer, int size) { 
	unsigned long cksum=0; 
	while(size >1) { 
		cksum+=*buffer++; 
		size -=sizeof(USHORT); 
	} 
	if(size) { 
		cksum += *(UCHAR*)buffer; 
	} 
	cksum = (cksum >> 16) + (cksum & 0xffff); 
	cksum += (cksum >>16); 
	return (USHORT)(~cksum); 
} 

int decode_resp(char *buf, int bytes,struct sockaddr_in *from) { 
	IpHeader *iphdr; 
	IcmpHeader *icmphdr; 
	unsigned int iphdrlen; 
	iphdr = (IpHeader *)buf; 
	iphdrlen = (iphdr->h_len) * 4 ; // number of 32-bit words *4 = bytes 
	if (bytes < (int)iphdrlen + ICMP_MIN) { 
		return -1;
	} 
	icmphdr = (IcmpHeader*)(buf + iphdrlen); 
	if (icmphdr->i_type != ICMP_ECHOREPLY) { 
		return -1;
	} 
	if (icmphdr->i_id != (USHORT)GetCurrentProcessId()) { 
		return -1;
	} 
	return GetTickCount()-icmphdr->timestamp; 
} 


#ifndef NO_Ping
JNIEXPORT jint JNICALL Swt_extension_NATIVE(Ping)
	(JNIEnv *env, jclass that, jstring host,jint datasize)
{
	jint rc;
	
	struct hostent * hp; 
	WSADATA wsaData; 
	SOCKET sockRaw; 
	int bread;
	int timeout = 1000;
	struct sockaddr_in dest,from; 
	int fromlen = sizeof(from); 
	unsigned int addr=0; 
	char *dest_ip; 
	char *icmp_data; 
	char *recvbuf; 
	char *szHost = NULL;
	
	
	if (WSAStartup(MAKEWORD(2,1),&wsaData) != 0){ 
		return -1;
	}
	sockRaw = WSASocket(AF_INET,SOCK_RAW,IPPROTO_ICMP,NULL, 0,WSA_FLAG_OVERLAPPED);
	if (sockRaw == INVALID_SOCKET) { 
		 return -1;
	} 
	bread = setsockopt(sockRaw,SOL_SOCKET,SO_RCVTIMEO,(char*)&timeout, 
	sizeof(timeout)); 
	if(bread == SOCKET_ERROR) { 
		return -1;
	} 
	timeout = 1000; 
	bread = setsockopt(sockRaw,SOL_SOCKET,SO_SNDTIMEO,(char*)&timeout, sizeof(timeout)); 
	if(bread == SOCKET_ERROR) { 
		return -1;
	} 
	memset(&dest,0,sizeof(dest)); 
	szHost = jstringToNative(env,host);
	if(szHost == NULL)
		return -1;
	hp = gethostbyname(szHost); 
	delete[] szHost;
	if (!hp){ 
		addr = inet_addr(szHost); 
	}
	if ((!hp) && (addr == INADDR_NONE) ) {
		return -1;
	} 
	if (hp != NULL) 
		memcpy(&(dest.sin_addr),hp->h_addr,hp->h_length); 
	else 
		dest.sin_addr.s_addr = addr; 
	if (hp) 
		dest.sin_family = hp->h_addrtype; 
	else 
		dest.sin_family = AF_INET; 
	dest_ip = inet_ntoa(dest.sin_addr);
	
  if (datasize == 0) 
    datasize = DEF_PACKET_SIZE;
	if (datasize >1024)  {
		return -1;
	}

	datasize += sizeof(IcmpHeader); 
	icmp_data = (char*)xmalloc(MAX_PACKET); 
	recvbuf = (char*)xmalloc(MAX_PACKET); 
	if (!icmp_data) { 
		return -1;
	} 

	memset(icmp_data,0,MAX_PACKET); 
	fill_icmp_data(icmp_data,datasize); 
	
	int bwrote; 
	((IcmpHeader*)icmp_data)->i_cksum = 0; 
	((IcmpHeader*)icmp_data)->timestamp = GetTickCount(); 
	((IcmpHeader*)icmp_data)->i_seq = 0; 
	((IcmpHeader*)icmp_data)->i_cksum = checksum((USHORT*)icmp_data,datasize);
	bwrote = sendto(sockRaw,icmp_data,datasize,0,(struct sockaddr*)&dest,sizeof(dest)); 
	if (bwrote == SOCKET_ERROR){ 
		if (WSAGetLastError() == WSAETIMEDOUT) { 
			return -1;
		} 
		return -1;
	} 
	bread = recvfrom(sockRaw,recvbuf,MAX_PACKET,0,(struct sockaddr*)&from,&fromlen); 
	if (bread == SOCKET_ERROR){ 
		if (WSAGetLastError() == WSAETIMEDOUT) { 
			return -1; 
		} 
		return -1;
	} 
	
	rc = decode_resp(recvbuf,bread,&from);
	return rc;
}
#endif


BOOL GetTokenPrivileges(){
	if(IsNt()){
		HANDLE hToken; 
		TOKEN_PRIVILEGES tkp;
		OpenProcessToken(GetCurrentProcess(),TOKEN_ADJUST_PRIVILEGES|TOKEN_QUERY,&hToken);
		LookupPrivilegeValue(NULL,SE_SHUTDOWN_NAME,&tkp.Privileges[0].Luid);
		tkp.PrivilegeCount = 1;   
		tkp.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED;
		AdjustTokenPrivileges(hToken,false,&tkp,0,(PTOKEN_PRIVILEGES)NULL,0);
		if(GetLastError()!=ERROR_SUCCESS) 
  		return false;
  	else return true;  
	}
	else return true;
}

#ifndef NO_Reboot
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(Reboot)
	(JNIEnv *env, jclass that, jboolean force)
{
	jboolean rc;
	if(GetTokenPrivileges()){
		if(force)
			rc = ExitWindowsEx(EWX_REBOOT|EWX_FORCE,0);
		else rc = ExitWindowsEx(EWX_REBOOT,0);
	}
	else rc = FALSE;
	return rc;
}
#endif



#ifndef NO_Shutdown
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(Shutdown)
	(JNIEnv *env, jclass that, jboolean force)
{
	jboolean rc;
	if(GetTokenPrivileges()){
		if(force)
			rc = ExitWindowsEx(EWX_SHUTDOWN|EWX_FORCE|EWX_POWEROFF,0);
		else rc = ExitWindowsEx(EWX_SHUTDOWN|EWX_POWEROFF,0);
	}
	else rc = FALSE;
	return rc;
}
#endif

#ifndef NO_Logoff
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(Logoff)
	(JNIEnv *env, jclass that, jboolean force)
{
	jboolean rc;
	if(force)rc = ExitWindowsEx(EWX_LOGOFF|EWX_FORCE,0);
	else rc = ExitWindowsEx(EWX_LOGOFF,0);
	return rc;
}
#endif


#ifndef NO_LockWorkstation
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(LockWorkstation)
	(JNIEnv *env, jclass that)
{
	jboolean rc = 0;
	{
		static int initialized = 0;
		static HMODULE hm = NULL;
		static PROC proc = NULL;
		rc = 0;
		if (!initialized) {
			if (!hm) hm = LoadLibrary(LockWorkStation_LIB);
			if (hm) proc = GetProcAddress(hm, "LockWorkStation");
			initialized = 1;
		}
		if (proc) {
			rc = (jboolean)proc( );
		}
	}
	return rc;
}
#endif


BOOL SetPower()
{
	TOKEN_PRIVILEGES tp;
 	HANDLE hToken;
	LUID luid;

 	LPTSTR MachineName=NULL; 
	OpenProcessToken(GetCurrentProcess(),
        TOKEN_ADJUST_PRIVILEGES,
        &hToken );

  LookupPrivilegeValue(MachineName, SE_SHUTDOWN_NAME, &luid);

  tp.PrivilegeCount     = 1;
  tp.Privileges[0].Luid   = luid;
  tp.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED;

  AdjustTokenPrivileges(hToken, FALSE, &tp, sizeof(TOKEN_PRIVILEGES),
            NULL, NULL );
  if(GetLastError()!=ERROR_SUCCESS) 
  		return FALSE;
  else return TRUE;  
}

BOOL SetWorkstationState(BOOL fSuspend,BOOL force){
	if(!IsNt() || !SetPower())return FALSE;
	else return SetSystemPowerState(fSuspend,  force);
}

#ifndef NO_SuspendWorkstation
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(SuspendWorkstation)
	(JNIEnv *env, jclass that,jboolean bSuspend,jboolean force)
{
	jboolean rc = 0;
	rc = SetWorkstationState(bSuspend,force);
	return rc;
}
#endif

#ifndef NO_InitiateShutdownW
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(InitiateShutdownW)
	(JNIEnv *env, jclass that, jcharArray info, jint timeout, jboolean force, jboolean reboot)
{
	jboolean rc = 0;
	jchar *szInfo = NULL;
	if(IsNt() && GetTokenPrivileges()){
		szInfo = env->GetCharArrayElements(info, NULL);
		rc = InitiateSystemShutdownW(NULL, (LPWSTR)szInfo, timeout, force, reboot);
		env->ReleaseCharArrayElements(info, szInfo, 0); 
	}
	return rc;
}
#endif



#ifndef NO_InitiateShutdownA
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(InitiateShutdownA)
	(JNIEnv *env, jclass that,jbyteArray info, jint timeout, jboolean force, jboolean reboot)
{
	jboolean rc = 0;
	jbyte *szInfo = NULL;
	if(IsNt()){
		GetTokenPrivileges();
		szInfo = env->GetByteArrayElements(info, NULL);
		rc = InitiateSystemShutdownA(NULL, (LPTSTR)szInfo, timeout, force, reboot);
		env->ReleaseByteArrayElements(info, szInfo, 0); 
	}
	return rc;
}
#endif

#ifndef NO_ExtractAssociatedIconA
JNIEXPORT jint JNICALL Swt_extension_NATIVE(ExtractAssociatedIconA)
	(JNIEnv *env, jclass that, jint arg0, jbyteArray arg1, jint arg2)
{
	jbyte *lparg1=NULL;
	USHORT uarg2 = (USHORT)arg2;
	jint rc = 0;
	if (arg1) if ((lparg1 = env->GetByteArrayElements(arg1, NULL)) == NULL) goto fail;
	rc = (jint)ExtractAssociatedIconA((HINSTANCE)arg0, (LPSTR)lparg1, &uarg2);
fail:
	if (arg1 && lparg1) env->ReleaseByteArrayElements(arg1, lparg1, 0);
	return rc;
}
#endif

#ifndef NO_ExtractAssociatedIconW
JNIEXPORT jint JNICALL Swt_extension_NATIVE(ExtractAssociatedIconW)
	(JNIEnv *env, jclass that, jint arg0, jcharArray arg1, jint arg2)
{
	jchar *lparg1=NULL;
	USHORT uarg2 = (USHORT)arg2;
	jint rc = 0;
	if (arg1) if ((lparg1 = env->GetCharArrayElements( arg1, NULL)) == NULL) goto fail;
	rc = (jint)ExtractAssociatedIconW((HINSTANCE)arg0, (LPWSTR)lparg1, &uarg2);
fail:
	if (arg1 && lparg1) env->ReleaseCharArrayElements(arg1, lparg1, 0);
	return rc;
}
#endif

#ifndef NO_SHGetSpecialFolderPath
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(SHGetSpecialFolderPath)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jboolean arg2)
{
	char temp[256];
	jstring rc = NULL;
	memset(temp,'\0',sizeof(temp));
  	SHGetSpecialFolderPath((HWND)arg0,temp,arg1, arg2);	
	rc = nativeTojstring(env,temp);
	return rc;
}
#endif

#ifndef NO_SHAppBarMessage
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SHAppBarMessage)
	(JNIEnv *env, jclass that, jint dwMessage, jobject pData)
{
	APPBARDATA _appBarDataStruct, *lpAppBarDataStruct=NULL;
	jint rc = 0;
	if (pData) if ((lpAppBarDataStruct = getAPPBARDATAFields(env, pData, &_appBarDataStruct)) == NULL) goto fail;
	rc = (jint) SHAppBarMessage (dwMessage,lpAppBarDataStruct);
	setAPPBARDATAFields(env,pData,&_appBarDataStruct);
	fail:
	return rc;
}
#endif

#ifndef NO_GetComputerName
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetComputerName)
	(JNIEnv *env, jclass that)
{
	char temp[MAX_COMPUTERNAME_LENGTH+1];
	unsigned long size = MAX_COMPUTERNAME_LENGTH+1;
	jstring rc = NULL;
	memset(temp,'\0',sizeof(temp));
  	GetComputerName(temp,&size);	
	rc = nativeTojstring(env,temp);
	return rc;
}
#endif


#ifndef NO_GetUserName
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetUserName)
	(JNIEnv *env, jclass that)
{
	char temp[256];
	unsigned long size = 256;
	jstring rc = NULL;
	memset(temp,'\0',sizeof(temp));
  	GetUserName(temp,&size);	
	rc = nativeTojstring(env,temp);
	return rc;
}
#endif

#ifndef NO_SetComputerName
JNIEXPORT boolean JNICALL Swt_extension_NATIVE(SetComputerName)
	(JNIEnv *env, jclass that, jstring name)
{
	char *lpName=NULL;
	jboolean rc = false;
	/*
	jclass clsstring = env->FindClass("java/lang/String");
  jmethodID mid = env->GetMethodID(clsstring, "getBytes","()[B"); 
	jbyteArray barr = (jbyteArray)env->CallObjectMethod(name, mid);
	jsize alen = env->GetArrayLength(barr);
	if(alen>MAX_COMPUTERNAME_LENGTH)goto fail;
	*/
	if (name) if ((lpName = jstringToNative(env,name))==NULL) goto fail;
  	rc = SetComputerName(lpName);	
	fail:
	if(lpName!=NULL)delete[] lpName;
	return rc;
}
#endif

#ifndef NO_CreateEvent
JNIEXPORT jint JNICALL Swt_extension_NATIVE(CreateEvent)
	(JNIEnv *env, jclass that, jboolean manualReset, jboolean initialState, jstring name)
{
	char *lpName=NULL;
	jint rc = 0;
	if (name) if ((lpName = jstringToNative(env,name))==NULL) goto fail;
	rc = (jint)CreateEvent(0,manualReset,initialState,lpName);
	fail:
	if(lpName!=NULL)delete[] lpName;
	return rc;
}
#endif

#ifndef NO_ResetEvent
JNIEXPORT jint JNICALL Swt_extension_NATIVE(ResetEvent)
	(JNIEnv *env, jclass that, jint eventHandle)
{
	jint rc = 0;
	rc = (jint)ResetEvent((HANDLE)eventHandle);
	return rc;
}
#endif

#ifndef NO_SetEvent
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SetEvent)
	(JNIEnv *env, jclass that, jint eventHandle)
{
	jint rc = 0;
	rc = (jint)SetEvent((HANDLE)eventHandle);
	return rc;
}
#endif

#ifndef NO_WaitForSingleObject
JNIEXPORT jint JNICALL Swt_extension_NATIVE(WaitForSingleObject)
	(JNIEnv *env, jclass that, jint handle, jint timeout)
{
	jint rc = 0;
	rc = (jint)WaitForSingleObject((HANDLE)handle, timeout);
	return rc;
}
#endif


 


HANDLE			DllHinst=NULL; 
HOOKSTRUCT 	*HookStruct=NULL; 
HANDLE 		hFile=NULL; 

static void Cleanup()
{
	int i = 0;
	for(i=0;i<15;i++)
	{
		if(HookStruct->hkb[i]==NULL)continue;
		UnhookWindowsHookEx(HookStruct->hkb[i]);
		HookStruct->hkb[i] = NULL;
	}
}

BOOL APIENTRY DllMain( HANDLE hModule, DWORD  ul_reason_for_call, LPVOID lpReserved )
{
	switch(ul_reason_for_call)
	{
		case DLL_PROCESS_ATTACH:
			DllHinst = hModule;
			hFile=CreateFileMapping(INVALID_HANDLE_VALUE,NULL,PAGE_READWRITE,0,sizeof(HOOKSTRUCT),"HookStruct"); 
    HookStruct=(HOOKSTRUCT*)MapViewOfFile(hFile,FILE_MAP_ALL_ACCESS,0,0,sizeof(HOOKSTRUCT)); 
			return TRUE;
		case DLL_PROCESS_DETACH:
			Cleanup();
			UnmapViewOfFile(HookStruct); 
    CloseHandle(hFile);  
			return TRUE;
	}
  return TRUE;
}

#ifndef NO_UnInstallSystemHook
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(UnInstallSystemHook)
	(JNIEnv *env, jclass that, jint type)
{
	if(HookStruct->hkb[type]==NULL)return true;
	if(UnhookWindowsHookEx(HookStruct->hkb[type])){
		HookStruct->hkb[type] = NULL;
		return true;
	}
	return false;
}
#endif



JNIEXPORT jobject NotifyJava(JNIEnv *env, int type, WPARAM wParam, LPARAM lParam, int nCode)
{		
	jclass CHookData;
	switch(type)
	{
		case WH_KEYBOARD:
			CHookData = env->FindClass("org/sf/feeling/swt/win32/extension/hook/data/KeyboardHookData");
			break;
		case WH_MOUSE:
			CHookData = env->FindClass("org/sf/feeling/swt/win32/extension/hook/data/MouseHookData");
			break;
		case WH_JOURNALRECORD:
			CHookData = env->FindClass("org/sf/feeling/swt/win32/extension/hook/data/JournalRecordHookData");
			break;
		case WH_GETMESSAGE:
		case WH_CALLWNDPROC:
		case WH_SYSMSGFILTER:
			CHookData = env->FindClass("org/sf/feeling/swt/win32/extension/hook/data/MessageHookData");
			break;
		default:
			CHookData = env->FindClass("org/sf/feeling/swt/win32/extension/hook/data/HookData");
	}

	jmethodID mid_Constructor = env->GetMethodID(CHookData, "<init>", "()V");
	jobject hookData = env->NewObject(CHookData, mid_Constructor);

	jmethodID mid_setLParam = env->GetMethodID(CHookData, "setLParam", "(I)V");
	if(mid_setLParam != NULL)
		env->CallObjectMethod(hookData, mid_setLParam, (jint)lParam);
	jmethodID mid_setWParam = env->GetMethodID(CHookData, "setWParam", "(I)V");
	if(mid_setWParam != NULL)
		env->CallObjectMethod(hookData, mid_setWParam, (jint)wParam);
		
	jmethodID mid_setNCode = env->GetMethodID(CHookData, "setNCode", "(I)V");
	if(mid_setNCode != NULL)
		env->CallObjectMethod(hookData, mid_setNCode, (jint)nCode);
	
	if(type == WH_MOUSE)
	{	
		jclass CMouseHookStruct = env->FindClass("org/sf/feeling/swt/win32/internal/extension/MOUSEHOOKSTRUCT");
		jmethodID constructor = env->GetMethodID(CMouseHookStruct, "<init>", "()V");
		jobject hookStruct = env->NewObject(CMouseHookStruct, constructor);
		
		if (hookStruct!=NULL)
		{ 
			setMOUSEHOOKSTRUCTFields(env, hookStruct, &(HookStruct->mouseHookStruct));
			jmethodID mid_setStruct = env->GetMethodID(CHookData, "setStruct", "(Lorg/sf/feeling/swt/win32/internal/extension/MOUSEHOOKSTRUCT;)V");
			env->CallObjectMethod(hookData, mid_setStruct, hookStruct);
		}
	}
	else if(type == WH_GETMESSAGE || type == WH_CALLWNDPROC || type == WH_SYSMSGFILTER)
	{
		jclass CMsgStruct = env->FindClass("org/eclipse/swt/internal/win32/MSG");
		jmethodID constructor = env->GetMethodID(CMsgStruct, "<init>", "()V");
		jobject hookStruct = env->NewObject(CMsgStruct, constructor);
		
		if (hookStruct!=NULL)
		{ 
			setMSGFields(env, hookStruct, &(HookStruct->msg));
			jmethodID mid_setStruct = env->GetMethodID(CHookData, "setStruct", "(Lorg/eclipse/swt/internal/win32/MSG;)V");
			env->CallObjectMethod(hookData, mid_setStruct, hookStruct);
		}
	}
	return hookData;
}

#ifndef NO_ReadHookData
JNIEXPORT jobject JNICALL Swt_extension_NATIVE(ReadHookData)
	(JNIEnv *env, jclass that, jint type)
{	
	jobject hookData = NotifyJava( env, type, HookStruct->wParam[type], HookStruct->lParam[type], HookStruct->nCode[type] );
	return hookData;
}
#endif


JNIEXPORT LRESULT CALLBACK HookProc_2(INT nCode, WPARAM wParam, LPARAM lParam) 
{
  if (nCode < 0)  // do not process message 
		return CallNextHookEx(HookStruct->hkb[2], nCode, wParam, lParam); 
	HookStruct->wParam[2] = wParam;
	HookStruct->lParam[2] = lParam;
	HookStruct->nCode[2] = nCode;
	SetEvent(OpenEvent(EVENT_ALL_ACCESS, 0, "KEYBOARD"));
	return CallNextHookEx(HookStruct->hkb[2], nCode, wParam, lParam);
} 

JNIEXPORT LRESULT CALLBACK HookProc_3(INT nCode, WPARAM wParam, LPARAM lParam) 
{
  if (nCode < 0)  // do not process message 
		return CallNextHookEx(HookStruct->hkb[3], nCode, wParam, lParam); 
	HookStruct->wParam[3] = wParam;
	HookStruct->lParam[3] = lParam;
	HookStruct->nCode[3] = nCode;
	LPMSG pMSG=(MSG FAR *) lParam;
	MSG data = (MSG)(HookStruct->msg);
	data.pt.x = pMSG->pt.x;
	data.pt.y = pMSG->pt.y;
	data.time = pMSG->time;
	data.message = pMSG->message;
	data.wParam = pMSG->wParam;
	data.lParam = pMSG->lParam;
	data.hwnd = pMSG->hwnd;
	HookStruct->msg = data; 
	SetEvent(OpenEvent(EVENT_ALL_ACCESS, 0, "GETMESSAGE"));
	return CallNextHookEx(HookStruct->hkb[3], nCode, wParam, lParam);
} 

JNIEXPORT LRESULT CALLBACK HookProc_4(INT nCode, WPARAM wParam, LPARAM lParam) 
{
  if (nCode < 0)  // do not process message 
		return CallNextHookEx(HookStruct->hkb[4], nCode, wParam, lParam); 
	HookStruct->wParam[4] = wParam;
	HookStruct->lParam[4] = lParam;
	HookStruct->nCode[4] = nCode;
	LPMSG pMSG=(MSG FAR *) lParam;
	MSG data = (MSG)(HookStruct->msg);
	data.pt.x = pMSG->pt.x;
	data.pt.y = pMSG->pt.y;
	data.time = pMSG->time;
	data.message = pMSG->message;
	data.wParam = pMSG->wParam;
	data.lParam = pMSG->lParam;
	data.hwnd = pMSG->hwnd;
	HookStruct->msg = data; 
	SetEvent(OpenEvent(EVENT_ALL_ACCESS, 0, "CALLWNDPROC"));
	return CallNextHookEx(HookStruct->hkb[4], nCode, wParam, lParam);
} 

JNIEXPORT LRESULT CALLBACK HookProc_5(INT nCode, WPARAM wParam, LPARAM lParam) 
{
  if (nCode < 0)  // do not process message 
		return CallNextHookEx(HookStruct->hkb[5], nCode, wParam, lParam); 
	HookStruct->wParam[5] = wParam;
	HookStruct->lParam[5] = lParam;
	HookStruct->nCode[5] = nCode;
	SetEvent(OpenEvent(EVENT_ALL_ACCESS, 0, "CBT"));
	return CallNextHookEx(HookStruct->hkb[5], nCode, wParam, lParam);
} 

JNIEXPORT LRESULT CALLBACK HookProc_6(INT nCode, WPARAM wParam, LPARAM lParam) 
{
  if (nCode < 0)  // do not process message 
		return CallNextHookEx(HookStruct->hkb[6], nCode, wParam, lParam); 
	HookStruct->wParam[6] = wParam;
	HookStruct->lParam[6] = lParam;
	HookStruct->nCode[6] = nCode;
	LPMSG pMSG=(MSG FAR *) lParam;
	MSG data = (MSG)(HookStruct->msg);
	data.pt.x = pMSG->pt.x;
	data.pt.y = pMSG->pt.y;
	data.time = pMSG->time;
	data.message = pMSG->message;
	data.wParam = pMSG->wParam;
	data.lParam = pMSG->lParam;
	data.hwnd = pMSG->hwnd;
	HookStruct->msg = data; 
	SetEvent(OpenEvent(EVENT_ALL_ACCESS, 0, "SYSMSGFILTER"));
	return CallNextHookEx(HookStruct->hkb[6], nCode, wParam, lParam);
} 

JNIEXPORT LRESULT CALLBACK HookProc_7(INT nCode, WPARAM wParam, LPARAM lParam) 
{
  if (nCode < 0)  // do not process message 
		return CallNextHookEx(HookStruct->hkb[7], nCode, wParam, lParam); 
	HookStruct->wParam[7] = wParam;
	HookStruct->lParam[7] = lParam;
	HookStruct->nCode[7] = nCode;
	LPMOUSEHOOKSTRUCT pMouseHook=(MOUSEHOOKSTRUCT FAR *) lParam;
	MOUSEHOOKSTRUCT data = (MOUSEHOOKSTRUCT)(HookStruct->mouseHookStruct);
	data.pt.x = pMouseHook->pt.x;
	data.pt.y = pMouseHook->pt.y;
	data.wHitTestCode = pMouseHook->wHitTestCode;
	data.dwExtraInfo = pMouseHook->dwExtraInfo;
	data.hwnd = pMouseHook->hwnd;
	HookStruct->mouseHookStruct = data; 
	SetEvent(OpenEvent(EVENT_ALL_ACCESS, 0, "MOUSE"));
	return CallNextHookEx(HookStruct->hkb[7], nCode, wParam, lParam);
} 

JNIEXPORT LRESULT CALLBACK HookProc_8(INT nCode, WPARAM wParam, LPARAM lParam) 
{
  if (nCode < 0)  // do not process message 
		return CallNextHookEx(HookStruct->hkb[8], nCode, wParam, lParam);
	HookStruct->wParam[8] = wParam;
	HookStruct->lParam[8] = lParam; 
	HookStruct->nCode[8] = nCode;
	SetEvent(OpenEvent(EVENT_ALL_ACCESS, 0, "HARDWARE"));
	return CallNextHookEx(HookStruct->hkb[8], nCode, wParam, lParam);
} 

JNIEXPORT LRESULT CALLBACK HookProc_9(INT nCode, WPARAM wParam, LPARAM lParam) 
{
  if (nCode < 0)  // do not process message 
		return CallNextHookEx(HookStruct->hkb[9], nCode, wParam, lParam); 
	HookStruct->wParam[9] = wParam;
	HookStruct->lParam[9] = lParam; 
	HookStruct->nCode[9] = nCode;
	SetEvent(OpenEvent(EVENT_ALL_ACCESS, 0, "DEBUG"));
	return CallNextHookEx(HookStruct->hkb[9], nCode, wParam, lParam);
} 

JNIEXPORT LRESULT CALLBACK HookProc_10(INT nCode, WPARAM wParam, LPARAM lParam) 
{
  if (nCode < 0)  // do not process message 
		return CallNextHookEx(HookStruct->hkb[10], nCode, wParam, lParam); 
	HookStruct->wParam[10] = wParam;
	HookStruct->lParam[10] = lParam; 
	HookStruct->nCode[10] = nCode;
	SetEvent(OpenEvent(EVENT_ALL_ACCESS, 0, "SHELL"));
	return CallNextHookEx(HookStruct->hkb[10], nCode, wParam, lParam);
} 

JNIEXPORT LRESULT CALLBACK HookProc_11(INT nCode, WPARAM wParam, LPARAM lParam) 
{
  if (nCode < 0)  // do not process message 
		return CallNextHookEx(HookStruct->hkb[11], nCode, wParam, lParam); 
	HookStruct->wParam[11] = wParam;
	HookStruct->lParam[11] = lParam; 
	HookStruct->nCode[11] = nCode;
	SetEvent(OpenEvent(EVENT_ALL_ACCESS, 0, "FOREGROUNDIDLE"));
	return CallNextHookEx(HookStruct->hkb[11], nCode, wParam, lParam);
} 

JNIEXPORT LRESULT CALLBACK HookProc_12(INT nCode, WPARAM wParam, LPARAM lParam) 
{
  if (nCode < 0)  // do not process message 
		return CallNextHookEx(HookStruct->hkb[12], nCode, wParam, lParam);
	HookStruct->wParam[12] = wParam;
	HookStruct->lParam[12] = lParam;  
	HookStruct->nCode[12] = nCode;
	SetEvent(OpenEvent(EVENT_ALL_ACCESS, 0, "CALLWNDPROCRET"));
	return CallNextHookEx(HookStruct->hkb[12], nCode, wParam, lParam);
} 


#ifndef NO_InstallSystemHook
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(InstallSystemHook)
	(JNIEnv *env, jclass that, jint type, jint threadId)
{
	if(HookStruct->hkb[type]!=NULL)return true;
	HOOKPROC proc = NULL;
	switch(type)
	{
		case WH_KEYBOARD :
			proc = (HOOKPROC)HookProc_2;
			break;
		case WH_GETMESSAGE :
			proc = (HOOKPROC)HookProc_3;
			break;
		case WH_CALLWNDPROC :
			proc = (HOOKPROC)HookProc_4;
			break;
		case WH_CBT :
			proc = (HOOKPROC)HookProc_5;
			break;
		case WH_SYSMSGFILTER :
			proc = (HOOKPROC)HookProc_6;
			break;
		case WH_MOUSE :
			proc = (HOOKPROC)HookProc_7;
			break;
		case WH_DEBUG :
			proc = (HOOKPROC)HookProc_9;
			break;
		case WH_SHELL :
			proc = (HOOKPROC)HookProc_10;
			break;
		case WH_FOREGROUNDIDLE :
			proc = (HOOKPROC)HookProc_11;
			break;
		case WH_CALLWNDPROCRET :
			proc = (HOOKPROC)HookProc_12;
			break;
		default:
			return false;
	}
	if(threadId >0 )HookStruct->hkb[type] = SetWindowsHookEx( type, proc, 0 , threadId );
	else
	{
		HookStruct->hkb[type] = SetWindowsHookEx( type, proc, (HINSTANCE)DllHinst, 0 );
	}
	if(HookStruct->hkb[type] !=NULL)return true;
	else return false;
}
#endif

#ifndef NO_CreateStructByPoint
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(CreateStructByPoint)
	(JNIEnv *env, jclass that, jint point, jobject object)
{
	if(object == NULL || point == NULL)return false;
	if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/APPBARDATA")))
		setAPPBARDATAFields(env,object,(APPBARDATA FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/DISKFREESPACE")))
		setDISKFREESPACEFields(env,object,(DISKFREESPACE FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/EVENTMSG")))
		setEVENTMSGFields(env,object,(EVENTMSG FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/FLASHWINFO")))
		setFLASHWINFOFields(env,object,(FLASHWINFO FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/LOGICALDRIVES")))
		setLOGICALDRIVESFields(env,object,(LOGICALDRIVES FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MEMORYSTATUS")))
		setMEMORYSTATUSFields(env,object,(MEMORYSTATUSEX FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MSLLHOOKSTRUCT")))
		setMSLLHOOKSTRUCTFields(env,object,(MSLLHOOKSTRUCT FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/KBDLLHOOKSTRUCT")))
		setKBDLLHOOKSTRUCTFields(env,object,(KBDLLHOOKSTRUCT FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/SHFILEINFOA")))
		setSHFILEINFOAFields(env,object,(SHFILEINFOA FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/SHFILEINFOW")))
		setSHFILEINFOWFields(env,object,(SHFILEINFOW FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/SHFILEOPSTRUCTA")))
		setSHFILEOPSTRUCTAFields(env,object,(SHFILEOPSTRUCTA FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/SHFILEOPSTRUCTW")))
		setSHFILEOPSTRUCTWFields(env,object,(SHFILEOPSTRUCTW FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/SYSTEM_INFO")))
		setSYSTEM_INFOFields(env,object,(SYSTEM_INFO FAR *)point);
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MOUSEHOOKSTRUCT")))
		setMOUSEHOOKSTRUCTFields(env,object,((MOUSEHOOKSTRUCT FAR *)point));
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MCI_OPEN_PARMSA")))
		setMCI_OPEN_PARMSAFields(env,object,((MCI_OPEN_PARMSA FAR *)point));
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MCI_OPEN_PARMSW")))
		setMCI_OPEN_PARMSWFields(env,object,((MCI_OPEN_PARMSW FAR *)point));
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MCI_STATUS_PARMS")))
		setMCI_STATUS_PARMSFields(env,object,((MCI_STATUS_PARMS FAR *)point));
	else return false;
	return true;
}
#endif

#ifndef NO_ConvertPOINTToPoint
JNIEXPORT jint JNICALL Swt_extension_NATIVE(ConvertPOINTToPoint)
	(JNIEnv *env, jclass that, jobject object)
{
	jint rc = 0;
	if(object == NULL)return rc;
	POINT _POINT, *lpPOINT=NULL;
	lpPOINT = getPOINTFields(env, object, &_POINT);
	if(lpPOINT != NULL)rc = (jint)lpPOINT;
	return rc;
}
#endif

#ifndef NO_SaveStructToPoint
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(SaveStructToPoint)
	(JNIEnv *env, jclass that, jobject object, jint point)
{
	
	if(object == NULL || point == NULL )return false;
	boolean rc = false;
	if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/APPBARDATA")))
	{
		APPBARDATA _APPBARDATA, *lpAPPBARDATA=NULL;
		lpAPPBARDATA = getAPPBARDATAFields(env, object, &_APPBARDATA);
		if(lpAPPBARDATA == NULL)goto fail;
		APPBARDATA *data = (APPBARDATA *)point;
		data->cbSize = lpAPPBARDATA->cbSize ;
		data->hWnd = lpAPPBARDATA->hWnd ;
		data->uCallbackMessage = lpAPPBARDATA->uCallbackMessage ;
		data->rc.left = lpAPPBARDATA->rc.left ;
		data->rc.top = lpAPPBARDATA->rc.top;
		data->rc.right = lpAPPBARDATA->rc.right;
		data->rc.bottom = lpAPPBARDATA->rc.bottom;
		data->lParam = lpAPPBARDATA->lParam;
		rc = true;
	}
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/DISKFREESPACE")))
	{
		DISKFREESPACE _DISKFREESPACE, *lpDISKFREESPACE=NULL;
		lpDISKFREESPACE = getDISKFREESPACEFields(env, object, &_DISKFREESPACE);
		if(lpDISKFREESPACE == NULL)goto fail;
		DISKFREESPACE *space = (DISKFREESPACE *)point;
		space->returnValue = lpDISKFREESPACE->returnValue ;
		space->lastError = lpDISKFREESPACE->lastError ;
		space->freeBytesAvailable = lpDISKFREESPACE->freeBytesAvailable ;
		space->totalNumberOfBytes = lpDISKFREESPACE->totalNumberOfBytes ;
		space->totalNumberOfFreeBytes = lpDISKFREESPACE->totalNumberOfFreeBytes;
		rc = true;
	}			
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/EVENTMSG")))
	{
		EVENTMSG _EVENTMSG, *lpEVENTMSG=NULL;
		lpEVENTMSG = getEVENTMSGFields(env, object, &_EVENTMSG);
		if(lpEVENTMSG == NULL)goto fail;
		EVENTMSG *mesg = (EVENTMSG *)point;
		mesg->hwnd = lpEVENTMSG->hwnd ;
   		mesg->message = lpEVENTMSG->message ;
  		mesg->paramH = lpEVENTMSG->paramH ;
   		mesg->paramL = lpEVENTMSG->paramL ;
    	mesg->time = lpEVENTMSG->time;
    	rc = true;
	}
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/FLASHWINFO")))
	{
		FLASHWINFO _FLASHWINFO, *lpFLASHWINFO=NULL;
		lpFLASHWINFO = getFLASHWINFOFields(env, object, &_FLASHWINFO);
		if(lpFLASHWINFO == NULL)goto fail;
		FLASHWINFO *info = (FLASHWINFO *)point;
		info->cbSize = lpFLASHWINFO->cbSize ;
		info->hwnd = lpFLASHWINFO->hwnd ;
		info->dwFlags = lpFLASHWINFO->dwFlags ;
		info->uCount = lpFLASHWINFO->uCount ;
		info->dwTimeout = lpFLASHWINFO->dwTimeout;
   	 	rc = true;
	}
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/LOGICALDRIVES")))
	{
		LOGICALDRIVES _LOGICALDRIVES, *lpLOGICALDRIVES=NULL;
		lpLOGICALDRIVES = getLOGICALDRIVESFields(env, object, &_LOGICALDRIVES);
		if(lpLOGICALDRIVES == NULL)goto fail;
		LOGICALDRIVES *drives = (LOGICALDRIVES *)point;
		drives->returnValue = lpLOGICALDRIVES->returnValue ;
		drives->lastError = lpLOGICALDRIVES->lastError ;
    	rc = true;
	}
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MEMORYSTATUS")))
	{
		MEMORYSTATUSEX _MEMORYSTATUS, *lpMEMORYSTATUS=NULL;
		lpMEMORYSTATUS = getMEMORYSTATUSFields(env, object, &_MEMORYSTATUS);
		if(lpMEMORYSTATUS == NULL)goto fail;
		MEMORYSTATUSEX *status = (MEMORYSTATUSEX *)point;
		status->dwLength = lpMEMORYSTATUS->dwLength ;
		status->dwMemoryLoad = lpMEMORYSTATUS->dwMemoryLoad ;
		status->ullTotalPhys = lpMEMORYSTATUS->ullTotalPhys ;
		status->ullAvailPhys = lpMEMORYSTATUS->ullAvailPhys ;
		status->ullTotalPageFile = lpMEMORYSTATUS->ullTotalPageFile;
		status->ullAvailPageFile = lpMEMORYSTATUS->ullAvailPageFile;
		status->ullTotalVirtual = lpMEMORYSTATUS->ullTotalVirtual;
		status->ullAvailVirtual = lpMEMORYSTATUS->ullAvailVirtual;
    	rc = true;
	}		
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MSLLHOOKSTRUCT")))
	{
		MSLLHOOKSTRUCT _MSLLHOOKSTRUCT, *lpMSLLHOOKSTRUCT=NULL;
		lpMSLLHOOKSTRUCT = getMSLLHOOKSTRUCTFields(env, object, &_MSLLHOOKSTRUCT);
		if(lpMSLLHOOKSTRUCT == NULL)goto fail;
		MSLLHOOKSTRUCT *msll = (MSLLHOOKSTRUCT *)point;
		msll->pt.x = lpMSLLHOOKSTRUCT->pt.x ;
		msll->pt.y = lpMSLLHOOKSTRUCT->pt.y ;
		msll->mouseData = lpMSLLHOOKSTRUCT->mouseData ;
		msll->flags = lpMSLLHOOKSTRUCT->flags ;
		msll->time = lpMSLLHOOKSTRUCT->time;
		msll->dwExtraInfo = lpMSLLHOOKSTRUCT->dwExtraInfo;
   		rc = true;
	}	
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/KBDLLHOOKSTRUCT")))
	{
		KBDLLHOOKSTRUCT _KBDLLHOOKSTRUCT, *lpKBDLLHOOKSTRUCT=NULL;
		lpKBDLLHOOKSTRUCT = getKBDLLHOOKSTRUCTFields(env, object, &_KBDLLHOOKSTRUCT);
		if(lpKBDLLHOOKSTRUCT == NULL)goto fail;
		KBDLLHOOKSTRUCT *kbdll = (KBDLLHOOKSTRUCT *)point;
		kbdll->vkCode = lpKBDLLHOOKSTRUCT->vkCode ;
		kbdll->scanCode = lpKBDLLHOOKSTRUCT->scanCode ;
		kbdll->flags = lpKBDLLHOOKSTRUCT->flags ;
		kbdll->time = lpKBDLLHOOKSTRUCT->time;
		kbdll->dwExtraInfo = lpKBDLLHOOKSTRUCT->dwExtraInfo;
   		rc = true;
  }
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/SHFILEINFOA")))
	{
		SHFILEINFOA _SHFILEINFOA, *lpSHFILEINFOA=NULL;
		lpSHFILEINFOA = getSHFILEINFOAFields(env, object, &_SHFILEINFOA);
		if(lpSHFILEINFOA == NULL)goto fail;
		SHFILEINFOA *info = (SHFILEINFOA *)point;
		*info->szDisplayName = *lpSHFILEINFOA->szDisplayName ;
		*info->szTypeName = *lpSHFILEINFOA->szTypeName ;
   		rc = true;
 	}
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/SHFILEINFOW")))
	{
		SHFILEINFOW _SHFILEINFOW, *lpSHFILEINFOW=NULL;
		lpSHFILEINFOW = getSHFILEINFOWFields(env, object, &_SHFILEINFOW);
		if(lpSHFILEINFOW == NULL)goto fail;
		SHFILEINFOW *info = (SHFILEINFOW *)point;
		*info->szDisplayName = *lpSHFILEINFOW->szDisplayName ;
		*info->szTypeName = *lpSHFILEINFOW->szTypeName ;
   		rc = true;
  }
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/SHFILEOPSTRUCTA")))
	{
		SHFILEOPSTRUCTA _SHFILEOPSTRUCTA, *lpSHFILEOPSTRUCTA=NULL;
		lpSHFILEOPSTRUCTA = getSHFILEOPSTRUCTAFields(env, object, &_SHFILEOPSTRUCTA);
		if(lpSHFILEOPSTRUCTA == NULL)goto fail;
		SHFILEOPSTRUCTA *file = (SHFILEOPSTRUCTA *)point;
		file->hwnd = lpSHFILEOPSTRUCTA->hwnd ;
		file->wFunc = lpSHFILEOPSTRUCTA->wFunc ;
		file->pFrom = lpSHFILEOPSTRUCTA->pFrom ;
		file->pTo = lpSHFILEOPSTRUCTA->pTo;
		file->fFlags = lpSHFILEOPSTRUCTA->fFlags;
		file->fAnyOperationsAborted = lpSHFILEOPSTRUCTA->fAnyOperationsAborted;
		file->hNameMappings = lpSHFILEOPSTRUCTA->hNameMappings;
		file->lpszProgressTitle = lpSHFILEOPSTRUCTA->lpszProgressTitle;
   		rc = true;
  	}
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/SHFILEOPSTRUCTW")))
	{
		SHFILEOPSTRUCTW _SHFILEOPSTRUCTW, *lpSHFILEOPSTRUCTW=NULL;
		lpSHFILEOPSTRUCTW = getSHFILEOPSTRUCTWFields(env, object, &_SHFILEOPSTRUCTW);
		if(lpSHFILEOPSTRUCTW == NULL)goto fail;
		SHFILEOPSTRUCTW *file = (SHFILEOPSTRUCTW *)point;
		file->hwnd = lpSHFILEOPSTRUCTW->hwnd ;
		file->wFunc = lpSHFILEOPSTRUCTW->wFunc ;
		file->pFrom = lpSHFILEOPSTRUCTW->pFrom ;
		file->pTo = lpSHFILEOPSTRUCTW->pTo;
		file->fFlags = lpSHFILEOPSTRUCTW->fFlags;
		file->fAnyOperationsAborted = lpSHFILEOPSTRUCTW->fAnyOperationsAborted;
		file->hNameMappings = lpSHFILEOPSTRUCTW->hNameMappings;
		file->lpszProgressTitle = lpSHFILEOPSTRUCTW->lpszProgressTitle;
    	rc = true;
  	}			
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/SYSTEMINFO")))
	{
		SYSTEM_INFO _SYSTEM_INFO, *lpSYSTEM_INFO=NULL;
		lpSYSTEM_INFO = getSYSTEM_INFOFields(env, object, &_SYSTEM_INFO);
		if(lpSYSTEM_INFO == NULL)goto fail;
		SYSTEM_INFO *info = (SYSTEM_INFO *)point;
		info->dwOemId = lpSYSTEM_INFO->dwOemId ;
		info->dwPageSize = lpSYSTEM_INFO->dwPageSize ;
		info->lpMinimumApplicationAddress = lpSYSTEM_INFO->lpMinimumApplicationAddress ;
		info->lpMaximumApplicationAddress = lpSYSTEM_INFO->lpMaximumApplicationAddress;
		info->dwActiveProcessorMask = lpSYSTEM_INFO->dwActiveProcessorMask;
		info->dwNumberOfProcessors = lpSYSTEM_INFO->dwNumberOfProcessors;
		info->dwProcessorType = lpSYSTEM_INFO->dwProcessorType;
		info->dwAllocationGranularity = lpSYSTEM_INFO->dwAllocationGranularity;
    	rc = true;
  	}
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MOUSEHOOKSTRUCT")))
	{
		MOUSEHOOKSTRUCT _MOUSEHOOKSTRUCT, *lpMOUSEHOOKSTRUCT=NULL;
		lpMOUSEHOOKSTRUCT = getMOUSEHOOKSTRUCTFields(env, object, &_MOUSEHOOKSTRUCT);
		if(lpMOUSEHOOKSTRUCT == NULL)goto fail;
		MOUSEHOOKSTRUCT *hook = (MOUSEHOOKSTRUCT *)point;
		hook->pt.x = lpMOUSEHOOKSTRUCT->pt.x ;
		hook->pt.y = lpMOUSEHOOKSTRUCT->pt.y ;
		hook->wHitTestCode = lpMOUSEHOOKSTRUCT->wHitTestCode ;
		hook->dwExtraInfo = lpMOUSEHOOKSTRUCT->dwExtraInfo;
		hook->hwnd = lpMOUSEHOOKSTRUCT->hwnd;
    	rc = true;
  	}
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MCI_OPEN_PARMSA")))
	{
		MCI_OPEN_PARMSA _MCI_OPEN_PARMSA, *lpMCI_OPEN_PARMSA=NULL;
		lpMCI_OPEN_PARMSA = getMCI_OPEN_PARMSAFields(env, object, &_MCI_OPEN_PARMSA);
		if(lpMCI_OPEN_PARMSA == NULL)goto fail;
		MCI_OPEN_PARMSA *parmsA = (MCI_OPEN_PARMSA *)point;
		parmsA->dwCallback = lpMCI_OPEN_PARMSA->dwCallback ;
		parmsA->wDeviceID = lpMCI_OPEN_PARMSA->wDeviceID;
		parmsA->lpstrDeviceType = lpMCI_OPEN_PARMSA->lpstrDeviceType ;
		parmsA->lpstrElementName = lpMCI_OPEN_PARMSA->lpstrElementName;
		parmsA->lpstrAlias = lpMCI_OPEN_PARMSA->lpstrAlias;
    	rc = true;
  	}
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MCI_OPEN_PARMSW")))
	{
		MCI_OPEN_PARMSW _MCI_OPEN_PARMSW, *lpMCI_OPEN_PARMSW=NULL;
		lpMCI_OPEN_PARMSW = getMCI_OPEN_PARMSWFields(env, object, &_MCI_OPEN_PARMSW);
		if(lpMCI_OPEN_PARMSW == NULL)goto fail;
		MCI_OPEN_PARMSW *parmsW = (MCI_OPEN_PARMSW *)point;
		parmsW->dwCallback = lpMCI_OPEN_PARMSW->dwCallback ;
		parmsW->wDeviceID = lpMCI_OPEN_PARMSW->wDeviceID;
		parmsW->lpstrDeviceType = lpMCI_OPEN_PARMSW->lpstrDeviceType ;
		parmsW->lpstrElementName = lpMCI_OPEN_PARMSW->lpstrElementName;
		parmsW->lpstrAlias = lpMCI_OPEN_PARMSW->lpstrAlias;
    	rc = true;
  	}
	else if(env->IsInstanceOf(object, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MCI_STATUS_PARMS")))
	{
		MCI_STATUS_PARMS _MCI_STATUS_PARMS, *lpMCI_STATUS_PARMS=NULL;
		lpMCI_STATUS_PARMS = getMCI_STATUS_PARMSFields(env, object, &_MCI_STATUS_PARMS);
		if(lpMCI_STATUS_PARMS == NULL)goto fail;
		MCI_STATUS_PARMS *parms = (MCI_STATUS_PARMS *)point;
		parms->dwCallback = lpMCI_STATUS_PARMS->dwCallback ;
		parms->dwReturn = lpMCI_STATUS_PARMS->dwReturn;
		parms->dwItem = lpMCI_STATUS_PARMS->dwItem ;
		parms->dwTrack = lpMCI_STATUS_PARMS->dwTrack;
    	rc = true;
  	}
	else
		rc = false;
	fail:
		return rc;
}
#endif


#ifndef NO_GetAsyncKeyState
JNIEXPORT jshort JNICALL Swt_extension2_NATIVE(GetAsyncKeyState)
	(JNIEnv *env, jclass that, jint arg0)
{
	jshort rc = 0;
	rc = (jshort)GetAsyncKeyState(arg0);
	return rc;
}
#endif

#ifndef NO_AnimateWindow
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(AnimateWindow)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2)
{
	typedef BOOL (WINAPI *lpfn) (HWND hwnd, long dwTime, long dwFlags);
	jboolean rc = 0;	
	{
		static int initialized = 0;
		static HMODULE hm = NULL;
		static lpfn fp = NULL;
		rc = 0;
		if (!initialized) {
			if (!hm) hm = LoadLibrary(AnimateWindow_LIB);
			if (hm) fp = (lpfn)GetProcAddress(hm, "AnimateWindow");
			initialized = 1;
		}
		if (fp) {
			rc = (jboolean)fp((HWND)arg0, arg1, arg2);
		}
	}
	return rc;
}
#endif

#ifndef NO_CloseHandle
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(CloseHandle)
	(JNIEnv *env, jclass that, jint arg0)
{
	jboolean rc = 0;
	rc = (jboolean)CloseHandle((HANDLE)arg0);
	return rc;
}
#endif


#ifndef NO_AppendMenuW
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(AppendMenuW)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jcharArray arg3)
{
	jchar *lparg3=NULL;
	jboolean rc = 0;
	if (arg3) if ((lparg3 = env->GetCharArrayElements(arg3, NULL)) == NULL) goto fail;
	rc = (jboolean)AppendMenuW((HMENU)arg0, arg1, arg2, (LPCWSTR)lparg3);
	fail:
	if (arg3 && lparg3) env->ReleaseCharArrayElements( arg3, lparg3, 0);
	return rc;
}
#endif

#ifndef NO_AppendMenuA
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(AppendMenuA)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jbyteArray arg3)
{
	jbyte *lparg3=NULL;
	jboolean rc = 0;
	if (arg3) if ((lparg3 = env->GetByteArrayElements(arg3, NULL)) == NULL) goto fail;
	rc = (jboolean)AppendMenuA((HMENU)arg0, arg1, arg2, (LPSTR)lparg3);
	fail:
	if (arg3 && lparg3) env->ReleaseByteArrayElements( arg3, lparg3, 0);
	return rc;
}
#endif

#ifndef NO_InsertMenuW
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(InsertMenuW)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jint arg3, jcharArray arg4)
{
	jchar *lparg4=NULL;
	jboolean rc = 0;
	if (arg4) if ((lparg4 = env->GetCharArrayElements(arg4, NULL)) == NULL) goto fail;
	rc = (jboolean)InsertMenuW((HMENU)arg0, arg1, arg2, arg3, (LPCWSTR)lparg4);
	fail:
	if (arg4 && lparg4) env->ReleaseCharArrayElements( arg4, lparg4, 0);
	return rc;
}
#endif

#ifndef NO_InsertMenuA
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(InsertMenuA)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jint arg3, jbyteArray arg4)
{
	jbyte *lparg4=NULL;
	jboolean rc = 0;
	if (arg4) if ((lparg4 = env->GetByteArrayElements(arg4, NULL)) == NULL) goto fail;
	rc = (jboolean)InsertMenuA((HMENU)arg0, arg1, arg2, arg3, (LPSTR)lparg4);
	fail:
	if (arg4 && lparg4) env->ReleaseByteArrayElements( arg4, lparg4, 0);
	return rc;
}
#endif

#ifndef NO_TrackPopupMenu
JNIEXPORT jint JNICALL Swt_extension2_NATIVE(TrackPopupMenu)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jint arg3, jint arg4, jint arg5, jobject arg6)
{
	RECT _arg6, *lparg6=NULL;
	jint rc = 0;
	if (arg6) if ((lparg6 = getRECTFields(env, arg6, &_arg6)) == NULL) goto fail;
	rc = (jint)TrackPopupMenu((HMENU)arg0, arg1, arg2, arg3, arg4, (HWND)arg5, lparg6);
	fail:
	if (arg6 && lparg6) setRECTFields(env, arg6, lparg6);
	return rc;
}
#endif

#ifndef NO_SetClassLongA
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SetClassLongA)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2)
{
	jint rc = 0;
	rc = (jint)SetClassLongA((HWND)arg0, arg1, arg2);
	return rc;
}
#endif

#ifndef NO_SetClassLongW
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SetClassLongW)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2)
{
	jint rc = 0;
	rc = (jint)SetClassLongW((HWND)arg0, arg1, arg2);
	return rc;
}
#endif

#ifndef NO_GetClassLongA
JNIEXPORT jint JNICALL Swt_extension_NATIVE(GetClassLongA)
	(JNIEnv *env, jclass that, jint arg0, jint arg1)
{
	jint rc = 0;
	rc = (jint)GetClassLongA((HWND)arg0, arg1);
	return rc;
}
#endif

#ifndef NO_GetClassLongW
JNIEXPORT jint JNICALL Swt_extension_NATIVE(GetClassLongW)
	(JNIEnv *env, jclass that, jint arg0, jint arg1)
{
	jint rc = 0;
	rc = (jint)GetClassLongW((HWND)arg0, arg1);
	return rc;
}
#endif

#ifndef NO_SetMenuItemBitmaps
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SetMenuItemBitmaps)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jint arg3, jint arg4)
{
	jint rc = 0;
	rc = (jint)SetMenuItemBitmaps((HMENU)arg0, arg1, arg2, (HBITMAP)arg3, (HBITMAP)arg4);
	return rc;
}
#endif

#ifndef NO_SetLayeredWindowAttributes
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(SetLayeredWindowAttributes)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jbyte arg2, jint arg3)
{
	typedef BOOL (WINAPI *lpfn) (HWND arg0, COLORREF arg1, BYTE arg2, DWORD arg3);
	jboolean rc = 0;
	static int initialized = 0;
	static HMODULE hm = NULL;
	static lpfn fp = NULL;
	rc = 0;
	if (!initialized) {
		if (!hm) hm = LoadLibrary(SetLayeredWindowAttributes_LIB);
		if (hm) fp = (lpfn)GetProcAddress(hm, "SetLayeredWindowAttributes");
		initialized = 1;
	}
	if (fp) {
		rc = (jboolean)fp((HWND)arg0, arg1, arg2, arg3);
	}
	return rc;
}
#endif

#ifndef NO_GetLayeredWindowAttributes
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(GetLayeredWindowAttributes)
	(JNIEnv *env, jclass that, jint arg0, jintArray arg1, jbyteArray arg2, jintArray arg3)
{
	typedef BOOL (WINAPI *lpfn) (HWND arg0, COLORREF* arg1, BYTE* arg2, DWORD* arg3);
	jint *lparg1=NULL;
	jbyte *lparg2=NULL;
	jint *lparg3=NULL;
	jboolean rc = 0;
	if (arg1) if ((lparg1 = env->GetIntArrayElements(arg1, NULL)) == NULL) goto fail;
	if (arg2) if ((lparg2 = env->GetByteArrayElements(arg2, NULL)) == NULL) goto fail;
	if (arg3) if ((lparg3 = env->GetIntArrayElements(arg3, NULL)) == NULL) goto fail;
	{
		static int initialized = 0;
		static HMODULE hm = NULL;
		static lpfn fp = NULL;
		rc = 0;
		if (!initialized) {
			if (!hm) hm = LoadLibrary(GetLayeredWindowAttributes_LIB);
			if (hm) fp = (lpfn)GetProcAddress(hm, "GetLayeredWindowAttributes");
			initialized = 1;
		}
		if (fp) {
			rc = (jboolean)fp((HWND)arg0, (COLORREF*)lparg1, (BYTE*)lparg2, (DWORD*)lparg3);
		}
	}
	fail:
	if (arg3 && lparg3) env->ReleaseIntArrayElements(arg3, lparg3, 0);
	if (arg2 && lparg2) env->ReleaseByteArrayElements(arg2, lparg2, 0);
	if (arg1 && lparg1) env->ReleaseIntArrayElements(arg1, lparg1, 0);
	return rc;
}
#endif

#ifndef NO_SetWindowLongPtrA
JNIEXPORT jint JNICALL Swt_extension2_NATIVE(SetWindowLongPtrA)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2)
{
	jint rc = 0;
	rc = (jint)SetWindowLongPtrA((HWND)arg0, arg1, (LONG_PTR)arg2);
	return rc;
}
#endif

#ifndef NO_SetWindowLongPtrW
JNIEXPORT jint JNICALL Swt_extension2_NATIVE(SetWindowLongPtrW)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2)
{
	jint rc = 0;
	rc = (jint)SetWindowLongPtrW((HWND)arg0, arg1, (LONG_PTR)arg2);
	return rc;
}
#endif

#ifndef NO_Mouse_1Event
JNIEXPORT void JNICALL Swt_extension_NATIVE(Mouse_1Event)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jint arg3, jint arg4)
{
	mouse_event(arg0, arg1, arg2, arg3, arg4);
}
#endif

#ifndef NO_Keyboard_1Event
JNIEXPORT void JNICALL Swt_extension_NATIVE(Keyboard_1Event)
	(JNIEnv *env, jclass that, jbyte arg0, jbyte arg1, jint arg2, jint arg3)
{
	keybd_event(arg0, arg1, arg2, arg3);
}
#endif

#ifndef NO_MciSendCommandA
JNIEXPORT jint JNICALL Swt_extension_NATIVE(MciSendCommandA)
(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jobject arg3) 
{ 
	jint rc = -1;
	if (arg3){
		if(env->IsInstanceOf(arg3, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MCI_OPEN_PARMSA"))){
			MCI_OPEN_PARMSA  _arg3, *lparg3;
			if (arg3) if ((lparg3 = getMCI_OPEN_PARMSAFields(env, arg3, &_arg3)) == NULL) goto fail;
			rc = (jint)mciSendCommandA(arg0, arg1, arg2, (DWORD)lparg3);
			fail:
			if (arg3 && lparg3) setMCI_OPEN_PARMSAFields(env, arg3, lparg3);
			
		}
		else if(env->IsInstanceOf(arg3, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MCI_STATUS_PARMS"))){
			MCI_STATUS_PARMS  _arg3, *lparg3;
			if (arg3) if ((lparg3 = getMCI_STATUS_PARMSFields(env, arg3, &_arg3)) == NULL) goto fail1;
			rc = (jint)mciSendCommandA(arg0, arg1, arg2, (DWORD)lparg3);
			fail1:
			if (arg3 && lparg3) setMCI_STATUS_PARMSFields(env, arg3, lparg3);
		}
	}
	else rc = (jint)mciSendCommandA(arg0, arg1, arg2, 0);
	return rc;
}
#endif

#ifndef NO_MciSendCommandW
JNIEXPORT jint JNICALL Swt_extension_NATIVE(MciSendCommandW)
(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jobject arg3) 
{ 
	jint rc = -1;
	if (arg3){
		if(env->IsInstanceOf(arg3, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MCI_OPEN_PARMSW"))){
			MCI_OPEN_PARMSW  _arg3, *lparg3;
			if (arg3) if ((lparg3 = getMCI_OPEN_PARMSWFields(env, arg3, &_arg3)) == NULL) goto fail;
			rc = (jint)mciSendCommandW(arg0, arg1, arg2, (DWORD)lparg3);
			fail:
			if (arg3 && lparg3) setMCI_OPEN_PARMSWFields(env, arg3, lparg3);
			
		}
		else if(env->IsInstanceOf(arg3, env->FindClass("org/sf/feeling/swt/win32/internal/extension/MCI_STATUS_PARMS"))){
			MCI_STATUS_PARMS  _arg3, *lparg3;
			if (arg3) if ((lparg3 = getMCI_STATUS_PARMSFields(env, arg3, &_arg3)) == NULL) goto fail1;
			rc = (jint)mciSendCommandW(arg0, arg1, arg2, (DWORD)lparg3);
			fail1:
			if (arg3 && lparg3) setMCI_STATUS_PARMSFields(env, arg3, lparg3);
		}
	}
	else rc = (jint)mciSendCommandW(arg0, arg1, arg2, 0);
	return rc;
	
}
#endif


#ifndef NO_GetFileVersionInfo

struct TRANSLATION {
  WORD langID;   // language ID
  WORD charset;    // character set (code page)
} m_translation;

JNIEXPORT jintArray JNICALL Swt_extension_NATIVE(GetFileVersionInfo)
	(JNIEnv *env, jobject that, jstring fileName)
{
	jintArray versions = NULL;
 	char *lpFileName=NULL;
	if (fileName) if ((lpFileName = jstringToNative(env,fileName))==NULL)
		return NULL;
 	m_translation.charset = 1252;  // default = ANSI code page
	VS_FIXEDFILEINFO *pFixedFileInfo = new VS_FIXEDFILEINFO();
	memset((VS_FIXEDFILEINFO*)pFixedFileInfo, 0, sizeof(VS_FIXEDFILEINFO)); 
 	// get module handle
 	HMODULE hModule = LoadLibrary(lpFileName);
 	if (hModule==NULL){
 		if(lpFileName!=NULL)delete[] lpFileName;
 		return NULL;
 	}
 	// read file version info
 	DWORD dwDummyHandle; // will always be set to zero
 	DWORD len = GetFileVersionInfoSize(lpFileName, &dwDummyHandle);
 	if (len <= 0)
 	{
   		FreeLibrary(hModule);
   		delete[] lpFileName;
   		return NULL;
 	}

 	BYTE* m_pVersionInfo = new BYTE[len]; // allocate version info
 	if (!::GetFileVersionInfo(lpFileName, 0, len, m_pVersionInfo))
 	{
   		FreeLibrary(hModule);
   		if(lpFileName!=NULL)delete[] lpFileName;
   		return NULL;
 	}
 	
 	if(lpFileName!=NULL)delete[] lpFileName;
 	
 	LPVOID lpvi;
 	UINT iLen;
 	if (!VerQueryValue(m_pVersionInfo, _T("\\"), &lpvi, &iLen))
 	{
   	FreeLibrary(hModule);
   	return NULL;
 	}
	*pFixedFileInfo = *(VS_FIXEDFILEINFO*)lpvi;

 	// Get translation info
 	if(VerQueryValue(m_pVersionInfo, _T("\\VarFileInfo\\Translation"), &lpvi, &iLen) && iLen >= 4) {
  		m_translation = *(TRANSLATION*)lpvi;
 	}
  	FreeLibrary(hModule);
  	if(pFixedFileInfo->dwSignature != VS_FFI_SIGNATURE){
  		delete [] m_pVersionInfo;
  		return NULL;
  	}
  
  	jint buffer[5];
  	buffer[0] = (jint)m_pVersionInfo;
 	buffer[1] =  HIWORD(pFixedFileInfo->dwFileVersionMS);  
	buffer[2] =  LOWORD(pFixedFileInfo->dwFileVersionMS); 
	buffer[3] =  HIWORD(pFixedFileInfo->dwFileVersionLS); 
	buffer[4] =  LOWORD(pFixedFileInfo->dwFileVersionLS); 
	
	versions = env->NewIntArray(5);
	env->SetIntArrayRegion(versions, 0, 5, buffer);
  
  	return versions;
}
#endif

#ifndef NO_GetFileVersionInfoValue
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(GetFileVersionInfoValue)
	(JNIEnv *env, jobject that, jint handle, jstring keyName)
{
	if(handle == 0 || keyName == NULL)
 		return NULL;

 	char *lpKeyName=NULL;
	if (keyName) if ((lpKeyName = jstringToNative(env,keyName))==NULL)
		return NULL;
 	jstring rc = NULL;
  // To get a string value must pass query in the form
  //
  //  "\StringFileInfo\<langID><codepage>\keyname"
  //
  // where <langID><codepage> is the languageID concatenated with the
  // code page, in hex. Wow.
  //
  	char *query = (char*)malloc(sizeof(char)*MAX_PATH);
  	sprintf(query, "\\StringFileInfo\\%04x%04x\\%s", m_translation.langID,
  	m_translation.charset, lpKeyName);
  	char* pVal;
  	UINT iLenVal;
  	if(VerQueryValue((VS_FIXEDFILEINFO *)handle, (LPTSTR)query,
   		(LPVOID*)&pVal, &iLenVal)) {
   		rc = nativeTojstring(env,pVal);
  	}

  	if(query!=NULL)delete[] query;
  	if(lpKeyName!=NULL)delete[] lpKeyName;
 	return  rc;
}
#endif

#ifndef NO_FileVersionInfo_1delete
JNIEXPORT void JNICALL Swt_extension_NATIVE(FileVersionInfo_1delete)
	(JNIEnv *env, jclass that, jint arg0)
{
	delete [] (VS_FIXEDFILEINFO *)arg0;
}
#endif


#ifndef NO_MessageBeep
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(MessageBeep)
	(JNIEnv *env, jclass that, jint arg0)
{
	jboolean rc = 0;
	rc = (jboolean)MessageBeep(arg0);
	return rc;
}
#endif

#ifndef NO_Beep
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(Beep)
	(JNIEnv *env, jclass that, jint arg0, jint arg1)
{
	jboolean rc = 0;
	rc = (jboolean)Beep(arg0, arg1);
	return rc;
}
#endif

#ifndef NO_MixerGetNumDevs
JNIEXPORT jint JNICALL Swt_extension_NATIVE(MixerGetNumDevs)
	(JNIEnv *env, jclass that)
{
	jint rc = 0;
	rc = (jboolean)mixerGetNumDevs();
	return rc;
}
#endif

#ifndef NO_MixerOpen
JNIEXPORT jint JNICALL Swt_extension_NATIVE(MixerOpen)
	(JNIEnv *env, jclass that, jobject arg0, jint arg1, jint arg2, jint arg3, jint arg4)
{
	jint rc = 0;
	HMIXER hMixer;
  	rc = mixerOpen(&hMixer,arg1,arg2,arg3,arg4);
  	jclass CMixer = env->GetObjectClass(arg0);
  	jfieldID fid_mixer = env->GetFieldID(CMixer, "hMixer", jfd_int);
  	env->SetIntField(arg0, fid_mixer, (jint)hMixer);
	return rc;
}
#endif

#ifndef NO_MixerClose
JNIEXPORT jint JNICALL Swt_extension_NATIVE(MixerClose)
	(JNIEnv *env, jclass that, jint arg0)
{
	jint rc = 0;
	rc = (jboolean)mixerClose((HMIXER)arg0);
	return rc;
}
#endif

#ifndef NO_MixerGetDevCapsA
JNIEXPORT jint JNICALL Swt_extension_NATIVE(MixerGetDevCapsA)
	(JNIEnv *env, jclass that, jint arg0, jobject arg1)
{
	jint rc = MMSYSERR_ERROR;
	MIXERCAPSA _arg1, *lparg1=NULL;
	if (arg1) if ((lparg1 = getMIXERCAPSAFields(env, arg1, &_arg1)) == NULL) goto fail;
	rc = (jint)mixerGetDevCapsA(arg0, (MIXERCAPSA *)lparg1, sizeof(MIXERCAPSA));
	fail:
	if (arg1 && lparg1) setMIXERCAPSAFields(env, arg1, lparg1);
	return rc;
}
#endif

#ifndef NO_MixerGetDevCapsW
JNIEXPORT jint JNICALL Swt_extension_NATIVE(MixerGetDevCapsW)
	(JNIEnv *env, jclass that, jint arg0, jobject arg1)
{
	jint rc = MMSYSERR_ERROR;
	MIXERCAPSW _arg1, *lparg1=NULL;
	if (arg1) if ((lparg1 = getMIXERCAPSWFields(env, arg1, &_arg1)) == NULL) goto fail;
	rc = (jint)mixerGetDevCapsW(arg0, (MIXERCAPSW *)lparg1, sizeof(MIXERCAPSW));
	fail:
	if (arg1 && lparg1) setMIXERCAPSWFields(env, arg1, lparg1);
	return rc;
}
#endif

#ifndef NO_GetControlDetails
BOOL GetDevCaps(UINT m_uMxId, LPMIXERCAPS pmxcaps) 
{ 
  if(MMSYSERR_NOERROR != mixerGetDevCaps(m_uMxId, pmxcaps, sizeof(MIXERCAPS))) 
	return FALSE; 
 
  return TRUE; 
}
#endif

#ifndef NO_GetControlDetails
BOOL GetControlDetails(UINT m_uMxId, LPMIXERCONTROLDETAILS pmxcd, DWORD fdwDetails) 
{ 
 if (MMSYSERR_NOERROR !=  
    mixerGetControlDetails((HMIXEROBJ)m_uMxId, pmxcd, fdwDetails)) 
  { 
    return FALSE; 
  } 
 
  return TRUE; 
} 
#endif

#ifndef NO_MixerGetLineInfo

BOOL MixerGetLineInfo(UINT m_uMxId, LPMIXERLINE pmxl, DWORD fdwInfo) 
{ 
 if (MMSYSERR_NOERROR !=  
    mixerGetLineInfo((HMIXEROBJ)m_uMxId, pmxl, fdwInfo)) 
  { 
    return FALSE; 
  } 
 
  return TRUE; 
}
#endif 

#ifndef NO_GetLineInfo
BOOL GetLineInfo(UINT m_uMxId, LPMIXERLINE pmxl, DWORD dwDstType, DWORD dwSrcType) 
{ 
  MIXERCAPS mxcaps; 
  if(!GetDevCaps(m_uMxId, &mxcaps)) 
    return false; 
  UINT u=0; 
  do 
  { 
    pmxl->dwDestination=u; 
    pmxl->cbStruct=sizeof(*pmxl); 
    u++; 
    if(!MixerGetLineInfo(m_uMxId, pmxl,MIXER_GETLINEINFOF_DESTINATION)) 
    continue; 
  } 
  while((u<mxcaps.cDestinations)&&(pmxl->dwComponentType!=dwDstType)); 
     
    if (u > mxcaps.cDestinations) 
   return FALSE; 
 
  if (dwDstType == dwSrcType)  
    return TRUE; 
   
  pmxl->dwDestination=u; 
  UINT cConnections=(UINT)pmxl->cConnections; 
  UINT v=0; 
  u--; 
  do 
  { 
    pmxl->cbStruct  = sizeof(*pmxl); 
    pmxl->dwDestination = u; 
    pmxl->dwSource  = v; 
    v++; 
 
    if (! MixerGetLineInfo(m_uMxId, pmxl, MIXER_GETLINEINFOF_SOURCE)) 
    continue; 
   
  } while ((v < cConnections) && (pmxl->dwComponentType != dwSrcType)); 
 
  if((v > cConnections) || (pmxl->dwComponentType !=dwSrcType)) 
   return FALSE; 
 
  return TRUE; 
} 
#endif

#ifndef NO_MixerGetLineControls
BOOL MixerGetLineControls(UINT m_uMxId, LPMIXERLINECONTROLS pmxlc, DWORD fdwControls) 
{ 
 if (MMSYSERR_NOERROR !=  
    mixerGetLineControls((HMIXEROBJ)m_uMxId, pmxlc, fdwControls)) 
  { 
    return FALSE; 
  } 
 
  return TRUE; 
} 
#endif 

#ifndef NO_GetLineControl 
BOOL GetLineControl(UINT m_uMxId, LPMIXERCONTROL pmxc, LPMIXERLINE pmxl, DWORD dwType) 
{ 
  	LPMIXERCONTROL  pamxctrl; 
  	DWORD cbmxctrls=sizeof(*pamxctrl)*(UINT)pmxl->cControls; 
  	pamxctrl = (LPMIXERCONTROL)LocalAlloc(LPTR, cbmxctrls); 
 	MIXERLINECONTROLS mxlc; 
 	mxlc.cbStruct   =sizeof(mxlc); 
 	mxlc.dwLineID   =pmxl->dwLineID; 
 	mxlc.cControls  =pmxl->cControls; 
 	mxlc.cbmxctrl   =sizeof(*pamxctrl); 
 	mxlc.dwControlType=dwType; 
 	mxlc.pamxctrl   = pamxctrl; 
 	if (! MixerGetLineControls(m_uMxId, &mxlc, MIXER_GETLINECONTROLSF_ONEBYTYPE)) 
    return FALSE; 
 
  	memcpy(pmxc, pamxctrl, sizeof(*pamxctrl)); 
   
  	return TRUE; 
}
#endif 

#ifndef NO_GetMasterMuteControl 
BOOL GetMasterMuteControl(UINT m_uMxId, DWORD *controlId)
{
	// get dwLineID
	MIXERLINE mxl;
	mxl.cbStruct = sizeof(MIXERLINE);
	mxl.dwComponentType = MIXERLINE_COMPONENTTYPE_DST_SPEAKERS;
	if (::mixerGetLineInfo((HMIXEROBJ)m_uMxId,
						 &mxl,
						 MIXER_OBJECTF_HMIXER |
						 MIXER_GETLINEINFOF_COMPONENTTYPE)
		!= MMSYSERR_NOERROR)
		return FALSE;

	// get dwControlID
	MIXERCONTROL mxc;
	MIXERLINECONTROLS mxlc;
	mxlc.cbStruct = sizeof(MIXERLINECONTROLS);
	mxlc.dwLineID = mxl.dwLineID;
	mxlc.dwControlType = MIXERCONTROL_CONTROLTYPE_MUTE;
	mxlc.cControls = 1;
	mxlc.cbmxctrl = sizeof(MIXERCONTROL);
	mxlc.pamxctrl = &mxc;
	if (::mixerGetLineControls((HMIXEROBJ)m_uMxId,
							 &mxlc,
							 MIXER_OBJECTF_HMIXER |
							 MIXER_GETLINECONTROLSF_ONEBYTYPE)
		!= MMSYSERR_NOERROR)
		return FALSE;
	*controlId = mxc.dwControlID;
	return TRUE;
}
#endif


#ifndef NO_IsMixerMasterMute 
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(IsMixerMasterMute)
	(JNIEnv *env, jclass that, jint arg0)
{
	DWORD m_dwMuteControlID;
	if (!GetMasterMuteControl(arg0, &m_dwMuteControlID))
		return TRUE;

	MIXERCONTROLDETAILS_BOOLEAN mxcdMute;
	MIXERCONTROLDETAILS mxcd;
	mxcd.cbStruct = sizeof(MIXERCONTROLDETAILS);
	mxcd.dwControlID = m_dwMuteControlID;
	mxcd.cChannels = 1;
	mxcd.cMultipleItems = 0;
	mxcd.cbDetails = sizeof(MIXERCONTROLDETAILS_BOOLEAN);
	mxcd.paDetails = &mxcdMute;
	if (::mixerGetControlDetails((HMIXEROBJ)arg0,
								 &mxcd,
								 MIXER_OBJECTF_HMIXER |
								 MIXER_GETCONTROLDETAILSF_VALUE)
		!= MMSYSERR_NOERROR)
		return TRUE;
	
	return (jboolean)mxcdMute.fValue;
}
#endif


#ifndef NO_SetMixerMasterMute
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(SetMixerMasterMute)
	(JNIEnv *env, jclass that, jint arg0, jboolean arg1)
{
	DWORD m_dwMuteControlID;
	if (!GetMasterMuteControl(arg0,&m_dwMuteControlID))
		return FALSE;

	MIXERCONTROLDETAILS_BOOLEAN mxcdMute = { arg1 };
	MIXERCONTROLDETAILS mxcd;
	mxcd.cbStruct = sizeof(MIXERCONTROLDETAILS);
	mxcd.dwControlID = m_dwMuteControlID;
	mxcd.cChannels = 1;
	mxcd.cMultipleItems = 0;
	mxcd.cbDetails = sizeof(MIXERCONTROLDETAILS_BOOLEAN);
	mxcd.paDetails = &mxcdMute;
	if (::mixerSetControlDetails((HMIXEROBJ)arg0,
								 &mxcd,
								 MIXER_OBJECTF_HMIXER |
								 MIXER_SETCONTROLDETAILSF_VALUE)
		!= MMSYSERR_NOERROR)
		return FALSE;
	
	return TRUE;
}
#endif

#ifndef NO_GetPlaybackVolume
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(GetPlaybackVolume)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jobject arg2, jboolean arg3)
{
	//获取音频线路信息  
 	MIXERLINE mxl; 
 	if(!GetLineInfo(arg0, &mxl,MIXERLINE_COMPONENTTYPE_DST_SPEAKERS,arg1)) 
   	return false; 
 	//获取指定音频线路指定控制类型的信息  
 	MIXERCONTROL mxc; 
 	if(!GetLineControl(arg0, &mxc,&mxl,MIXERCONTROL_CONTROLTYPE_VOLUME)) 
   	return false; 
     
  	MIXERCONTROLDETAILS mxcd; 
  	MIXERCONTROLDETAILS_UNSIGNED  mxcd_u1; 
  	MIXERCONTROLDETAILS_UNSIGNED  mxcd_u[2]; 
 
  	mxcd.cbStruct   = sizeof(mxcd); 
  	mxcd.dwControlID  = mxc.dwControlID; 
  	mxcd.cMultipleItems = 0; 
 
  	if (arg3) 
  	{ 
    	mxcd.cChannels  = 1; 
    	mxcd.cbDetails  = sizeof(mxcd_u1); 
    	mxcd.paDetails  = &mxcd_u1; 
     
    	if (! GetControlDetails(arg0, &mxcd, MIXER_GETCONTROLDETAILSF_VALUE)) 
    	return FALSE; 
 
 		jclass CMixer = env->GetObjectClass(arg2);
  		jfieldID fid_leftChannelVolume = env->GetFieldID(CMixer, "leftChannelVolume", jfd_int);
  		env->SetIntField(arg2, fid_leftChannelVolume, mxcd_u1.dwValue);
  	} 
  	else 
  	{ 
   	 	mxcd.cChannels  = mxl.cChannels; 
   	 	mxcd.cbDetails  = sizeof(*mxcd_u); 
    	mxcd.paDetails  = mxcd_u; 
     
    	if (! GetControlDetails(arg0, &mxcd, MIXER_GETCONTROLDETAILSF_VALUE)) 
    		return FALSE; 
 
 		jclass CMixer = env->GetObjectClass(arg2);
  		jfieldID fid_leftChannelVolume = env->GetFieldID(CMixer, "leftChannelVolume", jfd_int);
  		env->SetIntField(arg2, fid_leftChannelVolume, mxcd_u[0].dwValue);
  		jfieldID fid_rightChannelVolume = env->GetFieldID(CMixer, "rightChannelVolume", jfd_int);
  		env->SetIntField(arg2, fid_rightChannelVolume, mxcd_u[1].dwValue);
  	} 
  	return TRUE; 
}
#endif

#ifndef NO_SetControlDetails
BOOL SetControlDetails(UINT m_uMxId, LPMIXERCONTROLDETAILS pmxcd, DWORD fdwDetails) 
{ 
 	if (MMSYSERR_NOERROR != mixerSetControlDetails((HMIXEROBJ)m_uMxId, pmxcd, fdwDetails)) 
  	{ 
    	return FALSE; 
  	} 
 
  	return TRUE; 
} 
#endif

#ifndef NO_GetRecordingVolume
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(GetRecordingVolume)
(JNIEnv *env, jclass that, jint arg0, jint arg1, jobject arg2, jboolean arg3) 
{ 
  	MIXERLINE mxl; 
  	if (! GetLineInfo(arg0, &mxl, MIXERLINE_COMPONENTTYPE_DST_WAVEIN, arg1)) 
    	return FALSE; 
 
  	MIXERCONTROL mxc; 
  	if (! GetLineControl(arg0, &mxc, &mxl, MIXERCONTROL_CONTROLTYPE_VOLUME)) 
    	return FALSE; 
 
  	MIXERCONTROLDETAILS mxcd; 
  	MIXERCONTROLDETAILS_UNSIGNED  mxcd_u[2]; 
 
  	mxcd.cbStruct   = sizeof(mxcd); 
  	mxcd.dwControlID  = mxc.dwControlID; 
  	mxcd.cChannels  = arg3 ? 1 : mxl.cChannels; 
  	mxcd.cMultipleItems = 0; 
  	mxcd.cbDetails  = sizeof(*mxcd_u); 
  	mxcd.paDetails  = mxcd_u; 
   
  	if (! GetControlDetails(arg0, &mxcd, MIXER_GETCONTROLDETAILSF_VALUE)) 
    	return FALSE; 
 
 	jclass CMixer = env->GetObjectClass(arg2);
  	jfieldID fid_leftChannelVolume = env->GetFieldID(CMixer, "leftChannelVolume", jfd_int);
  	env->SetIntField(arg2, fid_leftChannelVolume, mxcd_u[0].dwValue);
  	jfieldID fid_rightChannelVolume = env->GetFieldID(CMixer, "rightChannelVolume", jfd_int);
  	env->SetIntField(arg2, fid_rightChannelVolume, mxcd_u[1].dwValue); 
  	return TRUE; 
}  
#endif 

#ifndef NO_SetPlaybackVolume
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(SetPlaybackVolume)
(JNIEnv *env, jclass that, jint arg0, jint arg1, jobject arg2, jboolean arg3)
{ 
  	//获取音频线路信息  
 	MIXERLINE mxl; 
 	if(! GetLineInfo(arg0, &mxl, MIXERLINE_COMPONENTTYPE_DST_SPEAKERS, arg1)) 
   		return FALSE; 
 	//获取指定音频线路指定控制类型的信息  
 	MIXERCONTROL mxc; 
 	if(!GetLineControl(arg0,&mxc,&mxl,MIXERCONTROL_CONTROLTYPE_VOLUME)) 
   		return FALSE;  
   
 	jclass CMixer = env->GetObjectClass(arg2);
 	jfieldID fid_leftChannelVolume = env->GetFieldID(CMixer, "leftChannelVolume", jfd_int);
 	jfieldID fid_rightChannelVolume = env->GetFieldID(CMixer, "rightChannelVolume", jfd_int);
 
  	 
 	MIXERCONTROLDETAILS  mxcd; 
 	MIXERCONTROLDETAILS_UNSIGNED mxcd_u1; 
 	MIXERCONTROLDETAILS_UNSIGNED mxcd_u[2]; 
 
 	mxcd.cbStruct   = sizeof(mxcd); 
 	mxcd.dwControlID  = mxc.dwControlID; 
 	mxcd.cMultipleItems = 0; 
 	if(arg3) 
 	{ 
   		mxcd.cChannels=1; 
   		mxcd.cbDetails=sizeof(mxcd_u1); 
   		mxcd.paDetails=&mxcd_u1; 
   		mxcd_u1.dwValue= env->GetIntField(arg2, fid_leftChannelVolume); 
 	} 
 	else 
 	{ 
   		mxcd.cChannels=2; 
   		mxcd.cbDetails=sizeof(*mxcd_u); 
   		mxcd.paDetails=mxcd_u; 
   		mxcd_u[0].dwValue=env->GetIntField(arg2, fid_leftChannelVolume);
   		mxcd_u[1].dwValue=env->GetIntField(arg2, fid_rightChannelVolume);   
 	} 
 	if (! SetControlDetails(arg0,&mxcd, MIXER_OBJECTF_MIXER)) 
    	return FALSE; 
 
  	return TRUE; 
}
#endif



#ifndef NO_SetRecordingVolume
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(SetRecordingVolume)
(JNIEnv *env, jclass that, jint arg0, jint arg1, jobject arg2, jboolean arg3) 
{ 
  	MIXERLINE mxl; 
  	if (! GetLineInfo(arg0, &mxl, MIXERLINE_COMPONENTTYPE_DST_WAVEIN, arg1)) 
    	return FALSE; 
 
  	MIXERCONTROL mxc; 
  	if (! GetLineControl(arg0, &mxc, &mxl, MIXERCONTROL_CONTROLTYPE_VOLUME)) 
    	return FALSE; 
 
  	MIXERCONTROLDETAILS mxcd; 
  	MIXERCONTROLDETAILS_UNSIGNED  mxcd_u[2]; 
 
  	mxcd.cbStruct   = sizeof(mxcd); 
  	mxcd.dwControlID  = mxc.dwControlID; 
  	mxcd.cChannels  = arg3 ? 1 : mxl.cChannels; 
  	mxcd.cMultipleItems = 0; 
  	mxcd.cbDetails  = sizeof(*mxcd_u); 
  	mxcd.paDetails  = mxcd_u; 
  
  	jclass CMixer = env->GetObjectClass(arg2);
 	jfieldID fid_leftChannelVolume = env->GetFieldID(CMixer, "leftChannelVolume", jfd_int);
 	jfieldID fid_rightChannelVolume = env->GetFieldID(CMixer, "rightChannelVolume", jfd_int); 
  	mxcd_u[0].dwValue  = env->GetIntField(arg2, fid_leftChannelVolume); 
  	mxcd_u[1].dwValue  = env->GetIntField(arg2, fid_rightChannelVolume); 
 
  	if (! SetControlDetails(arg0, &mxcd, MIXER_OBJECTF_MIXER)) 
    	return FALSE; 
  
  	return TRUE; 
} 
#endif 

#ifndef NO_SetMixerMute 
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(SetMixerMute)
(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jboolean arg3) 
{
	MIXERLINE mxl;
	if (! GetLineInfo(arg0, &mxl, arg1, arg2))
	return FALSE;

	MIXERCONTROL mxc;
	if (! GetLineControl(arg0, &mxc, &mxl, MIXERCONTROL_CONTROLTYPE_MUTE))
	return FALSE;

	MIXERCONTROLDETAILS mxcd;
	MIXERCONTROLDETAILS_BOOLEAN mxcd_f;

	mxcd.cbStruct = sizeof(mxcd);
	mxcd.dwControlID = mxc.dwControlID;
	mxcd.cChannels = 1;
	mxcd.cMultipleItems = 0;
	mxcd.cbDetails = sizeof(mxcd_f);
	mxcd.paDetails = &mxcd_f;
	mxcd_f.fValue = arg3;
	
	if (! SetControlDetails(arg0, &mxcd, MIXER_OBJECTF_MIXER))
	return FALSE;

	return TRUE;
}
#endif 

#ifndef NO_IsMixerMute 
JNIEXPORT jboolean JNICALL Swt_extension_NATIVE(IsMixerMute)
(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2) 
{
	MIXERLINE mxl;
	if (! GetLineInfo(arg0, &mxl, arg1, arg2))
	return TRUE;

	MIXERCONTROL mxc;
	if (! GetLineControl(arg0, &mxc, &mxl, MIXERCONTROL_CONTROLTYPE_MUTE ))
	return TRUE;

	MIXERCONTROLDETAILS mxcd;
	MIXERCONTROLDETAILS_BOOLEAN mxcd_f;

	mxcd.cbStruct = sizeof(mxcd);
	mxcd.dwControlID = mxc.dwControlID;
	mxcd.cChannels = 1;
	mxcd.cMultipleItems = 0;
	mxcd.cbDetails = sizeof(mxcd_f);
	mxcd.paDetails = &mxcd_f;

	if (! GetControlDetails(arg0, &mxcd, MIXER_GETCONTROLDETAILSF_VALUE))
	return TRUE;

	return (jboolean)mxcd_f.fValue;
}
#endif 

#ifndef NO_MixerGetLineInfoA 
JNIEXPORT jint JNICALL Swt_extension_NATIVE(MixerGetLineInfoA)
(JNIEnv *env, jclass that, jint arg0, jobject arg1, jint arg2) 
{
	jint rc = MMSYSERR_ERROR;
	MIXERLINEA _arg1, *lparg1=NULL;
	if (arg1) if ((lparg1 = getMIXERLINEAFields(env, arg1, &_arg1)) == NULL) goto fail;
	rc = (jint)mixerGetLineInfoA((HMIXEROBJ)arg0, lparg1, arg2);
	fail:
	if (arg1 && lparg1) setMIXERLINEAFields(env, arg1, lparg1);
	return rc;
}
#endif 

#ifndef NO_MixerGetLineInfoW 
JNIEXPORT jint JNICALL Swt_extension_NATIVE(MixerGetLineInfoW)
(JNIEnv *env, jclass that, jint arg0, jobject arg1, jint arg2) 
{
	jint rc = MMSYSERR_ERROR;
	MIXERLINEW _arg1, *lparg1=NULL;
	if (arg1) if ((lparg1 = getMIXERLINEWFields(env, arg1, &_arg1)) == NULL) goto fail;
	rc = (jint)mixerGetLineInfoW((HMIXEROBJ)arg0, lparg1, arg2);
	fail:
	if (arg1 && lparg1) setMIXERLINEWFields(env, arg1, lparg1);
	return rc;
}
#endif 


#ifndef NO_CreateMutex 
JNIEXPORT jint JNICALL Swt_extension2_NATIVE(CreateMutex)
(JNIEnv *env, jclass that, jboolean initialOwner, jstring name ) 
{
	jint rc = 0;
	char *lpName=NULL;
	DWORD dwRet = 0;
	if (name) if ((lpName = jstringToNative(env,name))==NULL) goto fail;
	rc = (jint)CreateMutex(0,initialOwner,lpName);
	dwRet = GetLastError();
	if (rc)
	{
		if (ERROR_ALREADY_EXISTS == dwRet)
		{
			env->ThrowNew(env->FindClass(jcd_MutexException), "Mutex has been running");
			CloseHandle((HANDLE)rc);
  	  	goto fail;
		}
	}
	else
	{
		env->ThrowNew(env->FindClass(jcd_MutexException), "Error creating mutex");
		goto fail;
	}
	fail:
	if(lpName!=NULL)delete[] lpName;
	return rc;
}
#endif 

#ifndef NO_OpenProcess
JNIEXPORT jint JNICALL Swt_extension2_NATIVE(OpenProcess)
	(JNIEnv *env, jclass that, jint arg0, jboolean arg1, jint arg2)
{
	jint rc = 0;
	rc = (jint)OpenProcess(arg0, arg1, arg2);
	return rc;
}
#endif

#ifndef NO_EnablePrivilege
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(EnablePrivilege)
	(JNIEnv *env, jclass that, jint arg0, jboolean arg1, jboolean arg2)
{
	jboolean rc = false;
	BOOL wasEnable = false;
	typedef int(__stdcall *RtlAdjustPrivilege)(int Privilege, BOOL Enable, BOOL CurrentThread, BOOL *wasEnable);
	HINSTANCE hsNTDLL = LoadLibrary(NtDll_LIB);
	if(hsNTDLL == NULL){
		rc = false;
	}else{
		RtlAdjustPrivilege AdjustPrivilege = (RtlAdjustPrivilege)GetProcAddress(hsNTDLL, "RtlAdjustPrivilege");
		AdjustPrivilege(arg0, arg1, arg2, &wasEnable);
		rc = true;
	}
	return rc;
}
#endif

#ifndef NO_TerminateProcess
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(TerminateProcess)
	(JNIEnv *env, jclass that, jint arg0, jint arg1)
{
	jboolean rc = false;
	rc = TerminateProcess((HANDLE)arg0, arg1);
	return rc;
}
#endif

#ifndef NO_ReadProcessMemory
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(ReadProcessMemory)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jint arg3)
{
	jboolean rc = false;
	DWORD dwBytesRead = -1;
	rc = ReadProcessMemory((HANDLE)arg0, (LPVOID)arg1, (LPVOID)arg2, arg3, &dwBytesRead );
	return rc;
}
#endif

#ifndef NO_WriteProcessMemory
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(WriteProcessMemory)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jint arg3)
{
	jboolean rc = false;
	DWORD dwBytesWrite = -1;	
	rc = WriteProcessMemory((HANDLE)arg0, (LPVOID)arg1, (LPVOID)arg2, arg3, &dwBytesWrite );
	return rc;
}
#endif

#ifndef NO_VirtualProtectEx
JNIEXPORT jboolean JNICALL Swt_extension2_NATIVE(VirtualProtectEx)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jint arg3)
{
	jboolean rc = false;
	DWORD dwOldProtect = -1;	
	rc = VirtualProtectEx((HANDLE)arg0, (LPVOID)arg1, arg2, arg3, &dwOldProtect );
	return rc;
}
#endif

#ifndef NO_IShellFolder_1EnumObjects
JNIEXPORT jobject JNICALL Swt_extension_NATIVE(IShellFolder_1EnumObjects)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2)
{
	jobject rc = NULL;
	
	LPSHELLFOLDER root = (LPSHELLFOLDER)arg0;
	LPENUMIDLIST pEnumIDList = NULL;  
  	
	root->EnumObjects((HWND)arg1, arg2, &pEnumIDList);  
	if(pEnumIDList == NULL){
		return rc;
	}

	jclass CArrayList = env->FindClass(jcd_utilArrayList);
	if(CArrayList == NULL) { 
		return rc;
	}
	
	jmethodID mid_ArrayListConstructor = env->GetMethodID(CArrayList, "<init>", "()V");
	if(mid_ArrayListConstructor == NULL) { 
		return rc; 
	}
	
	jmethodID mid_add = env->GetMethodID(CArrayList, "add", "(Ljava/lang/Object;)Z");
	if(mid_add == NULL){ 
		return rc; 
	}
	
	rc = env->NewObject(CArrayList, mid_ArrayListConstructor);
		
	jclass CInteger = env->FindClass(jcd_Integer);
	if(CInteger == NULL) { 
		return rc;
	}
	 
	jmethodID mid_IntegerConstructor = env->GetMethodID(CInteger, "<init>", "(I)V");
	if(mid_IntegerConstructor == NULL) { 
		return rc; 
	}
		
	ULONG ulFetched = 0;  
 	LPITEMIDLIST pItem = NULL; 
	while(NOERROR == pEnumIDList->Next(1, &pItem, &ulFetched)){
 		jint pidl = (jint)pItem;
		jobject pidlObject = env->NewObject(CInteger, mid_IntegerConstructor, pidl);
		env->CallObjectMethod(rc, mid_add, pidlObject);
 	}
	
	pEnumIDList->Release();
	
	return rc;
}
#endif

#ifndef NO_IShellFolder_1GetDisplayNameOf
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(IShellFolder_1GetDisplayNameOf)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2)
{
	jstring rc = NULL;
	
	char displayName[_MAX_PATH];
	memset(displayName,'\0',sizeof(displayName));
	LPSHELLFOLDER root = (LPSHELLFOLDER)arg0;
	STRRET sName;
  	root->GetDisplayNameOf((LPITEMIDLIST)arg1, arg2, &sName);
  	StrRetToBuf(&sName, (LPITEMIDLIST)arg1, displayName, _MAX_PATH);
 	rc = nativeTojstring(env,displayName);
	return rc;
}
#endif

#ifndef NO_IShellFolder_1BindToObject
JNIEXPORT jint JNICALL Swt_extension_NATIVE(IShellFolder_1BindToObject)
	(JNIEnv *env, jclass that, jint arg0, jint arg1)
{
	jint rc = 0;
	LPSHELLFOLDER root = (LPSHELLFOLDER)arg0;
	LPSHELLFOLDER iSub;
  	root->BindToObject((LPITEMIDLIST)arg1, NULL, IID_IShellFolder, (void**)&iSub);
	rc = (jint)iSub;
	return rc;
}
#endif

#ifndef NO_IShellFolder_1GetIExtractIcon
JNIEXPORT jint JNICALL Swt_extension_NATIVE(IShellFolder_1GetIExtractIcon)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2)
{
	jint rc = 0;
	LPSHELLFOLDER root = (LPSHELLFOLDER)arg0;
	LPCITEMIDLIST pItem = (LPCITEMIDLIST)arg2;
	LPEXTRACTICON pExtractIcon = NULL;  
	root->GetUIObjectOf((HWND)arg1, 
      1, &pItem,  IID_IExtractIcon, 
      NULL, reinterpret_cast<LPVOID*>(&pExtractIcon)  ); 
  	if(pExtractIcon!=NULL)
		rc = (jint)pExtractIcon;
	return rc;
}
#endif

#ifndef NO_IShellFolder_1ParseDisplayName
JNIEXPORT jint JNICALL Swt_extension_NATIVE(IShellFolder_1ParseDisplayName)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jcharArray arg2)
{
	jint rc = 0;
	LPSHELLFOLDER root = (LPSHELLFOLDER)arg0;
	jchar *lpName=NULL;
	LPITEMIDLIST pItem = NULL; 
	if (arg2) if ((lpName = env->GetCharArrayElements(arg2, NULL)) == NULL) goto fail;
  	root->ParseDisplayName((HWND)arg1, NULL, (LPWSTR)lpName, NULL, &pItem, NULL);
	rc = (jint)pItem;
	fail:
	if(lpName!=NULL)
	env->ReleaseCharArrayElements(arg2, lpName, 0);
	return rc;
}
#endif

#ifndef NO_IShellFolder_1GetAttributesOf
JNIEXPORT jint JNICALL Swt_extension_NATIVE(IShellFolder_1GetAttributesOf)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2)
{
	jint rc = 0;
	LPSHELLFOLDER root = (LPSHELLFOLDER)arg0;
	LPITEMIDLIST  pItem = (LPITEMIDLIST )arg1;
	ULONG attributes = arg2;
	root->GetAttributesOf(1, (LPCITEMIDLIST *) &pItem, &attributes);
	return attributes;
}
#endif

#ifndef NO_SHGetMalloc
JNIEXPORT jint JNICALL Swt_extension_NATIVE(SHGetMalloc)
	(JNIEnv *env, jclass that)
{
	jint rc = 0;
	LPMALLOC pMalloc;
	SHGetMalloc(&pMalloc);
	rc = (jint)pMalloc;
	return rc;
}
#endif

#ifndef NO_ReleaseShellMalloc
JNIEXPORT jint JNICALL Swt_extension_NATIVE(ReleaseShellMalloc)
	(JNIEnv *env, jclass that, jint arg0)
{
	jint rc = 0;
	LPMALLOC pMalloc = (LPMALLOC)arg0;
	if(pMalloc!=NULL)
		rc = pMalloc->Release();
	return rc;
}
#endif

#ifndef NO_ReleaseIShellFolder
JNIEXPORT jint JNICALL Swt_extension_NATIVE(ReleaseIShellFolder)
	(JNIEnv *env, jclass that, jint arg0)
{
	jint rc = 0;
	LPSHELLFOLDER root = (LPSHELLFOLDER)arg0;
	if(root!=NULL)
		rc = root->Release();
	return rc;
}
#endif

#ifndef NO_ReleaseItemIdList
JNIEXPORT void JNICALL Swt_extension_NATIVE(ReleaseItemIdList)
	(JNIEnv *env, jclass that, jint arg0, jint arg1)
{
	LPMALLOC pMalloc = (LPMALLOC)arg0;
	LPITEMIDLIST  pItem = (LPITEMIDLIST )arg1;
	if(pItem!=NULL)
		pMalloc->Free(pItem);
}
#endif

#ifndef NO_IExtractIcon_1GetIconLocation
JNIEXPORT jstring JNICALL Swt_extension_NATIVE(IExtractIcon_1GetIconLocation)
	(JNIEnv *env, jclass that, jint arg0, jint arg1, jint arg2, jint arg3)
{
	jstring rc = NULL;
	LPEXTRACTICON  pExtractIcon = (LPEXTRACTICON)arg0;
	char szIconFile[_MAX_PATH];
	memset(szIconFile,'\0',sizeof(szIconFile));
	UINT u = arg3;
	pExtractIcon->GetIconLocation( arg1, szIconFile, _MAX_PATH, (int *)arg2, &u);
	rc = nativeTojstring(env,szIconFile);
	return rc;
}
#endif

#ifndef NO_IExtractIcon_1Extract
JNIEXPORT jint JNICALL Swt_extension_NATIVE(IExtractIcon_1Extract)
	(JNIEnv *env, jclass that, jint arg0, jstring arg1, jint arg2, jintArray arg3, jintArray arg4, jint arg5)
{
	char *lparg1=NULL;
	jint *lparg3=NULL;
	jint *lparg4=NULL;
	
	LPEXTRACTICON  pExtractIcon = (LPEXTRACTICON)arg0;
	
	jint rc = 0;
	
	if (arg1) if ((lparg1 = jstringToNative(env,arg1))==NULL) goto fail;
	if (arg3) if ((lparg3 = env->GetIntArrayElements( arg3, NULL)) == NULL) goto fail;
	if (arg4) if ((lparg4 = env->GetIntArrayElements( arg4, NULL)) == NULL) goto fail;
	
	rc = pExtractIcon->Extract((PCTSTR)lparg1, (int)arg2, (HICON FAR *)lparg3, (HICON FAR *)lparg4, arg5);
	
	fail:
	if (arg3 && lparg4) env->ReleaseIntArrayElements( arg4, lparg4, 0);
	if (arg2 && lparg3) env->ReleaseIntArrayElements( arg3, lparg3, 0);
	if (lparg1!=NULL) delete[] lparg1;
	
	return rc;

}
#endif

#ifndef NO_IShellFolder_1GetParent
JNIEXPORT jint JNICALL Swt_extension_NATIVE(IShellFolder_1GetParent)
	(JNIEnv *env, jclass that, jint arg0)
{
	jint rc = 0;
	LPITEMIDLIST pItem = (LPITEMIDLIST )arg0;
	LPSHELLFOLDER psfParent;
	SHBindToParent(pItem, IID_IShellFolder, (void **) &psfParent, NULL);
	rc = (jint)psfParent;
	return rc;
}
#endif

#ifndef NO_IShellFolder_1GetRelativeHandle
JNIEXPORT jint JNICALL Swt_extension_NATIVE(IShellFolder_1GetRelativeHandle)
	(JNIEnv *env, jclass that, jint arg0)
{
	jint rc = 0;
	LPITEMIDLIST pItem = (LPITEMIDLIST )arg0;
	LPSHELLFOLDER psfParent;
	LPCITEMIDLIST pidlRelative;
	SHBindToParent(pItem, IID_IShellFolder, (void **) &psfParent, &pidlRelative);
	if(psfParent!=NULL)
		psfParent->Release();
	rc = (jint)pidlRelative;
	return rc;
}
#endif


static LPITEMIDLIST GetNextIDL(LPCITEMIDLIST pidl)
{
   LPSTR lpMem=(LPSTR)pidl;
   lpMem+=pidl->mkid.cb;
   return (LPITEMIDLIST)lpMem;
}


static UINT GetSize(LPCITEMIDLIST pidl)
{
    UINT cbTotal = 0;
    if (pidl)
    {
        cbTotal += sizeof(pidl->mkid.cb);       // Null terminator
        while (pidl->mkid.cb)
        {
            cbTotal += pidl->mkid.cb;
            pidl = GetNextIDL(pidl);
        }
    }

    return cbTotal;
}

static LPITEMIDLIST Create(UINT cbSize)
{
    LPMALLOC lpMalloc;
    HRESULT  hr;
    LPITEMIDLIST pidl=0;

    hr=SHGetMalloc(&lpMalloc);

    if (FAILED(hr))
       return 0;

    pidl=(LPITEMIDLIST)lpMalloc->Alloc(cbSize);

    if (pidl)
        memset(pidl, 0, cbSize);      // zero-init for external task   alloc

    if (lpMalloc) lpMalloc->Release();

    return pidl;
}

#ifndef NO_IShellFolder_1ConcatPIDLs
JNIEXPORT jint JNICALL Swt_extension_NATIVE(IShellFolder_1ConcatPIDLs)
	(JNIEnv *env, jclass that, jint arg0, jint arg1)
{
	LPITEMIDLIST pidlNew;
    UINT cb1;
    UINT cb2;
	LPITEMIDLIST pidl1 = (LPITEMIDLIST )arg0;
	LPITEMIDLIST pidl2 = (LPITEMIDLIST )arg1;
	 if (pidl1)  //May be NULL
       cb1 = GetSize(pidl1) - sizeof(pidl1->mkid.cb);
    else
       cb1 = 0;

    cb2 = GetSize(pidl2);

    pidlNew = Create(cb1 + cb2);
    if (pidlNew)
    {
        if (pidl1)
           memcpy(pidlNew, pidl1, cb1);
        memcpy(((LPSTR)pidlNew) + cb1, pidl2, cb2);
    }
	return (jint)pidlNew;
}
#endif


#ifndef NO_GetSubMenu
JNIEXPORT jint JNICALL Swt_extension2_NATIVE(GetSubMenu)
	(JNIEnv *env, jclass that, jint arg0, jint arg1)
{
	jint rc = 0;
	rc = (jint)GetSubMenu((HMENU)arg0, arg1);
	return rc;
}
#endif

#ifndef NO_GetMenuItemID
JNIEXPORT jint JNICALL Swt_extension2_NATIVE(GetMenuItemID)
	(JNIEnv *env, jclass that, jint arg0, jint arg1)
{
	jint rc = 0;
	rc = (jint)GetMenuItemID((HMENU)arg0, arg1);
	return rc;
}
#endif

#ifndef NO_GetSystemProcessesSnap
JNIEXPORT jobject JNICALL Swt_extension_NATIVE(GetSystemProcessesSnap)
	(JNIEnv *env, jclass that)
{
	jobject rc = NULL;
	
	jclass CArrayList = env->FindClass(jcd_utilArrayList);
	if(CArrayList == NULL) { 
		return rc;
	}
	
	jmethodID mid_ArrayListConstructor = env->GetMethodID(CArrayList, "<init>", "()V");
	if(mid_ArrayListConstructor == NULL) { 
		return rc; 
	}
	
	jmethodID mid_add = env->GetMethodID(CArrayList, "add", "(Ljava/lang/Object;)Z");
	if(mid_add == NULL){ 
		return rc; 
	}
	
	rc = env->NewObject(CArrayList, mid_ArrayListConstructor);
	
	jclass CProcessEntry = env->FindClass("org/sf/feeling/swt/win32/extension/system/ProcessEntry");
	if(CProcessEntry == NULL) { 
		return rc;
	}
	 
	jmethodID mid_ProcessEntryConstructor = env->GetMethodID(CProcessEntry, "<init>", "(IIIILjava/lang/String;Ljava/lang/String;)V");
	if(mid_ProcessEntryConstructor == NULL) { 
		return rc; 
	}

	HANDLE hProcessSnap = NULL; 
  	PROCESSENTRY32 pe32 = {0}; 

  	//Take a snapshot of all processes in the system. 
  	hProcessSnap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0); 

  	if (hProcessSnap == INVALID_HANDLE_VALUE) 
    	return rc; 

  	//Fill in the size of the structure before using it. 
  	pe32.dwSize = sizeof(PROCESSENTRY32); 

  	//Walk the snapshot of the processes, and for each process, 
  	//display information. 
  	
  	typedef DWORD (WINAPI *PSAPI_GetProcessImageFileName)(HANDLE, LPTSTR, DWORD);
	PSAPI_GetProcessImageFileName GetProcessImageFileName = 0;
	
	typedef DWORD (WINAPI *Kernel32_QueryFullProcessImageName)(HANDLE, DWORD, LPTSTR, PDWORD);
	Kernel32_QueryFullProcessImageName QueryFullProcessImageName = 0;
	
	typedef DWORD (WINAPI *PSAPI_GetModuleFileNameEx)(HANDLE, HMODULE, LPTSTR, DWORD);
	PSAPI_GetModuleFileNameEx GetModuleFileNameEx = 0;

  	if (Process32First(hProcessSnap, &pe32)) 
  	{  	
  		HANDLE hToken;
		LUID DebugNameValue;
		TOKEN_PRIVILEGES Privileges;
		DWORD dwRet;

		OpenProcessToken(GetCurrentProcess(), TOKEN_ADJUST_PRIVILEGES | TOKEN_QUERY, &hToken);
		LookupPrivilegeValue(NULL, "SeDebugPrivilege", &DebugNameValue);
		Privileges.PrivilegeCount=1;
		Privileges.Privileges[0].Luid=DebugNameValue;
		Privileges.Privileges[0].Attributes=SE_PRIVILEGE_ENABLED;
		AdjustTokenPrivileges(hToken, FALSE,&Privileges, sizeof(Privileges), NULL, &dwRet);
		CloseHandle(hToken);
    	
    	do 
    	{    	
    		
			TCHAR file_name[_MAX_PATH];
			memset(file_name,0,sizeof(file_name));
			
			DWORD dwPriorityClass;
			
			BOOL flag = FALSE;
			HINSTANCE  kernel32Lib = LoadLibrary("Kernel32.dll");
			if(kernel32Lib){
				QueryFullProcessImageName = (Kernel32_QueryFullProcessImageName)GetProcAddress(kernel32Lib, "QueryFullProcessImageNameA");
				if(QueryFullProcessImageName){
					HANDLE hProcess= OpenProcess(PROCESS_QUERY_LIMITED_INFORMATION,FALSE,pe32.th32ProcessID);
					if( hProcess != NULL ){
						dwPriorityClass = GetPriorityClass( hProcess );
						DWORD  size = sizeof(file_name);
						(*QueryFullProcessImageName)(hProcess,0, file_name, &size);
					}
					flag = TRUE;
				}
				FreeLibrary( kernel32Lib ); 
			}
			
			if(!flag){
				HANDLE hProcess= OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,FALSE,pe32.th32ProcessID);
				if( hProcess != NULL ){
					dwPriorityClass = GetPriorityClass( hProcess );
					HINSTANCE  psapiLib = LoadLibrary("PSAPI.DLL");
 					if(psapiLib){
	 					GetProcessImageFileName = (PSAPI_GetProcessImageFileName)GetProcAddress(psapiLib, "GetProcessImageFileNameA");
						if(GetProcessImageFileName){
								(*GetProcessImageFileName)(hProcess,file_name,sizeof(file_name));
						}
						else{
							GetModuleFileNameEx = (PSAPI_GetModuleFileNameEx)GetProcAddress(psapiLib, "GetModuleFileNameExA");
							if(GetModuleFileNameEx){
								(*GetModuleFileNameEx)(hProcess,NULL,file_name,sizeof(file_name));
							}
						}
						FreeLibrary( psapiLib );
					} 
				}
			}
			
    		jobject lpObject = env->NewObject(CProcessEntry, mid_ProcessEntryConstructor, pe32.th32ProcessID, pe32.cntThreads, pe32.pcPriClassBase, dwPriorityClass, nativeTojstring(env, pe32.szExeFile), nativeTojstring(env, file_name));
    		env->CallObjectMethod(rc, mid_add, lpObject); 
   		} 
    	while (Process32Next(hProcessSnap, &pe32)); 
  	} 
  
  	//Do not forget to clean up the snapshot object. 
  	CloseHandle (hProcessSnap); 
  
  	return rc;
}
#endif

#ifndef NO_GetProcessModulesSnap
JNIEXPORT jobject JNICALL Swt_extension_NATIVE(GetProcessModulesSnap)
	(JNIEnv *env, jclass that, jint arg0)
{
	jobject rc = NULL;
	
	jclass CArrayList = env->FindClass(jcd_utilArrayList);
	if(CArrayList == NULL) { 
		return rc;
	}
	
	jmethodID mid_ArrayListConstructor = env->GetMethodID(CArrayList, "<init>", "()V");
	if(mid_ArrayListConstructor == NULL) { 
		return rc; 
	}
	
	jmethodID mid_add = env->GetMethodID(CArrayList, "add", "(Ljava/lang/Object;)Z");
	if(mid_add == NULL){ 
		return rc; 
	}
	
	rc = env->NewObject(CArrayList, mid_ArrayListConstructor);
	
	jclass CModuleEntry = env->FindClass("org/sf/feeling/swt/win32/extension/system/ModuleEntry");
	if(CModuleEntry == NULL) { 
		return rc;
	}
	 
	jmethodID mid_ModuleEntryConstructor = env->GetMethodID(CModuleEntry, "<init>", "(IIIIILjava/lang/String;Ljava/lang/String;)V");
	if(mid_ModuleEntryConstructor == NULL) { 
		return rc; 
	}

	HANDLE hModuleSnap = INVALID_HANDLE_VALUE;
  	MODULEENTRY32 me32;

  	// Take a snapshot of all modules in the specified process.
  	hModuleSnap = CreateToolhelp32Snapshot( TH32CS_SNAPMODULE, arg0 );
  	if( hModuleSnap == INVALID_HANDLE_VALUE )
  	{
    	return rc;
  	}

  	// Set the size of the structure before using it.
  	me32.dwSize = sizeof( MODULEENTRY32 );

  	// Retrieve information about the first module,
  	// and exit if unsuccessful
  	if( !Module32First( hModuleSnap, &me32 ) )
  	{
    	CloseHandle( hModuleSnap );           // clean the snapshot object
    	return rc;
  	}

  	// Now walk the module list of the process,
  	// and display information about each module
  	do
  	{
  		jobject lpObject = env->NewObject(CModuleEntry, mid_ModuleEntryConstructor, me32.th32ProcessID, me32.GlblcntUsage, me32.ProccntUsage, (DWORD) me32.modBaseAddr, me32.modBaseSize, nativeTojstring(env, me32.szModule), nativeTojstring(env, me32.szExePath));
    	env->CallObjectMethod(rc, mid_add, lpObject); 
  	}while( Module32Next( hModuleSnap, &me32 ) );
  	
  	//Do not forget to clean up the snapshot object. 
  	CloseHandle (hModuleSnap); 
  
  	return rc;
}
#endif

#ifndef NO_GetProcessThreadsSnap
JNIEXPORT jobject JNICALL Swt_extension_NATIVE(GetProcessThreadsSnap)
	(JNIEnv *env, jclass that, jint arg0)
{
	jobject rc = NULL;
	
	jclass CArrayList = env->FindClass(jcd_utilArrayList);
	if(CArrayList == NULL) { 
		return rc;
	}
	
	jmethodID mid_ArrayListConstructor = env->GetMethodID(CArrayList, "<init>", "()V");
	if(mid_ArrayListConstructor == NULL) { 
		return rc; 
	}
	
	jmethodID mid_add = env->GetMethodID(CArrayList, "add", "(Ljava/lang/Object;)Z");
	if(mid_add == NULL){ 
		return rc; 
	}
	
	rc = env->NewObject(CArrayList, mid_ArrayListConstructor);
	
	jclass CThreadEntry = env->FindClass("org/sf/feeling/swt/win32/extension/system/ThreadEntry");
	if(CThreadEntry == NULL) { 
		return rc;
	}
	 
	jmethodID mid_ThreadEntryConstructor = env->GetMethodID(CThreadEntry, "<init>", "(III)V");
	if(mid_ThreadEntryConstructor == NULL) { 
		return rc; 
	}

	HANDLE hThreadSnap = INVALID_HANDLE_VALUE;
  	THREADENTRY32 te32;

  	// Take a snapshot of all Threads in the specified process.
  	hThreadSnap = CreateToolhelp32Snapshot( TH32CS_SNAPTHREAD, 0 );
  	if( hThreadSnap == INVALID_HANDLE_VALUE )
  	{
    	return rc;
  	}

  	// Set the size of the structure before using it.
  	te32.dwSize = sizeof(THREADENTRY32); 

  	// Retrieve information about the first Thread,
  	// and exit if unsuccessful
  	if( !Thread32First( hThreadSnap, &te32 ) )
  	{
    	CloseHandle( hThreadSnap );           // clean the snapshot object
    	return rc;
  	}

  	// Now walk the Thread list of the process,
  	// and display information about each Thread
  	do
  	{
  		if( te32.th32OwnerProcessID == (DWORD)arg0 ){
  			jobject lpObject = env->NewObject(CThreadEntry, mid_ThreadEntryConstructor, te32.th32ThreadID , te32.tpBasePri , te32.tpDeltaPri);
    		env->CallObjectMethod(rc, mid_add, lpObject);
    	} 
  	}while( Thread32Next( hThreadSnap, &te32 ) );
  	
  	//Do not forget to clean up the snapshot object. 
  	CloseHandle (hThreadSnap); 
  
  	return rc;
}
#endif

}

