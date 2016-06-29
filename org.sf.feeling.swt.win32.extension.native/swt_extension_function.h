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

#ifndef FTYPES_Included
#define FTYPES_Included

typedef HRESULT (__stdcall * FTYPE0)();
typedef HRESULT (__stdcall * FTYPE1)(int);
typedef HRESULT (__stdcall * FTYPE2)(int, int);
typedef HRESULT (__stdcall * FTYPE3)(int, int, int);
typedef HRESULT (__stdcall * FTYPE4)(int, int, int, int);
typedef HRESULT (__stdcall * FTYPE5)(int, int, int, int, int);

#endif