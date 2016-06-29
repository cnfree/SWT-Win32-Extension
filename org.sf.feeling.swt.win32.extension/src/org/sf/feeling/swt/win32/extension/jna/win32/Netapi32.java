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

import java.io.UnsupportedEncodingException;

import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.Type;
import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;

public class Netapi32
{

    public static final String DLL_NAME = "Netapi32.DLL";
    public static final int NERR_Success = 0;
    public static final int LevelUserInfo1 = 1;
    public static final int USER_PRIV_ADMIN = 2;

    /*
    typedef struct _USER_INFO_1 {
    LPWSTR usri1_name;
    LPWSTR usri1_password;
    DWORD  usri1_password_age;
    DWORD  usri1_priv;
    LPWSTR usri1_home_dir;
    LPWSTR usri1_comment;
    DWORD  usri1_flags;
    LPWSTR usri1_script_path;
    }USER_INFO_1, *PUSER_INFO_1, *LPUSER_INFO_1;
     */
    public static void main(String args[]) throws NativeException, IllegalAccessException, UnsupportedEncodingException
    {
        Pointer p = Pointer.createPointer(4);
        System.out.println("GetUserName(): " + Advapi32.GetUserName());
        System.out.println("NetUserGetInfo: " + NetUserGetInfo(null, Advapi32.GetUserName(), new DWORD(LevelUserInfo1), p));
        Pointer USER_INFO_1 = Pointer.createPointerToNativeMemory(p.getAsInt(0), 32);
        System.out.println("usri1_name: " + Native.getUnicodeMemoryAsString(USER_INFO_1.getAsInt(0)));
        System.out.println("usri1_password_age: " + USER_INFO_1.getAsInt(8));
        System.out.println("usri1_priv: " + USER_INFO_1.getAsInt(12));
        System.out.println("usri1_comment: " + Native.getUnicodeMemoryAsString(USER_INFO_1.getAsInt(20)));
        System.out.println("usri1_flags: " + USER_INFO_1.getAsInt(24));
        System.out.println("isAdmin: " + (USER_INFO_1.getAsInt(12) == USER_PRIV_ADMIN));
        System.out.println("NetApiBufferFree: " + NetApiBufferFree(p.getAsInt(0)));
        USER_INFO_1.dispose();
        p.dispose();
    }

    /*
    NET_API_STATUS NetUserGetInfo(
    __in   LPCWSTR servername,
    __in   LPCWSTR username,
    __in   DWORD level,
    __out  LPBYTE *bufptr
    );
     */
    public static int NetUserGetInfo(String servername, String username, DWORD level, Pointer out) throws NativeException, IllegalAccessException, UnsupportedEncodingException
    {
        Native Native = new Native(DLL_NAME, "NetUserGetInfo");

        // NetUserGetInfo is implemented as unicode only!
        Pointer p1 = Pointer.createPointerFromString(servername, "UTF-16LE");
        Pointer p2 = Pointer.createPointerFromString(username, "UTF-16LE");

        try
        {
            int i = 0;
            Native.setRetVal(Type.INT);
            Native.setParameter(i++, p1);
            Native.setParameter(i++, p2);
            Native.setParameter(i++, level.getValue());
            Native.setParameter(i++, out);
            Native.invoke();

            return Native.getRetValAsInt();
        }
        finally
        {
            p1.dispose();
            p2.dispose();
        }
    }

    /*
    NET_API_STATUS NetApiBufferFree(
    __in  LPVOID Buffer
    );
     */
    public static int NetApiBufferFree(int Buffer) throws NativeException, IllegalAccessException
    {
        Native Native = new Native(DLL_NAME, "NetApiBufferFree");

        Native.setRetVal(Type.INT);
        Native.setParameter(0, Buffer);
        Native.invoke();

        return Native.getRetValAsInt();
    }
}
