package com.cronje.martin.amf.types;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class AMF3IntegerVector implements AMF3InputElement {

	private int[] values;

	public void deserialize(DataInputStream dis) throws IOException {
		AMF3VariableLengthInteger u29Object = new AMF3VariableLengthInteger();
		u29Object.deserialize(dis);
		int u29 = u29Object.getValue();
		int size = (u29 & 0xFFFFFFFE) >> 1;
		byte fixed = dis.readByte();
		if ((u29 & 0x01) == 0) {
			throw new UnsupportedOperationException(
					"Integer Vector references unsupported");
		} else {
			System.out.println("Deserializing " + size + " integers");
			values = new int[size];
			for (int i = 0; i < size; ++i) {
				values[i] = dis.readInt();
			}
		}
		System.out.println("Integer vector ("
				+ (fixed == 0 ? "fixed-length" : "variable-length") + ") ["
				+ values.length + "]");
	}

	public void prettyPrint(FileWriter writer, String indent)
			throws IOException {
		writer.append("<");
		if (values.length != 0) {
			writer.append(System.lineSeparator()).append(indent)
					.append(Integer.toString(values[0]));
			for (int i = 1; i < values.length; ++i) {
				writer.append(",").append(System.lineSeparator());
				writer.append(indent).append(Integer.toString(values[i]));
			}
		}
		writer.append(System.lineSeparator()).append(indent.substring(2))
				.append(">");
	}

}
