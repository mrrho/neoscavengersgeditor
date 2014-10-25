package com.cronje.martin.amf.util;

import java.util.ArrayList;
import java.util.List;

public class StringsRepository {
	
	private static List<String> strings = null;
	
	public static void addString(String string) {
		if(strings == null) {
			strings = new ArrayList<String>();
		}
		strings.add(string);
	}
	
	public static String getString(int index) {
		if(strings == null || index >= strings.size()) {
			return null;
		}
		return strings.get(index);
	}
	
}
