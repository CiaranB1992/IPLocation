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
				System.out.println("current Computer's IP: " + InetAddress.getLocalHost().getHostAddress()); //gets Local IP
				
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
			
	    	String[] words = readArray("IPList.txt");
			
			for(int i = 0; i < words.length; i = i + 1){
				System.out.println(words[i]);
				
		        try {
		            // Create and send the HTTP request to get country from ip address
		        	
		            URL url = new URL("http://freegeoip.net/xml/" + words[i]);
		            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		            conn.setRequestMethod("GET");
		            conn.setRequestProperty("Accept", "application/xml");
		
		            // Only response status code 200 (OK) is acceptable
		            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
		                throw new RuntimeException("Failed : HTTP error code : "
		                    + conn.getResponseCode());
		            }
		            System.out.println("Arrived after lookup");
		
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
	    }
	    public static String[] readArray(String file){
			int ctr=0;
			try{
				Scanner s1 = new Scanner(new File(file));
				while(s1.hasNextLine()){
					ctr = ctr + 1;
					s1.next();
				}
				
				String[] words = new String[ctr];
				
				Scanner s2 = new Scanner(new File(file));
				for(int i = 0; i< ctr; i = i+1){
					words[i] = s2.next();
				}
				
				return words;
			}
			catch(FileNotFoundException e){
				
			}
			
			return null;
	    }
	}

