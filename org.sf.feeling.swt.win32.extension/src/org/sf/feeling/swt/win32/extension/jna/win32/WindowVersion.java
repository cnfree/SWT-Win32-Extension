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

import org.sf.feeling.swt.win32.extension.jna.datatype.win32.DWORD;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.win32.Kernel32;


public class WindowVersion {
    
    private static DWORD dword = null;
    private static boolean m_bSupportsUnicode = false;
    private static boolean m_bSupportsNewVersion = false;
    
    static {
        try {
            dword = Kernel32.GetVersion();
            if (isNT()) {
              m_bSupportsUnicode = true;
              if (getMajorVersion() >= 5) {
                 // 2000 or XP
                 m_bSupportsNewVersion = true;
              } 
            } else if (getMajorVersion() == 4 && getMinorVersion() == 90) {
                // Windows ME
                m_bSupportsNewVersion = true;
            }
        } catch(NativeException e) {
            throw new RuntimeException(e);
        } catch(IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    /** Creates a new instance of WindowsVersion */
    public WindowVersion() {
  
    }
    public String toString() {
        String ret = "Windows Version Info:\n";
        try {
            ret += "Major Version: "+getMajorVersion()+"\n";
            ret += "Minor Version: "+getMinorVersion()+"\n";
            ret += "Build: "+getBuild()+"\n";
            ret += "NT: "+isNT()+"\n";
            ret += "Supports Unicode: "+supportsUnicode()+"\n";
            ret += "Supports new version: "+ supportsNewVersion()+"\n";
            return ret;
        } catch(NativeException e) {
            return e.toString();
        }
    }
    
    public static boolean supportsUnicode() {
        return m_bSupportsUnicode;
    }
    public static boolean supportsNewVersion() {
        return m_bSupportsNewVersion;
    }
    
    public static boolean isNT() throws NativeException {
        return ((getBuild() & 0x80000000) == 0);
    }
    
    public static int getMajorVersion() throws NativeException {
        if(dword != null)
            return dword.getPointer().getAsByte(0);
        else
            return -1;
    }
    public static int getMinorVersion() throws NativeException {
        if(dword != null)
            return dword.getPointer().getAsByte(1);
        else
            return -1;
    }
    public static int getBuild() throws NativeException {
        if(dword != null)
            return dword.getPointer().getAsShort(2);
        else
            return -1;
    }
}
