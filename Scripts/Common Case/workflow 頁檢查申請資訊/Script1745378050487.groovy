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

// 函數：載入外部資源（jQuery 和 Toastr.js）
def loadExternalResources() {
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
    ''', [], FailureHandling.OPTIONAL)
}

def workflow_url = ""
def generateWorkflowUrl() {
    def workflowIdMap = [
        "CustomerApplyPurchaseDomain": GlobalVariable.PD_WORKFLOW_ID,
        "DeleteDomain"              : GlobalVariable.DD_WORKFLOW_ID,
        "PurchaseCertificate"       : GlobalVariable.PC_WORKFLOW_ID,
		"RenewCertificate"       : GlobalVariable.RC_WORKFLOW_ID,
		"ApplyThirdLevelRandom"  : GlobalVariable.TLR_WORKFLOW_ID,
		"ApplyReuseCertificate"  : GlobalVariable.RUC_WORKFLOW_ID,
		"ApplyReplaceCertificateProvider"  : GlobalVariable.RCP_WORKFLOW_ID,
		"ApplyAttachAntiBlockTarget"  : GlobalVariable.ABT_WORKFLOW_ID,
		"ApplyDetachAntiBlockTarget"  : GlobalVariable.DABT_WORKFLOW_ID,
		"ApplyAttachAntiHijackTarget"  : GlobalVariable.AJT_WORKFLOW_ID,
		"ApplyDetachAntiHijackTarget"  : GlobalVariable.DAJT_WORKFLOW_ID
    ]

    def workflowName = GlobalVariable.WORKFLOW_NAME
    if (workflowIdMap.containsKey(workflowName)) {
        return "${GlobalVariable.G_VIR_URL}/#/auto_detail/${workflowIdMap[workflowName]}"
    } else {
        println("❌ 找不到對應的 workflow_name: ${workflowName}")
        return ""
    }
}

workflow_url = generateWorkflowUrl()
println("✅Generated URL: ${workflow_url}")


// 進行導航
if (workflow_url != "") {
	WebUI.navigateToUrl(workflow_url, FailureHandling.STOP_ON_FAILURE)
	WebUI.maximizeWindow()
	WebUI.waitForPageLoad(2)
}

// 載入外部資源
loadExternalResources()

// 申請資訊驗證
def verifyWorkflowID(String id, String label, FailureHandling handling = FailureHandling.STOP_ON_FAILURE) {
	if (id) {
		// 驗證是否包含 workflow_id
		WebUI.verifyTextPresent(id.toString(), true, handling)
		println("✅ 驗證成功: ${label} 申請單號： ${id}")
	} else {
		println("⚠️ ${label} workflow_id 為空，略過驗證")
	}
}

// 執行申請單號驗證
verifyWorkflowID(GlobalVariable.PD_WORKFLOW_ID.toString(), "申請廳主買域名", FailureHandling.OPTIONAL)
verifyWorkflowID(GlobalVariable.PC_WORKFLOW_ID.toString(), "申請購買與部署憑證", FailureHandling.OPTIONAL)

// 域名驗證
if (GlobalVariable.DOMAIN) {
	WebUI.verifyTextPresent(GlobalVariable.DOMAIN.toString(), true, FailureHandling.STOP_ON_FAILURE)
	println("✅ 域名: " + GlobalVariable.DOMAIN)
} else {
	println("⚠️ GlobalVariable.DOMAIN 為空，無法進行驗證")
}

// 申請類型驗證
String pageTextl = WebUI.executeJavaScript("return document.body.innerText", [], FailureHandling.STOP_ON_FAILURE)
// 定義所有可能出現的申請類型 
List<String> applicationTypes = [
	"申請廳主買域名",
	"申請刪除域名",
	"申請憑證",
	"申請展延憑證",
	"申請撤銷憑證",
	"申請三級亂數",
	"申請轉移憑證",
	"申請更換憑證商",
	"申請抗封鎖目標",
	"申請撤下抗封鎖目標",
	"申請抗劫持目標",
	"申請撤下抗劫持目標",
	"申請更換主域名",
	"申請更換一對一的域名"
]

// 動態比對目前畫面是哪一個類型
String matchedType = applicationTypes.find { type -> pageTextl?.contains(type) }

if (matchedType != null) {
	println("✅ 畫面出現的申請類型為：${matchedType}")
	WebUI.verifyTextPresent(matchedType, true, FailureHandling.STOP_ON_FAILURE)
} else {
	println("❗ 未匹配任何申請類型，請確認頁面是否正確載入")
}

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

// 異動紀錄頁驗證
WebUI.click(findTestObject('Object Repository/Workflow Detail/div_record'))

WebUI.delay(1)

def pageTextll = WebUI.executeJavaScript('return document.body.innerText', [], FailureHandling.STOP_ON_FAILURE)

if (pageTextll.contains("- 失敗")) {
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
