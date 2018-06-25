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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import daksha.tpi.sysauto.file.IniFileReader;

public class IniUiDefLoader extends FileBasedUiDefLoader{
	private IniFileReader reader = null;

	public IniUiDefLoader(String defFilePath) throws Exception{
		super("Ini File UI Definition Loader", defFilePath);
	}
	
	@Override
	public void load() throws Exception{
		reader = new IniFileReader(getDefFilePath());
		Set<String> elements = reader.getAllSections();
		
		Map<String, HashMap<String,String>> elementHM =  new HashMap<String, HashMap<String,String>>();
		for (String element: elements){
			addElementMetaData(element, (Map<String,String>) reader.getSectionData(element));
		}		
	}
}
