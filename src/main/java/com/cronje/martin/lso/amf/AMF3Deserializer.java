package com.cronje.martin.lso.amf;

import java.io.DataInputStream;
import java.io.IOException;

import com.cronje.martin.amf.types.AMF3Boolean;
import com.cronje.martin.amf.types.AMF3Dictionary;
import com.cronje.martin.amf.types.AMF3Double;
import com.cronje.martin.amf.types.AMF3InputElement;
import com.cronje.martin.amf.types.AMF3Integer;
import com.cronje.martin.amf.types.AMF3IntegerVector;
import com.cronje.martin.amf.types.AMF3Null;
import com.cronje.martin.amf.types.AMF3Object;
import com.cronje.martin.amf.types.AMF3ObjectVector;
import com.cronje.martin.amf.types.AMF3Undefined;
import com.cronje.martin.amf.types.AMF3Utf8String;
import com.cronje.martin.amf.util.ObjectsRepository;

public class AMF3Deserializer {

	public AMF3Object deserialize(DataInputStream dis) throws IOException {
		AMF3Object object = new AMF3Object();
		object.deserializeClassName(dis);
		object.deserializeMembers(dis, 1);
		return object;
	}

	public static AMF3InputElement deserializeValueType(DataInputStream dis)
			throws IOException {
		byte marker = dis.readByte();
		AMF3InputElement element;
		switch (marker) {
		case 0x00:
			element = new AMF3Undefined();
			break;
		case 0x01:
			element = new AMF3Null();
			break;
		case 0x02:
			element = new AMF3Boolean(false);
			break;
		case 0x03:
			element = new AMF3Boolean(true);
			break;
		case 0x04:
			element = new AMF3Integer();
			break;
		case 0x05:
			element = new AMF3Double();
			break;
		case 0x06:
			element = new AMF3Utf8String();
			break;
		case 0x0A:
			element = new AMF3Object();
			ObjectsRepository.addObject(element);
			break;
		case 0x0D:
			element = new AMF3IntegerVector();
			break;
		case 0x10:
			element = new AMF3ObjectVector();
			break;
		case 0x11:
			element = new AMF3Dictionary();
			ObjectsRepository.addObject(element);
			break;
		default:
			throw new UnsupportedOperationException(
					"Unsupported Value Type Marker: " + marker);
		}
		element.deserialize(dis);
		return element;
	}

}
