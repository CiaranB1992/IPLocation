package ie.ciaranb.ipfinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class IPFinderURL {
	
	private IPList ipList;
	private ArrayList<String> listOfAddresses;
	
	/*
	 * Creates an IPList object and retrieves it's array information.
	 * Then passes the information to be checked by http://freegeoip.net/xml/
	 */
	public IPFinderURL() {
		
		ipList = new IPList();//creates the list object
		listOfAddresses = ipList.getIpAddresses();//stores IP addresses in the ListOfAddresses
		
		checkAddresses(listOfAddresses);
	}
	
	/*
	 * For each address call the method and perform a loopkup
	 */
	private void checkAddresses(ArrayList<String> listOfAddresses2){
    	for (String ipAddress : listOfAddresses) {

			System.out.println(ipAddress);
			System.out.println("Looking up IP...");
			
			urlIPFinder(ipAddress);//calls method urlIPFinder
			System.out.println();
			
	    }
	}

	/*
	 * This method makes a connection to http://freegeoip.net/xml/ and passes in an IP address.
	 * They perform the lookup and return the country of origin 
	 */
	public void urlIPFinder(String ipAddress) {
		try {
            // Create and send the HTTP request to get country from ip address
        	//*************************************************************************************
            URL url = new URL("http://freegeoip.net/xml/" + ipAddress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");

            // Only response status code 200 (OK) is acceptable
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
            }
            
            System.out.println("Successful lookup"); //only here to make a neat and tidy output

            // Get the HTTP response body - it is a string in XML form
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
              sb.append(line);
            }
            String webServiceResponse = sb.toString();
            
            // Parse XML string to an XML object
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(webServiceResponse));
            Document xmlResponse = builder.parse(is);
            
            //Get the countryName node value
            NodeList nodeList1 = xmlResponse.getElementsByTagName("CountryName");
            String countryName = nodeList1.item(0).getChildNodes().item(0).getTextContent();
        	
            // Get the CountryCode node value
            NodeList nodeList = xmlResponse.getElementsByTagName("CountryCode");
            String countryCode = "";
        	countryCode = nodeList.item(0).getChildNodes().item(0).getTextContent();
        	//*************************************************************************************
        	// Close down and free resources
            conn.disconnect();
            br.close();
            
            System.out.println("Response: " + countryName); //prints name of country
            System.out.println("Response: " + countryCode); //prints country code
            //If connection times out, it receives the error message from the third party API
            //So if the IP cannot be found, the error message is a generic response from the website
            
        } catch (MalformedURLException m) {
              System.out.println(m.getMessage());     
        } catch (IOException x) {
              System.out.println(x.getMessage());       
        } catch (Exception e) {
              System.out.println(e.getMessage());                
        }

	}
}
