import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamUtils {
    /**
     * @param strings: the input collection of <code>String</code>s.
     * @return a collection of <code>String</code>s that start with a capital letter.
     */
    public static Collection<String> capitalized(Collection<String> strings) {
        return strings.stream()
                .filter(p -> Character.isUpperCase(p.charAt(0))).collect(Collectors.toList());
    }


    /**
     * Find and return the longest <code>String</code> in a given collection of <code>String</code>s.
     *
     * @param strings: the given collection of <code>String</code>s.
     * @param from_start: a <code>boolean</code> flag that decides how ties are broken.
    If <code>true</code>, then the element encountered earlier in
     * the iteration is returned, otherwise the later element is returned.
     * @return the longest <code>String</code> in the given collection,
     * where ties are broken based on <code>from_start</code>.
     */
    public static String longest(Collection<String> strings, boolean from_start){



        return strings.stream()
                .reduce((a,b)->{
                    if(a.length() == b.length()) {
                        if (from_start) {
                            return a;
                        }
                        return b; // will be "a" for next reduction
                    } else if (a.length() < b.length()) {
                        return b;
                    }
                    return a;
                }).orElse(null);
    }
    /**
     * Find and return the least element from a collection of given elements that are comparable.
     *
     * @param items: the given collection of elements
     * @param from_start: a <code>boolean</code> flag that decides how ties are broken.
     * If <code>true</code>, the element encountered earlier in the
     * iteration is returned, otherwise the later element is returned.
     * @param <T>: the type parameter of the collection (i.e., the items are all of type T).
     * @return the least element in <code>items</code>, where ties are
     * broken based on <code>from_start</code>.
     */
    public static <T extends Comparable<T>> T least(Collection<T> items, boolean from_start){
        return items.stream()
                .reduce((a,b)->{
                    if(a.compareTo(b)==0) {
                        if (from_start) {
                            return a;
                        }
                        return b; // will be "a" for next reduction
                    } else if (a.compareTo(b)>0) {
                        return b;
                    }
                    return a;
                }).orElse(null);
    }

    /**
     * Flattens a map to a stream of <code>String</code>s, where each element in the list
     * is formatted as "key -> value".
     *
     * @param aMap the specified input map.
     * @param <K> the type parameter of keys in <code>aMap</code>.
     * @param <V> the type parameter of values in <code>aMap</code>.
     * @return the flattened list representation of <code>aMap</code>.
     */
    public static <K, V> List<String> flatten(Map<K, V> aMap) {
        return aMap.entrySet().stream().map(entryOfSet -> entryOfSet.getKey() + " -> " + entryOfSet.getValue()).collect(Collectors.toList());
    }

    /*
    public static int addAll(Collection<Integer> lst){
        return lst.stream().reduce(0,(a,b) -> a+b);

    }
    public static Collection<Integer>  exponentOnly(Collection<Integer> lst){
        return lst.stream().filter(p-> Math.log(p)/Math.log(2) == Math.round(Math.log(p)/Math.log(2))).collect(Collectors.toList());
    }
    public static Collection<String> fileWords(Path pathFile) throws IOException {
        Stream<String> lines = Files.lines(pathFile, StandardCharsets.UTF_8);
        Stream<String> words = lines.flatMap(line -> Stream.of(line.split(" ")));
        return words.collect(Collectors.toList());
    }

     */
    public static Collection<String> capitalizationFilter(Collection<String> strings) {
        return strings.stream().filter(Objects::nonNull)
                .filter(s -> s.charAt(0) > 64 && s.charAt(0) < 94)
                .collect(Collectors.toList());
    }



    public static void main(String[] args){




        /*
        double power = Math.log(4)/Math.log(2);
        System.out.println(power == Math.round(power));
        ArrayList<Integer> test = new ArrayList();
        test.add(1);
        test.add(2);
        System.out.println(addAll(test));

         */



        ArrayList<String> strings = new ArrayList();
        System.out.println(longest(strings, true));
        strings.add("Test");
        strings.add("One");
        strings.add("hi");
        strings.add("Duck");
        strings.add("boot");
        System.out.println(capitalized(strings));
        System.out.println(longest(strings, true));

/*
        ArrayList<Student> students = new ArrayList();
        students.add(new Student("Kevin", 1));
        students.add(new Student("John", 2));
        students.add(new Student("Ernest", 0));

        System.out.println(least(students, true).getName());

 */




    System.out.println("LEAST");
        ArrayList<String> studentList = new ArrayList();
        System.out.println(least(studentList,false));
        studentList.add("Ernest");
        studentList.add("Kevin");
        studentList.add("Rick");
        studentList.add("Jimmy");
        studentList.add("Will");
        studentList.add("Angelica");
        studentList.add("Aircus");
        System.out.println(least(studentList,false));

        HashMap<Integer, String> hmap = new HashMap();

        /*Adding elements to HashMap*/
        hmap.put(12, "Chatterbox");
        hmap.put(2, "Rakan");
        hmap.put(7, "Singed");
        hmap.put(30, "Adjest");
        hmap.put(3, "Anubis");

        List<String> mapResult = flatten(hmap);
        for (String print:mapResult) {
            System.out.println(print);
        }


    }


}
