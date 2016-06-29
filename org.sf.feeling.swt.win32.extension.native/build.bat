@echo off
REM /*****************************************************************************
REM * Copyright (c) 2007 cnfree.
REM * All rights reserved. This program and the accompanying materials
REM * are made available under the terms of the Eclipse Public License v1.0
REM * which accompanies this distribution, and is available at
REM * http://www.eclipse.org/legal/epl-v10.html
REM *
REM * Contributors:
REM * cnfree - initial API and implementation
REM *****************************************************************************/
@echo off

set JAVA_HOME=D:\jdk1.5

call "C:\Program Files\Microsoft SDKs\Windows\v6.1\Bin\SetEnv" /xp /x86 /Release
call "%PROGRAMFILES%\Microsoft Visual Studio 9.0\VC\vcvarsall.bat"

:MAKE
cd E:\opensource\org.sf.feeling.swt.win32.extension\win32

nmake -f make_win32.mak %1 %2 %3 %4 %5 %6 %7 %8 %9


del *.obj,*.exp,*.res,*.pdb,*.lib,swt-extension-win32.dll.manifest
del "./../lib/swt-extension-win32.dll"
move swt-extension-win32.dll "./../lib"
pause
