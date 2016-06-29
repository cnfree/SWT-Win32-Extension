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
#include <swt_extension_function.h>
#include <atlbase.h> 

extern "C" {
#define Swt_extension_NATIVE(func) Java_org_sf_feeling_swt_win32_internal_extension_Extension_##func

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeIGGI_1I)
  (JNIEnv* env, jclass, jint arg0, jstring arg1, jstring arg2, jint arg3, jint peer)
{
	CComBSTR bs1, bs2;
	bs1.Attach(jstobs(env, arg1));
	bs2.Attach(jstobs(env, arg2));
	int ret = ((FTYPE4)peer)(arg0, (int) bs1.m_str, (int) bs2.m_str, arg3);
	return ret;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeIII_1I)
  (JNIEnv* env, jclass, jint arg0, jint arg1, jint arg2, jint peer)
{
	int ret = ((FTYPE3)peer)(arg0, arg1, arg2);
	return ret;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeIIII_1I)
  (JNIEnv* env, jclass, jint arg0, jint arg1, jint arg2, jint arg3, jint peer)
{
	int ret = ((FTYPE4)peer)(arg0, arg1, arg2, arg3);
	return ret;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeII_1I)
  (JNIEnv* env, jclass, jint arg0, jint arg1, jint peer)
{
	int ret = ((FTYPE2)peer)(arg0, arg1);
	return ret;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeI_1I)
  (JNIEnv* env, jclass, jint arg0, jint peer)
{
	int ret = ((FTYPE1)peer)(arg0);
	return ret;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(Invoke_1I)
  (JNIEnv* env, jclass, jint peer)
{
	int ret = ((FTYPE0)peer)();
	return ret;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeG_1I)
  (JNIEnv* env, jclass, jstring arg0, jint peer)
{
	CComBSTR bs0;
	bs0.Attach(jstobs(env, arg0));
	int ret = ((FTYPE1)peer)((int)bs0.m_str);
	return ret;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeGG_1I)
  (JNIEnv* env, jclass, jstring arg0, jstring arg1, jint peer)
{
	CComBSTR bs0,bs1;
	bs0.Attach(jstobs(env, arg0));
	bs1.Attach(jstobs(env, arg1));
	int ret = ((FTYPE2)peer)((int)bs0.m_str, (int)bs1.m_str);
	return ret;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeP_1I)
  (JNIEnv* env, jclass, jbyteArray arg0, jint peer)
{
	jbyte* bytes = env->GetByteArrayElements(arg0, NULL);
	if (env->ExceptionOccurred()) {
		 env->ThrowNew(env->FindClass(jcd_Exception), "Error getting byteArrayElements");
		 return 0;
	}
	int ret = ((FTYPE1)peer)((int)bytes);
	env->ReleaseByteArrayElements(arg0, bytes, JNI_ABORT);
	return ret;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeIIIIO)
	(JNIEnv* env, jclass, jint arg0, jint arg1, jint arg2, jint arg3, jint peer)
{
	jint outparam = 0;
	int ret = ((FTYPE5)peer)(arg0, arg1, arg2, arg3, (int) &outparam);
	return outparam;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeIO)
	(JNIEnv* env, jclass, jint arg0, jint peer)
{
	jint outparam = 0;
	int ret = ((FTYPE2)peer)(arg0, (int) &outparam);
	return outparam;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeIGO)
	(JNIEnv* env, jclass, jint arg0, jstring arg1, jint peer)
{
	jint outparam = 0;
	CComBSTR bs1;
	bs1.Attach(jstobs(env, arg1));
	int ret = ((FTYPE3)peer)(arg0, (int) bs1.m_str, (int) &outparam);
	return outparam;
}

JNIEXPORT jint JNICALL Swt_extension_NATIVE(InvokeIG_1I)
  (JNIEnv* env, jclass, jint arg0, jstring arg1, jint peer)
{
	CComBSTR bs1;
	bs1.Attach(jstobs(env, arg1));
	int ret = ((FTYPE2)peer)(arg0, (int)bs1.m_str);
	return ret;
}

JNIEXPORT jbyteArray JNICALL Swt_extension_NATIVE(InvokeI_1S)
	(JNIEnv* env, jclass, jint arg0, jint returnSize, jint peer)
{
	jbyte* result = (jbyte*)((FTYPE1)peer)(arg0);
	if (result == 0) {
		return NULL;
	}
	jbyteArray ret = env->NewByteArray(returnSize);
	if (env->ExceptionOccurred()) {
		 env->ThrowNew(env->FindClass(jcd_Exception), "Error creating byteArray");
		 return NULL;
	}
	env->SetByteArrayRegion(ret, 0, returnSize, result);
	if (env->ExceptionOccurred()) {
		env->ThrowNew(env->FindClass(jcd_Exception), "Error setting byteArrayRegion");
		return NULL;
	}
	return ret;
}

JNIEXPORT jbyteArray JNICALL Swt_extension_NATIVE(InvokeP_1S)
  (JNIEnv* env, jclass, jbyteArray arg0, jint returnSize, jint peer)
{
	jbyte* bytes = env->GetByteArrayElements(arg0, NULL);
	if (env->ExceptionOccurred()) {
		env->ThrowNew(env->FindClass(jcd_Exception), "Error getting byteArrayElements");
		return NULL;
	}
	jbyte* result = (jbyte*)((FTYPE1)peer)((int)bytes);
	env->ReleaseByteArrayElements(arg0, bytes, JNI_ABORT);
	if (result == 0) {
		return NULL;
	}
	jbyteArray ret = env->NewByteArray(returnSize);
	if (env->ExceptionOccurred()) {
		env->ThrowNew(env->FindClass(jcd_Exception), "Error creating byteArray");
		return NULL;
	}
	env->SetByteArrayRegion(ret, 0, returnSize, result);
	if (env->ExceptionOccurred()) {
		env->ThrowNew(env->FindClass(jcd_Exception), "Error setting byteArrayRegion");
		return NULL;
	}
	return ret;
}

JNIEXPORT jbyteArray JNICALL Swt_extension_NATIVE(InvokePI_1S)
  (JNIEnv* env, jclass, jbyteArray arg0, jint arg1, jint returnSize, jint peer)
{
	jbyte* bytes = env->GetByteArrayElements(arg0, NULL);
	if (env->ExceptionOccurred()) {
		env->ThrowNew(env->FindClass(jcd_Exception), "Error getting byteArrayElements");
		return NULL;
	}
	jbyte* result = (jbyte*)((FTYPE2)peer)((int)bytes, arg1);
	env->ReleaseByteArrayElements(arg0, bytes, JNI_ABORT);
	if (result == 0) {
		return NULL;
	}
	jbyteArray ret = env->NewByteArray(returnSize);
	if (env->ExceptionOccurred()) {
		env->ThrowNew(env->FindClass(jcd_Exception), "Error creating byteArray");
		return NULL;
	}
	env->SetByteArrayRegion(ret, 0, returnSize, result);
	if (env->ExceptionOccurred()) {
		env->ThrowNew(env->FindClass(jcd_Exception), "Error setting byteArrayRegion");
		return NULL;
	}
	return ret;	
}

}