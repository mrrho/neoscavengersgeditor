package com.cronje.martin.amf.types;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class AMF3Undefined implements AMF3InputElement {

	public void deserialize(DataInputStream dis) throws IOException {

	}

	public void prettyPrint(FileWriter writer, String indent)
			throws IOException {
		writer.append("Undefined");
	}

}
