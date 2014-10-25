package com.cronje.martin.amf.types;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class AMF3Boolean implements AMF3InputElement {

	private boolean value;

	public AMF3Boolean(boolean b) {
		value = b;
	}

	public void deserialize(DataInputStream dis) throws IOException {
		System.out.println(value);
	}

	public void prettyPrint(FileWriter writer, String indent)
			throws IOException {
		writer.append(Boolean.toString(value));
	}

}
