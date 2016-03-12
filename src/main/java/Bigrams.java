import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Bigrams extends AlphaBet {

    private String text;
    private Map<String, Double> bigrams;

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
                        if (set.contains(temp.charAt(i))) {
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
        printBigrams();
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
        for (int i = 0; i < text.length(); i = i + 2) {
            StringBuilder temp = new StringBuilder();
            temp.append(text.charAt(i));
            temp.append(text.charAt(i + 1));
            bigrams.put(temp.toString(), bigrams.get(temp.toString()) + 1.);
        }
    }

    public Map<String, Double> findMostFrequentBigrams() {
        Map<String, Double> result = new HashMap<String, Double>();

        List list = new ArrayList(bigrams.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (int) (o2.getValue() - o1.getValue());
            }
        });
        System.out.println(list);
        for (int i = 0; i < 5; i++) {
            System.out.println(list.get(i));
//            result.put(list.get(i));
        }


        return result;
    }

    public static void main(String[] args) {
        Bigrams bigrams = new Bigrams("text.txt", false);
//        Bigrams bigrams = new Bigrams("text.txt", true);
        bigrams.print();
        for (Map.Entry<String, Double> pair : bigrams.findMostFrequentBigrams().entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

}
