/*******************************************************************************
 * Copyright 2015-18 Test Mile Software Testing Pvt Ltd
 * 
 * Website: www.TestMile.com
 * Email: support [at] testmile.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.testmile.daksha.tpi.sysauto.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.text.WordUtils;

import com.testmile.daksha.tpi.batteries.console.Console;

public class DataUtils {

	public static Map<String, String> zip(String[] left, String[] right) {
		Map<String, String> zipped = new HashMap<String, String>();
		for (int i = 0; i < left.length; i++) {
			zipped.put(left[i], right[i]);
		}
		return zipped;
	}

	public static Map<String, String> zip(List<String> left, List<String> right) {
		Map<String, String> zipped = new HashMap<String, String>();
		for (int i = 0; i < left.size(); i++) {
			zipped.put(left.get(i), right.get(i));
		}
		return zipped;
	}

	public static Map<String, Object> zipObjectValues(List<String> left, List<Object> right) {
		Map<String, Object> zipped = new HashMap<String, Object>();
		for (int i = 0; i < left.size(); i++) {
			zipped.put(left.get(i), right.get(i));
		}
		return zipped;
	}

	public static String join(String[] inArray, String delimiter) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inArray.length; i++) {
			sb.append(inArray[i]);
			sb.append(delimiter);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String join(List<String> inArray, String delimiter) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inArray.size(); i++) {
			sb.append(inArray.get(i));
			sb.append(delimiter);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String flatten(String[] inArray) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inArray.length; i++) {
			sb.append(String.format("[%d]\t", i) + inArray[i] + "\n");
		}
		return sb.toString();
	}

	public static String flatten(Object[] inArray) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inArray.length; i++) {
			sb.append(String.format("[%d]\t", i) + inArray[i].toString() + "\n");
		}
		return sb.toString();
	}

	public static String flatten(int[] inArray) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inArray.length; i++) {
			sb.append(String.format("[%d]\t", i) + Integer.toString(inArray[i]) + "\n");
		}
		return sb.toString();
	}

	public static String toString(int[] inArray) {
		List<String> sb = new ArrayList<String>();
		for (int i = 0; i < inArray.length; i++) {
			sb.add(Integer.toString(inArray[i]));
		}
		String rVal = "[" + join(sb, ", ") + "]";
		return rVal;
	}

	public static String flatten(List<String> inList) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inList.size(); i++) {
			sb.append(String.format("[%d]\t", i) + inList.get(i) + "\n");
		}
		return sb.toString();
	}

	public static String flatten(Map<String, String> inHashMap) {
		StringBuffer sb = new StringBuffer();
		for (String key : inHashMap.keySet()) {
			sb.append(String.format("[%s]\t", key) + inHashMap.get(key) + "\n");
		}
		return sb.toString();
	}

	public static void print(String[] inArray) {
		Console.display(flatten(inArray));
	}

	public static void print(List<String> inArrayList) {
		Console.display(flatten(inArrayList));
	}

	public static void print(Map<String, String> inHashMap) {
		Console.display(flatten(inHashMap));
	}

	public static List<String> arrayToArrayList(String[] array) {
		return new ArrayList<String>(Arrays.asList(array));
	}

	public static String toTitleCase(String inString) {
		String processedString = inString.replace("_", " ");
		return WordUtils.capitalizeFully(processedString);
	}

	public static List<String> splitAndConvertToUpperCase(String inString) {
		List<String> retValues = new ArrayList<String>();
		String[] values = inString.split(",");
		for (String v : values) {
			retValues.add(v.trim().toUpperCase());
		}
		return retValues;
	}

	public static String flatten(Set<String> inSet) {
		StringBuffer sb = new StringBuffer();
		for (String s : inSet) {
			sb.append(String.format("[%s]", s) + "\n");
		}
		return sb.toString();
	}

	public static void print(Set<String> inSet) {
		Console.display(flatten(inSet));
	}
	
	public static List<String> split(String str, String delimiter) {
		String[] parts = str.split(delimiter);
		if (parts.length == 0) {
			return Arrays.asList(new String[] {str});
		} else {
			List<String> l = new ArrayList<String>();
			for (String p: parts) {
				l.add(p.trim());
			}
			return l;
		}
	}
	
	public static List<String> split(String inString) {
		return split(inString, ",");
	}

}
