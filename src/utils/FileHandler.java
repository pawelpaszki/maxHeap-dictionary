package utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.introcs.In;

/**
 * @author Pawel Paszki
 * @version 06/04/2016
 *         This util class is used to read / write spanish and english words
 *         from and to text file
 */

public class FileHandler {

	/**
	 * 
	 * @param filename
	 *            specifies the relative path of the file to be read in.
	 * @return List of words read in from file
	 * @throws Exception
	 */
	public List<String> readFile(String filename) throws Exception {
		File dictionaryFile = new File(filename);
		if (dictionaryFile.exists()) {
			List<String> words = new ArrayList<String>();
			In inWords = new In(dictionaryFile);
			// each field is separated(delimited) by a '|'
			String delims = "[\t]";
			while (!inWords.isEmpty()) {
				String wordPair = inWords.readLine();

				// split into two words
				String[] wordTokens = wordPair.split(delims);
				if (wordTokens.length == 2) {
					words.add(wordTokens[0]);
					words.add(wordTokens[1]);
				}
			}
			return words;
		} else {
			return null;
		}
	}

	/**
	 * Save all word pairs to file
	 * @param filename specifies the path of the text file, which
	 * the words will be written to
	 */
	public void writeToFile(String filename, ArrayList<String> wordPairs) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(filename);
			for (int i = 0; i < wordPairs.size();) {
				out.println(wordPairs.get(i++) + "\t" + wordPairs.get(i++));
			}
			out.close();
		} catch (Exception e) {
			System.out.println("file has not been saved");
		}
	}
}
