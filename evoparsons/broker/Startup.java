package evoparsons.broker;

public class Startup {
				
	public static void main(String[] args) {				
		String providedConfig = System.getProperty("file");
		Log log = Log.console;
		Config.FromFile(null, providedConfig).init(null);
	}

}