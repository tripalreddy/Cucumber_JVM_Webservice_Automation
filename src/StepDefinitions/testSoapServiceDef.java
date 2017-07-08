package StepDefinitions;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class testSoapServiceDef {
	String SOAPUrl; 
	String xmlFile2Send;
	String ExpectedXML;
	static String ActualXML;
	URLConnection connection;
	
	
	
	
@Given("^I have Valid WSDL_URL with active service$")
public void i_have_Valid_WSDL_URL_with_active_service(){
    // Write code here that turns the phrase above into concrete actions
	try{
    SOAPUrl = "http://www.webservicex.com/CurrencyConvertor.asmx?wsdl";
}catch (Exception e){
	System.out.println("exception:"+e.getMessage());
}
}

@Given("^I have REQUEST_XML and EXPECTED_RESPONSE_XML details$")
public void i_have_REQUEST_XML_and_EXPECTED_RESPONSE_XML_details(){
    // Write code here that turns the phrase above into concrete actions
	xmlFile2Send = "C:\\Users\\tripalreddy\\workspace\\cucumber_WS_Framework\\src\\resources\\Request.xml";
	ExpectedXML = "C:\\Users\\tripalreddy\\workspace\\cucumber_WS_Framework\\src\\resources\\Exp_Response.xml";

}

@When("^I send request_xml to Endpoint URL or web_service$")
public void i_send_request_xml_to_Endpoint_URL_or_web_service(){
	 // Create the connection with http
   try{
	URL url = new URL(SOAPUrl);
    connection = url.openConnection();


    FileInputStream fin = new FileInputStream(xmlFile2Send);
    ByteArrayOutputStream bout = new ByteArrayOutputStream();

    copy(fin, bout);
    fin.close();

    byte[] b = bout.toByteArray();
   // StringBuffer buf=new StringBuffer();
    String s=new String(b);

    /**sample code to modify input xml- should be written here**/
	
  
    
    inputXMLupdation(xmlFile2Send, "web:FromCurrency","USD");
    inputXMLupdation(xmlFile2Send, "web:ToCurrency","INR");
    b=s.getBytes();
    
    
    // Set the appropriate HTTP parameters.
    connection.setRequestProperty("Content-Length", String.valueOf(b.length));
    connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

    connection.setRequestProperty("POST", "");
 //   ((HttpURLConnection) connection).setRequestMethod("POST");
    connection.setDoOutput(true);

    // send the XML that was read in to b.
    OutputStream out = connection.getOutputStream();

    out.write(b);
    System.out.println("modifiedInputXml:"+out);
    out.close();
}catch (Exception e){
	System.out.println("exception:"+e.getMessage());
}


}

@Then("^I get ACTUAL_RESPONSE_XML$")
public void i_get_ACTUAL_RESPONSE_XML(){
    // Read the response.
	try{   
    connection.connect();

    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
    BufferedReader in = new BufferedReader(isr);
	String inputLine;

    while ((inputLine = in.readLine()) != null)
    	writefile(inputLine);

    
    in.close();
	}catch (Exception e){
		System.out.println("exception:"+e.getMessage());
	}
}

@Then("^it exactly match with expected EXPECTED_RESPONSE_XML$")
public void it_exactly_match_with_expected_EXPECTED_RESPONSE_XML(){
	compXML(ActualXML,ExpectedXML);
}

//additional fucntions

public static void copy(InputStream in, OutputStream out)
        throws IOException {

    synchronized (in) {
        synchronized (out) {
            byte[] buffer = new byte[256];
            while (true) {
                int bytesRead = in.read(buffer);
                if (bytesRead == -1)
                    break;
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}

private static void writefile(String responseData) throws InterruptedException, IOException {
	// TODO Auto-generated method stub
	 ActualXML = "C:\\Users\\tripalreddy\\workspace\\cucumber_WS_Framework\\src\\resources\\Response.xml";
	
	try {
		FileOutputStream fout=new FileOutputStream(ActualXML);    
	     BufferedOutputStream bout=new BufferedOutputStream(fout);    
		
	     byte b[]=responseData.getBytes("utf-8");    
	     bout.write(b);  

			bout.flush();
			bout.close();
		System.out.println("Done");

	} catch (IOException e) {
		e.printStackTrace();

	}
}

/**
 * Method to update input XML file
 * @return 
 */
public static void inputXMLupdation(String inputXMLLocation, String tagToUpdate, String valueToUpdate){

	try {

			File inputXMLToUpdate = new File(inputXMLLocation);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputXMLToUpdate);
		        
			
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("soapenv:Body");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					//System.out.println("Staff id : " + eElement.getAttribute("id"));
					System.out.println("web:FromCurrency : " + eElement.getElementsByTagName("web:FromCurrency").item(0).getTextContent());
					System.out.println("web:ToCurrency : " + eElement.getElementsByTagName("web:ToCurrency").item(0).getTextContent());
					
					eElement.getElementsByTagName(tagToUpdate).item(0).setTextContent(valueToUpdate);
					System.out.println("tagToUpdate: " + eElement.getElementsByTagName(tagToUpdate).item(0).getTextContent());
					
				}
    			TransformerFactory transformerFactory = TransformerFactory.newInstance();
    			Transformer transformer = transformerFactory.newTransformer();
    			DOMSource source = new DOMSource(doc);
    			StreamResult result = new StreamResult(new File(inputXMLLocation));
    			transformer.transform(source, result); 
			}
		    } catch (Exception e) {
			e.printStackTrace();
		  }
}

/*
public void textDiff(){

    //Parse the two input files
    DocumentBuilderFactory dbFactory =   
              DocumentBuilderFactory.newInstance();
    dbFactory.setNamespaceAware(true);
    DocumentBuilder docBuilder = 
              dbFactory.newDocumentBuilder();
    Node doc = docBuilder.parse(new File(args[0]));
    Node doc1 = docBuilder.parse(new File(args[1]));

    //Run the diff
    try
    {
        Document diffAsDom = xmlUtils.diffToDoc(doc, 
                              doc1, new Options());
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    
}*/
//comp function

public void compXML(String ActualXML, String ExpectedXML){
	// reading two xml file to compare in Java program 
	
	try{
		FileInputStream fis1 = new FileInputStream(ActualXML); 
		FileInputStream fis2 = new FileInputStream(ExpectedXML); 
		// using BufferedReader for improved performance 
		BufferedReader source = new BufferedReader(new InputStreamReader(fis1)); 
		
		BufferedReader target = new BufferedReader(new InputStreamReader(fis2));
		
		//configuring XMLUnit to ignore white spaces 
		XMLUnit.setIgnoreWhitespace(true); 
		
		//comparing two XML using XMLUnit in Java 
		List<String> differences = compare(source, target); 
		//showing differences found in two xml files 
		//printDifferences(differences);
		int totalDifferences = differences.size(); 
		System.out.println("==============================="); 
		System.out.println("Total differences : " + totalDifferences); 
		System.out.println("================================"); 
		
		//for(Difference difference : differences){ 
			System.out.println(differences); 
		//	} 
		

		
	}catch(Exception e){
		System.out.println("exception in comp xml function:"+e.getMessage());
	}
	
	}

public static List<String> compare(Reader source, Reader target) throws IOException, SAXException{ 
	//creating Diff instance to compare two XML files 
	Diff xmlDiff = new Diff(source, target); 
	
	//for getting detailed differences between two xml files 
	DetailedDiff detailXmlDiff = new DetailedDiff(xmlDiff); 
	System.out.println("dif return:"+detailXmlDiff.getAllDifferences());
	return detailXmlDiff.getAllDifferences(); 
}

/*public static void printDifferences(List differences){ 
	int totalDifferences = differences.size(); 
	System.out.println("==============================="); 
	System.out.println("Total differences : " + totalDifferences); 
	System.out.println("================================"); 
	
	for(Difference difference : differences){ 
		System.out.println(difference.getDescription()); 
		} 
	} */




}
