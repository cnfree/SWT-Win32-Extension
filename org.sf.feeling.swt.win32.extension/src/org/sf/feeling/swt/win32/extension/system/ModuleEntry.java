
package org.sf.feeling.swt.win32.extension.system;

public class ModuleEntry
{

	private String moduleName;
	private String exePath;
	private int processId;
	private int globalRefCount;
	private int processRefCount;
	private int baseAddress;
	private int baseSize;

	ModuleEntry( int processId, int globalRefCount, int processRefCount,
			int baseAddress, int baseSize, String moduleName, String exePath )
	{
		this.processId = processId;
		this.globalRefCount = globalRefCount;
		this.processRefCount = processRefCount;
		this.baseAddress = baseAddress;
		this.baseSize = baseSize;
		this.moduleName = moduleName;
		this.exePath = exePath;
	}

	public String getModuleName( )
	{
		return moduleName;
	}

	public String getExePath( )
	{
		return exePath;
	}

	public int getProcessId( )
	{
		return processId;
	}

	public int getGlobalRefCount( )
	{
		return globalRefCount;
	}

	public int getProcessRefCount( )
	{
		return processRefCount;
	}

	public int getBaseAddress( )
	{
		return baseAddress;
	}

	public int getBaseSize( )
	{
		return baseSize;
	}

	public boolean equals( Object object )
	{
		if ( object == this )
			return true;
		if ( !( object instanceof ModuleEntry ) )
			return false;
		ModuleEntry r = (ModuleEntry) object;
		return ( ( r.moduleName == null && moduleName == null ) || ( r.moduleName != null && r.moduleName.equals( this.moduleName ) ) );
	}

	public int hashCode( )
	{
		return moduleName == null ? 13 * 7 + 5 : moduleName.hashCode( );
	}

}
