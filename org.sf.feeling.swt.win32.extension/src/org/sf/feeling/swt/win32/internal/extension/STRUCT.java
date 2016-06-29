package org.sf.feeling.swt.win32.internal.extension;

public class STRUCT implements java.io.Serializable {

	private static final long serialVersionUID = 668967787484473675L;

	public boolean saveToPoint(int point) {
		return Extension.SaveStructToPoint(this, point);
	}
}
