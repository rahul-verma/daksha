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

import java.util.List;
import java.util.Map;

public class ExcelFileLine2ArrayReader extends ExcelFileReader {

	public ExcelFileLine2ArrayReader(String path) throws Exception {
		super(path);
	}

	public synchronized List<Object> next() throws Exception {
		if (this.getCurrentRowIndex() < this.getRowCount()) {
			List<Object> retArray = null;
			while (true) {
				retArray = getRowAsArrayList(this.getCurrentRowIndex());
				this.setCurrentRowIndex(this.getCurrentRowIndex() + 1);
				if (retArray == null){
					if (this.getCurrentRowIndex() > this.getRowCount()){
						return null;
					} else {
						continue;
					}
				} else {
					break;
				}
			}
			
			Map<String, Object> zipped = zip(retArray);
			if (zipped.containsKey("EXCLUDE")) {
				String exclude = ((String) zipped.get("EXCLUDE")).toLowerCase().trim();
				
				if (retArray != null) {
					retArray.remove(this.getHeaders().indexOf("EXCLUDE"));
				}
			}
			return retArray;
		} else {
			return null;
		}
	}

}
