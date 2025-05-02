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
import groovy.json.JsonSlurper


// 申請刪除域名
WebUI.delay(3)
def responseDelete

// 嘗試發送刪除域名請求
try {
    responseDelete = WS.sendRequest(findTestObject('Object Repository/API/申請刪除域名/Happy Path/申請刪除域名'), FailureHandling.OPTIONAL)

    if (responseDelete != null && responseDelete.getStatusCode() == 201) {
        println("✅刪除域名執行成功，測試數據 Clear!")
    } else {
        println("❌申請刪除域名失敗，狀態碼: " + (responseDelete != null ? responseDelete.getStatusCode() : "無回應"))
    }
} catch (Exception e) {
    println("❌申請刪除域名過程中發生錯誤：" + e.getMessage())
}

// 確保 responseDelete 是有效的
if (responseDelete != null && responseDelete.getResponseText()) {
    def delete_domain = new JsonSlurper().parseText(responseDelete.getResponseText())
    def dd_workflow_id = delete_domain.workflow_id
    GlobalVariable.DD_WORKFLOW_ID = dd_workflow_id
    println("✅DD_WORKFLOW_ID: " + GlobalVariable.DD_WORKFLOW_ID)
} else {
    println("❌ 無法解析回應或回應無效")
}

// 送出 API 請求，取得刪除域名項目資料
def responseDeleteJobInfo = WS.sendRequest(
    findTestObject('Object Repository/API/申請刪除域名/Happy Path/取得刪除域名項目資料 (Job狀態檢查)'),
    FailureHandling.OPTIONAL
)

// 使用 JsonSlurper 解析回應資料
def deleteJobInfo = new JsonSlurper().parseText(responseDeleteJobInfo.getResponseText())

// 確認回應資料格式是否為列表
if (deleteJobInfo instanceof List) {
    // 嘗試找到 "DeleteDomainRecord" 的 job
    def deleteDomainJob = deleteJobInfo.find { it instanceof Map && it.name == "DeleteDomainRecord" }

    WebUI.delay(3)

    if (deleteDomainJob != null) {
        GlobalVariable.DeleteDomainRecord_job_id = deleteDomainJob.job_id
        println("✅ DeleteDomainRecord Job ID: ${GlobalVariable.DeleteDomainRecord_job_id}")
    } else {
        println("❌ 無找到 DeleteDomainRecord Job")
    }

    // 嘗試找到 "RevokeCert" 的 job
    def revokeCertJob = deleteJobInfo.find { it instanceof Map && it.name == "RevokeCert" }

    if (revokeCertJob != null) {
        GlobalVariable.RevokeCert_job_id = revokeCertJob.job_id
        println("✅ RevokeCert Job ID: ${GlobalVariable.RevokeCert_job_id}")
    } else {
        println("❌ 無找到 RevokeCert Job")
    }

    // 處理不存在的 job
    if (GlobalVariable.DeleteDomainRecord_job_id == null && GlobalVariable.RevokeCert_job_id == null) {
        println("❌ 無 DeleteDomainRecord 或 RevokeCert Job")
    }

    // 如果 RevokeCert_job_id 存在，則執行狀態變更請求
    if (GlobalVariable.RevokeCert_job_id != null) {
      //println("✅ RevokeCert Job ID found: ${GlobalVariable.RevokeCert_job_id}. Sending request to change status...")
        def responseChangeStatus = WS.sendRequest(findTestObject('Object Repository/API/申請展延憑證/Happy Path/更改RevokeCert 狀態'))
    } else {
        println("❌ No RevokeCert Job found, skipping status change.")
    }
} else {
    println("❌ 回應資料格式不正確，無法處理")
}

WebUI.closeBrowser()
