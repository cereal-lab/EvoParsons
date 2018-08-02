package evoparsons.broker;

import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ParsonsGenotypeIndex
{
	private static final String GENOTYPE_INDEX_FILE = "genotypeIndex.bro";
	private Log log;
	private static class HashRemapper implements Serializable {
		private static final long serialVersionUID = 1L;
		private int[] genome;
		public HashRemapper(int [] genome) { this.genome = genome; }
		public int[] getGenome() {
			return genome;
		}
		@Override
		public String toString() {
			return 
				Arrays.stream(genome)
					.mapToObj(i -> ((Integer)i).toString())
					.collect(Collectors.joining(","));
		}
		@Override
		public boolean equals(Object o) 
		{
			if (!(o instanceof HashRemapper)) return false;
			return Arrays.equals(((HashRemapper)o).getGenome(), this.getGenome());
		}
		
		@Override
		public int hashCode() 
		{
			return Arrays.hashCode(this.getGenome());
		}
	}		
	private Map<HashRemapper, Integer> genotypeIndexes;
	private String outputFolder;
	public ParsonsGenotypeIndex(Log log, Config config)
	{
		this.log = log;
		this.outputFolder = config.getOutputFolder();
		genotypeIndexes = 
			Utils.<Map<HashRemapper, Integer>>loadFromFile(log, Paths.get(outputFolder, GENOTYPE_INDEX_FILE).toString(), 
				HashMap<HashRemapper, Integer>::new);		
		if (genotypeIndexes.size() == 0)
			log.log("[ParsonsGenotypeIndex] genome hash to index map is empty");
		else 
		{
			log.log("[ParsonsGenotypeIndex] %d genomes were indexed. Restored from %s", genotypeIndexes.size(), GENOTYPE_INDEX_FILE );
			genotypeIndexes.entrySet().stream()
				.sorted(Comparator.comparing(entry -> entry.getValue()))
				.forEach(entry -> 
					log.log("\t%s\t\t%d", 
						new Object [] { entry.getKey().toString(), entry.getValue() }));
		}
		
	}

	public int remapGenotype(int[] genome) {
		HashRemapper genomeKey = new HashRemapper(genome);
		if (genotypeIndexes.containsKey(genomeKey))
			return genotypeIndexes.get(genomeKey);
		else 
		{
			int index = genotypeIndexes.size();
			genotypeIndexes.put(genomeKey, index);
			return index;
		}		
	}	

	public void save() {
		Utils.saveToFile(log, genotypeIndexes, Paths.get(outputFolder, GENOTYPE_INDEX_FILE).toString());
	}

	public ParsonsGenotype.Pair createPair(int[] parent, int[] child) {
		ParsonsGenotype p = new ParsonsGenotype(this, parent, child, true);
		ParsonsGenotype c = new ParsonsGenotype(this, child, parent, false);
		return new ParsonsGenotype.Pair(p, c);
	}	
	

}
