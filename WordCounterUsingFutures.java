import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.lang.Character;
import java.util.concurrent.*;

public class WordCounter {

    // The following are the ONLY variables we will modify for grading.
    // The rest of your code must run with no changes.
    public static final Path FOLDER_OF_TEXT_FILES  = Paths.get("C:\\Users\\Sung Mo Yang\\Desktop\\hw5"); // path to the folder where input text files are located
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get("C:\\Users\\Sung Mo Yang\\Desktop\\hw5output\\output.txt"); // path to the output plain-text (.txt) file
    public static final int  NUMBER_OF_THREADS     = 1;                // max. number of threads to spawn

    public static TreeMap<String, Integer> readFileToDict(File currentFile) throws FileNotFoundException {
        TreeMap<String, Integer> returnTreeMap = new TreeMap<>();

        FileReader reader = new FileReader(currentFile);
        int currentASCII = -1;
        StringBuilder currentLine = new StringBuilder();

        while (true) {
            try {
                if ((currentASCII = reader.read()) == -1) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Character.isLetter(((char) currentASCII))) {
                currentLine.append(Character.toLowerCase((char) currentASCII));
            } else if (Character.isWhitespace(((char) currentASCII))) {
                currentLine.append(Character.toLowerCase((char) currentASCII));
            }
        }

        String currentLineEditString = currentLine.toString();

        if (currentLineEditString.length() > 0) {
            while (!Character.isLetter(currentLineEditString.charAt(currentLineEditString.length() - 1))) {
                currentLineEditString = currentLineEditString.substring(0, currentLineEditString.length() - 2);
            }
        } else if (currentLineEditString.length() == 0) {
            return returnTreeMap;
        }

        String[] words = currentLineEditString.split(" ");

        for (String word : words) {
            if (returnTreeMap.containsKey(word)) {
                returnTreeMap.put(word, returnTreeMap.get(word) + 1);
            } else {
                returnTreeMap.put(word, 1);
            }
        }
        return returnTreeMap;
    }

    public static TreeMap<String, Integer> combineMaps(TreeMap<File, TreeMap<String, Integer>> allMaps) {
        TreeMap<String, Integer> returnTreeMap = new TreeMap<>();
        for (TreeMap<String, Integer> map : allMaps.values()) {

            for (String key : map.keySet()) {
                if (returnTreeMap.containsKey(key)) {
                    returnTreeMap.put(key, returnTreeMap.get(key) + map.get(key));
                } else {
                    returnTreeMap.put(key, map.get(key));
                }
            }

        }
        return returnTreeMap;
    }

    public static void createOutputFile(TreeMap<String, Integer> wordFrequency, TreeMap<File, TreeMap<String, Integer>> fileToTree) throws IOException {
        int longestLength = 0;
        String longestKey;
        for (String key : wordFrequency.keySet()) {
            if (key.length() > longestLength) {
                longestKey = key;
                longestLength = longestKey.length();
            }
        }
        longestLength++;

        FileWriter writing = new FileWriter(WORD_COUNT_TABLE_FILE.toFile());

        writing.write(String.format("%-" + longestLength + "s", ""));
        for (File f : fileToTree.keySet()) {
            writing.write(String.format("%-" + f.getName().length() + "s", f.getName().substring(0, f.getName().length() - 4)));
        }
        writing.write("total \n");

        Set<String> words = wordFrequency.keySet();
        Iterator<String> iterator = words.iterator();

        while (iterator.hasNext()) {
            String word = iterator.next();
            writing.write(String.format("%-" + longestLength + "s", word));
            for (File f : fileToTree.keySet()) {
                if (fileToTree.get(f).containsKey(word)) {
                    writing.write(String.format("%-" + f.getName().length() + "s", fileToTree.get(f).get(word)));
                } else {
                    writing.write(String.format("%-" + f.getName().length() + "s", 0));
                }
            }
            writing.write(String.valueOf(wordFrequency.get(word)));

            if (iterator.hasNext()) {
                writing.write(" \n");
            }
        }
        writing.close();
    }

    public static void main(String... args) throws IOException {
        // your implementation of how to run the WordCounter as a stand-alone multi-threaded program
        TreeMap<File, TreeMap<String, Integer>> allFiles = new TreeMap<>();
        File inputDirectory = FOLDER_OF_TEXT_FILES.toFile();

        if (inputDirectory.listFiles() == null) {
            throw new FileNotFoundException("File(s) cannot be found");
        }
        List<File> inputFiles = Arrays.asList(inputDirectory.listFiles());
        if (inputFiles.size() == 0) {
            throw new FileNotFoundException("File(s) cannot be found");
        }

        /**
         * remove this and replace with multithreading

        for (File f: inputFiles) {
            allFiles.put(f, readFileToDict(f));
        }
        TreeMap<String, Integer> allWords = combineMaps(allFiles);
        createOutputFile(allWords, allFiles);
        */

        List<Object> futes = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        for (File f : inputFiles) {
            Future future = executorService.submit(new multiThreadRead(f, allFiles));

            try {
                futes.add(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (futes.size() == inputFiles.size()) {
            executorService.shutdownNow();
        }

        TreeMap<String, Integer> allWords = combineMaps(allFiles);
        createOutputFile(allWords, allFiles);
    }

    public static class multiThreadRead implements Runnable {
        private File file;
        private TreeMap<File, TreeMap<String, Integer>> globalMap;

        public multiThreadRead(File file, TreeMap<File, TreeMap<String, Integer>> globalMap) {
            this.file = file;
            this.globalMap = globalMap;
        }
        @Override
        public void run() {
            TreeMap<String, Integer> frequencies = new TreeMap<>();
            try {
                frequencies = (readFileToDict(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            globalMap.put(file, frequencies);
        }
    }
}
