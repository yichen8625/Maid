import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator as TimeBasedOneTimePasswordGenerator
import java.nio.charset.StandardCharsets as StandardCharsets
import javax.crypto.Mac as Mac
import javax.crypto.spec.SecretKeySpec as SecretKeySpec
import java.security.SecureRandom as SecureRandom
import org.apache.commons.codec.binary.Hex as Hex
import org.apache.commons.codec.binary.Base32 as Base32
import org.openqa.selenium.Cookie
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.util.HashMap
import java.util.Map
import com.kms.katalon.core.webui.driver.DriverFactory



WebUI.openBrowser('')

WebDriver driver = DriverFactory.getWebDriver()


Map<String, String> cookies = new HashMap<>()
cookies.put("PHPSESSID", "f4403c6f199c8d0548773115342911f2")  // 你可以替换成实际的 Cookie 值


cookies.each { name, value ->

    Cookie cookie = new Cookie(name, value, "https://pdns.vir999.com/", "/", null)
    driver.manage().addCookie(cookie)
}

WebUI.navigateToUrl(GlobalVariable.G_PDNS_URL)

WebUI.waitForPageLoad(10)


