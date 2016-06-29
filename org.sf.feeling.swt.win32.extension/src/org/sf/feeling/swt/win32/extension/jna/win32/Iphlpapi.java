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
package org.sf.feeling.swt.win32.extension.jna.win32;

import org.sf.feeling.swt.win32.extension.jna.Native;
import org.sf.feeling.swt.win32.extension.jna.Type;
import org.sf.feeling.swt.win32.extension.jna.datatype.LONG;
import org.sf.feeling.swt.win32.extension.jna.exception.NativeException;
import org.sf.feeling.swt.win32.extension.jna.ptr.Pointer;
import org.sf.feeling.swt.win32.extension.jna.win32.structure.IP_ADAPTER_INFO;

public class Iphlpapi
{
	public static final String DLL_NAME = "Iphlpapi.dll";
	
	public static interface Error
	{
		int ERROR_SUCCESS = (0);
		int ERROR_BUFFER_OVERFLOW = (111);
		int ERROR_INVALID_DATA = (13);
		int ERROR_INVALID_PARAMETER = (87);
		int ERROR_NO_DATA = (232);
		int ERROR_NOT_SUPPORTED = (50);
	}
	
	/**
	 * <div class="title">GetAdaptersInfo Function</div><!--Content type: HTML. Transform: webcollection2mtps.xslt.--><div class="clsServerSDKContent">
	 
	 <p><!----></p><p>The
	 <strong>GetAdaptersInfo</strong> function retrieves adapter information for the local computer.</p>
	 <blockquote><div><strong>On Windows&nbsp;XP and later:&nbsp;&nbsp;</strong>Use the
	 <a id="ctl00_rs1_mainContentContainer_ctl01" onclick="javascript:Track('ctl00_rs1_mainContentContainer_ctl00|ctl00_rs1_mainContentContainer_ctl01',this);" href="http://msdn.microsoft.com/en-us/library/aa365915%28VS.85%29.aspx"><strong>GetAdaptersAddresses</strong></a> function instead of <strong>GetAdaptersInfo</strong>.</div></blockquote>
	 
	 <h4>Syntax</h4>
	 <div class="" id="ctl00_rs1_mainContentContainer_ctl02_"><table><tbody><tr><td style="border: 0px none ; margin: 0px; padding: 0px;"><div class="CodeSnippetTitleBar"><div class="CodeDisplayLanguage"></div></div></td></tr><tr><td style="border: 0px none ; margin: 0px; padding: 0px;"><pre class="libCScode" id="ctl00_rs1_mainContentContainer_ctl02" space="preserve">DWORD GetAdaptersInfo(
	 __out    PIP_ADAPTER_INFO <em>pAdapterInfo</em>,
	 __inout  PULONG <em>pOutBufLen</em>
	 );
	 </pre></td></tr></tbody></table></div>
	 <h4>Parameters</h4>
	 <dl>
	 <dt><em>pAdapterInfo</em> [out]  </dt>
	 
	 <dd>
	 <p>A pointer to a buffer that receives a linked list of
	 <a id="ctl00_rs1_mainContentContainer_ctl03" onclick="javascript:Track('ctl00_rs1_mainContentContainer_ctl00|ctl00_rs1_mainContentContainer_ctl03',this);" href="http://msdn.microsoft.com/en-us/library/aa366062%28VS.85%29.aspx"><strong>IP_ADAPTER_INFO</strong></a> structures.</p>
	 </dd>
	 <dt><em>pOutBufLen</em> [in, out]  </dt>
	 <dd>
	 <p>A pointer to a <strong>ULONG</strong> variable that specifies the size of the buffer pointed to by the <em>pAdapterInfo</em> parameter. If this size is insufficient to hold the adapter information,
	 
	 <strong>GetAdaptersInfo</strong> fills in this variable with the required size, and returns an error code of ERROR_BUFFER_OVERFLOW.</p>
	 </dd>
	 </dl>
	 <h4>Return Value</h4>
	 <p>If the function succeeds, the return value is ERROR_SUCCESS.</p>
	 <p>If the function fails, the return value is one of the following error codes.</p>
	 <table class="clsStd">
	 <tbody><tr><th>Return code</th><th>Description</th></tr>
	 <tr><td>
	 
	 <p>ERROR_BUFFER_OVERFLOW</p>
	 </td><td><p>The buffer to receive the adapter information is too small. This value is returned if the buffer size indicated by the <em>pOutBufLen</em> parameter is too small to hold the adapter information or the <em>pAdapterInfo</em> parameter was a NULL pointer. When this error code is returned, the <em>pOutBufLen</em> parameter points to the required buffer size.</p>
	 </td></tr>
	 <tr><td>
	 <p>ERROR_INVALID_DATA</p>
	 
	 </td><td><p>Invalid adapter information was retrieved.</p>
	 </td></tr>
	 <tr><td>
	 <p>ERROR_INVALID_PARAMETER</p>
	 </td><td><p>One of the parameters is invalid. This error is returned if the  <em>pOutBufLen</em> parameter is a NULL pointer, or the calling process does not have read/write access to the memory pointed to by <em>pOutBufLen</em> or the calling process does not have write access to the memory pointed to by the <em>pAdapterInfo</em> parameter.</p>
	 
	 </td></tr>
	 <tr><td>
	 <p>ERROR_NO_DATA</p>
	 </td><td><p>No adapter information exists for the local computer.</p>
	 </td></tr>
	 <tr><td>
	 <p>ERROR_NOT_SUPPORTED</p>
	 </td><td><p>
	 The <strong>GetAdaptersInfo</strong> function is not supported by the operating system running on the local computer.</p>
	 
	 </td></tr>
	 <tr><td>
	 <p>Other</p>
	 </td><td><p>If the function fails, use
	 <a id="ctl00_rs1_mainContentContainer_ctl04" onclick="javascript:Track('ctl00_rs1_mainContentContainer_ctl00|ctl00_rs1_mainContentContainer_ctl04',this);" href="http://msdn.microsoft.com/en-us/library/ms679351%28VS.85%29.aspx"><strong>FormatMessage</strong></a> to obtain the message string for the returned error.</p>
	 </td></tr>
	 </tbody></table>
	 <h4>Remarks</h4>
	 <p>The
	 <strong>GetAdaptersInfo</strong> function can retrieve information only for IPv4 addresses. </p>
	 
	 <p>The order in which adapters appear in the list returned by this function can be controlled from the Network Connections folder: select the Advanced Settings menu item from the Advanced menu.</p>
	 <p>The
	 <strong>GetAdaptersInfo</strong> and
	 <a id="ctl00_rs1_mainContentContainer_ctl05" onclick="javascript:Track('ctl00_rs1_mainContentContainer_ctl00|ctl00_rs1_mainContentContainer_ctl05',this);" href="http://msdn.microsoft.com/en-us/library/aa365947%28VS.85%29.aspx"><strong>GetInterfaceInfo</strong></a> functions do not return information about the IPv4 loopback interface. Information on the loopback interface is returned by the <a id="ctl00_rs1_mainContentContainer_ctl06" onclick="javascript:Track('ctl00_rs1_mainContentContainer_ctl00|ctl00_rs1_mainContentContainer_ctl06',this);" href="http://msdn.microsoft.com/en-us/library/aa365949%28VS.85%29.aspx"><strong>GetIpAddrTable</strong></a> function.</p>
	 <blockquote><div><strong>On Windows&nbsp;XP and later:&nbsp;&nbsp;</strong>The list of adapters returned by
	 <strong>GetAdaptersInfo</strong> includes unidirectional adapters. To generate a list of adapters that can both send and receive data, call
	 
	 <a id="ctl00_rs1_mainContentContainer_ctl07" onclick="javascript:Track('ctl00_rs1_mainContentContainer_ctl00|ctl00_rs1_mainContentContainer_ctl07',this);" href="http://msdn.microsoft.com/en-us/library/aa366034%28VS.85%29.aspx"><strong>GetUniDirectionalAdapterInfo</strong></a>, and exclude the returned adapters from the list returned by
	 <strong>GetAdaptersInfo</strong>.</div>
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	public static IP_ADAPTER_INFO[] GetAdaptersInfo(int maxAdapters) throws NativeException, IllegalAccessException
	{
		Native GetAdaptersInfo = new Native(DLL_NAME, "GetAdaptersInfo");
		GetAdaptersInfo.setRetVal(Type.INT);
		if(maxAdapters < 1)
		{
			return null;
		}
		else
		{
			Pointer p = IP_ADAPTER_INFO.reserveMemoryOf_IP_ADAPTER_INFO(maxAdapters);
			GetAdaptersInfo.setParameter(0, p);
			LONG pOutBufLen = new LONG(p.getSize());
			GetAdaptersInfo.setParameter(1, pOutBufLen.getPointer());
			GetAdaptersInfo.invoke();
			
			int ret = GetAdaptersInfo.getRetValAsInt();
			
			switch (ret)
			{
				case Error.ERROR_SUCCESS:
					break;
				case Error.ERROR_BUFFER_OVERFLOW:
					throw new NativeException("ERROR_BUFFER_OVERFLOW: need a buffer of _" + pOutBufLen.getValue() + "Bytes");
				case Error.ERROR_INVALID_DATA:
					throw new NativeException("ERROR_INVALID_DATA: Invalid adapter information was retrieved.");
				case Error.ERROR_INVALID_PARAMETER:
					throw new NativeException("ERROR_INVALID_PARAMETER: One of the parameters is invalid. This error is returned if the pOutBufLen parameter is a NULL pointer, or the calling process does not have read/write access to the memory pointed to by pOutBufLen or the calling process does not have write access to the memory pointed to by the pAdapterInfo parameter.");
				case Error.ERROR_NO_DATA:
					throw new NativeException("ERROR_NO_DATA: No adapter information exists for the local computer.");
				case Error.ERROR_NOT_SUPPORTED:
					throw new RuntimeException("ERROR_NOT_SUPPORTED: The GetAdaptersInfo function is not supported by the operating system running on the local computer.");
				default:
					//TODO : If the function fails, use FormatMessage to obtain the message string for the returned error.
					System.err.println("Unknown return code "+pOutBufLen.getValue()+"  "+ p.getSize());
					throw new RuntimeException("Unknown return code " + ret);
			}
			if(pOutBufLen.getValue() != p.getSize())
			{
				System.err.println("GetAdaptersInfo : Not enougth size ?");
			}
			return IP_ADAPTER_INFO.getListOf_IP_ADAPTER_INFO(p);
		}
	}

}
