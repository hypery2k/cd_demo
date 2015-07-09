/**
 * 
 */
package sample.layout;

import org.junit.Test;
import org.openqa.selenium.By;

import sample.util.GalenBaseTest;

/**
 * @author mreinhardt
 *
 */
public class CssLayoutTest extends GalenBaseTest {
    
  public final static String NAV_FORM_BTN = "(//*[contains(@class,'bs-docs-sidenav')]/li/a)[6]";
  
  public final static String INPUT_EMAIL = "//*[contains(@data-example-id,'basic-forms')]//input[contains(@type,'email')]";
	
	/**
	 * @param pTestDevice
	 */
	public CssLayoutTest(TestDevice pTestDevice) {
		super(pTestDevice);
	}

	@Test
	public void shouldShowCorrectBaseLayout() throws Exception {
	  // or use verifyPage("/css","/specs/cssPageLayout.spec");
	  load("/css/#forms");
	  clickElement(By.xpath(NAV_FORM_BTN));
    enterText(By.xpath(INPUT_EMAIL),"invalidEmail");
		verifyPage("/specs/cssPageLayout.spec");
	}

}
