package MainPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class getCurrentIP {
	
	public static void main(String[] args) {
		try{
			System.out.println("Local IP: " + InetAddress.getLocalHost().getHostAddress()); //gets Local IP
			
		}catch (Exception ex){
			System.out.println("Error " + ex.getMessage());
		}
		
		try{
			String url = "http://vallentinsource.com/globalip.php"; //website that outputs global IP address
			BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			
			String receive = br.readLine();
			
			System.out.println("Global IP: " + receive);
			
		}catch (Exception ex){
			System.out.println("Error " + ex.getMessage());
		}
		
	}

}
