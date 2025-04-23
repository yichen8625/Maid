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

'申請刪除域名'
// 申請刪除域名
WebUI.delay(3)
def responseDeleteDomain = WS.sendRequest(findTestObject('Object Repository/API/申請刪除域名/Happy Path/申請刪除域名'))
WS.verifyResponseStatusCode(responseDeleteDomain , 201, FailureHandling.STOP_ON_FAILURE) // 檢查 HTTP 狀態碼是否為 201

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
    messageText.innerText = "正在進行申請刪除域名操作，請稍候...";
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
    }, 5000); // 顯示 5 秒後淡出並移除
""", [])

def delete_domain = new JsonSlurper().parseText(responseDeleteDomain .getResponseText()) // 提取申請購買部署憑證 workflow_id
def dd_workflow_id = delete_domain.workflow_id
GlobalVariable.DD_WORKFLOW_ID = dd_workflow_id
println("✅DD_WORKFLOW_ID: " + GlobalVariable.DD_WORKFLOW_ID)

// 取得刪除域名申請詳細資料
def responseDetails = WS.sendRequest(findTestObject('Object Repository/API/申請刪除域名/Happy Path/取得刪除域名申請詳細資料'))
WS.verifyResponseStatusCode(responseDetails, 200) // 檢查 HTTP 狀態碼是否為 200

def delete_domain_detail = new JsonSlurper().parseText(responseDetails.getResponseText())
WS.verifyElementPropertyValue(responseDetails, 'workflow_id', GlobalVariable.DD_WORKFLOW_ID.toString(), FailureHandling.STOP_ON_FAILURE) // 驗證 workflow_id
WS.verifyElementPropertyValue(responseDetails, 'domain', GlobalVariable.DOMAIN.toString(), FailureHandling.STOP_ON_FAILURE) // 驗證 domain
WS.verifyElementPropertyValue(responseDetails, 'workflow_name', 'DeleteDomain', FailureHandling.STOP_ON_FAILURE) // 驗證 workflow_name
def workflow_name = delete_domain_detail.workflow_name
GlobalVariable.WORKFLOW_NAME = workflow_name
println("✅WORKFLOW_NAME: " + GlobalVariable.WORKFLOW_NAME)

// 取得刪除域名申請項目資料 (Job 檢查)
WebUI.delay(4)
def responseWorkflow = WS.sendRequest(findTestObject('Object Repository/API/申請刪除域名/Happy Path/取得刪除域名項目資料 (Job狀態檢查)'))
WS.verifyResponseStatusCode(responseWorkflow, 200) // 檢查 HTTP 狀態碼是否為 200

def delete_domain_workflow = new JsonSlurper().parseText(responseWorkflow.getResponseText()) // 解析自動化工作項目資料 response

// 定義預期的 job 名稱
def expectedNames = [
    "DeleteDomainRecord"
]

// 驗證所有預期的 name 是否出現在回應中 
def actualNames = delete_domain_workflow.collect { it.name } // 獲取實際的 job names

expectedNames.each { expectedName ->
    assert actualNames.contains(expectedName) : "Missing expected job name: ${expectedName}"
}

// 檢查所有 job 的 status 是否為 success
def failedJobs = delete_domain_workflow.findAll { it.status != 'success' } // 找到所有 status 不是 success 的 job

if (failedJobs) {
    println "❌ 以下 job status 不为 success："
    failedJobs.each { job ->
        println "🔴 Job ID: ${job.job_id}, Name: ${job.name}, Status: ${job.status}, Message: ${job.message ?: '無讯息'}"
    }
}

// 將 job_id 存入 GlobalVariables
delete_domain_workflow.each { job ->
    if (expectedNames.contains(job.name)) {
        String globalVariableName = "${job.name}_job_id"
        GlobalVariable."${globalVariableName}" = job.job_id
        println "✅ 存储 job_id for ${job.name}: ${job.job_id} into GlobalVariable.${globalVariableName}"
    }
}

