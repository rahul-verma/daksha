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
package com.testmile.daksha.core.guiauto.automator.appium;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testmile.daksha.Daksha;
import com.testmile.daksha.core.guiauto.GuiAutoSingleton;
import com.testmile.daksha.core.guiauto.automator.selenium.BaseSeleniumWebGuiDriver;
import com.testmile.daksha.core.guiauto.enums.Direction;
import com.testmile.daksha.core.guiauto.enums.GuiDriverEngine;
import com.testmile.daksha.core.guiauto.enums.GuiElementLoaderType;
import com.testmile.daksha.core.guiauto.enums.MobileView;
import com.testmile.daksha.core.guiauto.enums.OSType;
import com.testmile.daksha.core.guiauto.identifier.appium.AppiumElementIdentifier;
import com.testmile.daksha.core.guiauto.launcher.appium.AppiumServer;
import com.testmile.daksha.core.problem.ErrorType;
import com.testmile.daksha.core.problem.Problem;
import com.testmile.daksha.tpi.TestContext;
import com.testmile.daksha.tpi.enums.DakshaOption;
import com.testmile.daksha.tpi.guiauto.enums.GuiAutomationContext;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BaseAppiumGuiDriver extends BaseSeleniumWebGuiDriver<AppiumDriver<MobileElement>,MobileElement>{
	private String appPath = null;
	private float swipeTop;
	private float swipeBottom;
	private int swipeMaxWait;
	
	public BaseAppiumGuiDriver(TestContext testContext, GuiElementLoaderType loaderType) throws Exception{
		super(testContext, GuiDriverEngine.APPIUM, loaderType);
		swipeTop = testContext.getConfig().value(DakshaOption.GUIAUTO_SWIPE_TOP).asFloat();
		swipeBottom = testContext.getConfig().value(DakshaOption.GUIAUTO_SWIPE_BOTTOM).asFloat();
		swipeMaxWait = testContext.getConfig().value(DakshaOption.GUIAUTO_SWIPE_MAX_WAIT).asInt();
	}
		
	public BaseAppiumGuiDriver(TestContext testContext) throws Exception{
		this(testContext, GuiElementLoaderType.AUTOMATOR);
	}
	
	@Override
	public void load() throws Exception{
		AppiumDriver<MobileElement> driver = null;
		AppiumServer server = null;
		if (this.getTestContext().getConfig().value(DakshaOption.APPIUM_AUTO_LAUNCH).asBoolean() == true) {
			server = GuiAutoSingleton.INSTANCE.getDriverServerLauncher().startServer();
		} else {
			server = new AppiumServer(this.getTestContext().getConfig().value(DakshaOption.APPIUM_HUB_URL).asString());
		}
		URL hubUrl = server.getURL();
		try{
			switch(getOSType()){
			case ANDROID: driver = new AndroidDriver<MobileElement>(hubUrl, capabilities); break;
			case IOS: driver = new IOSDriver<MobileElement>(hubUrl, capabilities); break;
			}
	
		}catch (UnreachableBrowserException e){
			throwUnreachableBrowserException(getOSType(), e);
		}
		this.setDriver(driver);
		this.setWaiter(new WebDriverWait(this.getUnderlyingEngine(), this.getWaitTime()));	
		this.setIdentifier(new AppiumElementIdentifier(this));
	}
	
	private void throwUnreachableBrowserException(OSType platformType, Throwable e) throws Exception {
		throw new Problem(
				"Automator",
				this.getName(),
				"Constructor",
				ErrorType.APPIUM_UNREACHABLE_BROWSER,
				"Unreachable Appium Browser for " + Daksha.getAppiumPlatformString(platformType),
				e
				);
	}
	
	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}
	
	protected AppiumDriver<MobileElement> getUnderlyingEngine() {
		return (AppiumDriver<MobileElement>) getGuiDriverEngine();
	}
	
	protected boolean isWebView() {
		return getUnderlyingEngine().getContext().contains(MobileView.WEBVIEW.toString());
	}

	protected boolean isNativeView() {
		return getUnderlyingEngine().getContext().contains(MobileView.NATIVE_APP.toString());
	}

	private void validateSwipeSupport() throws Exception {
		if ((GuiAutomationContext.isMobileWebContext(this.getAutomatorContext())) || isWebView()){
			throw new Exception("Swipe actions are not supported for Web View.");
		}
	}

	protected void validateScrollSupport() throws Exception {
		if ((GuiAutomationContext.isMobileNativeContext(this.getAutomatorContext())) || isNativeView()){
			throw new Exception("Scroll actions are not supported for Native View.");
		}
	}
	
	public String swipeUsingADB(int startx, int starty, int endx, int endy, int duration) {
        return executeAsString("adb shell input touchscreen swipe "+startx+" "+starty+" "+endx+" "+endy+" "+duration);
    }

    private String executeAsString(String command) {
        try {
            Process pr = execute(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = input.readLine()) != null) {
                if (!line.isEmpty()) {
                    sb.append(line);
                }
            }
            input.close();
            pr.destroy();
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Execution error while executing command" + command, e);
        }
    }  
    
    private Process execute(String command) throws Exception {
        List<String> commandP = new ArrayList<String>();
        String[] com = command.split(" ");
        for (int i = 0; i < com.length; i++) {
            commandP.add(com[i]);
        }
        ProcessBuilder prb = new ProcessBuilder(commandP);
        Process pr = prb.start();
        pr.waitFor(10, TimeUnit.SECONDS);
        return pr;
    }

	
	private void swipe(Direction direction, int count, float startFraction, float endFraction) throws Exception {
		validateSwipeSupport();
		JavascriptExecutor js = (JavascriptExecutor) this.getUnderlyingEngine();
		Dimension size = this.getUnderlyingEngine().manage().window().getSize();
		int x1 = (int) (0.5 * size.width);
		int y1 = (int) (size.height * startFraction);
		int x2 = (int) (0.5 * size.width);
		int y2 = (int) (size.height * endFraction);

		if (this.getOSType() == OSType.ANDROID) {
			new AndroidTouchAction((AndroidDriver<MobileElement>)this.getUnderlyingEngine())
			.press(PointOption.point(x1,  y1))
			.waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeMaxWait)))
			.moveTo(PointOption.point(x2, y2)).release().perform();
		} else {
			Map<String, Double> swipeElement = new HashMap<String, Double>();
			swipeElement.put("startX", (double) x1);
			swipeElement.put("startY", (double) y1);
			swipeElement.put("endX", (double) x2);
			swipeElement.put("endY", (double) y2);
			swipeElement.put("duration", (double) swipeMaxWait);
			js.executeScript("mobile: swipe", swipeElement);
		}
		
	}

	@Override
	public void swipeUp(int count) throws Exception {
		swipe(Direction.UP, count, swipeBottom, swipeTop);
	}

	@Override
	public void swipeUp() throws Exception {
		swipeUp(1);
	}

	@Override
	public void swipeDown(int count) throws Exception {
		swipe(Direction.DOWN, count, swipeTop, swipeBottom);
	}

	@Override
	public void swipeDown() throws Exception {
		swipeDown(1);
	}
	
	private void NOTEXPOSED_startService() {
		//		AppiumDriverLocalService service;
		//		String nodeLoc ="/Applications/Appium.app/Contents/Resources/node/bin/node";
		//		String appiumLoc ="/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js";
		//		service = AppiumDriverLocalService
		//			.buildService
		//			(
		//			new AppiumServiceBuilder()
		//				.usingDriverExecutable(new File(nodeLoc))
		//				.withAppiumJS(new File(appiumLoc))
		//			);
		//		service.start();
	}
	
	
}
