Feature: POC for BDD using Selenium java with cucumber-jvm Parameter handling
	@soap  
	  Scenario: Soap service- Test
	  Given I have Valid WSDL_URL with active service
	  And I have REQUEST_XML and EXPECTED_RESPONSE_XML details
	  When I send request_xml to Endpoint URL or web_service
	  Then I get ACTUAL_RESPONSE_XML 
	  And it exactly match with expected EXPECTED_RESPONSE_XML