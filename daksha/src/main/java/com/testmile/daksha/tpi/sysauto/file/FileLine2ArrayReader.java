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

import java.util.ArrayList;
import java.util.List;

public class FileLine2ArrayReader {
	private FileLineReader reader = null;
	private String delimiter = null;
	private String[] headers = null;

	public FileLine2ArrayReader(String filePath, String delimiter) throws Exception {
		reader = new FileLineReader(filePath);
		this.delimiter = delimiter;
		this.populateHeaders();
	}

	public FileLine2ArrayReader(String filePath) throws Exception {
		this(filePath, ",");
	}

	protected void populateHeaders() throws Exception {
		String line = nextRawLine();
		if ((line != null) && (line != "EMPTY_LINE")) {
			this.headers = splitLine(line);
		} else {
			throw new Exception("Invalid input file data. Empty headers line.");
		}
	}

	public String[] getHeaders() {
		return this.headers;
	}

	protected String[] splitLine(String line) {
		return line.split(this.delimiter);
	}

	public String[] next() {
		String line = (String) reader.next();
		if (line != null) {
			return this.splitLine(line);
		} else {
			return null;
		}
	}

	public String nextRawLine() {
		return reader.next();
	}

	public List<String[]> all() {
		List<String[]> allLines = new ArrayList<String[]>();
		for (String line : reader.all()) {
			allLines.add(this.splitLine(line));
		}
		return allLines;
	}

	public void close() {
		this.reader.close();
	}

}
