package ie.ciaranb.ipfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;



public class Runner {

	    public static void main(String[] args) {
	    	
	    	try{
				System.out.println("Computer's IPV4 IP: " + InetAddress.getLocalHost().getHostAddress()); //gets Local IP
				
			}catch (Exception ex){
				System.out.println("Oops! " + ex.getMessage());
			}
			try{
				String url = "http://vallentinsource.com/globalip.php"; //Website that outputs global IP address
				BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
				
				String receive = br.readLine();
				
				System.out.println("Global IP: " + receive);
				
			}catch (Exception ex){
				System.out.println("Oops! " + ex.getMessage());
			}
			
	    	ArrayList<String> ipAddresses;
	    	
			try {
				ipAddresses = readTextFile("IPList.txt");
			
				//for(int i = 0; i < lines.size(); i = i + 1){
		    	for (String ipAddress : ipAddresses) {
	
					System.out.println(ipAddress);
					System.out.println("Looking up IP...");
					
			        try {
			            // Create and send the HTTP request to get country from ip address
			        	
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
			
			            // Get the HTTP response body
			            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			            StringBuilder sb = new StringBuilder();
			            String line;
			            while ((line = br.readLine()) != null) {
			              sb.append(line);
			            }
			
			            conn.disconnect();
			            br.close();
			            
			            System.out.println("Response: " + sb.toString());
			
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

