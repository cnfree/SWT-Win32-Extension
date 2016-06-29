package org.sf.feeling.swt.win32.internal.extension;

public abstract class MIXERLINE extends STRUCT
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4833169844860678263L;

	public int cbStruct;

	public int dwDestination;

	public int dwSource;

	public int dwLineID;

	public int fdwLine;
	
	public int dwUser;

	public int dwComponentType;

	public int cChannels;

	public int cConnections;

	public int cControls;

	public static final int sizeof = Extension.IsUnicode ? 280 : 168;
}
