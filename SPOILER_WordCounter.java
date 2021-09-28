import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WordCounter {

    // The following are the ONLY variables we will modify for grading.
    // The rest of your code must run with no changes.
    public static final Path FOLDER_OF_TEXT_FILES = Paths.get("C:\\Users\\shiro\\Downloads\\FIles for Downloads\\CSE 216\\cse216hw4\\WordCounterFiles"); // path to the folder where input text files are located
    public static final Path WORD_COUNT_TABLE_FILE = Paths.get("C:\\Users\\shiro\\Downloads\\FIles for Downloads\\CSE 216\\cse216hw4\\outputFile\\output.txt"); // path to the output plain-text (.txt) file
    public static final int NUMBER_OF_THREADS = 1;                // max. number of threads to spawn

    public static class FileParser implements Runnable {


        private HashMap<String, HashMap<String, Integer>> fileHashMaps;
        private File content;


        public FileParser(HashMap<String, HashMap<String, Integer>> fileHashMaps, File content) {
            this.fileHashMaps = fileHashMaps;
            this.content = content;
        }


        @Override
        public void run() {
            HashMap<String, Integer> frequencies = (parseFile(content));
            fileHashMaps.put(content.getName(),frequencies);


        }
    }



    public static void main(String... args) {
        // your implementation of how to run the WordCounter as a stand-alone multi-threaded program
        //System.out.println(System.getProperty("user.dir"));
        File directory = FOLDER_OF_TEXT_FILES.toFile();
        File[] contents = directory.listFiles();

        ArrayList<String> fileName = new ArrayList();
        ArrayList<Thread> threadsRunning = new ArrayList();
        HashMap<String, HashMap<String,Integer>> fileHashMaps = new HashMap();

        int numberOfFiles = 0;
        if (contents!=null){
            numberOfFiles = contents.length;
        }
        int fileIndex = 0;
        int numberOfThreadsCounter = NUMBER_OF_THREADS;
        int numberOfIterations = numberOfFiles / numberOfThreadsCounter;


        long start = System.currentTimeMillis();
        if (numberOfThreadsCounter>1) {

        for (int i = 0; i < numberOfIterations; i++) {

            while (fileIndex < numberOfFiles && numberOfThreadsCounter > 0) {

                Thread runner1 = new Thread(new FileParser(fileHashMaps, contents[fileIndex]));
                threadsRunning.add(runner1);

                threadsRunning.get(fileIndex).start();
                fileIndex++;
                numberOfThreadsCounter--;
            }
            numberOfThreadsCounter = NUMBER_OF_THREADS;

            try {
                for (Thread thread : threadsRunning) {
                    thread.join();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadsRunning.clear();
        int index = 0;
        for (int i = fileIndex; i < numberOfFiles; i++) {
            Thread runner1 = new Thread(new FileParser(fileHashMaps, contents[i]));
            threadsRunning.add(runner1);
            threadsRunning.get(index).start();
            index++;
        }
        try {
            for (Thread thread : threadsRunning) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
        else {
            for (File content : contents) {
                fileHashMaps.put(content.getName(), parseFile(content));
            }
        }

        List<String> uniqueWords = combineMapWords(fileHashMaps);

        for (File content : contents) {
            fileName.add(content.getName());
        }
        writeToFile(WORD_COUNT_TABLE_FILE, fileHashMaps, uniqueWords, fileName);
        long end = System.currentTimeMillis();

        System.out.println(end - start + " miliseconds");


    }

    public static void writeToFile(Path outputPath, HashMap<String, HashMap<String,Integer>> fileHashMaps, List<String> uniqueWords, ArrayList<String> fileName) {

        int maxWord = findMax(uniqueWords.stream()
                .map(String::length).collect(Collectors.toList())) + 1;
        try {
            FileWriter writerFile = new FileWriter(outputPath.toFile());
            writerFile.write(String.format("%-" + maxWord + "s", ""));

            int spacing = fileName.stream()
                    .map(String::length)
                    .reduce((a, b) -> a >= b ? a : b).orElse(10);
            for (String s : fileName) {
                String printOut = String.format("%-" + spacing + "s", s.substring(0, s.length() - 4));
                writerFile.write(printOut);

            }
            String printTotal = String.format("%-" + spacing + "s", "total");
            writerFile.write(printTotal);


            writerFile.write("\n");
            for (String uniqueWord : uniqueWords) {
                writerFile.write(String.format("%-" + maxWord + "s", uniqueWord));
                int total = 0;
                for (String s : fileName) {
                    int value = 0;
                    if (fileHashMaps.get(s).containsKey(uniqueWord)) {
                        value = fileHashMaps.get(s).get(uniqueWord);
                    }
                    String printOut = String.format("%-" + spacing + "d", value);
                    writerFile.write(printOut);
                    total += value;

                }
                String totalValue = String.format("%-" + spacing + "d", total);
                writerFile.write(totalValue);

                writerFile.write("\n");



            }
            writerFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<String> combineMapWords(HashMap<String, HashMap<String,Integer>> listMaps) {
        ArrayList<String> result = new ArrayList();
        List<HashMap<String,Integer>> arrHashMaps = new ArrayList<>(listMaps.values());
        for (HashMap<String, Integer> arrHashMap : arrHashMaps) {
            result.addAll(arrHashMap.entrySet().stream()
                    .map(entryOfSet -> entryOfSet.getKey()).collect(Collectors.toList()));
        }

        return result.stream().distinct().sorted().collect(Collectors.toList());
    }

    public static HashMap<String, Integer> parseFile(File file) {
        ArrayList<String> words = new ArrayList();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {

                //changes the line into words in an array
                String[] arr = st.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");

                //iterates through the array
                for (String word : arr) {

                    //to prevent counting empty lines
                    if (word.length() > 0) {
                        words.add(word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // key is the word
        // value is 1
        HashMap<String, Integer> frequencyMap = new HashMap();

        for (String s : words) {
            frequencyMap.merge(s, 1, Integer::sum);
        }


        return frequencyMap;
    }


    public static int findMax(List<Integer> maxArr) {
        return maxArr.stream()
                .reduce((a, b) -> a < b ? b : a)
                .orElse(0);
    }
/*
    public static <K, V> List<String> flatten(Map<K, V> aMap) {
        return aMap.entrySet().stream().map(entryOfSet -> entryOfSet.getKey() + " -> " + entryOfSet.getValue()).collect(Collectors.toList());
    }

 */


}