package stocks;


 /**
  * StockProject main class
  * @author ktong
  *
  */
public class Main {
	
	/**
	 * the api key
	 */
	public static String API_KEY = "e62aab1c906045bdb4eb84f23b6e3bbe";
   
	/**
	 * starts the program
	 * @param args				command line arguments
	 * @throws Exception		whenever exceptions are thrown within the StockReader class
	 */
    public static void main(String[] args) throws Exception {
    	StockReader.start();
    	
    }
}