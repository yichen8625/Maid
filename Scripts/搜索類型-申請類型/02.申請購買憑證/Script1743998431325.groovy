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

'申請購買憑證'

// 發送申請廳主買域名請求
def response = WS.sendRequest(findTestObject('Object Repository/API/申請買憑證/申請購買與部屬憑證'))
WS.verifyResponseStatusCode(response, 201) // 檢查 HTTP 狀態碼是否為 201

def responseText = response.getResponseText()
def jsonResponse = new groovy.json.JsonSlurper().parseText(responseText)
def workflowId = jsonResponse.workflow_id
testCaseVariables.workflow_id = workflowId


WebUI.navigateToUrl(GlobalVariable.G_URL)

WebUI.maximizeWindow()

WebUI.waitForPageLoad(2)

'申請憑證 filter'
WebUI.click(findTestObject('Object Repository/Home/span_item'))

WebUI.click(findTestObject('Object Repository/Home/Search Type/div_申請類型'))

WebUI.click(findTestObject('Object Repository/Home/申請類型/input_申請類型'))

WebUI.click(findTestObject('Object Repository/Home/申請類型/List/input_申請憑證'))

WebUI.click(findTestObject('Object Repository/Home/btn_search'))

WebUI.delay(5)

WebUI.verifyElementText(findTestObject('Object Repository/Home/td_申請類型'), '申請憑證')
