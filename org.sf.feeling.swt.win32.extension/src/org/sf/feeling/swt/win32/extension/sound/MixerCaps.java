package org.sf.feeling.swt.win32.extension.sound;

import org.eclipse.swt.internal.win32.OS;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.MIXERCAPS;
import org.sf.feeling.swt.win32.internal.extension.MIXERCAPSA;
import org.sf.feeling.swt.win32.internal.extension.MIXERCAPSW;

public class MixerCaps
{
	private MIXERCAPS mixerCaps;

	MixerCaps(MIXERCAPS mixerCaps)
	{
		this.mixerCaps = mixerCaps;
	}

	public int getWMid()
	{
		return mixerCaps.wMid;
	}

	public int getWPid()
	{
		return mixerCaps.wPid;
	}

	public int getVDriverVersion()
	{
		return mixerCaps.vDriverVersion;
	}

	public int getFdwSupport()
	{
		return mixerCaps.fdwSupport;
	}

	public int getCDestinations()
	{
		return mixerCaps.cDestinations;
	}

	public String getSzPname()
	{
		char[] chars;
		if (mixerCaps instanceof MIXERCAPSA)
		{
			MIXERCAPSA mixerCapsA = (MIXERCAPSA) mixerCaps;
			chars = new char[Extension.MAX_PATH];
			byte[] bytes = mixerCapsA.szPname;
			OS.MultiByteToWideChar(Extension.CP_ACP, Extension.MB_PRECOMPOSED, bytes,
					bytes.length, chars, chars.length);
		}
		else
		{
			MIXERCAPSW mixerCapsW = (MIXERCAPSW) mixerCaps;
			chars = mixerCapsW.szPname;
		}
		int index = 0;
		while (index < chars.length)
		{
			if (chars[index] == 0) break;
			index++;
		}
		return new String(chars, 0, index);
	}
}
