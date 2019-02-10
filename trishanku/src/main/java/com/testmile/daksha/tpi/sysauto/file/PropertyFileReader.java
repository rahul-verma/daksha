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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyFileReader {
	FileLineReader reader = null;
	String[] excludePrefixes = {};

	public PropertyFileReader(String filePath) throws IOException {
		reader = new FileLineReader(filePath);
	}

	public PropertyFileReader(String filePath, String[] excludePrefixes) throws IOException {
		reader = new FileLineReader(filePath);
		this.excludePrefixes = excludePrefixes;
	}

	public Map<String, String> getAllProperties() {
		Map<String, String> retMap = new HashMap<String, String>();
		List<String> lines = reader.all();
		for (String line : lines) {
			String[] lineParts = line.split("=", 2);
			if (lineParts.length == 2) {
				String left = lineParts[0].trim();
				boolean include = true;
				for (String exPrefix : excludePrefixes) {
					if (left.startsWith(exPrefix)) {
						include = false;
						break;
					}
				}
				if (include) {
					retMap.put(lineParts[0].trim(), lineParts[1].trim());
				}
			}
		}
		return retMap;
	}

	public void close() {
		this.reader.close();
	}
}
