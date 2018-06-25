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
package daksha.core.cleanup.loader;

import java.io.File;

abstract public class FileBasedUiDefLoader extends BaseUiDefLoader{
	private File defFile= null;
	private String defPath = null;

	public FileBasedUiDefLoader(String name, String defFilePath) throws Exception {
		super(name);
		this.defPath = defFilePath;
		this.defFile = new File(defFilePath);
		if (!this.defFile.isAbsolute()){
			this.throwRelativePathException("constructor", defFilePath);
		} 
		
		if (!this.defFile.exists()){
			this.throwFileNotFoundException("constructor", defFilePath);
		} 
		
		if (!this.defFile.isFile()){
			this.throwNotAFileException("constructor", defFilePath);
		}
	}

	protected String getDefFilePath(){
		return defPath;
	}
}
