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
package org.sf.feeling.swt.win32.extension.shell;

import java.io.File;

import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * Shell link Utility class.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public class ShellLink {
	/**
	 * Get the shortcut target position of specified linked file.
	 * 
	 * @param linkFilePath
	 *            a linked file(*.lnk)'s path.
	 * @return shortcut target position
	 */
	public static String getShortCutTarget(String linkFilePath) {
		return Extension.GetShortCutTarget(linkFilePath);
	}

	/**
	 * Set the shortcut target position of specified linked file.
	 * 
	 * @param linkFilePath
	 *            a linked file(*.lnk)'s path.
	 * @param targetFilePath
	 *            the path of the target file.
	 * @return return true if set shortcut target successful.
	 */
	public static boolean setShortCutTarget(String linkFilePath,
			String targetFilePath) {
		return Extension.SetShortCutTarget(linkFilePath, targetFilePath) == 0;
	}
	
	/**
	 * Get the shortcut working directory of specified linked file.
	 * 
	 * @param linkFilePath
	 *            a linked file(*.lnk)'s path.
	 * @return shortcut working directory
	 */
	public static String getShortCutWorkingDirectory(String linkFilePath) {
		return Extension.GetShortCutTarget(linkFilePath);
	}

	/**
	 * Set the shortcut working directory of specified linked file.
	 * 
	 * @param linkFilePath
	 *            a linked file(*.lnk)'s path.
	 * @param directory
	 *            the path of the working directory.
	 * @return return true if set shortcut working directory successful.
	 */
	public static boolean setShortCutWorkingDirectory(String linkFilePath,
			String directory) {
		return Extension.SetShortCutWorkingDirectory(linkFilePath, directory) == 0;
	}

	/**
	 * Get the shortcut arguments of specified linked file.
	 * 
	 * @param linkFilePath
	 *            a linked file(*.lnk)'s path.
	 * @return shortcut target arguments.
	 */
	public static String getShortCutArguments(String linkFilePath) {
		return Extension.GetShortCutArguments(linkFilePath);
	}

	/**
	 * Set the shortcut arguments of specified linked file.
	 * 
	 * @param linkFilePath
	 *            a linked file(*.lnk)'s path.
	 * @param Arguments
	 *            the arguments of the linked file.
	 * 
	 * @return return true if set shortcut arguments successful.
	 */
	public static boolean setShortCutArguments(String linkFilePath,
			String Arguments) {
		return Extension.SetShortCutArguments(linkFilePath, Arguments) == 0;
	}

	/**
	 * Get the shortcut description of specified linked file.
	 * 
	 * @param linkFilePath
	 *            a linked file(*.lnk)'s path.
	 * @return shortcut target description.
	 */
	public static String getShortCutDescription(String linkFilePath) {
		return Extension.GetShortCutDescription(linkFilePath);
	}

	/**
	 * Set the shortcut description of specified linked file.
	 * 
	 * @param linkFilePath
	 *            a linked file(*.lnk)'s path.
	 * @param description
	 *            the description of the linked file.
	 * @return return true if set shortcut arguments successful.
	 */
	public static boolean setShortCutDescription(String linkFilePath,
			String description) {
		return Extension.SetShortCutDescription(linkFilePath, description) == 0;
	}

	/**
	 * Get the shortcut target position of specified linked file.
	 * 
	 * @param linkFile
	 *            is a linked file(*.lnk).
	 * @return shortcut target position
	 */
	public static String getShortCutTarget(File linkFile) {
		return getShortCutTarget(linkFile.getAbsolutePath());
	}

	/**
	 * Set the shortcut target position of specified linked file.
	 * 
	 * @param linkFile
	 *            is a linked file(*.lnk).
	 * @param targetFile
	 *            the target file.
	 * @return return true if set shortcut target successful.
	 */
	public static boolean setShortCutTarget(File linkFile, File targetFile) {
		return setShortCutTarget(linkFile.getAbsolutePath(), targetFile
				.getAbsolutePath());
	}
	
	/**
	 * Set the shortcut working directory position of specified linked file.
	 * 
	 * @param linkFile
	 *            is a linked file(*.lnk).
	 * @param workingDirectory
	 *            the working directory.
	 * @return return true if set shortcut working directory successful.
	 */
	public static boolean setShortCutWorkingDirectory(File linkFile,
			File workingDirectory) {
		return setShortCutWorkingDirectory(linkFile.getAbsolutePath(), workingDirectory
				.getAbsolutePath());
	}

	/**
	 * Get the shortcut working directory position of specified linked file.
	 * 
	 * @param linkFile
	 *            is a linked file(*.lnk).
	 * @return shortcut working directory
	 */
	public static String getShortCutWorkingDirectory(File linkFile) {
		return getShortCutWorkingDirectory(linkFile.getAbsolutePath());
	}

	/**
	 * Get the shortcut arguments of specified linked file.
	 * 
	 * @param linkFile
	 *            a linked file(*.lnk).
	 * @return shortcut target arguments.
	 */
	public static String getShortCutArguments(File linkFile) {
		return getShortCutArguments(linkFile.getAbsolutePath());
	}

	/**
	 * Set the shortcut arguments of specified linked file.
	 * 
	 * @param linkFile
	 *            is a linked file(*.lnk).
	 * @param arguments
	 *            the arguments of the linked file.
	 * @return return true if set shortcut arguments successful.
	 */
	public static boolean setShortCutArguments(File linkFile, String arguments) {
		return setShortCutArguments(linkFile.getAbsolutePath(), arguments);
	}

	/**
	 * Get the shortcut description of specified linked file.
	 * 
	 * @param linkFile
	 *            a linked file(*.lnk).
	 * @return shortcut target description.
	 */
	public static String getShortCutDescription(File linkFile) {
		return getShortCutDescription(linkFile.getAbsolutePath());
	}

	/**
	 * Set the shortcut description of specified linked file.
	 * 
	 * @param linkFile
	 *            is a linked file(*.lnk).
	 * @param description
	 *            the description of the linked file.
	 * @return return true if set shortcut description successful.
	 */
	public static boolean setShortCutDescription(File linkFile,
			String description) {
		return setShortCutDescription(linkFile.getAbsolutePath(), description);
	}

	/**
	 * Create a shortcut file by a specified file.
	 * 
	 * @param sourceFilePath
	 *            path of the specified file for creating shortcut
	 * @param linkFilePath
	 *            path of the shortcut file are created shortcut for specified
	 *            file
	 * @return return true if creating shortcut successful.
	 * 
	 */
	public static boolean createShortCut(String sourceFilePath,
			String linkFilePath) {
		return Extension.CreateShortCut(sourceFilePath, linkFilePath) == 0;
	}
}
