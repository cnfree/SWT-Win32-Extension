package org.sf.feeling.swt.win32.extension.sound;

import org.eclipse.swt.internal.win32.OS;
import org.sf.feeling.swt.win32.internal.extension.Extension;
import org.sf.feeling.swt.win32.internal.extension.MIXERLINE;
import org.sf.feeling.swt.win32.internal.extension.MIXERLINEA;
import org.sf.feeling.swt.win32.internal.extension.MIXERLINEW;

public class MixerLine
{
	public static final int sizeof = MIXERLINE.sizeof;

	MIXERLINE mixerLine;

	MixerLine(MIXERLINE mixerLine)
	{
		this.mixerLine = mixerLine;
	}

	public MixerLine()
	{
		if (Extension.IsUnicode) mixerLine = new MIXERLINEW();
		else
			mixerLine = new MIXERLINEA();
	};

	public int getCbStruct()
	{
		return mixerLine.cbStruct;
	}

	public void setCbStruct(int cbStruct)
	{
		mixerLine.cbStruct = cbStruct;
	}

	public int getDwDestination()
	{
		return mixerLine.dwDestination;
	}

	public void setDwDestination(int dwDestination)
	{
		mixerLine.dwDestination = dwDestination;
	}

	public int getDwSource()
	{
		return mixerLine.dwSource;
	}

	public void setDwSource(int dwSource)
	{
		mixerLine.dwSource = dwSource;
	}

	public int getDwLineID()
	{
		return mixerLine.dwLineID;
	}

	public void setDwLineID(int dwLineID)
	{
		mixerLine.dwLineID = dwLineID;
	}

	public int getFdwLine()
	{
		return mixerLine.fdwLine;
	}

	public void setFdwLine(int fdwLine)
	{
		mixerLine.fdwLine = fdwLine;
	}

	public int getDwUser()
	{
		return mixerLine.dwUser;
	}

	public void setDwUser(int dwUser)
	{
		mixerLine.dwUser = dwUser;
	}

	public int getDwComponentType()
	{
		return mixerLine.dwComponentType;
	}

	public void setDwComponentType(int dwComponentType)
	{
		mixerLine.dwComponentType = dwComponentType;
	}

	public int getCChannels()
	{
		return mixerLine.cChannels;
	}

	public void setCChannels(int cChannels)
	{
		mixerLine.cChannels = cChannels;
	}

	public int getCConnections()
	{
		return mixerLine.cConnections;
	}

	public void setCConnections(int cConnections)
	{
		mixerLine.cConnections = cConnections;
	}

	public int getCControls()
	{
		return mixerLine.cControls;
	}

	public void setCControls(int cControls)
	{
		mixerLine.cControls = cControls;
	}

	public String getSzShortName()
	{
		char[] chars;
		if (mixerLine instanceof MIXERLINEA)
		{
			MIXERLINEA mixerLineA = (MIXERLINEA) mixerLine;
			chars = new char[16];
			byte[] bytes = mixerLineA.szShortName;
			OS.MultiByteToWideChar(Extension.CP_ACP, Extension.MB_PRECOMPOSED, bytes,
					bytes.length, chars, chars.length);
		}
		else
		{
			MIXERLINEW mixerLineW = (MIXERLINEW) mixerLine;
			chars = mixerLineW.szShortName;
		}
		int index = 0;
		while (index < chars.length)
		{
			if (chars[index] == 0) break;
			index++;
		}
		return new String(chars, 0, index);
	}

	public void setSzShortName(String shortName)
	{
		if (mixerLine instanceof MIXERLINEA)
		{
			((MIXERLINEA) mixerLine).szShortName = shortName.getBytes();
		}
		else
		{
			((MIXERLINEW) mixerLine).szShortName = shortName.toCharArray();
		}
	}

	public String getSzName()
	{
		char[] chars;
		if (mixerLine instanceof MIXERLINEA)
		{
			MIXERLINEA mixerLineA = (MIXERLINEA) mixerLine;
			chars = new char[64];
			byte[] bytes = mixerLineA.szName;
			OS.MultiByteToWideChar(Extension.CP_ACP, Extension.MB_PRECOMPOSED, bytes,
					bytes.length, chars, chars.length);
		}
		else
		{
			MIXERLINEW mixerLineW = (MIXERLINEW) mixerLine;
			chars = mixerLineW.szName;
		}
		int index = 0;
		while (index < chars.length)
		{
			if (chars[index] == 0) break;
			index++;
		}
		return new String(chars, 0, index);
	}

	public void setSzName(String name)
	{
		if (mixerLine instanceof MIXERLINEA)
		{
			((MIXERLINEA) mixerLine).szName = name.getBytes();
		}
		else
		{
			((MIXERLINEW) mixerLine).szName = name.toCharArray();
		}
	}
}
