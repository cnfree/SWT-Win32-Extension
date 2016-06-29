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
package org.sf.feeling.swt.win32.extension.jna;

import org.sf.feeling.swt.win32.internal.extension.Extension;

/**
 * Win32 dll function utility class.
 * 
 * @author <a href="mailto:cnfree2000@hotmail.com">cnfree</a>
 * 
 */
public final class Function {

	/** The function pointer. */
	private int fPoint;

	/** The dll handle. */
	private int dllHandle;

	/** The library name. */
	private final String dll;

	/**
	 * The entry point name. This may not be the same as the name in source
	 * code, use DUMPBIN /EXPORTS to get the exact name.
	 */
	private final String name;

	private final int hash;

	/**
	 * 
	 * @param dll
	 *            the name of a DLL (can be either just the name of the DLL or a
	 *            full path - use backslashes (\)). Note, that the name is not
	 *            case sensitive and that the ".dll"-part is optional. The exact
	 *            syntax for the parameter can be found in the MSDN
	 *            documentation for
	 *            {@link <a href="http://msdn.microsoft.com/library/default.asp?url=/library/en-us/dllproc/base/loadlibrary.asp">LoadLibrary</a>}.
	 * @param name
	 *            the function name exposed in the DLL.
	 * @throws Exception
	 *             if unable to load the DLL or find the function.
	 * @throws NullPointerException
	 *             if either dll or name is null.
	 */
	public Function(String dll, String name) throws Exception {
		if ((dll == null) || (name == null)) {
			throw new NullPointerException("neither the DLL name (" + dll
					+ ") or the function name (" + name + ") can be null");
		}

		this.name = name;
		this.dll = dll;
		dllHandle = Extension.LoadLibrary(dll);
		fPoint = Extension.LoadFunction(dllHandle, name);
		hash = dll.hashCode() ^ name.hashCode();
	}

	public int hashCode() {
		return hash;
	}

	/**
	 * @return the function pointer
	 * @throws IllegalStateException
	 *             if this FuncPtr has been closed.
	 */
	public int getHandle() {
		checkState();
		return fPoint;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o.getClass() != getClass())
			return false;
		if (o == this)
			return true;
		Function fp = (Function) o;
		return (dll.equals(fp.dll) && name.equals(fp.name));
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return "[" + dll + ":" + name + "]";
	}

	/**
	 * Call to "free" the function pointer. Windows automatically manages a
	 * reference count for each library and will close the library if this was
	 * the last active function. <br>
	 * <br>
	 * After close() has been called the FuncPtr should not be used anymore as
	 * all invoke's will throw an IllegalStateException.
	 */
	public synchronized void close() throws Exception {
		if (fPoint != 0) {
			Extension.FreeLibrary(dllHandle);
			dllHandle = 0;
			fPoint = 0;
		}
	}

	/**
	 * backup for releasing resources, by calling {@link #close()}.
	 */
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	/**
	 * @throws IllegalStateException
	 *             if this FuncPtr has been closed.
	 */
	private void checkState() {
		if (fPoint == 0) {
			throw new IllegalStateException(toString() + " has been closed");
		}
	}

	/**
	 * for calling native methods taking no args, and returning an int.
	 * 
	 * @return the result of the native method.
	 * @throws Exception
	 *             if the native method failed.
	 * @throws NullPointerException
	 *             if flags is null.
	 * @throws IllegalStateException
	 *             if this FuncPtr has been closed.
	 */
	public int invoke_I() throws Exception {
		checkState();
		return Extension.Invoke_I(fPoint);
	}

	/**
	 * for calling native methods taking one int arg, and returning an int.
	 * 
	 * @param arg0
	 *            the arg that should be passed to the native method.
	 * @return the result of the native method.
	 * @throws Exception
	 *             if the native method failed.
	 * @throws NullPointerException
	 *             if flags is null.
	 * @throws IllegalStateException
	 *             if this FuncPtr has been closed.
	 */
	public int invoke_I(int arg0) throws Exception {
		checkState();
		return Extension.InvokeI_I(arg0, fPoint);
	}

	public boolean invoke_B(int arg0) throws Exception {
		return invoke_I(arg0) != 0;
	}

	public boolean invoke_B(boolean arg0) throws Exception {
		return invoke_I(arg0 ? 1 : 0) != 0;
	}

	/**
	 * for calling native methods taking two int args, and returning an int.
	 * 
	 */
	public int invoke_I(int arg0, int arg1) throws Exception {
		checkState();
		return Extension.InvokeII_I(arg0, arg1, fPoint);
	}

	/**
	 * for calling native methods taking two int args, and returning an int.
	 * 
	 */
	public int invoke_I(int arg0, int arg1, int arg2) throws Exception {
		checkState();
		return Extension.InvokeIII_I(arg0, arg1, arg2, fPoint);
	}

	public boolean invoke_B(int arg0, int arg1, int arg2) throws Exception {
		return invoke_I(arg0, arg1, arg2) != 0;
	}

	/**
	 * for calling native methods taking four int args, and returning an int.
	 * 
	 */
	public int invoke_I(int arg0, int arg1, int arg2, int arg3)
			throws Exception {
		checkState();
		return Extension.InvokeIIII_I(arg0, arg1, arg2, arg3, fPoint);
	}

	/**
	 * for calling native methods taking one String arg, and returning an int.
	 * 
	 * @param arg0
	 *            the arg that should be passed to the native method as a BSTR.
	 * @return the result of the native method.
	 * @throws Exception
	 *             if the native method failed.
	 * @throws NullPointerException
	 *             if flags is null.
	 * @throws IllegalStateException
	 *             if this FuncPtr has been closed.
	 */
	public int invoke_I(String arg0) throws Exception {
		checkState();
		return Extension.InvokeG_I(arg0, fPoint);
	}

	/**
	 * for calling native methods taking two String args, and returning an int.
	 * 
	 */
	public int invoke_I(String arg0, String arg1) throws Exception {
		checkState();
		return Extension.InvokeGG_I(arg0, arg1, fPoint);
	}

	/**
	 * for calling native methods taking one int and one String args, and
	 * returning an int.
	 * 
	 */
	public int invoke_I(int arg0, String arg1) throws Exception {
		checkState();
		return Extension.InvokeIG_I(arg0, arg1, fPoint);
	}

	/**
	 * for calling native methods taking one int, two String and one int args,
	 * and returning an int (used for MessageBoxW).
	 * 
	 */
	public int invoke_I(int arg0, String arg1, String arg2, int arg3)
			throws Exception {
		checkState();
		return Extension.InvokeIGGI_I(arg0, arg1, arg2, arg3, fPoint);
	}

	/**
	 * for calling native methods taking one pointer to a byte array, and
	 * returning an int. Can for example be used for methods taking a pointer to
	 * a struct, like DispatchMessageW.
	 * 
	 * @param arg0
	 *            the arg that should be passed to the native method as a
	 *            pointer to a byte array.
	 * @return the result of the native method.
	 * @throws Exception
	 *             if the native method failed.
	 * @throws NullPointerException
	 *             if flags is null.
	 * @throws IllegalStateException
	 *             if this FuncPtr has been closed.
	 */
	public int invoke_I(byte[] arg0) throws Exception {
		checkState();
		return Extension.InvokeP_I(arg0, fPoint);
	}

	/**
	 * for calling native methods taking two int args, where the last int is a
	 * [out] parameter. This [out] parameter will be returned by this function.
	 * An example of such a function is GetNumberOfEventLogRecords().
	 * 
	 * @param arg0
	 *            the [in] int argument.
	 * @return the result of the native method.
	 * @throws Exception
	 *             if the native method failed.
	 * @throws NullPointerException
	 *             if flags is null.
	 * @throws IllegalStateException
	 *             if this FuncPtr has been closed.
	 */
	public int invoke_OI(int arg0) throws Exception {
		checkState();
		return Extension.InvokeIO(arg0, fPoint);
	}

	/**
	 * for calling native methods taking five int args, where the first four are
	 * standard [in] parameters and the last int is a [out] parameter.
	 * 
	 */
	public int invoke_OI(int arg0, int arg1, int arg2, int arg3)
			throws Exception {
		checkState();
		return Extension.InvokeIIIIO(arg0, arg1, arg2, arg3, fPoint);
	}

	/**
	 * for calling native methods taking one [in] int, one [in] String and one
	 * [out] int arg.
	 * 
	 */
	public int invoke_OI(int arg0, String arg1) throws Exception {
		checkState();
		return Extension.InvokeIGO(arg0, arg1, fPoint);
	}

	/**
	 * for calling native methods taking one int arg, and returning a byte array
	 * of a specified size (typically used when calling methods returning a
	 * struct with a known byte-size).
	 * 
	 * @param arg0
	 *            the [in] int argument.
	 * @param returnSize
	 *            the size of the byte array to return.
	 * @return the result of the native method - will be a byte array with size
	 *         returnSize.
	 * @throws Exception
	 *             if the native method failed.
	 * @throws NullPointerException
	 *             if flags is null.
	 * @throws IllegalStateException
	 *             if this FuncPtr has been closed.
	 */
	public byte[] invoke_S(int arg0, int returnSize) throws Exception {
		checkState();
		return Extension.InvokeI_S(arg0, returnSize, fPoint);
	}

	/**
	 * for calling native methods taking one pointer to a byte array, and
	 * returning a byte array of a specified size.
	 * 
	 */
	public byte[] invoke_S(byte[] arg0, int returnSize) throws Exception {
		checkState();
		return Extension.InvokeP_S(arg0, returnSize, fPoint);
	}

	/**
	 * for calling native methods taking one pointer to a byte array and one int
	 * args, and returning a byte array of a specified size.
	 * 
	 */
	public byte[] invoke_S(byte[] arg0, int arg1, int returnSize)
			throws Exception {
		checkState();
		return Extension.InvokePI_S(arg0, arg1, returnSize, fPoint);
	}
}
