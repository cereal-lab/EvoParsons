package evoparsons.broker;

import java.util.List;

/**
 * This class is used to store individual programs loaded from files, with each program
 * stored in one instance of the class.
 * @author paul
 *
 */
public class Program {
	public static final String LANG = "[#language#]";
	public static final String TITLE = "[#title#]";
	public static final String DESC = "[#description#]";
	public static final String SOURCE = "[#source#]";
	public final String language;
	public final String title;
	public final String description;
	public final List<String> body;
	public final String fileName;
	
	public Program(String fileName, String language, String title, String description, List<String> body) {
		this.language = language;
		this.fileName = fileName;
		this.title = title;
		this.description = description;
		this.body = body;
	}
}
