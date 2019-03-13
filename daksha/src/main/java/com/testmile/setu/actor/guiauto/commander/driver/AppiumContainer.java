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

package com.testmile.setu.actor.guiauto.commander.driver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.testmile.setu.actor.guiauto.adapter.GuiAutomatorBuilder;
import com.testmile.setu.actor.guiauto.adapter.driver.SetuDriverConfig;
import com.testmile.setu.actor.guiauto.adapter.driver.SetuGuiAutoActorOption;
import com.testmile.setu.actor.guiauto.core.GuiMultiElement;
import com.testmile.trishanku.Trishanku;
import com.testmile.trishanku.tpi.value.Value;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class AppiumContainer extends DriverContainer<AppiumDriver<MobileElement>,MobileElement>{

	protected AppiumContainer(AppiumDriver<MobileElement> tool, SetuDriverConfig config) throws Exception {
		super(tool, config);
	}
	
	public WebDriver asWebDriver() {
		return this.getTool();
	}
	
	public static AppiumContainer container(SetuDriverConfig config) throws Exception {
		AppiumBuilder builder = new AppiumBuilder(config);
		AppiumDriver<MobileElement> driver = builder.build();
		return new AppiumContainer(driver, config);
	}
	
	public AppiumDriver<MobileElement> asAppiumDriver() throws Exception{
		return this.getTool();
	}
	
	@Override
	public GuiMultiElement<AppiumDriver<MobileElement>, MobileElement> findElements(String by, String value) throws Exception {
		 List<MobileElement> elements = this.getTool().findElements(DriverElementContainer.getAppiumByLocator(by, value));
		 return DriverElementContainer.appiumMultiElement(this.getTool(), elements, this.getConfig());
	}
	
	@Override
	public DriverElementContainer<AppiumDriver<MobileElement>, MobileElement> findElement(String by, String value) throws Exception {
		MobileElement element = this.getTool().findElement(DriverElementContainer.getAppiumByLocator(by, value));
		 return DriverElementContainer.appiumElement(this.getTool(), element, this.getConfig());
	}

}

class AppiumBuilder extends GuiAutomatorBuilder{
	private static AppiumDriverServerLauncher appiumServerLauncher = new AppiumDriverServerLauncher();
	private MutableCapabilities capabilities = null;
	
	public AppiumBuilder(SetuDriverConfig config) throws Exception{
		super(config);
		createCapabilities();
	}
		
	private void createCapabilities() throws Exception {
		for(String cap: getConfig().getDriverCapabilities().keySet()) {
			capabilities.setCapability(cap, getConfig().getDriverCapabilities().get(cap));
		}
		
		Proxy proxy = getConfig().getProxy();
		if (proxy != null) {
			capabilities.setCapability("proxy", proxy);
		}
	}
		
	public AppiumDriver<MobileElement> build() throws Exception{
		AppiumDriver<MobileElement> driver = null;
		AppiumServer server = null;
		if (getConfig().value(SetuGuiAutoActorOption.APPIUM_AUTO_LAUNCH).asBoolean() == true) {
			server = appiumServerLauncher.startServer();
		} else {
			server = new AppiumServer(getConfig().value(SetuGuiAutoActorOption.APPIUM_HUB_URL).asString());
		}
		URL hubUrl = server.getURL();
		try{
			switch(this.getConfig().getMobileOSName()){
			case Android: driver = new AndroidDriver<MobileElement>(hubUrl, capabilities); break;
			case iOS: driver = new IOSDriver<MobileElement>(hubUrl, capabilities); break;
			}
		} catch (UnreachableBrowserException e){
			throw new Exception("Unreachable Appium device: " + this.getConfig().getMobileOSName() + e);
		}
		
		return driver;
	}
}


class AppiumServer {
	private int port;
	private URL hubUrl = null;
	
	public AppiumServer(int port) throws MalformedURLException {
		this.port = port;
		hubUrl = new URL(String.format("http://127.0.0.1:%d/wd/hub", this.port));
	}

	public AppiumServer(String url) throws Exception {
		this.hubUrl = new URL(url);
		port = this.hubUrl.getPort();
	}

	public int getPort() {
		return this.port;
	}
	
	public URL getURL() {
		return this.hubUrl;
	}

}


/*
 * The following code is a modified version of Roland Castelino's example here:
 * https://www.linkedin.com/pulse/start-stop-appium-server-programatically-mac-roland-castelino/
 */

class AppiumDriverServerLauncher {
	private Map<Integer, AppiumServer> servers = new HashMap<Integer, AppiumServer>();
	private Logger logger = null;
	private int lastPort = 4722;
	private String APPIUM_COMMAND = "appium";
	
	public AppiumDriverServerLauncher() {
		logger = Trishanku.getLogger();
	}

	public AppiumServer startServer() throws Exception {
		lastPort += 1;
		startAppiumServer(lastPort);
		return new AppiumServer(lastPort);
	}

	 public void stopServer(AppiumServer server) throws Exception {
	 	stopAppiumServer(server.getPort());
	 	servers.remove(server.getPort());
    }

    private void startAppiumServer(int port) throws Exception {
        logger.debug(String.format("Starting Appium server on port %s", port));

        // Check if Appium server already up and running
        if (!isAppiumServerRunning(port)) {
            // command to start Appium server --> appium -p 4273
            String completeAppiumCommand = String.format("%s -p %s", APPIUM_COMMAND, port);
            logger.debug("Starting Server");
            try {
                logger.debug("Appium server started with version: " + runCMD(completeAppiumCommand));
            } catch (Exception serverNotStarted) {
                logger.warn("Could not start Appium Server");
                throw new Exception(serverNotStarted.getMessage());
            }
        } else {
            logger.debug("Appium server already started");
        }
    }


    /**
     * To check if Appium server is already up and running on the desired port
     * @param port desired port for server to start
     * @return true if server running, else false.
     * @throws Exception
     */
    private boolean isAppiumServerRunning(int port) throws Exception {
        logger.debug(String.format("Checking if Appium server is executing on port %s", port));

        // command to check if Appium service running on port --> sh -c lsof -P | grep ':4723'
        String checkCommand[] = new String[]{"sh","-c", String.format("lsof -P | grep ':%s'", port)};
        if (runCommandAndWaitToComplete(checkCommand).equals("")) {
            logger.warn(String.format("Appium server is not running on port %s", port));
            return false;
        } else {
            logger.debug(String.format("Appium server is running on port %s", port));
            return true;
        }
    }

    /**
     * To stop appium server
     * @param port desired port for server to stop
     * @throws Exception
     */
    private void stopAppiumServer(int port) throws Exception {
        logger.debug(String.format("Stopping Appium server on port %s", port));

        // command to stop Appium service running on port --> sh -c lsof -P | grep ':4723' | awk '{print $2}' | xargs kill -9
        String stopCommand[] = new String[]{String.format("netstat -anp tcp | grep %s | awk '{print $2}' | xargs kill -9", port)};
        runCommandAndWaitToComplete(stopCommand);
    }

    /**
     * To execute a terminal command, and get the complete log response.
     *
     * @param command - command we intend to execute via terminal
     * @return - the execution log. We can scan this to check if the command executed was a success or failure.
     * @throws Exception
     */
    public String runCommandAndWaitToComplete(String[] command) throws Exception {
        String completeCommand = String.join(" ", command);
        logger.debug("Executing command: " + completeCommand);
        String line;
        String returnValue = "";

        try {
            Process processCommand = Runtime.getRuntime().exec(command);
            BufferedReader response = new BufferedReader(new InputStreamReader(processCommand.getInputStream()));

            try {
                processCommand.waitFor();
            } catch (InterruptedException commandInterrupted) {
                throw new Exception("Were waiting for process to end but something interrupted it" + commandInterrupted.getMessage());
            }

            while ((line = response.readLine()) != null) {
                returnValue = returnValue + line + "\n";
            }

            response.close();

        } catch (Exception e) {
            throw new Exception("Unable to run command: " + completeCommand + ". Error: " + e.getMessage());
        }

        logger.debug("Response : runCMDAndWaitToComplete(" + completeCommand + ") : " + returnValue);
        return returnValue;
    }

    /**
     * Helper method to run an arbitrary command-line 'command', waits for few seconds after command executes
     * @param command string that will be sent to command-line
     * @return The first line response after executing command. (can be used to verify)
     */
    public String runCMD(String command) throws Exception {
        logger.debug("Executing command: " + command);
        try {
            Process process = Runtime.getRuntime().exec((command));
            process.waitFor(10, TimeUnit.SECONDS);
            BufferedReader response = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return response.readLine();
        } catch (Exception e) {
            logger.warn("Unable to run command: " + command + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

}

