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

package daksha.ex.gettingstarted;

import com.testmile.daksha.Daksha;
import com.testmile.daksha.core.guiauto.automator.proxy.GuiAutomatorProxy;
import com.testmile.daksha.tpi.TestContext;
import com.testmile.daksha.tpi.enums.Browser;
import com.testmile.daksha.tpi.guiauto.maker.GuiAutomatorFactory;
import com.testmile.daksha.tpi.guiauto.maker.selenium.SeleniumBuilder;

public class Basic1WithCentralTestContext{
	
	public static void main (String args[]) throws Exception {
		// Initialize and set central configuration
		TestContext centralContext = Daksha.init();
		centralContext.setGuiAutoMaxWaitTime(30);
		centralContext.setBrowserType(Browser.HTML_UNIT);
		centralContext.freeze();
		// Central Context can not be modified after this point
		
		// Create Selenium automator with central context options
		SeleniumBuilder builder = GuiAutomatorFactory.getSeleniumBuilder(centralContext);
		GuiAutomatorProxy automator = builder.build();

		automator.goTo("https://www.google.com");
		System.out.println(automator.getPageTitle());
		automator.close();
	}

}
