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

#ifndef INC_swt_extension_registry_H
#define INC_swt_extension_registry_H

#include <shlwapi.h>

static char* const jcd_KeyIterator        = "org/sf/feeling/swt/win32/extension/registry/KeyIterator";
static char* const jcd_RegistryException  = "org/sf/feeling/swt/win32/extension/registry/RegistryException";
static char* const jcd_RegistryKey        = "org/sf/feeling/swt/win32/extension/registry/RegistryKey";
static char* const jcd_RegistryValue      = "org/sf/feeling/swt/win32/extension/registry/RegistryValue";
static char* const jcd_RootKey            = "org/sf/feeling/swt/win32/extension/registry/RootKey";
static char* const jcd_ValueIterator      = "org/sf/feeling/swt/win32/extension/registry/ValueIterator";
static char* const jcd_ValueType          = "org/sf/feeling/swt/win32/extension/registry/ValueType";

static char* const jfd_KeyIterator        = "Lorg/sf/feeling/swt/win32/extension/registry/KeyIterator;";
static char* const jfd_RegistryException  = "Lorg/sf/feeling/swt/win32/extension/registry/RegistryException;";
static char* const jfd_RegistryKey        = "Lorg/sf/feeling/swt/win32/extension/registry/RegistryKey;";
static char* const jfd_RegistryValue      = "Lorg/sf/feeling/swt/win32/extension/registry/RegistryValue;";
static char* const jfd_RootKey            = "Lorg/sf/feeling/swt/win32/extension/registry/RootKey;";
static char* const jfd_ValueIterator      = "Lorg/sf/feeling/swt/win32/extension/registry/ValueIterator;";
static char* const jfd_ValueType          = "Lorg/sf/feeling/swt/win32/extension/registry/ValueType;";


int getRootKey(JNIEnv *env, jobject obj) {
    jclass CRegistryKey = env->GetObjectClass(obj);
    jfieldID fid_root = env->GetFieldID(CRegistryKey, "root", jfd_RootKey);
    if(fid_root == NULL) { return 0; }
	jobject obj_RootKey = env->GetObjectField(obj, fid_root);
    if(obj_RootKey == NULL) { return 0; }
	jclass CRootKey = env->GetObjectClass(obj_RootKey);
    if(CRootKey == NULL) { return 0; }
	jmethodID mid_getValue = env->GetMethodID(CRootKey, "getValue", "()I");
    if(mid_getValue == NULL) { return 0; }
	return env->CallIntMethod(obj_RootKey, mid_getValue);
} 

static int startKeyIteration(JNIEnv* env, jobject obj) {
	 jclass CKeyIterator = env->GetObjectClass(obj);
	 if(CKeyIterator == NULL) { return -1; }

	 jfieldID fid_key = env->GetFieldID(CKeyIterator, "key", jfd_RegistryKey);
	 if(fid_key == NULL) { return -1; }

	 jobject obj_RegistryKey = env->GetObjectField(obj, fid_key);
	 if(obj_RegistryKey == NULL) { return -1; }

	 int nRootKey = getRootKey(env, obj_RegistryKey);

	 jclass CRegistryKey = env->GetObjectClass(obj_RegistryKey);
	 if(CRegistryKey == NULL) { return -1; }

	 jfieldID fid_path = env->GetFieldID(CRegistryKey, "path", jfd_String);
	 if(fid_path == NULL) { return -1; }

	 jstring path = (jstring)env->GetObjectField(obj_RegistryKey, fid_path);
	 char *szPath = jstringToNative(env,path);
		
	 HKEY hkey;
	 if(RegOpenKeyEx((HKEY)nRootKey, szPath, 0, KEY_READ, &hkey) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Open key failed");
		  delete[] szPath;
		  return -1;
	 } 
	 delete[] szPath;
	 
	 int count = 0;
	 int maxsize = 0;
	 if(RegQueryInfoKey(hkey, NULL, NULL, NULL, (LPDWORD)&count, (LPDWORD)&maxsize, NULL, NULL, NULL, NULL, NULL, NULL) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Query info key failed");
		  return -1;
	 } 

	 jfieldID fid_hkey = env->GetFieldID(CKeyIterator, "hkey", jfd_int);
	 if(fid_hkey == NULL) { return 0; }
	 env->SetIntField(obj, fid_hkey, (DWORD)hkey);

	 jfieldID fid_maxsize = env->GetFieldID(CKeyIterator, "maxsize", jfd_int);
	 if(fid_maxsize == NULL) { return 0; }
	 env->SetIntField(obj, fid_maxsize, maxsize + 1);

	 jfieldID fid_index = env->GetFieldID(CKeyIterator, "index", jfd_int);
	 if(fid_index == NULL) { return 0; }
	 env->SetIntField(obj, fid_index, 0);

	 jfieldID fid_count = env->GetFieldID(CKeyIterator, "count", jfd_int);
	 if(fid_count == NULL) { return 0; }
	 env->SetIntField(obj, fid_count, count);

	 return count;
} 

static int startValueIteration(JNIEnv* env, jobject obj) {
	 jclass CValueIterator = env->GetObjectClass(obj);
	 if(CValueIterator == NULL) { return -1; }

	 jfieldID fid_key = env->GetFieldID(CValueIterator, "key", jfd_RegistryKey);
	 if(fid_key == NULL) { return -1; }

	 jobject obj_RegistryKey = env->GetObjectField(obj, fid_key);
	 if(obj_RegistryKey == NULL) { return -1; }

	 int nRootKey = getRootKey(env, obj_RegistryKey);

	 jclass CRegistryKey = env->GetObjectClass(obj_RegistryKey);
	 if(CRegistryKey == NULL) { return -1; }

	 jfieldID fid_path = env->GetFieldID(CRegistryKey, "path", jfd_String);
	 if(fid_path == NULL) { return -1; }

	 jstring path = (jstring)env->GetObjectField(obj_RegistryKey, fid_path);
	 char *szPath = jstringToNative(env, path);

	 HKEY hkey;
	 if(RegOpenKeyEx((HKEY)nRootKey, szPath, 0, KEY_READ, &hkey) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Open key failed");
		  delete[] szPath;
		  return -1;
	 }
	 delete[] szPath;

	 int count = 0;
	 int maxsize = 0;
	 if(RegQueryInfoKey(hkey, NULL, NULL, NULL, NULL, NULL, NULL, (LPDWORD)&count, (LPDWORD)&maxsize, NULL, NULL, NULL) != ERROR_SUCCESS) {
		  env->ThrowNew(env->FindClass(jcd_RegistryException), "Query info key failed");
		  return -1;
	 } 

	 jfieldID fid_hkey = env->GetFieldID(CValueIterator, "hkey", jfd_int);
	 if(fid_hkey == NULL) { return 0; }
	 env->SetIntField(obj, fid_hkey, (DWORD)hkey);

	 jfieldID fid_maxsize = env->GetFieldID(CValueIterator, "maxsize", jfd_int);
	 if(fid_maxsize == NULL) { return 0; }
	 env->SetIntField(obj, fid_maxsize, maxsize + 1);

	 jfieldID fid_index = env->GetFieldID(CValueIterator, "index", jfd_int);
	 if(fid_index == NULL) { return 0; }
	 env->SetIntField(obj, fid_index, 0);

	 jfieldID fid_count = env->GetFieldID(CValueIterator, "count", jfd_int);
	 if(fid_count == NULL) { return 0; }
	 env->SetIntField(obj, fid_count, count);

	 return count;
} 

#endif
