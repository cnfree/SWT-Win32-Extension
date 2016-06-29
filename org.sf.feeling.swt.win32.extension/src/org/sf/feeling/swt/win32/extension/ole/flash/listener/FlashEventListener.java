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
package org.sf.feeling.swt.win32.extension.ole.flash.listener;

/**
 * Flash Player event listener
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 */
public interface FlashEventListener {
	/**
	 * Generated when target changes ReadyState.
	 * 
	 * @param newState
	 *            the new state of the control, one of:
	 *            READYSTATE_UNINITIALIZED; READYSTATE_LOADING;
	 *            READYSTATE_LOADED; READYSTATE_INTERACsTIVE;
	 *            READYSTATE_COMPLETE.
	 */
	void onReadyStateChange(int newState);

	/**
	 * <p>
	 * Generated as the Flash movie is downloading.
	 * </p>
	 */
	void onProgress(int percentDone);

	/**
	 * <p>
	 * Generated when an FSCommand action is performed in the movie with a URL
	 * and the URL starts with "FSCommand :". Use this to create a response to a
	 * frame or button action in the Flash movie.
	 * </p>
	 * 
	 * @param command
	 *            "Quit", "Fullscreen", "AllowScale", "Showmenu", "Exec"
	 */
	void onFSCommand(String command, String args);
}
