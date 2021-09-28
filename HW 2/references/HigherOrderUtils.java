import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;





public class HigherOrderUtils {
    interface NamedBiFunction<T,U,R> extends BiFunction<T,U,R>{
        String name();
    }
    //<Integer,Integer,Integer>
    //<Double,Double,Double>
    public static NamedBiFunction<Double,Double,Double> add = new NamedBiFunction<Double,Double,Double>() {


        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble+aDouble2;
        }

        public String name(){
            return "add";
        }



    };
    public static NamedBiFunction<Double,Double,Double> subtract = new NamedBiFunction<Double,Double,Double>() {


        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble-aDouble2;
        }

        public String name(){
            return "diff";
        }



    };
    public static NamedBiFunction<Double,Double,Double> multiply = new NamedBiFunction<Double,Double,Double>() {


        @Override
        public Double apply(Double aDouble, Double aDouble2) {
            return aDouble*aDouble2;
        }

        public String name(){
            return "mult";
        }



    };
    public static NamedBiFunction<Double,Double,Double> divide = new NamedBiFunction<Double,Double,Double>() {


        @Override
        public Double apply(Double aDouble, Double aDouble2) throws ArithmeticException{
            if (aDouble2==0){
                throw new ArithmeticException("Can't Divide by 0");
            }
            return aDouble/aDouble2;
        }

        public String name(){
            return "div";
        }



    };









    /**
     * Applies a given list of bifunctions -- functions that take two arguments of a certain type
     * and produce a single instance of that type -- to a list of arguments of that type. The
     * functions are applied in an iterative manner, and the result of each function is stored in
     * the list in an iterative manner as well, to be used by the next bifunction in the next
     * iteration. For example, given
     * List<Double> args = Arrays.asList(1d, 1d, 3d, 0d, 4d), and
     * List<NamedBiFunction<Double, Double, Double>> bfs = [add, multiply, add, divide],
     * <code>zip(args, bfs)</code> will proceed iteratively as follows:
     * - index 0: the result of add(1,1) is stored in args[1] to yield args = [1,2,3,0,4]
     * - index 1: the result of multiply(2,3) is stored in args[2] to yield args = [1,2,6,0,4]
     * - index 2: the result of add(6,0) is stored in args[3] to yield args = [1,2,6,6,4]
     * - index 3: the result of divide(6,4) is stored in args[4] to yield args = [1,2,6,6,1]
     *
     * @param args: the arguments over which <code>bifunctions</code> will be applied.
     * @param bifunctions: the list of bifunctions that will be applied on <code>args</code>.
     * @param <T>: the type parameter of the arguments (e.g., Integer, Double)
     * @return the item in the last index of <code>args</code>, which has the final
     * result of all the bifunctions being applied in sequence.
     */
    public static <T> T zip(List<T> args, List<NamedBiFunction<T, T, T>> bifunctions){
        int i = 0;
        while ((i+1)<args.size()) {
            args.set(i + 1, bifunctions.get(i % 4).apply(args.get(i), args.get(i + 1)));
            i++;
        }

        return args.get(i);
    }
    static Function<Character,String> charString = new Function<Character, String>() {
        @Override
        public String apply(Character c) {
            String s = "";
            int n = c-96;

            for(int i = 0; i<n;i++){
                s+= Character.toString(c);
            }
            return s;
        }
    };
    static Function<String,Integer> stringInteger = new Function<String, Integer>() {
        @Override
        public Integer apply(String s) {
            return s.length();
        }

    };
    public static class FunctionComposition {
        BiFunction<Function,Function, Function> composition = new BiFunction<Function,Function, Function>(){
            @Override
            public Function apply(Function function1, Function function2) {
                return function1.andThen(function2);
            }


        };
    }



    public static void main(String[] args){

        /*List<BiFunction<Double, Double, Double>> bfs = new ArrayList();

        bfs.add(add);
        bfs.add(multiply);
        bfs.add(add);
        bfs.add(divide);

         */


        FunctionComposition testComposition = new FunctionComposition();
        Function<Character,Integer> testFunc = testComposition.composition.apply(charString,stringInteger);
        System.out.println("testfunc return: ");
        System.out.println(testFunc.apply('z'));
        System.out.println("end");




        List<NamedBiFunction<Double, Double, Double>> bfs = Arrays.asList(add, multiply, add, divide);
        List<Double> arguments = Arrays.asList(1d, 1d, 3d, 0d, 4d,2d,2d);
        System.out.println("ZIP return:");
        System.out.println(zip(arguments, bfs));
        System.out.println("_______________");
        System.out.println(add.apply(multiply.apply(1.,2.), add.apply(2.0,3.0)));
        //Double ans = zip(arguments, bfs);
    }


}
