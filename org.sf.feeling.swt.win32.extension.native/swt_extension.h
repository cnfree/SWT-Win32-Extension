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

#ifndef INC_swt_extension_H
#define INC_swt_extension_H

#include <winsock2.h>
#include <windows.h>
#include <psapi.h>
#include <tlhelp32.h>
#include <wininet.h>
#include <shlwapi.h>
#include <shlobj.h>
#include <winuser.h>
#include <stdio.h>
#include <tchar.h>


/* Optional custom definitions to exclude some types */
#include "defines.h"


#define SetLayeredWindowAttributes_LIB "user32.dll"
#define GetLayeredWindowAttributes_LIB "user32.dll"
#define LockWorkStation_LIB "user32.dll"
#define GetLongPathName_LIB "kernel32.dll" 
#define AnimateWindow_LIB "user32.dll"
#define NtDll_LIB "ntdll.dll"

static char* const jfd_boolean  = "Z";
static char* const jfd_byte  = "B";
static char* const jfd_char  = "C";
static char* const jfd_short = "S";
static char* const jfd_int= "I";
static char* const jfd_long  = "J";
static char* const jfd_float = "F";
static char* const jfd_double= "D";

// primitive references
static char* const jfd_Boolean  = "Ljava/lang/Boolean;";
static char* const jfd_Byte  = "Ljava/lang/Byte;";
static char* const jfd_Character= "Ljava/lang/Character;";
static char* const jfd_Short = "Ljava/lang/Short;";
static char* const jfd_Integer  = "Ljava/lang/Integer;";
static char* const jfd_Long  = "Ljava/lang/Long;";
static char* const jfd_Float = "Ljava/lang/Double;";
static char* const jfd_Double= "Ljava/lang/Float;";

// references
static char* const jfd_Object= "Ljava/lang/Object;";
static char* const jfd_Class = "Ljava/lang/Class;";
static char* const jfd_String= "Ljava/lang/String;";

// primitive arrays
static char* const jfd_booleanArray= "[Z";
static char* const jfd_byteArray= "[B";
static char* const jfd_charArray= "[C";
static char* const jfd_shortArray  = "[S";
static char* const jfd_intArray = "[I";
static char* const jfd_longArray= "[J";
static char* const jfd_floatArray  = "[F";
static char* const jfd_doubleArray = "[D";

// primitive reference arrays
static char* const jfd_BooleanArray= "[Ljava/lang/Boolean;";
static char* const jfd_ByteArray= "[Ljava/lang/Byte;";
static char* const jfd_CharacterArray = "[Ljava/lang/Char;";
static char* const jfd_ShortArray  = "[Ljava/lang/Short;";
static char* const jfd_IntArray = "[Ljava/lang/Integer;";
static char* const jfd_LongArray= "[Ljava/lang/Long;";
static char* const jfd_FloatArray  = "[Ljava/lang/Double;";
static char* const jfd_DoubleArray = "[Ljava/lang/Float;";

// reference arrays
static char* const jfd_ObjectArray = "[Ljava/lang/Object;";
static char* const jfd_ClassArray  = "[Ljava/lang/Class;";
static char* const jfd_StringArray = "[Ljava/lang/String;";

// java.lang interfaces
static char* const jcd_Cloneable = "java/lang/Cloneable";
static char* const jcd_Comparable= "java/lang/Comparable";
static char* const jcd_Runnable  = "java/lang/Runnable";

// java.lang classes
static char* const jcd_Boolean= "java/lang/Boolean";
static char* const jcd_Byte= "java/lang/Byte";
static char* const jcd_Character = "java/lang/Character";
static char* const jcd_Class  = "java/lang/Class";
static char* const jcd_ClassLoader  = "java/lang/ClassLoader";
static char* const jcd_Compiler  = "java/lang/Compiler";
static char* const jcd_Double = "java/lang/Double";
static char* const jcd_Float  = "java/lang/Float";
static char* const jcd_InheritableThreadLocal= "java/lang/InheritableThreadLocal";
static char* const jcd_Integer= "java/lang/Integer";
static char* const jcd_Long= "java/lang/Long";
static char* const jcd_Math= "java/lang/Math";
static char* const jcd_Number = "java/lang/Number";
static char* const jcd_Object = "java/lang/Object";
static char* const jcd_Package= "java/lang/Package";
static char* const jcd_Process= "java/lang/Process";
static char* const jcd_Runtime= "java/lang/Runtime";
static char* const jcd_RuntimePermission  = "java/lang/RuntimePermission";
static char* const jcd_SecurityManager = "java/lang/SecurityManager";
static char* const jcd_Short  = "java/lang/Short";
static char* const jcd_StrictMath= "java/lang/StrictMath";
static char* const jcd_String = "java/lang/String";
static char* const jcd_StringBuffer = "java/lang/StringBuffer";
static char* const jcd_System = "java/lang/System";
static char* const jcd_Thread = "java/lang/Thread";
static char* const jcd_ThreadGroup  = "java/lang/ThreadGroup";
static char* const jcd_ThreadLocal  = "java/lang/ThreadLocal";
static char* const jcd_Throwable = "java/lang/Throwable";
static char* const jcd_Void= "java/lang/Void";

// java.lang exceptions
static char* const jcd_ArithmeticException= "java/lang/ArithmeticException";
static char* const jcd_ArrayIndexOutOfBoundsException = "java/lang/ArrayIndexOutOfBoundsException";
static char* const jcd_ArrayStoreException= "java/lang/ArrayStoreException";
static char* const jcd_ClassCastException = "java/lang/ClassCastException";
static char* const jcd_ClassNotFoundException= "java/lang/ClassNotFoundException";
static char* const jcd_CloneNotSupportedException  = "java/lang/CloneNotSupportedException";
static char* const jcd_Exception = "java/lang/Exception";
static char* const jcd_IllegalAccessException= "java/lang/IllegalAccessException";
static char* const jcd_IllegalArgumentException = "java/lang/IllegalArgumentException";
static char* const jcd_IllegalMonitorStateException= "java/lang/IllegalMonitorStateException";
static char* const jcd_IllegalStateException = "java/lang/IllegalStateException";
static char* const jcd_IllegalThreadStateException = "java/lang/IllegalThreadStateException";
static char* const jcd_IndexOutOfBoundsException= "java/lang/IndexOutOfBoundsException";
static char* const jcd_InstantiationException= "java/lang/InstantiationException";
static char* const jcd_InterruptedException  = "java/lang/InterruptedException";
static char* const jcd_NegativeArraySizeException  = "java/lang/NegativeArraySizeException";
static char* const jcd_NoSuchFieldException  = "java/lang/NoSuchFieldException";
static char* const jcd_NoSuchMethodException = "java/lang/NoSuchMethodException";
static char* const jcd_NullPointerException  = "java/lang/NullPointerException";
static char* const jcd_NumberFormatException = "java/lang/NumberFormatException";
static char* const jcd_RuntimeException= "java/lang/RuntimeException";
static char* const jcd_SecurityException  = "java/lang/SecurityException";
static char* const jcd_StringIndexOutOfBoundsException= "java/lang/StringIndexOutOfBoundsException";
static char* const jcd_UnsupportedOperationException  = "java/lang/UnsupportedOperationException";

// java.lang errors
static char* const jcd_AbstractMethodError= "java/lang/AbstractMethodError";
static char* const jcd_ClassCircularityError = "java/lang/ClassCircularityError";
static char* const jcd_ClassFormatError= "java/lang/ClassFormatError";
static char* const jcd_Error  = "java/lang/Error";
static char* const jcd_ExceptionInInitializerError = "java/lang/ExceptionInInitializerError";
static char* const jcd_IllegalAccessError = "java/lang/IllegalAccessError";
static char* const jcd_IncompatibleClassChangeError= "java/lang/IncompatibleClassChangeError";
static char* const jcd_InstantiationError = "java/lang/InstantiationError";
static char* const jcd_InternalError= "java/lang/InternalError";
static char* const jcd_LinkageError = "java/lang/LinkageError";
static char* const jcd_NoClassDefFoundError  = "java/lang/NoClassDefFoundError";
static char* const jcd_NoSuchFieldError= "java/lang/NoSuchFieldError";
static char* const jcd_NoSuchMethodError  = "java/lang/NoSuchMethodError";
static char* const jcd_OutOfMemoryError= "java/lang/OutOfMemoryError";
static char* const jcd_StackOverflowError = "java/lang/StackOverflowError";
static char* const jcd_ThreadDeath  = "java/lang/ThreadDeath";
static char* const jcd_UnknownError = "java/lang/UnknownError";
static char* const jcd_UnsatisfiedLinkError  = "java/lang/UnsatisfiedLinkError";
static char* const jcd_UnsupportedClassVersionError= "java/lang/UnsupportedClassVersionError";
static char* const jcd_VerifyError  = "java/lang/VerifyError";
static char* const jcd_VirtualMachineError= "java/lang/VirtualMachineError";

///// package java.io /////

// java.io interfaces
static char* const jcd_ioDataInput  = "java/io/DataInput";
static char* const jcd_ioDataOutput = "java/io/DataOutput";
static char* const jcd_ioExternalizable= "java/io/Externalizable";
static char* const jcd_ioFileFilter = "java/io/FileFilter";
static char* const jcd_ioFilenameFilter= "java/io/FilenameFilter";
static char* const jcd_ioObjectInput= "java/io/ObjectInput";
static char* const jcd_ioObjectInputValidation  = "java/io/ObjectInputValidation";
static char* const jcd_ioObjectOutput  = "java/io/ObjectOutput";
static char* const jcd_ioObjectStreamConstants  = "java/io/ObjectStreamConstants";
static char* const jcd_ioSerializable  = "java/io/Serializable";

// java.io classes
static char* const jcd_ioBufferedInputStream = "java/io/BufferedInputStream";
static char* const jcd_ioBufferedOutputStream= "java/io/BufferedOutputStream";
static char* const jcd_ioBufferedReader= "java/io/BufferedReader";
static char* const jcd_ioBufferedWriter= "java/io/BufferedWriter";
static char* const jcd_ioByteArrayInputStream= "java/io/ByteArrayInputStream";
static char* const jcd_ioByteArrayOutputStream  = "java/io/ByteArrayOutputStream";
static char* const jcd_ioCharArrayReader  = "java/io/CharArrayReader";
static char* const jcd_ioCharArrayWriter  = "java/io/CharArrayWriter";
static char* const jcd_ioDataInputStream  = "java/io/DataInputStream";
static char* const jcd_ioDataOutputStream = "java/io/DataOutputStream";
static char* const jcd_ioFile = "java/io/File";
static char* const jcd_ioFileDescriptor= "java/io/FileDescriptor";
static char* const jcd_ioFileInputStream  = "java/io/FileInputStream";
static char* const jcd_ioFileOutputStream = "java/io/FileOutputStream";
static char* const jcd_ioFilePermission= "java/io/FilePermission";
static char* const jcd_ioFileReader = "java/io/FileReader";
static char* const jcd_ioFileWriter = "java/io/FileWriter";
static char* const jcd_ioFilterInputStream= "java/io/FilterInputStream";
static char* const jcd_ioFilterOutputStream  = "java/io/FilterOutputStream";
static char* const jcd_ioFilterReader  = "java/io/FilterReader";
static char* const jcd_ioFilterWriter  = "java/io/FilterWriter";
static char* const jcd_ioInputStream= "java/io/InputStream";
static char* const jcd_ioInputStreamReader= "java/io/InputStreamReader";
static char* const jcd_ioLineNumberInputStream  = "java/io/LineNumberInputStream";
static char* const jcd_ioLineNumberReader = "java/io/LineNumberReader";
static char* const jcd_ioObjectInputStream= "java/io/ObjectInputStream";
static char* const jcd_ioObjectOutputStream  = "java/io/ObjectOutputStream";
static char* const jcd_ioObjectStreamClass= "java/io/ObjectStreamClass";
static char* const jcd_ioObjectStreamField= "java/io/ObjectStreamField";
static char* const jcd_ioOutputStream  = "java/io/OutputStream";
static char* const jcd_ioOutputStreamWriter  = "java/io/OutputStreamWriter";
static char* const jcd_ioPipedInputStream = "java/io/PipedInputStream";
static char* const jcd_ioPipedOutputStream= "java/io/PipedOutputStream";
static char* const jcd_ioPipedReader= "java/io/PipedReader";
static char* const jcd_ioPipedWriter= "java/io/PipedWriter";
static char* const jcd_ioPrintStream= "java/io/PrintStream";
static char* const jcd_ioPrintWriter= "java/io/PrintWriter";
static char* const jcd_ioPushbackInputStream = "java/io/PushbackInputStream";
static char* const jcd_ioPushbackReader= "java/io/PushbackReader";
static char* const jcd_ioRandomAccessFile = "java/io/RandomAccessFile";
static char* const jcd_ioReader  = "java/io/Reader";
static char* const jcd_ioSequenceInputStream = "java/io/SequenceInputStream";
static char* const jcd_ioSerializablePermission = "java/io/SerializablePermission";
static char* const jcd_ioStreamTokenizer  = "java/io/StreamTokenizer";
static char* const jcd_ioStringBufferInputStream= "java/io/StringBufferInputStream";
static char* const jcd_ioStringReader  = "java/io/StringReader";
static char* const jcd_ioStringWriter  = "java/io/StringWriter";
static char* const jcd_ioWriter  = "java/io/Writer";

// java.io exceptions
static char* const jcd_ioCharConversionException= "java/io/CharConversionException";
static char* const jcd_ioEOFException  = "java/io/EOFException";
static char* const jcd_ioFileNotFoundException  = "java/io/FileNotFoundException";
static char* const jcd_ioInterruptedIOException = "java/io/InterruptedIOException";
static char* const jcd_ioInvalidClassException  = "java/io/InvalidClassException";
static char* const jcd_ioInvalidObjectException = "java/io/InvalidObjectException";
static char* const jcd_ioIOException= "java/io/IOException";
static char* const jcd_ioNotActiveException  = "java/io/NotActiveException";
static char* const jcd_ioNotSerializableException  = "java/io/NotSerializableException";
static char* const jcd_ioObjectStreamException  = "java/io/ObjectStreamException";
static char* const jcd_ioOptionalDataException  = "java/io/OptionalDataException";
static char* const jcd_ioStreamCorruptedException  = "java/io/StreamCorruptedException";
static char* const jcd_ioSyncFailedException = "java/io/SyncFailedException";
static char* const jcd_ioUnsupportedEncodingException = "java/io/UnsupportedEncodingException";
static char* const jcd_ioUTFDataFormatException = "java/io/UTFDataFormatException";
static char* const jcd_ioWriteAbortedException  = "java/io/WriteAbortedException";

///// package java.util /////

// java.util Interfaces
static char* const jcd_utilCollection  = "java/util/Collection";
static char* const jcd_utilComparator  = "java/util/Comparator";
static char* const jcd_utilEnumeration = "java/util/Enumeration";
static char* const jcd_utilEventListener  = "java/util/EventListener";
static char* const jcd_utilIterator = "java/util/Iterator";
static char* const jcd_utilList  = "java/util/List";
static char* const jcd_utilListIterator= "java/util/ListIterator";
static char* const jcd_utilMap= "java/util/Map";
static char* const jcd_utilObserver = "java/util/Observer";
static char* const jcd_utilSet= "java/util/Set";
static char* const jcd_utilSortedMap= "java/util/SortedMap";
static char* const jcd_utilSortedSet= "java/util/SortedSet";

// java.util classes
static char* const jcd_utilAbstractCollection= "java/util/AbstractCollection";
static char* const jcd_utilAbstractList= "java/util/AbstractList";
static char* const jcd_utilAbstractMap = "java/util/AbstractMap";
static char* const jcd_utilAbstractSequentialList  = "java/util/AbstractSequentialList";
static char* const jcd_utilAbstractSet = "java/util/AbstractSet";
static char* const jcd_utilArrayList= "java/util/ArrayList";
static char* const jcd_utilArrays= "java/util/Arrays";
static char* const jcd_utilBitSet= "java/util/BitSet";
static char* const jcd_utilCalendar = "java/util/Calendar";
static char* const jcd_utilCollections = "java/util/Collections";
static char* const jcd_utilDate  = "java/util/Date";
static char* const jcd_utilDictionary  = "java/util/Dictionary";
static char* const jcd_utilEventObject = "java/util/EventObject";
static char* const jcd_utilGregorianCalendar = "java/util/GregorianCalendar";
static char* const jcd_utilHashMap  = "java/util/HashMap";
static char* const jcd_utilHashSet  = "java/util/HashSet";
static char* const jcd_utilHashtable= "java/util/Hashtable";
static char* const jcd_utilLinkedList  = "java/util/LinkedList";
static char* const jcd_utilListResourceBundle= "java/util/ListResourceBundle";
static char* const jcd_utilLocale= "java/util/Locale";
static char* const jcd_utilObservable  = "java/util/Observable";
static char* const jcd_utilProperties  = "java/util/Properties";
static char* const jcd_utilPropertyPermission= "java/util/PropertyPermission";
static char* const jcd_utilPropertyResourceBundle  = "java/util/PropertyResourceBundle";
static char* const jcd_utilRandom= "java/util/Random";
static char* const jcd_utilResourceBundle = "java/util/ResourceBundle";
static char* const jcd_utilSimpleTimeZone = "java/util/SimpleTimeZone";
static char* const jcd_utilStack = "java/util/Stack";
static char* const jcd_utilStringTokenizer= "java/util/StringTokenizer";
static char* const jcd_utilTimer = "java/util/Timer";
static char* const jcd_utilTimerTask = "java/util/TimerTask";
static char* const jcd_utilTimeZone = "java/util/TimeZone";
static char* const jcd_utilTreeMap  = "java/util/TreeMap";
static char* const jcd_utilTreeSet  = "java/util/TreeSet";
static char* const jcd_utilVector= "java/util/Vector";
static char* const jcd_utilWeakHashMap = "java/util/WeakHashMap";

// java.util exceptions
static char* const jcd_utilConcurrentModificationException  = "java/util/ConcurrentModificationException";
static char* const jcd_utilEmptyStackException  = "java/util/EmptyStackException";
static char* const jcd_utilMissingResourceException= "java/util/MissingResourceException";
static char* const jcd_utilNoSuchElementException  = "java/util/NoSuchElementException";
static char* const jcd_utilTooManyListenersException  = "java/util/TooManyListenersException";

static char* const jcd_Extension = "org/sf/feeling/swt/win32/internal/extension/Extension";
static char* const jcd_MutexException = "org/sf/feeling/swt/win32/extension/system/MutexException";

static int const SHELLLINK_ERROR_SAVE = 1001;
static int const SHELLLINK_ERROR_SETPATH = 1002;
static int const SHELLLINK_ERROR_SETDESC = 1003;


static BSTR jstobs(JNIEnv* env, jstring js) {
    if (!js) {
         return NULL;
    }
    int len = env->GetStringLength(js);
    BSTR bs = ::SysAllocStringLen(NULL, len);
    if (!bs) {
        return NULL;
    }
    env->GetStringRegion(js, 0, len, (jchar *)bs);
    if (env->ExceptionOccurred()) {
        env->ThrowNew(env->FindClass(jcd_Exception), "Error getting string region.");
		return NULL;
    }
    return bs;
}

static char* jstringToNative( JNIEnv  *env, jstring jstr )
{
  const char* pstr = env->GetStringUTFChars(jstr, false);
  int nLen = MultiByteToWideChar( CP_UTF8, 0, pstr, -1, NULL, NULL );
  LPWSTR lpwsz = new WCHAR[nLen];    
  MultiByteToWideChar( CP_UTF8, 0, pstr, -1, lpwsz, nLen );
  int nLen1 = WideCharToMultiByte( CP_ACP, 0, lpwsz, nLen, NULL, NULL, NULL, NULL );    
  LPSTR lpsz = new CHAR[nLen1];
  int size = 0;
  size = WideCharToMultiByte( CP_ACP, 0, lpwsz, nLen, lpsz, nLen1, NULL, NULL );
  if( size <= 0 ){
  	delete [] lpwsz;
  	return NULL;
  }
  env->ReleaseStringUTFChars(jstr, pstr );
  delete [] lpwsz;
  return lpsz;
}




static jstring nativeTojstring( JNIEnv* env, char* str )
{
  jstring rtn = 0;
  int slen = strlen(str);
  unsigned short * buffer = 0;
  if( slen == 0 )
    rtn = env->NewStringUTF( str ); 
  else
  {
    int length = MultiByteToWideChar( CP_ACP, 0, (LPCSTR)str, slen, NULL, 0 );
    buffer = (unsigned short *)malloc( length*2 + 1 );
    if( MultiByteToWideChar( CP_ACP, 0, (LPCSTR)str, slen, (LPWSTR)buffer, length ) >0 )
      rtn = env->NewString(  (jchar*)buffer, length );
  }
  if( buffer )
  free( buffer );
  return rtn;
}

static boolean isUnicode(JNIEnv* env){
	jclass CExtension = env->FindClass(jcd_Extension);
	if(CExtension == NULL) return false;
	jfieldID fid_IsUnicode = env->GetStaticFieldID(CExtension, "IsUnicode", jfd_boolean);
	if(fid_IsUnicode == NULL) return false;
	return env->GetStaticBooleanField(CExtension, fid_IsUnicode);
}

#endif /* INC_swt_extension_H */







