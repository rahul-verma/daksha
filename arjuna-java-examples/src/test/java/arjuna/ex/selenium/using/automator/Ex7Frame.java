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

package arjuna.ex.selenium.using.automator;

import arjex.s02guiauto.ep01selenium.WPLoginLogout;
import arjuna.tpi.Arjuna;
import arjuna.tpi.guiauto.GuiAutomator;
import arjuna.tpi.guiauto.With;
import arjuna.tpi.guiauto.component.Frame;
import arjuna.tpi.test.TestConfig;

public class Ex7Frame {

	public static void main(String[] args) throws Exception {
		TestConfig config = Arjuna.init();
		GuiAutomator automator = Arjuna.createGuiAutomator(config);
		
		WPLoginLogout.login(automator);
		
		automator.Element(With.linkText("Posts")).click();
		automator.Element(With.linkText("Add New")).click();
		
		automator.Element(With.id("title")).setText("Sample");
		
		With tinymce = With.id("tinymce");
		With publish = With.id("publish");
		
		// Frame by identifier and jump to root
		automator.frame(With.id("content_ifr")).focus();
		automator.Element(tinymce).setText("This is a test - frame by name.");
		automator.domRoot().focus();
		automator.Element(publish).click();
		
		// Frame by index
		automator.frame(With.index(0)).focus();
		automator.Element(tinymce).setText("This is a test - frame by index.");
		// Focusing on root from frame itself
		automator.domRoot().focus();
		automator.Element(publish).click();
		
		// jump to parent
		Frame frame = automator.domRoot().frame(With.xpath("//iframe"));
		frame.focus();
		automator.Element(tinymce).setText("This is a test - jumping to parent after this.");
		frame.parent().focus();
		automator.Element(publish).click();
		
		WPLoginLogout.logout(automator);
	}

}
