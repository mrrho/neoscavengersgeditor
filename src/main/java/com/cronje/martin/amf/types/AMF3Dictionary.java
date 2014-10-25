package com.cronje.martin.amf.types;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.cronje.martin.amf.util.ObjectsRepository;
import com.cronje.martin.lso.amf.AMF3Deserializer;

public class AMF3Dictionary implements AMF3InputElement {

	private AMF3InputElement[] keys = null;
	private AMF3InputElement[] values = null;
	private AMF3Dictionary reference = null;

	public void deserialize(DataInputStream dis) throws IOException {
		AMF3VariableLengthInteger optionsObject = new AMF3VariableLengthInteger();
		optionsObject.deserialize(dis);
		int options = optionsObject.getValue();
		int nOrRef = (options & 0xFFFFFFFE) >> 1;
		System.out.println("Deserializing " + nOrRef + " dictionary items");
		if ((options & 0x01) == 0) {
			reference = (AMF3Dictionary) ObjectsRepository.getObject(nOrRef);
		} else {
			System.out.println("Deserializing " + nOrRef + " dictionary items");
			dis.readByte();
			keys = new AMF3InputElement[nOrRef];
			values = new AMF3InputElement[nOrRef];
			for (int i = 0; i < nOrRef; ++i) {
				keys[i] = AMF3Deserializer.deserializeValueType(dis);
				values[i] = AMF3Deserializer.deserializeValueType(dis);
			}
		}
	}

	public void prettyPrint(FileWriter writer, String indent)
			throws IOException {
		AMF3Dictionary dict = this;
		if (reference != null) {
			dict = reference;
		}
		writer.append("{");
		if (dict.keys != null && dict.keys.length != 0) {
			writer.append(System.lineSeparator()).append(indent);
			dict.keys[0].prettyPrint(writer, indent + "  ");
			writer.append(" = ");
			dict.values[0].prettyPrint(writer, indent + "  ");
			for (int i = 1; i < dict.keys.length; ++i) {
				writer.append(",").append(System.lineSeparator())
						.append(indent);
				dict.keys[i].prettyPrint(writer, indent + "  ");
				writer.append(" = ");
				dict.values[i].prettyPrint(writer, indent + "  ");
			}
		}
		writer.append(System.lineSeparator()).append(indent.substring(2))
				.append("}");
	}

}
