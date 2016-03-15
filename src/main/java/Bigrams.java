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

    public static int getBigramValue(String bigram) {
        int result = 0;
        char c = bigram.charAt(0);
        result += ALPHABET.get(c) * ALPHABET.size();
        c = bigram.charAt(1);
        result += ALPHABET.get(c);
        return result;
    }

    public static String getBigramOnValue(int value) {
        StringBuilder result = new StringBuilder();
        result.append(base[value / base.length]);
        result.append(base[value % base.length]);
        return result.toString();
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
        ArrayList<String> bigramsCipherText = new ArrayList<String>();
        bigramsCipherText.add("ст");
        bigramsCipherText.add("но");
        bigramsCipherText.add("то");
        bigramsCipherText.add("на");
        bigramsCipherText.add("ен");

        Bigrams bigrams = new Bigrams("Base/text.txt", false);
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

        System.out.println("***********************************************************");

//        for (int i = 0; i < bigramsCipherText.size(); i++) {
//            for (int j = 0; j < bigramsCipherText.size(); j++) {
//                if (!bigramsCipherText.get(i).equals(bigramsCipherText.get(j))) {
//                    System.out.println(bigramsCipherText.get(i) + " " + bigramsCipherText.get(j));
//                }
//            }
//        }

        for (Map.Entry<String, Double> entry1 : bigrams.findMostFrequentBigrams(5).entrySet()) {
            for (Map.Entry<String, Double> entry2 : bigrams.findMostFrequentBigrams(5).entrySet()) {
                if (!entry1.getKey().equals(entry2.getKey())) {
                    for (int i = 0; i < bigramsCipherText.size(); i++) {
                        for (int j = 0; j < bigramsCipherText.size(); j++) {
                            if (!bigramsCipherText.get(i).equals(bigramsCipherText.get(j))) {
//                                System.out.println(bigramsCipherText.get(i) + " " + bigramsCipherText.get(j));
                                breaking(bigramsCipherText.get(i), bigramsCipherText.get(j), entry1.getKey(), entry2.getKey());
                            }
                        }
                    }
                }
            }
        }
    }

//    public void breaking(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
    public static void breaking(String x1, String x2, String y1, String y2) {
//        System.out.println("1: " + entry1.getKey() + " " + entry1.getValue());
//        System.out.println("2: " + entry2.getKey() + " " + entry2.getValue());

//        System.out.println("x1 = " + x1);
//        System.out.println("x2 = " + x2);
//        System.out.println("y1 = " + y1);
//        System.out.println("y2 = " + y2);
        for (Map.Entry<Integer, Integer> entry : Mathematics.calculateSystemComparisons(Bigrams.getBigramValue(x1),
        Bigrams.getBigramValue(x2), Bigrams.getBigramValue(y1), Bigrams.getBigramValue(y2), AlphaBet.base.length * AlphaBet.base.length).entrySet()) {
            Text text = new Text("Base/text.txt");
            System.out.println(entry.getKey() + " " + entry.getValue());
            System.out.println(text.decrypt(entry.getKey(), entry.getValue()));
        }
    }

    public static void printMap(Map<String, Double> map) {
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public static Map<String, Double>  check() {
        Bigrams bigrams = new Bigrams("Base/1.txt", true);
        bigrams.print();
        return bigrams.findMostFrequentBigrams(5);
    }

}
