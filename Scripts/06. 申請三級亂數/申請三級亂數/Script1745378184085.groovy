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
import groovy.json.JsonSlurper


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
// (PDNS) NS 指向設定
def responseNameServerPoint = WS.sendRequest(findTestObject('Object Repository/API/申請三級亂數/Happy Path/(PDNS) NS 指向設定'))
WS.verifyResponseStatusCode(responseNameServerPoint, 200, FailureHandling.STOP_ON_FAILURE)

// (PDNS) 打憑證(付費)Tag
def responseIpResolution = WS.sendRequest(findTestObject('Object Repository/API/申請三級亂數/Happy Path/(PDNS) 打憑證(付費)Tag'))
WS.verifyResponseStatusCode(responseIpResolution, 200, FailureHandling.STOP_ON_FAILURE)

// (PDNS) dctest Table更新
def responseTableDomains = WS.sendRequest(findTestObject('Object Repository/API/申請三級亂數/Happy Path/(PDNS) dctest Table 更新'))
WS.verifyResponseStatusCode(responseIpResolution, 200, FailureHandling.STOP_ON_FAILURE)

// 申請三級亂數
WebUI.delay(4)
def responseThirdLevelRandom = WS.sendRequest(findTestObject('Object Repository/API/申請三級亂數/Happy Path/申請三級亂數'))
WS.verifyResponseStatusCode(responseThirdLevelRandom, 201, FailureHandling.STOP_ON_FAILURE)

// 顯示 Toast 提示訊息
WebUI.executeJavaScript("""
    // 建立 toast 消息顯示的 HTML 元素
    var toast = document.createElement("div");
    toast.id = "dynamicToast";
    toast.style.position = "fixed";
    toast.style.top = "10%";  // 設置 toast 顯示在畫面中上
    toast.style.left = "50%";
    toast.style.transform = "translateX(-50%)";  // 水平居中
    toast.style.backgroundColor = "#333";
    toast.style.color = "#fff";
    toast.style.padding = "10px 20px";
    toast.style.borderRadius = "5px";
    toast.style.zIndex = "10000";
    toast.style.display = "flex";
    toast.style.alignItems = "center";  // 使 icon 和文字在垂直方向對齊

    // 創建 loading icon
    var loadingIcon = document.createElement("div");
    loadingIcon.style.border = "4px solid #f3f3f3";  // 淺灰色邊框
    loadingIcon.style.borderTop = "4px solid #3498db";  // 藍色上邊框
    loadingIcon.style.borderRadius = "50%";
    loadingIcon.style.width = "20px";  // 設定圓形大小
    loadingIcon.style.height = "20px";
    loadingIcon.style.animation = "spin 1s linear infinite";  // 旋轉動畫
    loadingIcon.style.marginRight = "10px";  // 在 icon 和文字之間增加 10px 的間隙

    // 創建樣式，讓 loading 圖示旋轉
    var style = document.createElement("style");
    style.innerHTML = `
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    `;
    document.head.appendChild(style);

    // 將 loading 圖示添加到 toast 中
    toast.appendChild(loadingIcon);

    // 設置 toast 文字
    var messageText = document.createElement("span");
    messageText.innerText = "正在進行申請三級亂數操作，請稍候...";
    toast.appendChild(messageText);

    // 添加到頁面中
    document.body.appendChild(toast);

    // 設置 toast 動畫消失效果
    setTimeout(function() {
        toast.style.transition = "opacity 1s";
        toast.style.opacity = "0";
        setTimeout(function() {
            toast.remove();
        }, 1000);
    }, 18000); // 顯示 18 秒後淡出並移除
""", [])

// 解析 workflow_id 並儲存到 GlobalVariable
def third_level_random = new JsonSlurper().parseText(responseThirdLevelRandom.getResponseText())
def tlr_workflow_id = third_level_random.workflow_id
GlobalVariable.TLR_WORKFLOW_ID = tlr_workflow_id
println("✅TLR_WORKFLOW_ID: " + GlobalVariable.TLR_WORKFLOW_ID)

// 取得申請三級亂數詳細資料
def responseDetails = WS.sendRequest(findTestObject('Object Repository/API/申請三級亂數/Happy Path/取得申請三級亂數詳細資料'))
WS.verifyResponseStatusCode(responseDetails, 200)

def third_level_random_detail = new JsonSlurper().parseText(responseDetails.getResponseText())
WS.verifyElementPropertyValue(responseDetails, 'workflow_id', GlobalVariable.TLR_WORKFLOW_ID.toString(), FailureHandling.STOP_ON_FAILURE)
WS.verifyElementPropertyValue(responseDetails, 'domain', GlobalVariable.DOMAIN.toString(), FailureHandling.STOP_ON_FAILURE)
WS.verifyElementPropertyValue(responseDetails, 'workflow_name', 'ApplyThirdLevelRandom', FailureHandling.STOP_ON_FAILURE)

def workflow_name = third_level_random_detail.workflow_name
GlobalVariable.WORKFLOW_NAME = workflow_name
println("✅WORKFLOW_NAME: " + GlobalVariable.WORKFLOW_NAME)

// 取得申請三級亂數項目資料 (Job狀態檢查)
WebUI.delay(4)
def responseWorkflow = WS.sendRequest(findTestObject('Object Repository/API/申請三級亂數/Happy Path/取得申請三級亂數項目資料 (Job狀態檢查)'))
WS.verifyResponseStatusCode(responseWorkflow, 200)

def third_level_random_workflow = new JsonSlurper().parseText(responseWorkflow.getResponseText())

// 預期的 Job 名稱清單
def expectedNames = [
	"AddThirdLevelRandom",
	"RecheckThirdLevelRandom"
]

def actualNames = third_level_random_workflow.collect { it.name }

expectedNames.each { expectedName ->
	assert actualNames.contains(expectedName) : "Missing expected job name: ${expectedName}"
}

// 驗證所有 Job 狀態為 success
def failedJobs = third_level_random_workflow.findAll { it.status != 'success' }

if (failedJobs) {
	println "❌ 以下 job status 不為 success："
	failedJobs.each { job ->
		println "🔴 Job ID: ${job.job_id}, Name: ${job.name}, Status: ${job.status}, Message: ${job.message ?: '無訊息'}"
	}
}

// 儲存每個 Job 的 job_id 到 GlobalVariable
third_level_random_workflow.each { job ->
	if (expectedNames.contains(job.name)) {
		String globalVariableName = "${job.name}_job_id"
		GlobalVariable."${globalVariableName}" = job.job_id
		println "✅ 存儲 job_id for ${job.name}: ${job.job_id} into GlobalVariable.${globalVariableName}"
	}
}


// ======================
// 手動調整 Job 狀態
// ======================

// 更改 AddThirdLevelRandom 狀態
WebUI.delay(3)
def responseAddThirdLevelRandom = WS.sendRequest(findTestObject('Object Repository/API/申請三級亂數/Happy Path/更改AddThirdLevelRandom 狀態'))
WS.verifyResponseStatusCode(responseAddThirdLevelRandom, 204, FailureHandling.STOP_ON_FAILURE)

// 更改 RecheckThirdLevelRandom 狀態
WebUI.delay(3)
def responseRecheckThirdLevelRandom = WS.sendRequest(findTestObject('Object Repository/API/申請三級亂數/Happy Path/更改RecheckThirdLevelRandom 狀態'))
WS.verifyResponseStatusCode(responseRecheckThirdLevelRandom, 204, FailureHandling.STOP_ON_FAILURE)

