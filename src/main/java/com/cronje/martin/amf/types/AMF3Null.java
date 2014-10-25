package com.cronje.martin.amf.types;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class AMF3Null implements AMF3InputElement {

	public void deserialize(DataInputStream dis) throws IOException {
		System.out.println("null");
	}

	public void prettyPrint(FileWriter writer, String indent)
			throws IOException {
		writer.append("Null");
	}

}
