import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Text {

    private String text;

    public Text() {

    }

    public Text(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder result = new StringBuilder();
            String temp;
            while ((temp = reader.readLine()) != null) {
                result.append(temp);
            }
            reader.close();
            text = result.toString();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String decrypt(int a, int b) {

        a = a % (AlphaBet.base.length * AlphaBet.base.length);
        b = b % (AlphaBet.base.length * AlphaBet.base.length);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length() - 1; i = i + 2) {
            StringBuilder temp = new StringBuilder();
            temp.append(text.charAt(i));
            temp.append(text.charAt(i + 1));
            int count = Bigrams.getBigramValue(temp.toString()) - b;
            while (count < 0) {
                count += AlphaBet.base.length * AlphaBet.base.length;
            }
            try {
                count *= Mathematics.getReverse(a, (AlphaBet.base.length * AlphaBet.base.length)).intValue();
            } catch (NullPointerException e) {

            }
            count %= (AlphaBet.base.length * AlphaBet.base.length);
            result.append(Bigrams.getBigramOnValue(count));
        }
        return result.toString();
    }

    public String encrypt(int a, int b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length() - 1; i = i + 2) {
            StringBuilder temp = new StringBuilder();
            temp.append(text.charAt(i));
            temp.append(text.charAt(i + 1));
            int count = Bigrams.getBigramValue(temp.toString());
            count = (a * count + b) % (AlphaBet.base.length * AlphaBet.base.length);
            result.append(Bigrams.getBigramOnValue(count));
        }
        return result.toString();
    }

    public static void main(String[] args) {
//        Text text = new Text("base/plainText.txt");
//        System.out.println(text.encrypt(355, 764));
//        System.out.println(text.encrypt(1316, 764));

        System.out.println(Bigrams.getBigramValue("юя"));
        System.out.println(Bigrams.getBigramOnValue(929));

        Text text = new Text("base/cipherText.txt");
        System.out.println(text.decrypt(355, 764));
        System.out.println(text.decrypt(1316, 764));

        System.out.println("***************************");
        text = new Text("base/text.txt");
        System.out.println(text.decrypt(385560, 700));
        System.out.println(text.decrypt(199, 700));
        System.out.println(text.decrypt(231800, 700));

    }
}
