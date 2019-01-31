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
package com.testmile.daksha.core.guiauto.identifier;

import com.testmile.daksha.core.guiauto.enums.LocateBy;
import com.testmile.daksha.core.guiauto.enums.MobileNativeLocateBy;
import com.testmile.daksha.core.guiauto.enums.MobileWebLocateBy;
import com.testmile.daksha.core.guiauto.enums.NativeLocateBy;
import com.testmile.daksha.core.guiauto.enums.VisualLocateBy;
import com.testmile.daksha.core.guiauto.enums.WebLocateBy;

public class GuiElementLocator {
	private LocateBy by = null;
	private String value = null;
	
	public GuiElementLocator(LocateBy by, String value){
		this.by = by;
		this.value = value;
	}
	
	public LocateBy asLocateBy() {
		return this.by;
	}
	
	public WebLocateBy asWebLocateBy() {
		return WebLocateBy.valueOf(by.toString());
	}
	
	public MobileWebLocateBy asMobileWebLocateBy() {
		return MobileWebLocateBy.valueOf(by.toString());
	}
	
	public MobileNativeLocateBy asMobileNativeLocateBy() {
		return MobileNativeLocateBy.valueOf(by.toString());
	}
	
	public NativeLocateBy asNativeLocateBy() {
		return NativeLocateBy.valueOf(by.toString());
	}
	
	public VisualLocateBy asVisualLocateBy() {
		return VisualLocateBy.valueOf(by.toString());
	}
	
	public String getValue() {
		return value;
	}
}
