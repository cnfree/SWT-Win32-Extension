package org.sf.feeling.swt.win32.internal.extension;

public abstract class MIXERCAPS extends STRUCT
{
	private static final long serialVersionUID = 2401785660472033236L;

	public int wMid;

	public int wPid;

	public int vDriverVersion;

	public int fdwSupport;

	public int cDestinations;

	public static int sizeof = Extension.IsUnicode ? 540 : 280;
}
