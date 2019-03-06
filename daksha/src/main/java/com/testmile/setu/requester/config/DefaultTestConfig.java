package com.testmile.setu.requester.config;

import com.testmile.daksha.DakshaSingleton;
import com.testmile.setu.requester.connector.BaseSetuObject;
import com.testmile.setu.requester.connector.SetuArg;
import com.testmile.setu.requester.connector.SetuResponse;
import com.testmile.setu.requester.testsession.TestSession;
import com.testmile.trishanku.tpi.enums.SetuOption;
import com.testmile.trishanku.tpi.value.Value;

public class DefaultTestConfig extends BaseSetuObject implements TestConfig {
	private String name;
	private TestSession session;

	public DefaultTestConfig(TestSession testSession, String name, String setuId) {
		this.session = testSession;
		this.name = name;
		
		this.setSetuId(setuId);
		this.setSelfSetuIdArg("configSetuId");
		this.setTestSessionSetuIdArg(testSession.getSetuId());
	}

	@Override
	public TestSession getTestSession() {
		return this.session;
	}
	
	private Value fetchConfOptionValue(SetuActionType actionType, String option) throws Exception {
		SetuResponse response = this.sendRequest(
				actionType,
				SetuArg.arg("option", option)
		);
		return response.getValue();	
	}
	
	public Value getSetuOptionValue(String option) throws Exception{
		return this.fetchConfOptionValue(
				SetuActionType.CONFIGURATOR_GET_SETU_OPTION_VALUE,
				DakshaSingleton.INSTANCE.normalizeSetuOption(option).toString()
		);
	}	
	
	public Value getSetuOptionValue(SetuOption option) throws Exception{
		return this.fetchConfOptionValue(
				SetuActionType.CONFIGURATOR_GET_SETU_OPTION_VALUE,
				option.toString()
		);
	}
	
	public Value getUserOptionValue(String option) throws Exception{
		return this.fetchConfOptionValue(
				SetuActionType.CONFIGURATOR_GET_USER_OPTION_VALUE,
				DakshaSingleton.INSTANCE.normalizeUserOption(option)
		);
	}

	@Override
	public String getName() {
		return this.name;
	}
	

	
	// For Setu
//	public String getAppDir() throws Exception {
//		GuiAutomationContext aContext = getValue(SetuOption.GUIAUTO_CONTEXT).asEnum(GuiAutomationContext.class);
//		Value testOS = this.getValue(SetuOption.TESTRUN_TARGET_PLATFORM);
//		return getValue(SetuOption.APPS_DIR).asString() 
//				+ File.separator 
//				+ testOS.asString().toLowerCase() + File.separator; 
//	}
	
//	@Override
//	public String getSeleniumDriverPath(Browser browser) throws Exception {
//		Value driverPathValue = getValue(SetuOption.SELENIUM_DRIVER_PATH);
//		if (!driverPathValue.isNotSet()) {
//			return driverPathValue.asString();
//		}
//		
//		// Validate a wrongly made driver path call
////		Trishanku.getSeleniumDriverPathSystemProperty(browser);
//		
//		// Construct and return the path
//		String driversDir = getValue(SetuOption.SELENIUM_DRIVERS_DIR).asString();
//		String os = getValue(SetuOption.TESTRUN_TARGET_PLATFORM).asString().toLowerCase();
////		String binName = modifyExeNameForWindows(Trishanku.getDriverName(browser));
////		String driverPath = driversDir + File.separator + os + File.separator + binName;
//		//return driverPath;
//		return null;
//	}
	
//	private String modifyExeNameForWindows(String name) {
//		if (System.getProperty("os.name").toLowerCase().contains("win")) {
//			return name + ".exe";
//		} else {
//			return name;
//		}
//	}

}
