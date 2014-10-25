package com.cronje.martin.amf.types;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cronje.martin.amf.util.TraitNamesRepository;
import com.cronje.martin.lso.amf.AMF3Deserializer;

public class AMF3Object implements AMF3InputElement {

	AMF3Utf8String className = null;

	private List<AMF3Utf8String> traitNames = null;
	private List<AMF3InputElement> sealedMembers = new ArrayList<AMF3InputElement>();

	public void deserializeClassName(DataInputStream dis) throws IOException {
		className = new AMF3Utf8String();
		className.deserialize(dis);
		System.out.println("Class Name: " + className);
	}

	public AMF3Utf8String getClassName() {
		return className;
	}

	public void setClassName(AMF3Utf8String className) {
		this.className = className;
	}

	public void deserialize(DataInputStream dis) throws IOException {
		AMF3VariableLengthInteger optionsElement = new AMF3VariableLengthInteger();
		optionsElement.deserialize(dis);
		int options = optionsElement.getValue();
		System.out.println("Options: 0x"
				+ Integer.toHexString(options).toUpperCase());
		boolean dynamic = false;
		int traitCount = 0;
		if ((options & 0x01) == 0) {
			throw new UnsupportedOperationException(
					"Object references not supported yet");
		} else {
			if ((options & 0x02) == 0) {
				int index = (options & 0xFFFFFFFC) >> 2;
				System.out.println("Trait reference index: " + index);
				traitNames = TraitNamesRepository.getTraitNameList(index);
				traitCount = traitNames.size();
			} else {
				if ((options & 0x04) == 1) {
					throw new UnsupportedOperationException(
							"Externalizable object traits not supported yet");
				} else {
					if ((options & 0x08) == 1) {
						throw new UnsupportedOperationException(
								"Dynamic types not supported yet");
					} else {
						dynamic = false;
						System.out.println("Dynamic types not present");
						traitCount = (options & 0xFFFFFFF0) >> 4;
						System.out.println("Traits: " + traitCount);
						deserializeClassName(dis);
						deserializeTraitNames(dis, traitCount);
					}
				}
			}
		}
		deserializeMembers(dis, traitCount);
		if (dynamic) {
			throw new UnsupportedOperationException(
					"Dynamic types not supported");
		}
	}

	private void deserializeTraitNames(DataInputStream dis, int traitCount)
			throws IOException {
		traitNames = new ArrayList<AMF3Utf8String>();
		AMF3Utf8String traitName;
		for (int i = 0; i < traitCount; ++i) {
			traitName = new AMF3Utf8String();
			traitName.deserialize(dis);
			traitNames.add(traitName);
			System.out.println("Trait Name " + (i + 1) + ": " + traitName);
		}
		TraitNamesRepository.addTraitNameList(traitNames);
	}

	public void deserializeMembers(DataInputStream dis, int count)
			throws IOException {
		AMF3InputElement element;
		for (int i = 0; i < count; ++i) {
			if (traitNames != null) {
				System.out.println("Deserializing sealed member: "
						+ traitNames.get(i));
			}
			element = AMF3Deserializer.deserializeValueType(dis);
			sealedMembers.add(element);
			System.out.println(element.toString());
		}
	}

	public void prettyPrint(FileWriter writer, String indent)
			throws IOException {
		writer.append("'");
		if (className != null) {
			className.prettyPrint(writer, null);
		}
		writer.append("' : (").append(System.lineSeparator());
		for (int i = 0; i < sealedMembers.size(); ++i) {
			writer.append(indent);
			if (traitNames != null) {
				traitNames.get(i).prettyPrint(writer, null);
				writer.append(": ");
			}
			sealedMembers.get(i).prettyPrint(writer, indent + "  ");
			writer.append(System.lineSeparator());
		}
		writer.append(indent.substring(2)).append(")");
		writer.flush();
	}
}
