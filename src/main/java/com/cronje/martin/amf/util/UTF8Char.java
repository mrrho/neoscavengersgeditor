package com.cronje.martin.amf.util;

import java.io.DataInputStream;
import java.io.IOException;

public class UTF8Char {

	public static int readUtf8Char(DataInputStream dis) throws IOException {
		int c = dis.readByte();
		if ((c & 0x80) != 0) {
			throw new UnsupportedOperationException(
					"non-ASCII UTF-8 not supported yet");
		}
		return c;
	}

}
