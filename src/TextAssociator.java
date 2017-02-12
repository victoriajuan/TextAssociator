import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//smile sea the very thing is fun and games as far as jot down behavioral norm and comprehend dirty joke for signals structures

/* CSE 373 Starter Code
 * @Author Yiran Juan
 * 
 * TextAssociator represents a collection of associations between words.
 * See write-up for implementation details and hints
 * 
 */
public class TextAssociator {
	private WordInfoSeparateChain[] table;
	private int size;
	
	/* INNER CLASS
	 * Represents a separate chain in your implementation of your hashing
	 * A WordInfoSeparateChain is a list of WordInfo objects that have all
	 * been hashed to the same index of the TextAssociator
	 */
	private class WordInfoSeparateChain {
		private List<WordInfo> chain;
		
		/* Creates an empty WordInfoSeparateChain without any WordInfo
		 */
		public WordInfoSeparateChain() {
			this.chain = new ArrayList<WordInfo>();
		}
		
		/* Adds a WordInfo object to the SeparateCahin
		 * Returns true if the WordInfo was successfully added, false otherwise
		 */
		public boolean add(WordInfo wi) {
			if (!chain.contains(wi)) {
				chain.add(wi);
				return true;
			}
			return false;
		}
		
		/* Removes the given WordInfo object from the separate chain
		 * Returns true if the WordInfo was successfully removed, false otherwise
		 */
		public boolean remove(WordInfo wi) {
			if (chain.contains(wi)) {
				chain.remove(wi);
				return true;
			}
			return false;
		}
		
		// Returns the size of this separate chain
		public int size() {
			return chain.size();
		}
		
		// Returns the String representation of this separate chain
		public String toString() {
			return chain.toString();
		}
		
		// Returns the list of WordInfo objects in this chain
		public List<WordInfo> getElements() {
			return chain;
		}
	}
	
	
	/* Creates a new TextAssociator without any associations 
	 */
	public TextAssociator() {
		this.table = new WordInfoSeparateChain[3405];
		this.size = 0;
	}
	
	
	/* Adds a word with no associations to the TextAssociator 
	 * Returns False if this word is already contained in your TextAssociator ,
	 * Returns True if this word is successfully added
	 */
	public boolean addNewWord(String word) {
		int hashIndex = getHashCode(word);
		WordInfo newWord = new WordInfo(word);
		double loadFactor = (double)size / (double)table.length;
//		System.out.println(this.size);
		
		//if certain index contains no element, create a new WordInfoSeparateChain
		if(table[hashIndex] == null){
			table[hashIndex] = new WordInfoSeparateChain();
			//resize the table depending on the load factor
			if(loadFactor <= 0.75){
				table[hashIndex].add(newWord);
				size ++;
				return true;
			}else{
				resize();
				addNewWord(word); 
			}
		}
		return false;
	}
	
	
	/* Adds an association between the given words. Returns true if association correctly added, 
	 * returns false if first parameter does not already exist in the TextAssociator or if 
	 * the association between the two words already exists
	 */
	public boolean addAssociation(String word, String association) {
		int hashIndex = getHashCode(word);
		
		//if the word does exist in the table
		if(table[hashIndex] != null){
			for(WordInfo chainItem : table[hashIndex].getElements()){
				//find the specific word in the list of WordInfo
				if(chainItem.getWord().equals(word)){
					//if the association exist with the word, add association
					if(!chainItem.getAssociations().contains(association)){
						chainItem.addAssociation(association);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/* Remove the given word from the TextAssociator, returns false if word 
	 * was not contained, returns true if the word was successfully removed.
	 * Note that only a source word can be removed by this method, not an association.
	 */
	public boolean remove(String word) {
		int hashIndex = getHashCode(word);
		
		//if the word doesn't exist in the table
		if(table[hashIndex] == null){
			return false;
		}else {
			for(WordInfo chainItem : table[hashIndex].getElements()){
				//find the specific word in the list of WordInfo
				if(chainItem.getWord().equalsIgnoreCase(word)){
					//remove the whole WordInfo relates to the word 
					table[hashIndex].remove(chainItem);
					size--;
					return true;
				}
			}
		}
		return false;
	}
	
	
	/* Returns a set of all the words associated with the given String  
	 * Returns null if the given String does not exist in the TextAssociator
	 */
	public Set<String> getAssociations(String word) {
		int hashIndex = getHashCode(word);
		
		if(table[hashIndex] == null){
			return null;
		}else {
			//find certain word in the list of WordInfo object
			for(WordInfo chainItem : table[hashIndex].getElements()){
				if(chainItem.getWord().equalsIgnoreCase(word)){
					return chainItem.getAssociations();
				}
			}
			return null ;
		}
	}
	
	
	/* Prints the current associations between words being stored
	 * to System.out
	 */
	public void prettyPrint() {
		System.out.println("Current number of elements : " + size);
		System.out.println("Current table size: " + table.length);
		
		//Walk through every possible index in the table
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				WordInfoSeparateChain bucket = table[i];
				
				//For each separate chain, grab each individual WordInfo
				for (WordInfo curr : bucket.getElements()) {
					System.out.println("\tin table index, " + i + ": " + curr);
				}
			}
		}
		System.out.println();
	}
	
	/**
	 * hash function to a given word a hash code
	 * @param str
	 * 		string parameter to assign a hash code
	 * @return hashCode
	 * 		the assigned hash code
	 */
	private int getHashCode(String str){
		return (Math.abs(str.hashCode()) % table.length);
	}
	
	/**
	 * resize the TextAssociator table by doubling it
	 */
	private void resize(){
		//new table with double size of the original table
		WordInfoSeparateChain[] tempTable = new WordInfoSeparateChain[table.length * 2];
		WordInfoSeparateChain[] oldTable = table;
		table = tempTable;
		
		//first take each element in the table array
		for(WordInfoSeparateChain chain: oldTable){
			if(chain != null){
				//then take the list of WordInfo objects  from chain
				for(WordInfo chainItem: chain.getElements()){
					//new hashIndex of the copying element in the new table
					int tempHashIndex = getHashCode(chainItem.getWord());
					
					//copying elements to the new table
					if(table[tempHashIndex] == null){
						table[tempHashIndex] = new WordInfoSeparateChain();
					}
					table[tempHashIndex].add(chainItem);
				}
			}
		}
		//update the table
		//table = tempTable;
	}
}