/*******************************************************************************
 * Copyright (c) 2007 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/
package org.sf.feeling.swt.win32.extension.sound;

import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.internal.extension.Extension2;

public class Beep {

	public static final int BEEP_TYPE_STANDARD = 0xFFFFFFFF;
	public static final int BEEP_TYPE_ICONASTERISK = Win32.MB_ICONINFORMATION;
	public static final int BEEP_TYPE_ICONEXCLAMATION = Win32.MB_ICONWARNING;
	public static final int BEEP_TYPE_ICONHAND = Win32.MB_ICONERROR;
	public static final int BEEP_TYPE_ICONQUESTION = Win32.MB_ICONQUESTION;
	public static final int BEEP_TYPE_OK = Win32.MB_OK;

	/**
	 * The MessageBeep function plays a waveform sound. The waveform sound for
	 * each sound type is identified by an entry in the [sounds] section of the
	 * registry.
	 * 
	 * @param type
	 *            Specifies the sound type, as identified by an entry in the
	 *            [sounds] section of the registry. This parameter can be one of
	 *            the following values: <br>
	 *            Value Sound <br>
	 *            BEEP_TYPE_STANDARD Standard beep using the computer speaker <br>
	 *            BEEP_TYPE_ICONASTERISK SystemAsterisk <br>
	 *            BEEP_TYPE_ICONEXCLAMATION SystemExclamation <br>
	 *            BEEP_TYPE_ICONHAND SystemHand <br>
	 *            BEEP_TYPE_ICONQUESTION SystemQuestion <br>
	 *            BEEP_TYPE_OK SystemDefault
	 */
	public static boolean messageBeep(int type) {
		return Extension2.MessageBeep(type);
	}

	/**
	 * The Beep function generates simple tones on the speaker. The function is
	 * synchronous; it does not return control to its caller until the sound
	 * finishes.
	 * 
	 * @param freq
	 *            Windows NT: Specifies the frequency, in hertz, of the sound.
	 *            This parameter must be in the range 37 through 32,767 (0x25
	 *            through 0x7FFF).
	 * 
	 * @param druation
	 *            Windows NT: Specifies the duration, in milliseconds, of the
	 *            sound.
	 */
	public static boolean beep(int freq, int druation) {
		if (freq < 0x25 || freq > 0x7FFF)
			throw new IllegalStateException(
					"The parameter freq must be in the range 37 through 32,767 (0x25 through 0x7FFF).");
		return Extension2.Beep(freq, druation);
	}
}
