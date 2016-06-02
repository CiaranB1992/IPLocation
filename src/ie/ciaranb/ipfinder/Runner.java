package ie.ciaranb.ipfinder;

public class Runner {
	
	/*
	 * Serves as the executing class for this project
	 */
    public static void main(String[] args) {		
		try{
			new IPFinderURL();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

