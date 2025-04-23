<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>取得廳主買域名詳細資料</name>
   <tag></tag>
   <elementGuidId>b0628efc-9bd2-4228-8f59-6e76053380f4</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>true</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent></httpBodyContent>
   <httpBodyType></httpBodyType>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Accept</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>4826e572-7c88-4364-ae81-a0d96f2d26f4</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>X-API-Key</name>
      <type>Main</type>
      <value>${ADM_KEY}</value>
      <webElementGuid>2a6aaaf0-6f13-428c-881e-3cb921a34438</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>59d5f822-3f76-4a84-b481-8b19cd047802</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>9.7.4</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path></path>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>${DEV}/workflow_api/adm/workflows/${PD_WORKFLOW_ID}</restUrl>
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
      <id>63d283d3-1860-4453-9231-7cd115dcd821</id>
      <masked>false</masked>
      <name>DEV</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.ADM_KEY</defaultValue>
      <description></description>
      <id>1182465c-399b-43ce-9e89-8d58805795e2</id>
      <masked>false</masked>
      <name>ADM_KEY</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.PD_WORKFLOW_ID</defaultValue>
      <description></description>
      <id>85f3d7c4-f345-4576-b307-afb6bf088b7d</id>
      <masked>false</masked>
      <name>PD_WORKFLOW_ID</name>
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
