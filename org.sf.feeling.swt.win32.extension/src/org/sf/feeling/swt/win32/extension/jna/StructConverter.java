/*******************************************************************************
 * Copyright (c) 2011 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/
package org.sf.feeling.swt.win32.extension.jna;


public class StructConverter
{
    private StructConverter()
    {
        // never called
    }
    
    /**
     * @param val max 16 characters long. Should not contain any '0x'-prefix.
     * 			the empty string will return 0.
     * @throws NumberFormatException if val is longer than 16 characters
     * 			or if val contains non parseable characters ([0-9, a-f, A-F] is legal).
     * @throws NullPointerException if val is null.
     * @see #parseInt(String)
     */
    public static long parseLong(String val)
    {
        return parseHex(val, 16);
    }
    
    /**
     * parse a hex string into an int. This method differs from
     * {@link Integer#parseInt(java.lang.String, int) Integer.parseInt(val, 16)}
     * in that it does not allow '-' and allows the full range of eight
     * hex characters (eg. 'FFFFFFFF' is allowed). Since int is signed
     * the string is parsed in the standard signed 2-complement
     * representaion, eg:
     * <ul>
     * 	<li>7FFFFFFF -> {@link Integer#MAX_VALUE Integer.MAX_VALUE}</li>
     * 	<li>80000000 -> {@link Integer#MIN_VALUE Integer.MIN_VALUE}</li>
     * 	<li>FFFFFFFF -> -1</li>
     * </ul>
     *
     * @param val max 8 characters long. Should not contain any '0x'-prefix.
     * 			the empty string will return 0.
     * @throws NumberFormatException if val is longer than 8 characters
     * 			or if val contains non parseable characters ([0-9, a-f, A-F] is legal).
     * @throws NullPointerException if val is null.
     */
    public static int parseInt(String val)
    {
        return (int)parseHex(val, 8);
    }
    
    /**
     * @param val max 4 characters long. Should not contain any '0x'-prefix.
     * 			the empty string will return 0.
     * @throws NumberFormatException if val is longer than 4 characters
     * 			or if val contains non parseable characters ([0-9, a-f, A-F] is legal).
     * @throws NullPointerException if val is null.
     * @see #parseInt(String)
     */
    public static short parseShort(String val)
    {
        return (short)parseHex(val, 4);
    }
    
    /**
     * @param maxChars the maximum number of hex digits, should be in the range ]0; 16]
     * @throws NumberFormatException if unable to parse val.
     */
    private static long parseHex(String val, int maxChars)
    {
        int length = val.length();
        if(length > maxChars)
        {
            throw new NumberFormatException("unable to parse: '" + val + "' (too many digits)");
        }
        int shift = 0;
        long res = 0;
        for(int i = length - 1; i >= 0; i--)
        {
            int digit = Character.digit(val.charAt(i), 16);
            if(digit < 0)
            {
                throw new NumberFormatException("unable to parse: '" + val + "' (invalid char at pos: " + i + ")");
            }
            res += ((long)digit) << shift;
            shift += 4;
        }
        return res;
    }
    
    
    static public long bytesIntoLong(byte[] bytes, int offset)
    {
        int nLo = bytesIntoInt(bytes, offset);
        int nHi = bytesIntoInt(bytes, offset + 4);
        return ((long)(nHi) << 32) + (nLo & 0xFFFFFFFFL);
    }
    
    static public double bytesIntoDouble(byte[] bytes, int offset)
    {
        double d = Double.longBitsToDouble(bytesIntoLong(bytes, offset));
        return d;
    }
    
    static public float bytesIntoFloat(byte[] bytes, int offset)
    {
        float f = Float.intBitsToFloat(bytesIntoInt(bytes, offset));
        return f;
    }
    
    
    static public boolean bytesIntoBoolean(byte[] bytes, int offset)
    {
        return bytes[offset] != 0;
    }
    
    static public int bytesIntoInt(byte[] bytes, int offset)
    {
        int l = (bytes[offset+0] & 0xff) |
                ((bytes[offset + 1] & 0xff) << 8) |
                ((bytes[offset + 2] & 0xff) << 16) |
                ((bytes[offset + 3] & 0xff) << 24);
        return l;
    }
    
    static public short bytesIntoShort(byte[] bytes, int offset)
    {
        int l =  (bytes[offset] & 0xff) |
                ((bytes[offset + 1] & 0xff) << 8);
        return (short) l;
    }
    
    static public int longIntoBytes(long data, byte[] bytes, int start)
    {
        bytes[start++] = (byte) (data);
        bytes[start++] = (byte) (data >>> 8);
        bytes[start++] = (byte) (data >>> 16);
        bytes[start++] = (byte) (data >>> 24);
        bytes[start++] = (byte) (data >>> 32);
        bytes[start++] = (byte) (data >>> 40);
        bytes[start++] = (byte) (data >>> 48);
        bytes[start++] = (byte) (data >>> 56);
        return start;
    }
    
    static public int floatIntoBytes(float f, byte[] bytes, int start)
    {
        int data = Float.floatToIntBits(f);
        bytes[start++] = (byte) (data);
        bytes[start++] = (byte) (data >>> 8);
        bytes[start++] = (byte) (data >>> 16);
        bytes[start++] = (byte) (data >>> 24);
        return start;
    }
    
    static public int doubleIntoBytes(double d, byte[] bytes, int start)
    {
        long data = Double.doubleToLongBits(d);
        bytes[start++] = (byte) (data);
        bytes[start++] = (byte) (data >>> 8);
        bytes[start++] = (byte) (data >>> 16);
        bytes[start++] = (byte) (data >>> 24);
        bytes[start++] = (byte) (data >>> 32);
        bytes[start++] = (byte) (data >>> 40);
        bytes[start++] = (byte) (data >>> 48);
        bytes[start++] = (byte) (data >>> 56);
        return start;
    }
    
    static public int intIntoBytes(int data, byte[] bytes, int start)
    {
        bytes[start++] = (byte) (data);
        bytes[start++] = (byte) (data >>> 8);
        bytes[start++] = (byte) (data >>> 16);
        bytes[start++] = (byte) (data >>> 24);
        return start;
    }
	
	public static final byte[] getBytes(int value)
	{
		byte[] x = new byte[4];
		
		x[0] = (byte)(value      );
		x[1] = (byte)(value >>  8);
		x[2] = (byte)(value >> 16);
		x[3] = (byte)(value >> 24);
		
		return x;
	}
    
    static public int shortIntoBytes(short data, byte[] bytes, int start)
    {
        bytes[start++] = (byte) (data);
        bytes[start++] = (byte) (data >>> 8);
        return start;
    }
    
    static public int byteArrayIntoBytes(byte[] src, byte[] dest, int start)
    {
        System.arraycopy(src, 0, dest, start, src.length);
        return start + src.length;
    }
    
    public static short swapShort(short x)
    {
        return (short) (((x & 0xFF) << 8) | ((x & 0xFF00) >>> 8));
    }
    
    public static int swapInt(int i)
    {
        int byte0 = i & 0xff;
        int byte1 = (i>>8) & 0xff;
        int byte2 = (i>>16) & 0xff;
        int byte3 = (i>>24) & 0xff;
        return ((byte0<<24) | (byte1<<16) | (byte2<<8) | byte3);
    }

    public static int swapByte(byte b)
    {
        return b & 0xff;
    }
}
