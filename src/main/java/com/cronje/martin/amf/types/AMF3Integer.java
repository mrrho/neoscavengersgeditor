package com.cronje.martin.amf.types;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class AMF3Integer implements AMF3InputElement {

	AMF3VariableLengthInteger value;

	public void deserialize(DataInputStream dis) throws IOException {
		value = new AMF3VariableLengthInteger();
		value.deserialize(dis);
		if (value.getValue() >= 0x08000000 && value.getValue() < -0x08000000) {
			throw new IllegalArgumentException(
					"Special integer treatment not supported yet");
		}
		System.out.println("AMF3Integer: " + value.getValue());
	}

	public void prettyPrint(FileWriter writer, String indent)
			throws IOException {
		value.prettyPrint(writer, indent);
	}

}
