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
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator
import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.SecureRandom
import java.nio.charset.StandardCharsets
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.binary.Base32

WebUI.openBrowser('')

WebUI.navigateToUrl(GlobalVariable.G_URL)

WebUI.waitForPageLoad(2)

WebUI.click(findTestObject('Object Repository/Verification page/div_google_sigin'))

WebUI.setText(findTestObject('Object Repository/Verification page/input_email'), account)

WebUI.click(findTestObject('Object Repository/Verification page/span_next'))

//HENNGE login
WebUI.waitForPageLoad(2)

WebUI.setText(findTestObject('Object Repository/Verification page/HENNGE/input_user'), hennge_user)

WebUI.setEncryptedText(findTestObject('Object Repository/Verification page/HENNGE/input_pwd'), hennge_pwd)

WebUI.click(findTestObject('Object Repository/Verification page/HENNGE/input_login'))

def generateTOTP(String secretKey) {
    try {
        // Base32 解码密钥
        Base32 base32 = new Base32()
        byte[] decodedKey = base32.decode(secretKey)
        
        // 获取当前时间戳（单位：秒）
        long time = System.currentTimeMillis() / 1000L
        long timeWindow = time / 30L  // 每 30 秒一个时间窗口
        
        // 将时间窗口转换为 8 字节数组
        byte[] timeBytes = new byte[8]
        for (int i = 7; i >= 0; i--) {
            timeBytes[i] = (byte) (timeWindow & 0xFF)
            timeWindow >>= 8
        }
        
        // 使用 HMAC-SHA1 算法计算哈希
        Mac mac = Mac.getInstance("HmacSHA1")
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, "HmacSHA1")
        mac.init(secretKeySpec)
        byte[] hash = mac.doFinal(timeBytes)
        
        // 输出哈希值（可选，用于调试）
        println("哈希值: " + Hex.encodeHexString(hash))
        
        // 动态截断方法，获取最后的 TOTP
        int offset = hash[19] & 0xF
        int otp = (hash[offset] & 0x7F) << 24
        otp |= (hash[offset + 1] & 0xFF) << 16
        otp |= (hash[offset + 2] & 0xFF) << 8
        otp |= (hash[offset + 3] & 0xFF)
        
        // 获取 TOTP 的最后 6 位数字
        otp = otp % 1000000
        
        return String.format("%06d", otp)  // 补齐 6 位数字
    } catch (Exception e) {
        e.printStackTrace()
    }
    return null
}

// secretkey 
String secretKey = "JVMXSSLPMNWEGWBZOJAU6MKLGY"  // 这应该是你实际的 Base32 密钥

// 打印 TOTP
String totp = generateTOTP(secretKey)
println("生成的 TOTP: " + totp)

WebUI.setText(findTestObject('Object Repository/Verification page/HENNGE/input_totp'), totp)

WebUI.click(findTestObject('Object Repository/Verification page/HENNGE/input_submit'))

WebUI.click(findTestObject('Object Repository/Verification page/btn_keep'))

WebUI.click(findTestObject('Object Repository/Verification page/btn_keep_ll'))





