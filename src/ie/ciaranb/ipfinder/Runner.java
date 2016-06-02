package ie.ciaranb.ipfinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


public class Runner {
	
	private static String receive;

	    public static void main(String[] args) {
	    		    	
	    	try{
				System.out.println("Computer IPV4 IP: " + InetAddress.getLocalHost().getHostAddress()); //gets Local IP
				
			}catch (Exception ex){
				System.out.println("Oops! " + ex.getMessage());
			}
			try{
				String url = "http://vallentinsource.com/globalip.php"; //Website that outputs global IP address
				BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
				
				receive = br.readLine();
				
				System.out.println("Current computer IP: " + receive);
				
			}catch (Exception ex){
				System.out.println("Oops! " + ex.getMessage());
			}
			
	    	ArrayList<String> ipAddresses;
	    	
			try {
				ipAddresses = readTextFile("IPList.txt");
				ipAddresses.add(0, receive);
			
				//for(int i = 0; i < lines.size(); i = i + 1){
		    	for (String ipAddress : ipAddresses) {
	
					System.out.println(ipAddress);
					System.out.println("Looking up IP...");
					
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
			            System.out.println("Successful lookup");
			
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
			            String countryName = "";
			            countryName = nodeList1.item(0).getChildNodes().item(0).getTextContent();
		            	
			            // Get the CountryCode node value
			            NodeList nodeList = xmlResponse.getElementsByTagName("CountryCode");
			            String countryCode = "";
		            	countryCode = nodeList.item(0).getChildNodes().item(0).getTextContent();
		            	//*************************************************************************************
		            	// Close down and free resources
			            conn.disconnect();
			            br.close();
			            
			            System.out.println("Response: " + countryName);
			            System.out.println("Response: " + countryCode);
			            System.out.println();
			
			        } catch (MalformedURLException m) {
			              System.out.println(m.getMessage());     
			        } catch (IOException x) {
			              System.out.println(x.getMessage());       
			        } catch (Exception e) {
			              System.out.println(e.getMessage());                
			        }
			    }
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }

	    public static ArrayList<String> readTextFile(String file) throws FileNotFoundException {
				
				ArrayList<String> lines = new ArrayList<String>();
				
				
				Scanner s1 = new Scanner(new File(file));
				while(s1.hasNextLine()){
					lines.add(s1.nextLine());
				}
				
				s1.close();

			return lines;
	    }
	}

