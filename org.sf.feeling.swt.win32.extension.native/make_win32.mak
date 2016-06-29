############################################################################
# Copyright (c) 2007 cnfree.
#  All rights reserved. This program and the accompanying materials
#  are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
# cnfree - initial API and implementation
############################################################################

# Makefile for SWT libraries on Windows

# assumes JAVA_HOME is set in the environment from which nmake is run

APPVER=5.0
!include <make_common.mak>
!include <win32.mak>

pgm_ver_str="SWT $(maj_ver).$(min_ver) for Windows"
timestamp_str=__DATE__\" \"__TIME__\" (EST)\"
copyright = "Copyright (C) cnfree.  All rights reserved."

EXTENSION_PREFIX  = swt-extension
WS_PREFIX   	  = win32
EXTENSION_LIB     = $(EXTENSION_PREFIX)-$(WS_PREFIX).dll
VC6_LIBS    	  = vcrt6.lib
EXTENSION_LIBS    = Ws2_32.lib netapi32.lib mpr.lib ole32.lib comctl32.lib user32.lib gdi32.lib comdlg32.lib kernel32.lib shell32.lib oleaut32.lib advapi32.lib imm32.lib winspool.lib oleacc.lib usp10.lib wininet.lib Shlwapi.lib winmm.lib version.lib
EXTENSION_OBJS    = swt_extension.obj swt_extension_structs.obj swt_extension_function.obj


# Uncomment for Native Stats tool
#NATIVE_STATS = -DNATIVE_STATS

# Uncomment for debug flags
#SWT_CDEBUG = -Zi -Odi
#SWT_LDEBUG = /DEBUG /DEBUGTYPE:both

# note: thoroughly test all examples after changing any optimization flags
#CFLAGS = $(cdebug) $(cflags) $(cvarsmt) $(CFLAGS) \
CFLAGS = -O1 -DNDEBUG $(cflags) $(cvarsmt) $(CFLAGS) \
	-DSWT_VERSION=$(SWT_VERSION) $(NATIVE_STATS) -D_CRT_SECURE_NO_WARNINGS -DUSE_ASSEMBLER \
	/I"$(JAVA_HOME)\include" /I"$(JAVA_HOME)\include\win32" /I.
RCFLAGS = $(rcflags) $(rcvars) $(RCFLAGS) -DSWT_FILE_VERSION=\"$(maj_ver).$(min_ver)\" -DSWT_COMMA_VERSION=$(comma_ver)

all: $(EXTENSION_LIB) 

.c.obj:
	cl $(CFLAGS) $*.c

.cpp.obj:
	cl $(CFLAGS) $*.cpp

$(EXTENSION_LIB): $(EXTENSION_OBJS) swt_extension.res
	echo $(VC6_LIBS) >>templrf
	echo $(ldebug) $(dlllflags) >templrf
	echo $(EXTENSION_LIBS) >>templrf
	echo $(EXTENSION_OBJS) >>templrf
	echo swt_extension.res >>templrf
	echo -out:$(EXTENSION_LIB) >>templrf
	link @templrf
	del templrf


swt_extension.res:
	rc $(RCFLAGS) -DSWT_ORG_FILENAME=\"$(EXTENSION_LIB)\" -r -fo swt_extension.res swt_extension.rc

install: all
	copy *.dll $(OUTPUT_DIR)

clean:
    del *.obj,*.res,*.dll,*.lib,*.exp

