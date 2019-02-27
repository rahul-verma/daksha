package daksh.exnew;

import setuexp.DefaultGuiAutomator;
import setuexp.DropDown;
import setuexp.GuiAutomator;
import setuexp.RadioGroup;
import setuexp.With;

public class Ex6RadioGroup {

	public static void main(String[] args) throws Exception {
		GuiAutomator automator = new DefaultGuiAutomator();
		WPLoginLogout.login(automator);
		
		automator.element(With.LINK_TEXT,"Settings").click();
		RadioGroup dateFormat = automator.radioGroup(With.NAME, "date_format");
		System.out.println(dateFormat.hasValueSelected("Y-m-d"));
		System.out.println(dateFormat.hasIndexSelected(1));
		System.out.println(dateFormat.getFirstSelectedOptionValue());
		dateFormat.selectByValue("\\c\\u\\s\\t\\o\\m");
		dateFormat.selectByIndex(2);
		
		WPLoginLogout.logout(automator);
	}

}
