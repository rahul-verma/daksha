/*******************************************************************************
 * Copyright 2015-19 Test Mile Software Testing Pvt Ltd
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

package com.testmile.setu.requester.guiauto.gui;

import com.testmile.setu.requester.guiauto.With;
import com.testmile.setu.requester.guiauto.automator.AppAutomator;
import com.testmile.setu.requester.guiauto.automator.GuiAutomator;
import com.testmile.setu.requester.guiauto.component.ChildWindow;
import com.testmile.setu.requester.guiauto.component.DropDown;
import com.testmile.setu.requester.guiauto.component.Frame;
import com.testmile.setu.requester.guiauto.component.GuiElement;
import com.testmile.setu.requester.guiauto.component.GuiMultiElement;
import com.testmile.setu.requester.guiauto.component.RadioGroup;

public interface Gui extends AppAutomator{

	void addChild(String label, Gui gui);

	GuiElement element(String name) throws Exception;

	GuiMultiElement multiElement(String name) throws Exception;
	
	DropDown dropdown(String name) throws Exception;

	RadioGroup radioGroup(String name) throws Exception;
	
	ChildWindow childWindow(String name) throws Exception;
	
	Frame frame(String name) throws Exception;

	GuiAutomator automator();

}
