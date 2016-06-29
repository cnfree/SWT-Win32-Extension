package org.sf.feeling.swt.win32.internal.extension;

public class MCI_OPEN_PARMSA extends MCI_OPEN_PARMS {

	private static final long serialVersionUID = 4914097676408232264L;

	public static MCI_OPEN_PARMSA valueOf(int point) {
		MCI_OPEN_PARMSA struct = new MCI_OPEN_PARMSA();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}
}
