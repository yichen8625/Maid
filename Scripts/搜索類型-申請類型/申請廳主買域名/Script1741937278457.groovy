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
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import groovy.json.JsonSlurper
import com.kms.katalon.core.util.KeywordUtil

'申請廳主買域名'

// 產生隨機數字並存入 GlobalVariable
int randomNum = new Random().nextInt(10000)  // 產生一個 0 到 9999 之間的隨機數字
GlobalVariable.RANDOMNUM = randomNum

// 發送申請廳主買域名請求
def response = WS.sendRequest(findTestObject('Object Repository/API/Postman/申請廳主買域名'))
WS.verifyResponseStatusCode(response, 201) // 檢查 HTTP 狀態碼是否為 201

// 提取申請廳主買域名請求 workflow_id
def jsonResponse = new JsonSlurper().parseText(response.getResponseText())
def workflow_id = jsonResponse.workflow_id
GlobalVariable.WORKFLOW_ID = workflow_id

// 發送取得自動化申請詳細資料請求
response = WS.sendRequest(findTestObject('Object Repository/API/Postman/取得自動化申請詳細資料'))
WS.verifyResponseStatusCode(response, 200) // 檢查 HTTP 狀態碼是否為 200

// 解析自動化工作項目資料 response
jsonResponse = new JsonSlurper().parseText(response.getResponseText())
def domain = jsonResponse.domain
GlobalVariable.DOMAIN = domain

// 發送取得自動化工作項目資料請求
response = WS.sendRequest(findTestObject('Object Repository/API/Postman/取得自動化工作項目資料'))
WS.verifyResponseStatusCode(response, 200) // 檢查 HTTP 狀態碼是否為 200

// 解析自動化工作項目資料 response
jsonResponse = new JsonSlurper().parseText(response.getResponseText())

// 定義需要檢查的欄位
def requiredFields = [
    "CheckDomainBlocked",
    "VerifyTLD",
    "UpdateNameServer",
    "UpdateDomainRecord",
    "MergeErrorRecord",
    "RecheckDomainResolution",
    "RemoveTag",
    "PurchaseAndDeployCert",
    "CheckPurchaseDeployCertificateStatus",
    "RecheckCert"
]

// 檢查每個欄位是否存在且不為 null 或 空
requiredFields.each { field ->
    if (jsonResponse?."${field}" == null) {
        KeywordUtil.markFailed("Missing or null field: ${field}")
    } else {
        println "Field ${field} exists and has value: ${jsonResponse."${field}"}"
    }
}

// 將每個 job_id GlobalVariable 中
GlobalVariable.CheckDomainBlocked_job_id = jsonResponse.CheckDomainBlocked
GlobalVariable.VerifyTLD_job_id = jsonResponse.VerifyTLD
GlobalVariable.UpdateNameServer_job_id = jsonResponse.UpdateNameServer
GlobalVariable.UpdateDomainRecord_job_id = jsonResponse.UpdateDomainRecord
GlobalVariable.MergeErrorRecord_job_id = jsonResponse.MergeErrorRecord
GlobalVariable.RecheckDomainResolution_job_id = jsonResponse.RecheckDomainResolution
GlobalVariable.RemoveTag_job_id = jsonResponse.RemoveTag
GlobalVariable.PurchaseAndDeployCert_job_id = jsonResponse.PurchaseAndDeployCert
GlobalVariable.CheckPurchaseDeployCertificateStatus_job_id = jsonResponse.CheckPurchaseDeployCertificateStatus
GlobalVariable.RecheckCert_job_id = jsonResponse.RecheckCert

println "CheckDomainBlocked_job_id: ${GlobalVariable.CheckDomainBlocked_job_id}"
println "VerifyTLD_job_id: ${GlobalVariable.VerifyTLD_job_id}"
println "UpdateNameServer_job_id: ${GlobalVariable.UpdateNameServer_job_id}"
println "UpdateDomainRecord_job_id: ${GlobalVariable.UpdateDomainRecord_job_id}"
println "MergeErrorRecord_job_id: ${GlobalVariable.MergeErrorRecord_job_id}"
println "RecheckDomainResolution_job_id: ${GlobalVariable.RecheckDomainResolution_job_id}"
println "RemoveTag_job_id: ${GlobalVariable.RemoveTag_job_id}"
println "PurchaseAndDeployCert_job_id: ${GlobalVariable.PurchaseAndDeployCert_job_id}"
println "CheckPurchaseDeployCertificateStatus_job_id: ${GlobalVariable.CheckPurchaseDeployCertificateStatus_job_id}"
println "RecheckCert_job_id: ${GlobalVariable.RecheckCert_job_id}"


'申請廳主買域名 filter'

WebUI.navigateToUrl(GlobalVariable.G_URL)

WebUI.maximizeWindow()

WebUI.waitForPageLoad(2)

WebUI.click(findTestObject('Object Repository/Home/span_item'))

WebUI.click(findTestObject('Object Repository/Home/Search Type/div_申請類型'))

WebUI.click(findTestObject('Object Repository/Home/申請類型/input_申請類型'))

WebUI.click(findTestObject('Object Repository/Home/申請類型/List/input_申請廳主買域名'))

WebUI.click(findTestObject('Object Repository/Home/申請狀態/input_申請狀態'))

WebUI.click(findTestObject('Object Repository/Home/申請狀態/List/span_全選'))

WebUI.click(findTestObject('Object Repository/Home/btn_search'))

WebUI.delay(5)

WebUI.click(findTestObject('Object Repository/Home/a_ListPage'))

WebUI.verifyElementText(findTestObject('Object Repository/Home/td_申請類型'), '申請廳主買域名')

WebUI.verifyTextPresent(GlobalVariable.WORKFLOW_ID, false)

WebUI.verifyTextPresent(GlobalVariable.DOMAIN, false)
