package com.cronje.martin.amf.types;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class AMF3Double implements AMF3InputElement {

	private double value;

	public void deserialize(DataInputStream dis) throws IOException {
		value = dis.readDouble();
		System.out.println("AMF3Double: " + value);
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void prettyPrint(FileWriter writer, String indent)
			throws IOException {
		writer.append(Double.toString(value));
	}

}
