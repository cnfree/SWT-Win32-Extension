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

package org.sf.feeling.swt.win32.extension.jna;

/**
 * WindowProc this interface must be implemented to receive events from native
 * window.<br>
 * 
 */
public interface WindowProc
{

	/**
	 * Method windowProc recieve events from native window
	 * 
	 * @param hwnd
	 *            an int [in] Handle to the window.
	 * @param uMsg
	 *            an int [in] Specifies the message.
	 * @param wParam
	 *            an int [in] Specifies additional message information. The
	 *            contents of this parameter depend on the value of the uMsg
	 *            parameter.
	 * @param lParam
	 *            an int Specifies additional message information. The contents
	 *            of this parameter depend on the value of the uMsg parameter.
	 * 
	 * @return an int The return value is the result of the message processing
	 *         and depends on the message sent.
	 * 
	 */
	public int windowProc( int hwnd, int uMsg, int wParam, int lParam );
}
