package Homework5;

import java.io.*;
import java.util.Comparator;
import java.util.Scanner;

import ch07.trees.BinarySearchTree;
import support.WordFreq;

public class Ngram {
	public static Comparator<WordFreq> gramComparator() {
		return new Comparator<WordFreq>() {
			public int compare(WordFreq one, WordFreq two) {
				if(two.getFreq()!=one.getFreq()) {
					return (two.getFreq()-one.getFreq());}
				else
					return (one.compareTo(two));
			}
		};
	}
	
	public static void main(String[] args) throws IOException {
		Scanner wordsIn = new Scanner(new FileReader("src/Homework5/TheAdventuresOfSherlockHolmes.txt"));
		wordsIn.useDelimiter("[^a-zA-Z]+"); // one or more delimiters are nonletters,'
//		wordsIn.useDelimiter("[\\s?':;,.]+"); 
		
		WordFreq nGramToTry;
	    WordFreq nGramInTree;
		
	    Comparator<WordFreq> comp = gramComparator();
		BinarySearchTree<WordFreq> tree = new BinarySearchTree<WordFreq>();
		BinarySearchTree<WordFreq> treeplus = new BinarySearchTree<WordFreq>();
		SortedABList<WordFreq> list = new SortedABList<WordFreq>(comp);
		
		
		Scanner scan = new Scanner(System.in);
		Scanner s = new Scanner(System.in).useDelimiter("\\n");

		// Reading all words from file into ABList
		ABList<Object> wordList = new ABList<Object>();

		while (wordsIn.hasNext()) {
			wordList.add(wordsIn.next());
		}

		System.out.print("n-gram length> ");
		int n = scan.nextInt();
		int b = n+1;
	    System.out.print("Minimum n-gram frequency> ");
	    int minFreq = scan.nextInt();

		// add n-grams of the text file to binary search tree
		for (int i=0; i<wordList.size()-n+1; i++) {
			String nGram ="";
			for (int j = 0; j<n; j++) {
				nGram += wordList.get(i + j)+" ";
			}
			nGram = nGram.toLowerCase();
			nGramToTry = new WordFreq(nGram);
			nGramInTree = tree.get(nGramToTry);
			if (nGramInTree != null)
	        {
	          // word already in tree, just increment frequency
	          nGramInTree.inc();
	        }
			else
	        {
	          // insert new word into tree
	          nGramToTry.inc();               // set frequency to 1
	          tree.add(nGramToTry);
	        }
		}
		
	    System.out.println("the "+n+"-grams with frequency counts of " + minFreq + " and above:");
	    System.out.println();
	    System.out.println("Freq  Word");
	    System.out.println("----- -----------------");
	    for (WordFreq nGramFromTree: tree)
	    {
	      if (nGramFromTree.getFreq() >= minFreq)
	      {
	        System.out.println(nGramFromTree);
	      }
	    }
	    System.out.println();
	    
	    for (int i=0; i<wordList.size()-b+1; i++) {
			String nGram ="";
			for (int j = 0; j<b; j++) {
				nGram += wordList.get(i + j)+" ";
			}
			nGram = nGram.toLowerCase();
			nGramToTry = new WordFreq(nGram);
			nGramInTree = treeplus.get(nGramToTry);
			if (nGramInTree != null)
	        {
	          // word already in tree, just increment frequency
	          nGramInTree.inc();
	        }
			else
	        {
	          // insert new word into tree
	          nGramToTry.inc();               // set frequency to 1
	          treeplus.add(nGramToTry);
	        }
		}
	    
    	System.out.println("Enter 4 words (X to stop)");
    	String search = s.next().replaceAll("\\r", " ");
    	nGramToTry = new WordFreq(search);	
    	nGramInTree = tree.get(nGramToTry);
    	
    	if(nGramInTree == null)
    		System.out.println("n-gram not in document.");
    	
    	for (WordFreq nGramFromTree: treeplus)
	    {
	      if (nGramFromTree.getWordIs().startsWith(search))
	      {
	        list.add(nGramFromTree);
	      }
	    }
    	
	    System.out.println(list.size());
	    for(WordFreq nGramFromList:list) {
	    	double percentage = 100*(double)nGramFromList.getFreq()/(double)nGramInTree.getFreq();
	    	System.out.print(String.format("%.2f", percentage)+"% ");
	    	System.out.println(nGramFromList);
	    }
		
	    scan.close();
	    s.close();
}
}