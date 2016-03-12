import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Bigrams extends AlphaBet {

    private String text;
    private Map<String, Double> bigrams;
    private static final Map<Character, Integer> ALPHABET;

    static {
        ALPHABET = new HashMap<Character, Integer>();
        int count = 0;
        for (char c : base) {
            ALPHABET.put(c, count++);
        }
//        for (Map.Entry<Character, Integer> entry : ALPHABET.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
    }

    Bigrams(String fileName, boolean check) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder result = new StringBuilder();
            String temp;

            while ((temp = reader.readLine()) != null) {
                if (!check) {
                    result.append(temp);
                } else {
                    Set<Character> set = new HashSet<Character>();
                    for (char c : base) {
                        set.add(c);
                    }
                    for (int i = 0; i < temp.length(); i++) {
//                        if (set.contains(temp.charAt(i))) {
                        if (ALPHABET.containsKey(temp.charAt(i))) {
                            result.append(temp.charAt(i));
                        }
                    }
                }
            }


            reader.close();
            text = result.toString();

            bigrams = new HashMap<String, Double>();
            for (char c1 : base) {
                for (char c2 : base) {
                    StringBuilder s = new StringBuilder();
                    s.append(c1);
                    s.append(c2);
                    bigrams.put(s.toString(), 0.);
                }
            }

            calculateBigams();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        System.out.println("text: " +text);
        System.out.println(bigrams);
        System.out.println(bigrams.size());
//        printBigrams();
    }

    private int sum(Map<String, Double> map) {
        int s = 0;
        for (Map.Entry<String, Double> pair : map.entrySet()) {
            s += pair.getValue().intValue();
        }
        return s;
    }

    public void printBigrams() {
        int s = sum(bigrams);
        System.out.print("   ");
        for (char c : base) {
            System.out.print(c + "      ");
        }
        System.out.println();
        for (char c1 : base) {
            System.out.print(c1 + "  ");
            for (char c2 : base) {
                StringBuilder temp = new StringBuilder();
                temp.append(c1);
                temp.append(c2);
                Double result = bigrams.get(temp.toString());
                result /= s;
                System.out.print(String.format("%.4f", result) + " ");
            }
            System.out.println();
        }
    }

    public void calculateBigams() {
        for (int i = 0; i < text.length() - 1; i = i + 2) {
            StringBuilder temp = new StringBuilder();
            temp.append(text.charAt(i));
            temp.append(text.charAt(i + 1));
            bigrams.put(temp.toString(), bigrams.get(temp.toString()) + 1.);
        }
    }

    public Map<String, Double> findMostFrequentBigrams(int n) {
        Map<String, Double> result = new HashMap<String, Double>();

        List<Map.Entry<String, Double>> list = new ArrayList(bigrams.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (int) (o2.getValue() - o1.getValue());
            }
        });

        int count = 0;
        for (Map.Entry<String, Double> entry : list) {
            if (count != n) {
                result.put(entry.getKey(), entry.getValue());
                count++;
            }
        }
        return result;
    }

    public static int getBigramValue(Map.Entry<String, Double> entry) {
        int result = 0;
        char c = entry.getKey().charAt(0);
        result += ALPHABET.get(c) * ALPHABET.size();
        c = entry.getKey().charAt(1);
        result += ALPHABET.get(c);
        return result;
    }


    public static void main(String[] args) {
        ArrayList<String> b = new ArrayList<String>();
        b.add("ст");
        b.add("но");
        b.add("то");
        b.add("на");
        b.add("ен");

        Bigrams bigrams = new Bigrams("text.txt", false);
        bigrams.print();
        printMap(bigrams.findMostFrequentBigrams(5));
//        for (Map.Entry<String, Double> pair : bigrams.findMostFrequentBigrams(5).entrySet()) {
//            System.out.println(pair.getKey() + " " + pair.getValue());
//        }
        System.out.println("------------------------------");
        for (Map.Entry<String, Double> pair : check().entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
            System.out.println(getBigramValue(pair));
        }
    }

    public static void printMap(Map<String, Double> map) {
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public static Map<String, Double>  check() {
        Bigrams bigrams = new Bigrams("1.txt", true);
        bigrams.print();
        return bigrams.findMostFrequentBigrams(5);
    }

//    public calculateSystem()

}
