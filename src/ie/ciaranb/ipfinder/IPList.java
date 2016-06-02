package ie.ciaranb.ipfinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class IPList {
	
	/*
	 * IPList object information 
	 */
	private ArrayList<String> ipAddresses;//stores IP address read from file
	private String machineLocalIP;//returns executing machines IP
	
	/*
	 * Returns ArrayList of addresses
	 */
	public ArrayList<String> getIpAddresses() {
		return ipAddresses;
	}
	
	/*
	 * Executes provided methods to retrieve the local IP address 
	 * and create, read and store information from a given file
	 */
	public IPList() {
		
		ipAddresses = new ArrayList<String>();
		
		try{
			getLocalAddress();
			readTextFile("IPList.txt");//hard-coded for convenience, can be altered
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Retrieves and prints out the executing machines local address and stores it
	 */
	private void getLocalAddress() throws Exception{

		System.out.println("Computer IPV4 IP: " + InetAddress.getLocalHost().getHostAddress()); //gets Local IP
			
		String url = "http://vallentinsource.com/globalip.php"; //Website that outputs local IPV4 address in correct format
		BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
		
		machineLocalIP = br.readLine();
		
		System.out.println("Current computer IP: " + machineLocalIP);
	}
	
	/*
	 * Loops through the text file and appends all IP address to the List object
	 */
    private void readTextFile(String file) throws FileNotFoundException {
		ipAddresses.add(0, machineLocalIP);//add local address in position 0
		
		Scanner s1 = new Scanner(new File(file));//read in the text file
		while(s1.hasNextLine()){
			ipAddresses.add(s1.nextLine());//append the address to the arraylist
		}
		
		s1.close();
    }

}
