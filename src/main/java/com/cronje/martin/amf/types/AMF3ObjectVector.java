package com.cronje.martin.amf.types;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class AMF3ObjectVector implements AMF3InputElement {

	private AMF3Utf8String typeName = null;
	private AMF3Object[] objects = null;

	public void deserialize(DataInputStream dis) throws IOException {
		AMF3VariableLengthInteger u29Object = new AMF3VariableLengthInteger();
		u29Object.deserialize(dis);
		int u29 = u29Object.getValue();
		int size = (u29 & 0xFFFFFFFE) >> 1;
		byte fixed = dis.readByte();
		typeName = new AMF3Utf8String();
		typeName.deserialize(dis);
		System.out.println("Type Name: " + typeName);
		byte marker;
		if ((u29 & 0x01) == 0) {
			throw new UnsupportedOperationException(
					"Object Vector references unsupported");
		} else {
			System.out.println("Deserializing " + size + " objects");
			objects = new AMF3Object[size];
			for (int i = 0; i < size; ++i) {
				marker = dis.readByte();
				if (marker != 0x0A) {
					throw new IllegalArgumentException("Expected object");
				}
				objects[i] = new AMF3Object();
				objects[i].deserialize(dis);
			}
		}
		System.out.println("Object vector ("
				+ (fixed == 0 ? "fixed-length" : "variable-length") + ") ["
				+ objects.length + "]");
	}

	public void prettyPrint(FileWriter writer, String indent)
			throws IOException {
		if (typeName != null) {
			writer.append("(type='");
		}
		typeName.prettyPrint(writer, indent);
		writer.append("') <");
		if (objects.length != 0) {
			writer.append(System.lineSeparator()).append(indent);
			objects[0].prettyPrint(writer, indent + "  ");
			for (int i = 1; i < objects.length; ++i) {
				writer.append(",").append(System.lineSeparator())
						.append(indent);
				objects[i].prettyPrint(writer, indent + "  ");
			}
		}
		writer.append(System.lineSeparator()).append(indent.substring(2))
				.append(">");
	}

}
