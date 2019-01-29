package evoparsons.broker;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Startup {		
				
	public static void main(String[] args) {				
		String providedConfig = System.getProperty("file");
		Log log = Log.console;
		Config.FromFile(log, providedConfig).init(log, null);
	}

}