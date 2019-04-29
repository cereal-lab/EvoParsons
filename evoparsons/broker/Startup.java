package evoparsons.broker;

public class Startup {
				
	public static void main(String[] args) {				
		String connectionString = System.getProperty("db");
		String providedConfig = System.getProperty("file");		
		if (connectionString == null)
			Config.FromFile(null, providedConfig).init(null);
		else {			
			Config.FromDB(null, connectionString, providedConfig).init(null);
		}
	}

}