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
import org.openqa.selenium.chrome.ChromeOptions
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.chrome.ChromeDriver


WebUI.openBrowser('')

WebUI.navigateToUrl(GlobalVariable.G_VIR_URL)

WebUI.waitForPageLoad(2)

WebUI.click(findTestObject('Object Repository/Verification page/div_google_sigin'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Object Repository/Verification page/input_email'), account, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Verification page/span_next'), FailureHandling.CONTINUE_ON_FAILURE)

//HENNGE login
WebUI.waitForPageLoad(2)

WebUI.setText(findTestObject('Object Repository/Verification page/HENNGE/input_user'), hennge_user, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setEncryptedText(findTestObject('Object Repository/Verification page/HENNGE/input_pwd'), hennge_pwd, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Verification page/HENNGE/input_login'))

// secretkey 
String secretKey = 'JVMXSSLPMNWEGWBZOJAU6MKLGY' 

// 打印 TOTP
String totp = generateTOTP(secretKey)

println('生成的 TOTP: ' + totp)

WebUI.setText(findTestObject('Object Repository/Verification page/HENNGE/input_totp'), totp, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Verification page/HENNGE/input_submit'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Verification page/btn_keep'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Verification page/btn_keep_ll'), FailureHandling.CONTINUE_ON_FAILURE)

def generateTOTP(String secretKey) {
    try {
        Base32 base32 = new Base32()

        byte[] decodedKey = base32.decode(secretKey)

        long time = System.currentTimeMillis() / 1000

        long timeWindow = time / 30

        byte[] timeBytes = new byte[8]

        for (int i = 7; i >= 0; i--) {
            (timeBytes[i]) = ((timeWindow & 255) as byte)

            timeWindow >>= 8
        }
        
        Mac mac = Mac.getInstance('HmacSHA1')

        SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, 'HmacSHA1')

        mac.init(secretKeySpec)

        byte[] hash = mac.doFinal(timeBytes)

        println('哈希值: ' + Hex.encodeHexString(hash))

        int offset = (hash[19]) & 15

        int otp = ((hash[offset]) & 127) << 24

        otp |= (((hash[(offset + 1)]) & 255) << 16)

        otp |= (((hash[(offset + 2)]) & 255) << 8)

        otp |= ((hash[(offset + 3)]) & 255)

        otp = (otp % 1000000)

        return String.format('%06d', otp)
    }
    catch (Exception e) {
        e.printStackTrace()
    } 
    
    return null
}


