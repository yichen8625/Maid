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
def response = WS.sendRequest(findTestObject('Object Repository/API/申請廳主買域名/申請廳主買域名'))
WS.verifyResponseStatusCode(response, 201) // 檢查 HTTP 狀態碼是否為 201

// 提取申請廳主買域名請求 workflow_id
def jsonResponse = new JsonSlurper().parseText(response.getResponseText())
def workflow_id = jsonResponse.workflow_id
GlobalVariable.WORKFLOW_ID = workflow_id

// 發送取得自動化申請詳細資料請求
response = WS.sendRequest(findTestObject('Object Repository/API/申請廳主買域名/取得自動化申請詳細資料'))
WS.verifyResponseStatusCode(response, 200) // 檢查 HTTP 狀態碼是否為 200

// 解析自動化工作項目資料 response
jsonResponse = new JsonSlurper().parseText(response.getResponseText())
def domain = jsonResponse.domain
GlobalVariable.DOMAIN = domain // check domain refund random 

// 發送取得自動化工作項目資料請求
response = WS.sendRequest(findTestObject('Object Repository/API/申請廳主買域名/取得自動化工作項目資料'))
WS.verifyResponseStatusCode(response, 200) // 檢查 HTTP 狀態碼是否為 200

// 解析自動化工作項目資料 response
def jsonResponsell = new JsonSlurper().parseText(response.getResponseText())

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
    def job = jsonResponsell.find { it.get('name') == field }
    if (job != null) {
        GlobalVariable."${field}_job_id" = job.get('job_id')
    } else {
        KeywordUtil.markFailed("Missing or null field: ${field}")
    }
}

jsonResponsell.each { job ->
    // 打印 job name 和 job_id
    println "Job name: ${job.name}, Job ID: ${job.job_id}"

    // 確保每個 job_id 被正確存入對應的 GlobalVariable 中
    if (job.name == "CheckDomainBlocked") {
        GlobalVariable.CheckDomainBlocked_job_id = job.job_id
    } else if (job.name == "VerifyTLD") {
        GlobalVariable.VerifyTLD_job_id = job.job_id
    } else if (job.name == "UpdateNameServer") {
        GlobalVariable.UpdateNameServer_job_id = job.job_id
    } else if (job.name == "UpdateDomainRecord") {
        GlobalVariable.UpdateDomainRecord_job_id = job.job_id
    } else if (job.name == "MergeErrorRecord") {
        GlobalVariable.MergeErrorRecord_job_id = job.job_id
    } else if (job.name == "RecheckDomainResolution") {
        GlobalVariable.RecheckDomainResolution_job_id = job.job_id
    } else if (job.name == "RemoveTag") {
        GlobalVariable.RemoveTag_job_id = job.job_id
    } else if (job.name == "PurchaseAndDeployCert") {
        GlobalVariable.PurchaseAndDeployCert_job_id = job.job_id
    } else if (job.name == "CheckPurchaseDeployCertificateStatus") {
        GlobalVariable.CheckPurchaseDeployCertificateStatus_job_id = job.job_id
    } else if (job.name == "RecheckCert") {
        GlobalVariable.RecheckCert_job_id = job.job_id
    }
}


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


'導轉 workflow detail 頁'
WebUI.navigateToUrl(GlobalVariable.G_URL+"/#/auto_detail/"+GlobalVariable.WORKFLOW_ID)

WebUI.maximizeWindow()

WebUI.waitForPageLoad(2)


'申請資訊驗證'
WebUI.verifyTextPresent("申請廳主買域名", true)

WebUI.verifyTextPresent(GlobalVariable.WORKFLOW_ID.toString(), true)
println("申請單號: " + GlobalVariable.WORKFLOW_ID)

WebUI.verifyTextPresent(GlobalVariable.DOMAIN.toString(), true)
println("域名: " + GlobalVariable.DOMAIN)

WebUI.executeJavaScript('var script = document.createElement("script"); script.src = "https://code.jquery.com/jquery-3.6.0.min.js"; document.head.appendChild(script);', [], FailureHandling.CONTINUE_ON_FAILURE)

WebUI.delay(1) 

WebUI.executeJavaScript('var link = document.createElement("link"); link.rel = "stylesheet"; link.href = "https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css"; document.head.appendChild(link);', [], FailureHandling.CONTINUE_ON_FAILURE)
WebUI.executeJavaScript('var script = document.createElement("script"); script.src = "https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"; document.head.appendChild(script);', [], FailureHandling.CONTINUE_ON_FAILURE)

WebUI.delay(1)  

WebUI.executeJavaScript('''
    var style = document.createElement("style");
    style.innerHTML = `
        .toast-center-top {
            top: 10% !important;
            left: 50% !important;
            transform: translate(-50%, -50%) !important;
            position: fixed !important;
            z-index: 9999;
        }
    `;
    document.head.appendChild(style);
''', [], FailureHandling.CONTINUE_ON_FAILURE)

// 设置 toastr 的位置居中
WebUI.executeJavaScript('''
       toastr.options = {
           "positionClass": "toast-center-top",
           "timeOut": "700",
           "closeButton": false, 
           "iconClass": "toast-success",  
           "closeHtml": "<button>&times;</button>"
       };
       toastr.success("申請資訊驗證(申請單號/域名)", "Success", { "positionClass": "toast-center-top" });
   ''', [], FailureHandling.STOP_ON_FAILURE)

WebUI.takeFullPageScreenshot()

WebUI.delay(1)

'異動紀錄頁驗證'
WebUI.click(findTestObject('Object Repository/Workflow Detail/div_record'))

WebUI.executeJavaScript('var script = document.createElement("script"); script.src = "https://code.jquery.com/jquery-3.6.0.min.js"; document.head.appendChild(script);', [], FailureHandling.CONTINUE_ON_FAILURE)

WebUI.delay(1) 

WebUI.executeJavaScript('var link = document.createElement("link"); link.rel = "stylesheet"; link.href = "https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css"; document.head.appendChild(link);', [], FailureHandling.CONTINUE_ON_FAILURE)
WebUI.executeJavaScript('var script = document.createElement("script"); script.src = "https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"; document.head.appendChild(script);', [], FailureHandling.CONTINUE_ON_FAILURE)

WebUI.delay(1)  

def pageText = WebUI.executeJavaScript('return document.body.innerText', [], FailureHandling.STOP_ON_FAILURE)

if (pageText.contains("- 失敗")) {
    WebUI.executeJavaScript('''
        toastr.options = {
            "positionClass": "toast-center-top",  
            "timeOut": "700",  
            "closeButton": false, 
            "iconClass": "toast-error",  
            "closeHtml": "<button>&times;</button>"  
        };
        toastr.error("異動紀錄有失敗內容", "Fail", { "positionClass": "toast-center-top" });
    ''', [], FailureHandling.STOP_ON_FAILURE)
} else {
    WebUI.executeJavaScript('''
        toastr.options = {
            "positionClass": "toast-center-top",
            "timeOut": "700",
            "closeButton": false, 
            "iconClass": "toast-success",  
            "closeHtml": "<button>&times;</button>"
        };
        toastr.success("無異動紀錄失敗", "Success", { "positionClass": "toast-center-top" });
    ''', [], FailureHandling.STOP_ON_FAILURE)
}

WebUI.takeFullPageScreenshot()