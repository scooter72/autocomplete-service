package server.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of the Dictionary interface,
 * loads the dictionary from a file on the file system.
 */
public class FileDictionary implements Dictionary {
    private String[] words;
    private static final String DICTIONARY_TXT = "/dictionary.txt";

    public FileDictionary() {
        this(DICTIONARY_TXT);
    }

    public FileDictionary(String fileName) {
        words = loadDictionary(fileName);
    }

    private String[] loadDictionary(String fileName) {
        List<String> wordsList = new ArrayList<>();
        InputStreamReader inputStreamReader = null;

        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
            inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                wordsList.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    /* ignore */
                }
            }
        }

        return wordsList.stream()
                .sorted()
                .toArray(String[]::new);
    }

    public String[] getWords() {
        if (words == null) {
            words= loadDictionary(DICTIONARY_TXT);
        }
        return words;
    }
}
