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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.testmile.daksha.tpi.sysauto.utils.DataUtils;

public class FileLine2MapReader {
	private String[] headers = null;
	private FileLine2ArrayReader reader = null;
	private String delimiter = null;

	public FileLine2MapReader(String filePath) throws Exception {
		reader = new FileLine2ArrayReader(filePath);
		this.setDelimiter(",");
		this.populateHeaders();
	}

	public FileLine2MapReader(String filePath, String delimiter) throws Exception {
		reader = new FileLine2ArrayReader(filePath, delimiter);
		this.populateHeaders();
	}

	protected void populateHeaders() throws Exception {
		String line = reader.nextRawLine();
		if ((line != null) && (line != "EMPTY_LINE")) {
			this.headers = reader.splitLine(line);
		} else {
			throw new Exception("Invalid input file data. Empty headers line.");
		}
	}

	public String[] getHeaders() {
		return this.headers;
	}

	protected Map<String, String> zip(String[] lineParts) throws Exception {
		if (lineParts.length != this.headers.length) {
			throw new Exception("Invalid input file data. Number of headers and data values do not match.\r\n"
					+ DataUtils.flatten(this.headers) + "\r\n" + DataUtils.flatten(lineParts) + "\r\n");
		} else {
			return DataUtils.zip(this.headers, lineParts);
		}
	}

	public Map<String, String> next() throws Exception {
		String[] lineParts = reader.next();
		Map<String, String> zipped = null;
		if (lineParts == null) {
			return null;
		} else {
			zipped = this.zip(lineParts);
			if (zipped.containsKey("EXCLUDE")) {
				String exclude = zipped.get("EXCLUDE").toLowerCase().trim();
				if (exclude.equals("y") || exclude.equals("yes") || exclude.equals("true")) {
					zipped = this.next();
				}
				if (zipped != null) {
					zipped.remove("EXCLUDE");
				}

			}
			return zipped;
		}
	}

	public List<HashMap<String, String>> all() throws Exception {
		List<HashMap<String, String>> allLines = new ArrayList<HashMap<String, String>>();
		for (String[] line : reader.all()) {
			allLines.add((HashMap<String, String>)this.zip(line));
		}
		return allLines;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
}
