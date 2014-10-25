package com.cronje.martin.amf.types;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.cronje.martin.amf.util.StringsRepository;
import com.cronje.martin.amf.util.UTF8Char;

public class AMF3Utf8String implements AMF3InputElement {

	private String value;

	public void deserialize(DataInputStream dis) throws IOException {
		AMF3VariableLengthInteger lengthOrRef = new AMF3VariableLengthInteger();
		lengthOrRef.deserialize(dis);
		int lengthOrRefValue = lengthOrRef.getValue();
		if ((lengthOrRefValue & 0x01) == 0) {
			value = StringsRepository.getString((lengthOrRefValue & 0xFFFFFFFE) >> 1);
		} else {
			int[] cp = new int[(lengthOrRefValue & 0xFFFFFFFE) >> 1];
			for (int i = 0; i < cp.length; ++i) {
				cp[i] = UTF8Char.readUtf8Char(dis);
			}
			value = new String(cp, 0, cp.length);
			if(cp.length != 0) {
				StringsRepository.addString(value);
			}
		}
		System.out.println("AFM3Utf8String: " + value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public void prettyPrint(FileWriter writer, String indent)
			throws IOException {
		if(value == null || value.isEmpty()) {
			writer.append("");
		}
		else {
			writer.append(value);
		}
	}

}
