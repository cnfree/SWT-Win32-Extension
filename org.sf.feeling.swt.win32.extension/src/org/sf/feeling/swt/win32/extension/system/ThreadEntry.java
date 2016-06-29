
package org.sf.feeling.swt.win32.extension.system;

public class ThreadEntry
{

	private int threadId;
	private int basePriority;
	private int deltaPriority;

	ThreadEntry( int threadId, int basePriority, int deltaPriority )
	{
		this.threadId = threadId;
		this.basePriority = basePriority;
		this.deltaPriority = deltaPriority;
	}

	public int getThreadId( )
	{
		return threadId;
	}

	public int getBasePriority( )
	{
		return basePriority;
	}

	public int getDeltaPriority( )
	{
		return deltaPriority;
	}

	public boolean equals( Object object )
	{
		if ( object == this )
			return true;
		if ( !( object instanceof ThreadEntry ) )
			return false;
		ThreadEntry r = (ThreadEntry) object;
		return threadId == r.threadId;
	}

	public int hashCode( )
	{
		return threadId;
	}
}
