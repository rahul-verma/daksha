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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LabeledStringsFileReader {
	FileLineReader reader = null;
	private List<String> labels = new ArrayList<String>();
	private Map<String, ArrayList<String>> labeledStrings = new HashMap<String, ArrayList<String>>();
	private String currentLabel = null;

	public LabeledStringsFileReader(String filePath) throws IOException {
		reader = new FileLineReader(filePath);
		init();
	}

	private void addLabel(String label) {
		if (!labeledStrings.containsKey(label)) {
			labeledStrings.put(label, new ArrayList<String>());
			labels.add(label);
		}
	}

	private void addString(String inString) {
		if (this.currentLabel != null) {
			labeledStrings.get(this.currentLabel).add(inString);
		}
	}

	private void handleLine(String line) {
		String trimmedLine = line.trim();
		String pattern = "\\[\\s*(.*?)\\s*\\]";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(trimmedLine);
		if (m.find()) {
			addLabel(m.group(1));
			this.currentLabel = m.group(1);
		} else {
			if (!trimmedLine.startsWith("#")) {
				addString(trimmedLine);
			}
		}
	}

	private void init() {
		List<String> lines = reader.all();
		for (String line : lines) {
			handleLine(line);
		}
	}

	public void close() {
		this.reader.close();
	}

	public List<String> getLabels() {
		return labels;
	}

	public Map<String, ArrayList<String>> getAllLabeledStrings() {
		return labeledStrings;
	}

	public List<String> getStringsForLabel(String label) {
		return labeledStrings.get(label);
	}
}
