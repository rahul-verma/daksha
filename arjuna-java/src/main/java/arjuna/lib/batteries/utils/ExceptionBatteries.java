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
package arjuna.lib.batteries.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

public class ExceptionBatteries {
	private static boolean internalBuild = false;
	
	private static String[] removeThese = new String[] {
			"com.testmile.pvt",
			"sun.reflect",
			"java.lang.reflect",
			"java.lang.Thread.run"
	};
	
	public static String getStackTraceAsString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String st = sw.toString();
		return cleanTrace(st);
	}

	public static String getStackTraceAsString(AssertionError e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String st = sw.toString();
		return cleanTrace(st);
	}

	public static String getStackTraceAsString(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String st = sw.toString();
		return cleanTrace(st);
	}

	private static String cleanTrace(String trace) {
		String lineSep = System.getProperty("line.separator");
		StringReader stringReader = new StringReader(trace);
		BufferedReader bufferedReader = new BufferedReader(stringReader);
		StringBuffer buf = new StringBuffer();

		try {
			String line = bufferedReader.readLine();
			if(line == null) {
				return "";
			}
			buf.append(line).append(lineSep);

			while((line = bufferedReader.readLine()) != null) {
				boolean isExcluded = false;
				if (!internalBuild){
					for (String excluded : removeThese) {
						if(line.contains(excluded)) {
							isExcluded = true;
							break;
						}
					}
				}
				if (!isExcluded) {
					buf.append(line).append(lineSep);
				}
			}
		}
		catch(IOException ioex) {
			// do nothing
		}

		return buf.toString();
	}
}
