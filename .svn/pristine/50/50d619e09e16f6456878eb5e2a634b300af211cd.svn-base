package com.yuchengtech.mobile.server.web.utils;

public class Base64 {

	private static byte base64Alphabet[] = {
		65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 
		75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 
		85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 
		101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 
		111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 
		121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 
		56, 57, 60, 47
	}; // '< 60' 43 A-Za-z0-9</=


	
	private static byte pad = 61; //'='

	
/**
 * Base64 constructor comment.
 */
public Base64() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-6-19 16:02:00)
 * @return byte[]
 * @param src byte
 */
public static byte[] decode(byte[] src) {

	int i, j, k, l;
	k=0;
	int n=0;
	
	byte dst[] = new byte[src.length];

	
	for(i=0; i<src.length; i+=4)
	{
		n = 0;
		l=0;
		for(j=0; j<4; j++)
		{
			if( (l = Base64.getReverseMapping(src[ i +j ] )) != -1)
				n = (n << 6) + l;
			else
				break;
		}
		if( j == 4)
		{
			dst[ k++ ] = (byte)((n >> 16 )& 0xff);
			dst[ k++ ] = (byte)((n >> 8 )& 0xff);
			dst[ k++ ] = (byte)( n & 0xff);
		}
		else if( j == 3)
		{
			n = n << 6;
			dst[ k++ ]  = (byte)((n >> 16 )& 0xff); //(byte)((n >> 16 )& 0xff);
			dst[ k++ ]  = (byte)(( n >> 8 )& 0xff); //(byte)((n >> 8 )& 0xff);

			break;
		}
		else if( j == 2)
		{
			n = n << 12;
			dst[ k++ ]  = (byte)((n >> 16 )& 0xff);//(byte)((n >> 16 )& 0xff);
			break;
		}
	}

	byte[] tmp = new byte[k];
	System.arraycopy(dst, 0, tmp, 0, k);
	return tmp;
	
}
/**
 * Insert the method's description here.
 * Creation date: (2001-6-19 16:01:35)
 * @return byte
 * @param src byte[]
 */
public static byte[] encode(byte[] src) {


	int i, k=0;
	int n=0;
	int len = src.length;
		
	byte dst[] = new byte[len * 2];
	
	for( i=0; (i + 2)<len; i+=3)
	{
	     n = ((int)src[i] & 0xff) << 16;
	     n = n  + (((int)src[i + 1] & 0xff) << 8);
	     n = n + ((int)src[i + 2] & 0xff);
	
		dst[ k++ ] = base64Alphabet[(n>>18) &0x3f];
		dst[ k++ ] = base64Alphabet[(n>>12) &0x3f];
		dst[ k++ ] = base64Alphabet[(n>>6) &0x3f];
		dst[ k++ ] = base64Alphabet[(n) &0x3f];
	}
	if( len%3 == 2)
	{
		n = ((int)src[i] & 0xff) << 16;
		n = n + (((int)src[i+1] & 0xff) <<8);
		
		dst[ k++ ] = base64Alphabet[(n>>18) &0x3f];
		dst[ k++ ] = base64Alphabet[(n>>12) &0x3f];
		dst[ k++ ] = base64Alphabet[(n>>6) &0x3f];
		dst[ k++ ] = pad;
	}
	else if( len%3 == 1)
	{
		n = ((int)src[i] & 0xff) << 16;
		dst[ k++ ] = base64Alphabet[(n>>18) &0x3f];
		dst[ k++ ] = base64Alphabet[(n>>12) &0x3f];
		dst[ k++ ] = pad;
		dst[ k++ ] = pad;
	}

	byte[] retBuf = new byte[k];

	System.arraycopy(dst, 0, retBuf, 0, k);
	return retBuf;
	
}
/**
 * Insert the method's description here.
 * Creation date: (2001-6-19 16:03:00)
 * @return byte
 * @param chr byte
 */
private static byte getReverseMapping(byte chr) {
	for( int i=0; i<64; i++)
	{
		if( base64Alphabet[i] == chr)
			return (byte)i;
	}
	return (byte) -1;
}
}
