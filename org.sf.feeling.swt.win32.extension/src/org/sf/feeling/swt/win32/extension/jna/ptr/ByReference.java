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
package org.sf.feeling.swt.win32.extension.jna.ptr;

import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.memory.MemoryBlockFactory;


/** Provides generic "pointer to type" functionality, often used in C
 * code to return values to the caller in addition to a function result.
 * <p>
 * Derived classes should define <code>setValue(&lt;T&gt;)</code>
 * and <code>&lt;T&gt; getValue()</code> methods which write to/read from
 * memory.
 * <p>This class derives from PointerType instead of Memory in order to
 * restrict the API to only <code>getValue/setValue</code>.
 * <p>NOTE: this class would ideally be replaced by a generic.
 */
public abstract class ByReference extends PointerType {
    
    protected ByReference(int dataSize) throws NativeException {
        setPointer(new Pointer(MemoryBlockFactory.createMemoryBlock( dataSize )));
    }
}
