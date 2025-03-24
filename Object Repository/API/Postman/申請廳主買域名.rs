<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>申請廳主買域名</name>
   <tag></tag>
   <elementGuidId>93e81e90-e15b-4e7f-a463-7f3e86078b9c</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>true</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n  \&quot;domain\&quot;: \&quot;${newName}.com\&quot;,\n  \&quot;site_group\&quot;: \&quot;${newName}\&quot;,\n  \&quot;force_binding\&quot;: true,\n  \&quot;web_layout\&quot;: \&quot;simple\&quot;,\n  \&quot;apply_certificate\&quot;: true,\n  \&quot;apply_third_level_random\&quot;: true,\n  \&quot;verify_mode\&quot;: \&quot;TXT\&quot;,\n  \&quot;txt\&quot;: \&quot;bbc\&quot;,\n  \&quot;company_maintenance\&quot;: true,\n  \&quot;domain_provider\&quot;: \&quot;dctest\&quot;,\n  \&quot;provider_account\&quot;: \&quot;test123\&quot;,\n  \&quot;provider_password\&quot;: \&quot;test456\&quot;\n}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>x-api-key</name>
      <type>Main</type>
      <value>${X-API-Key}</value>
      <webElementGuid>d6fbd113-a7a1-4d23-b8b9-c3cb2cebbd40</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>false</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Authorization</name>
      <type>Main</type>
      <value>${Authorization}</value>
      <webElementGuid>6f4f8512-43d7-4b97-b693-71df0c5a3e8f</webElementGuid>
   </httpHeaderProperties>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>ead8b850-6cd7-490d-a848-0a919bef5d37</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>9.7.4</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path></path>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>${dev}/pf/application/purchase_domain/customer</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <variables>
      <defaultValue>GlobalVariable.dev</defaultValue>
      <description></description>
      <id>79cd644c-dba4-4c51-8e82-2de6282c8b48</id>
      <masked>false</masked>
      <name>dev</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.X-API-Key</defaultValue>
      <description></description>
      <id>8305bd12-796f-41eb-9993-14177264bff9</id>
      <masked>false</masked>
      <name>X-API-Key</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.Authorization</defaultValue>
      <description></description>
      <id>227374c1-97cb-4a8c-945b-e4fdf829c1c3</id>
      <masked>false</masked>
      <name>Authorization</name>
   </variables>
   <variables>
      <defaultValue>GlobalVariable.newName</defaultValue>
      <description></description>
      <id>4af2f34b-f483-4a4e-b559-f3b5840e4316</id>
      <masked>false</masked>
      <name>newName</name>
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
