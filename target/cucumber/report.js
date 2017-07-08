$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("features/testSoapService.feature");
formatter.feature({
  "line": 1,
  "name": "POC for BDD using Selenium java with cucumber-jvm Parameter handling",
  "description": "",
  "id": "poc-for-bdd-using-selenium-java-with-cucumber-jvm-parameter-handling",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 3,
  "name": "Soap service- Test",
  "description": "",
  "id": "poc-for-bdd-using-selenium-java-with-cucumber-jvm-parameter-handling;soap-service--test",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 2,
      "name": "@soap"
    }
  ]
});
formatter.step({
  "line": 4,
  "name": "I have Valid WSDL_URL with active service",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "I have REQUEST_XML and EXPECTED_RESPONSE_XML details",
  "keyword": "And "
});
formatter.step({
  "line": 6,
  "name": "I send request_xml to Endpoint URL or web_service",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "I get ACTUAL_RESPONSE_XML",
  "keyword": "Then "
});
formatter.step({
  "line": 8,
  "name": "it exactly match with expected EXPECTED_RESPONSE_XML",
  "keyword": "And "
});
formatter.match({
  "location": "testSoapServiceDef.i_have_Valid_WSDL_URL_with_active_service()"
});
formatter.result({
  "duration": 660379165,
  "status": "passed"
});
formatter.match({
  "location": "testSoapServiceDef.i_have_REQUEST_XML_and_EXPECTED_RESPONSE_XML_details()"
});
formatter.result({
  "duration": 1041880,
  "status": "passed"
});
formatter.match({
  "location": "testSoapServiceDef.i_send_request_xml_to_Endpoint_URL_or_web_service()"
});
formatter.result({
  "duration": 1436764900,
  "status": "passed"
});
formatter.match({
  "location": "testSoapServiceDef.i_get_ACTUAL_RESPONSE_XML()"
});
formatter.result({
  "duration": 629565063,
  "status": "passed"
});
formatter.match({
  "location": "testSoapServiceDef.it_exactly_match_with_expected_EXPECTED_RESPONSE_XML()"
});
formatter.result({
  "duration": 109498431,
  "status": "passed"
});
});