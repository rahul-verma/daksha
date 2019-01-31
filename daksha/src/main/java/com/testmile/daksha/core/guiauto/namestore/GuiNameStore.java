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
package com.testmile.daksha.core.guiauto.namestore;

import java.util.HashMap;
import java.util.Map;

import com.testmile.daksha.tpi.guiauto.namestore.GuiNamespaceLoader;

public enum GuiNameStore{
	INSTANCE;

	private Map<String, GuiNamespace> nsMap =  new HashMap<String, GuiNamespace>();

	public synchronized boolean isUiDefLoader(String name){
		return nsMap.containsKey(name);
	}

	public synchronized boolean hasNamespace(String name) {
		return nsMap.containsKey(name.toLowerCase());
	}
	
	public synchronized GuiNamespace loadNamespace(String name, GuiNamespaceLoader loader) throws Exception{
		if(!hasNamespace(name)){
			loader.load();
			this.nsMap.put(name.toLowerCase(), loader.getNamespace());
		}
		return nsMap.get(name.toLowerCase());
	}
	

	public synchronized GuiNamespace getNamespace(String name) throws Exception{
		return nsMap.get(name.toLowerCase());
	}

}
