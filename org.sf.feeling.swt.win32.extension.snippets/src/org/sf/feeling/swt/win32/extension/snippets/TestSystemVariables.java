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
package org.sf.feeling.swt.win32.extension.snippets;

import org.sf.feeling.swt.win32.extension.system.SystemVariables;

public class TestSystemVariables {
	public static void main(String[] args) {
		SystemVariables var = SystemVariables.getInstance();
		String[] names = var.getVariableNames();
		System.out.print("System Variables: ");
		for (int i = 0; i < names.length; i++) {
			System.out.print(names[i] + ";");
		}
		System.out.println("");
		System.out.println("System Variables contains Path = "
				+ var.contains("Path"));
		if (var.contains("Path"))
			System.out.println("System Variable Path value = "
					+ var.getValue("Path"));
		System.out.println("Set System Variable Test = Hello");
		var.setVariable("Test", "Feeling");
		System.out.println("System Variables contains Test = "
				+ var.contains("Test"));
		if (var.contains("Test")) {
			System.out.println("System Variable Test value = "
					+ var.getValue("Test"));
			System.out.println("Delete System Variable Test");
			var.removeVariable("Test");
			System.out.println("System Variables contains Test = "
					+ var.contains("Test"));
		}
	}
}