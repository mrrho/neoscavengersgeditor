package com.cronje.martin.amf.util;

import java.util.ArrayList;
import java.util.List;

import com.cronje.martin.amf.types.AMF3Utf8String;

public class TraitNamesRepository {
	
	private static List<List<AMF3Utf8String>> traitNamesList;
	
	public static void addTraitNameList(List<AMF3Utf8String> traitNames) {
		if(traitNamesList == null) {
			traitNamesList = new ArrayList<List<AMF3Utf8String>>();
		}
		traitNamesList.add(traitNames);
	}
	
	public static List<AMF3Utf8String> getTraitNameList(int index) {
		if(traitNamesList == null || index >= traitNamesList.size()) {
			return null;
		}
		return traitNamesList.get(index);
	}
	
	public static int size() {
		if(traitNamesList == null) {
			return 0;
		}
		else {
			return traitNamesList.size();
		}
	}

}
