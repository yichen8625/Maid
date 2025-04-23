<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>申請廳主買域名</name>
   <tag></tag>
   <elementGuidId>d45f8534-87f6-4c34-b7b6-96ec7c94f7d3</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n  \&quot;domain\&quot;: \&quot;qatest${RANDOMNUM}.com\&quot;,\n  \&quot;hall_name\&quot;: \&quot;DCTEST測試環境@dct\&quot;,\n  \&quot;site_group\&quot;: \&quot;dct\&quot;,\n  \&quot;force_binding\&quot;: true,\n  \&quot;web_layout\&quot;: \&quot;normal\&quot;,\n  \&quot;apply_certificate\&quot;: true,\n  \&quot;apply_third_level_random\&quot;: false,\n  \&quot;verify_mode\&quot;: \&quot;none\&quot;,\n  \&quot;txt\&quot;: \&quot;\&quot;,\n  \&quot;company_maintenance\&quot;: true,\n  \&quot;domain_provider\&quot;: \&quot;DCTEST測試環境@dct\&quot;,\n  \&quot;provider_account\&quot;: \&quot;\&quot;,\n  \&quot;provider_password\&quot;: \&quot;\&quot;\n}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>accept</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>e1ea3c6a-ba4d-44b2-b0bc-9eb24853a13a</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>X-API-Key</name>
      <type>Main</type>
      <value>${PF_KEY}</value>
      <webElementGuid>43b91ec1-d627-4779-9679-751815912600</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>b76ede40-32e4-48ed-bcf1-ff9e015083ce</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>9.7.4</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path></path>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>${DEV}/workflow_api/pf/application/purchase_domain/customer</restUrl>
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
      <id>d38f3b3a-7c38-4509-b241-61c61ad839a4</id>
      <masked>false</masked>
      <name>DEV</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.PF_KEY</defaultValue>
      <description></description>
      <id>bad78502-f201-49aa-8133-910f5e092217</id>
      <masked>false</masked>
      <name>PF_KEY</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.RANDOMNUM</defaultValue>
      <description></description>
      <id>f495f5e2-8702-4bac-bfa2-46ddea7013e4</id>
      <masked>false</masked>
      <name>RANDOMNUM</name>
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
