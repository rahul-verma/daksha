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

package daksha.ex.selenium.using.automator;

import com.testmile.daksha.Daksha;
import com.testmile.daksha.core.guiauto.automator.DefaultGuiAutomator;
import com.testmile.daksha.tpi.guiauto.ChildWindow;
import com.testmile.daksha.tpi.guiauto.GuiAutomator;
import com.testmile.daksha.tpi.guiauto.MainWindow;
import com.testmile.daksha.tpi.test.TestConfig;

public class Ex4WindowHandling {

	public static void main(String[] args) throws Exception {
		TestConfig config = Daksha.init();
		GuiAutomator automator = new DefaultGuiAutomator(config);
		WPLoginLogout.login(automator);
		
		MainWindow mainWin = automator.mainWindow();
		mainWin.maximize();
		System.out.println(mainWin.getTitle());
		automator.executeJavaScript("window.open('/abc')");
		ChildWindow win = automator.newChildWindow();
		win.jump();
		System.out.println(win.getTitle());
		win.close();
		automator.executeJavaScript("window.open('/def')");
		automator.executeJavaScript("window.open('/xyz')");
		automator.closeAllChildWindows();
		System.out.println(mainWin.getTitle());
		
		WPLoginLogout.logout(automator);
	}

}
