
package org.sf.feeling.swt.win32.extension.system;

public class ProcessEntry
{

	private int processId;
	private int threadsCount;
	private int priorityBase;
	private int priorityClass;
	private String exePath;
	private String processName;

	ProcessEntry( int processId, int threadsCount, int priorityBase,
			int priorityClass, String processName, String exePath )
	{
		this.processId = processId;
		this.threadsCount = threadsCount;
		this.priorityBase = priorityBase;
		this.priorityClass = priorityClass;
		this.processName = processName;
		this.exePath = exePath;
	}

	public int getPriorityBase( )
	{
		return priorityBase;
	}

	public int getPriorityClass( )
	{
		return priorityClass;
	}

	public String getProcessName( )
	{
		return processName;
	}

	public int getProcessId( )
	{
		return processId;
	}

	public int getThreadsCount( )
	{
		return threadsCount;
	}

	public String getExePath( )
	{
		return exePath;
	}

	public boolean equals( Object object )
	{
		if ( object == this )
			return true;
		if ( !( object instanceof ProcessEntry ) )
			return false;
		ProcessEntry r = (ProcessEntry) object;
		return ( r.processId == this.processId );
	}

	public int hashCode( )
	{
		return processId;
	}

}
