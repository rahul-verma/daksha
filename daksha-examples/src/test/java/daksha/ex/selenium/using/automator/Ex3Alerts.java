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
import com.testmile.daksha.tpi.test.DakshaTestConfig;
import com.testmile.setu.requester.guiauto.automator.GuiAutomator;
import com.testmile.setu.requester.guiauto.component.Alert;

public class Ex3Alerts {

	public static void main(String[] args) throws Exception {
		DakshaTestConfig config = Daksha.init();
		GuiAutomator automator = Daksha.createGuiAutomator(config);
		
		WPLoginLogout.login(automator);
		
		automator.browser().executeJavaScript("alert('dummy')");
		automator.alert().confirm();
		automator.browser().executeJavaScript("alert('dummy')");
		automator.alert().dismiss();
		
		automator.browser().executeJavaScript("alert('Sample')");
		Alert alert = automator.alert();
		assert alert.getText() == "Sample";
		alert.confirm();
		
		automator.browser().executeJavaScript("prompt('Are You Sure?')");
		alert = automator.alert();
		alert.sendText("Yes");	
		alert.confirm();
		
		WPLoginLogout.logout(automator);
	}

}
