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

WebUI.navigateToUrl(GlobalVariable.G_URL)

WebUI.maximizeWindow()

WebUI.waitForPageLoad(2)

'申請廳主買域名 filter'
WebUI.click(findTestObject('Object Repository/Home/span_item'))

WebUI.click(findTestObject('Object Repository/Home/Search Type/div_申請類型'))

WebUI.click(findTestObject('Object Repository/Home/申請類型/input_申請類型'))

WebUI.click(findTestObject('Object Repository/Home/申請類型/List/input_申請廳主買域名'))

WebUI.click(findTestObject('Object Repository/Home/btn_search'))

WebUI.delay(5)

WebUI.verifyElementText(findTestObject('Object Repository/Home/td_申請類型'), '申請廳主買域名')

// 控端，廳主申請買域名

def randomFourDigitInt = (int)(Math.random() * 10000); // 生成 0 到 9999 的整數
def formattedNumber = String.format("%04d", randomFourDigitInt); // 格式化為四位數（補零）
def newName = "test" + formattedNumber; // 拼接字符串
println("亂數："+ newName)

def value1 = 'exampleValue1'
	test1 = 'chunkForPumb'
def value2 = 'exampleValue2'
def valueN = 'exampleValueN'

def response = WS.sendRequest(findTestObject('Object Repository/API/Postman/申請廳主買域名', [
	'variable1': value1,
	'variable2': value2,
	'variableN': valueN
]))

// 進一步處理響應
WS.verifyResponseStatusCode(response, 200)
