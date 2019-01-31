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
 * Unless required by automatorlicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package daksha.ex.testng.guiauto.variants.v2.automator.withExternalizedLocators;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.testmile.daksha.core.guiauto.automator.proxy.GuiAutomatorProxy;
import com.testmile.daksha.core.guiauto.enums.OSType;
import com.testmile.daksha.core.guiauto.identifier.GuiElementMetaData;
import com.testmile.daksha.core.guiauto.namestore.GuiNamespace;
import com.testmile.daksha.tpi.TestContext;
import com.testmile.daksha.tpi.guiauto.element.GuiElement;
import com.testmile.daksha.tpi.guiauto.element.GuiMultiElement;
import com.testmile.daksha.tpi.guiauto.maker.GuiAutomatorFactory;
import com.testmile.daksha.tpi.guiauto.maker.GuiNamespaceLoaderFactory;
import com.testmile.daksha.tpi.guiauto.maker.selenium.SeleniumBuilder;
import com.testmile.daksha.tpi.guiauto.namestore.GuiNamespaceLoader;
import com.testmile.daksha.tpi.testng.TestNGBaseTest;

public class WebTestWithSeleniumAutomator extends TestNGBaseTest{
	private ThreadLocal<GuiAutomatorProxy> threadWiseAutomator = new ThreadLocal<GuiAutomatorProxy>();
	private ThreadLocal<GuiNamespace> threadWiseNamespace = new ThreadLocal<GuiNamespace>();
	
	protected void tweakCentralContext(TestContext centralContext)  throws Exception {
		centralContext.setTargetPlatform(OSType.MAC);
	}
	
	protected void setUpClass(TestContext testContext) throws Exception {
		SeleniumBuilder builder = GuiAutomatorFactory.getSeleniumBuilder(testContext);
		this.threadWiseAutomator.set(builder.build());
		GuiNamespaceLoader loader = GuiNamespaceLoaderFactory.createNamespaceLoader(testContext, "simpleapp/wordpress.gns");
		loader.load();
		this.threadWiseNamespace.set(loader.getNamespace());
	}
	
	private GuiElementMetaData getEData(String name) throws Exception {
		return this.threadWiseNamespace.get().getMetaData(name, this.threadWiseAutomator.get().getAutomatorContext());
	}
	
	@Test
	public void test() throws Exception{
		GuiAutomatorProxy automator = this.threadWiseAutomator.get();
		
		automator.goTo(this.getContext().getValue("wp.admin.url").asString());	

		GuiElement userTextBox = automator.element(getEData("LOGIN"));
		userTextBox.waitUntilPresent();
		userTextBox.enterText(this.getContext().getValue("wp.username").asString());
		automator.element(getEData("PASSWORD")).enterText(this.getContext().getValue("wp.password").asString());
		automator.element(getEData("SUBMIT")).click();		
		automator.waitForBody();
		
		automator.element(getEData("POSTS")).hover();
		automator.element(getEData("CATEGORIES")).click();	

		automator.waitForBody();
		GuiMultiElement tags = automator.elements(getEData("CAT_CHECKBOXES"));
		tags.getInstanceAtOrdinal(2).check();
		tags.getInstanceAtIndex(1).uncheck();
			
		for (GuiElement element: tags.getAllInstances()){
			element.check();
			element.uncheck();
		}

		automator.element(getEData("SETTINGS")).click();
			
		GuiElement blogNameTextBox = automator.element(getEData("BLOG_NAME"));
		blogNameTextBox.enterText("Hello");
		blogNameTextBox.enterText("Hello");
		blogNameTextBox.setText("Hello");
		
		automator.element(getEData("MEMBERSHIP")).check();

		// Experiments with Select control - Selection using different attributes
		GuiElement roleDropDown = automator.element(getEData("ROLE")).asDropDown();
		roleDropDown.selectText("Author");
		assertTrue(roleDropDown.hasSelectedText("Author"), "Check Author Role Selected");
		roleDropDown.selectAtIndex(0);
		assertTrue(roleDropDown.hasSelectedIndex(0), "Check Author Role Selected");
		roleDropDown.selectValue("author");
		assertTrue(roleDropDown.hasSelectedValue("author"), "Check Author Role Selected");
		
		automator.goTo(this.getContext().getValue("wp.logout.url").asString());
	}
	
	public void tearDownClass(TestContext testContext) throws Exception {
		this.threadWiseAutomator.get().close();
	}
}
