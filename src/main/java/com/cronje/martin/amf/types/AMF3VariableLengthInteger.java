package com.cronje.martin.amf.types;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class AMF3VariableLengthInteger implements AMF3InputElement {

	private int value;

	public void deserialize(DataInputStream dis) throws IOException {
		int read = dis.read();
		value = read & 0x7F;
		if ((read & 0x80) == 0x80) {
			read = dis.read();
			value = (value << 7) | (read & 0x7F);
		}
		if ((read & 0x80) == 0x80) {
			read = dis.read();
			value = (value << 7) | (read & 0x7F);
		}
		if ((read & 0x80) == 0x80) {
			read = dis.read();
			value = (value << 8) | read;
		}
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	public void prettyPrint(FileWriter writer, String indent)
			throws IOException {
		writer.append(Integer.toString(value));
	}

}
