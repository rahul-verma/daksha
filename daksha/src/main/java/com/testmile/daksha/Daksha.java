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
package com.testmile.daksha;

import java.util.List;

import org.apache.log4j.Logger;
import org.testng.ITestContext;

import com.testmile.daksha.core.batteries.config.Configuration;
import com.testmile.daksha.core.guiauto.GuiAutoSingleton;
import com.testmile.daksha.core.guiauto.enums.OSType;
import com.testmile.daksha.tpi.TestContext;
import com.testmile.daksha.tpi.enums.Browser;
import com.testmile.daksha.tpi.guiauto.enums.GuiAutomationContext;

public class Daksha {
	private static DakshaSingleton core = DakshaSingleton.INSTANCE;
	private static GuiAutoSingleton guiAuto = GuiAutoSingleton.INSTANCE;
	
	public static TestContext init(String rootDir) throws Exception{
		return core.init(rootDir);
	}
	
	public static TestContext init() throws Exception {
		return init(System.getProperty("user.dir"));
	}
	
	public static TestContext getCentralContext() throws Exception {
		return core.getCentralContext();		
	}
	
	public static void registerContext(TestContext context) throws Exception {
		core.registerContext(context);
	}
	
	public static String getRootDir() {
		return core.getRootDir();
	}
		
	public static TestContext getTestContext(String name) throws Exception {
		return core.getTestContext(name);
	}
	
	public static TestContext getTestContext(ITestContext context) throws Exception {
		return core.getTestContext(context);
	}
	
	public static Configuration getTestContextConfig(String name) throws Exception {
		return core.getTestContextConfig(name);
	}
	
	public static Configuration getTestContextConfig(ITestContext context) throws Exception {
		return core.getTestContextConfig(context);
	}
	 
	public static Logger getLogger() { 
		return core.getLogger();
	}
	
	public static String getAutomationContextName(GuiAutomationContext context) {
		return guiAuto.getAutomationContextName(context);
	}

	public static List<String> getAllowedPickByStrings() throws Exception {
		return guiAuto.getAllowedPickByStrings() ;
	}

	public static List<String> getAllAllowedGuiElementTypes() {
		return guiAuto.getAllAllowedGuiElementTypes();
	}
	
	public static boolean isAllowedAppiumPlatform(String platformName){
		return guiAuto.isAllowedAppiumPlatform(platformName);
	}
	
	public static String getAppiumPlatformString(OSType platform) throws Exception{
		return guiAuto.getAppiumPlatformString(platform);
	}
	
	public static String getAppiumBrowserString(String rawName) throws Exception{
		return guiAuto.getAppiumBrowserString(rawName);
	}

	public static boolean isAllowedAppiumBrowser(OSType platform, String browser) throws Exception {
		return guiAuto.isAllowedAppiumBrowser(platform, browser);
	}
	
	public static String getDriverName(Browser browser) throws Exception {
		return guiAuto.getDriverName(browser);
	}
	
	public static boolean isDriverPathNeeded(Browser browser) throws Exception {
		return guiAuto.isSeleniumDriverPathNeeded(browser);
	}
	
	public static String getSeleniumDriverPathSystemProperty(Browser browser) throws Exception {
		return guiAuto.getSeleniumDriverPathSystemProperty(browser);
	}

	public static TestContext createTestContext(String name) throws Exception {
		return core.createTestContext(name);
	}
	
}
