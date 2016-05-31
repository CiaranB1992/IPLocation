package ie.ciaranb.ipfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Runner {
	
	public static void main(String[] args) throws FileNotFoundException {
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
		System.out.println();
		System.out.println("Reading File: ");
		System.out.println();
	       //Name of the file
	       String fileName="IPList.txt";
	       try{

	          //Create object of FileReader
	          FileReader inputFile = new FileReader(fileName);

	          //Instantiate the BufferedReader Class
	          BufferedReader bufferReader = new BufferedReader(inputFile);

	          //Variable to hold the one line data
	          String line;

	          // Read file line by line and print on the console
	          while ((line = bufferReader.readLine()) != null)   {
	            System.out.println(line);
	          }
	          //Close the buffer reader
	          bufferReader.close();
	          
	       }catch(Exception e){
	          System.out.println("Error while reading file line by line:" + e.getMessage());                      
	       }
	}
	
}
