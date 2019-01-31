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

package daksha.ex.testng.guiauto.variants.v3.simleapp;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.testmile.daksha.core.guiauto.enums.OSType;
import com.testmile.daksha.tpi.TestContext;
import com.testmile.daksha.tpi.guiauto.element.GuiElement;
import com.testmile.daksha.tpi.guiauto.element.GuiMultiElement;
import com.testmile.daksha.tpi.guiauto.gui.DefaultGui;
import com.testmile.daksha.tpi.guiauto.gui.Gui;
import com.testmile.daksha.tpi.guiauto.maker.GuiAutomatorFactory;
import com.testmile.daksha.tpi.guiauto.maker.selenium.SeleniumBuilder;
import com.testmile.daksha.tpi.testng.TestNGBaseTest;

public class WebTestWithSeleniumAutomator extends TestNGBaseTest{
	private ThreadLocal<Gui> threadWiseApp = new ThreadLocal<Gui>();
	
	protected void tweakCentralContext(TestContext centralContext)  throws Exception {
		centralContext.setTargetPlatform(OSType.MAC);
	}
	
	protected void setUpClass(TestContext testContext) throws Exception {
		SeleniumBuilder builder = GuiAutomatorFactory.getSeleniumBuilder(testContext);
		threadWiseApp.set(new DefaultGui("WordPress", builder.build(), "simpleapp/wordpress.gns"));
	}
	
	@Test
	public void test() throws Exception{
		Gui app = this.threadWiseApp.get();

		app.goTo(this.getContext().getValue("wp.admin.url").asString());	

		GuiElement userTextBox = app.element("LOGIN");
		userTextBox.waitUntilPresent();
		userTextBox.enterText(this.getContext().getValue("wp.username").asString());
		app.element("PASSWORD").enterText(this.getContext().getValue("wp.password").asString());
		app.element("SUBMIT").click();		
		app.waitForBody();
		
		app.element("POSTS").hover();
		app.element("CATEGORIES").click();	

		app.waitForBody();
		GuiMultiElement tags = app.elements("CAT_CHECKBOXES");
		tags.getInstanceAtOrdinal(2).check();
		tags.getInstanceAtIndex(1).uncheck();
			
		for (GuiElement element: tags.getAllInstances()){
			element.check();
			element.uncheck();
		}

		app.element("SETTINGS").click();
			
		GuiElement blogNameTextBox = app.element("BLOG_NAME");
		blogNameTextBox.enterText("Hello");
		blogNameTextBox.enterText("Hello");
		blogNameTextBox.setText("Hello");
		
		app.element("MEMBERSHIP").check();

		// Experiments with Select control - Selection using different attributes
		GuiElement roleDropDown = app.element("ROLE").asDropDown();
		roleDropDown.selectText("Author");
		assertTrue(roleDropDown.hasSelectedText("Author"), "Check Author Role Selected");
		roleDropDown.selectAtIndex(0);
		assertTrue(roleDropDown.hasSelectedIndex(0), "Check Author Role Selected");
		roleDropDown.selectValue("author");
		assertTrue(roleDropDown.hasSelectedValue("author"), "Check Author Role Selected");

		app.goTo(this.getContext().getValue("wp.logout.url").asString());
	}
	
	public void tearDownClass(TestContext testContext) throws Exception {
		this.threadWiseApp.get().close();
	}
}
