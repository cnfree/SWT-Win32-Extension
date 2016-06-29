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
package org.sf.feeling.swt.win32.extension.registry;

/**
 * <code>RegistryException</code> signals that a registry operation has failed.
 *
 * @see java.lang.RuntimeException
 *
 */
public class RegistryException extends RuntimeException {
   /**
	 * 
	 */
	private static final long serialVersionUID = 119936693750378901L;

/**
    * Constructs a new <code>RegistryException</code> with no detail message.
    */
   public RegistryException() {
   } // RegistryException()

   /**
    * Constructs a new <code>RegistryException</code> with the specified detail
    * message.
    *
    * @param msg the detail message.
    */
   public RegistryException(String msg) {
      super(msg);
   } // RegistryException()
} // RegistryException
