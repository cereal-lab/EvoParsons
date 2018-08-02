package evoparsons.broker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public final class Utils {
    @SuppressWarnings("unchecked")
	public static <T> T loadFromFile(Log log, String path, Supplier<T> defaultBuilder)
	{
		try (
			FileInputStream studentsIn = new FileInputStream(path);
			ObjectInputStream studentObjectIn = new ObjectInputStream(studentsIn))
		{									
			return (T)studentObjectIn.readObject();
		}
		catch (FileNotFoundException e) {
			log.log("[Utils.loadFromFile] %s was not found", path);	
			return defaultBuilder.get();
		}
		catch (Exception ex)
		{
			log.err("[Utils.loadFromFile] Error reading %s: %s", path, ex.getMessage());
			System.exit(1);
			return null;
		}
    }	
    
	public static void saveToFile(Log log, Object state, String path) {
		//Path filePath = Paths.get(outputFolder, fileName);
		try (
			FileOutputStream out = new FileOutputStream(path);
			ObjectOutputStream objectOut = new ObjectOutputStream(out);
		)
		{			
			objectOut.writeObject(state);
			log.log("[Utils.saveToFile] %s saved", path);
		}
		catch (IOException e)
		{
            log.err("[Utils.saveToFile] %s save failed", path);
			e.printStackTrace();
		}		
	}    

	private static Random rnd = new Random();

	public static <A, B> Optional<B> randFromMap(Map<A, B> mp)
	{
		return 
			mp.entrySet()
				.stream()
				.skip(rnd.nextInt(Math.max(1, mp.size())))
				.findFirst()
				.map(e -> e.getValue());
	}
}