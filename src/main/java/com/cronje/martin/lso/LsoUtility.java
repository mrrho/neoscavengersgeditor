package com.cronje.martin.lso;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.cronje.martin.amf.types.AMF3Object;
import com.cronje.martin.lso.amf.AMF3Deserializer;

public class LsoUtility {

	private static final String MAGIC_FOOTPRINT = "TCSO";

	public static void main(String[] args) throws IOException {
		DataInputStream dis = new DataInputStream(new BufferedInputStream(
				new FileInputStream(new File(args[0]))));
		LsoUtility.deserialize(dis);
		dis.close();

	}

	public static AMF3Object deserialize(DataInputStream dis)
			throws IOException {
		dis.skip(2);
		Integer fileSize = dis.readInt() + 6;
		System.out.println("File Size: " + fileSize);
		byte[] magic = new byte[4];
		dis.read(magic);
		if (!new String(magic).equals(MAGIC_FOOTPRINT)) {
			throw new UnsupportedOperationException("Expected magic footpring");
		}
		dis.skip(6);
		Short nameLength = dis.readShort();
		byte[] name = new byte[nameLength];
		dis.read(name);
		System.out.println("Object name: " + new String(name));
		Integer amfVersion = dis.readInt();
		System.out.println("Data Stream: AMF version: " + amfVersion);
		if (amfVersion == 3) {
			AMF3Deserializer amf3Deserializer = new AMF3Deserializer();
			AMF3Object root = amf3Deserializer.deserialize(dis);
			FileWriter writer = new FileWriter("pretty.txt");
			root.prettyPrint(writer, "  ");
			writer.flush();
			writer.close();
			return root;
		} else {
			throw new UnsupportedOperationException("Unsuuported AMF version: "
					+ amfVersion);
		}

	}

}
