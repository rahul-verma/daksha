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
package com.testmile.daksha.tpi.sysauto.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.testmile.daksha.tpi.batteries.console.Console;

public class FileLineReader {
	Scanner reader = null;
	String commentPrefix = ";";

	public FileLineReader(String filePath) throws IOException {
		File file = new File(filePath);
		reader = new Scanner(file);
	}
	
	public FileLineReader(String filePath, String commentPrefix) throws IOException {
		File file = new File(filePath);
		this.commentPrefix = commentPrefix;
		reader = new Scanner(file);
	}

	String delimiter = null;

	public String next() {
		if (this.reader.hasNextLine()) {
			String line = this.reader.nextLine();
			if (line.trim().isEmpty() || line.trim().startsWith(commentPrefix)) {
				return this.next();
			} else {
				return line;
			}
		} else {
			return null;
		}
	}

	public List<String> all() {
		try {
			List<String> al = new ArrayList<String>();
			String line = null;
			while ((line = (String) this.next()) != null) {
				al.add(line);
			}
			// DSHandler.print(al);
			return al;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Console.displayExceptionBlock(e);
			return null;
		}
	}

	public void close() {
		this.reader.close();
	}

}
