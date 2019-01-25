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
package com.testmile.daksha.core.guiauto.element.sikuli;

import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import com.testmile.daksha.core.guiauto.automator.ConcreteGuiAutomator;
import com.testmile.daksha.core.guiauto.element.BaseConcreteGuiMultiElement;
import com.testmile.daksha.core.guiauto.element.proxy.GuiMultiElementProxy;
import com.testmile.daksha.tpi.guiauto.gui.Gui;

public class SikuliMultiElement extends BaseConcreteGuiMultiElement<Screen,Match>{

	public SikuliMultiElement(Gui gui, ConcreteGuiAutomator<Screen,Match> automator, GuiMultiElementProxy eProxy){
		super(gui, automator, eProxy);
	}
	
	public SikuliMultiElement(ConcreteGuiAutomator<Screen,Match> automator, GuiMultiElementProxy eProxy){
		this(null, automator, eProxy);
	}
	
	protected boolean doesTextMatch(Match element, String text) throws Exception{
		return false;
	}
	protected boolean doesTextContain(Match element, String text) throws Exception{
		return false;
	}
}