package org.sf.feeling.swt.win32.internal.extension;

public class MCI_OPEN_PARMSW extends MCI_OPEN_PARMS {

	private static final long serialVersionUID = 8673384021167409884L;

	public static MCI_OPEN_PARMSW valueOf(int point) {
		MCI_OPEN_PARMSW struct = new MCI_OPEN_PARMSW();
		Extension.CreateStructByPoint(point, struct);
		return struct;
	}

}
