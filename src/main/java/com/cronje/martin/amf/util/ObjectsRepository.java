package com.cronje.martin.amf.util;

import java.util.ArrayList;
import java.util.List;

import com.cronje.martin.amf.types.AMF3InputElement;

public class ObjectsRepository {
	
	private static List<AMF3InputElement> objects = null;
	
	public static void addObject(AMF3InputElement object) {
		if(objects == null) {
			objects = new ArrayList<AMF3InputElement>();
		}
		objects.add(object);
	}
	
	public static AMF3InputElement getObject(int index) {
		if(objects == null || index >= objects.size()) {
			return null;
		}
		return objects.get(index);
	}
	
}
