package evoparsons.broker;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
// imports for collections
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParsonsLibrary
{
	// ---------------------------------------------------------------------------------
	// PRIVATE methods & attributes to manage programs & transforms
	// ---------------------------------------------------------------------------------

	/**
	 * This is where we keep all the {@link Program} objects representing the
	 * programs available in the broker's folder
	 **/
	private List<Program> programStore;
	private List<Transform> transformStore;
	private Log log;

	/**
	 * Building a new instance
	 **/
	public ParsonsLibrary(Log log, Config config)
	{
		this.log = log;
		programStore = 
			load(config.getProgramsFolder(), 
				new String[] { Program.LANG, Program.TITLE, Program.DESC, Program.SOURCE },
				(fileName, groupedLines) -> 
					new Program(
						fileName,
						String.join(System.lineSeparator(), groupedLines.get(Program.LANG)),
						String.join(System.lineSeparator(), groupedLines.get(Program.TITLE)),
						String.join(System.lineSeparator(), groupedLines.get(Program.DESC)),
						groupedLines.get(Program.SOURCE))
				);
		log.log("[ParsonsLibrary] %d programs loaded to memory:%n%s", 
			new Object [] {
				programStore.size(),
				IntStream.range(0, programStore.size())
					.mapToObj(i -> String.format("\t%d\t%s", i, programStore.get(i).fileName))
					.collect(Collectors.joining(System.lineSeparator()))});
		transformStore = 
			loadTransforms(config.getTransformsFolder(), 
				new String[] { Transform.TITLE, Transform.DESC, Transform.SELECT, Transform.TRANSFORM },
				(fileName, groupedLines) -> 
					new Transform(
						fileName,
						groupedLines.get(Transform.TITLE),
						groupedLines.get(Transform.DESC),
						groupedLines.get(Transform.SELECT),
						groupedLines.get(Transform.TRANSFORM))
				);			
		log.log("[ParsonsLibrary] %d transforms loaded to memory:%n%s", 
			new Object[] 
			{
				transformStore.size(),
				IntStream.range(0, transformStore.size())
					//.mapToObj(i -> String.format("\t%d\t%s\t%s", i, transformStore.get(i).getFileName(), transformStore.get(i).getTitle()))
					.mapToObj(i -> String.format("\t%d\t%s", i, transformStore.get(i).getFileName()))
					.collect(Collectors.joining(System.lineSeparator()))
				
			});		
	}
	
	public int getProgramCount() { return programStore.size(); }
	public int getTransformCount() { return transformStore.size(); }

	public Program getProgram(int ProgNum)
	{
		//log(String.format("Requesting ProgNum = %d", ProgNum));
		return programStore.get(ProgNum);
	}

	public Stream<Program> programStream() {
		return programStore.stream();
	}

	public Stream<Transform> transformStream() {
		return transformStore.stream();
	}

	public Transform getTransform(int TransNum)
	{
		//log(String.format("Requesting TransNum = %d (should be between 0 and %d)", TransNum, transformStore.size()));
		return transformStore.get(TransNum);
	}

	//TODO: nice to have common format - line JSON, XML, etc	
	private <C> List<C> load(String folder, String[] sections, BiFunction<String, Map<String, List<String>>, C> mapper)
	{
		class LinesCollector {
			public int sectionIndex;
			public List<String> lines;
			public Map<String, List<String>> collected;
			public String[] sections;
			public LinesCollector(String[] sections) {
				this.sections = sections;
				sectionIndex = 0;
				lines = new ArrayList<>();
				collected = new HashMap<>();
			}
		}

		try
		{
		return
			Arrays.stream( new File(folder).listFiles())
				.filter(f -> f.isFile() && f.getName().endsWith(".bro"))
				.sorted(Comparator.comparing(f -> f.getName()))
				.map(file -> {		
					try {													
					Map<String, List<String>> groupedLines = 
						Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)
							.stream()
							.collect(
								() -> new LinesCollector(sections),
								(acc, line) -> {
									if ((acc.sectionIndex < acc.sections.length) && line.contains(acc.sections[acc.sectionIndex]))
									{
										acc.lines = new ArrayList<>();
										acc.collected.put(acc.sections[acc.sectionIndex], acc.lines);
										acc.sectionIndex = acc.sectionIndex + 1;										
									} else {
										String trimmedLine = line.trim();
										if (!trimmedLine.isEmpty()) acc.lines.add(trimmedLine);
									}
								},
								(acc1, acc2) -> {})
							.collected; //single threaded, ordered
					return mapper.apply(file.getName(), groupedLines);
					}
					catch (IOException ex)
					{
						log.err("[ParsonsLibrary.load] Error parsing %s", file.getName());
						ex.printStackTrace();
						return null;
					}											
				})
				.filter(program -> program != null)
				.collect(Collectors.toList());
		} catch (NullPointerException e) {
			log.err("[ParsonsLibrary.load] Folder %s was not found%nCWD: %s", folder, System.getProperty("user.dir"));
			System.exit(1);
			return null;
		}
	}

	/**
	 * Load all the transforms stored as files in the folder specified as
	 * parameter into the {@link transformStore} attribute.
	 * 
	 * @return number of transforms loaded
	 **/
	private <T> List<T> loadTransforms(String folder, String[] sections, BiFunction<String, Map<String, String>, T> mapper)
	{
		try {
		return 
			Arrays.stream(new File(folder).listFiles())
				.filter(f -> f.isFile() && f.getName().endsWith(".bro"))
				.sorted(Comparator.comparing(f -> f.getName()))
				.map(file -> {		
					try {
						String fileContent = String.join(System.lineSeparator(), Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
						Map<String, String> elements = 
							Arrays.stream(sections)
								.collect(
									HashMap<String, String>::new,
									(acc, key) -> {
										String keyLookup = "^" + key + "=(.*)$";
										Pattern pattern = Pattern.compile(keyLookup, Pattern.MULTILINE);
										Matcher matcher = pattern.matcher(fileContent);
										if (matcher != null && matcher.find())
										{
											acc.put(key, matcher.group(1));
										}											
									},
									(acc1, acc2) -> {}
								);
						return mapper.apply(file.getName(), elements);
					}
					catch (IOException ex)
					{
						log.err("[ParsonsLibrary.loadTransforms] Error parsing %s", file.getName());
						ex.printStackTrace();
						return null;
					}							
				})
				.filter(program -> program != null)
				.collect(Collectors.toList());
		} catch (NullPointerException e) {
			log.err("[ParsonsLibrary.loadTransforms] Folder %s was not found%nCWD: %s", folder, System.getProperty("user.dir"));
			System.exit(1);
			return null;
		}
	}
}
