package daksha.ex.testng.guiauto.variants.v6.pageTransitions;

import static org.testng.Assert.assertTrue;

import arjuna.tpi.tpi.guiauto.automator.SetuClientGuiAutomator;
import arjuna.tpi.tpi.guiauto.element.SetuClientGuiElement;

public class Settings extends BaseNestedGui{

	public Settings(SetuClientGuiAutomator automator) throws Exception {
		super(automator);
	}
	
	public void playWithSettings() throws Exception{
		SetuClientGuiElement blogNameTextBox = this.Element("BLOG_NAME");
		blogNameTextBox.enterText("Hello");
		blogNameTextBox.enterText("Hello");
		blogNameTextBox.setText("Hello");
		
		this.Element("MEMBERSHIP").check();

		// Experiments with Select control - Selection using different attributes
		SetuClientGuiElement roleDropDown = this.Element("ROLE");
		roleDropDown.selectText("Author");
		assertTrue(roleDropDown.hasSelectedText("Author"), "Check Author Role Selected");
		roleDropDown.selectAtIndex(0);
		assertTrue(roleDropDown.hasSelectedIndex(0), "Check Author Role Selected");
		roleDropDown.selectValue("author");
		assertTrue(roleDropDown.hasSelectedValue("author"), "Check Author Role Selected");		
	}

}
