<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>更改UpdateNameServer 狀態</name>
   <tag></tag>
   <elementGuidId>cf196a71-630c-408e-b3cc-7d928b03c741</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n    \&quot;id\&quot;:${UpdateNameServer_job_id},\n    \&quot;status\&quot;:\&quot;success\&quot;\n    }&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Accept</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>43795102-a8f9-43e9-8b04-2b18cd39b046</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>X-API-Key</name>
      <type>Main</type>
      <value>${ADM_KEY}</value>
      <webElementGuid>9e86b58c-cda7-4c37-8282-58c24b5c228b</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>ae28ab1a-1f81-42f3-9e27-49b3e88097b8</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>9.7.4</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path></path>
   <restRequestMethod>PATCH</restRequestMethod>
   <restUrl>${DEV}/workflow_api/adm/jobs/status</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <variables>
      <defaultValue>GlobalVariable.DEV</defaultValue>
      <description></description>
      <id>b3e7f260-e9bc-43c1-b227-09cbf4e9a769</id>
      <masked>false</masked>
      <name>DEV</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.ADM_KEY</defaultValue>
      <description></description>
      <id>6b3a9d6e-17f1-4ad7-889b-676b1522e9b3</id>
      <masked>false</masked>
      <name>ADM_KEY</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.UpdateNameServer_job_id</defaultValue>
      <description></description>
      <id>70e53c26-a1c0-41d8-b89b-7afe0fc981e0</id>
      <masked>false</masked>
      <name>UpdateNameServer_job_id</name>
   </variables>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()

ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
